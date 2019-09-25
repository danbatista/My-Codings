<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Contatos</title>
</head>
<body>

<table border="1">

	<tr>
		<th> Nome</th>
		<th> Telefone </th>
		
	</tr>
	
	<c:forEach var="item" items="${listaNome}" varStatus="id">
	 <tr>  	<td>  ${item.nome}  </td>    
		<td>  ${item.telefone}</td>   
		<td> <a href = "update?id=${item.id}">Editar</a>  </td>   
		<td> <a href = "delete">Deletar</a>  </td>  
	</tr>
    </c:forEach> 
    </table>
</body>
</html>