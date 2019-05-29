<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="../../_include/header.jsp" %>

<body>
<%@include file="../../_include/iecondition.jsp" %>

<div id="container">

    <%@include file="../../_include/topbar.jsp" %>

    <div id="wrapper" class="columns">

        <%@include file="../../_include/aside_nav.jsp" %>

        <div id="contentSide" class="column is-two-thirds">

            <c:choose>
                <c:when test="${book != null}">
                    <section>

                        <header>

                            <h1>Livre : ${book.title}</h1>

                        </header>

                        <article>

                            <div class="menu">
                                <ul class="menu-list">
                                    <li class="list-content"><a href="${pageContext.request.contextPath}/authors/details/${book.author.id}">Auteur : ${book.author.firstname} ${book.author.lastname}</a></li>
                                    <li class="list-content">Publié en : ${book.publicationDate.getYear()}</li>
                                    <li class="list-content"><a href="${pageContext.request.contextPath}/categories/details/${book.category.id}">Catégorie : ${book.category.title}</a></li>
                                    <li class="list-content">Nombre de copies : ${book.copies}</li>
                                    <li class="list-content">Nombre de copies disponibles : ${copiesAvailable}</li>
                                </ul>
                            </div>

                        </article>

                    </section>
                </c:when>
                <c:otherwise>
                    <section>

                        <header>

                            <h1>Aucun livre n'a été trouvé !</h1>

                        </header>

                        <article>

                        </article>

                    </section>
                </c:otherwise>
            </c:choose>

        </div>

        <div class="column"></div>

    </div>

</div>

<!-- Load JavaScript file -->
<script src="<c:url value="/resources/js/script.js" />"></script>
</body>
</html>
