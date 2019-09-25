/**
 * 
 */
package br.net.oi.contratardadosavulsos.bo.impl;

import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.COMMA;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.INTL_CODE;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.POR;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.PORTAL_WEB;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.REAIS;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.net.oi.commons.protocolo.bo.ProtocoloUNIPROBO;
import br.net.oi.contratardadosavulsos.bo.ContratarDadosAvulsosBO;
import br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants;
import br.net.oi.contratardadosavulsos.model.exception.ClienteInelegivelException;
import br.net.oi.contratardadosavulsos.model.exception.GenericException;
import br.net.oi.contratardadosavulsos.model.vo.PacoteVO;
import br.net.oi.poi.atend.auditoriaj14.commons.utils.AuditoriaUtilitario;
import br.net.oi.poi.atend.auditoriaj14.commons.vo.TratamentoOutputVO;
import br.net.oi.ws.associarprotocolounipro.service.enums.EStatus;
import br.net.oi.ws.associarprotocolounipro.service.enums.ETipoServico;
import br.net.oi.ws.associarprotocolounipro.to.ProtocoloNativoTO;
import br.net.oi.ws.common.vo.AtorVO;
import br.net.oi.ws.contratarpacotes3g.service.ContratarPacotes3GService;
import br.net.oi.ws.contratarpacotes3g.to.ContratarPacotes3GServiceInput;
import br.net.oi.ws.contratarpacotes3g.to.ContratarPacotes3GServiceOutput;
import br.net.oi.ws.verificarelegibilidademsisdn.service.VerificarElegibilidadeMSISDNService;
import br.net.oi.ws.verificarelegibilidademsisdn.to.OfertaVO;
import br.net.oi.ws.verificarelegibilidademsisdn.to.VerificarElegibilidadeMSISDNServiceInput;
import br.net.oi.ws.verificarelegibilidademsisdn.to.VerificarElegibilidadeMSISDNServiceOutput;

/**
 * Business implementation for ContratarDadosAvulsosBO
 * 
 * @author mark.gary.m.lalap
 */
@Service(ContratarDadosAvulsosConstants.CONTRATAR_BO)
public class ContratarDadosAvulsosBOImpl implements ContratarDadosAvulsosBO {

	/**
	 * LOGGER variable
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ContratarDadosAvulsosBOImpl.class);

	private static final String PORTAL_WEB = "PORTAL_WEB";

	@Inject
	@Named(ContratarDadosAvulsosConstants.PORTAL_CONFIGURATION)
	private Configuration propPortal;

	@Autowired
	private ProtocoloUNIPROBO protocoloUNIPROBO;

	@Autowired
	private VerificarElegibilidadeMSISDNService verificarElegibilidadeMSISDNService;

	@Autowired
	private ContratarPacotes3GService contratarPacotes3GService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.net.oi.contratardadosavulsos.bo.ContratarDadosAvulsosBO
	 * #generateProtocol(java.lang.String)
	 */
	public String generateProtocol(final String msisdn) {
		String protocolo = StringUtils.EMPTY;
		
		// Auditoria
		Date dataEnvio = new Date();
		long miTempoAntesChamada = System.currentTimeMillis();
		
		TratamentoOutputVO tratamento = null;
		if (StringUtils.isNotBlank(msisdn)) {
			try {
				// call protocoloUNIPRO WS for protocol generation
				protocolo = protocoloUNIPROBO.gerarProtocolo(msisdn, StringUtils.EMPTY);
			} catch (final Exception e) {
				tratamento = new TratamentoOutputVO();
				tratamento.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
				tratamento.setDescricao(e.getMessage());
				tratamento.setDetalhes(e.getClass().getName());
				LOGGER.error("Ocorreu um erro no AberturaProtocoloService", e);
			} finally {
				AuditoriaUtilitario.auditoriaIntegracao(new Object[] { msisdn }, new Object[] { protocolo }, dataEnvio, tratamento, miTempoAntesChamada,
						propPortal.getInt(ContratarDadosAvulsosConstants.PROP_GERAR_ID), propPortal.getString(ContratarDadosAvulsosConstants.PROP_MENSAGEM_SUCESSO));
			}
		}
		return protocolo;
	}

