package br.net.oi.contratardadosavulsos.web.controller;

import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_DATA_CORTE;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_DATE;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_DECIMAL;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_FLAG_ERROR_SELECT;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_IP;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_MSISDN;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_PACKAGES;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_PLAN;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_PRICE;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_PROTOCOL;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_PROTOCOLO;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_SELECTED;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_SELECTED_ID;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_SELECTED_ID_CAMPANHA;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ATTR_SELECTED_PACKAGE;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ORIGEM_REDIRECT;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.PORTAL_CONFIGURATION;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.PROP_CONFIRMAR_PACOTES_ID_OPERACAO;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.PROP_CONTRATAR_PACOTES_ID_OPERACAO;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.PROP_ENDPOINT;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.PROP_ID_FUNCIONALIDADE_REDIRECT;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.PROP_MENSAGEM_SUCESSO;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.PROP_ORIGEM;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.PROP_TIMEOUT;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.PROP_VERIFICAR_PACOTES_ID_OPERACAO;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.REQUEST_DADOS_CONFIRM;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.REQUEST_DADOS_CONTRACT;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.REQUEST_DADOS_HOME;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.REQUEST_DADOS_REDUCE;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.VIEW_CONFIRM_PAGE;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.VIEW_ERROR_CONNECTION_PAGE;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.VIEW_HOME_PAGE;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.VIEW_REDUCE_PAGE;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.VIEW_SUCCESS_PAGE;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.net.oi.contratardadosavulsos.bo.ContratarDadosAvulsosBO;
import br.net.oi.contratardadosavulsos.model.exception.GenericException;
import br.net.oi.contratardadosavulsos.model.exception.PacoteGenericException;
import br.net.oi.contratardadosavulsos.model.vo.ContratarDadosAvulsosExtraInfo;
import br.net.oi.contratardadosavulsos.model.vo.PacoteVO;
import br.net.oi.contratardadosavulsos.util.ContratarDadosAvulsosUtil;
import br.net.oi.contratardadosavulsos.web.command.PackageCommandVO;
import br.net.oi.poi.atend.auditoriaj14.commons.utils.AuditoriaUtilitario;
import br.net.oi.poi.atend.auditoriaj14.commons.vo.TratamentoOutputVO;
import br.net.oi.poi.atend.auditoriaj14.commons.vo.UserInfo;

/**
 * 
 * @author edward.l.gabriel
 * 
 */
@Controller
public class ContratarPacoteDadosController extends AbstractController {

