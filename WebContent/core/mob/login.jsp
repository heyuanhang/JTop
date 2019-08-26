<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<html class="ui-page-login">

	<head>
		<meta charset="GBK">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<title>JTopCMS - 移动工作台 - 管理员登录</title>
		<link href="css/mui.min.css" rel="stylesheet" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js"></script>
	 	
		<script type="text/javascript" src="../javascript/device.min.js"></script>
		
		<script language="javascript">  
		 
		 
		 </script>
		<style>
		.login {
			margin-top: 1rem;
		}
		.login .mui-btn-block {
			padding: 5px 0;
		}
	</style>
	</head>
	<body>
		<header class="mui-bar mui-bar-nav">
			<h1 class="mui-title">管理员登录</h1>
		</header>
		<div class="mui-content login">
			<form id="loginForm" class="mui-input-group"  name="loginForm" method="post">
				<div class="mui-input-row">
					<label>账号</label>
					<input tabindex="1"  id="userName" maxlength="20" name="userName" type="text" class="mui-input-clear mui-input" placeholder="请输入账号">
				
					<script> 
											 
												$('#userName').val(localStorage.jtopcms_member_mob_login_name);
											 
					</script>
				</div>
				<div class="mui-input-row">
					<label>密码</label>
					<input tabindex="2" id="parampw" name="parampw"  type="password" class="mui-input-clear mui-input" placeholder="请输入密码">
				</div>
				<div class="mui-input-row">
					<label>验证码</label>
					<input tabindex="3" maxlength="4" id="sysCheckCode" name="sysCheckCode" type="text" class="mui-input-clear mui-input" placeholder="请输入验证码">
										
				</div>
				&nbsp;
										<img id="checkCodeImg" onclick="javascript:changeCode();" style="cursor: pointer;" src="<cms:BasePath/>common/authImg.do?count=4&line=1&point=20&width=90&height=24&jump=4" />
			
			</form>
			
			
			
			<div class="mui-content-padded mui-text-center">
				<button id='login' onclick="javascript:login();" class="mui-btn mui-btn-block mui-btn-primary">登录</button>
				<div class="link-area">Powered By JTopCMS
				</div>
			</div>
			<div class="mui-content-padded oauth-area">

			</div>
		</div>
	</body>

</html>

<script type="text/javascript">

function changeCode()
{
	$('#checkCodeImg').attr('src','<cms:BasePath/>common/authImg.do?count=4&line=1&point=20&width=90&height=24&jump=4&rand='+Math.random());

}


function login()
{
		 

		var currentLoginHref = window.location.href;
		 
	    if(window.location.href.indexOf('<cms:BasePath/>') != 0)
	    {
	    	$.dialog({ 
							   					title :'提示',
							    				width: '120px', 
							    				height: '60px', 
							                    lock: true, 
							    				icon: '32X32/fail.png', 
							    				
							                    content: '错误的登录入口！', 
							       ok: true 
							                    
			});
			
			return;
	    }
 	    
	   // $('#parampw').val($('#parampw').val().substring(0,$('#parampw1').val().length));
	   
		var url = "<cms:BasePath/>login/postLogin.do";
        
        var postData =encodeURI($("#loginForm").serialize());
        
        postData = encodeData(postData);
           
		$.ajax({
				      		type: "POST",
				       		url: url,
				       		data: postData,
				   
				       		success: function(msg)
				            {     
				               if('-1' == msg)
				               {
				               		changeCode();
				               		
				               		$.dialog({ 
							   					title :'提示',
							    				width: '100px', 
							    				height: '60px', 
							                    lock: true, 
							    				icon: '32X32/fail.png', 
							    				
							                    content: '禁止登录！', 
							       ok: true 
							                    
								  });
				               		
				               		
				               }
				               if('0' == msg)
				               {
				               		changeCode();
				               		
				               		$.dialog({ 
							   					title :'提示',
							    				width: '100px', 
							    				height: '60px', 
							                    lock: true, 
							    				icon: '32X32/fail.png', 
							    				
							                    content: '验证码错误！', 
							       ok: true 
							                    
								  });
				               		
				               		
				               } 
				               else if('1' == msg)
				               {
				               
				               	 	
				               		localStorage.jtopcms_member_mob_login_name = $('#userName').val();
				                
				               		$.dialog.tips('登录成功！正在跳转...',3600,'loading.gif');
				               		window.location.href = '<cms:BasePath/>core/mob/censor.jsp';
				               		
				               		
				               }
				               else if('-999' == msg)
				               {
				               		$('#codeTr').hide();
				               		$('#phoneCodeTr').show();
				               		
				               		$.dialog({ 
							   					title :'提示',
							    				width: '300px', 
							    				height: '60px', 
							                    lock: true, 
							    				icon: '32X32/fail.png', 
							    				
							                    content: '您使用的帐号为机构主管，需要手机验证！', 
							       ok: true 
							                    
								  });
				               		
				               		
				               }	
				               else
				               {
				               		changeCode();
				               		
				               	    $.dialog({ 
							   					title :'提示',
							    				width: '160px', 
							    				height: '60px', 
							                    lock: true, 
							    				icon: '32X32/fail.png', 
							    				
							                    content: '用户名或密码错误！', 
							       ok: function ()
							       {
							      
							       		$('#parampw').val('');
							       		$('#sysCheckCode').val('');
							       		
							       			//window.location.reload();
							       } 
							                    
								  });
				               } 
				                
				              
				            }
		});	

      
     
}

 

</script>
