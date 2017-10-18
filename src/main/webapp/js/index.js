var test = 1;
$(function(){
	var myDate = new Date();
	var date = myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate();
	$("#head_time").html("当前系统日期:&nbsp;"+date);
});