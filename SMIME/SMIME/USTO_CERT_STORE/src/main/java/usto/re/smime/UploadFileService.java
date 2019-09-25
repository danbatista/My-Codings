package usto.re.smime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import usto.re.smime.utils.ConvertCertificates;

@Path("/file")
public class UploadFileService {

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("password") String password,
			@FormDataParam("account") String account) {

		String temp = System.getProperty("java.io.tmpdir");
		String tempFinal = temp + "/" + fileDetail.getFileName();
		// save it
		String output = writeToFile(uploadedInputStream, tempFinal, password, account);
		System.out.println("Saida:" + output);
		return Response.status(200).entity(output).build();

	}

	@POST
	@Path("/pubkey")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadPubKey(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, 
			@FormDataParam("password") String password) {
      System.out.println();
		String temp = System.getProperty("java.io.tmpdir");
		String tempFinal = temp + "/" + fileDetail.getFileName();
		// save it
		//String output = writeToFile(uploadedInputStream, tempFinal, password);
		//System.out.println("Saida:" + output);
	return Response.status(200).entity("SUcesso!").build();
		

	}

	// save uploaded file to new location
	private String writeToFile(InputStream uploadedInputStream, String uploadedFileLocation, String pfxPassword,
			String account) {

		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();

			try {
				ConvertCertificates conv = new ConvertCertificates();
				conv.decryptCert(uploadedFileLocation, pfxPassword);

				if(!ConvertCertificates.returnEmail(conv.getCertificate(), account))return "C101";
				
				return "C100";
			} catch (Exception e) {
				e.printStackTrace();
				return "C102";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}

	}
	

}
