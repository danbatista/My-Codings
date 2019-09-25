<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" href="../style/forms.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<style>
    .error {
        color: red; font-weight: bold;
    }
</style>
</head>
<body>
    <div align="center">
        <h2>Spring MVC Form - Update Form</h2>
        <table border="0" width="90%">
        <form:form action="atualiza" commandName="TreinamentoCommand" class="frmBloqueio">
                <tr>
                    <td align="left" width="20%">Nome: </td>
                    <td align="left" width="40%"><form:input path="nome" size="30" value = "${contato.nome}"/></td>
                    <td align="left"><form:errors path="nome" cssClass="error"/></td>
                </tr>
                <tr>
                    <td>Telefone: </td>
                    <td><form:input path="telefone" size="30" value = "${contato.telefone}" /></td>
                    <td><form:errors path="telefone" cssClass="error"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td align="center"><input class = "bt-enviar"  type="submit" value="Salvar"/></td>
                    <td></td>
                </tr>
        </form:form>
        </table>
    </div>
</body>
</html>