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
<script type="text/javascript" src="<%=basePath%>a/js/vnote2js.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>a/css/vnote2css.css">

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
					<div style="height: 20%">
						<table style="width: 100%;height: 100%">
							<tr style="width: 100%;height: 100%">
								<td style="width: 90%;height: 100%"><textarea id="txt2"
										class="contents2 " spellcheck="true">输入文本...</textarea></td>
								<td style="width: 10%;height: 100%;">昵称: <input
									style="width:55px;" type="text" id="nicheng" value="Guest"><input
									type="button" style="width: 100%" value="提交" onclick="bindup()"></td>
							</tr>
						</table>
					</div>
					<div style="height: 80%">
						<textarea id="txt" class="contents " readonly="readonly"
							spellcheck="true"></textarea>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br />
	<div id="controls">

		<a href="javascript:AddFavorite()">收藏${param.id}页面</a> <a
			href="javascript:myhelp()">帮助与关于</a>


		<!--<a href="pwd?mod=0&id=<%=strid%>">编辑模式</a>-->
		<a href="javascript:tobianji()">编辑模式</a>
	</div>

	<div class="stack " style="display:none;" id="help">
		<div class="layer_1">
			<div class="layer_2">
				<div id="myhelpc" class="layer_3"></div>
			</div>
		</div>
	</div>

</body>
</html>
