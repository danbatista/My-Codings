package usto.re.smime.dao;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import usto.re.smime.pojo.Arquivo;

public class ArquivoDAOImpl implements ArquivoDAO{
	
	private final EntityManagerFactory entityManagerFactory;
	private final EntityManager entityManager;
	 
	public ArquivoDAOImpl(){
 
		/*CRIANDO O NOSSO EntityManagerFactory COM AS PORPRIEDADOS DO ARQUIVO persistence.xml */
		this.entityManagerFactory = Persistence.createEntityManagerFactory("smime_PU");
 
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}
 
	/**
	 * CRIA UM NOVO REGISTRO NO BANCO DE DADOS
	 * */
	public void salvar(Arquivo arquivo){
 
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(arquivo);
		this.entityManager.getTransaction().commit();
	}
 
	/**
	 * ALTERA UM REGISTRO CADASTRADO
	 * */
	public void alterar(Arquivo arquivo){
 
		this.entityManager.getTransaction().begin();
		this.entityManager.merge(arquivo);
		this.entityManager.getTransaction().commit();
	}
 
	/**
	 * RETORNA TODOS OS ARQUIVOS CADASTRADOS NO BANCO DE DADOS 
	 * */

	public List<Arquivo> tudo(){
 
		List<Arquivo> resultList = this.entityManager.createQuery("FROM arquivo ORDER BY email").getResultList();
		return resultList;
	}


	/**
	 * CONSULTA UM ARQUIVO CADASTRADO  PELO CÓDIGO
	 * */
	public Arquivo buscarPorCodigo(Integer codigo){
 
		  Arquivo arq = entityManager.createQuery("FROM arquivo a where a.id = :id", Arquivo.class).
				   setParameter("id", codigo).getSingleResult();
			System.out.println(arq.getId());	 
				   return arq;
	}
	
	/**
	 * CONSULTA UM ARQUIVO CADASTRADO PELO EMAIL
	 * */
	public Arquivo buscarPorEmail(String email){
		
		   Arquivo arq = entityManager.createQuery("FROM arquivo a where a.email = :email", Arquivo.class).
		   setParameter("email", email).getSingleResult();
		 
		   return arq;
		
	}
	
	/**
	 * EXCLUINDO UM REGISTRO PELO CÓDIGO
	**/
	public void excluirPorCodigo(Integer codigo){
 
		Arquivo arquivo = buscarPorCodigo(codigo);
        System.out.println("Apagando:"+arquivo.getEmail());
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(arquivo);
		this.entityManager.getTransaction().commit();
		this.entityManager.close();
 
	}
	
	/**
	 * EXCLUINDO UM REGISTRO PELO CÓDIGO
	**/
	public void excluirPorEmail(String email){
 
		Arquivo arquivo = this.buscarPorEmail(email);
 
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(arquivo);
		this.entityManager.getTransaction().commit();
		this.entityManager.close();
 
	}
}

