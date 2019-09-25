<%@ include file="/WEB-INF/views/pacote/include.jsp"%>
<html>
<head>
<title><spring:message code="title.home" />
</title>
</head>
<body>
	<header><span class="icon-logo"></span></header>

	<div class="error"
		<c:if test="${not empty flagErrorSelect && flagErrorSelect eq false}">style="display: none"</c:if>>
		<p class="error">
			<spring:message code="contratar-pacote-dados.select.error" />
		</p>
	</div>

	<spring:message code="contratar-pacote-dados.bundle.msg" />
	<h2><spring:message code="contratar-pacote-dados.package.header" /></h2>

	<form:form commandName="webCommand" id="frmHome" method="POST" action="confirm3g">
	
		<ul class="plans">
			<c:forEach items="${packages}" var="packages">
				<li>
					<label>
						<form:radiobutton path="idOferta" name="plan" value="${packages.idOferta}" />
						<c:choose>
							<c:when test="${empty packages.plan}">
        						<span class="plan">${packages.descricao}</span>
    						</c:when>
    						<c:otherwise>
        						<span class="plan">${packages.plan}</span>
								<span class="por">por</span>
								<span class="currency">R$</span><span class="integer">${packages.price}</span><span class="decimal">,${packages.decimal}</span>
    						</c:otherwise>
						</c:choose>
					</label>
				</li>
				<input type="hidden" id="desc_${packages.idOferta}" value="${packages.descricao}" />
				<input type="hidden" id="campanha_${packages.idOferta}" value="${packages.idCampanha}" />
				<input type="hidden" id="plan_${packages.idOferta}" value="${packages.plan}" />
				<input type="hidden" id="price_${packages.idOferta}" value="${packages.price}" />
				<input type="hidden" id="decimal_${packages.idOferta}" value="${packages.decimal}" />
				<input type="hidden" id="dataCorte_${packages.idOferta}" value="${packages.dataCorte}" />
				<br class="clear" />
			</c:forEach>
		</ul>
		
		<form:hidden path="descricao" />
		<form:hidden path="idCampanha" />
		<form:hidden path="plan" />
		<form:hidden path="price" />
		<form:hidden path="decimal" />
		<form:hidden path="dataCorte" />
		
	</form:form>

	<div class="actions">
		<table>
			<tr>
				<td>
					<a class="btn btn-primary" id="btnContinue" href="javascript:void(0);">
						<spring:message code="btn.continuar" />
					</a>
				</td>
				<td class="separator"></td>
				<td>
					<a class="btn btn-secondary" id="btnCancel" href="reducao3g">
						<spring:message code="btn.cancelar" />
					</a>
				</td>
			</tr>
		</table>
	</div>

	<div class="footer-note">
		<spring:message code="contratar-pacote-dados.footer.note" />
	</div>

</body>

<script type="text/javascript">
	$(document).ready(function() {

		$("input:radio").change(function() {
			var idOferta = $("input:checked").val();
			$("#descricao").val($("#desc_" + idOferta).val());
			$("#idCampanha").val($("#campanha_" + idOferta).val());
			$("#plan").val($("#plan_" + idOferta).val());
			$("#price").val($("#price_" + idOferta).val());
			$("#decimal").val($("#decimal_" + idOferta).val());
			$("#dataCorte").val($("#dataCorte_" + idOferta).val());
		});

		$("#btnContinue").click(function() {
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
