package usto.re.smime.pojo;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity(name="arquivo")
public class Arquivo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
 
	@Column(name="email")	
	private String  email;
 
	@Lob
	@Column(name="chave_publica")
	private byte[] chavePublica;

	public Arquivo(Integer id, String email, byte[] chavePublica) {
		super();
		this.id = id;
		this.email = email;
		this.chavePublica = chavePublica;
	}
	
	public Arquivo(String email, byte[] chavePublica) {
		super();
		this.email = email;
		this.chavePublica = chavePublica;
	}

	public Arquivo(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getChavePublica() {
		return chavePublica;
	}

	public void setChavePublica(byte[] chavePublica) {
		this.chavePublica = chavePublica;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(chavePublica);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arquivo other = (Arquivo) obj;
		if (!Arrays.equals(chavePublica, other.chavePublica))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Arquivo [id=" + id + ", email=" + email + ", chavePublica=" + Arrays.toString(chavePublica) + "]";
	}
	
	
}
