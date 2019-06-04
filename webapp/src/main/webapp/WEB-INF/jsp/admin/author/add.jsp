<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@include file="../../../_include/header.jsp" %>

<body>
<%@include file="../../../_include/iecondition.jsp" %>

<div id="container">

    <%@include file="../../../_include/topbar.jsp" %>

    <div id="wrapper" class="columns">

        <%@include file="../../../_include/aside_nav.jsp" %>

        <div id="contentSide" class="column is-two-thirds">

            <section>

                <header>

                    <h1>Ajouter un auteur</h1>

                </header>

                <article>

                    <form:form action="${pageContext.request.contextPath}/authors/adding" method="POST" modelAttribute="author">
                        <form:hidden path="id" id="${author.id}" />

                        <div class="field">
                            <form:label path="lastname" class="label">Nom :</form:label>
                            <div class="control">
                                <form:input id="lastname" name="lastname" path="lastname" class="input" type="text" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="firstname" class="label">Prénom :</form:label>
                            <div class="control">
                                <form:input id="firstname" name="firstname" path="firstname" class="input" type="text" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <div class="control">
                                <form:button class="button is-dark">Ajouter Auteur</form:button>
                            </div>
                        </div>
                    </form:form>

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
