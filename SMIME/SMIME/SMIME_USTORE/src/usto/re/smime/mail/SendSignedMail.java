package usto.re.smime.mail;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.smime.SMIMECapabilitiesAttribute;
import org.bouncycastle.asn1.smime.SMIMECapability;
import org.bouncycastle.asn1.smime.SMIMECapabilityVector;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMEException;
import org.bouncycastle.mail.smime.SMIMESignedGenerator;

public class SendSignedMail {

	public static MimeMessage sendSignedMail(MimeMessage body, String pathToCert, String pwdCert) {
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
			Enumeration elem = keystore.aliases();
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

			//ReadSignedMail.readSignedMail(signedMessage);
			// Transport.send(encryptedMessage);
			return signedMessage;
		} catch (SMIMEException ex) {
			ex.getUnderlyingException().printStackTrace(System.err);
			ex.printStackTrace(System.err);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			return null;
		}

	}


}
