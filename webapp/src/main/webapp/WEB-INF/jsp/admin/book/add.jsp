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

                    <h1>Ajouter un livre</h1>

                </header>

                <article>

                    <form:form action="${pageContext.request.contextPath}/books/adding" method="POST" modelAttribute="book">
                        <form:hidden path="id" id="${book.id}" />

                        <div class="field">
                            <form:label path="title" class="label">Titre :</form:label>
                            <div class="control">
                                <form:input id="title" name="title" path="title" class="input" type="text" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="author" class="label">Auteur :</form:label>
                            <div class="control">
                                <form:input id="author" name="author" path="author" class="input" type="text" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="category" class="label">Auteur :</form:label>
                            <div class="control">
                                <form:input id="category" name="category" path="category" class="input" type="text" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="publicationDate" class="label">Date de publication :</form:label>
                            <div class="control">
                                <form:input id="publicationDate" name="publicationDate" path="publicationDate" class="input" type="text" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="copies" class="label">Auteur :</form:label>
                            <div class="control">
                                <form:input id="copies" name="copies" path="copies" class="input" type="number" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <div class="control">
                                <form:button class="button is-dark">Ajouter Book</form:button>
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
