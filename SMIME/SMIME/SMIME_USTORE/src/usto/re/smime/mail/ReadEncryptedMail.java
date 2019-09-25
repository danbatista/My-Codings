package usto.re.smime.mail;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.RecipientId;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.mail.smime.SMIMEEnveloped;
import org.bouncycastle.mail.smime.SMIMEException;
import org.bouncycastle.mail.smime.SMIMEUtil;

import usto.re.smime.utils.ConvertCertificates;

public class ReadEncryptedMail {
/*
	public static MimeMessage decrypto(String path, String pwd, MimeMessage m) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, IOException, MessagingException, CMSException,
			UnrecoverableKeyException, SMIMEException {

		//
		// Open the key store
		//
		ConvertCertificates conv = new ConvertCertificates();
		 path = "/home/daniel/Documentos/meucertificado.p12";
		 pwd = "qwe123";
		conv.decryptCert(path, pwd);
		//RecipientId recId = new JceKeyTransRecipientId(conv.getCertificate());

		//
		// Get a Session object with the default properties.
		//
		String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = System.getProperties();


		// Get session
		Session session = Session.getDefaultInstance(props, null);
	
//		// session.setDebug(true);
		javax.mail.Store store = session.getStore("imaps");
		store.connect("10.0.1.163", "daniel@usto.re", "123456");
//
		Folder inbox = store.getFolder("Inbox");
		inbox.open(Folder.READ_WRITE);
//
		Message[] msg = inbox.getMessages();
	
		Part strippedMessage = null;
		MimeMessage decriptedMsg = new MimeMessage(session);
		
		for (Message message : msg) {

			if (message.isMimeType("application/pkcs7-mime;name=smime.p7m; smime-type=enveloped-data")) {

				try {
					SMIMEEnveloped env = new SMIMEEnveloped((MimeMessage)message);
					Collection<RecipientInformation> recipients = env.getRecipientInfos().getRecipients();
					Iterator<RecipientInformation> iter = recipients.iterator();
					while (iter.hasNext()) {
						RecipientInformation recipientInfo = iter.next();
						// System.out.println(info.getRID());
						RecipientId id = recipientInfo.getRID();
					// if (id.match(conv.getCertificate())) {
						// try {
						if(recipientInfo == null)return null;
						
						MimeBodyPart part = SMIMEUtil.toMimeBodyPart(recipientInfo.getContent(conv.getpKey(), "BC"));
						
						
						// strippedMessage contains the decrypted
						// message.
						strippedMessage = part;
						
						   Object msgContent = part.getContent();
					
						     if (msgContent instanceof Multipart) {

						         Multipart multipart = (Multipart) msgContent;

						      //   System.out.println("BodyPart MultiPartCount: "+multipart.getCount());

						         for (int j = 0; j < multipart.getCount(); j++) {

						          BodyPart bodyPart = multipart.getBodyPart(j);

						          String disposition = bodyPart.getDisposition();

						          if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) { 
						              //System.out.println("Mail have some attachment");

						              DataHandler handler = bodyPart.getDataHandler();
						             // System.out.println("file name : " + handler.getName());                                 
						            }else{
						            	
						            String texto = ReadEncryptedMail.getFinalContent(bodyPart);
						            System.out.println(texto);
						            
						            }
						     
						        }
						     }
						     else                
						      System.out.println("Error during reading the message");
					}
				
				} catch (CMSException e) {
					throw new MessagingException("Error during the decryption of the message", e);
				}

			} // end if
			else {
			//	System.out.println(message.getMessageNumber());
			}
		}
   return decriptedMsg;
	
	} // end method
*/
	public static String getFinalContent(Part p) throws MessagingException, IOException {

		String finalContents = "";
		if (p.getContent() instanceof String) {
			finalContents = (String) p.getContent();
		} else {
			Multipart mp = (Multipart) p.getContent();
			if (mp.getCount() > 0) {
				Part bp = mp.getBodyPart(0);
				try {
					finalContents = ReadEncryptedMail.dumpPart(bp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return finalContents.trim();
	}

	public static  String dumpPart(Part p) throws Exception {

		InputStream is = p.getInputStream();
		// If "is" is not already buffered, wrap a BufferedInputStream
		// around it.
		if (!(is instanceof BufferedInputStream)) {
			is = new BufferedInputStream(is);
		}
		return ReadEncryptedMail.getStringFromInputStream(is);
	}

	public static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}



}
