<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="../_include/header.jsp" %>

<body>
<%@include file="../_include/iecondition.jsp" %>

<div id="container">

    <%@include file="../_include/topbar.jsp" %>

<div id="wrapper" class="columns">

    <div id="login" class="column is-one-third is-offset-one-third">

        <section>

            <header>

                <h1>Oups ! Il y a eu une erreur ...</h1>

            </header>

            <article>

                <p>${message}</p>

            </article>

        </section>

    </div>

    <div class="column"></div>

</div>

</div>

</body>
</html>
