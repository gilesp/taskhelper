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
                    <c:forEach var="page" items="${taskDefinition.pages}" varStatus="status">
                        <c:forEach var="pi" items="${page.items}">
                            <tr>
                                <td>${page.name} - ${pi.name }</td>
                                <td>
                                    <select>
                                        <c:forEach var="field" items="${dataConnector.dataItems}" varStatus="status">
                                            <option>${field}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                    </tbody>
                </table>
            </section>
        </div>          
    </h:page>     
</jsp:root>