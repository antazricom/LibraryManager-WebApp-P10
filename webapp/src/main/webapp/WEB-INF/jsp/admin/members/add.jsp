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

                    <h1>Ajouter un membre</h1>

                </header>

                <article>

                    <form:form action="${pageContext.request.contextPath}/admin/members/adding" method="POST" modelAttribute="newmember">
                        <div class="field">
                            <form:label path="lastname" class="label">Nom :</form:label>
                            <div class="control">
                                <form:input id="lastname" name="lastname" path="lastname" class="input" type="text" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="firstname" class="label">Prénom :</form:label>
                            <div class="control">
                                <form:input id="firstname" name="firstname" path="firstname" class="input" type="text" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="login" class="label">Login :</form:label>
                            <div class="control">
                                <form:input id="login" name="login" path="login" class="input" type="text" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="email" class="label">Email :</form:label>
                            <div class="control">
                                <form:input id="email" name="email" path="email" class="input" type="email" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="phone" class="label">Téléphone :</form:label>
                            <div class="control">
                                <form:input id="phone" name="phone" path="phone" class="input" type="text" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="password" class="label">Mot de passe :</form:label>
                            <div class="control">
                                <form:input id="password" name="password" path="password" class="input" type="password" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="status" class="label">Statut :</form:label>
                            <div class="control">
                                <div class="select">
                                    <form:select id="status" name="status" path="status" required="required">
                                        <option value="member">Membre</option>
                                        <option value="admin">Administrateur</option>
                                    </form:select>
                                </div>
                            </div>
                        </div>

                        <div class="field">
                            <div class="control">
                                <form:button class="button is-dark">Ajouter</form:button>
                            </div>
                        </div>
                    </form:form>

                    <a href="${pageContext.request.contextPath}/admin/members">
                        <button class="button is-danger button-cancel">Annuler</button>
                    </a>

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
