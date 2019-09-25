<%@ include file="/WEB-INF/views/include.jsp"%>
<html>
<head>
<title><spring:message code="title.home" /></title>
</head>
<body>
	<!-- START #wrap -->
	<div id="wrap">
		<div class="header">
			<div class="figure" id="logo">
				<img src="<c:url value="../assets/img/content/logo.png"/>" alt="OI" />
			</div>
		</div>
		<div class="section">
			<div class="header">
				<h1><spring:message code="header.oiCelular" /></h1>
				<div class="error" <c:if test="${not empty flagErrorSelect && flagErrorSelect}">style="display: block"</c:if>>
					<span class="error bold"><spring:message code="header.labelAttention" /></span><spring:message code="header.errSelect" />
				</div>
				<p><spring:message code="header.msgNoPlan" /></p>
			</div>
			<div class="section content">
				<h2><spring:message code="header.msgSelect" arguments="${msisdn}" /></h2>
				<form action="confirm" id="frmHome" method="POST">
					<c:forEach items="${packages}" var="pacote">
						<input type="radio" name="pacote" class="radioBtn" value="${pacote.idOferta}" />
						<span class="label">${pacote.descricao}</span>
						<br class="clear" />
					</c:forEach>
					<!--
					<input type="radio" name="pacote" class="radioBtn" value="1" />
					<span class="label">100MB de Internet por apenas R$15,00 por mês</span>
					<br class="clear" />
					<input type="radio" name="pacote" class="radioBtn" value="2" />
					<span class="label">200MB de Internet por apenas R$23,00 por mês</span>
					<br class="clear" />
					<input type="radio" name="pacote" class="radioBtn" value="3" />
					<span class="label">500MB de Internet por apenas R$45,00 por mês</span>
					<br class="clear" />
					-->
					<input type="hidden" name="PROCOLO_OP" id="PROCOLO_OP" value="${protocolo}" />
					<input type="hidden" name="USUARIO_IP" id="USUARIO_IP" value="${usuarioIP}" />
				</form>
				<div class="sectionBottom">
					<p><spring:message code="header.msgLimit" /></p>
				</div>
			</div>
			<div class="footer">
				<ul>
					<li class="bt"><a id="btnClose" href="http://minha.oi.com.br"><spring:message code="btn.fechar" /></a>
					</li>
					<li class="bt"><a id="btnConfirm" href="javascript:void(0);"><spring:message code="btn.continuar" /></a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	
	<!-- END #wrap -->
</body>
<script type="text/javascript">
	$(document).ready(function () {
		$("#btnConfirm").click(function () {
			/* Uncomment the javascript for client side validation
			if (!$("input[name='pacote']").is(':checked')) {
			   $(".error").css("display", "block");
			} else { */
				$("#frmHome").submit();
			//}
		});
	});
</script>
</html>