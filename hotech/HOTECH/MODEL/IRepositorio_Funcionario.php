<?php

interface IRepositorio_Funcionario {
	
	public function Inserir(funcionario $funcionario);
	public function Excluir(funcionario $funcionario);
	public function Pesquisar($campo, $valor);
	public function Alterar(funcionario $funcionario);
	public function PegarTodos();
	
	
}?>