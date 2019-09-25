<%@ include file="/WEB-INF/views/include.jsp"%>
<html>
<head>
<title><spring:message code="title.indisponivel" /></title>
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
				<h1><spring:message code="header.labelAttention" /></h1>
			</div>
			<div class="section content">
				<p><spring:message code="${msg}" /></p>
			</div>
			<div class="footer">
				<ul>
					<li class="bt"><a href="http://minha.oi.com.br"><spring:message code="btn.voltar" /></a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- END #wrap -->
</body>
</html>