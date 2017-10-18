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
			function showNamesByTeam(){
				var team = $("#team").val();
				var param = {"team":team};
				$.post('ajax/returnNamesByTeam',param,function(data){
					$("#nameList").html(data);
				});
			}
			function deleteScore(id){
				var flag = window.confirm("确认删除？");
				if(flag){
					window.location.href="deleteScore?id="+id;
				}
			}
		</script>
		<style type="text/css">
			th{background-color:#0976AF}
			td{text-align: center}
		</style>
	</head>
	<body onload="showDia()">
		<br>
		<form action="addScore" method="post">
		日期:<input type="text" name="date" readonly="true"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.date);return false;"><img src="images/search.gif"></a>&nbsp团队:<input type="text" id="team" name="team" list="teamList" onchange="showNamesByTeam()"><datalist id="teamList"><c:forEach items="${teams}" var="t"><option>${t}</option></c:forEach></datalist>&nbsp姓名:<input type="text" name="name" list="nameList"><datalist id="nameList"></datalist>&nbsp充分:<input type="text" name="score">&nbsp<input type="submit" value="提交">
		</form>
		<br>
		<form action="addScore" method="post">充分日期起始:&nbsp<input type="text" name="startDate" readonly="true"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.startDate);return false;"><img src="images/search.gif"></a>&nbsp充分日期结束:&nbsp<input type="text" name="endDate" readonly="true"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.endDate);return false;"><img src="images/search.gif"></a>&nbsp<input type="submit" value="查询"></form>
		<table cellspacing="1" style="width:100%">
			<tr><th>日期</th><th>团队</th><th>人名</th><th>充分</th><th>操作</th></tr>
			<c:forEach items="${scores}" var="score" varStatus="i">
				<c:if test="${i.count%2==0}"><tr style="background-color:#ECECEC"><td>${score.date}</td><td>${score.team}</td><td>${score.name}</td><td>${score.score}</td><td><span onclick="deleteScore(${score.id})">删除</span></td></tr></c:if>
				<c:if test="${i.count%2==1}"><tr style="background-color:#C9DAE2"><td>${score.date}</td><td>${score.team}</td><td>${score.name}</td><td>${score.score}</td><td><span onclick="deleteScore(${score.id})">删除</span></td></tr></c:if>
			</c:forEach>
		</table>
		<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="common/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;"></iframe>	
	</body>
</html>