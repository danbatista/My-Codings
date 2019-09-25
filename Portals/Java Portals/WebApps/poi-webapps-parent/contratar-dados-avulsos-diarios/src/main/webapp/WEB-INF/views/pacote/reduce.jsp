<%@ include file="/WEB-INF/views/pacote/include.jsp"%>

<html>
<head>
<title><spring:message code="title.reduce" />
</title>
</head>
<body>
	<header> <span class="icon-logo"></span> </header>
	<h2>
		<spring:message code="contratar-pacote-dados.reduced.msg" />
	</h2>
	<p>
		<spring:message code="contratar-pacote-dados.reduced.confirm.msg" />
	</p>

	<div class="actions">
		<table>
			<tr>
				<td><a class="btn btn-primary" id="btnContinue" href="<spring:message code='contratar-pacote-dados.homepage'/>">
					<spring:message code="btn.confirmar" /> </a></td>
				<td class="separator"></td>
				<td><a class="btn btn-secondary" id="btnBack" href="home3g">
					<spring:message code="btn.voltar" /> </a></td>
			</tr>
		</table>
	</div>

	<div class="footer-note">
		<spring:message code="contratar-pacote-dados.footer.note" />
	</div>

</body>
</html>

