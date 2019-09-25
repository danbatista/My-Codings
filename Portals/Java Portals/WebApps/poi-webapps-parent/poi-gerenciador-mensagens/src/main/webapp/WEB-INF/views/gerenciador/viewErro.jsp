<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="../scripts/Html5.fix.IE8.js" type="text/javascript"></script>
	<link type="text/css" href="../style/gerenciaMsg.css" rel="stylesheet" />
	<script type="text/javascript" src="../scripts/jquery.js"></script>
	<link type="text/css" href="../scripts/fancybox/jquery.fancybox-1.3.2.css" rel="stylesheet" />
	
	 <!--[if IE]>
		<link rel="stylesheet" type="text/css" href="../style/ie.css" />
	 <!--<![endif]-->
	 
	<!--[if !IE]><!-->
		<link rel="stylesheet" type="text/css" href="../style/not-ie.css" />
	 <!--<![endif]-->
	 
</head>

<body>
	<div id="sso-home" class="area-sso">
		<!-- Menu -->
		<h2 class="sso-home">
			<span class="logo"> 
				<a href="mensagem"> 
					<img src="../images/oi-logo5.jpg" title="Minha Oi" alt="Minha Oi"> 
				</a> 
			</span> 
			<span class="tit-sso"><spring:message code="gerenciar.mensagem.title"/></span>
		</h2>
		<!-- END Menu -->

		<div id="geral" class="wrapper clearfix">
			<section> 
				<form method="post" action="result" class="sweetForm" id="crieSeuLogin">
					<input type="hidden" id="hd_canal" name="hd_canal" />
					<fieldset class="crieSeuLogin fieldsetProcLogin2">					
						
							<b><spring:message code="gerenciar.err.message.login"/></b>
						
					</fieldset>
				</form>
			</section>
		</div>
	</div>
	<footer class="clearfix">
		<div class="onlybg">
			<div class="wrapper">
				<spring:message code="gerenciar.footer.copyright"/>
				<spring:message code="gerenciar.footer.copyright" />
				<a href="<spring:message code="gerenciar.footer.url"/>" 
					title="<spring:message code="gerenciar.footer.url"/>">
					<spring:message code="gerenciar.footer.url" />
				</a>
				<spring:message code="gerenciar.footer.reservados" />
			</div>
		</div>
	</footer>
</body>
</html>