<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div id="contact">
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
</div>

<div id="section">
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
            <c:set var="section" value="${sectionEntry.key}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.SectionType"/>

        <c:choose>
        <c:when test="${section eq 'PERSONAL' or section eq 'OBJECTIVE'}">
            <c:set var="textContent" value="${sectionEntry.value}"/>
            <jsp:useBean id="textContent" type="ru.javawebinar.basejava.model.TextSection"/>

    <h2>${section}</h2>
    <p class="text">${textContent.description}</p>

    </c:when>
    </c:choose>

    <c:choose>
        <c:when test="${section eq 'ACHIEVEMENT' or section eq 'QUALIFICATIONS'}">
            <c:set var="listContent" value="${sectionEntry.value}"/>
            <jsp:useBean id="listContent" type="ru.javawebinar.basejava.model.ListSection"/>
            <h2>${section}</h2>
            <ul><c:forEach var="str" items="${listContent.descriptions}">
                <li>${str}</li>
            </c:forEach>
            </ul>
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${section eq 'EXPERIENCE' or section eq 'EDUCATION'}">
            <c:set var="organizationContent" value="${sectionEntry.value}"/>
            <jsp:useBean id="organizationContent" type="ru.javawebinar.basejava.model.ExperienceSection"/>
            <h2>${section}</h2>
            <c:forEach var="org" items="${organizationContent.experiencesList}">
                <c:choose>
                    <c:when test="${empty org.homePage.url}">
                        <p class="co"> ${org.homePage.name}</p>
                    </c:when>
                    <c:otherwise>
                        <p class="co"><a href="${org.homePage.url}">${org.homePage.name}</a></p>
                    </c:otherwise>

                </c:choose>

                <c:forEach var="pos" items="${org.positions}">
                    <jsp:useBean id="pos" type="ru.javawebinar.basejava.model.Experience.ExperienceList"/>
                    <p class="date"><%=DateUtil.toHtml(pos.getDataFrom(), pos.getDataTo())%>
                    </p>
                    <p class="pos"><b>${pos.position}</b></p>
                    <p class="des">${pos.description}</p>
                </c:forEach>


            </c:forEach>
        </c:when>
    </c:choose>

    </c:forEach>

</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>