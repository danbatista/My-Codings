/**
 * 
 */
package br.net.oi.contratardadosavulsos.constants;

/**
 * Constants class for Contratar Dados Avulsos
 * 
 * @author mark.gary.m.lalap
 */
public interface ContratarDadosAvulsosConstants {

	/* Constants for JSP views */
	String VIEW_HOME = "home";
	String VIEW_CONFIRM = "confirm";
	String VIEW_SUCCESS = "success";
	String VIEW_ERROR_CONNECTION = "error/errorConnection";

	String VIEW_HOME_PAGE = "pacote/home";
	String VIEW_CONFIRM_PAGE = "pacote/confirm";
	String VIEW_SUCCESS_PAGE = "pacote/success";
	String VIEW_ERROR_CONNECTION_PAGE = "pacote/error/erro";
	String VIEW_REDUCE_PAGE = "/pacote/reduce";

	/* Constants for Request Mappings */
	String REQUEST_HOME = "/home";
	String REQUEST_CONFIRM = "/confirm";
	String REQUEST_CONTRACT = "/contract";
	String REQUEST_SUCCESS = "/success";
	String REQUEST_ERROR_CONNECTION = "/errorConnection";

	/* Constants for Request Mappings */
    String REQUEST_DADOS_HOME = "/home3g";
    String REQUEST_DADOS_CONFIRM = "/confirm3g";
    String REQUEST_DADOS_CONTRACT = "/contract3g";
    String REQUEST_DADOS_ERROR_CONNECTION = "/erro3g";
    String REQUEST_DADOS_REDUCE = "/reducao3g";


	/* Response Attributes */
	String ATTR_PACKAGES = "packages";
	String ATTR_SELECTED_PACKAGE = "selectedPackage";
	String ATTR_SELECTED_ID = "selectedId";
	String ATTR_SELECTED = "description";
	String ATTR_SELECTED_ID_CAMPANHA = "idCampanha";
	String ATTR_PLAN = "plan";
	String ATTR_PRICE = "price";
	String ATTR_DECIMAL = "decimal";
	String ATTR_DATA_CORTE = "corte";
	String ATTR_SELECTED_ID_OFERTA = "idOferta";
	String ATTR_MSISDN = "msisdn";
	String ATTR_DATE = "date";
	String ATTR_TIME = "time";
	String ATTR_PROTOCOL = "protocol";
	String ATTR_FLAG_ERROR_SELECT = "flagErrorSelect";
	String ATTR_TRATAMENTO_RETORNO = "tratamentoRetornoVO";
	String ATTR_PROTOCOLO = "protocolo";
	String ATTR_IP = "usuarioIP";

	/* Request Parameters */
	String PARAM_PACOTE = "pacote";

	/* Additional Elements */
	String CONTRATAR_BO = "ContratarDadosAvulsosBOImpl";
	String PORTAL_WEB = "PORTAL_WEB";
	String PROTOCOLO_OP = "PROCOLO_OP";
	String INTL_CODE = "55";
	String MSISDN = "MSISDN";
	String CHANNEL = "CHANNEL";
	String SESSION_ATTRIBUTES = "sessionMap";
	String SESSION_PACKAGES_MOBILE = "sessionPackagesMobile";
	String SESSION_PACKAGES_REDIRECT = "sessionPackagesRedirect";
	String SESSION_PROTOCOL = "sessionProtocol";
	String OPENING_PARENTHESIS = "(";
	String CLOSING_PARENTHESIS = ")";
	String WHITESPACE = " ";
	String DASH = "-";
	String COMMA = ",";
	String DATE_FORMAT = "dd/MM/yyyy";
	String TIME_FORMAT = "HH:mm";
	String COD_POSTULACAO_OUTROS = "2";
	String POR = "por";
	String REAIS = "R$";
	int LENGTH_10 = 10;
	int LENGTH_11 = 11;
	int INDEX_0 = 0;
	int INDEX_DDD = 2;
	int INDEX_6 = 6;
	int INDEX_7 = 7;
	
	String ORIGEM_MOBILE = "mobile";
	String ORIGEM_REDIRECT = "redirect";

	/* Portal Parameters */
	String PORTAL_CONFIGURATION = "configurationPortal";
	String PROP_ID_FUNCIONALIDADE = "contratar-dados-avulsos.ID_FUNCIONALIDADE";
	String PROP_ID_FUNCIONALIDADE_REDIRECT = "contratar-pacotes-dados.ID_FUNCIONALIDADE";
	String PROP_CONTRATAR_ID_OPERACAO = "contratar-dados-avulsos.CONTRATAR_ID_OPERACAO";
	String PROP_CONTRATAR_PACOTES_ID_OPERACAO = "contratar-pacotes-dados.CONTRATAR_ID_OPERACAO";
	String PROP_VERIFICAR_ID_OPERACAO = "contratar-dados-avulsos.VERIFICAR_ID_OPERACAO";
	String PROP_VERIFICAR_PACOTES_ID_OPERACAO = "contratar-pacotes-dados.VERIFICAR_ID_OPERACAO";
	String PROP_CONFIRMAR_ID_OPERACAO = "contratar-dados-avulsos.CONFIRMAR_ID_OPERACAO";
	String PROP_CONFIRMAR_PACOTES_ID_OPERACAO = "contratar-pacotes-dados.CONFIRMAR_ID_OPERACAO";
	String PROP_ENDPOINT = "contratar-dados-avulsos.END_POINT";
	String PROP_TIMEOUT = "contratar-dados-avulsos.TIME_OUT";
	String PROP_CONTRATAR_CODIGO_SUCESSO = "contratar-dados-avulsos.contratar.CODIGO_SUCESSO";
	String PROP_CONSULTAR_CODIGO_SUCESSO = "contratar-dados-avulsos.consultar.CODIGO_SUCESSO";
	String PROP_MENSAGEM_SUCESSO = "contratar-dados-avulsos.MENSAGEM_SUCESSO";
	String PROP_VERIFICAR_ID = "contratar-dados-avulsos.verifyElegibility_id";
	String PROP_CONTRACT_ID = "contratar-dados-avulsos.contract_id";
	String PROP_GERAR_ID = "contratar-dados-avulsos.gerarprotocolo_id";
	String PROP_ASSOCIAR_ID = "contratar-dados-avulsos.associarprotocolo_id";
	String PROP_CHANNEL = "contratar-dados-avulsos.channel";
	String PROP_ORIGEM = "contratar-pacote-dados.origem";
	
	String LEFT_BRACKET = "[";
	String RIGHT_BRACKET = "]";
	String COLON = ":";
	String IP = "X-FORWARDED-FOR";
	String PORT = "X-FORWARDED-PORT";
	
}
