#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import javax.inject.Named;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import br.net.oi.poi.atend.auditoria.commons.interfaces.WebTratamentoErroAuditavel;
import br.net.oi.poi.atend.auditoria.commons.vo.TratamentoOutputVO;

/**
 * Controller responsavel por atender as requisicoes do portlet
 */

@Controller
@RequestMapping(value = "VIEW")
@Named("${nome_do_controller}")
public class ${nome_do_controller} implements WebTratamentoErroAuditavel{
	
	public static final Logger log = LoggerFactory.getLogger(${nome_do_controller}.class);
		
	/**
	 * Render 
	 *
	 * @param request objeto de request
	 * @param portletSession objeto de sessão
	 * @param response  RenderResponse
	 * @return ModelAndView ModelAndView
	 */
	@RenderMapping
	public ModelAndView exibirFormularioInicial(RenderRequest request, RenderResponse response, PortletSession portletSession){
		
		log.info("Excutando o metodo exibirFormularioInicial");
		
		ModelAndView mv = new ModelAndView("hello");
						
		return mv;

	}
	
	/**
	 * {@inheritDoc}
	 */
	 public TratamentoOutputVO gerarTratamentoRetorno(String nomeMetodo, PortletRequest request) {
		//TODO implementar
		return null;
	}
	
	
}
