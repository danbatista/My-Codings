<?php

interface  IRepositorio_Quartos {
	
	public function Inserir(quartos $quartos);
	public function Excluir(quartos $quartos);
	public function Pesquisar($campo, $valor);
	public function Alterar(quartos $quartos);
	public function PegarTodos();
	
} ?>