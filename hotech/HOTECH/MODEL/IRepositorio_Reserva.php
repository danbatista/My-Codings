<?php

interface IRepositorio_Reserva{
	
	
		public function Inserir(reserva $Reserva);
		public function Excluir(reserva $Reserva);
		public function Pesquisar($campo, $valor);
		public function Alterar(reserva $Reserva);
		public function PegarTodos();

} ?>