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

                    <div class="columns">
                        <div class="column is-two-thirds">
                            <h1>Gestion des membres</h1>
                        </div>
                        <div class="column">
                            <a class="button-on-title button is-dark" href="${pageContext.request.contextPath}/admin/members/add">
                                Créer un nouveau membre
                            </a>
                        </div>
                    </div>

                </header>

                <article>
                    <div class="menu">
                        <ul class="menu-list">
                            <c:forEach var="member" items="${members}">
                                <li class="list-content">
                                    <div class="list-with-border columns">
                                        <div class="column is-three-fifths">
                                            <a disabled="disabled">
                                                ${member.lastname}, ${member.firstname} (${member.login})
                                            </a>
                                        </div>
                                        <div class="column is-one-fifth">
                                            <a href="${pageContext.request.contextPath}/admin/members/update/${member.id}">
                                                <i class="far fa-edit"></i> &nbsp; Modifier
                                            </a>
                                        </div>
                                    </div>
                                </li>
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
