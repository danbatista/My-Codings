<?php

class Controller_Cliente {
	
	public function  Insere ($Codigo_Cliente,$CPF_CLIENTE,$Data_Nascimento_Cliente,$COD_QUARTO,
 		$COD_PESSOA){ 
		//Inicio aqui o meu Repositorio cliente passando para ele um novo Cliente
 $Repositorio_Cliente = new Repositorio_Cliente;
  $Repositorio_Cliente=  $Repositorio_Cliente->Inserir(new cliente($Codigo_Cliente, $CPF_CLIENTE, 
  		                 $Data_Nascimento_Cliente, $COD_QUARTO, $COD_PESSOA));
	}
	
	public function  Altera ($Codigo_Cliente,$CPF_CLIENTE,$Data_Nascimento_Cliente,$COD_QUARTO,
 		$COD_PESSOA){
 	   $Repositorio_Cliente = new Repositorio_Cliente;
 	  $Repositorio_Cliente=  $Repositorio_Cliente->Alterar($Codigo_Cliente, $CPF_CLIENTE, 
 	  		                  $Data_Nascimento_Cliente, $COD_QUARTO, $COD_PESSOA); 			
	}
	
	public function  Deleta ($Codigo_Cliente){
		$Repositorio_Cliente = new Repositorio_Cliente;
		$Repositorio_Cliente=  $Repositorio_Cliente->Excluir($Codigo_Cliente);
	}
	
	public  function  Pesquisa_one ($Codigo_Cliente){
		$Repositorio_Cliente = new Repositorio_Cliente;
		$Repositorio_Cliente=  $Repositorio_Cliente->Pesquisar($Codigo_Cliente);
		return Dados_do_Cliente; // Como retornar dados do Cliente
	}
	public function  Pesquisa_all ($Codigo_Cliente){
		$Repositorio_Cliente = new Repositorio_Cliente;
		$Repositorio_Cliente=  $Repositorio_Cliente->PegarTodos();
	}
	
}

?>