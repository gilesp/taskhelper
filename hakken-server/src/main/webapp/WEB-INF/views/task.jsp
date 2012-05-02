<jsp:root version="2.0"
           xmlns:jsp="http://java.sun.com/JSP/Page"
           xmlns:c="http://java.sun.com/jsp/jstl/core"
           xmlns:h="urn:jsptagdir:/WEB-INF/tags/"
           xmlns="http://www.w3.org/1999/xhtml">

    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
    <h:page section="admin" title="${task.name}" intro="${task.description}">
        <div class="holder_content">
            <section class="group4">
                <c:choose>
                    <c:when test="${empty task.pages}">
                        <p>No pages defined</p>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="page" items="${task.pages}" varStatus="status">
                            <h4>Page - ${page.name}</h4>
                            <ul>
                                <c:forEach var="item" items="${page.items}">
                                    <li>Item - ${item.name} - ${item.label} [${item.type}]</li>
                                </c:forEach>
                            </ul>
                            <ul class="selectors">
                                <c:forEach var="selector" items="${page.nextPages}">
                                    <li>Next Page = ${selector.pageName}, condition=${selector.condition}</li>
                                </c:forEach>
                            </ul>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
	        </section>
        </div>          
    </h:page>     
</jsp:root>
