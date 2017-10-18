$(function(){
	$(".delete").click(function(){
		var flag = window.confirm("确认删除？");
		var id = $(this).parent().parent().children().eq(0).html();
		if(flag){
			window.location.href="organization/delete?id="+id;
		}
	});
	$(".modify").click(function(){
		var target = $(this).parent().parent().children();
		$("#id").val(target.eq(0).html());
		$("#name").val(target.eq(1).html());
		$("#insurance").val(target.eq(3).html());
		$("#win_interest").val(target.eq(4).html());
		$("#lose_interest").val(target.eq(5).html());
		$("#mark").val(target.eq(6).html());
		$("#manager").val(target.eq(2).html());
		$("#add_div").css("display","none");
		$("#modify_div").css("display","block");
	});
	$("#cancel").click(function(){
		$("#add_div").css("display","block");
		$("#modify_div").css("display","none");
	});
});