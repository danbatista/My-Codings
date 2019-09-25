package br.net.oi.intranet.gerenciadormensagens.web.command;


import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class TreinamentoCommand {
	
	private static final long SerialVersionUID = 1l;
	
	private int id;
	
	@NotEmpty
    private String nome;
 
    @NotEmpty
    @Size(min = 10, max = 11)
    private String telefone;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
 
    // getters and setters
 
}