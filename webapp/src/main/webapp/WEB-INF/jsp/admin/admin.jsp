<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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

                    <h1>Interface d'administration</h1>

                </header>

                <article>
                    <nav class="admin-nav">
                        <li>
                            <a href="${pageContext.request.contextPath}/admin/members">
                                <button class="button admin-nav-btn is-primary is-fullwidth is-large">Gestion des membres</button>
                            </a>
                        </li>
                    </nav>
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
