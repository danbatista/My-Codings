package usto.re.smime.mail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.smime.SMIMECapabilitiesAttribute;
import org.bouncycastle.asn1.smime.SMIMECapability;
import org.bouncycastle.asn1.smime.SMIMECapabilityVector;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMEEnvelopedGenerator;
import org.bouncycastle.mail.smime.SMIMEException;
import org.bouncycastle.mail.smime.SMIMESignedGenerator;
import org.bouncycastle.util.Strings;

import usto.re.smime.utils.ConvertCertificates;

/**
 * @daniel Esta classe destinasse a encriptação e assinatura de e-mail.
 */
public class SendSignedAndEncryptedMail {

	public static MimeMessage encryptMailMessage(MimeMessage body, String pathToCert, String pwdCert,
			PublicKey publicKey) {
		try {
			MailcapCommandMap mailcap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();

			mailcap.addMailcap(
					"application/pkcs7-signature;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.pkcs7_signature");
			mailcap.addMailcap(
					"application/pkcs7-mime;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.pkcs7_mime");
			mailcap.addMailcap(
					"application/x-pkcs7-signature;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.x_pkcs7_signature");
			mailcap.addMailcap(
					"application/x-pkcs7-mime;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.x_pkcs7_mime");
			mailcap.addMailcap(
					"multipart/signed;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.multipart_signed");

			CommandMap.setDefaultCommandMap(mailcap);

			/* Add BC */
			Security.addProvider(new BouncyCastleProvider());

			/* Open the keystore */
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			keystore.load(new FileInputStream(pathToCert), pwdCert.toCharArray());

			String alias = "";
			Enumeration<String> elem = keystore.aliases();
			X509Certificate c = null;
			while (elem.hasMoreElements()) {
				alias = (String) elem.nextElement();
				c = (X509Certificate) keystore.getCertificate(alias);
				Principal subject = c.getSubjectDN();
				String subjectArray[] = subject.toString().split(",");
				for (String s : subjectArray) {
					String[] str = s.trim().split("=");
					String key = str[0];
					String value = str[1];
					// System.out.println(key + " - " + value);
				}
			}

			Certificate[] chain = keystore.getCertificateChain(alias);
			/* Get the private key to sign the message with */

			PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, pwdCert.toCharArray());
			if (privateKey == null) {
				throw new Exception("cannot find private key for alias: " + alias);
			}

			/* Create the SMIMESignedGenerator */
			SMIMECapabilityVector capabilities = new SMIMECapabilityVector();
			capabilities.addCapability(SMIMECapability.dES_EDE3_CBC);
			capabilities.addCapability(SMIMECapability.rC2_CBC, 128);
			capabilities.addCapability(SMIMECapability.dES_CBC);
			String oid = "2.5.29.14";
			ASN1EncodableVector attributes = new ASN1EncodableVector();

			attributes.add(new SMIMECapabilitiesAttribute(capabilities));

			SMIMESignedGenerator signer = new SMIMESignedGenerator();
			signer.addSigner(privateKey,
					(X509Certificate) chain[0], "RSA".equals(privateKey.getAlgorithm())
							? SMIMESignedGenerator.DIGEST_SHA1 : SMIMESignedGenerator.DIGEST_MD5,
					new AttributeTable(attributes), null);

			List certList = new ArrayList();
			certList.add(chain[0]);
			// certList.add(conv.getCertificate());
			CertStore certs = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
			signer.addCertificatesAndCRLs(certs);

			/* Sign the message */
			Properties props = System.getProperties();
			Session session = Session.getDefaultInstance(props, null);
			MimeMultipart mm = signer.generate(body, "BC");
			MimeMessage signedMessage = new MimeMessage(session);

			/* Set all original MIME headers in the signed message */
			Enumeration headers = body.getAllHeaderLines();
			while (headers.hasMoreElements()) {
				signedMessage.addHeaderLine((String) headers.nextElement());
			}

			/* Set the content of the signed message */
			signedMessage.setContent(mm);
			signedMessage.saveChanges();

			/* Create the encrypter */

			SMIMEEnvelopedGenerator encrypter = new SMIMEEnvelopedGenerator();

			encrypter.addKeyTransRecipient(publicKey, oid.getBytes());

			/* Encrypt the message */
			MimeBodyPart encryptedPart = encrypter.generate(signedMessage, SMIMEEnvelopedGenerator.RC2_CBC, "BC");

			/*
			 * Create a new MimeMessage that contains the encrypted and signed
			 * content
			 */
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			encryptedPart.writeTo(out);

			MimeMessage encryptedMessage = new MimeMessage(session, new ByteArrayInputStream(out.toByteArray()));

			/* Set all original MIME headers in the encrypted message */
			headers = body.getAllHeaderLines();
			while (headers.hasMoreElements()) {
				String headerLine = (String) headers.nextElement();
				/*
				 * Make sure not to override any content-* headers from the
				 * original message
				 */
				if (!Strings.toLowerCase(headerLine).startsWith("content-")) {
					encryptedMessage.addHeaderLine(headerLine);
				}
			}
			// ReadEncryptedMail.decrypt(encryptedMessage);
			// Transport.send(encryptedMessage);
			return encryptedMessage;
		} catch (SMIMEException ex) {
			ex.getUnderlyingException().printStackTrace(System.err);
			ex.printStackTrace(System.err);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			return null;
		}

	}

	public static void main(String[] args)
			throws AddressException, MessagingException, UnrecoverableKeyException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, KeyStoreException, NoSuchProviderException, IOException {
		ConvertCertificates conv = new ConvertCertificates();
		String path = "/home/daniel/Downloads/meucertificado.p12";
		String pwd = "qwe123";
		conv.decryptCert(path, pwd);
		/* Create the message to sign and encrypt */
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "10.0.1.194");
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);
		MimeMessage body = new MimeMessage(session);
		body.setFrom(new InternetAddress("dbatista89@live.com"));
		body.setRecipient(Message.RecipientType.TO, new InternetAddress("daniel@usto.re"));
		body.setSubject("example signed message");
		body.setContent("Mensagem ultramegapower assinada", "text/plain");
		body.saveChanges();
		MimeMessage encrypted = 	
	encryptMailMessage(body, "/home/daniel/Downloads/dbatista89@live.com.pfx", "Mypassqwe123", conv.getPubKey());
	   Transport.send(encrypted);
	}

}