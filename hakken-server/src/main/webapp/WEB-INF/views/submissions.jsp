<jsp:root version="2.0"
           xmlns:jsp="http://java.sun.com/JSP/Page"
           xmlns:c="http://java.sun.com/jsp/jstl/core"
           xmlns:h="urn:jsptagdir:/WEB-INF/tags/"
           xmlns="http://www.w3.org/1999/xhtml">

    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
    <h:page section="admin" title="Submissions" intro="All submissions made by all users of hakken">
        <div class="holder_content">
            <section class="group4">
                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>User</th>
                            <th>Job Id</th>
                            <th>Task</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
	                <c:forEach var="submission" items="${submissions}">
	                    <tr>
	                        <td>${submission.id}</td>
	                        <td>${submission.username}</td>
	                        <td>${submission.jobId}</td>
	                        <td>${submission.taskDefinitionName}</td>
	                        <td><a href="${submission.id}/resubmit">Resubmit</a></td>
	                        <td><a href="${submission.id}/delete">Delete</a></td>
	                    </tr>
	                </c:forEach>
                    </tbody>
                </table>
            </section>
        </div>
    </h:page>
</jsp:root>