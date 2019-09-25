package usto.re.smime.model;

import java.io.InputStream;
import java.util.List;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import usto.re.smime.pojo.Arquivo;

public interface ArquivoModel {

	public List<Arquivo> tudo();
	public Arquivo buscarPorCodigo(Integer codigo);
	public Arquivo buscarPorEmail(String email);
	public String excluirPorCodigo(Integer codigo);
	public String excluirPorEmail(String email);
	public String salvar(String senha, InputStream uploadedInputStream, FormDataContentDisposition fileDetail);
	public String alterar(String senha, InputStream uploadedInputStream, FormDataContentDisposition fileDetail);
}
