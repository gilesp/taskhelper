<jsp:root version="2.0"
           xmlns:jsp="http://java.sun.com/JSP/Page"
           xmlns:c="http://java.sun.com/jsp/jstl/core"
           xmlns:h="urn:jsptagdir:/WEB-INF/tags/"
           xmlns="http://www.w3.org/1999/xhtml">

    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
    <h:page section="admin" title="Create mapping" intro="Create a mapping between Task ${taskDefinition.name} and DataConnector ${dataConnector}">
        <div class="holder_content">
            <section class="group4">
                <table>
                    <thead>
                        <td>Task Page Item</td>
                        <td>DataConnector data item</td>
                    </thead>
                    <tbody>
                    <form name="createMapping" action="/hakken/admin/mapping/save" method="POST">
                        <input type="hidden" name="taskName" value="${taskDefinition.name}"/>
                        <input type="hidden" name="connectorName" value="${dataConnector.name}"/>
                    <c:forEach var="page" items="${taskDefinition.pages}" varStatus="status">
                        <c:forEach var="pi" items="${page.items}">
                            <tr>
                                <td>${page.name} - ${pi.name }</td>
                                <td>
                                    <select name="${page.name}_${pi.name }" class="mapping">
                                        <option value="" >--none--</option>
                                        <c:forEach var="field" items="${dataConnector.dataItems}" varStatus="status">
                                            <option value="${field}">${field}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
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