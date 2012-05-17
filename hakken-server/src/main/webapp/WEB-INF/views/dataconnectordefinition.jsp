<jsp:root version="2.0"
           xmlns:jsp="http://java.sun.com/JSP/Page"
           xmlns:c="http://java.sun.com/jsp/jstl/core"
           xmlns:h="urn:jsptagdir:/WEB-INF/tags/"
           xmlns="http://www.w3.org/1999/xhtml">

    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
    <h:page section="admin" title="${definition.name}" intro="">
        <div class="holder_content">
            <section class="group1">
                <h3>Available Dataitems</h3>
                <ol>
                <c:forEach var="dataitem" items="${definition.dataItemNames}" varStatus="status">
                    <li>${dataitem}</li>
                </c:forEach>
                </ol>
            </section>
            <section class="group3">
                <h3>
            </section>
        </div>
        <div class="holder_content">
             <!-- 
            <section class="group2">
                <h3>Mappings</h3>
                <p>
                <form name="newMapping" action="/hakken/admin/mapping/new"  method="POST">
                    <input type="hidden" name="connectorName" value="${connectorName}"/>
                    <input type="hidden" name="definitionName" value="${definition.name}"/>
                    <select id="taskName" name="taskName">
                    <c:forEach var="task" items="${taskDefinitions}">
                        <option value="${task.name}" >${task.name}</option>
                    </c:forEach>
                    </select>
                    <input type="submit" value="Create New Mapping"/>
                </form>
                </p>
            </section>
             -->
        </div>          
    </h:page>     
</jsp:root>