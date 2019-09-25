<?php
 class funcionario  extends pessoas{
 	
 	private $Matricula_Funcionario; 
 	private $Cargo_Funcinario;
 	private $Data_Nascimento;
 	private $CPF_Funcionario;
 	private $COD_PESSOA;
 	
 	public function __construct ($Matricula_Funcionario,$Cargo_Funcinario ,$Data_Nascimento, $CPF_Funcionario, 
 			$COD_PESSOA){
 				$this->Matricula_Funcionario = $Matricula_Funcionario ; 
 				$this->Cargo_Funcinario=$Cargo_Funcinario;
 				$this->Data_Nascimento =$Data_Nascimento ;
 				$this->CPF_Funcionario =$CPF_Funcionario; 
 				$this->COD_PESSOA = $COD_PESSOA;
		
 	}
	public function getMatriculaFuncionario() {
		return $this->Matricula_Funcionario;
	}
	public function setMatriculaFuncionario($Matricula_Funcionario) {
		$this->Matricula_Funcionario = $Matricula_Funcionario;
		return $this;
	}
	public function getCargoFuncinario() {
		return $this->Cargo_Funcinario;
	}
	public function setCargoFuncinario($Cargo_Funcinario) {
		$this->Cargo_Funcinario = $Cargo_Funcinario;
		return $this;
	}
	public function getDataNascimento() {
		return $this->Data_Nascimento;
	}
	public function setDataNascimento($Data_Nascimento) {
		$this->Data_Nascimento = $Data_Nascimento;
		return $this;
	}
	public function getCPFFuncionario() {
		return $this->CPF_Funcionario;
	}
	public function setCPFFuncionario($CPF_Funcionario) {
		$this->CPF_Funcionario = $CPF_Funcionario;
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