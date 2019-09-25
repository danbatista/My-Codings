<?php


class fornecedor extends pessoas {
	
	private $FORNECEDOR_ID;
	private $Fornecedor_CNPJ;  
	private $Nome_da_Empresa; 
	private $COD_PESSOA;
	
	public function __construct($FORNECEDOR_ID,$Fornecedor_CNPJ,$Nome_da_Empresa,$COD_PESSOA){
		$this->FORNECEDOR_ID =$FORNECEDOR_ID;
		$this->Fornecedor_CNPJ =$Fornecedor_CNPJ;
		$this->Nome_da_Empresa =$Nome_da_Empresa;
		$this->COD_PESSOA =$COD_PESSOA;
	}
	public function getFORNECEDORId() {
		return $this->FORNECEDOR_ID;
	}
	public function setFORNECEDORId($FORNECEDOR_ID) {
		$this->FORNECEDOR_ID = $FORNECEDOR_ID;
		return $this;
	}
	public function getFornecedorCnpj() {
		return $this->Fornecedor_CNPJ;
	}
	public function setFornecedorCnpj($Fornecedor_CNPJ) {
		$this->Fornecedor_CNPJ = $Fornecedor_CNPJ;
		return $this;
	}
	public function getNomeDaEmpresa() {
		return $this->Nome_da_Empresa;
	}
	public function setNomeDaEmpresa($Nome_da_Empresa) {
		$this->Nome_da_Empresa = $Nome_da_Empresa;
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