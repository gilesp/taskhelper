<jsp:root version="2.0"
           xmlns:jsp="http://java.sun.com/JSP/Page"
           xmlns:c="http://java.sun.com/jsp/jstl/core"
           xmlns:h="urn:jsptagdir:/WEB-INF/tags/"
           xmlns="http://www.w3.org/1999/xhtml">

    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
    <h:page section="admin" title="Mapping details" intro="${mapping.id} - Task: ${mapping.taskDefinitionName} - Connector Task Definition: ${mapping.dataConnectorTaskDefinitionMapping}">
        <div class="holder_content">
            <section class="group1">
                <h3>Mappings</h3>
                <p>
                <ul>
                <c:forEach var="entry" items="${mapping.connectorToTaskMappings}" varStatus="status" >
                    <li>${entry.key} to ${entry.value}</li>
                </c:forEach>
                </ul>
                </p>
            </section>
        </div>          
    </h:page>     
</jsp:root>