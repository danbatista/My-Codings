package usto.re.smime.mail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.UIDFolder;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.crypto.OctetStreamData;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.RecipientId;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMEEnveloped;
import org.bouncycastle.mail.smime.SMIMEException;
import org.bouncycastle.mail.smime.SMIMESigned;
import org.bouncycastle.mail.smime.SMIMEUtil;
import org.bouncycastle.mail.smime.examples.ExampleUtils;
import org.bouncycastle.util.Store;
import usto.re.smime.data.DataMsg;
import usto.re.smime.utils.ConvertCertificates;

public class ReadSignedMail {

	private static DataMsg verify(SMIMESigned s) throws Exception {
		DataMsg dataSigner = new DataMsg();
		Store certs = s.getCertificates();

		Provider BC = new BouncyCastleProvider();
		SignerInformationStore signers = s.getSignerInfos();

		Collection c = signers.getSigners();
		Iterator it = c.iterator();

		//
		// check each signer
		//
		while (it.hasNext()) {
			SignerInformation signer = (SignerInformation) it.next();
			Collection certCollection = certs.getMatches(signer.getSID());
			Iterator certIt = certCollection.iterator();
			X509Certificate cert = new JcaX509CertificateConverter().setProvider(BC)
					.getCertificate((X509CertificateHolder) certIt.next());
			
			Principal subject = cert.getSubjectDN();
			String subjectArray[] = subject.toString().split(",");
			for (String sub : subjectArray) {
				String[] str = sub.trim().split("=");
				String value = str[1];
				if(value.indexOf("@") > 0){
					dataSigner.setSigner(value);
				}
			}
        System.out.println("NAME OF: " + dataSigner.getSigner());
			if (signer.verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider(BC).build(cert))) {
				System.out.println("signature verified");
				dataSigner.setValidSigner(true);
			} else {
				System.out.println("signature failed!");
				dataSigner.setValidSigner(false);
			}
		}
		return dataSigner;
	}

	public static DataMsg readSignedMail(MimeMessage msg, PrivateKey pkey, Session session)
			throws MessagingException, CMSException, IOException, SMIMEException, Exception {
		DataMsg data = new DataMsg();
		//
		// make sure this was a multipart/signed message - there should be
		// two parts as we have one part for the content that was signed and
		// one part for the actual signature.is

		//

		if (msg.isMimeType("multipart/signed")) {
			SMIMESigned s = new SMIMESigned((MimeMultipart) msg.getContent());
			data = verify(s);
			//
			// extract the content
			//
			MimeBodyPart content = s.getContent();

			System.out.println("Content1:");

			Object cont = content.getContent();

			if (cont instanceof String) {
				System.out.println((String) cont);
				data.setContent(cont.toString());
			} else if (cont instanceof Multipart) {
				Multipart mp = (Multipart) cont;
				int count = mp.getCount();
				for (int i = 0; i < count; i++) {
					BodyPart m = mp.getBodyPart(i);
					Object part = m.getContent();
					//data.setContent(ReadEncryptedMail.getFinalContent(m));
					// System.out.println("Part " + i);
					// System.out.println("---------------------------");

					if (part instanceof String) {
						data.setContent((String)part);
						System.out.println("Conteúdo da Mensagem: " + (String) part);
					} else {
						System.out.println("can't print...");
					}
				}
			}

			return data;
			// IN cas of error application/pkcs7-mime;name=smime.p7m;
			// smime-type=enveloped-data
		} else if (msg.isMimeType("application/pkcs7-mime")) {
			try {
				SMIMEEnveloped env = new SMIMEEnveloped((MimeMessage) msg);
				Collection<RecipientInformation> recipients = env.getRecipientInfos().getRecipients();
				Iterator<RecipientInformation> iter = recipients.iterator();
				while (iter.hasNext()) {
					RecipientInformation recipientInfo = iter.next();
					// System.out.println(info.getRID());
					RecipientId id = recipientInfo.getRID();
					// if (id.match(conv.getCertificate())) {
					// try {
					if (recipientInfo == null)
						return null;

					MimeBodyPart part = SMIMEUtil.toMimeBodyPart(recipientInfo.getContent(pkey, "BC"));
					//ExampleUtils.dumpContent(part, "/home/daniel/content");
					Object msgContent = part.getContent();
                
					if (msgContent instanceof Multipart) {

						Multipart multipart = (Multipart) msgContent;
						SMIMESigned s = new SMIMESigned((MimeMultipart) multipart);
						// System.out.println("BodyPart MultiPartCount:
						// "+multipart.getCount());

						for (int j = 0; j < multipart.getCount(); j++) {

							BodyPart bodyPart = multipart.getBodyPart(j);

							String disposition = bodyPart.getDisposition();

							if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) {
								// System.out.println("Mail have some
								// attachment");

								DataHandler handler = bodyPart.getDataHandler();
								System.out.println("file name : " + handler.getName());

							} else {
								ByteArrayOutputStream out = new ByteArrayOutputStream();
								bodyPart.writeTo(out);

								data = verify(s);

								MimeMessage decryptedMessage = new MimeMessage(session,
										new ByteArrayInputStream(out.toByteArray()));
								data.setSubject(decryptedMessage.getSubject());
								data.setFrom(decryptedMessage.getFrom()[0].toString());
								data.setContent(ReadEncryptedMail.getFinalContent(bodyPart));

							}

						}
					} else
						System.out.println("Error during reading the message");
				}

			} catch (CMSException e) {
				throw new MessagingException("Error during the decryption of the message", e);
			}
			return data;
		} // end if
		else {
			System.out.println("Mensagem normal!");
		}
		return null;
	}

	public static void main(String[] args)
			throws 
			UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, FileNotFoundException,
			KeyStoreException, NoSuchProviderException, IOException, MessagingException {
		ConvertCertificates conv = new ConvertCertificates();
		conv.decryptCert("/home/daniel/Documentos/dbatista89@live.com.pfx", "Mypassqwe123");
		//System.out.println(conv.getCertificate());
       
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		props.setProperty("mail.imap.port", "993");
		Session session = Session.getDefaultInstance(props, null);
		//session.setDebug(true);
		javax.mail.Store store = session.getStore();
		store.connect("10.0.1.194","dbatista89@live.com", "123456");
		Folder inbox = store.getFolder("Inbox");
		inbox.open(Folder.READ_WRITE);
		UIDFolder folder = (UIDFolder) inbox;
		Long id = Long.parseLong("481");
		Message message = folder.getMessageByUID(id);
		DataMsg data = new DataMsg();

		try {
			data = ReadSignedMail.readSignedMail((MimeMessage) message, conv.getpKey(), session);
		} catch (CMSException e) {
			e.printStackTrace();
		} catch (SMIMEException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
