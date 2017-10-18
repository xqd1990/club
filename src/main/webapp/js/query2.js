var objs;
$(function(){
	$("#detail_query_button").click(function(){
		var start = $("#detail_start").val();
		var end = $("#detail_end").val();
		if(start==""||end==""){
			alert("请填选日期！");
		}else{
			var param = {"start":start,"end":end};
			$.post("bill/query",param,function(data){
				var end = new RegExp(/\d+$/);
				$("#result").html("<tr><th>牌局名</th><th>玩家昵称</th><th>战绩</th><th>保险合计</th><th>总计</th><th>结束时间</th></tr>");
				$.each(data,function(i,item){
					var content = "<tr class='row"+(i%2)+"'><td>"+end.exec(item.desk)+"</td><td>"+item.player.account+"</td><td>"+item.socre+"</td><td>"+item.insurance+"</td><td></td><td>"+item.finish_time+"</td></tr>";
					$("#result").append(content);
				});
			},"json");
		}
	});
	$("#general_query_button").click(function(){
		var start = $("#general_start").val();
		var end = $("#general_end").val();
		if(start==""||end==""){
			alert("请填选日期！");
		}else{
			var param = {"start":start,"end":end};
			$.post("bill/query",param,function(data){
				objs = data;
				var end = new RegExp(/\d+$/);
				$("#result").html("<tr><th>桌号</th><th>人数</th><th>卓积分小计</th><th>卓保险小合计</th><th>主机结算</th><th>结账<input type='checkbox' onclick='all_select(this)'></th><th>结束时间<input type='button' value='结算' onclick='pay_for_ids()'><input type='button' value='撤销' onclick='cancel_for_ids()'></th></tr>");
				var finish_time = objs[0].finish_time;
				var desk = end.exec(objs[0].desk);
				var count = 0;
				var score = 0;
				var insurance = 0;
				var final_state = objs[0].server_state;
				$.each(objs,function(i,item){
					if(finish_time!=item.finish_time){
						$("#result").append("<tr class='pay"+final_state+"'><td>"+end.exec(desk)+"</td><td onMouseOver='omover(this,event)' onMouseOut='omout(this,event)'>"+count+"</td><td>"+score+"</td><td>"+insurance+"</td><td>"+Math.round((score+insurance)*0.975)+"</td><td><input type='checkbox' name='sel'></td><td>"+finish_time+"</td></tr>");
						desk = end.exec(item.desk);
						finish_time = item.finish_time;
						count = 1;
						score = item.socre;
						insurance = item.insurance;
						final_state = item.server_state;
					}else{
						count++;
						score = item.socre + score;
						insurance = insurance + item.insurance;
					}
				});
				$("#result").append("<tr class='pay"+final_state+"'><td>"+desk+"</td><td onMouseOver='omover(this,event)' onMouseOut='omout(this,event)'>"+count+"</td><td>"+score+"</td><td>"+insurance+"</td><td>"+(score+insurance)*0.975+"</td><td><input type='checkbox' name='sel'></td><td>"+finish_time+"</td></tr>");
			},"json");
		}
	});
	$("#query").click(function(){
		var paid = $(".pay1");
		var unpaid = $(".pay0");
		$("#paid_num").val(paid.length);
		var paid_total = 0;
		for(var i=0;i<paid.length;i++){
			paid_total = paid_total + parseFloat(paid.eq(i).children().eq(4).html());
		}
		$("#paid").val(paid_total);
		$("#unpaid_num").val(unpaid.length);
		var unpaid_total = 0;
		for(var i=0;i<unpaid.length;i++){
			unpaid_total = unpaid_total + parseFloat(unpaid.eq(i).children().eq(4).html());
		}
		$("#unpaid").val(unpaid_total);
	});
});
function all_select(obj){
	if(obj.checked){
		$(":checkbox").prop("checked",true);
	}else{
		$(":checkbox").prop("checked",false);
	}
}
function pay_for_ids(){
	var chs = $("input[name='sel']:checked");
	var num = chs.length;
	var total = 0;
	for(var i=0;i<num;i++){
		total = total + parseFloat(chs.eq(i).parent().prev().html());
	}
	var flag = window.confirm(num+"桌 共"+total+"元 确定结账？");
	if(flag){
		
		var id_array = new Array();
		var count = 0;
		for(var i=0;i<chs.length;i++){
			$.each(objs,function(j,n){
				if(n.finish_time==chs.eq(i).parent().next().html()){
					id_array[count] = n.id;
					count++;
				}
			});
		}
		$.ajax({  
		    type : 'POST',  
		    url: 'bill/pay',  
		    contentType : "application/json" ,
		    data : JSON.stringify(id_array), 
		    success : function(data) {
		    	alert(data);
		    	$("#general_query_button").click();
		    }  
		});
	}
}
function cancel_for_ids(){
	var flag = window.confirm("确定撤销？");
	if(flag){
		var chs = $("input[name='sel']:checked");
		var id_array = new Array();
		var count = 0;
		for(var i=0;i<chs.length;i++){
			$.each(objs,function(j,n){
				if(n.finish_time==chs.eq(i).parent().next().html()){
					id_array[count] = n.id;
					count++;
				}
			});
		}
		$.ajax({  
		    type : 'POST',  
		    url: 'bill/cancel',  
		    contentType : "application/json" ,
		    data : JSON.stringify(id_array), 
		    success : function(data) {
		    	alert(data);
		    	$("#general_query_button").click();
		    }  
		});
	}
}
function omover(obj,e){
	var time = $(obj).nextAll().last().html();
	$("#detail_div").html("详情：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;积分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保险<br>")
	$.each(objs,function(i,item){
		if(item.finish_time==time){
			$("#detail_div").append(item.player.account+"：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+item.socre+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+item.insurance+"<br>");
		}
	});
	$("#detail_div").css("display","block");
	$("#detail_div").css("left",e.pageX);
	$("#detail_div").css("top",e.pageY); 
}
function omout(obj){
	$("#detail_div").css("display","none");
}