package usto.re.smime.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DataMsg {

	private String subject;
	private String content;
	private String to;
	private String from;
	private String signer;
	private Boolean validSigner;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSigner() {
		return signer;
	}

	public void setSigner(String signer) {
		this.signer = signer;
	}

	public Boolean getValidSigner() {
		return validSigner;
	}

	public void setValidSigner(Boolean validSigner) {
		this.validSigner = validSigner;
	}
}
