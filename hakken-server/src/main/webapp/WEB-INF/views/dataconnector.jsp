<jsp:root version="2.0"
           xmlns:jsp="http://java.sun.com/JSP/Page"
           xmlns:c="http://java.sun.com/jsp/jstl/core"
           xmlns:h="urn:jsptagdir:/WEB-INF/tags/"
           xmlns="http://www.w3.org/1999/xhtml">

    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
    <h:page section="admin" title="${connectorName}" intro="${dataConnector.info}">
        <div class="holder_content">
            <section class="group4">
                <h3>Available Definitions</h3>
                <form name="newMapping" action="/hakken/admin/dataconnector/newmapping" method="POST">
                    <input type="hidden" name="connectorName" value="${connectorName}"/>
                    <select id="definitionName" name="definitionName">
                    <c:forEach var="definition" items="${dataConnector.definitions}" varStatus="status">
                        <option value="${definition.name}">${definition.name}</option>
                    </c:forEach>
                    </select>
                    <input type="submit" value="Create New Mapping"/>
                </form>
                <ol>
                </ol>
            </section>
        </div>          
    </h:page>     
</jsp:root>