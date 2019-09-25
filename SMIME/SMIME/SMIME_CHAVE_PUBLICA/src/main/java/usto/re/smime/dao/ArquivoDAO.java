package usto.re.smime.dao;

import java.util.List;

import usto.re.smime.pojo.Arquivo;


public interface ArquivoDAO {

	public void salvar(Arquivo arquivo);
	public void alterar(Arquivo arquivo);
	public List<Arquivo> tudo();
	public Arquivo buscarPorCodigo(Integer codigo);
	public Arquivo buscarPorEmail(String email);
	public void excluirPorCodigo(Integer codigo);
	public void excluirPorEmail(String email);
}
