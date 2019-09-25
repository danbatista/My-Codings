package usto.re.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpCookie;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
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
import usto.re.smime.mail.SendSignedMail;

@Path("/sign")
public class ServiceSigned {

	private List<String> arrayAnexos = new ArrayList<String>();
	private String ANEXOS = " ";

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_JSON)
	public ComposeMSG encrypt(ComposeMSG msg) throws AddressException, MessagingException, UnrecoverableKeyException,
			NoSuchAlgorithmException, CertificateException, FileNotFoundException, KeyStoreException,
			NoSuchProviderException, IOException, IllegalAccessException, InvocationTargetException {
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
		// cria a Multipart
		Multipart mp = new MimeMultipart();
		int cont1 = 0;
		if (!msg.getFile().isEmpty()) {
			for (String listFile : msg.getFile()) {
				// System.out.println("Só entrou um vez aqui");
				// System.out.println("Adicionando arquivos ao E-mail:");
				System.out.println(listFile);
				System.out.println("Listando Aqui:" + msg.getLabelAttachments().get(cont1));
				// cria a segunda parte da mensage
				MimeBodyPart mbp2 = new MimeBodyPart();
				// anexa o arquivo na mensagem se tiver
				FileDataSource fds;
				try {
					fds = new FileDataSource(processFile(listFile, msg.getSessiondrive(), msg.getAuthdrive(),
							msg.getLabelAttachments().get(cont1)));
					listFDS.add(fds);
					mbp2.setDataHandler(new DataHandler(fds));
					mbp2.setFileName(fds.getName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mp.addBodyPart(mbp2);
				cont1++;
			}
		}
		System.out.println(msg.getListebdrive().size());
		if (!msg.getListebdrive().isEmpty() && msg.getListebdrive() != null) {
			arrayAnexos.add("<span>");
			for (String listEb : msg.getListebdrive()) {
					arrayAnexos.add(listEb);
			}
			arrayAnexos.add("</span>");
			for (String string : arrayAnexos) {
				ANEXOS = ANEXOS + "\n" + string;
			}

		}
		
		// cria a primeira parte da mensagem
		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setText(msg.getContent()+ "\n" + ANEXOS);
				mp.addBodyPart(mbp1);
		// adiciona a Multipart na mensagem
		body.setContent(mp);
		body.saveChanges();

		// Adiciona a mensagem capturada no arquivo do DataMSG

		msg.setMsg(body);

		ComposeMSG message = new ComposeMSG();
		BeanUtils.copyProperties(message, msg);
		message.setMsg(SendSignedMail.sendSignedMail(msg.getMsg(), cert_path, msg.getPassCert()));
		System.out.println("Mensagem Assinada com sucesso!");
		// file.delete();
		// id 100 with error
		// id 000 with no error

		if (message.getMsg() == null) {
			message.setId("100");

			if (!msg.getFile().isEmpty()) {
				for (FileDataSource fileDataSource : listFDS) {
					System.out.println("Deletando:" + fileDataSource.getName());
					fileDataSource.getFile().delete();
				}
			}

			return message;
		} else {
			message.setId("000");

			Transport.send(message.getMsg());

			if (!msg.getFile().isEmpty()) {
				for (FileDataSource fileDataSource : listFDS) {
					System.out.println("Deletando:" + fileDataSource.getName());
					fileDataSource.getFile().delete();
				}
			}
			return message;
		}

		// return message;
	}

	public static File processFile(String url, String sessiondrive, String authdrive, String label) throws Exception {
		System.out.println("INIt.....");
		HttpCookie cookie2 = new HttpCookie("JSESSIONID", sessiondrive);
		HttpCookie cookie3 = new HttpCookie("ZM_AUTH_TOKEN", authdrive);
		HttpCookie cookie4 = new HttpCookie("ZM_TEST", "true");

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
		System.out.println("its OKAY so far");
		String temp = System.getProperty("java.io.tmpdir");
		String tempFinal = temp + "/" + label;
		
		File file = new File(tempFinal);
		

		if (!file.exists()) {
  java.nio.file.Path path = Files.write(Paths.get(tempFinal), b, StandardOpenOption.CREATE_NEW);
       file = new File(path.toString());
		}
		return file;
	};

}
