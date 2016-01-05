<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>password</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="<%=basePath%>a/js/jquery-1.9.1.js"></script>

</head>
<body>
	<div>
		<input type="hidden" id="frm"
			value="<%=request.getParameter("frm").toString()%>"> <input
			type="hidden" id="mod"
			value="<%=request.getParameter("mod").toString()%>"> <input
			type="hidden" id="id"
			value="<%=request.getParameter("id").toString()%>"> 
			输入密码（没有密码则留空）：
			<input type="password" id="pwd"> <input id="ok" type="button"
			value="确定" onclick="a()"> <input id="modify" type="button"
			value="修改(留空删除)" onclick="modify()"> 
		<div id="status"></div>
	</div>

</body>
<script type="text/javascript">
  if($("#frm").val()==0){//确认
    $("#modify").hide();
  }
    if($("#frm").val()==1){//修改
    $("#ok").hide();

  }

  
  
  
function jspost(action, values) {
    var id = Math.random();
    document.write('<form id="post' + id + '" name="post'+ id +'" action="' + action + '" method="post">');
    for (var key in values) {
        document.write('<input type="hidden" name="' + key + '" value="' + values[key] + '" />');
    }
    document.write('</form>');    
    document.getElementById('post' + id).submit();
}

  	  function a(){ 	 
		$.post("pwd1", {
			id : $("#id").val(),
			mod:$("#mod").val(),//请求的mod
			pwd:$("#pwd").val()
		}, function(data, status) {
		    if(data=="false"){
			$("#status").html("密码错误");
			}else{

		jspost("<%=path%>/" + $("#id").val(), {'pwd' : $("#pwd").val()});
			}
		});
	}
	function modify() {
      $.post("addpwd", {
			id : $("#id").val(),			
			pwd:$("#pwd").val()
		}, function(data, status) {

		self.location.href="<%=path%>/" + $("#id").val();
		
		});
	}

</script>
</html>
