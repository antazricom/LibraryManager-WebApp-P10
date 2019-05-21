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
                <c:when test="${loan != null}">
                    <section>

                        <header>

                            <h1>Prêt n° ${loan.id}</h1>

                        </header>

                        <article>

                            <div class="menu">
                                <ul class="menu-list">
                                    <li class="list-content"><a href="${pageContext.request.contextPath}/books/details/${loan.book.id}"><span class="has-text-weight-light"> Livre : </span>${loan.book.title}</a></li>
                                </ul>
                            </div>

                            <div class="menu">
                                <ul class="menu-list">
                                    <li class="list-content add-list-style"><span class="has-text-weight-light"> Date de début : </span>${loan.dateBegin}</li>
                                    <li class="list-content add-list-style"><span class="has-text-weight-light"> Date de fin : </span>${loan.dateEnd}</li>
                                </ul>
                            </div>

                            <div class="menu">
                                <ul class="menu-list">
                                    <li class="list-content add-list-style"><span class="has-text-weight-light"> Date de retour attendu : </span>${loan.dateReturn}</li>
                                    <c:choose>
                                        <c:when test="${loan.returned}">
                                            <li class="list-content add-list-style"><span class="has-text-weight-light"> Rendu : </span><i class="fas fa-check has-text-success"></i></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="list-content add-list-style"><span class="has-text-weight-light"> Rendu : </span><i class="fas fa-times has-text-danger"></i></li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </div>

                        </article>

                    </section>
                </c:when>
                <c:otherwise>
                    <section>

                        <header>

                            <h1>Aucun prêt n'a été trouvé !</h1>

                        </header>

                        <article>

                        </article>

                    </section>
                </c:otherwise>
            </c:choose>

            <section>

                <header>

                    <h1>Étendre le prêt</h1>

                </header>

                <article>

                    <c:choose>
                        <c:when test="${loan.extended == false}">
                            <p><span class="has-text-weight-light"> Le prêt sera étendu d'un mois à partir du :  </span><b>${loan.dateEnd}</b></p>

                            <a href="${pageContext.request.contextPath}/loans/extend/${loan.id}">
                                <button class="button is-primary">Prolonger</button></a>

                        </c:when>
                        <c:otherwise>
                            <button class="button is-danger" disabled>le prêt a déjà été prolongé</button>
                        </c:otherwise>
                    </c:choose>

                </article>

            </section>

            <section>

                <header>

                    <h1>Retourner le livre</h1>

                </header>

                <article>

                    <c:choose>
                        <c:when test="${loan.returned == false}">
                            <a href="${pageContext.request.contextPath}/loans/return/${loan.id}">
                                <button class="button is-light">Rendre le livre</button></a>

                        </c:when>
                        <c:otherwise>
                            <button class="button is-danger" disabled>Le livre a déjà été rendu</button>
                        </c:otherwise>
                    </c:choose>

                </article>

            </section>

        </div>

        <div class="column"></div>

    </div>

</div>

<!-- Load JavaScript file -->
<script src="js/scripts.js"></script>
</body>
</html>