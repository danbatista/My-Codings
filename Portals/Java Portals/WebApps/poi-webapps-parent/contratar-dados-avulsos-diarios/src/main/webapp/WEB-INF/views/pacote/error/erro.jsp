<%@ include file="/WEB-INF/views/pacote/include.jsp"%>

<html>
<head>
    <title><spring:message code="title.indisponivel" /></title>
</head>
<body>

    <header>
        <span class="icon-logo"></span>
    </header>

    <h2 class="error">
        <span class="icon-error"></span><br/>
        <spring:message code="contratar-pacote-dados.erro.msg" />
    </h2>
    <p>
        <spring:message code="contratar-pacote-dados.error.msg" />
    </p>

<!--    <div class="actions">
        <input type="button" value="Ok" class="btn btn-primary btn-full"/>
    </div>
-->    
     <div class="actions">
        <table>
            <tr>
                <td>
                    <a class="btn btn-primary" id="btnOk" href="<spring:message code='contratar-pacote-dados.homepage'/>"><spring:message	code="btn.ok" /></a>
                </td>
            </tr>
        </table>
    </div>
    
</body>
</html>

