<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="../_include/header.jsp" %>

<body>
<%@include file="../_include/iecondition.jsp" %>

<div id="container">

    <%@include file="../_include/topbar.jsp" %>

<div id="wrapper" class="columns">

    <div id="login" class="column is-one-third is-offset-one-third">

        <section>

            <header>

                <h1 id="error-message-title">Oups ! Il y a eu une erreur ... </h1>
                <h2 id="error-message">${message}</h2>

            </header>

            <article>

                <a href="${pageContext.request.contextPath}/">Retour</a>

            </article>

        </section>

    </div>

    <div class="column"></div>

</div>

</div>

<!-- Load JavaScript file -->
<script src="<c:url value="/resources/js/script.js" />"></script>

</body>
</html>
