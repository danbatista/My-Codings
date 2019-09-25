package usto.re.smime.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import usto.re.smime.dao.ArquivoDAO;
import usto.re.smime.dao.ArquivoDAOImpl;
import usto.re.smime.pojo.Arquivo;
import usto.re.smime.utils.ConvertCertificates;

public class ArquivoModelImpl implements ArquivoModel {

	ArquivoDAO dao = new ArquivoDAOImpl();

	@Override
	public String alterar(String senha, InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {

		Arquivo arquivo = new Arquivo();

		try {

			String temp = System.getProperty("java.io.tmpdir");
			String tempFinal = temp + "/" + fileDetail.getFileName();

			ConvertCertificates conv = new ConvertCertificates();

			conv.decryptCert(writeToFile(uploadedInputStream, tempFinal, senha, null), senha);

			Principal subject = conv.getCertificate().getSubjectDN();
			String subjectArray[] = subject.toString().split(",");
			for (String s : subjectArray) {
				String[] str = s.trim().split("=");
				String value = str[1];
				if(value.indexOf("@") != -1){
					System.out.println(value);
					arquivo.setEmail(value);
				}
			}

			arquivo.setChavePublica(conv.getPubKey().getEncoded());
			
			dao.alterar(arquivo);
			return "100";
		} catch (UnrecoverableKeyException | NoSuchAlgorithmException | CertificateException | KeyStoreException
				| NoSuchProviderException | IOException e) {
			e.printStackTrace();
			return "101";
		}

		
	}

	@Override
	public String salvar(String senha, InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {

		Arquivo arquivo = new Arquivo();

		try {

			String temp = System.getProperty("java.io.tmpdir");
			String tempFinal = temp + "/" + fileDetail.getFileName();

			ConvertCertificates conv = new ConvertCertificates();
          // System.out.println(writeToFile(uploadedInputStream, tempFinal, senha, null));
			conv.decryptCert(writeToFile(uploadedInputStream, tempFinal, senha, null), senha);

			Principal subject = conv.getCertificate().getSubjectDN();
			String subjectArray[] = subject.toString().split(",");
			for (String s : subjectArray) {
				String[] str = s.trim().split("=");
				String value = str[1];
				if(value.indexOf("@") != -1){
					System.out.println(value);
					arquivo.setEmail(value);
				}
			}
			arquivo.setChavePublica(conv.getPubKey().getEncoded());
			dao.salvar(arquivo);
			return "100";
		} catch (UnrecoverableKeyException | NoSuchAlgorithmException | CertificateException | KeyStoreException
				| NoSuchProviderException | IOException e) {
			e.printStackTrace();
			return "101";
		}

	}

	@Override
	public List<Arquivo> tudo() {
		try {
			return dao.tudo();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Arquivo buscarPorCodigo(Integer codigo) {
		try {
			return dao.buscarPorCodigo(codigo);

		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Arquivo buscarPorEmail(String email) {
		try {

			return dao.buscarPorEmail(email);
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public String excluirPorCodigo(Integer codigo) {
		try {
			dao.excluirPorCodigo(codigo);
			return "100";
		} catch (Exception e) {
			return "Não foi possivel realizar essa operação, por favor, tente novamente!";
		}

	}

	@Override
	public String excluirPorEmail(String email) {
		try {
			dao.buscarPorEmail(email);
			return "Operação realizada com sucesso!";
		} catch (Exception e) {
			return "Não foi possivel realizar essa operação, por favor, tente novamente!";
		}
	}

	private String writeToFile(InputStream uploadedInputStream, String uploadedFileLocation, String pfxPassword,
			String account) {

		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			// out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return uploadedFileLocation;
	}

}
