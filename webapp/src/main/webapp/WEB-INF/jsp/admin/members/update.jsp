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
                        <div class="column is-four-fifths">
                            <h1>Mettre à jour le profil de : <br />
                                <span class="has-text-ocre">${member.lastname} ${member.firstname}</span></h1>
                        </div>

                        <div class="column is-one-fifth">
                            <a href="${pageContext.request.contextPath}/admin/members/delete/${member.id}">
                                <button class="button is-warning button-on-title">Supprimer le membre</button>
                            </a>
                        </div>
                    </div>

                </header>

                <article>

                    <form:form action="${pageContext.request.contextPath}/admin/members/updating" method="POST" modelAttribute="member">
                        <form:hidden path="id" id="${member.id}" />
                        <form:hidden path="password" id="${member.password}" />

                        <div class="field">
                            <form:label path="lastname" class="label">Nom :</form:label>
                            <div class="control">
                                <form:input id="lastname" name="lastname" path="lastname" class="input" type="text" value="${member.lastname}" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="firstname" class="label">Prénom :</form:label>
                            <div class="control">
                                <form:input id="firstname" name="firstname" path="firstname" class="input" type="text" value="${member.firstname}" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="login" class="label">Login :</form:label>
                            <div class="control">
                                <form:input id="login" name="login" path="login" class="input" type="text" value="${member.login}" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="email" class="label">Email :</form:label>
                            <div class="control">
                                <form:input id="email" name="email" path="email" class="input" type="email" value="${member.email}" required="required" />
                            </div>
                        </div>

                        <div class="field">
                            <form:label path="phone" class="label">Téléphone :</form:label>
                            <div class="control">
                                <form:input id="phone" name="phone" path="phone" class="input" type="text" value="${member.phone}" required="required" />
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
                                <form:button class="button is-dark">Mettre à jour</form:button>
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
