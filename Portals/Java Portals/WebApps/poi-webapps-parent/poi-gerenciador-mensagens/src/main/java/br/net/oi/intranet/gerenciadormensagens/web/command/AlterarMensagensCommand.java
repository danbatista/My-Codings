/**
 * 
 */
package br.net.oi.intranet.gerenciadormensagens.web.command;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Command class for Alterar Mensagens
 * @author reuben.e.d.tunguia
 */
public class AlterarMensagensCommand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String alterarNmfuncionalidade;
	
	private String alterarID;
	
	private String alterarSituacao;
	
	private String alterarPrazo;
	
	private String alterarIdCanal;
	
	private String alterarCanal;
	
	private String previousFuncionalidade;
	
	private String mensagemID;
	
	private String newMessageDescStatus;
	
	private String newMessageOrientacao;
	
	private String idCanal;
	
	private String canal;
	
	private String isOrientacao;

	/**
	 * @return the alterarNmfuncionalidade
	 */
	public String getAlterarNmfuncionalidade() {
		return alterarNmfuncionalidade;
	}

	/**
	 * @param alterarNmfuncionalidade the alterarNmfuncionalidade to set
	 */
	public void setAlterarNmfuncionalidade(String alterarNmfuncionalidade) {
		this.alterarNmfuncionalidade = alterarNmfuncionalidade;
	}

	/**
	 * @return the alterarID
	 */
	public String getAlterarID() {
		return alterarID;
	}

	/**
	 * @param alterarID the alterarID to set
	 */
	public void setAlterarID(String alterarID) {
		this.alterarID = alterarID;
	}

	/**
	 * @return the alterarSituacao
	 */
	public String getAlterarSituacao() {
		return alterarSituacao;
	}

	/**
	 * @param alterarSituacao the alterarSituacao to set
	 */
	public void setAlterarSituacao(String alterarSituacao) {
		this.alterarSituacao = alterarSituacao;
	}

	/**
	 * @return the alterarPrazo
	 */
	public String getAlterarPrazo() {
		return alterarPrazo;
	}

	/**
	 * @param alterarPrazo the alterarPrazo to set
	 */
	public void setAlterarPrazo(String alterarPrazo) {
		this.alterarPrazo = alterarPrazo;
	}

	/**
	 * @return the alterarIdCanal
	 */
	public String getAlterarIdCanal() {
		return alterarIdCanal;
	}

	/**
	 * @param alterarIdCanal the alterarIdCanal to set
	 */
	public void setAlterarIdCanal(String alterarIdCanal) {
		this.alterarIdCanal = alterarIdCanal;
	}

	/**
	 * @return the alterarCanal
	 */
	public String getAlterarCanal() {
		return alterarCanal;
	}

	/**
	 * @param alterarCanal the alterarCanal to set
	 */
	public void setAlterarCanal(String alterarCanal) {
		this.alterarCanal = alterarCanal;
	}

	/**
	 * @return the previousFuncionalidade
	 */
	public String getPreviousFuncionalidade() {
		return previousFuncionalidade;
	}

	/**
	 * @param previousFuncionalidade the previousFuncionalidade to set
	 */
	public void setPreviousFuncionalidade(String previousFuncionalidade) {
		this.previousFuncionalidade = previousFuncionalidade;
	}

	/**
	 * @return the mensagemID
	 */
	public String getMensagemID() {
		return mensagemID;
	}

	/**
	 * @param mensagemID the mensagemID to set
	 */
	public void setMensagemID(String mensagemID) {
		this.mensagemID = mensagemID;
	}

	/**
	 * @return the newMessageDescStatus
	 */
	public String getNewMessageDescStatus() {
		return newMessageDescStatus;
	}

	/**
	 * @param newMessageDescStatus the newMessageDescStatus to set
	 */
	public void setNewMessageDescStatus(String newMessageDescStatus) {
		this.newMessageDescStatus = newMessageDescStatus;
	}

	/**
	 * @return the newMessageOrientacao
	 */
	public String getNewMessageOrientacao() {
		return newMessageOrientacao;
	}

	/**
	 * @param newMessageOrientacao the newMessageOrientacao to set
	 */
	public void setNewMessageOrientacao(String newMessageOrientacao) {
		this.newMessageOrientacao = newMessageOrientacao;
	}

	/**
	 * @return the idCanal
	 */
	public String getIdCanal() {
		return idCanal;
	}

	/**
	 * @param idCanal the idCanal to set
	 */
	public void setIdCanal(String idCanal) {
		this.idCanal = idCanal;
	}

	/**
	 * @return the canal
	 */
	public String getCanal() {
		return canal;
	}

	/**
	 * @param canal the canal to set
	 */
	public void setCanal(String canal) {
		this.canal = canal;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * @return the isOrientacao
	 */
	public String getIsOrientacao() {
		return isOrientacao;
	}

	/**
	 * @param isOrientacao the isOrientacao to set
	 */
	public void setIsOrientacao(String isOrientacao) {
		this.isOrientacao = isOrientacao;
	}
	
}
