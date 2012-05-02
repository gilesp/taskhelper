<jsp:root version="2.0"
           xmlns:jsp="http://java.sun.com/JSP/Page"
           xmlns:c="http://java.sun.com/jsp/jstl/core"
           xmlns:h="urn:jsptagdir:/WEB-INF/tags/"
           xmlns="http://www.w3.org/1999/xhtml">

    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
    <h:page section="admin" title="Admin" intro="This is where you configure and monitor the server from.">
        <div class="holder_content">
            <section class="group1">
                <h3>Task Definitions</h3>
                <c:choose>
                    <c:when test="${empty taskDefinitions}">
                        <p>No task definitions have been loaded</p>
                    </c:when>
                    <c:otherwise>
                        <ul>
                            <c:forEach var="task" items="${taskDefinitions}" varStatus="status" >
                                <li>${status.count}) <a href="/hakken/admin/task/${task.name}">${task.name}</a> - ${task.description}</li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </section>
            <section class="group2">
                <h3>Data Connectors</h3>
                <c:choose>
                    <c:when test="${empty dataConnectors}">
                        <p>No data connectors have been defined</p>
                    </c:when>
                    <c:otherwise>
                        <ul>
                            <c:forEach var="entry" items="${dataConnectors}" varStatus="status" >
                                <li><a href="/hakken/admin/dataconnector/${entry.key}">${entry.key}</a> ${entry.value.info}</li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </section>
            <section class="group3">
                <h3>Mappings</h3>
                <c:choose>
                    <c:when test="${empty mappings}">
                        <p>No mappings have been defined</p>
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
            </section>
        </div>
        <div class="holder_content">
            <section class="group1">
                <h3>Tools</h3>
                <ul>
                   <li><a href="reloadTasks">Reload Task Definitions</a></li>
                </ul>
            </section>
        </div>
    </h:page>               
</jsp:root> 