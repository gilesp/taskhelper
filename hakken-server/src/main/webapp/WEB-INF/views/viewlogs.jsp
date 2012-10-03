<jsp:root version="2.0"
           xmlns:jsp="http://java.sun.com/JSP/Page"
           xmlns:c="http://java.sun.com/jsp/jstl/core"
           xmlns:h="urn:jsptagdir:/WEB-INF/tags/"
           xmlns="http://www.w3.org/1999/xhtml">

    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
    <h:page section="admin" title="Logs" intro="All log entries">
        <div class="holder_content">
            <section class="group4">
                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Timestamp</th>
                            <th>Username</th>
                            <th>Message</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="log" items="${logs}">
                        <tr>
                            <td>${log.id}</td>
                            <td>${log.timestamp}</td>
                            <td>${log.username}</td>
                            <td>${log.message}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </section>
        </div>
    </h:page>
</jsp:root>