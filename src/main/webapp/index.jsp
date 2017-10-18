<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script type="text/javascript" src="js/jquery-1.8.1.min.js"></script>
	<script type="text/javascript">
		function getDate() {
			var myDate = new Date();
			var date = myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate();
			$("#date").html("当前系统日期:   "+date);
		}
	</script>
	<style type="text/css">
		ul{list-style: none;width:100%;margin:0;padding:0;}
		li{text-align: center;margin:25px;font-size:12px;background-color:white}
	</style>
  </head>
  
  <body style="width:100%;margin:0" onload="getDate()">
  	<header style="width:100%;background-color: #0476B4">
  		<div style="width:100%;height:22px">
  			<span style="font-size:14px;color:white;">欢迎使用俱乐部积分统计系统</span>
  			<span id="date" style="font-size:14px;color:white;float:right"></span>
  		</div>
  	</header>
  	<section style="position:absolute;top:22px;bottom:0;width:100%">
	  	<div id="left" style="float:left;height:100%;width:125px;background-color:#D8D8D8">
	  		<ul>
	  			<li><img src="images/dec.gif"><b><a href="club" target="main">俱乐部管理</a></b></li>
	  			<li><img src="images/dec.gif"><b><a href="player" target="main">玩&nbsp&nbsp&nbsp家管理</a></b></li>
	  			<li><img src="images/dec.gif"><b><a href="bill.jsp" target="main">日账单管理</a></b></li>
	  			<li><img src="images/dec.gif"><b><a href="bill" target="main">查&nbsp&nbsp&nbsp询管理</a></b></li>
	  			<li><img src="images/dec.gif"><b><a href="addScore" target="main">充&nbsp&nbsp&nbsp分管理</a></b></li>
	  		</ul>
	  	</div>
	  	<div id="frame" style="position:absolute;height:100%;left:125px;right:0;background-image:url('images/background.gif')">
    		<iframe style="width:100%;height:100%" name="main" frameborder="0" src="club">
    		</iframe>
    	</div>
  	</section>
  </body>
  
</html>