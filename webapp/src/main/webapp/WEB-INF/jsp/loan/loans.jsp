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

            <section>

                <header>

                    <h1>Liste de mes prêts</h1>

                </header>

                <article>

                    <div class="menu">
                        <ul class="menu-list">
                            <c:forEach items="${loans}" var="loan">
                                <c:choose>
                                    <c:when test="${loan.returned == false}">
                                        <li class="list-content"><a href="./details/${loan.id}">${loan.book.title}<br />
                                            Date de fin : ${loan.dateEnd.getDay()}/${loan.dateEnd.getMonth()}/${loan.dateEnd.getYear()}<br />
                                            <c:choose>
                                                <c:when test="${loan.returned}">
                                                    Rendu : <i class="fas fa-check has-text-success"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    Rendu : <i class="fas fa-times has-text-danger"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </a></li>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </div>

                </article>

            </section>

            <section>

                <header>

                    <h1>Historique des prêts</h1>

                </header>

                <article>

                    <div class="menu">
                        <ul class="menu-list">
                            <c:forEach items="${loans}" var="loan">
                                <c:choose>
                                    <c:when test="${loan.returned == true}">
                                        <li class="list-content"><a href="./details/${loan.id}">${loan.book.title}<br />
                                            Date de fin : ${loan.dateEnd.getDay()}/${loan.dateEnd.getMonth()}/${loan.dateEnd.getYear()}<br />
                                            <c:choose>
                                                <c:when test="${loan.returned}">
                                                    Rendu : <i class="fas fa-check has-text-success"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    Rendu : <i class="fas fa-times has-text-danger"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </a></li>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </div>

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
