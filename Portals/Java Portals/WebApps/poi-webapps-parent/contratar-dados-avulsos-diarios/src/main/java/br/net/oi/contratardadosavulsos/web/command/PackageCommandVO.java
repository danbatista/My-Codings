package br.net.oi.contratardadosavulsos.web.command;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PackageCommandVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idOferta;
	private String idCampanha;
	private String descricao;
	private String plan;
	private String price;
	private String decimal;
	private String dataCorte;

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the idOferta
	 */
	public String getIdOferta() {
		return idOferta;
	}

	/**
	 * @param idOferta
	 *            the idOferta to set
	 */
	public void setIdOferta(final String idOferta) {
		this.idOferta = idOferta;
	}

	/**
	 * @return the idCampanha
	 */
	public String getIdCampanha() {
		return idCampanha;
	}

	/**
	 * @param idCampanha
	 *            the idCampanha to set
	 */
	public void setIdCampanha(final String idCampanha) {
		this.idCampanha = idCampanha;
	}

	/**
	 * @return the plan
	 */
	public String getPlan() {
		return plan;
	}

	/**
	 * @param plan
	 *            the plan to set
	 */
	public void setPlan(final String plan) {
		this.plan = plan;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(final String price) {
		this.price = price;
	}

	/**
	 * @return the decimal
	 */
	public String getDecimal() {
		return decimal;
	}

	/**
	 * @param decimal
	 *            the decimal to set
	 */
	public void setDecimal(final String decimal) {
		this.decimal = decimal;
	}

	/**
	 * @return the dataCorte
	 */
	public String getDataCorte() {
		return dataCorte;
	}

	/**
	 * @param dataCorte the dataCorte to set
	 */
	public void setDataCorte(final String dataCorte) {
		this.dataCorte = dataCorte;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
