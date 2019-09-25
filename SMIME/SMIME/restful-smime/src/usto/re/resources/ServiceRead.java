package usto.re.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.UIDFolder;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.mail.smime.SMIMEException;

import usto.re.model.BodyMail;
import usto.re.smime.data.DataMsg;
import usto.re.smime.mail.ReadSignedMail;
import usto.re.smime.utils.ConvertCertificates;

@Path("/read")
public class ServiceRead {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataMsg ReadMail(BodyMail mail) throws IOException, UnrecoverableKeyException, NoSuchAlgorithmException,
			CertificateException, KeyStoreException, NoSuchProviderException, MessagingException {

		System.out.println("Reading email");
		ConvertCertificates conv = new ConvertCertificates();
		Random radom = new Random();
		int number = 0;

		//////////////////////
		// ==============================CERTIFICADO===============================================================\\
		if (mail.getPasswd() == null || mail.getPassCert() == null)
			return null;

		String cert_path = "";

		// Essa seção se inicia para tratamento da serialização do Certificado
		// Comeca o download do arquivo
		String temp = System.getProperty("java.io.tmpdir");
		String tempFinal = temp + "/" + mail.getLabel();

		File file = new File(tempFinal);
		
		 if(!file.exists()){
			 cert_path = file.getAbsolutePath();
			 }else{
				 cert_path = tempFinal;
			 }

		// ===================================END============================================================\\

		conv.decryptCert(cert_path, mail.getPassCert());
		System.out.println(conv.getCertificate());

		Properties props = System.getProperties();

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);
		javax.mail.Store store = session.getStore("imaps");
		store.connect(mail.getHost(), mail.getAccount(), mail.getPasswd());
		Folder inbox = store.getFolder("Inbox");
		inbox.open(Folder.READ_WRITE);
		UIDFolder folder = (UIDFolder) inbox;
		Long id = Long.parseLong(mail.getId());
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

		// System.out.println(data.getContent());
		//file.delete();
		return data;

	}
}
