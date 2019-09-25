package usto.re.smime.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.mail.Provider;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;



/*
 * By Daniel Batista
 * 
 *  25/10/2016
 *  daniel@usto.re
 */
public class ConvertCertificates {

	private X509Certificate certificate;
	private PrivateKey pKey;
	private PublicKey pubKey;
	private Provider prov;

	public X509Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}

	public PrivateKey getpKey() {
		return pKey;
	}

	public void setpKey(PrivateKey pKey) {
		this.pKey = pKey;
	}

	public ConvertCertificates() {
	}

	public PublicKey getPubKey() {
		return pubKey;
	}

	public void setPubKey(PublicKey pubKey) {
		this.pubKey = pubKey;
	}

	public Provider getProv() {
		return prov;
	}

	public void setProv(Provider prov) {
		this.prov = prov;
	}

	public X509Certificate decryptCert(String pfxFilename, String pfxPassword) throws NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, IOException, KeyStoreException, UnrecoverableKeyException, NoSuchProviderException {
		/* Add BC */
	
		Security.addProvider(new BouncyCastleProvider());
		
		KeyStore p12 =KeyStore.getInstance("PKCS12");
		p12.load(new FileInputStream(pfxFilename), pfxPassword.toCharArray());
		Enumeration elem = p12.aliases();
		X509Certificate c = null;
		String alias = "";
		while (elem.hasMoreElements()) {
			alias = (String) elem.nextElement();
		//	System.out.println("alias:" + alias);
			//System.out.println("    >>>   ");
			c = (X509Certificate) p12.getCertificate(alias);
			Principal subject = c.getSubjectDN();
			
			String subjectArray[] = subject.toString().split(",");
			for (String s : subjectArray) {
				String[] str = s.trim().split("=");
				String key = str[0];
				String value = str[1];
			 //System.out.println(key + " - " + value);
			}
		}
		setPubKey(c.getPublicKey());
		PrivateKey pkey = (PrivateKey) p12.getKey(alias, pfxPassword.toCharArray());
		setCertificate(c);
		setpKey(pkey);
      return c;
	}
	
	public X509Certificate decryptCertInpt(InputStream is, String pfxPassword) throws NoSuchAlgorithmException,
	CertificateException, FileNotFoundException, IOException, KeyStoreException, UnrecoverableKeyException, NoSuchProviderException {
/* Add BC */

Security.addProvider(new BouncyCastleProvider());

KeyStore p12 =KeyStore.getInstance("PKCS12");
p12.load(is, pfxPassword.toCharArray());
Enumeration elem = p12.aliases();
X509Certificate c = null;
String alias = "";
while (elem.hasMoreElements()) {
	alias = (String) elem.nextElement();
//	System.out.println("alias:" + alias);
	//System.out.println("    >>>   ");
	c = (X509Certificate) p12.getCertificate(alias);
	Principal subject = c.getSubjectDN();
	String subjectArray[] = subject.toString().split(",");
	for (String s : subjectArray) {
		String[] str = s.trim().split("=");
		String key = str[0];
		String value = str[1];
		//System.out.println(key + " - " + value);
	}
}
setPubKey(c.getPublicKey());
PrivateKey pkey = (PrivateKey) p12.getKey(alias, pfxPassword.toCharArray());
setCertificate(c);
setpKey(pkey);
return c;
}
	
	public static boolean returnEmail(X509Certificate cert, String account){
		Principal subject = cert.getSubjectDN();
		String subjectArray[] = subject.toString().split(",");
		boolean control = false;
		for (String s : subjectArray) {
			String[] str = s.trim().split("=");
			String value = str[1];
			System.out.println(account +"<=== + ===>"+value);
			if(account.equals(value)){
				control = true;
			}
		}
		return control;
		
	}
	
	public static void main(String[] args) throws UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, KeyStoreException, NoSuchProviderException, IOException {
		ConvertCertificates conv = new ConvertCertificates();
		conv.decryptCert("/home/daniel/Documentos/marina@usto.re.p12", "123456");
		String account = "marina@usto.re";
		if(returnEmail(conv.getCertificate(), account))System.out.println("DANIEL");; 
		//System.out.println(conv.getCertificate());
		
		
	}
}
