/**
 * 
 */
package br.net.oi.contratardadosavulsos.bo;

import java.rmi.RemoteException;
import java.util.List;

import br.net.oi.contratardadosavulsos.model.exception.GenericException;
import br.net.oi.contratardadosavulsos.model.vo.PacoteVO;

/**
 * Business interface for contracting 3G Internet packages
 * @author mark.gary.m.lalap
 */
public interface ContratarDadosAvulsosBO {
	
	/**
	 * Consult AberturaProtocoloUNIPRO service for generation of unique protocol
	 * @param msisdn
	 * @return protocol
	 */
	String generateProtocol(final String msisdn);
	
	/**
	 * Consult VerificarElegibilidadeMSISDN service for eleigibility verification of terminal
	 * @param msisdn
	 * @param origem
	 * @return list of packages if eligible
	 * @throws Exception 
	 */
	List<PacoteVO> verifyElegibility(final String msisdn, final String origem) throws Exception;
	
	/**
	 * Consult ContratarPacotes3G service for contracting of package
	 * @param msisdn
	 * @param origem
	 * @param pacote
	 * @param protocoloPai
	 * @return protocol
	 * @throws GenericException 
	 * @throws RemoteException 
	 * @throws Exception 
	 */
	String contract(final String msisdn, final String origem, final PacoteVO pacote, final String protocoloPai) throws GenericException, RemoteException;
	
	public String verificaNumeroTerminal(String msisdn);
}
