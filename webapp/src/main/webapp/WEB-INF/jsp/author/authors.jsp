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

                    <h1>Liste des auteurs</h1>

                </header>

                <article>

                    <div class="menu">
                        <ul class="menu-list">
                            <c:forEach items="${authors}" var="author">
                                <li class="list-content list-with-border"><a href="./details/${author.id}">${author.firstname} ${author.lastname}</a></li>
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