	/**
	 * LOGGER variable
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ContratarPacoteDadosController.class);

	@Autowired
	private ContratarDadosAvulsosBO bo;

	@Inject
	@Named(PORTAL_CONFIGURATION)
	private Configuration propPortal;

	@ModelAttribute("webCommand")
	public PackageCommandVO getCommand() {
		return new PackageCommandVO();
	}

	/**
	 * Render homepage of the application
	 * 
	 * @param request
	 * @return homePage view
	 * @throws Exception 
	 */
	@RequestMapping(value = REQUEST_DADOS_HOME)
	public ModelAndView render(final HttpServletRequest request, final HttpServletResponse response, final boolean hasSelectErrors) throws Exception {

		LOGGER.info("START -- ContratarPacoteDadosController.render()");

		Map<String, Object> modelMap = new HashMap<String, Object>();
		String protocol = getSessionProtocol(request.getSession());
		String view = VIEW_HOME_PAGE;
		
		final Date dataEnvio = new Date();
		TratamentoOutputVO outputVO = null;

		final UserInfo userInfoVO = getDefaultUserInfo(request);
		//userInfoVO.setIpUsuario(getFormattedIPAddress(request.getRemoteAddr(), request.getRemotePort()));
		userInfoVO.setIpUsuario(getFormattedIPAddress(request));
		LOGGER.info("IP_Usuario:" + userInfoVO.getIpUsuario());
		final String msisdn = getMSISDN(request);
		final String origem = propPortal.getString(PROP_ORIGEM);

		try {
			if (StringUtils.isNotBlank(msisdn) || StringUtils.isNotBlank(origem)) {
				LOGGER.info("Parameter not empty: msisdn [" + msisdn + "] - origem [" + origem + "]");
				// call UNIPRO Service for protocol generation
				if (StringUtils.isEmpty(protocol)) {
					protocol = bo.generateProtocol(msisdn);
					setSessionProtocol(protocol, request);
				}
					
				final List<PacoteVO> packageList = getPackage(request, msisdn, origem);

				if (!CollectionUtils.isEmpty(packageList)) {
					modelMap = showPackageList(request, packageList, msisdn, protocol, hasSelectErrors);
				} else {
					// in case the msisdn and channel is elegible but the
					// service
					// returned an empty package list, display error page
					final PacoteGenericException e = new PacoteGenericException("O Serviço de elegibilidade retornou uma lista Vazia");
					outputVO = new TratamentoOutputVO();
					outputVO.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
					outputVO.setDescricao(e.getMessage());
					outputVO.setDetalhes(e.getClass().getName());

					logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(PROP_VERIFICAR_PACOTES_ID_OPERACAO), userInfoVO);

					LOGGER.error(StringUtils.EMPTY, e);
					throw e;
				}					
			} else {
				// in case the msisdn or origem returned empty
				// display error page
				final PacoteGenericException e = new PacoteGenericException("As informacoes do Headers nao foram enviadas. ");
				outputVO = new TratamentoOutputVO();
				outputVO.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
				outputVO.setDescricao(e.getMessage());
				outputVO.setDetalhes(e.getClass().getName());
				logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(PROP_VERIFICAR_PACOTES_ID_OPERACAO), userInfoVO);
				if (LOGGER.isInfoEnabled()) {
					LOGGER.error(StringUtils.EMPTY, e);
				}
				throw e;
			}
		} catch (final Exception e) {
			final PacoteGenericException e1 = new PacoteGenericException("O Serviço de elegibilidade retornou uma lista Vazia", e);
			LOGGER.error(StringUtils.EMPTY, e);
			throw e1;
		}
		
		return new ModelAndView(view, modelMap);
	}

	/**
	 * Render confirmation screen for contracting packages
	 * 
	 * @return homePage view
	 */
	@RequestMapping(value = REQUEST_DADOS_CONFIRM)
	public ModelAndView viewConfirmationScreen(final HttpServletRequest request, final HttpServletResponse response, 
			@ModelAttribute("webCommand") PackageCommandVO cmd) {
		LOGGER.info("START -- ContratarPacoteDadosController.viewConfirmationScreen()");
		final Map<String, Object> modelMap = new HashMap<String, Object>();
		final String packageId = cmd.getIdOferta();
		final Date dataEnvio = new Date();
		ModelAndView mv = null;

		LOGGER.info("Selected package ID [" + packageId + "]");

		try {
			final UserInfo userInfoVO = getDefaultUserInfo(request);
			//userInfoVO.setIpUsuario(getFormattedIPAddress(request.getRemoteAddr(), request.getRemotePort()));
			userInfoVO.setIpUsuario(getFormattedIPAddress(request));
			LOGGER.info("IP_Usuario:" + userInfoVO.getIpUsuario());
			// if the user did not select a package from the list,
			// render the initial page with error messages
			if (StringUtils.isBlank(packageId)) {
				mv = render(request, response, true);
			} else {
				//final String idOferta = cmd.getIdOferta();
				final String idCampanha = cmd.getIdCampanha();
				final String descricao = cmd.getDescricao();
	
				// check if selected package ID matches the package in
				// CommandVO
				LOGGER.info("Selected package [" + descricao + "]");
				modelMap.put(ATTR_SELECTED_ID, packageId);
				modelMap.put(ATTR_SELECTED, descricao);
				modelMap.put(ATTR_SELECTED_ID_CAMPANHA, idCampanha);
				modelMap.put(ATTR_PLAN, cmd.getPlan());
				modelMap.put(ATTR_PRICE, cmd.getPrice());
				modelMap.put(ATTR_DECIMAL, cmd.getDecimal());
				modelMap.put(ATTR_DATA_CORTE, cmd.getDataCorte());
	
				userInfoVO.setIdUsuario(getMSISDN(request.getSession()));
				userInfoVO.setProtocolo(getSessionProtocol(request.getSession()));
	
				mv = new ModelAndView(VIEW_CONFIRM_PAGE, modelMap);
				logAuditUserInfo(null, dataEnvio, request, null, null, propPortal.getString(PROP_CONFIRMAR_PACOTES_ID_OPERACAO), userInfoVO);
			}
		} catch (final Exception e) {
			mv = new ModelAndView(VIEW_ERROR_CONNECTION_PAGE);
		}
		
		LOGGER.info("END -- ContratarPacoteDadosController.viewConfirmationScreen()");
		return mv;
	}

	@RequestMapping(value = REQUEST_DADOS_CONTRACT)
	public ModelAndView contract(final HttpServletRequest request, final HttpServletResponse response, 
			@RequestParam("idCampanha") String idCampanha, 
			@RequestParam("descricao") String descricao, 
			@RequestParam("idOferta") String idOferta) {
		LOGGER.info("START -- ContratarPacoteDadosController.contract()");
		final Map<String, Object> modelMap = new HashMap<String, Object>();
		String view = VIEW_SUCCESS_PAGE;
		
		try {
			final HttpSession session = request.getSession();
			PacoteVO selectedPackage = null;
			
			if (StringUtils.isNotBlank(idOferta)) {
				selectedPackage = createSelectedPackage(descricao, idCampanha, idOferta);
			}
	
			LOGGER.info("Selected package [" + selectedPackage + "]");
	
			final String sessionProtocol = getSessionProtocol(session);
			final String msisdn = getMSISDN(session);
			final String origem = propPortal.getString(PROP_ORIGEM);
			Date dataEnvio = new Date();
			final UserInfo userInfoVO = getDefaultUserInfo(request);
			//userInfoVO.setIpUsuario(getFormattedIPAddress(request.getRemoteAddr(), request.getRemotePort()));
			userInfoVO.setIpUsuario(getFormattedIPAddress(request));
			userInfoVO.setIdUsuario(msisdn);
			userInfoVO.setProtocolo(sessionProtocol);
			LOGGER.info("IP_Usuario:" + userInfoVO.getIpUsuario());
			// check if selected package ID matches a package from the list in
			// HttpSession
			if (selectedPackage != null) {
				// call the web service to contract for the selected package
				LOGGER.info("#### Selected package 2 [" + selectedPackage + "]");
				try {
					final String protocol = bo.contract(msisdn, origem, selectedPackage, sessionProtocol);
					modelMap.put(ATTR_SELECTED_PACKAGE, selectedPackage.getDescricao());
					modelMap.put(ATTR_PROTOCOL, protocol);
					modelMap.put(ATTR_DATE, ContratarDadosAvulsosUtil.getCurrentDate());
					LOGGER.info(" #### protocol [" + protocol + "]");
				} catch (Exception e) {
					final TratamentoOutputVO outputVO = createDefaultTratamentoOutput(e);
					logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(PROP_CONTRATAR_PACOTES_ID_OPERACAO), userInfoVO);
					view = VIEW_ERROR_CONNECTION_PAGE;
				}
			} else {
				// display error page if no packages match and if list is empty
				final GenericException e = new GenericException("Ocorreu um erro técnico");
				final TratamentoOutputVO outputVO = createDefaultTratamentoOutput(e);
				logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(PROP_CONTRATAR_PACOTES_ID_OPERACAO), userInfoVO);
				LOGGER.error(StringUtils.EMPTY, e);
				view = VIEW_ERROR_CONNECTION_PAGE;
			}
			LOGGER.info(" #### view [" + view + "]");
			logAuditUserInfo(null, dataEnvio, request, null, null, propPortal.getString(PROP_CONTRATAR_PACOTES_ID_OPERACAO), userInfoVO);
			LOGGER.info("END -- ContratarPacoteDadosController.contract()");
		} catch (final Exception e) {
			view = VIEW_ERROR_CONNECTION_PAGE;
		}
		return new ModelAndView(view, modelMap);
	}
	
	@RequestMapping(value = REQUEST_DADOS_REDUCE)
	public ModelAndView reduce (final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.info("START -- ContratarPacoteDadosController.reduce");
		LOGGER.info("END -- ContratarPacoteDadosController.reduce");
		return new ModelAndView(VIEW_REDUCE_PAGE);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param msisdn
	 * @param origem
	 * @return
	 * @throws Exception 
	 */
	private List<PacoteVO> getPackage(final HttpServletRequest request, final String msisdn, final String origem) throws Exception {
		List<PacoteVO> packageList = getSessionPackages(request, ORIGEM_REDIRECT);
		if (CollectionUtils.isEmpty(packageList)) {
			// send the msisdn and channel for elegibility verification
			// if(StringUtils.isNotBlank(request.getParameter("USUARIO_IP"))) {
			packageList = bo.verifyElegibility(msisdn, origem);
			// put the packages in HttpSession when available
			setSessionPackages(packageList, request, ORIGEM_REDIRECT);
			// } else {
			// //request.getRequestDispatcher("/web/home?USUARIO_IP=" +
			// request.getRemoteAddr()).forward(request, response);
			// }
		}
		
		return packageList;
	}

	/**
	 * Do a web application auditing
	 * 
	 * @param request
	 * @param msisdn
	 * @param origem
	 */
	private void logAuditUserInfo(final TratamentoOutputVO outputVO, Date dataEnvio, final HttpServletRequest request, final String msisdn, final String origem, final String idOperacao,
			UserInfo userInfoVo) {
		/* START Web Application Auditing */
		LOGGER.info("START -- ContratarPacoteDadosController.logAudit()");
		// Extra Info for IP Address, msisdn and channel
		final ContratarDadosAvulsosExtraInfo extraInfo = new ContratarDadosAvulsosExtraInfo();
		extraInfo.setMsisdn(msisdn);
		extraInfo.setOrigem(origem);

		// Portal Parameters
		final int idFuncionalidade = propPortal.getInt(PROP_ID_FUNCIONALIDADE_REDIRECT);
		final String endpoint = propPortal.getString(PROP_ENDPOINT);
		final int timeout = propPortal.getInt(PROP_TIMEOUT);
		final String mensagem = propPortal.getString(PROP_MENSAGEM_SUCESSO);

		AuditoriaUtilitario.auditoriaRequisicao(request, dataEnvio, outputVO, idFuncionalidade, extraInfo, idOperacao, endpoint, timeout, mensagem, userInfoVo);
		LOGGER.info("END -- ContratarPacoteDadosController.logAudit()");
		/* END Web Application Auditing */
	}
	
	/**
	 * 
	 * @param request
	 * @param packageList
	 * @param msisdn
	 * @param protocol
	 * @param hasSelectErrors
	 * @return
	 */
	private ModelMap showPackageList(final HttpServletRequest request, final List<PacoteVO> packageList, final String msisdn, final String protocol, 
			final boolean hasSelectErrors){
		final ModelMap modelMap = new ModelMap();
		final Date dataEnvio = new Date();
		modelMap.put(ATTR_PACKAGES, packageList);
		modelMap.put(ATTR_MSISDN, msisdn);
		modelMap.put(ATTR_FLAG_ERROR_SELECT, hasSelectErrors);
		
		final UserInfo userInfoVO = getDefaultUserInfo(request);
		userInfoVO.setIdUsuario(msisdn);
		userInfoVO.setProtocolo(protocol);
		//userInfoVO.setIpUsuario(getFormattedIPAddress(request.getRemoteAddr(), request.getRemotePort()));
		userInfoVO.setIpUsuario(getFormattedIPAddress(request));
		LOGGER.info("IP_Usuario:" + userInfoVO.getIpUsuario());
		// Usuário não selecionou pacote
		if (hasSelectErrors) {
			TratamentoOutputVO outputVO = new TratamentoOutputVO();
			outputVO.setCodigo(TratamentoOutputVO.ERRO_NEGOCIO);
			outputVO.setDescricao("Usuário não selecionou pacote.");
			outputVO.setDetalhes("Redirecionando para tela de seleção de pacotes.");
			logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(PROP_VERIFICAR_PACOTES_ID_OPERACAO), userInfoVO);
		} else {
			logAuditUserInfo(null, dataEnvio, request, null, null, propPortal.getString(PROP_VERIFICAR_PACOTES_ID_OPERACAO), userInfoVO);
		}

		modelMap.put(ATTR_PROTOCOLO, protocol);
		modelMap.put(ATTR_IP, request.getRemoteAddr());
		LOGGER.info("END -- ContratarPacoteDadosController.render()");
		return modelMap;
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	private TratamentoOutputVO createDefaultTratamentoOutput(final Exception e){
		final TratamentoOutputVO outputVO = new TratamentoOutputVO();
		outputVO.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
		outputVO.setDescricao(e.getMessage());
		outputVO.setDetalhes(e.getClass().getName());
		return outputVO;
	}
	
	private PacoteVO createSelectedPackage(final String descricao, final String idCampanha, final String idOferta){
		final PacoteVO selectedPackage = new PacoteVO();
		selectedPackage.setDescricao(descricao);
		selectedPackage.setIdCampanha(idCampanha);
		selectedPackage.setIdOferta(idOferta);
		
		return selectedPackage;
	}

	/**
	 * @param bo
	 *            the bo to set
	 */
	@Override
	public void setBo(final ContratarDadosAvulsosBO bo) {
		this.bo = bo;
	}

	/**
	 * @param propPortal
	 *            the propPortal to set
	 */
	@Override
	public void setPropPortal(final Configuration propPortal) {
		this.propPortal = propPortal;
	}

}
