<jsp:root version="2.0"
           xmlns:jsp="http://java.sun.com/JSP/Page"
           xmlns:c="http://java.sun.com/jsp/jstl/core"
           xmlns:h="urn:jsptagdir:/WEB-INF/tags/"
           xmlns="http://www.w3.org/1999/xhtml">

    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
    <h:page section="admin" title="${task.name}" intro="${task.description}">
        <div class="holder_content">
            <section class="group4">
                <pre class="prettyprint"><code class="language-js">${taskAsJson}</code></pre>
	        </section>
        </div>          
        <div class="holder_content">
            <section class="group1">
                <h3>Create Mapping</h3>
                <c:choose>
	                <c:when test="${empty dcDefinitionMappings}">
	                    <p>No DataConnector instances are defined.</p>
	                </c:when>
	                <c:otherwise>
	                    <form name="createMapping" action="/hakken/admin/mapping/new" method="POST">
	                        <input type="hidden" name="taskName" value="${task.name}"/>
	                        <select name="dcTaskDefMappingId">
	                        <c:forEach var="instance" items="${dcDefinitionMappings}">
	                            <option value="${instance.id}">${instance.dataConnectorName} - ${instance.taskDefinitionName}</option>
	                        </c:forEach>
	                        </select>
	                        <input type="submit" value="Create Mapping"/>
	                    </form>
	                </c:otherwise>
                </c:choose>
            </section>
        </div>
    </h:page>     
</jsp:root>
