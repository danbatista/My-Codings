/**
 * 
 */
package br.net.oi.contratardadosavulsos.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import br.net.oi.contratardadosavulsos.constants.ContratarDadosAvulsosConstants;
import br.net.oi.contratardadosavulsos.model.vo.PacoteVO;

/**
 * Utility class for Contratar Dados Avulsos
 * @author mark.gary.m.lalap
 */
public final class ContratarDadosAvulsosUtil {
	
	private final static DateFormat DF = new SimpleDateFormat(ContratarDadosAvulsosConstants.DATE_FORMAT);
	private final static DateFormat TF = new SimpleDateFormat(ContratarDadosAvulsosConstants.TIME_FORMAT);
	
	/**
	 * Static utility class
	 */
	private ContratarDadosAvulsosUtil() {
	}
	
	/**
	 * Returns the terminal in format (DDD) numero terminal
	 * e. g. (22) 9622-0202 
	 * @param msisdn
	 * @return String - formatted terminal
	 */
	public static String formatMSISDN(final String msisdn) {
		final StringBuffer formattedTelefone = new StringBuffer();
		int index = ContratarDadosAvulsosConstants.INDEX_6;
		
		if(StringUtils.isNotBlank(msisdn) && StringUtils.isNumeric(msisdn)) {
			final String ddd = msisdn.substring(ContratarDadosAvulsosConstants.INDEX_0, 
						ContratarDadosAvulsosConstants.INDEX_DDD);
			if(StringUtils.length(msisdn) == ContratarDadosAvulsosConstants.LENGTH_11) {
				index = ContratarDadosAvulsosConstants.INDEX_7;
			}
			
			final String firstCut = msisdn.substring(ContratarDadosAvulsosConstants.INDEX_DDD, index);
			final String lastCut = msisdn.substring(index);
			
			formattedTelefone.append(ContratarDadosAvulsosConstants.OPENING_PARENTHESIS)
				.append(ddd).append(ContratarDadosAvulsosConstants.CLOSING_PARENTHESIS)
				.append(ContratarDadosAvulsosConstants.WHITESPACE).append(firstCut)
				.append(ContratarDadosAvulsosConstants.DASH).append(lastCut);
		}

		return formattedTelefone.toString();
	}
	
	/**
	 * Removes the parenthesis and hyphen in the terminal number
	 * @param msisdn
	 * @return String - unformatted msisdn
	 */
	public static String unformatMSISDN(String msisdn){
		if(StringUtils.isNotBlank(msisdn)){
			msisdn.replaceAll(ContratarDadosAvulsosConstants.WHITESPACE, StringUtils.EMPTY)
			.replaceAll(ContratarDadosAvulsosConstants.OPENING_PARENTHESIS, StringUtils.EMPTY)
			.replaceAll(ContratarDadosAvulsosConstants.CLOSING_PARENTHESIS, StringUtils.EMPTY)
			.replaceAll(ContratarDadosAvulsosConstants.DASH, StringUtils.EMPTY);
		}
		return msisdn;
	}
	
	/**
	 * Returns the DDD from MSISDN
	 * @param msisdn
	 * @return
	 */
	public static String getDDD(final String msisdn) {
		return msisdn.substring(ContratarDadosAvulsosConstants.INDEX_0, ContratarDadosAvulsosConstants.INDEX_DDD);
	}
	
	/**
	 * Gets selected package from the list
	 * @param packageList
	 * @param pId
	 * @return selectedPackage
	 */
	public static PacoteVO getSelected(final List<PacoteVO> packageList, final String pId) {
		PacoteVO selectedPackage = null;
		if(!CollectionUtils.isEmpty(packageList)) {
			for(final PacoteVO p : packageList) {
				if(p.getIdOferta().equalsIgnoreCase(pId)) {
					selectedPackage = p;
					break;
				}
			}
		}
		return selectedPackage;
	}
	
	/**
	 * Returns current calendar date
	 * @return
	 */
	public static String getCurrentDate() {
		return DF.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * Returns current calendar time
	 * @return
	 */
	public static String getCurrentTime() {
		return TF.format(Calendar.getInstance().getTime());
	}
	
}
