$(function(){
	$("#go_button").click(function(){
		var current_page = $("#page").val();
		window.location.href="player/show?current_page="+current_page;
	});
	$("#cancel").click(function(){
		$("#add_div").css("display","block");
		$("#modify_div").css("display","none");
	});
	$(".de").click(function(){
		var flag = window.confirm("确认删除？");
		if(flag){
			var id = $(this).parent().parent().children().eq(0).html();
			window.location.href="player/delete?current_page=1&id="+id;
		}
	});
	$(".mo").click(function(){
		var data = $(this).parent().parent().children();
		$("#id").val(data.eq(0).html());
		$("#account").val(data.eq(1).html());
		$("#organization").val(data.eq(2).html());
		$("#win_interest").val(data.eq(3).html());
		$("#lose_interest").val(data.eq(4).html());
		$("#mark").val(data.eq(5).html());
		$("#add_div").css("display","none");
		$("#modify_div").css("display","block");
	});
	$("#sbi").click(function(){
		var id = $("#search_by_id").val();
		$.post("player/queryById",{"id":id},function(data){
			$("#id").val(data.id);
			$("#account").val(data.account);
			$("#organization").val(data.organization.name);
			$("#win_interest").val(data.win_interest);
			$("#lose_interest").val(data.lose_interest);
			$("#mark").val(data.mark);
			$("#add_div").css("display","none");
			$("#modify_div").css("display","block");
		},"json");
	});
	$("#sba").click(function(){
		var account = $("#search_by_account").val();
		$.post("player/queryByAccount",{"account":account},function(data){
			$("#id").val(data.id);
			$("#account").val(data.account);
			$("#organization").val(data.organization.name);
			$("#win_interest").val(data.win_interest);
			$("#lose_interest").val(data.lose_interest);
			$("#mark").val(data.mark);
			$("#add_div").css("display","none");
			$("#modify_div").css("display","block");
		},"json");
	});
});