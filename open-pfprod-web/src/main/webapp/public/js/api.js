/** 登录与注册 */
(function($) {
	$(function() {
		//上下文路径
		var contextPath = $('#globalContextPath').val();

		//匿名登录
		var postLogin={};
		$("#anonymousDiv input.submit").click(function(){
			var loginForm=$("#anonymousDiv");
			var areaId = loginForm.find("input[name='areaid']").val();
			var resourceId = loginForm.find("input[name='resourceid']").val();
			var loginUrl = contextPath + "/u/88888/888";
			$.ajax({
	            url : loginUrl,
	            type : "post",
	            dataType : "json",
	            contentType:"application/json",
	            data : JSON.stringify({
	            	loginType : 0,
	            	areaId : parseInt(areaId, 10),
	            	resourceId : resourceId,
	            	terminalType : parseInt(loginForm.find("input[name='an_terminal']:checked").val(), 10),
	    			unicode : loginForm.find("input[name='unicode']").val()
	            }),
	            success : function (back) {
	            	$('#anonymousTokenStr').html(JSON.stringify(back));
	            	if(back.data){
	            		var token = {
    	            		accessToken : back.data.accessToken,
    	            		areaId : areaId,
    	            		resourceId : resourceId,
    	            		usId : back.data.usId,
    	            		unicode : back.data.unicode
    	            	};
    	            	$.cookies.set( 'token', token );
	            	}
	            }
			});
		});
		
		
		//单点登录
		$("#loginDiv input.submit").click(function(){
			var postLogin={};
			var loginForm=$("#loginDiv");
			var areaId = loginForm.find("input[name='areaid']").val();
			var resourceId = loginForm.find("input[name='resourceid']").val();
			var loginUrl = contextPath + "/u/88888/888";
			postLogin.loginType = 2;
			postLogin.terminalType = parseInt(loginForm.find("input[name='terminal']:checked").val(), 10);
			postLogin.userName = loginForm.find("input[name='username']").val();
			postLogin.password = loginForm.find("input[name='password']").val();
			postLogin.unicode = loginForm.find("input[name='unicode']").val();
			postLogin.pushToken = loginForm.find("input[name='ptoken']").val();
			postLogin.areaId =  parseInt(areaId, 10);
			postLogin.resourceId = loginForm.find("input[name='resourceid']").val();
			postLogin.imageCode = $('input#imageCode').val();
			var postData = JSON.stringify(postLogin);
			$.ajax({
	            url : loginUrl,
	            type : "post",
	            dataType : "json",
	            contentType:"application/json",
	            data : postData,
	            success : function (back) {
	            	$('#loginTokenResult').html(JSON.stringify(back));
	            	if(back.data){
	            		var token = {
    	            		accessToken : back.data.accessToken,
    	            		areaId : areaId,
    	            		resourceId : resourceId,
    	            		usId : back.data.usId,
    	            		unicode : back.data.unicode
    	            	};
    	            	$.cookies.set( 'token', token );
	            	}
	            }
			});
		});
		
		
		//刷新图片验证码
		$("#refreshImageCode").click(function(){
			var token = $.cookies.get('token');
			var postObj={};
			postObj.busiNo = 1;
			var postData = JSON.stringify(postObj);
			$.ajax({
	            url : contextPath + "/u/88888/777",
	            type : "post",
	            //dataType : "text",
	            contentType : 'application/json;charset=UTF-8',
	            data : postData,
	            headers : token,
	            success : function (back) {
	            	if(back.result){
	            		$('img#refreshImageCode').attr('src', back.data.img);
	            	}
	            	$('#validImgContent').html(JSON.stringify(back));
	            }
			});
		}).click();
		
	});
})(jQuery);



