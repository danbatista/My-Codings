<?php
/**
 * Created by DANIEL B..
 *
 * LAST Update: 26-11-2015
 * Time: 10:00
 */
class pessoas{
	PRIVATE $PESSOA_ID;
	PRIVATE $Nome_Pessoa;
	PRIVATE $Endereco_Pessoa;
	PRIVATE $Telefone;
	PRIVATE $Telefone_Comercial;
	PRIVATE $Telefone_Celular;
	PRIVATE $Email_Pessoa;
	PRIVATE $CODIGO_HOTEL;
	public function __construct
	($PESSOA_ID,$Nome_Pessoa,$Endereco_Pessoa,$Telefone,$Telefone_Comercial,$Telefone_Celular,
	$Email_Pessoa, $CODIGO_HOTEL)
	{
		$this->PESSOA_ID=$PESSOA_ID;
		$this->Nome_Pessoa=$Nome_Pessoa;
		$this-> Endereco_Pessoa= $Endereco_Pessoa;
		$this->Telefone=$Telefone;
	    $this->Telefone_Comercial=$Telefone_Comercial;
		$this->Telefone_Celular= $Telefone_Celular;
		$this->Email_Pessoa= $Email_Pessoa;
		$this->CODIGO_HOTEL=$CODIGO_HOTEL;
	}
	
	public function getPESSOAId() {
		return $this->PESSOA_ID;
	}
	public function setPESSOAId($PESSOA_ID) {
		$this->PESSOA_ID = $PESSOA_ID;
		return $this;
	}
	public function getNomePessoa() {
		return $this->Nome_Pessoa;
	}
	public function setNomePessoa($Nome_Pessoa) {
		$this->Nome_Pessoa = $Nome_Pessoa;
		return $this;
	}
	public function getEnderecoPessoa() {
		return $this->Endereco_Pessoa;
	}
	public function setEnderecoPessoa($Endereco_Pessoa) {
		$this->Endereco_Pessoa = $Endereco_Pessoa;
		return $this;
	}
	public function getTelefone() {
		return $this->Telefone;
	}
	public function setTelefone($Telefone) {
		$this->Telefone = $Telefone;
		return $this;
	}
	public function getTelefoneComercial() {
		return $this->Telefone_Comercial;
	}
	public function setTelefoneComercial($Telefone_Comercial) {
		$this->Telefone_Comercial = $Telefone_Comercial;
		return $this;
	}
	public function getTelefoneCelular() {
		return $this->Telefone_Celular;
	}
	public function setTelefoneCelular($Telefone_Celular) {
		$this->Telefone_Celular = $Telefone_Celular;
		return $this;
	}
	public function getEmailPessoa() {
		return $this->Email_Pessoa;
	}
	public function setEmailPessoa($Email_Pessoa) {
		$this->Email_Pessoa = $Email_Pessoa;
		return $this;
	}
	public function getCODIGOHotel() {
		return $this->CODIGO_HOTEL;
	}
	public function setCODIGOHotel($CODIGO_HOTEL) {
		$this->CODIGO_HOTEL = $CODIGO_HOTEL;
		return $this;
	}
	
	
} ?>