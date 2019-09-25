<%@ include file="/WEB-INF/views/pacote/include.jsp"%>

<html>
<head>
    <title><spring:message code="title.protocolo" /></title>
</head>
<body>

    <header>
        <span class="icon-logo"></span>
    </header>

    <h2 class="success">
        <span class="icon-success"></span><br/>
        <spring:message code="contratar-pacote-dados.success.header" />
    </h2>
    <p>
        <spring:message code="contratar-pacote-dados.success.msg" />
    </p>

    <p>
        <spring:message code="contratar-pacote-dados.protocolo" />: ${protocol} <br/>
        <spring:message code="contratar-pacote-dados.pacote" />: ${selectedPackage} <br/>
        <spring:message code="contratar-pacote-dados.data" />: ${date}
    </p>
   
      <div class="actions">
        <table>
            <tr>
                <td>
                    <a class="btn btn-primary" href="<spring:message code='contratar-pacote-dados.homepage'/>"><spring:message code="btn.ok" /></a>
                </td>
            </tr>
        </table>
    </div>

</body>
</html>
