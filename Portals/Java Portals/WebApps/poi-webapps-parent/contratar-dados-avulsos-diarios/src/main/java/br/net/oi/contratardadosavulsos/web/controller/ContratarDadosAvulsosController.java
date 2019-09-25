/**
 * 
 */
package br.net.oi.contratardadosavulsos.web.controller;

import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ORIGEM_MOBILE;

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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.net.oi.contratardadosavulsos.bo.ContratarDadosAvulsosBO;
import br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants;
import br.net.oi.contratardadosavulsos.model.exception.ErroConexaoException;
import br.net.oi.contratardadosavulsos.model.exception.GenericException;
import br.net.oi.contratardadosavulsos.model.vo.ContratarDadosAvulsosExtraInfo;
import br.net.oi.contratardadosavulsos.model.vo.PacoteVO;
import br.net.oi.contratardadosavulsos.util.ContratarDadosAvulsosUtil;
import br.net.oi.poi.atend.auditoriaj14.commons.utils.AuditoriaUtilitario;
import br.net.oi.poi.atend.auditoriaj14.commons.vo.TratamentoOutputVO;
import br.net.oi.poi.atend.auditoriaj14.commons.vo.UserInfo;

/**
 * Controller class for contracting 3G Internet packages
 * 
 * @author mark.gary.m.lalap
 */
@Controller
public class ContratarDadosAvulsosController extends AbstractController {

	/**
	 * LOGGER variable
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ContratarDadosAvulsosController.class);

	@Autowired
	private ContratarDadosAvulsosBO bo;

	@Inject
	@Named(ContratarDadosAvulsosConstants.PORTAL_CONFIGURATION)
	private Configuration propPortal;

	/**
	 * Render homepage of the application
	 * 
	 * @param request
	 * @return homePage view
	 * @throws Exception 
	 * @throws throws GenericException
	 */
	@RequestMapping(value = ContratarDadosAvulsosConstants.REQUEST_HOME)
	public ModelAndView render(final HttpServletRequest request, final HttpServletResponse response, final boolean hasSelectErrors) throws Exception {
		debug("START -- ContratarDadosAvulsosController.render()");

		final Map<String, Object> modelMap = new HashMap<String, Object>();
		final Map<String, String> headerMap = getHeaderAttributes(request);
		final String protocolFromRequest = request.getParameter("PROCOLO_OP");
		String protocol = null;
		Date dataEnvio = new Date();
		TratamentoOutputVO outputVO = null;
		
		UserInfo userInfoVO = new UserInfo();
		userInfoVO.setCpfUsuario("");
		userInfoVO.setIdUsuario("");
		//userInfoVO.setIpUsuario(getFormattedIPAddress(request.getRemoteAddr(), request.getRemotePort()));
		userInfoVO.setIpUsuario(getFormattedIPAddress(request));
		userInfoVO.setProtocolo("");
		userInfoVO.setTipoUsuario("");
		LOGGER.info("IP_Usuario:" + userInfoVO.getIpUsuario());

		debug("headerMap - " + headerMap.toString());
		if (!CollectionUtils.isEmpty(headerMap)) {
			// extract msisdn and channel from the header
			final String msisdn = headerMap.get(ContratarDadosAvulsosConstants.MSISDN);
			final String origem = headerMap.get(ContratarDadosAvulsosConstants.CHANNEL);

			debug("headerMap not empty: msisdn [" + msisdn + "] - origem [" + origem + "]");

			final List<PacoteVO> packageList = getPackage(request, response, msisdn, origem);
			
			if (!CollectionUtils.isEmpty(packageList)) {
				if(StringUtils.isBlank(protocolFromRequest)) {
					// call UNIPRO Service for protocol generation
					protocol = bo.generateProtocol(msisdn);
					if (StringUtils.isNotBlank(protocol)) {
						setSessionProtocol(protocol, request);
					}
				}
				
				modelMap.put(ContratarDadosAvulsosConstants.ATTR_PACKAGES, packageList);
				modelMap.put(ContratarDadosAvulsosConstants.ATTR_MSISDN, ContratarDadosAvulsosUtil.formatMSISDN(msisdn));
				modelMap.put(ContratarDadosAvulsosConstants.ATTR_FLAG_ERROR_SELECT, hasSelectErrors);
					
				userInfoVO.setIdUsuario(msisdn);
				userInfoVO.setProtocolo(protocol);
				
				//Usuário não selecionou pacote
				if (hasSelectErrors) {
					outputVO = new TratamentoOutputVO();
					outputVO.setCodigo(TratamentoOutputVO.ERRO_NEGOCIO);
					outputVO.setDescricao("Usuário não selecionou pacote.");
					outputVO.setDetalhes("Redirecionando para tela de seleção de pacotes.");
					logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(ContratarDadosAvulsosConstants.PROP_VERIFICAR_ID_OPERACAO), userInfoVO);
				} else { 
					logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(ContratarDadosAvulsosConstants.PROP_VERIFICAR_ID_OPERACAO), userInfoVO);
				}
				
			} else {
				// in case the msisdn and channel is elegible but the service
				// returned an empty package list, throw an Exception
				final GenericException e = new GenericException("O Servi�o de elegibilidade retornou uma lista Vazia");
				outputVO = new TratamentoOutputVO();
				outputVO.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
				outputVO.setDescricao(e.getMessage());
				outputVO.setDetalhes(e.getClass().getName());
					
				logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(ContratarDadosAvulsosConstants.PROP_VERIFICAR_ID_OPERACAO), userInfoVO);
				
