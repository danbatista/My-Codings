<?php


 class cliente  extends pessoas{
	
 	private $Codigo_Cliente ;
 	private $CPF_CLIENTE;
 	private $Data_Nascimento_Cliente ;
 	private $COD_QUARTO;
 	private $COD_PESSOA;
 	public function __construct ($Codigo_Cliente,$CPF_CLIENTE,$Data_Nascimento_Cliente,$COD_QUARTO,
 		$COD_PESSOA){
 			$this->Codigo_Cliente =$Codigo_Cliente;
 			$this->CPF_CLIENTE =$CPF_CLIENTE;
 			$this->Data_Nascimento_Cliente =$Data_Nascimento_Cliente;
 			$this->COD_QUARTO =$COD_QUARTO;
 			$this->COD_PESSOA =$COD_PESSOA;		
 		
 	}
	public function getCodigoCliente() {
		return $this->Codigo_Cliente;
	}
	public function setCodigoCliente($Codigo_Cliente) {
		$this->Codigo_Cliente = $Codigo_Cliente;
		return $this;
	}
	public function getCPFCliente() {
		return $this->CPF_CLIENTE;
	}
	public function setCPFCliente($CPF_CLIENTE) {
		$this->CPF_CLIENTE = $CPF_CLIENTE;
		return $this;
	}
	public function getDataNascimentoCliente() {
		return $this->Data_Nascimento_Cliente;
	}
	public function setDataNascimentoCliente($Data_Nascimento_Cliente) {
		$this->Data_Nascimento_Cliente = $Data_Nascimento_Cliente;
		return $this;
	}
	public function getCODQuarto() {
		return $this->COD_QUARTO;
	}
	public function setCODQuarto($COD_QUARTO) {
		$this->COD_QUARTO = $COD_QUARTO;
		return $this;
	}
	public function getCODPessoa() {
		return $this->COD_PESSOA;
	}
	public function setCODPessoa($COD_PESSOA) {
		$this->COD_PESSOA = $COD_PESSOA;
		return $this;
	}
	
	
	
} ?>