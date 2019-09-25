#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bo.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.net.oi.poi.atend.auditoria.commons.interfaces.NegociosAuditavel;
import br.net.oi.poi.atend.auditoria.commons.vo.TratamentoOutputVO;

import ${package}.bo.${interfaceNome};

/**
 * Classe contendo as regras de Negócio
 * 
 */
@Service("${interfaceNome}Impl")
public class ${interfaceNome}Impl implements ${interfaceNome}, NegociosAuditavel {

	private static final Logger log = LoggerFactory.getLogger(${interfaceNome}Impl.class);
	
	//TODO implementar

	/**
	 * {@inheritDoc}
	 */
	public TratamentoOutputVO gerarTratamentoRetorno(String nomeMetodo, Object retornoNegocio, List<TratamentoOutputVO> listaTratamentoIntegracao) {
		
		//TODO implementar
		
		return null;
	}
}