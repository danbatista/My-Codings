<?php


interface IRepositorio_Cliente {
	
	public function Inserir(Cliente $cliente);  
    public function Excluir($codigo);
    public function Pesquisar($campo, $valor);
    public function Alterar(Cliente $cliente);
    public function PegarTodos();
}


?>