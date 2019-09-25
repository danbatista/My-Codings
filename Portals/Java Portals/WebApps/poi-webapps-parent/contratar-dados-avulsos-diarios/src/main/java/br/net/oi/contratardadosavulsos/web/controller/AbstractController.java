/**
 * 
 */
package br.net.oi.contratardadosavulsos.web.controller;

import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ORIGEM_MOBILE;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.ORIGEM_REDIRECT;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.SESSION_PACKAGES_MOBILE;
import static br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants.SESSION_PACKAGES_REDIRECT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.net.oi.contratardadosavulsos.bo.ContratarDadosAvulsosBO;
import br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants;
import br.net.oi.contratardadosavulsos.model.vo.PacoteVO;
import br.net.oi.poi.atend.auditoriaj14.commons.vo.UserInfo;

/**
 * Abstract Controller for Contratar Dados Avulsos
 * 
 * @author mark.gary.m.lalap
 */
public abstract class AbstractController {
	
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
	 * Returns Hash Map containing MSISDN and channel
	 * @param request
	 * @return
	 */
	protected Map<String, String> getHeaderAttributes(final HttpServletRequest request) {
		final Map<String, String> headerMap = new HashMap<String, String>();
		final String channel = getChannel(request);
		final String msisdn = getMSISDN(request);
		
		if(StringUtils.isNotBlank(channel)) {
			headerMap.put(ContratarDadosAvulsosConstants.CHANNEL, getChannel(request));
		}
		
		if(StringUtils.isNotBlank(msisdn)) {
			headerMap.put(ContratarDadosAvulsosConstants.MSISDN, getMSISDN(request));
		}
		
		return headerMap;
	}
	
	/**
	 * Returns the current channel of the user from HTTP request header
	 * @param request
	 * @return
	 */
	protected String getChannel(final HttpServletRequest request) {
		final HttpSession session = request.getSession();
		String channel = getChannel(session);
		
		if(StringUtils.isBlank(channel)) {
			// channel =
			// request.getHeader(ContratarDadosAvulsosConstants.CHANNEL);
			channel = propPortal.getString(ContratarDadosAvulsosConstants.PROP_CHANNEL);
			session.setAttribute(ContratarDadosAvulsosConstants.CHANNEL, channel);
		}
		
		return channel;
	}
	
	/**
	 * Returns the current channel of the user from HTTP request header
	 * @param session
	 * @return
	 */
	protected String getChannel(final HttpSession session) {
		return (String) session.getAttribute(ContratarDadosAvulsosConstants.CHANNEL);
	}
	
	/**
	 * Returns the current MSISDN of the user from HTTP request header
	 * @param request
	 * @return
	 */
	protected String getMSISDN(final HttpServletRequest request) {
		final HttpSession session = request.getSession();
		String msisdn = getMSISDN(session);
	
		if(StringUtils.isBlank(msisdn)) {
			msisdn = request.getHeader(ContratarDadosAvulsosConstants.MSISDN);
			msisdn = bo.verificaNumeroTerminal(msisdn);
			session.setAttribute(ContratarDadosAvulsosConstants.MSISDN, msisdn);
		}

		return msisdn;
	}
	
	/**
	 * Returns the current MSISDN of the user from HTTP request header
	 * @param session
	 * @return
	 */
	protected String getMSISDN(final HttpSession session) {
		return (String) session.getAttribute(ContratarDadosAvulsosConstants.MSISDN);
	}

	/**
	 * Returns the current MSISDN of the user from HTTP request header
	 * @param session
	 * @return
	 */
	protected String getIP(final HttpSession session) {
		return (String) session.getAttribute(ContratarDadosAvulsosConstants.IP);
	}
	
	/**
	 * Returns the current MSISDN of the user from HTTP request header
	 * @param session
	 * @return
	 */
	protected String getPort(final HttpSession session) {
		return (String) session.getAttribute(ContratarDadosAvulsosConstants.PORT);
	}
	
	/**
	 * Saves Web Service packages in session
	 * @param session
	 * @param pacotes
	 */
	protected void setSessionPackagesRedirect(final List<PacoteVO> pacotes, final HttpSession session) {
		session.setAttribute(SESSION_PACKAGES_REDIRECT, pacotes);
	}
	
	/**
	 * Saves Web Service packages in session
	 * @param session
	 * @param pacotes
	 */
	protected void setSessionPackagesMobile(final List<PacoteVO> pacotes, final HttpSession session){
		session.setAttribute(SESSION_PACKAGES_MOBILE, pacotes);
	}
	
	/**
	 * Saves Web Service packages in session
	 * @param request
	 * @param pacotes
	 */
	protected void setSessionPackages(final List<PacoteVO> pacotes, final HttpServletRequest request, final String origem) {
		if(ORIGEM_MOBILE.equals(origem)){
			setSessionPackagesMobile(pacotes, request.getSession());
		}else if(ORIGEM_REDIRECT.equals(origem)){
			setSessionPackagesRedirect(pacotes, request.getSession());
		}
	}
	
