<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
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
<section>
    <c:set var="action" scope="request" value="${param.action}"/>
    <c:choose>
        <c:when
                test="${action eq 'add'}"><h2>Add resume</h2>
        </c:when>
        <c:otherwise>
            <h2>Edit resume</h2>
        </c:otherwise>
    </c:choose>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded" id="editOrAdd">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="action" value=${action}>
        <dl>
            <dt>Name:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>

        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>

        <dl>
            <c:forEach var="contentType" items="<%=SectionType.values()%>">
                <c:choose>

                    <c:when test="${contentType eq 'PERSONAL' || contentType eq 'OBJECTIVE'}">
                        <h4>${contentType.name()}</h4>
                        <c:set var="textContent" value="${resume.getSection(contentType)}" scope="request"/>
                        <c:if test="${not empty textContent}">
                            <jsp:useBean id="textContent" class="ru.javawebinar.basejava.model.TextSection"/>
                            <dd><textarea name='${contentType.name()}' cols=75 rows=5
                                          form="editOrAdd">${textContent.description}</textarea>
                            </dd>
                        </c:if>
                        <c:if test="${empty textContent}">
                            <dd><textarea name='${contentType.name()}' cols=75 rows=5 form="editOrAdd"></textarea></dd>
                        </c:if>
                    </c:when>

                    <c:when test="${contentType eq 'ACHIEVEMENT' || contentType eq 'QUALIFICATIONS'}">
                        <c:set var="listContent" value="${resume.getSection(contentType)}" scope="page"/>
                        <h4>${contentType.name()}</h4>
                        <c:if test="${not empty listContent}">
                            <jsp:useBean id="listContent"
                                         class="ru.javawebinar.basejava.model.ListSection"/>
                            <dd><textarea name='${contentType.name()}' cols=75 rows=5 form="editOrAdd"><c:forEach
                                    var="items"
                                    items="${listContent.descriptions}">${items}</c:forEach></textarea>
                            </dd>
                        </c:if>
                        <c:if test="${empty listContent}">
                            <dd><textarea name='${contentType.name()}' cols=75 rows=5 form="editOrAdd"></textarea></dd>
                        </c:if>
                    </c:when>

                    <c:when test="${contentType eq 'EXPERIENCE' || contentType eq 'EDUCATION'}">
                        <h4>${contentType.name()} </h4>
                        <p>
                        <c:set var="organizationContent" scope="request" value="${resume.sections.get(contentType)}"/>
                        <jsp:useBean id="organizationContent" scope="request"
                                     class="ru.javawebinar.basejava.model.ExperienceSection"/>
                        <c:forEach var="organization" items="${organizationContent.experiencesList}"
                                   varStatus="counter">
                            <dl>
                                <dt>Name</dt>
                                <dd><input type="text" name="${contentType.name()}" size=30
                                           value="${organization.homePage.name}" required></dd>
                            </dl>
                            <dl>
                                <dt>URL</dt>
                                <dd><input type="text" name="${contentType.name()}${counter.index}url" size=30
                                           value="${organization.homePage.url}"></dd>
                            </dl>
                            <br>
                            <c:forEach var="position" items="${organization.positions}">
                                <jsp:useBean id="position"
                                             type="ru.javawebinar.basejava.model.Experience.ExperienceList"/>
                                <p>
                                <dl>
                                    <dt>Position</dt>
                                    <dd><input type="text" name="${contentType.name()}${counter.index}pos" size=30
                                               value="${position.description}"
                                               required></dd>
                                </dl>
                                <dl>
                                    <dt>Start</dt>
                                    <dd><input type="text" required name="${contentType.name()}${counter.index}start"
                                               size=30
                                               value="<%=DateUtil.format(position.getDataFrom())%>"
                                               placeholder="MM/yyyy"></dd>
                                </dl>
                                <dl>
                                    <dt>End</dt>
                                    <c:choose>
                                        <c:when test="<%=position.getDataTo()!=null && position.getDataTo().isEqual(DateUtil.NOW)%>">
                                            <dd><input type="text" name="${contentType.name()}${counter.index}end"
                                                       size=30
                                                       value=""
                                                       placeholder="MM/yyyy"></dd>
                                        </c:when>
                                        <c:otherwise>
                                            <dd><input type="text" name="${contentType.name()}${counter.index}end"
                                                       size=30
                                                       value="<%=DateUtil.format(position.getDataTo())%>"
                                                       placeholder="MM/yyyy"></dd>
                                        </c:otherwise>
                                    </c:choose>
                                </dl>
                                <dl>
                                    <dt>Description</dt>
                                    <dd><textarea name='${contentType.name()}${counter.index}des' cols=75 rows=5
                                                  form="editOrAdd">${position.description}</textarea>
                                </dl>
                            </c:forEach>
                            <br>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>
        </dl>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

