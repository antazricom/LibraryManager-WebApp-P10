<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="../_include/header.jsp" %>

<body>
<%@include file="../_include/iecondition.jsp" %>

<div id="container">

    <%@include file="../_include/topbar.jsp" %>

<div id="wrapper" class="columns">

    <div id="contentSide" class="column is-offset-2 is-fullwidth">

        <section>

            <header>

                <h1>Connexion</h1>

            </header>

            <article>

                <c:if test="${message != null}">
                    <span>${message}</span>
                </c:if>

                <form:form action="${pageContext.request.contextPath}/auth/process" method="POST" modelAttribute="identifiants">
                    <div class="field">
                        <form:label path="login" class="label">Identifiant</form:label>
                        <div class="control">
                            <form:input path="login" class="input" type="text" required="required" />
                        </div>
                    </div>

                    <div class="field">
                        <form:label path="password" class="label">Mot de passe</form:label>
                        <div class="control">
                            <form:input path="password" class="input" type="password" required="required" />
                        </div>
                    </div>

                    <form:button class="button is-dark">Se connecter</form:button>
                </form:form>

            </article>

        </section>

        <div class="is-offset-one-third is-one-third">
            <footer>
                <p>
                    LibraryManager: Gestion de prêt de livre <br />
                    Openclassrooms P7 @ <a href="https://openclassrooms.com/fr/paths/88-developpeur-dapplication-java">Parcours Développeur d'Application Java/JEE</a> -
                    <a href="http://www.antazri.xyz" target="_blank">Anthony Tazzari</a>
                </p>
            </footer>
        </div>

    </div>

    <div class="column"></div>

</div>

</div>
</body>
</html>
