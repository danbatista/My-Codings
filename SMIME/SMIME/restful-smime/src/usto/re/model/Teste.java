package usto.re.model;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import usto.re.smime.utils.ConvertCertificates;

public class Teste {
	private static List<String> array = new ArrayList<String>();

	public static void main(String[] args) throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException, NoSuchProviderException, UnrecoverableKeyException {
    
	  ConvertCertificates conv = new ConvertCertificates();
	  conv.decryptCert("/home/daniel/Documentos/meucertificado.p12", "qwe123");
	  try {
		  System.out.println(conv.getPubKey().getEncoded());
		System.out.println(loadPublicKey(conv.getPubKey().getEncoded().toString().getBytes()));
	} catch (GeneralSecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	
	  private static PublicKey loadPublicKey(byte[] c) throws GeneralSecurityException {
			
		    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(c);
		   PublicKey pub = null;
		   KeyFactory key = KeyFactory.getInstance("RSA");
		    pub = key.generatePublic(x509KeySpec);
		    return pub;
	}
	  
}
