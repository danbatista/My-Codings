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
 * Command class for Ler Mensagens
 * @author reuben.e.d.tunguia
 *
 */
public class LerMensagensCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String lerSituacao;
	
	private String lerPrazo;

	/**
	 * @return the lerSituacao
	 */
	public String getLerSituacao() {
		return lerSituacao;
	}

	/**
	 * @param lerSituacao the lerSituacao to set
	 */
	public void setLerSituacao(String lerSituacao) {
		this.lerSituacao = lerSituacao;
	}

	/**
	 * @return the lerPrazo
	 */
	public String getLerPrazo() {
		return lerPrazo;
	}

	/**
	 * @param lerPrazo the lerPrazo to set
	 */
	public void setLerPrazo(String lerPrazo) {
		this.lerPrazo = lerPrazo;
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
