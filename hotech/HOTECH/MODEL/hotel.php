<?php
	
	/**
	 * Created by DANIEL B..
	* 
	* LAST Update: 26-11-2015
	* Time: 10:00
	*/
	class hotel {
	private $COD_HOTEL;		
	private $CNPJ_HOTEL;	
	private $Nome_Hotel;
	private $Endereco_Hotel;
	private $Telefone;
	private $Nome_Fantasia;
	 
		public function __construct($COD_HOTEL, $CNPJ_HOTEL, $Nome_Hotel,$Endereco_Hotel, $Telefone,
				$Nome_Fantasia){
				$this->COD_HOTEL = $COD_HOTEL;
				$this->CNPJ_HOTEL = $CNPJ_HOTEL;
				$this->Nome_Hotel = $Nome_Hotel;
				$this->Endereco_Hotel = $Endereco_Hotel;
				$this->Telefone = $Telefone;
				$this->Nome_Fantasia = $Nome_Fantasia;
			}
	public function getCODHotel() {
		return $this->COD_HOTEL;
	}
	public function setCODHotel($COD_HOTEL) {
		$this->COD_HOTEL = $COD_HOTEL;
		return $this;
	}
	public function getCNPJHotel() {
		return $this->CNPJ_HOTEL;
	}
	public function setCNPJHotel($CNPJ_HOTEL) {
		$this->CNPJ_HOTEL = $CNPJ_HOTEL;
		return $this;
	}
	public function getNomeHotel() {
		return $this->Nome_Hotel;
	}
	public function setNomeHotel($Nome_Hotel) {
		$this->Nome_Hotel = $Nome_Hotel;
		return $this;
	}
	public function getEnderecoHotel() {
		return $this->Endereco_Hotel;
	}
	public function setEnderecoHotel($Endereco_Hotel) {
		$this->Endereco_Hotel = $Endereco_Hotel;
		return $this;
	}
	public function getTelefone() {
		return $this->Telefone;
	}
	public function setTelefone($Telefone) {
		$this->Telefone = $Telefone;
		return $this;
	}
	public function getNomeFantasia() {
		return $this->Nome_Fantasia;
	}
	public function setNomeFantasia($Nome_Fantasia) {
		$this->Nome_Fantasia = $Nome_Fantasia;
		return $this;
	}
	
		
		
	}
	
	?>