	/**
	 * Associate a Web Service protocol by a UNIPRO generated protocol
	 * 
	 * @param msisdn
	 * @param protocoloPai
	 * @param protocoloFilho
	 * @return
	 */
	private String associateProtocol(final String msisdn, final String protocoloPai, final String protocoloFilho) {
		String protocolo = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(msisdn)) {			
			if (StringUtils.isNotBlank(protocoloPai)) {
			Date dataEnvio = new Date();
			long miTempoAntesChamada = System.currentTimeMillis();
			ProtocoloNativoTO protocoloNativoTO = null;
			TratamentoOutputVO tratamento = null;
			try {
				// call protocoloUNIPRO WS for protocol association
				protocoloNativoTO = new ProtocoloNativoTO(msisdn, StringUtils.EMPTY, EStatus.FECHADO.getFlag(), ContratarDadosAvulsosConstants.COD_POSTULACAO_OUTROS,
						ETipoServico.SOLICITACAO_SERVICO.getCodigo(), protocoloFilho);
				final boolean associacaoProtocolo = protocoloUNIPROBO.associarProtocolo(protocoloPai, protocoloNativoTO);
				protocolo = protocoloUNIPROBO.obterProtocoloExecucao(protocoloPai, protocoloNativoTO.getProtocolo(), associacaoProtocolo);
			} catch (final Exception e) {
				protocolo = protocoloFilho;
				tratamento = new TratamentoOutputVO();
				tratamento.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
				tratamento.setDescricao(e.getMessage());
				tratamento.setDetalhes(e.getClass().getName());
				LOGGER.error("Ocorreu um erro no AssociarProtocoloService", e);
			} finally {
				AuditoriaUtilitario.auditoriaIntegracao(new Object[] { protocoloNativoTO, protocoloPai }, new Object[] { protocolo }, dataEnvio, tratamento, miTempoAntesChamada,
						propPortal.getInt(ContratarDadosAvulsosConstants.PROP_ASSOCIAR_ID), propPortal.getString(ContratarDadosAvulsosConstants.PROP_MENSAGEM_SUCESSO));
			}
			} else {				
				protocolo = protocoloFilho;
			}
		}
		return protocolo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.net.oi.contratardadosavulsos.bo.ContratarDadosAvulsosBO
	 * #verifyElegibility(java.lang.String, java.lang.String)
	 */
	public List<PacoteVO> verifyElegibility(final String msisdn, final String origem) throws Exception {
		
		Date dataEnvio = new Date();
		long miTempoAntesChamada = System.currentTimeMillis();
		List<PacoteVO> listaPacotes = null;
		TratamentoOutputVO tratamento = null;
		VerificarElegibilidadeMSISDNServiceOutput output = null;
		VerificarElegibilidadeMSISDNServiceInput input = null;
		if (StringUtils.isNotBlank(msisdn)) {
			try {
				input = new VerificarElegibilidadeMSISDNServiceInput();
				input.setAtor(new AtorVO(PORTAL_WEB));
				input.setMsisdn(msisdn);
				input.setOrigem(origem);

				// call VerificarElegibilidade service for checking msisdn elegibility
				output = verificarElegibilidadeMSISDNService.verificarElegibilidadeMSISDN(input);
				if (output != null) {
					// on success response, put the packages in the list to return
					if (output.getResponseControl() != null &&
							verificarElegibilidadeMSISDNService.retornouComSucesso(output.getResponseControl().getCode())) {
						listaPacotes = transformServicePackage(output);
					} else {
						final Exception e = new ClienteInelegivelException("Ineligible request received.");
						LOGGER.error(StringUtils.EMPTY, e);
						tratamento = new TratamentoOutputVO();
						tratamento.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
						tratamento.setDescricao(e.getMessage());
						tratamento.setDetalhes(e.getClass().getName());
						throw e;
					}
				}
			} catch (final Exception e) {
				tratamento = new TratamentoOutputVO();
				tratamento.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
				tratamento.setDescricao(e.getMessage());
				tratamento.setDetalhes(e.getClass().getName());
				LOGGER.error("Ocorreu um erro no VerificarElegibilidadeService", e);
				throw e;
			} finally {
				AuditoriaUtilitario.auditoriaIntegracao(new Object[] { input }, new Object[] { output }, dataEnvio, tratamento, miTempoAntesChamada,
						propPortal.getInt(ContratarDadosAvulsosConstants.PROP_VERIFICAR_ID), propPortal.getString(ContratarDadosAvulsosConstants.PROP_MENSAGEM_SUCESSO));
			}
		}
		return listaPacotes;
	}