				LOGGER.error(StringUtils.EMPTY, e);
				throw e;
			}
			
		} else {
			final GenericException e = new GenericException("As informa��es do Headers n�o foram enviadas. ");
			outputVO = new TratamentoOutputVO();
			outputVO.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
			outputVO.setDescricao(e.getMessage());
			outputVO.setDetalhes(e.getClass().getName());
			
			logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(ContratarDadosAvulsosConstants.PROP_VERIFICAR_ID_OPERACAO), userInfoVO);
			
			LOGGER.error(StringUtils.EMPTY, e);
			throw e;
		}

		modelMap.put(ContratarDadosAvulsosConstants.ATTR_PROTOCOLO, getSessionProtocol(request.getSession()));
		modelMap.put(ContratarDadosAvulsosConstants.ATTR_IP, request.getRemoteAddr());
		debug("END -- ContratarDadosAvulsosController.render()");
		return new ModelAndView(ContratarDadosAvulsosConstants.VIEW_HOME, modelMap);
	}

	/**
	 * Get the package from session, if not available, make a WS call
	 * 
	 * @param request
	 * @param msisdn
	 * @param origem
	 * @return
	 * @throws Exception 
	 */
	private List<PacoteVO> getPackage(final HttpServletRequest request, final HttpServletResponse response, final String msisdn, final String origem) throws Exception {
		List<PacoteVO> packageList = getSessionPackages(request, ORIGEM_MOBILE);
		if (CollectionUtils.isEmpty(packageList)) {
			// send the msisdn and channel for elegibility verification
//			if(StringUtils.isNotBlank(request.getParameter("USUARIO_IP"))) {
				packageList = bo.verifyElegibility(msisdn, origem);
				// put the packages in HttpSession when available
				setSessionPackages(packageList, request, ORIGEM_MOBILE);
//			} else {
//				//request.getRequestDispatcher("/web/home?USUARIO_IP=" + request.getRemoteAddr()).forward(request, response);
//			}
		}
		return packageList;
	}

	/**
	 * Render confirmation screen for contracting packages
	 * 
	 * @return homePage view
	 * @throws Exception 
	 * @throws throws GenericException
	 */
	@RequestMapping(value = ContratarDadosAvulsosConstants.REQUEST_CONFIRM)
	public ModelAndView viewConfirmationScreen(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		debug("START -- ContratarDadosAvulsosController.viewConfirmationScreen()");
		final Map<String, Object> modelMap = new HashMap<String, Object>();
		ModelAndView mv = null;
		final String packageId = request.getParameter(ContratarDadosAvulsosConstants.PARAM_PACOTE);
		Date dataEnvio = new Date();
		TratamentoOutputVO outputVO = null;
		
		debug("Selected package ID [" + packageId + "]");
		
		UserInfo userInfoVO = new UserInfo();
		userInfoVO.setCpfUsuario("");
		userInfoVO.setIdUsuario("");
		//userInfoVO.setIpUsuario(request.getRemoteAddr());
		userInfoVO.setIpUsuario(getFormattedIPAddress(request));
		LOGGER.info("IP_Usuario:" + userInfoVO.getIpUsuario());
		userInfoVO.setProtocolo("");
		userInfoVO.setTipoUsuario("");


		// if the user did not select a package from the list,
		// render the initial page with error messages
		if (StringUtils.isBlank(packageId)) {
			mv = render(request, response, true);
		} else {
			final List<PacoteVO> packageList = getSessionPackages(request.getSession(), ORIGEM_MOBILE);
			final PacoteVO selectedPackage = ContratarDadosAvulsosUtil.getSelected(packageList, packageId);
			debug("Selected package [" + selectedPackage + "]");

			// check if selected package ID matches a package from the list in
			// HttpSession
			if (!CollectionUtils.isEmpty(packageList) && selectedPackage != null) {
				modelMap.put(ContratarDadosAvulsosConstants.ATTR_SELECTED_ID, packageId);
				modelMap.put(ContratarDadosAvulsosConstants.ATTR_SELECTED, selectedPackage.getDescricao());
				modelMap.put(ContratarDadosAvulsosConstants.ATTR_PROTOCOLO, getSessionProtocol(request.getSession()));
				modelMap.put(ContratarDadosAvulsosConstants.ATTR_IP, request.getRemoteAddr());
				mv = new ModelAndView(ContratarDadosAvulsosConstants.VIEW_CONFIRM, modelMap);
			} else {
				// throw an Exception if no packages match and if list is empty
				LOGGER.error(StringUtils.EMPTY, new GenericException("Ocorreu um erro t�cnico"));
				throw new GenericException("Ocorreu um erro t�cnico");
			}
			
			userInfoVO.setIdUsuario(getMSISDN(request.getSession()));
			userInfoVO.setProtocolo(getSessionProtocol(request.getSession()));
			
			logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(ContratarDadosAvulsosConstants.PROP_CONFIRMAR_ID_OPERACAO), userInfoVO);
		}
		
		debug("END -- ContratarDadosAvulsosController.viewConfirmationScreen()");
		return mv;
	}

	/**
	 * Render confirmation screen for contracting packages
	 * 
	 * @param request
	 * @param packageId
	 * @throws throws GenericException
	 * @return homePage view
	 */
	@RequestMapping(value = ContratarDadosAvulsosConstants.REQUEST_CONTRACT)
	public ModelAndView contract(final HttpServletRequest request, final HttpServletResponse response,
			@RequestParam(ContratarDadosAvulsosConstants.PARAM_PACOTE) final String packageId) throws Exception {
		debug("START -- ContratarDadosAvulsosController.contract()");
		final Map<String, Object> modelMap = new HashMap<String, Object>();
		final HttpSession session = request.getSession();
		final List<PacoteVO> packageList = getSessionPackages(session, ORIGEM_MOBILE);
		final PacoteVO selectedPackage = ContratarDadosAvulsosUtil.getSelected(packageList, packageId);
		debug("Selected package [" + selectedPackage + "]");
		
		final String sessionProtocol = getSessionProtocol(session);
		final String msisdn = getMSISDN(session);
		final String origem = getChannel(session);

		Date dataEnvio = new Date();
		TratamentoOutputVO outputVO = null;
		
		debug("Selected package ID [" + packageId + "]");
		
		UserInfo userInfoVO = new UserInfo();
		userInfoVO.setCpfUsuario("");
		//userInfoVO.setIpUsuario(request.getRemoteAddr());
		userInfoVO.setIpUsuario(getFormattedIPAddress(request));
		LOGGER.info("IP_Usuario:" + userInfoVO.getIpUsuario());
		userInfoVO.setTipoUsuario("");
		userInfoVO.setIdUsuario(msisdn);
		userInfoVO.setProtocolo(sessionProtocol);
		
		// check if selected package ID matches a package from the list in
		// HttpSession
		if (StringUtils.isNotBlank(packageId) && selectedPackage != null) {
			// call the web service to contract for the selected package
			try {
				final String protocol = bo.contract(msisdn, origem, selectedPackage, sessionProtocol);
				modelMap.put(ContratarDadosAvulsosConstants.ATTR_SELECTED_PACKAGE, selectedPackage.getDescricao());
				modelMap.put(ContratarDadosAvulsosConstants.ATTR_PROTOCOL, protocol);
				modelMap.put(ContratarDadosAvulsosConstants.ATTR_DATE, ContratarDadosAvulsosUtil.getCurrentDate());
				modelMap.put(ContratarDadosAvulsosConstants.ATTR_TIME, ContratarDadosAvulsosUtil.getCurrentTime());
			} catch (Exception e) {
				outputVO = new TratamentoOutputVO();
				outputVO.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
				outputVO.setDescricao(e.getMessage());
				outputVO.setDetalhes(e.getClass().getName());
								
				logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(ContratarDadosAvulsosConstants.PROP_CONTRATAR_ID_OPERACAO), userInfoVO);
				throw e;
			}
		} else {
			// throw an Exception if no packages match and if list is empty
			final GenericException e = new GenericException("Ocorreu um erro técnico");
			outputVO = new TratamentoOutputVO();
			outputVO.setCodigo(TratamentoOutputVO.ERRO_TECNICO);
			outputVO.setDescricao(e.getMessage());
			outputVO.setDetalhes(e.getClass().getName());
	
			logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(ContratarDadosAvulsosConstants.PROP_CONTRATAR_ID_OPERACAO), userInfoVO);
			LOGGER.error(StringUtils.EMPTY, e);
			throw e;
		}
		
		
		
		logAuditUserInfo(outputVO, dataEnvio, request, null, null, propPortal.getString(ContratarDadosAvulsosConstants.PROP_CONTRATAR_ID_OPERACAO), userInfoVO);
		debug("END -- ContratarDadosAvulsosController.contract()");
		return new ModelAndView(ContratarDadosAvulsosConstants.VIEW_SUCCESS, modelMap);
	}

	/**
	 * Render the error page where user does not select a package
	 * 
	 * @param request
	 * @return error page
	 */
	@RequestMapping(value = ContratarDadosAvulsosConstants.REQUEST_ERROR_CONNECTION)
	public void connectionError() throws ErroConexaoException {
		LOGGER.error(StringUtils.EMPTY, new ErroConexaoException("Ocorreu um erro t�cnico"));
		throw new ErroConexaoException("Ocorreu um erro t�cnico");
	}

	/**
	 * Do a web application auditing
	 * 
	 * @param request
	 * @param msisdn
	 * @param origem
	 */
	private void logAuditUserInfo(final TratamentoOutputVO outputVO, Date dataEnvio, final HttpServletRequest request, final String msisdn, 
								  final String origem, final String idOperacao, UserInfo userInfoVo) {
		/* START Web Application Auditing */
		debug("START -- ContratarDadosAvulsosController.logAudit()");
		// Extra Info for IP Address, msisdn and channel
		final ContratarDadosAvulsosExtraInfo extraInfo = new ContratarDadosAvulsosExtraInfo();
		extraInfo.setMsisdn(msisdn);
		extraInfo.setOrigem(origem);

		// Portal Parameters
		final int idFuncionalidade = propPortal.getInt(ContratarDadosAvulsosConstants.PROP_ID_FUNCIONALIDADE);
		final String endpoint = propPortal.getString(ContratarDadosAvulsosConstants.PROP_ENDPOINT);
		final int timeout = propPortal.getInt(ContratarDadosAvulsosConstants.PROP_TIMEOUT);
		final String mensagem = propPortal.getString(ContratarDadosAvulsosConstants.PROP_MENSAGEM_SUCESSO);
		
		AuditoriaUtilitario.auditoriaRequisicao(request, dataEnvio, outputVO, idFuncionalidade, extraInfo, idOperacao, endpoint, timeout, mensagem, userInfoVo);
		debug("END -- ContratarDadosAvulsosController.logAudit()");
		/* END Web Application Auditing */
	}

	/**
	 * @param bo
	 *            the bo to set
	 */
	public void setBo(final ContratarDadosAvulsosBO bo) {
		this.bo = bo;
	}

	/**
	 * @param propPortal
	 *            the propPortal to set
	 */
	public void setPropPortal(final Configuration propPortal) {
		this.propPortal = propPortal;
	}

}
