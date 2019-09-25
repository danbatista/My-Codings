package usto.re.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BodyMail {
	
	private String id;
	private Object msg;
	private String host;
    private String label;
    private String passCert;
    private String account;
    private String passwd;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	

}
