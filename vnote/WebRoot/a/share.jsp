<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String strid = request.getParameter("id");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="shortcut icon" href="a/favicon.ico">

<base href="<%=basePath%>">

<title>vnote</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<!-- 加载js和css -->
<script type="text/javascript" src="<%=basePath%>a/js/jquery-1.9.1.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>a/css/vnotecss.css">

</head>
<body id="body">
	<div id="editstatus"></div>
	<div id="tip"></div>
	<input type="hidden" id="myid" value="${param.id}">
	<br />

	<div class="stack ">
		<div class="layer_1">
			<div class="layer_2">
				<div id="layer3" class="layer_3">
					<textarea id="txt" class="contents " spellcheck="true"
						readonly="readonly"></textarea>
				</div>
			</div>
		</div>
	</div>
	<br />
	<div id="controls">

		<a href="javascript:AddFavorite()">收藏${param.id}页面</a> <a
			href="javascript:if($('#help').css('display')=='none') $('#help').show();else $('#help').hide()">帮助与关于</a>
	</div>

	<div class="stack " style="display: none;" id="help">
		<div class="layer_1">
			<div class="layer_2">
				<div class="layer_3">
					<textarea class="contents ">
再次点击页面下方的“帮助与关于”关闭该信息
============================= 				
					</textarea>
				</div>
			</div>
		</div>
	</div>

</body>
<script>
	$(document).ready(function() {
		share();
	});
	function share() {
		$.post("shareget", {
			id : $("#myid").val()
		}, function(data, status) {
			$("#txt").val(data);
			setTimeout(share(), 3000);
		});
	}
</script>
</html>
