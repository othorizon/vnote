<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<script type="text/javascript" src="/vnote/a/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/a/jquery-1.9.1.js"></script>
  </head>
  <body>
  <input type="hidden" id="mod" value="<%=request.getParameter("mod").toString()%>">
  <input type="hidden" id="id" value="<%=request.getParameter("id").toString()%>">
    <input type="password" id="pwd">
    <input type="button" value="确定" onclick="a()" >
    <div id="status"></div>
  </body>
  <script type="text/javascript">

  	  function a(){ 	 
		$.post("pwd1", {
			id : $("#id").val(),
			mod:$("#mod").val(),
			pwd:$("#pwd").val()
		}, function(data, status) {
		    if(data=="false"){
			$("#status").html("密码错误");
			}else{
			self.location.href="<%=path%>/"+$("#id").val();
			}
		});
  
  }
  </script>
</html>
