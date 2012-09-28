<jsp:root version="2.0"
           xmlns:jsp="http://java.sun.com/JSP/Page"
           xmlns:c="http://java.sun.com/jsp/jstl/core"
           xmlns:h="urn:jsptagdir:/WEB-INF/tags/"
           xmlns="http://www.w3.org/1999/xhtml">

    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
    <h:page section="admin" title="Create instance" intro="Create a DataConnectorTaskDefinition instance for ${dataConnector.name} ${definition.name}">
        <div class="holder_content">
            <section class="group4">
                <h3>Properties</h3>
                    <form name="createMapping" action="/hakken/admin/dataconnector/savemapping" method="POST">
                        <input type="hidden" name="connectorName" value="${dataConnector.name}"/>
                        <input type="hidden" name="definitionName" value="${definition.name}"/>

                    <table>                        
                    <c:forEach var="property" items="${definition.configProperties}" varStatus="status">
                            <tr>
                                <td>${property.name}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${property.dataItem}">
                                            <select name="${property.name}" class="mapping">
		                                        <option value="" >--none--</option>
		                                        <c:forEach var="diName" items="${definition.dataItemNames}" varStatus="status">
		                                            <option value="${diName}">${diName}</option>
		                                        </c:forEach>
		                                    </select>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" name="${property.name}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                    </c:forEach>
                    </table>
                        <input type="submit" value="Create Instance"/>
                    </form>
            </section>
        </div>          
    </h:page>     
</jsp:root>