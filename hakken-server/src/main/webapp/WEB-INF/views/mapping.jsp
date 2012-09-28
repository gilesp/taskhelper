<jsp:root version="2.0"
           xmlns:jsp="http://java.sun.com/JSP/Page"
           xmlns:c="http://java.sun.com/jsp/jstl/core"
           xmlns:h="urn:jsptagdir:/WEB-INF/tags/"
           xmlns="http://www.w3.org/1999/xhtml">

    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
    <h:page section="admin" title="Mapping details" intro="${mapping.id} - Task: ${mapping.taskDefinitionName} - Connector Task Definition: ${mapping.dataConnectorTaskDefinitionMapping}">
        <div class="holder_content">
            <section class="group4">
                <table>
                    <thead>
                        <td>Task Page Item</td>
                        <td>DataConnector Task Definition data item</td>
                    </thead>
                    <tbody>
                    <form name="updateMapping" action="/hakken/admin/mapping/update" method="POST">
                        <input type="hidden" name="mappingId" value="${mapping.id}"/>
                    <c:forEach var="page" items="${taskDefinition.pages}" varStatus="status">
                        <c:forEach var="pi" items="${page.items}">
                            <!--  skip labels as we don't need to map them to anything -->
                            <c:if test="${not (pi.type eq 'LABEL')}">
                                <tr>
		                            <td>${page.name} - ${pi.name }</td>
		                            <td>
                                        <c:set var="key" value="${page.name}@@${pi.name }"/>
		                                <select name="${key}" class="mapping">
		                                    <option value="" >--none--</option>
		                                    <c:forEach var="diName" items="${dcTaskDefinition.dataItemNames}" varStatus="status">
		                                        <c:choose>
		                                        
		                                            <c:when test="${diName eq mapping.taskToConnectorMappings[key]}">
		                                                <option value="${diName}" selected="selected">${diName}</option>
		                                            </c:when>
		                                            <c:otherwise>
		                                                <option value="${diName}">${diName}</option>
		                                            </c:otherwise>
		                                        </c:choose>
		                                     </c:forEach>
		                                 </select>
		                            </td>
		                        </tr>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                    <c:forEach var="entry" items="${mapping.connectorToTaskMappings}" varStatus="status" >
                        
                    </c:forEach>
                        <input type="submit" value="Update Mapping"/>
                    </form>
                    </tbody>
                </table>
            </section>
        </div>          
    </h:page>     
</jsp:root>