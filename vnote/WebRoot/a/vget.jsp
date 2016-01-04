<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'vget.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="/vnote/a/jquery-1.9.1.js"></script>
</head>
  <body>
  ${param.id}.<br/>
  	<input type="text" id="txt">
  
This is my JSP page2www. <br>
    <a href="/test">test</a>

  </body>
<script>
$(document).ready(function(){
get();
});
function get(){

$.post("ajaxget",
  function(data,status){
   $("#txt").val(data);
	setTimeout(get(), 3000)
  });
}
   </script>
</html>