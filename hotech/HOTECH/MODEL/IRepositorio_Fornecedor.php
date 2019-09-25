<?php

interface IRepositorio_Fornecedor{
	
	public function Inserir(fornecedor $fornecedor);
	public function Excluir(fornecedor $fornecedor);
	public function Pesquisar($campo, $valor);
	public function Alterar(fornecedor $fornecedor);
	public function PegarTodos();
} ?>