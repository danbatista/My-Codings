<%@ include file="/WEB-INF/views/include.jsp"%>
<html>
<head>
<title><spring:message code="title.confirm" /></title>
</head>
<body>
	<!-- START #wrap -->
	<div id="wrap">
		<div class="header">
			<div class="figure" id="logo">
				<img src="<c:url value="../assets/img/content/logo.png"/>" alt="OI" />
			</div>
		</div>
		<form id="frmConfirm" action="contract" method="POST">
			<div class="section">
				<div class="header">
					<h1><spring:message code="header.oiCelular" /></h1>
				</div>
				<div class="section content">
					<input type="hidden" name="pacote" value="${selectedId}" />
					<p><spring:message code="header.msgSelected" arguments="${description}" argumentSeparator="|"/></p>
					<p><spring:message code="header.msgConfirm" /></p>
					<div class="sectionBottom">
						<p><spring:message code="header.msgInvoice" /></p>
					</div>
				</div>
				<div class="footer">
					<ul>
						<li class="bt"><a href="home"><spring:message code="btn.voltar" /></a>
						</li>
						<li class="bt"><a href="javascript:void(0);" id="btnConfirm"><spring:message code="btn.confirmar" /></a>
						</li>
					</ul>
				</div>
			</div>
			<input type="hidden" name="PROCOLO_OP" id="PROCOLO_OP" value="${protocolo}" />
			<input type="hidden" name="USUARIO_IP" id="USUARIO_IP" value="${usuarioIP}" />
		</form>
	</div>
	<!-- END #wrap -->
</body>
<script type="text/javascript">
	$(document).ready(function () {
		$("#btnConfirm").click(function () {
			$("#frmConfirm").submit();
		});
	});
</script>
</html>