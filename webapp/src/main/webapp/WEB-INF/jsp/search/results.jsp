<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="UTF-8" %>
<%@include file="../../_include/header.jsp" %>

<body>
<%@include file="../../_include/iecondition.jsp" %>

<div id="container">

    <%@include file="../../_include/topbar.jsp" %>

    <div id="wrapper" class="columns">

        <%@include file="../../_include/aside_nav.jsp" %>

        <div id="contentSide" class="column is-two-thirds">

            <section>

                <header>

                    <h1>Moteur de recherche</h1>

                </header>

                <article>

                    <form:form action="${pageContext.request.contextPath}/search/results" method="GET" modelAttribute="request">
                        <div class="field has-addons">
                            <div class="control is-expanded has-icons-left">
                                <form:input path="title" class="input is-medium" type="text" name="request" id="request" placeholder="Quel livre recherchez-vous ?" />
                                <span class="icon is-small is-left">
                          <i class="fas fa-search"></i>
                        </span>
                            </div>
                            <div class="control">
                                <form:button class="button is-medium is-dark">
                                    Rechercher
                                </form:button>
                            </div>
                        </div>
                    </form:form>

                </article>

            </section>


            <section>
                <c:choose>
                    <c:when test="${fn:length(results) == 0}">
                        <header>

                            <h1>Aucun résultat</h1>

                        </header>

                        <article>

                        </article>
                    </c:when>
                    <c:otherwise>
                        <header>

                            <h1>Résultats</h1>

                        </header>

                        <article>

                            <div class="menu">
                                <ul class="menu-list">
                                    <c:forEach items="${results}" var="book">
                                        <li class="list-content">
                                            <a href="${pageContext.request.contextPath}/books/details/${book.id}">
                                                    ${book.title} (${book.author.firstname} ${book.author.lastname})</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>

                        </article>
                    </c:otherwise>
                </c:choose>
            </section>

        </div>

        <div class="column"></div>

    </div>

</div>
</body>
</html>
