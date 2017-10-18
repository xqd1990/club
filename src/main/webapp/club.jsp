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
			function checkStyle(obj){
				var str = $(obj).val();
				var test = /^\d+(\.\d+)?$/;
				if(!str.match(test)){
					alert("输入格式有误!");
					$(obj).val("0.0");
				}
			}
			function deleteClub(id){
				var flag = window.confirm("确认删除？");
				if(flag){
					window.location.href="clubDelete?id="+id;
				}
			}
			function modifyClub(obj){
				var targets = $(obj).parent().parent().children();
				$("#id").val(targets.eq(1).html());
				$("#name").val(targets.eq(0).html());
				$("#principal").val(targets.eq(2).html());
				$("#statement").val(targets.eq(3).html());
				$("#interest").val(targets.eq(4).html());
				$("#wechat").val(targets.eq(5).html());
				$("#nameInItaly").val(targets.eq(6).html());
				$("#extra").val(targets.eq(7).html());
				$("#state").val(targets.eq(8).html());
				$("#add").css({display:"none"});
				$("#mod").css({display:"block"});
			}
			function cancel(){
				$("#add").css({display:"block"});
				$("#mod").css({display:"none"});
			}
			function checkNum(obj){
				var test = /^(0|1)?$/;
				var str = $(obj).val();
				if(!str.match(test)){
					alert("输入格式有误!");
					$(obj).val("1");
				}
			}
		</script>
		<style type="text/css">
			th{background-color:#0976AF}
			td{text-align: center}
			ul{list-style:none;width:300px;border-style:solid}
		</style>
	</head>
	<body onload="showDia()">
		<div id="add">
			<form action="clubAdd" method="post">
				<span>俱乐部编号：</span><input type="text" name="id""><span>俱乐部名称：</span><input type="text" name="name"><span>该部负责人：</span><input type="text" name="principal"><br>
				<span>俱乐部报表：</span><input type="text" name="statement"><span>俱乐部返水：</span><input type="text" name="interest" onchange="checkStyle(this)"><span>俱乐部群名：</span><input type="text" name="wechat"><br>
				<span>其意大利名：</span><input type="text" name="nameInItaly"><span>俱乐部加成：</span><input type="text" name="extra" value="0.0" onchange="checkStyle(this)"><span>俱乐部标识：</span><input type="text" name="state" value="1" onchange="checkNum(this)"><br>
				<input type="submit" value="确认"><input type="reset" value="取消">
			</form>
		</div>
		<div id="mod" style="display:none">
			<form action="clubModify" method="post">
				<span>俱乐部编号：</span><input type="text" name="id" id="id" readonly="true"><span>俱乐部名称：</span><input type="text" name="name" id="name"><span>该部负责人：</span><input type="text" name="principal" id="principal"><br>
				<span>俱乐部报表：</span><input type="text" name="statement" id="statement"><span>俱乐部返水：</span><input type="text" name="interest" id="interest" onchange="checkStyle(this)"><span>俱乐部群名：</span><input type="text" name="wechat" id="wechat"><br>
				<span>其意大利名：</span><input type="text" name="nameInItaly" id="nameInItaly"><span>俱乐部加成：</span><input type="text" name="extra" id="extra" onchange="checkStyle(this)"><span>俱乐部标识：</span><input type="text" name="state" id="state" value="1" onchange="checkNum(this)"><br>
				<input type="submit" value="确认"><input type="button" value="取消" onclick="cancel()">
			</form>
		</div>
		<table cellspacing="1" style="width:100%">
			<tr><th>俱乐部名</th><th>俱乐部ID</th><th>负责人</th><th>报表名</th><th>返水</th><th>群名</th><th>意大利名</th><th>加成</th><th>标识</th><th>操作</th></tr>
			<c:forEach items="${clubs}" var="club" varStatus="i">
				<c:if test="${i.count%2==0}"><tr style="background-color:#ECECEC"><td>${club.name}</td><td>${club.id}</td><td>${club.principal}</td><td>${club.statement}</td><td>${club.interest}</td><td>${club.wechat}</td><td>${club.nameInItaly}</td><td>${club.extra}</td><td>${club.state}</td><td><span onclick="deleteClub(${club.id})">删除</span>&nbsp<span onclick="modifyClub(this)">修改</span></td></tr></c:if>
				<c:if test="${i.count%2==1}"><tr style="background-color:#C9DAE2"><td>${club.name}</td><td>${club.id}</td><td>${club.principal}</td><td>${club.statement}</td><td>${club.interest}</td><td>${club.wechat}</td><td>${club.nameInItaly}</td><td>${club.extra}</td><td>${club.state}</td><td><span onclick="deleteClub(${club.id})">删除</span>&nbsp<span onclick="modifyClub(this)">修改</span></td></tr></c:if>
			</c:forEach>
		</table>
		<br>
		
	</body>
</html>