package usto.re.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpCookie;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.GeneralSecurityException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import usto.re.model.ComposeMSG;
import usto.re.model.GetPublicKeyBytes;
import usto.re.smime.mail.SendSignedAndEncryptedMail;
import usto.re.smime.utils.ConvertCertificates;

@Path("/encrypt")
public class ServiceEncryption {

	private List<String> arrayAnexos = new ArrayList<String>();
	private String ANEXOS = " ";

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String encrypt(ComposeMSG msg) throws AddressException, MessagingException, FileNotFoundException,
			IOException, IllegalAccessException, InvocationTargetException, GeneralSecurityException {
		System.out.println("The method has begin");
		// Create a list of FileDataSource do remove after this e-mail is sent

		List<FileDataSource> listFDS = new ArrayList<FileDataSource>();

		//////////////////////
		// ==============================CERTIFICADO===============================================================\\
		// System.out.println(msg.getPassCert());
		// System.out.println("Bytes" + msg.getCertByte());
		if (msg.getLabel() == null || msg.getPassCert() == null)
			return null;

		String cert_path = "";

		// Essa seção se inicia para tratamento da serialização do
		// Certificado
		// Comeca o download do arquivo
		System.out.println("its OKAY so far");
		String temp = System.getProperty("java.io.tmpdir");
		String tempFinal = temp + "/" + msg.getLabel();

		File file = new File(tempFinal);
		System.out.println(file.getAbsolutePath());

		if (!file.exists()) {
			cert_path = file.getAbsolutePath();
		} else {
			cert_path = tempFinal;
		}

		// ===================================END============================================================\\

		Properties props = System
				.getProperties();/*
									 * Create the message to sign and encrypt
									 */
		System.out.println("To connect : " + msg.getHost());
		props.setProperty("mail.smtp.host", msg.getHost());
		props.setProperty("mail.smtp.port", "25");

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);
		MimeMessage body = new MimeMessage(session);
		body.setFrom(new InternetAddress(msg.getGetFrom()));
		body.setRecipient(Message.RecipientType.TO, new InternetAddress(msg.getTo()));
		body.setSubject(msg.getSubject());
		int cont1 = 0;
		if (!msg.getFile().isEmpty()) {
			arrayAnexos.add("<span>");
			for (String listFile : msg.getFile()) {
				// System.out.println("Só entrou um vez aqui");
				// System.out.println("Adicionando arquivos ao E-mail:");
				System.out.println(listFile);
				if (!listFile.equals("<div></div>"))
					arrayAnexos.add(listFile);

				/*
				 * try { processFile(listFile, msg.getSessiondrive(),
				 * msg.getAuthdrive(), msg.getLabelAttachments().get(cont1),
				 * msg.getTo()); } catch (Exception e) { // TODO Auto-generated
				 * catch block e.printStackTrace(); } cont1++;
				 */
			}
			arrayAnexos.add("</span>");
			for (String string : arrayAnexos) {
				ANEXOS = ANEXOS + "\n" + string;
			}

		}
		System.out.println("StRING DE ANEXOS: " + ANEXOS);
		// cria a primeira parte da mensagem
		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setText(msg.getContent() + "\n" + ANEXOS);
		// cria a Multipart
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(mbp1);
		// adiciona a Multipart na mensagem
		body.setContent(mp);
		body.saveChanges();

		// Adiciona a mensagem capturada no arquivo do DataMSG

		msg.setMsg(body);

		// Get user publicKey

		PublicKey pubKey = GetPublicKeyBytes.getBytes(msg.getId(), msg.getTo());
		if (pubKey != null) {
			ComposeMSG message = new ComposeMSG();
			BeanUtils.copyProperties(message, msg);
			message.setMsg(
					SendSignedAndEncryptedMail.encryptMailMessage(msg.getMsg(), cert_path, msg.getPassCert(), pubKey));
			// file.delete();
			// id 100 with error
			// id 000 with no error

			if (message.getMsg() == null) {
				message.setId("101");

				// if (!msg.getFile().isEmpty()) {
				// for (FileDataSource fileDataSource : listFDS) {
				// System.out.println("Deletando:" + fileDataSource.getName());
				// fileDataSource.getFile().delete();
				// }
				// }

				return message.getId();
			} else {
				message.setId("100");
				Transport.send(message.getMsg());

				// if (!msg.getFile().isEmpty()) {
				// for (FileDataSource fileDataSource : listFDS) {
				// System.out.println("Deletando:" + fileDataSource.getName());
				// fileDataSource.getFile().delete();
				// }
				// }

				return message.getId();
			}
		} else {
			return "102";
		}
		// return message;
	}

	public static File processFile(String url, String sessiondrive, String authdrive, String label, String email)
			throws Exception {

		String arq = "/opt/zimbra/zimlets-deployed/zm_usto_re_smime/" + email + "/" + label;
		File file = new File(arq);
		System.out.println(file.getAbsolutePath());

		if (file.exists()) {
			return file;
		} else {

			HttpCookie cookie2 = new HttpCookie("JSESSIONID", sessiondrive);
			HttpCookie cookie3 = new HttpCookie("ZM_AUTH_TOKEN", authdrive);
			HttpCookie cookie4 = new HttpCookie("ZM_TEST", "true");
			System.out.println(url);
			System.out.println(sessiondrive);
			System.out.println(authdrive);
			System.out.println(label);
			SslContextFactory factory = new SslContextFactory(true);

			HttpClient client = new HttpClient(factory);
			client.start();
			Request request = client.newRequest(url);

			// request.cookie(cookie1);
			request = request.cookie(cookie2);
			request = request.cookie(cookie3);
			request = request.cookie(cookie4);
			request = request.method(HttpMethod.GET);
			ContentResponse contentResponse = null;
			contentResponse = request.send();
			byte[] b = contentResponse.getContent();

			// Comeca o download do arquivo
			System.out.println("Processando arquivo de anexos");
			// String temp = System.getProperty("java.io.tmpdir");
			// String tempFinal = temp + "/" + label;pwd
			String tempFinal = "/opt/zimbra/zimlets-deployed/zm_usto_re_smime/" + email;
			File fileNew = new File(tempFinal);
			fileNew.mkdir();
			java.nio.file.Path path = Files.write(Paths.get(tempFinal + "/" + label), b, StandardOpenOption.CREATE_NEW);
			System.out.println(path.toString());
			// file = new File(path.toString());
			return file;
		}
	};

}
