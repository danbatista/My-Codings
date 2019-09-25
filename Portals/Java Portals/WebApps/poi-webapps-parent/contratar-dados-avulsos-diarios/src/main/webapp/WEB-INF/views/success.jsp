<%@ include file="/WEB-INF/views/include.jsp"%>
<html>
<head>
<title><spring:message code="title.protocolo" /></title>
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
				<h1><spring:message code="header.labelProtocol" /></h1>
			</div>
			<div class="section content">
				<h2><spring:message code="header.msgSuccess" /></h2>
				<div class="section protocol">
					<div class="header">
						<p>
							<strong><spring:message code="hader.labelNumeroProtocolo" /></strong> <span class="num">${protocol}</span>
						</p>
					</div>
					<p>
						<strong><spring:message code="header.labelRequest" /></strong> <span><spring:message code="header.oiCelular" /></span>
					</p>
					<p>
						<strong><spring:message code="header.labelDate" /></strong> <span>${date}</span>
						<strong class="hour"><spring:message code="header.labelTime" /></strong> <span>${time}</span>
					</p>
					<p>
						<span class="inline prot-left"><strong><spring:message code="header.labelTerm" /></strong>
						</span> <span class="inline prot-right"><spring:message code="header.msgTerm" /></span>
					</p>
				</div>
			</div>
			<div class="footer">
				<ul>
					<li class="bt"><a href="http://minha.oi.com.br"><spring:message code="btn.fechar" /></a>
					</li>
				</ul>
			</div>
		</div>
		<!-- START Google Analytics Tagging -->
		<script type="text/javascript">
			dpc_callTrans('', '', '', '', 'Venda', 'mobile-${protocol}', '${selectedPackage}', '2.50');
		</script>
		<!-- END Google Analytics Tagging -->
	</div>
	<!-- END #wrap -->
</body>
</html>