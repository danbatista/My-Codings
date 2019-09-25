package br.net.oi.intranet.gerenciadormensagens.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.net.oi.intranet.gerenciadormensagens.web.command.TreinamentoCommand;
import br.net.oi.poi.treinamento.business.dao.TreinamentoDAO;
import br.net.oi.poi.treinamento.business.vo.TreinamentoVO;

/**
 * @author nino.r.montillano
 * @edited Daniel B.
 */
@Controller
public class GerenciardorMensagensController {

	@Autowired
	public TreinamentoDAO bo;

	@RequestMapping(value = "/")
	public ModelAndView index() {
		final ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}

	/**
	 * Logger instance
	 */
	private static final Logger logger = LoggerFactory.getLogger(GerenciardorMensagensController.class);

	/**
	 * This method will render the initial view of Gerenciar Mensagem
	 * 
	 * @return ModelAndView
	 */

	@ModelAttribute(value = "TreinamentoCommand")
	public TreinamentoCommand getTreinamentoCommandObject() {
		return new TreinamentoCommand();
	}

	@RequestMapping(value = "home")
	public ModelAndView render() {
		logger.info("GerenciardorMensagensController.render() : START");
		ModelAndView modelAndView = new ModelAndView("view");

		logger.info("GerenciardorMensagensController.render() : END");
		return modelAndView;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(@ModelAttribute("treinamentoCommand") @Valid TreinamentoCommand CMD, BindingResult result) {

		if (result.hasErrors()) {
			return "view";
		} else {

			TreinamentoVO vo = new TreinamentoVO();
			vo.setNome(CMD.getNome());
			vo.setTelefone(CMD.getTelefone());
			bo.insert(vo);

			return "InsertSuccess";

		}
	}

	/**
	 * This method will render the list contacts of Gerenciar Mensagem
	 * 
	 * @return ModelAndView
	 */
	
	@RequestMapping(value = "list")
	public ModelAndView list(){
	 ModelAndView modelAndView = new ModelAndView("lista");
		try {
		 modelAndView.addObject("listaNome",bo.listAll());
		}catch(Exception e){
			logger.error("",e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "update")
		public ModelAndView update(@RequestParam(value="id") int id){
		
			ModelAndView  modelAndView = new ModelAndView("viewAtualiza");
			TreinamentoVO vo = new TreinamentoVO();
			vo.setId(id);
	  return modelAndView.addObject("contato",bo.getListById(vo.getId()));
			
		
		}
	
	@RequestMapping(value = "/atualiza", method = RequestMethod.POST)
	public String Atualiza(@ModelAttribute("treinamentoCommand") @Valid TreinamentoCommand CMD, BindingResult result) {

		if (result.hasErrors()) {
			return "viewAtualiza";
		} else {

			TreinamentoVO vo = new TreinamentoVO();
			vo.setId(CMD.getId());
			vo.setNome(CMD.getNome());
			vo.setTelefone(CMD.getTelefone());
			bo.update(vo);

			return "lista";

		}
	}
}
	
	

