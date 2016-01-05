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
<script type="text/javascript" src="<%=basePath%>a/js/vnotejs.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>a/css/vnotecss.css">

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
					<textarea id="txt" class="contents " spellcheck="true"></textarea>
				</div>
			</div>
		</div>
	</div>
	<br />
	<div id="controls">

		<a href="javascript:AddFavorite()">收藏${param.id}页面</a> <a
			href="javascript:if($('#help').css('display')=='none') $('#help').show();else $('#help').hide()">帮助与关于</a>
		<a href="javascript:tobianji()">只写模式</a>
		<a href="javascript:mdfpwd()">修改密码</a>
	</div>

	<div class="stack " style="display: none;" id="help">
		<div class="layer_1">
			<div class="layer_2">
				<div class="layer_3">
					<textarea class="contents ">
再次点击页面下方的“帮助与关于”关闭该信息
=============================				
该网站为一个记事本，打开网站后会为你随机分配一个url地址，你也可以手动填写一个。			
就是这么简单。。
					 
-------特色-----------
同一个页面（url）可以被同时打开多个，但是不能同时编辑内容，只允许一个人编辑内容，而编辑的内容会被同步的显示在所有的页面中。
简单地说就是：这个就像一个群聊功能， 该网站主要用于协同办公，比如大家一起来共同完成一份简单的文档。
					 
-------注意----------				 
1.由于只是测试，内容不保证不会丢失。 
2.目前没有加密功能，所有内容都是公开且不受保护的。
					 
--------其他------------
1.目前为测试版 Beta1.0，功能正在开发完善中。如果有兴趣可以联系作者 QQ:492422846

2.网站样式直接从 notepad.cc 该网站扒下来的，未经允许，如被发现，来打我吧~
					</textarea>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
