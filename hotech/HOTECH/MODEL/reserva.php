<?php

class reserva {
	
	private $Reserva_ID;
	private $CODIGO_QUARTO ;
	private $Data_INICIO;
	private $Data_FIM;
	private $COD_CLIENTE;
	
	public function __construct ($Reserva_ID,$CODIGO_QUARTO ,$Data_INICIO, $Data_FIM,$COD_CLIENTE){
		$this->Reserva_ID  =$Reserva_ID; 
		$this->CODIGO_QUARTO  =$CODIGO_QUARTO; 
		$this->Data_INICIO  =$Data_INICIO;
		$this->Data_FIM    =$Data_FIM;
	    $this->COD_CLIENTE  =$COD_CLIENTE;
	}
	public function getReservaId() {
		return $this->Reserva_ID;
	}
	public function setReservaId($Reserva_ID) {
		$this->Reserva_ID = $Reserva_ID;
		return $this;
	}
	public function getCODIGOQuarto() {
		return $this->CODIGO_QUARTO;
	}
	public function setCODIGOQuarto($CODIGO_QUARTO) {
		$this->CODIGO_QUARTO = $CODIGO_QUARTO;
		return $this;
	}
	public function getDataInicio() {
		return $this->Data_INICIO;
	}
	public function setDataInicio($Data_INICIO) {
		$this->Data_INICIO = $Data_INICIO;
		return $this;
	}
	public function getDataFim() {
		return $this->Data_FIM;
	}
	public function setDataFim($Data_FIM) {
		$this->Data_FIM = $Data_FIM;
		return $this;
	}
	public function getCODCliente() {
		return $this->COD_CLIENTE;
	}
	public function setCODCliente($COD_CLIENTE) {
		$this->COD_CLIENTE = $COD_CLIENTE;
		return $this;
	}
	
	
}?>