//package usto.re.smime.utils;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.security.InvalidKeyException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.SignatureException;
//import java.security.UnrecoverableKeyException;
//import java.security.cert.CertificateException;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Properties;
//
//import javax.mail.Folder;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//
//import org.apache.commons.io.IOUtils;
//import org.bouncycastle.cms.CMSException;
//import org.bouncycastle.cms.CMSProcessable;
//import org.bouncycastle.cms.CMSSignedData;
//import org.bouncycastle.cms.SignerInformation;
//import org.bouncycastle.cms.SignerInformationStore;
//import org.bouncycastle.util.Store;
//
//import com.sun.mail.util.BASE64DecoderStream;
//
//import oracle.security.crypto.core.AuthenticationException;
//
//public class MailDecryptRead {
//
//	public static Message decrypt(Message msg, String pathServer) throws IOException, CMSException, CertificateException,
//			InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException,
//			MessagingException, UnrecoverableKeyException, KeyStoreException, AuthenticationException {
//
//		BASE64DecoderStream base64DecoderStream = (BASE64DecoderStream) msg.getContent();
//		byte[] buffer = IOUtils.toByteArray(base64DecoderStream);
//
//		// Loading the file first
//		InputStream is = msg.getInputStream();
//		is.read();
//		is.close();
//
//		// DataInputStream in = new DataInputStream(is);
//		// in.readFully(buffer);
//		// in.close();
//
//		// Corresponding class of signed_data is CMSSignedData
//		CMSSignedData signature = new CMSSignedData(buffer);
//		Store cs = signature.getCertificates();
//		SignerInformationStore signers = signature.getSignerInfos();
//		Collection<?> c = signers.getSigners();
//		Iterator<?> it = c.iterator();
//		// the following array will contain the content of xml document
//		byte[] data = null;
//		while (it.hasNext()) {
//
//			SignerInformation signer = (SignerInformation) it.next();
//			Collection<?> certCollection = cs.getMatches(signer.getSID());
//			Iterator<?> certIt = certCollection.iterator();
//			CMSProcessable sc = signature.getSignedContent();
//			data = (byte[]) sc.getContent();
//
//		}
//		
//		Files.write(Paths.get(pathServer), data, StandardOpenOption.CREATE_NEW);
//		List<String> list = Files.readAllLines(Paths.get(pathServer), Charset.defaultCharset());
//		list.remove(0);
//		String finalContent = "";
//		for (String string : list) {
//			finalContent = finalContent + string + "\n";
//		}
//		Files.delete(Paths.get(pathServer));
//		System.out.println(finalContent);
//		msg.setText(finalContent);
//		msg.saveChanges();
//		
//
//		return msg;
//	}
//
//	public static void readMailEncrypted(String host, String username, String password) throws Exception {
//		String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
//
//		Properties props = System.getProperties();
//
//		props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
//		props.setProperty("mail.imap.socketFactory.fallback", "false");
//		props.setProperty("mail.imap.port", "993");
//		props.setProperty("mail.imap.socketFactory.port", "993");
//		props.setProperty("mail.imap.ssl.enable", "false");
//		props.setProperty("mail.imap.starttls.enable", "false");
//		props.setProperty("mail.imap.starttls.required", "false");
//
//		// Get session
//		Session session = Session.getDefaultInstance(props);
//		session.setDebug(true);
//		javax.mail.Store store = session.getStore("imaps");
//		store.connect(host, username, password);
//		System.out.println("Store complete");
//		// store.connect(username, password);
//		System.out.println(store.isConnected());
//		Folder inbox = store.getFolder("Inbox");
//		inbox.open(Folder.READ_WRITE);
//		Message[] msg = inbox.getMessages();
//
//		for (Message message : msg) {
//
//			if (message.isMimeType("application/pkcs7-mime") || message.isMimeType("application/x-pkcs7-mime")) {
//
//				//
//				// in this case the content is wrapped in the signature block.
//				//
//				//
//				// extract the content
//				//
//				// decrypt(null);
//				System.out.println("Assunto:" + message.getSubject());
//				ConvertCertificates conv = new ConvertCertificates();
//				conv.decryptCert("/home/daniel/Downloads/meucertificado.p12", "qwe123");
//
//				decrypt(message,"/home/daniel/alo.txt");
//				/*
//				 * DataSource source = new ByteArrayDataSource(new
//				 * ByteArrayInputStream(message.toString().getBytes()),
//				 * "application/pkcs7-mime");
//				 * 
//				 * MimeMultipart mime = new MimeMultipart(source);
//				 * 
//				 * ConvertCertificates conv = new ConvertCertificates();
//				 * conv.decryptCert("/home/daniel/Downloads/eudes@usto.re.p12",
//				 * "qwe123");
//				 * 
//				 * 
//				 * // MimeBodyPart mbp= new MimeBodyPart(mime); //
//				 * SmimeEnveloped env = new SmimeEnveloped(mime, AlgID.md5);
//				 * 
//				 * // mbp = env.getEnclosedBodyPart(conv.getpKey(), //
//				 * conv.getCertificate()); // Object cont = mbp.getContent();
//				 * 
//				 * BASE64DecoderStream base64DecoderStream =
//				 * (BASE64DecoderStream) message.getContent(); byte[] byteArray
//				 * = IOUtils.toByteArray(base64DecoderStream); String encoded =
//				 * DatatypeConverter.printBase64Binary(byteArray);
//				 * 
//				 * // byte[] decodeBase64 = Base64.decodeBase64(byteArray);
//				 * String decodeBase64 = new
//				 * String(DatatypeConverter.parseBase64Binary(encoded)); byte[]
//				 * teste = decodeBase64.getBytes("UTF-8"); String m = new
//				 * String(teste, "UTF-8");
//				 * 
//				 * System.out.println(m); java.nio.file.Path path =
//				 * Files.write(Paths.get("/home/daniel/opa.txt"),
//				 * m.getBytes(),StandardOpenOption.CREATE_NEW);
//				 * 
//				 * File file = new File(path.toString()); file.createNewFile();
//				 */
//
//			} else {
//				System.err.println("Not a signed message!");
//			}
//
//		}
//
//	}
//
//	public static void main(String args[]) throws Exception {
//
//		String host = "10.0.1.163";
//		final String username = "eudes@usto.re";
//		final String password = "123456";
//		readMailEncrypted(host, username, password);
//
//	}
//
//}