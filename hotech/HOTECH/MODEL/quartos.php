<?php


class quartos {
	
	
	private $CODIGO_HOTEL;
	private $TIPO; 
	private $VALOR;
	private $NUMERO_QUARTO ;
	private $CODIGO_QUARTO;
	
	public function __construct ($CODIGO_HOTEL,$TIPO,$VALOR,$NUMERO_QUARTO,$CODIGO_QUARTO){
		$this->CODIGO_HOTEL =$CODIGO_HOTEL;
	 	$this->TIPO =$TIPO;
		$this->VALOR = $VALOR ;
		$this-> NUMERO_QUARTO = $NUMERO_QUARTO;
		$this-> CODIGO_QUARTO = $CODIGO_QUARTO;
		
	}
	public function getCODIGOHotel() {
		return $this->CODIGO_HOTEL;
	}
	public function setCODIGOHotel($CODIGO_HOTEL) {
		$this->CODIGO_HOTEL = $CODIGO_HOTEL;
		return $this;
	}
	public function getTIPO() {
		return $this->TIPO;
	}
	public function setTIPO($TIPO) {
		$this->TIPO = $TIPO;
		return $this;
	}
	public function getVALOR() {
		return $this->VALOR;
	}
	public function setVALOR($VALOR) {
		$this->VALOR = $VALOR;
		return $this;
	}
	public function getNUMEROQuarto() {
		return $this->NUMERO_QUARTO;
	}
	public function setNUMEROQuarto($NUMERO_QUARTO) {
		$this->NUMERO_QUARTO = $NUMERO_QUARTO;
		return $this;
	}
	public function getCODIGOQuarto() {
		return $this->CODIGO_QUARTO;
	}
	public function setCODIGOQuarto($CODIGO_QUARTO) {
		$this->CODIGO_QUARTO = $CODIGO_QUARTO;
		return $this;
	}
	
} ?>