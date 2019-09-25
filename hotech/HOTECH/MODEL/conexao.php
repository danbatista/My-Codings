<?php
abstract class database{
	/*Método construtor do banco de dados*/
	private function __construct(){}
	 
	/*Evita que a classe seja clonada*/
	private function __clone(){}
	 
	/*Método que destroi a conexão com banco de dados e remove da memória todas as variáveis setadas*/
	public function __destruct() {
		$this->disconnect();
		foreach ($this as $key => $value) {
			unset($this->$key);
		}
	}
	 
	private static $dbtype   = "mysql";
	private static $host     = "localhost";
	private static $port     = "3306";
	private static $user     = "u660634306_facol";
	private static $password = "7707805";
	private static $db       = "u660634306_hotel";
	 
	/*Metodos que trazem o conteudo da variavel desejada
	 @return   $xxx = conteudo da variavel solicitada*/
	private function getDBType()  {return self::$dbtype;}
	private function getHost()    {return self::$host;}
	private function getPort()    {return self::$port;}
	private function getUser()    {return self::$user;}
	private function getPassword(){return self::$password;}
	private function getDB()      {return self::$db;}
	 
	private function connect(){
		try
		{
			$this->conexao = new PDO($this->getDBType().":host=".$this->getHost().";port=".$this->getPort().";dbname=".$this->getDB(), $this->getUser(), $this->getPassword());
		}
		catch (PDOException $i)
		{
			//se houver exceção, exibe
			die("Erro: <code>" . $i->getMessage() . "</code>");
		}
		 
		return ($this->conexao);
	}
	 
	private function disconnect(){
		$this->conexao = null;
	}
	/*Método insert que insere valores no banco de dados e retorna o último id inserido*/
	public function insertDB($sql,$params=null){
		$conexao=$this->connect();
		$query=$conexao->prepare($sql);
		$query->execute($params);
		$rs = $conexao->lastInsertId() or die(print_r($query->errorInfo(), true));
		self::__destruct();
		return $rs;
	}
	 
	/*Método update que altera valores do banco de dados e retorna o número de linhas afetadas*/
	public function updateDB($sql,$params=null){
		$query=$this->connect()->prepare($sql);
		$query->execute($params);
		$rs = $query->rowCount() or die(print_r($query->errorInfo(), true));
		self::__destruct();
		return $rs;
	}
	 
	/*Método delete que excluí valores do banco de dados retorna o número de linhas afetadas*/
	public function deleteDB($sql,$params=null){
		$query=$this->connect()->prepare($sql);
		$query->execute($params);
		$rs = $query->rowCount() or die(print_r($query->errorInfo(), true));
		self::__destruct();
		return $rs;
	}
	}
	
	
?>


