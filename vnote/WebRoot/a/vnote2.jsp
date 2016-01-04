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

<script type="text/javascript" src="/vnote/a/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/a/jquery-1.9.1.js"></script>
<style type="text/css">
body {
	padding: 0;
	margin: 0;
	background: url('/a/images/bg.png') top left repeat #ebeef2;
	font: normal 14px Arial, Helvetica, sans-serif;
	color: #b7bdc3;
}

.stack {
	position: absolute;
	top: 50px;
	right: 85px;
	bottom: 75px;
	left: 85px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	-webkit-box-shadow: 0px 0px 9px rgba(121, 126, 133, 0.15);
	-moz-box-shadow: 0px 0px 9px rgba(121, 126, 133, 0.15);
	box-shadow: 0px 0px 9px rgba(121, 126, 133, 0.15);
}

.stack .layer_1 {
	position: absolute;
	top: -2px;
	bottom: 0px;
	right: 0px;
	left: 0px;
	background-color: #fdfeff;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	-webkit-box-shadow: 0px 1px 2px rgba(80, 83, 87, 0.25);
	-moz-box-shadow: 0px 1px 2px rgba(80, 83, 87, 0.25);
	box-shadow: 0px 1px 2px rgba(80, 83, 87, 0.25);
}

.stack .layer_1 .layer_2 {
	position: absolute;
	top: -1px;
	bottom: 2px;
	right: -2px;
	left: -2px;
	background-color: #fdfeff;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	-webkit-box-shadow: 0px 1px 0 rgba(61, 64, 67, 0.2);
	-moz-box-shadow: 0px 1px 0 rgba(61, 64, 67, 0.2);
	box-shadow: 0px 1px 0 rgba(61, 64, 67, 0.2);
}

.stack .layer_1 .layer_2 .layer_3 {
	position: absolute;
	top: 0px;
	bottom: 1px;
	right: -2px;
	left: -2px;
	background-color: #fdfeff;
	border: 1px solid #dcdde1;
	padding: 18px 20px;
	font: normal 15px Arial, Helvetica, sans-serif;
	line-height: 20px;
	color: #3a3b3c;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
}

.stack .layer_1 .layer_2 .layer_3 .contents {
	width: 100%;
	height: 100%;
	margin: 0;
	padding: 0 6px 0 0;
	border: 1px solid transparent;
	background: transparent;
	resize: none;
	overflow-y: auto;
	white-space: pre-wrap;
	font: normal 15px Arial, Helvetica, sans-serif;
	line-height: 20px;
	color: #3a3b3c;
}

.stack .layer_1 .layer_2 .layer_3 .contents2 {
	width: 100%;
	height: 100%;
	margin: 0;
	padding: 0 0 0 0;
	border: 1px solid transparent;
	background: transparent;
	resize: none;
	overflow-y: auto;
	white-space: pre-wrap;
	font: normal 15px Arial, Helvetica, sans-serif;
	line-height: 20px;
	color: #3a3b3c;
}

.stack .layer_1 .layer_2 .layer_3 .contents.monospace {
	font: normal 12px Monaco, 'Courier New', monospace;
	line-height: 18px;
}

#controls {
	position: absolute;
	height: 25px;
	bottom: 20px;
	right: 0;
	left: 0;
	text-align: center;
}

#controls a {
	color: #9ba0a5;
	text-decoration: none;
	margin: 0 10px;
}

#controls a:hover {
	color: #84878c;
}

#controls a#padlock {
	margin-right: 0;
	opacity: 0.65;
	-moz-opacity: 0.65;
	filter: alpha(opacity = 65);
	vertical-align: -1px;
}

#controls a#padlock:hover {
	opacity: 1;
	-moz-opacity: 1;
	filter: alpha(opacity = 100);
}
</style>
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
			href="javascript:if($('#help').css('display')=='none') $('#help').show();else $('#help').hide()">帮助与关于</a>


		<!--<a href="pwd?mod=0&id=<%=strid%>">编辑模式</a>-->
      <a href="javascript:tobianji()">编辑模式</a>

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
<script>
	var edit = 0;
	$(document).ready(function() {
		//读取文本
		get();

		//杂项
		others();
	});
	var timer = null;
	// 点击提交按钮提交数据
	function bindup() {

		edit = 1;

		//alert($("#txt2").val());
		var str = $("#txt2").val() + "\n" + "---------由" + $("#nicheng").val()
				+ " 添加于" + new Date().toLocaleString() + "-----------"
				+ "\n\n\n" + $("#txt").val();
		//绑定数据上传事件

		$.post("ajaxtxt", {
			id : $("#myid").val(),
			txt : str
		}, function(data, status) {
			edit = 0;
			get();
		});

	}
	function get() {
		$.post("ajaxget", {
			id : $("#myid").val()
		}, function(data, status) {
			$("#txt").val(data);
			if (edit == 0)
				setTimeout(get(), 3000)
		});
	}

	function AddFavorite(sURL, sTitle) {
		url = window.location.href;
		id = $("#myid").val();
		title = "vnote-" + id;
		try {
			window.external.addFavorite(url, title);
		} catch (e) {
			try {
				window.sidebar.addPanel(url, title, "");
			} catch (e) {
				alert("请按 Ctrl+D 加入收藏夹");
			}
		}
	}
	function others() {
		$("#txt2").on("click", function() {
			if ($("#txt2").val() == "输入文本...") {
				$("#txt2").val("");
			}
		});
		$("#txt2").on("blur", function() {
			if ($("#txt2").val() == "") {
				$("#txt2").val("输入文本...");
			}
		});
	}
	function tobianji(){
	
		$.post("pwd", {
			id : $("#myid").val(),
			mod : 0
		}, function(data, status) {
		$("#layer3").html(data);

		});	
	}
</script>

</html>
