package usto.re.model;

import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ComposeMSG {

	  private String id;
	  private List<String> listebdrive;
	  private MimeMessage msg;
	  private String getFrom;
	  private String content;
	  private String to;
	  private String subject;
	  private List<String> file;
	  private String host;
	  private String sessiondrive;
      private String authdrive;
      private String label;
      private String passCert;
      private List<String> labelAttachments;

	  public ComposeMSG() {
		// TODO Auto-generated constructor stub
	}
	  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getGetFrom() {
		return getFrom;
	}
	public void setGetFrom(String getFrom) {
		this.getFrom = getFrom;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getSessiondrive() {
		return sessiondrive;
	}

	public void setSessiondrive(String sessiondrive) {
		this.sessiondrive = sessiondrive;
	}

	public String getAuthdrive() {
		return authdrive;
	}

	public void setAuthdrive(String authdrive) {
		this.authdrive = authdrive;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	public String getPassCert() {
		return passCert;
	}

	public void setPassCert(String passCert) {
		this.passCert = passCert;
	}

	public List<String> getFile() {
		return file;
	}

	public void setFile(List<String> file) {
		this.file = file;
	}

	public List<String> getLabelAttachments() {
		return labelAttachments;
	}

	public void setLabelAttachments(List<String> labelAttachments) {
		this.labelAttachments = labelAttachments;
	}

	public MimeMessage getMsg() {
		return msg;
	}

	public void setMsg(MimeMessage msg) {
		this.msg = msg;
	}

	public List<String> getListebdrive() {
		return listebdrive;
	}

	public void setListebdrive(List<String> listebdrive) {
		this.listebdrive = listebdrive;
	}

	


}
