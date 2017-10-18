<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
	<base href="<%=basePath%>">
	    
	    <title>My JSP 'login.jsp' starting page</title>
	    
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<script type="text/javascript" src="js/jquery-1.8.1.min.js"></script>
		<script type="text/javascript">
			function showDia(){
				<c:if test="${not empty error}">alert("${error}");</c:if>
				<c:if test="${not empty success}">alert("${success}");</c:if>
			}
			function deletePlayer(id){
				var flag = window.confirm("确认删除？");
				if(flag){
					window.location.href="playerDelete?id="+id;
				}
			}
			function cancel(){
				$("#add").css({display:"block"});
				$("#mod").css({display:"none"});
			}
			function modifyPlayer(obj){
				var targets = $(obj).parent().parent().children();
				$("#id").val(targets.eq(2).html());
				$("#name").val(targets.eq(1).html());
				$("#account").val(targets.eq(3).html());
				$("#team").val(targets.eq(0).html());
				$("#interest").val(targets.eq(4).html());
				$("#tel").val(targets.eq(5).html());
				$("#password").val(targets.eq(6).html());
				$("#add").css({display:"none"});
				$("#mod").css({display:"block"});
			}
			function checkStyle(obj){
				var str = $(obj).val();
				var test = /^\d+(\.\d+)?$/;
				if(!str.match(test)){
					alert("输入格式有误!");
					$(obj).val("0.0");
				}
			}
			function outPlayer(){
				window.location.href = "playerOut?flag=3&team="+$("#targetTeam").val();
			}
		</script>
		<style type="text/css">
			th{background-color:#0976AF}
			td{text-align: center}
		</style>
	</head>
	<body onload="showDia()">
		<div id="add">
			<form action="playerAdd" method="post">
				<span>玩家编号：</span><input type="text" name="id" onchange="checkStyle(this)"><span>玩家人名：</span><input type="text" name="name"><span>玩家账号：</span><input type="text" name="account"><br>
				<span>玩家团队：</span><input type="text" name="team"><span>玩家返水：</span><input type="text" name="interest" value="0.05" onchange="checkStyle(this)"><span>玩家电话：</span><input type="text" name="tel"><br>
				<span>玩家密码：</span><input type="text" name="password"><br>
				<input type="submit" value="确认"><input type="reset" value="取消">
			</form>
		</div>
		<div id="mod" style="display:none">
			<form action="playerModify" method="post">
				<span>玩家编号：</span><input type="text" name="id" id="id" readonly="true"><span>玩家名称：</span><input type="text" name="name" id="name"><span>玩家昵称：</span><input type="text" name="account" id="account"><br>
				<span>玩家团队：</span><input type="text" name="team" id="team"><span>玩家返水：</span><input type="text" name="interest" id="interest" onchange="checkStyle(this)"><span>玩家电话：</span><input type="text" name="tel" id="tel"><br>
				<span>玩家密码：</span><input type="text" name="password" id="password"><br>
				<input type="submit" value="确认"><input type="button" value="取消" onclick="cancel()">
			</form>
		</div>
		<table cellspacing="1" style="width:100%">
			<tr><th>团队</th><th>人名</th><th>ID</th><th>账号</th><th>返水</th><th>联系电话</th><th>密码</th><th>操作</th></tr>
			<c:forEach items="${players}" var="player" varStatus="i">
				<c:if test="${i.count%2==0}"><tr style="background-color:#ECECEC"><td>${player.team}</td><td>${player.name}</td><td>${player.id}</td><td>${player.account}</td><td>${player.interest}</td><td>${player.tel}</td><td>${player.password}</td><td><span onclick="deletePlayer(${player.id})">删除</span>&nbsp<span onclick="modifyPlayer(this)">修改</span></td></tr></c:if>
				<c:if test="${i.count%2==1}"><tr style="background-color:#C9DAE2"><td>${player.team}</td><td>${player.name}</td><td>${player.id}</td><td>${player.account}</td><td>${player.interest}</td><td>${player.tel}</td><td>${player.password}</td><td><span onclick="deletePlayer(${player.id})">删除</span>&nbsp<span onclick="modifyPlayer(this)">修改</span></td></tr></c:if>
			</c:forEach>
		</table>
		<span style="margin-left:40%">当前第${currentpage}页/共${lastpage}页</span>
		<span style="margin-left:1%"><a href="player?request_page=1"><input type="button" value="首页"></a><c:if test="${currentpage==1}"><input type="button" value="上一页" disabled="true"></c:if><c:if test="${currentpage>1}"><a href="player?request_page=${currentpage-1}"><input type="button" value="上一页"></a></c:if><c:if test="${currentpage==lastpage}"><input type="button" value="下一页" disabled="true"></c:if><c:if test="${currentpage<lastpage}"><a href="player?request_page=${currentpage+1}"><input type="button" value="下一页"></a></c:if><a href="player?request_page=${lastpage}"><input type="button" value="末页"></a><br/><br/></span>
		<hr>
		请选择团队:&nbsp<input type="text" id="targetTeam" list="teamList"><datalist id="teamList"><c:forEach items="${teams}" var="t"><option>${t}</option></c:forEach></datalist>&nbsp<input type="button" value="导出" onclick="outPlayer()">
		<a href="playerOut?flag=1"><input type="button" value="导出玩家ID"></a>
		<a href="playerOut?flag=2"><input type="button" value="导出玩家Al"></a>
	</body>
</html>