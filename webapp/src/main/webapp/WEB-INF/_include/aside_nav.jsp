<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside id="navSide" class="column is-one-fifth">

    <header>

        <span id="username">${member.login}</span><br />
        <span id="usersubtitle"><a href="${pageContext.request.contextPath}/auth/logout">Se déconnecter</a></span>

    </header>

    <nav>

        <li><a href="${pageContext.request.contextPath}/loans"><i class="far fa-calendar-alt"></i> &nbsp;Mes emprunts</a></li>
        <li><a href="${pageContext.request.contextPath}/books"><i class="fas fa-book"></i> &nbsp;Livres</a></li>
        <li><a href="${pageContext.request.contextPath}/authors"><i class="fas fa-user"></i> &nbsp;Auteurs</a></li>
        <li><a href="${pageContext.request.contextPath}/categories"><i class="fas fa-stream"></i> &nbsp;Catégories</a></li>
        <li><a href="${pageContext.request.contextPath}/search"><i class="fas fa-search"></i> &nbsp;Rechercher</a></li>

    </nav>

    <footer>

        <p>
            LibraryManager: Gestion de prêt de livre <br />
            Openclassrooms P7 @ <a href="https://openclassrooms.com/fr/paths/88-developpeur-dapplication-java">Parcours Développeur d'Application Java/JEE</a> -
            <a href="http://www.antazri.xyz" target="_blank">Anthony Tazzari</a>
        </p>

    </footer>

</aside>
