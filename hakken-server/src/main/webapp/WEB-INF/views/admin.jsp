<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
    <title>Task -Admin - Hakken</title>
    <link rel="stylesheet" href="http://yui.yahooapis.com/2.7.0/build/reset-fonts-grids/reset-fonts-grids.css" type="text/css">
    <link rel="stylesheet" href="../resources/css/yuiapp.css" type="text/css">
    <link id="theme" rel="stylesheet" href="../resources/css/hakken.css" type="text/css">
<!--     <script src="http://yui.yahooapis.com/3.5.0/build/yui/yui-min.js"></script> -->
</head>
<body>
    <div id="doc3" class="yui-t6">
        <div id="hd">
            <h1>Hakken - admin - ${task.name}</h1>
            <div id="navigation">
                <ul id="primary-navigation">
                    <li><a href="/hakken/">Home</a></li>
                    <li><a href="/hakken/admin/">Admin</a></li>
                </ul>
                <ul id="user-navigation">
                    <li><a href="login">Login</a></li>
                </ul>
                <div class="clear"></div>
            </div>
        </div>
        
        <div id="bd">
            <div id="yui-main">
                <div class="yui-b">
                    <div class="yui-g">
                        
                        <!--  Task Definitions -->
                        <div class="block">
                            <div class="hd">
                                <h2>Task Definitions</h2>
                            </div>
                            <div class="bd">
                                <h3>Available task definitions</h3>
                                <c:choose>
                                    <c:when test="${empty taskDefinitions}">
                                        <p>No task definitions have been loaded</p>
                                    </c:when>
                                    <c:otherwise>
                                        <ul>
                                            <c:forEach var="task" items="${taskDefinitions}" varStatus="status" >
                                                <li>${status.count}) <a href="/hakken/admin/task/${task.name}">${task.name}</a> - ${task.description}</li>
                                            </c:forEach>
                                        </ul>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        
                        <!-- Data Connectors -->
                        <div class="block">
                            <div class="hd">
                                <h2>Data Connectors</h2>
                            </div>
                            <div class="bd">
                                <h3>Available data connectors</h3>
                                <c:choose>
                                    <c:when test="${empty dataConnectors}">
                                        <p>No data connectors have been defined</p>
                                    </c:when>
                                    <c:otherwise>
                                        <ul>
                                            <c:forEach var="task" items="${dataConnectors}" varStatus="status" >
                                                <li>${status.count})</li>
                                            </c:forEach>
                                        </ul>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="sidebar" class="yui-b">
	            <div class="block">
	                <div class="hd">
	                    <h2>Tools</h2>
	                </div>
	                <div class="bd">
	                    <ul>
	                       <li><a href="reloadTasks">Reload Task Definitions</a></li>
	                    </ul>
	                </div>
	            </div>
	        </div>
        </div>
    </div>
</body>
</html>
