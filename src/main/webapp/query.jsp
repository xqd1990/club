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
			function queryTeam(){
				var start_date = $("#team_start").val();
				var end_date = $("#team_end").val();
				var team = $("#team_choose").val();
				var param = {"start":start_date,"end":end_date,"team":team};
				$.post('billQuery?flag=0',param,function(data){
					$("#teamTable").html(data);
					var targets = $("#total").nextAll();
					var addItems = $("#total").parent().prevAll();
					for(var i=0;i<targets.size();i++){
						var total = 0;
						for(var j=0;j<(addItems.size()-1);j++){
							var addItem = addItems.eq(j).children().eq(i+1).html();
							total = total + parseFloat(addItem);
						}
						targets.eq(i).html(total);
					}
				});
			}
			function queryTeaml(){
				var start_date = $("#team_start").val();
				var end_date = $("#team_end").val();
				var team = $("#team_choose").val();
				var param = {"start":start_date,"end":end_date,"team":team};
				$.post('billQuery?flag=10',param,function(data){
					$("#teamTable").html(data);
					var targets = $("#total").nextAll();
					var addItems = $("#total").parent().prevAll();
					for(var i=0;i<targets.size();i++){
						var total = 0;
						for(var j=0;j<(addItems.size()-1);j++){
							var addItem = addItems.eq(j).children().eq(i+1).html();
							total = total + parseFloat(addItem);
						}
						targets.eq(i).html(total);
					}
				});
			}
			function queryPlayer(){
				var start_date = $("#player_start").val();
				var end_date = $("#player_end").val();
				var player = $("#player_choose").val();
				var param = {"start":start_date,"end":end_date,"player":player};
				$.post('billQuery?flag=1',param,function(data){
					alert("导出成功!");
				});
			}
			function queryTeamInterest(){
				var start_date = $("#team_interest_start").val();
				var end_date = $("#team_interest_end").val();
				var param = {"start":start_date,"end":end_date};
				$.post('billQuery?flag=2',param,function(data){
					$("#table").html(data);
					var targets = $("#itotal").nextAll();
					var addItems = $("#itotal").parent().prevAll();
					for(var i=0;i<targets.size();i++){
						var total = 0;
						for(var j=0;j<(addItems.size()-1);j++){
							var addItem = addItems.eq(j).children().eq(i+1).html();
							total = total + parseFloat(addItem);
						}
						targets.eq(i).html(total);
					}
				});
			}
			function queryTimes(){
				var start_date = $("#time_start").val();
				var end_date = $("#time_end").val();
				var team = $("#time_choose").val();
				var param = {"start":start_date,"end":end_date,"team":team};
				$.post('billQuery?flag=3',param,function(data){
					$("#time").html(data);
					var targets = $("#tot").nextAll();
					var addItems = $("#tot").parent().prevAll();
					for(var i=0;i<targets.size();i++){
						var total = 0;
						for(var j=0;j<(addItems.size()-1);j++){
							var addItem = addItems.eq(j).children().eq(i+1).html();
							total = total + parseFloat(addItem);
						}
						targets.eq(i).html(total);
					}
				});
			}
			function queryTotal(){
				var start_date = $("#t_start").val();
				var end_date = $("#t_end").val();
				var param = {"start":start_date,"end":end_date};
				$.post('billQuery?flag=4',param,function(data){
					$("#total").html(data);
				});
			}
			function outTeam(){
				var start_date = $("#team_start").val();
				var end_date = $("#team_end").val();
				var team = $("#team_choose").val();
				var param = {"start":start_date,"end":end_date,"team":team};
				$.post('billQuery?flag=5',param,function(data){
					alert("导出成功!");
				});
			}
		</script>
		<style type="text/css">
			th{background-color:#0976AF;}
			td{text-align:center;}
		</style>
	</head>
	<body>
		<br>
		<span style="margin-left:15%">（团队账单）查询日期账单:&nbsp<input id="team_start" type="text" name="startDate"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.startDate);return false;"><input type="button" value="start"></a><input id="team_end" type="text" name="endDate"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.endDate);return false;"><input type="button" value="end"></a>&nbsp<input id="team_choose" type="text" list="teamList"><datalist id="teamList"><c:forEach items="${teams}" var="team"><option>${team}</option></c:forEach></datalist>&nbsp<input type="button" value="查询" onclick="queryTeam()">&nbsp<input type="button" value="非充" onclick="queryTeaml()">&nbsp<input type="button" value="导出" onclick="outTeam()"></span>
		<table id="teamTable" cellspacing="1" style="width:100%">
			
		</table>
		<br>
		<span style="margin-left:15%">（玩家账单）查询日期账单:&nbsp<input id="player_start" type="text" name="start_Date"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.start_Date);return false;"><input type="button" value="start"></a><input id="player_end" type="text" name="end_Date"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.end_Date);return false;"><input type="button" value="end"></a>&nbsp<input id="player_choose" type="text" list="nameByTeamList"><datalist id="nameByTeamList"><c:forEach items="${names}" var="p_name"><option>${p_name}</option></c:forEach></datalist>&nbsp<input type="button" value="导出" onclick="queryPlayer()"></span>
		<br><br>
		<span style="margin-left:15%">（团队返水）查询团队返水:&nbsp<input id="team_interest_start" type="text" name="date1"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.date1);return false;"><input type="button" value="start"></a><input id="team_interest_end" type="text" name="date2"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.date2);return false;"><input type="button" value="end"></a>&nbsp<input type="button" value="查询" onclick="queryTeamInterest()"></span>
		<table id="table" cellspacing="1" style="width:100%">
			
		</table>
		<br>
		<span style="margin-left:15%">（团队场次）查询团队场次:&nbsp<input id="time_start" type="text" name="timedate1"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.timedate1);return false;"><input type="button" value="start"></a><input id="time_end" type="text" name="timedate2"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.timedate2);return false;"><input type="button" value="end"></a>&nbsp<input id="time_choose" type="text" list="timeList"><datalist id="timeList"><c:forEach items="${teams}" var="team"><option>${team}</option></c:forEach></datalist>&nbsp<input type="button" value="查询" onclick="queryTimes()"></span>
		<table id="time" cellspacing="1" style="width:100%">
		
		</table>
		<br>
		<span style="margin-left:15%">（日期总账）查询日期总账:&nbsp<input id="t_start" type="text" name="tdate1"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.tdate1);return false;"><input type="button" value="start"></a><input id="t_end" type="text" name="tdate2"><a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.all.tdate2);return false;"><input type="button" value="end"></a>&nbsp<input type="button" value="查询" onclick="queryTotal()"></span>
		<table id="total" cellspacing="1" style="width:100%">
			
		</table>
		<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="common/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;"></iframe>
	</body>
</html>