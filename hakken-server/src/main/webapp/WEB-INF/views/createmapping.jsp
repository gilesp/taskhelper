<jsp:root version="2.0"
           xmlns:jsp="http://java.sun.com/JSP/Page"
           xmlns:c="http://java.sun.com/jsp/jstl/core"
           xmlns:h="urn:jsptagdir:/WEB-INF/tags/"
           xmlns="http://www.w3.org/1999/xhtml">

    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
    <h:page section="admin" title="Create mapping" intro="Create a mapping between Task ${taskDefinition.name} and ${dcTaskDefMappingName}">
        <div class="holder_content">
            <section class="group4">
                <table>
                    <thead>
                        <td>Task Page Item</td>
                        <td>DataConnector Task Definition data item</td>
                    </thead>
                    <tbody>
                    <form name="createMapping" action="/hakken/admin/mapping/save" method="POST">
                        <input type="hidden" name="taskName" value="${taskDefinition.name}"/>
                        <input type="hidden" name="dcTaskDefMappingId" value="${dcTaskDefMappingId}"/>
                    <c:forEach var="page" items="${taskDefinition.pages}" varStatus="status">
                        <c:forEach var="pi" items="${page.items}">
                            <!--  skip labels as we don't need to map them to anything -->
	                        <c:if test="${not (pi.type eq 'LABEL')}">
	                            <tr>
	                                <td>${page.name} - ${pi.name }</td>
	                                <td>
	                                    <select name="${page.name}@@${pi.name }" class="mapping">
	                                        <option value="" >--none--</option>
	                                        <c:forEach var="diName" items="${dcTaskDefinition.dataItemNames}" varStatus="status">
	                                           <c:choose>
	                                               <c:when test="${diName eq pi.name}">
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
                        <input type="submit" value="Create New Mapping"/>
                    </form>
                    </tbody>
                </table>
            </section>
        </div>          
    </h:page>     
</jsp:root>