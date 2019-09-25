/**
 * 
 */
package br.net.oi.intranet.gerenciadormensagens.web.command;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Required;

/**
 * Command class for Incluir Mensagen
 * @author mark.gary.m.lalap
 */
public class IncluirCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String funcionalidadeSelect;
	private String situacaoSelect;
	private String tipoMensagemSelect;
	private String codigoTextbox;
	private String tipoInteracaoSelect;
	private String descricaoTextbox;
	private String chaveTextbox;
	private String posicaoTextbox;
	private String statusSelect;
	private String prazoSelect;
	private String mensagemText;
	private String meioSelect;
	private String codRetornoTextbox;
	private String crmSelect;
	private String orientacaoText;
	private String originalText;
	private String funcionalidade;
	private String idCanal;
	private String canal;
	/**
	 * @return the funcionalidadeSelect
	 */
	public String getFuncionalidadeSelect() {
		return funcionalidadeSelect;
	}
	/**
	 * @param funcionalidadeSelect the funcionalidadeSelect to set
	 */
	@Required
	public void setFuncionalidadeSelect(String funcionalidadeSelect) {
		this.funcionalidadeSelect = funcionalidadeSelect;
	}
	/**
	 * @return the situacaoSelect
	 */
	public String getSituacaoSelect() {
		return situacaoSelect;
	}
	/**
	 * @param situacaoSelect the situacaoSelect to set
	 */
	public void setSituacaoSelect(String situacaoSelect) {
		this.situacaoSelect = situacaoSelect;
	}
	/**
	 * @return the tipoMensagemSelect
	 */
	public String getTipoMensagemSelect() {
		return tipoMensagemSelect;
	}
	/**
	 * @param tipoMensagemSelect the tipoMensagemSelect to set
	 */
	@Required
	public void setTipoMensagemSelect(String tipoMensagemSelect) {
		this.tipoMensagemSelect = tipoMensagemSelect;
	}
	/**
	 * @return the codigoTextbox
	 */
	public String getCodigoTextbox() {
		return codigoTextbox;
	}
	/**
	 * @param codigoTextbox the codigoTextbox to set
	 */
	public void setCodigoTextbox(String codigoTextbox) {
		this.codigoTextbox = codigoTextbox;
	}
	/**
	 * @return the tipoInteracaoSelect
	 */
	public String getTipoInteracaoSelect() {
		return tipoInteracaoSelect;
	}
	/**
	 * @param tipoInteracaoSelect the tipoInteracaoSelect to set
	 */
	@Required
	public void setTipoInteracaoSelect(String tipoInteracaoSelect) {
		this.tipoInteracaoSelect = tipoInteracaoSelect;
	}
	/**
	 * @return the descricaoTextbox
	 */
	public String getDescricaoTextbox() {
		return descricaoTextbox;
	}
	/**
	 * @param descricaoTextbox the descricaoTextbox to set
	 */
	public void setDescricaoTextbox(String descricaoTextbox) {
		this.descricaoTextbox = descricaoTextbox;
	}
	/**
	 * @return the chaveTextbox
	 */
	public String getChaveTextbox() {
		return chaveTextbox;
	}
	/**
	 * @param chaveTextbox the chaveTextbox to set
	 */
	public void setChaveTextbox(String chaveTextbox) {
		this.chaveTextbox = chaveTextbox;
	}
	/**
	 * @return the posicaoTextbox
	 */
	public String getPosicaoTextbox() {
		return posicaoTextbox;
	}
	/**
	 * @param posicaoTextbox the posicaoTextbox to set
	 */
	public void setPosicaoTextbox(String posicaoTextbox) {
		this.posicaoTextbox = posicaoTextbox;
	}
	/**
	 * @return the statusSelect
	 */
	public String getStatusSelect() {
		return statusSelect;
	}
	/**
	 * @param statusSelect the statusSelect to set
	 */
	@Required
	public void setStatusSelect(String statusSelect) {
		this.statusSelect = statusSelect;
	}
	/**
	 * @return the prazoSelect
	 */
	public String getPrazoSelect() {
		return prazoSelect;
	}
	/**
	 * @param prazoSelect the prazoSelect to set
	 */
	public void setPrazoSelect(String prazoSelect) {
		this.prazoSelect = prazoSelect;
	}
	/**
	 * @return the mensagemText
	 */
	public String getMensagemText() {
		return mensagemText;
	}
	/**
	 * @param mensagemText the mensagemText to set
	 */
	@Required
	public void setMensagemText(String mensagemText) {
		this.mensagemText = mensagemText;
	}
	/**
	 * @return the meioSelect
	 */
	public String getMeioSelect() {
		return meioSelect;
	}
	/**
	 * @param meioSelect the meioSelect to set
	 */
	public void setMeioSelect(String meioSelect) {
		this.meioSelect = meioSelect;
	}
	/**
	 * @return the codRetornoTextbox
	 */
	public String getCodRetornoTextbox() {
		return codRetornoTextbox;
	}
	/**
	 * @param codRetornoTextbox the codRetornoTextbox to set
	 */
	public void setCodRetornoTextbox(String codRetornoTextbox) {
		this.codRetornoTextbox = codRetornoTextbox;
	}
	/**
	 * @return the crmSelect
	 */
	public String getCrmSelect() {
		return crmSelect;
	}
	/**
	 * @param crmSelect the crmSelect to set
	 */
	public void setCrmSelect(String crmSelect) {
		this.crmSelect = crmSelect;
	}
	/**
	 * @return the orientacaoText
	 */
	public String getOrientacaoText() {
		return orientacaoText;
	}
	/**
	 * @param orientacaoText the orientacaoText to set
	 */
	public void setOrientacaoText(String orientacaoText) {
		this.orientacaoText = orientacaoText;
	}
	/**
	 * @return the originalText
	 */
	public String getOriginalText() {
		return originalText;
	}
	/**
	 * @param originalText the originalText to set
	 */
	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}
	/**
	 * @return the funcionalidade
	 */
	public String getFuncionalidade() {
		return funcionalidade;
	}
	/**
	 * @param funcionalidade the funcionalidade to set
	 */
	public void setFuncionalidade(String funcionalidade) {
		this.funcionalidade = funcionalidade;
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
	
}
