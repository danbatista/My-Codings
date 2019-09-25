package usto.re.smime.controller;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.gson.Gson;

import usto.re.smime.model.ArquivoModel;
import usto.re.smime.model.ArquivoModelImpl;
import usto.re.smime.pojo.Arquivo;

/**
 * Essa classe vai expor os nossos métodos para serem acessasdos via http
 * 
 * @Path - Caminho para a chamada da classe que vai representar o nosso serviço
 * */
@Path("/service")
public class ArquivoController {
	
	ArquivoModel model = new ArquivoModelImpl();
	@POST	
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/login")
	public String login(
			@FormDataParam("password") String senha,
			@FormDataParam("user") String user){
		 if(user != null && senha != null){
			 System.out.println(user + senha);
		if(user.equals("admin@eb.mil.br") && senha.equals("adminBHU*")){
		  
	      return "100";
		}else{
			
			return "101";
		}
	}else{
		
		return "102";
	}
	 
	}
 
	/**
	 * @Consumes - determina o formato dos dados que vamos postar
	 * @Produces - determina o formato dos dados que vamos retornar
	 * 
	 * Esse método cadastra uma nova pessoa
	 * */
	@POST	
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/upload")
	public String Cadastrar(
			@FormDataParam("password") String senha,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail){
		
		try {
			
			String retorno = model.salvar(senha, uploadedInputStream, fileDetail);
			
			System.out.println("Registro cadastrado com sucesso!");
			
			return retorno;
 
		} catch (Exception e) {
			e.printStackTrace();
			
			return "102";
		}
	 
	}
	
	/**
	 * Essse método altera um arquivo já cadastrado
	 * **/
	@PUT
	@Produces(MediaType.MULTIPART_FORM_DATA)
	@Consumes(MediaType.TEXT_PLAIN)	
	@Path("/alterar")
	public String Alterar(@FormDataParam("password") String senha,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail){
		
		try {
			
			model.alterar(senha, uploadedInputStream, fileDetail);
			
			return "100";
 
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			return "Ocorreu um erro enquanto seu certificado era enviado, por favor, tente novamente!";
		}
	 		 
	}
	
	/**
	 * Esse método lista todos os arquivos cadastradas na base
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todosCertificados")
	public List<Arquivo> TodosCertificados(){

		List<Arquivo> arquivos =  model.tudo();
		return arquivos;
	
	}
	
	/**
	 * Esse método busca um arquivo cadastrado pelo código
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/buscarPorID")
	public String buscaPorID(@FormDataParam("id") String id){
		
		int codigo = Integer.parseInt(id);
		
		Gson gson = new Gson();
		
		String arquivo = gson.toJson(model.buscarPorCodigo(codigo));
		
		return arquivo;
	}
	
	/**
	 * Esse método busca um arquivo cadastrado pelo email
	 * */
	@POST
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Consumes(MediaType.TEXT_PLAIN)	
	@Path("/buscarPorEmail")
	public byte[] buscaPorEmail(String email){
		
		Arquivo arq = new Arquivo();
		arq= model.buscarPorEmail(email);
		System.out.println(arq.getEmail());
		return arq.getChavePublica();
	}

	/**
	 * Excluindo um arquivo pelo código
	 * */
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/del")	
	public String excluirPorCodigo(int id){
		
		//int codigo = Integer.parseInt(id);
		System.out.println("Deletando:" + id);
		model.excluirPorCodigo(id);
		
		return "100";
	}
	
	/**
	 * Excluindo um arquivo pelo email
	 * */
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/excluirPorEmail")	
	public String excluirPorEmail(@FormDataParam("email") String email){
		
		model.excluirPorEmail(email);
		
		return "excluido";
	}
 
}