	/**
	 * Retrieves Web Service protocol in session
	 * @param session
	 * @return String session protocol
	 */
	protected String getSessionProtocol(final HttpSession session) {
		return (String) session.getAttribute(ContratarDadosAvulsosConstants.SESSION_PROTOCOL);
	}
	
	/**
	 * Saves Web Service protocol in session
	 * @param session
	 * @param protocol
	 */
	protected void setSessionProtocol(final String protocol, final HttpSession session) {
		session.setAttribute(ContratarDadosAvulsosConstants.SESSION_PROTOCOL, protocol);
	}
	
	/**
	 * Saves Web Service protocol in session
	 * @param session
	 * @param protocol
	 */
	protected void setSessionProtocol(final String protocol, final HttpServletRequest request) {
		setSessionProtocol(protocol, request.getSession());
	}
	
	/**
	 * Retrieves Web Service packages in session
	 * @param session
	 * @return List<PacoteVO> session packages
	 */
	@SuppressWarnings("unchecked")
	protected List<PacoteVO> getSessionPackages(final HttpSession session, final String origem) {
		List<PacoteVO> pacotes = new ArrayList<PacoteVO>();
		
		if(ORIGEM_MOBILE.equals(origem)){
			if(session.getAttribute(SESSION_PACKAGES_MOBILE) != null){
				pacotes.addAll((List<PacoteVO>) session.getAttribute(SESSION_PACKAGES_MOBILE));
			}
		}else if(ORIGEM_REDIRECT.equals(origem)){
			if(session.getAttribute(SESSION_PACKAGES_REDIRECT) != null){
				pacotes.addAll((List<PacoteVO>) session.getAttribute(SESSION_PACKAGES_REDIRECT));
			}
		}
		
		return pacotes;
	}
	
	/**
	 * Retrieves Web Service packages in session
	 * @param request
	 * @return List<PacoteVO> session packages
	 */
	protected List<PacoteVO> getSessionPackages(final HttpServletRequest request, final String origem) {
		return getSessionPackages(request.getSession(), origem);
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	protected UserInfo getDefaultUserInfo(final HttpServletRequest request){
		UserInfo userInfoVO = new UserInfo();
		userInfoVO.setCpfUsuario(StringUtils.EMPTY);
		userInfoVO.setIdUsuario(StringUtils.EMPTY);
		userInfoVO.setIpUsuario(request.getRemoteAddr());
		userInfoVO.setProtocolo(StringUtils.EMPTY);
		userInfoVO.setTipoUsuario(StringUtils.EMPTY);
		return userInfoVO;
	}
	
	/*protected String getFormattedIPAddress(final String ipAddress, final int port){
		StringBuffer buffer = new StringBuffer(ContratarDadosAvulsosConstants.LEFT_BRACKET);
		buffer.append(ipAddress).append(ContratarDadosAvulsosConstants.RIGHT_BRACKET);
		buffer.append(ContratarDadosAvulsosConstants.COLON).append(port);
		return buffer.toString();
	}*/

	protected String getFormattedIPAddress(final HttpServletRequest request){
		final HttpSession session = request.getSession();
		String ipAddress = getIP(session);
		String port = getPort(session);
		
		if(StringUtils.equals(ipAddress,StringUtils.EMPTY) || ipAddress == null){
			ipAddress = request.getHeader(ContratarDadosAvulsosConstants.IP);
		}
		
		if(StringUtils.equals(port,StringUtils.EMPTY) || port == null){
			port = request.getHeader(ContratarDadosAvulsosConstants.PORT);
		}
		
		StringBuffer buffer = new StringBuffer(ContratarDadosAvulsosConstants.LEFT_BRACKET);
		buffer.append(ipAddress).append(ContratarDadosAvulsosConstants.RIGHT_BRACKET);
		buffer.append(ContratarDadosAvulsosConstants.COLON).append(port);
		return buffer.toString();
	}
	
	
	/**
	 * LOGGER INFO
	 * @param LOGGER
	 * @param str
	 */
	protected static void info(final String str) {
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(str);
		}
	}

	/**
	 * LOGGER INFO
	 * @param LOGGER
	 * @param str
	 * @param obj
	 */
	protected static void info(final String str, final Object obj) {
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(str, obj);
		}
	}
	
	/**
	 * LOGGER DEBUG
	 * @param LOGGER
	 * @param str
	 */
	protected static void debug(final String str) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(str);
		}
	}

	/**
	 * LOGGER DEBUG
	 * @param LOGGER
	 * @param str
	 * @param obj
	 */
	protected static void debug(final String str, final Object obj) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(str, obj);
		}
	}
	
	public ContratarDadosAvulsosBO getBo() {
		return bo;
	}

	public void setBo(ContratarDadosAvulsosBO bo) {
		this.bo = bo;
	}

	public Configuration getPropPortal() {
		return propPortal;
	}

	public void setPropPortal(Configuration propPortal) {
		this.propPortal = propPortal;
	}

}
