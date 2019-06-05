<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                                <form:input path="title" class="input is-medium" type="text" name="request" id="request" placeholder="Quel livre recherchez-vous ?" required="required" />
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

        </div>

        <div class="column"></div>

    </div>

</div>
</body>
</html>