	/**
	 * Transform service packages to business transform object
	 * 
	 * @param output
	 * @return
	 */
	private List<PacoteVO> transformServicePackage(final VerificarElegibilidadeMSISDNServiceOutput output) {
		List<PacoteVO> listaPacotes = Collections.emptyList();
		if (output != null && !CollectionUtils.isEmpty(output.getListaOferta())) {
			final List<OfertaVO> listaOferta = output.getListaOferta();
			listaPacotes = new ArrayList<PacoteVO>();
			// iterate through list of packages to set in the BO model object
			for (final OfertaVO o : listaOferta) {
				final PacoteVO pacote = new PacoteVO();
				pacote.setIdOferta(o.getIdOferta());
				pacote.setIdCampanha(o.getIdCampanha());
				pacote.setDescricao(o.getDescricao());
				pacote.setDataCorte(o.getDataCorte());
				
				if(StringUtils.isNotBlank(o.getDescricao())){
					final String arrayString[] = breakDescricao(o.getDescricao());
					// Breakdown of descricao
					pacote.setPlan(arrayString[0]);
					pacote.setPrice(arrayString[1]);
					pacote.setDecimal(arrayString[2]);					
				}else{
					pacote.setPlan(StringUtils.EMPTY);
					pacote.setPrice(StringUtils.EMPTY);
					pacote.setDecimal(StringUtils.EMPTY);
				}

				listaPacotes.add(pacote);
			}
		}

		return listaPacotes;
	}

