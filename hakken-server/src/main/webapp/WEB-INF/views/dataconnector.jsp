<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>
<title>Admin - Hakken</title>
<link rel="stylesheet"
	href="http://yui.yahooapis.com/2.7.0/build/reset-fonts-grids/reset-fonts-grids.css"
	type="text/css">
<link rel="stylesheet" href="../../resources/css/yuiapp.css"
	type="text/css">
<link id="theme" rel="stylesheet" href="../../resources/css/hakken.css"
	type="text/css">
<!--     <script src="http://yui.yahooapis.com/3.5.0/build/yui/yui-min.js"></script> -->
</head>
<body>
	<div id="doc3" class="yui-t6">
		<div id="hd">
			<h1>Hakken - admin</h1>
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

						<div class="block">
							<div class="hd">
								<h2>${dataConnector}</h2>
							</div>
							<div class="bd">
								<p>${dataConnector.info}
							</div>
						</div>
						<div class="block">
                            <div class="hd">
                                <h2>Properties</h2>
                            </div>
                            <div class="bd">
                                <ul>
                                <c:forEach var="field" items="${dataConnector.dataItems}" varStatus="status">
                                    <ul>${status.count}) ${field}</ul>
                                </c:forEach>
                                </ul>
                            </div>
                        </div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
