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
			var xhr
			function uploadFile(){
					var files = document.getElementById("file").files;
					var file = files[0];
					if(file){
						var formData = new FormData();
						formData.append("file",file);
						xhr = new XMLHttpRequest();
						xhr.open("POST", "ajax/addDayBill", true);
						xhr.send(formData);
						xhr.onreadystatechange = function(){
							if(xhr.readyState==4){
								if(xhr.status==200){
									alert(xhr.responseText);
								}else{
									alert("导入出错！");
								}
							}
						}
					}else{
						alert("未选择文件！");
					}
				}
			function deleteBills(){
				var start = $("#start").val();
				var end = $("#end").val();
				window.location.href="billDelete?start="+start+"&end="+end;
			}
		</script>
		<style type="text/css">
			
		</style>
	</head>
	<body onload="showDia()">
		<br><br><br>
		<span style="margin-left:30%">导入日账单:&nbsp<input type="file" id="file" style="border-style:solid;">&nbsp<input type="button" value="上传" onclick="uploadFile()"></span>
		<br><br><br><br>
		<span style="margin-left:25%">删除日账单:&nbsp<input id="start" type="text" name="start" readonly=true>&nbsp<a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.start);return false;"><img src="images/search.gif"></a>&nbsp<input id="end" type="text" name="end" readonly="true">&nbsp<a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.end);return false;"><img src="images/search.gif"></a>&nbsp<input type="button" value="删除" onclick="deleteBills()"></span>
		<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="common/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;"></iframe>
	</body>
</html>