	/**
	 * Transform description by plan, price, decimal
	 * @param descricao
	 * @return
	 */
	private String[] breakDescricao(String descricao) {
		final String arrayString[] = new String[3];
		try{
			final int i = descricao.indexOf(REAIS);
			final String s = descricao.substring(i, descricao.length()).replace(REAIS, StringUtils.EMPTY).trim();
			
			arrayString[0] = descricao.substring(0, descricao.indexOf(POR)).trim();
			arrayString[1] = s.substring(0, s.indexOf(COMMA)).trim();
			arrayString[2] = s.substring(s.indexOf(COMMA) + 1, s.length()).trim();			
		}catch(Exception e){
			//Error occurs on breaking the description
			//Reason: The description doesn't have the correct format []
			LOGGER.error("Exception Occurs on breaking the descricao:",e);
			arrayString[0] = StringUtils.EMPTY;
			arrayString[1] = StringUtils.EMPTY;
			arrayString[2] = StringUtils.EMPTY;			
		}
		return arrayString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.net.oi.contratardadosavulsos.bo.ContratarDadosAvulsosBO
	 * #contract(java.lang.String, java.lang.String,
	 * br.net.oi.contratardadosavulsos.model.vo.PacoteVO, java.lang.String)
	 */
	public String contract(final String msisdn, final String origem, final PacoteVO pacote, final String protocoloPai) throws GenericException, RemoteException {
		
		Date dataEnvio = new Date();
		long miTempoAntesChamada = System.currentTimeMillis();
		String protocolo = StringUtils.EMPTY;
		TratamentoOutputVO tratamento = null;
		ContratarPacotes3GServiceOutput output = null;
		if (StringUtils.isNotBlank(msisdn) && pacote != null) {
			final ContratarPacotes3GServiceInput input = new ContratarPacotes3GServiceInput();
			input.setAtor(new AtorVO(PORTAL_WEB));
			input.setIdCampanha(pacote.getIdCampanha());
			input.setIdOferta(pacote.getIdOferta());
			input.setMsisdn(msisdn);
			input.setOrigem(origem);
			input.setPacoteDados(pacote.getDescricao());

			try {
				// call ContratarPacotes3G for contracting the selected package
				output = contratarPacotes3GService.contratarPacotes3G(input);
				if (output != null) {
					final String code = output.getResponseControl().getCode();
					final String successCode = propPortal.getString(ContratarDadosAvulsosConstants.PROP_CONTRATAR_CODIGO_SUCESSO);
					// on success response, set the protocol to the String to
					// return
					if (StringUtils.isNotBlank(code) && (code.equalsIgnoreCase(successCode))) {
						final String protocoloFilho = output.getNumTT();
						protocolo = associateProtocol(msisdn, protocoloPai, protocoloFilho);
						
						if(StringUtils.isBlank(protocolo)){
							LOGGER.info("Protocol Association Failed - returning protocoloPai - [{}]",protocoloPai);
							protocolo = protocoloPai;
						}
					} else {
						final GenericException e = new GenericException("Service returned error code.");
						LOGGER.error(StringUtils.EMPTY, e);
						tratamento = new TratamentoOutputVO();
						tratamento.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
						tratamento.setDescricao(e.getMessage());
						tratamento.setDetalhes(e.getClass().getName());
						throw e;
					}
				}
			} catch (final RemoteException e) {
				tratamento = new TratamentoOutputVO();
				tratamento.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
				tratamento.setDescricao(e.getMessage());
				tratamento.setDetalhes(e.getClass().getName());
				LOGGER.error("Ocorreu um erro no ContratarPacotes3GService", e);
				throw e;
			} finally {
				AuditoriaUtilitario.auditoriaIntegracao(new Object[] { input }, new Object[] { output }, dataEnvio, tratamento, miTempoAntesChamada,
						propPortal.getInt(ContratarDadosAvulsosConstants.PROP_CONTRACT_ID), propPortal.getString(ContratarDadosAvulsosConstants.PROP_MENSAGEM_SUCESSO));
			}
		}
		return protocolo;
	}
	
	public String verificaNumeroTerminal(String msisdn){

		if (StringUtils.isNotBlank(msisdn) && msisdn.length() > 11) {
			String DDI = msisdn.substring(0,2);
			String terminal = msisdn.substring(2);

			if (DDI.equals(INTL_CODE)) {
				msisdn = terminal;
			}
		}
		
		return msisdn;
		
	}


	/**
	 * @param verificarElegibilidadeMSISDNService
	 *            the verificarElegibilidadeMSISDNService to set
	 */
	public void setVerificarElegibilidadeMSISDNService(final VerificarElegibilidadeMSISDNService verificarElegibilidadeMSISDNService) {
		this.verificarElegibilidadeMSISDNService = verificarElegibilidadeMSISDNService;
	}

	/**
	 * @param contratarPacotes3GService
	 *            the contratarPacotes3GService to set
	 */
	public void setContratarPacotes3GService(final ContratarPacotes3GService contratarPacotes3GService) {
		this.contratarPacotes3GService = contratarPacotes3GService;
	}

	/**
	 * @param protocoloUNIPROBO
	 *            the protocoloUNIPROBO to set
	 */
	public void setProtocoloUNIPROBO(final ProtocoloUNIPROBO protocoloUNIPROBO) {
		this.protocoloUNIPROBO = protocoloUNIPROBO;
	}

	/**
	 * @param propPortal
	 *            the propPortal to set
	 */
	public void setPropPortal(final Configuration propPortal) {
		this.propPortal = propPortal;
	}

	
	
}
