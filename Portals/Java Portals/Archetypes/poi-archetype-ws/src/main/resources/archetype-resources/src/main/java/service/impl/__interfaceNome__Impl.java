#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.net.oi.poi.atend.auditoria.commons.interfaces.IntegracaoAuditavel;
import br.net.oi.poi.atend.auditoria.commons.vo.TratamentoOutputVO;
import br.net.oi.ws.common.client.ServiceClientAxis2;
import ${package}.service.${interfaceNome};

/**
 * @author douglas.mendonca@accenture.com
 *
 */
public class ${interfaceNome}Impl extends ServiceClientAxis2 implements ${interfaceNome}, IntegracaoAuditavel {
	
	private static final Logger log = LoggerFactory.getLogger(${interfaceNome}Impl.class);

	private static final Object RETORNO_SUCESSO = "0";
	
	//private ManterFiguracaoListaStub stub;
	
	/**
	 * Construtor default
	 */
	public ${interfaceNome}Impl(){
		super();
	}

	/**
	 * Construtor sendo utilizando pelo spring para injetar dependências
	 * 
	 * @param urlTarget Endpoint
	 * @param connectionTimeout Timeout de conexão
	 * @param readTimeout Timeout de leitura
	 * @param defaultMaxConnectionsPerHost Número máximo de conexão por HOST
	 * @param maxTotalConnections Número total de conexão
	 */
	public ${interfaceNome}Impl(String urlTarget, int connectionTimeout, int readTimeout, 
			int defaultMaxConnectionsPerHost, int maxTotalConnections){
		
		log.debug("HelloWorldService");
		log.debug("urlTarget: "+urlTarget);
		log.debug("connectionTimeout: "+connectionTimeout);
		log.debug("readTimeout: "+readTimeout);
		
		//TODO instanciar o stub de acordo com o exemplo abaixo
//		try {
//			stub = new ManterFiguracaoListaStub(urlTarget);
//			this.setHttpClient(stub, connectionTimeout, readTimeout, defaultMaxConnectionsPerHost, maxTotalConnections);
//		} catch (AxisFault e) {
//			throw new IllegalStateException("Nao foi possivel inicializar o stub ManterFiguracaoListaStub");
//		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean retornouComSucesso(String code) {
		boolean result = false;
		
		log.info("Avaliando o codigo de retorno {}", code);

		if (code != null && code.equals(RETORNO_SUCESSO)) {
			result = true;
			log.info("HelloWorldService retornou com SUCESSO");
		}else{
			log.info("HelloWorldService retornou com ERRO");
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public TratamentoOutputVO gerarTratamentoRetorno(String nomeMetodo,
			Object retornoNegocio) {
		
		//TODO implementar
		
		return null;
	}	

}
