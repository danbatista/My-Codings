<%@ include file="/WEB-INF/views/pacote/include.jsp"%>

<html>
<head>
<title><spring:message code="title.confirm" />
</title>
</head>
<body>

	<header> <span class="icon-logo"></span> </header>
	<form:form commandName="webCommand" id="frmConfirm" action="contract3g" method="POST">
		<h2>
			<spring:message code="contratar-pacote-dados.confirm.header" />
		</h2>
		<p>
			<c:choose>
				<c:when test="${empty plan}">
					<spring:message code="contratar-pacote-dados.confirm.msg2" 
					arguments="${description}!${corte}" argumentSeparator="!" />
				</c:when>
				<c:otherwise>
					<spring:message code="contratar-pacote-dados.confirm.msg"
					arguments="${plan}!${price}!${decimal}!${corte}" argumentSeparator="!" />
				</c:otherwise>
			</c:choose>
			
			<form:hidden path="idCampanha" value="${idCampanha}" />
			<form:hidden path="descricao" value="${description}" />
			<form:hidden path="idOferta" value="${selectedId}" />
		</p>

		<div class="actions">
			<table>
				<tr>
					<td><a class="btn btn-primary" id="btnContinue" href="contract3g">
						<spring:message code="btn.confirmar" />
					</a></td>
					<td class="separator"></td>
					<td><a class="btn btn-secondary" id="btnBack" href="home3g">
						<spring:message code="btn.voltar" />
					</a></td>
				</tr>
			</table>
		</div>

		<div class="footer-note">
			<spring:message code="contratar-pacote-dados.footer.note" />
		</div>
	</form:form>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#btnContinue").click(function(e) {
			e.preventDefault();
			$("#frmConfirm").submit();
		});
	});
</script>
</html>

