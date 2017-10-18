alert(123);
alert(window.parent.test);
$(function(){
	$("#upload_button").click(function(){
		var files = document.getElementById("file").files;
		var file = files[0];
		if(file){
			var upload = new FormData();
			upload.append('file',file);
			$.ajax({
				url:"bill/in",
				type:"post",
				data:upload,
				cache:false,
				processData:false,
				contentType:false,
				success: function(data){
					alert(data);
				}
			});
		}
	});
});