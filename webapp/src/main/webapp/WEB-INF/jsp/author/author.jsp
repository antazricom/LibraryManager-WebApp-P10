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
                <c:when test="${author != null}">
                    <section>

                        <header>

                            <h1>Auteur : ${author.firstname} ${author.lastname}</h1>

                        </header>

                        <article>

                            <div class="menu">
                                <ul class="menu-list">
                                    <c:forEach items="${books}" var="book">
                                        <li class="list-content"><a href="${pageContext.request.contextPath}/books/details/${book.id}">${book.title} (${book.publicationDate})</a></li>
                                    </c:forEach>
                                </ul>
                            </div>

                        </article>

                    </section>
                </c:when>
                <c:otherwise>
                    <section>

                        <header>

                            <h1>Aucun auteur n'a été trouvé !</h1>

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
<script src="js/scripts.js"></script>
</body>
</html>
