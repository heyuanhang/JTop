<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>JTop内容管理系统</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="../../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		
		<script type="text/javascript" src="../../javascript/device.min.js"></script>
		<script type="text/javascript" src="jsencrypt.min.js"></script>
		
		<script language="javascript">  
		 if(device.mobile())
		{ 
			window.location.href='<cms:BasePath/>core/mob/login.jsp';	 
		} 
		 
		 </script>
		<style type="text/css">
		
		#moquu_top,#moquu_wxin,#moquu_wshare,#moquu_wmaps {
	z-index: 2;;
	width: 50px;;
	height: 50px;;
	right: 10px;;
	position: fixed;;
	cursor: pointer;;
	_position: absolute;;
	_bottom: auto;;
	_top: expression(eval(document . documentElement . scrollTop + document .
		documentElement . clientHeight-this . offsetHeight-(parseInt(this .
		currentStyle . marginTop, 10) || 0 ) -(
		parseInt(this . currentStyle . marginBottom, 10) || 0 ) ) )
}

#moquu_wxin {
	top: 360px;;
	_margin-top: 360px
}

#moquu_wxin a {
	background: url(moquu/img/sbtn.png) 0 -0px;;
	right: 0;;
	float: left;;
	width: 50px;;
	height: 50px;;
	text-indent: -9999px
}

#moquu_wxin a:hover {
	background: url(moquu/img/sbtn.png) -50px -0px
}

#moquu_wshare {
	top: 412px;;
	_margin-top: 412px
}

#moquu_wshare a {
	background: url(moquu/img/sbtn.png) 0 -50px;;
	width: 50px;;
	height: 50px;;
	right: 0;;
	float: left;;
	text-indent: -9999px
}

#moquu_wshare a:hover {
	background: url(moquu/img/sbtn.png) -50px -50px
}

#moquu_wmaps {
	top: 464px;;
	_margin-top: 464px
}

#moquu_wmaps a {
	background: url(moquu/img/sbtn.png) 0 -150px;;
	width: 50px;;
	height: 50px;;
	right: 0;;
	float: left;;
	text-indent: -9999px
}

#moquu_wmaps a:hover {
	background: url(moquu/img/sbtn.png) -50px -150px
}

#moquu_top {
	top: 516px;;
	_margin-top: 516px;;
	background: url(moquu/img/sbtn.png) 0 -101px;;
	width: 50px;;
	height: 50px
}

#moquu_top :hover {
	background: url(moquu/img/sbtn.png) -50px -101px
}

.moquu_wxin,.moquu_wshare {
	position: relative;;
	z-index: 2
}

.moquu_wxin a:hover .moquu_wxinh,.moquu_wshare a:hover .moquu_wshareh {
	display: block
}

.moquu_wxin .moquu_wxinh {
	position: absolute;;
	display: none;;
	left: -280px;;
	top: -200px;;
	width: 275px;;
	height: 355px;;
	background: url(moquu/img/moquu_ico.png) -1px -482px no-repeat
}


 				
.moquu_wshare .moquu_wshareh {
	position: absolute;;
	display: none;;
	left: -120px;;
	top: -50px;;
	width: 375px;;
	height: 460px;;
	background: url(<cms:BasePath/>common/logingate.png) no-repeat
}
 

@media screen and (max-width:2560px) {
	body {
		background: url(moquu/img/2.jpg);;
		font: 12px/ 1.5 arial, sans-serif;;
		text-align: center
	}
}

@media screen and (max-width:1920px) {
	body {
		background: url(moquu/img/3.jpg);;
		font: 12px/ 1.5 arial, sans-serif;;
		text-align: center
	}
}

@media screen and (max-width:1600px) {
	body {
		background: url(moquu/img/4.jpg);;
		font: 12px/ 1.5 arial, sans-serif;;
		text-align: center
	}
}

@media screen and (max-width:1200px) {
	body {
		background: url(moquu/img/1.jpg);;
		font: 12px/ 1.5 arial, sans-serif;;
		text-align: center
	}
}





*{
	margin:0;
	padding:0;
}
body, button, input, select, textarea {
	font:12px/1.5 tahoma, helvetica, arial, \5b8b\4f53, sans-serif;
}
/** text-align:center;**/
body{ background:url(bg_login.jpg) repeat-x #d8ecf3;}
#con{ width:906px; height:541px; margin:0 auto; background:url(login.jpg) no-repeat;}
.height285{ height:267px; width:100%;  display:block;}
.login_input{ height:16px; background-color:#e5f8fc; border:1px solid #99cad8; padding:2px;}
.c1{ color:#3d92a7; width:220px;}
.c2{color:#a8a8a8; width:220px;}
.c3{
	color:#900; width:120px;
}
.red1{ color:#C30;}
.login_button{ background:url(login.png) no-repeat; width:130px; height:120px; border:none; cursor:pointer; vertical-align:middle;}
a.fpw{ color:#3d92a7; text-decoration:none;}
a.fpw:hover{ color:#3d92a7; text-decoration: underline;}
</style>
	</head>
	<script>
	 
	
	if(window.parent.window.location != window.location)
	{
		window.parent.window.location = window.location;
	}
	</script>
	<body>
		<div id="con">
			<div class="height285">
			</div>
			<form id="loginForm" name="loginForm" method="post">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="20">
							&nbsp;
						</td>
						<td align="left" valign="middle">
							<span class="red1" style="display:none">登陆失败!</span>
						</td>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td width="30%" height="39">
							&nbsp;
						</td>
						<td width="31%" align="left" valign="top">
							<input tabindex="1" type="text" class="login_input c1" id="userName" maxlength="20" name="userName" value="" />
						</td>
						<td width="39%" rowspan="3" align="left" valign="middle">
							<input tabindex="4" type="button" class="login_button" onclick="javascript:login();" />
						</td>
					</tr>
					<tr>
						<td height="37">
							&nbsp;
						</td>
						<td align="left" valign="top">
							<input tabindex="2" type="password"" id="parampw" name="parampw"  class="login_input c2"     value=""  />
						 
						</td>
					</tr>
					<tr id="codeTr" style="display:">
						<td height="44">
							&nbsp;
						</td>
						<td align="left" valign="top">
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>

									<td>
										<input tabindex="3" id="sysCheckCode" name="sysCheckCode" type="text" class="login_input c3" maxlength="4"/>
									</td>
									<td>
										&nbsp;
										<img id="checkCodeImg" onclick="javascript:changeCode();" style="cursor: pointer;" src="<cms:BasePath/>common/authImg.do?count=4&line=3&point=120&width=90&height=24&jump=4" />
									</td>
								</tr>
							</table>


						</td>
					</tr>
					
					
					<tr id="phoneCodeTr" style="display:none">
						<td height="44">
							&nbsp;
						</td>
						<td align="left" valign="top">
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>

									<td>
										<input tabindex="3" id="phoneCheckCode" name="phoneCheckCode" type="text" class="login_input c3" maxlength="6"/>
									</td>
									<td>
										&nbsp;
								<a href="#" >点击发送验证码</a>
								</td>
								</tr>
							</table>


						</td>
					</tr>

				</table>
			</form>

		</div>
		
	 
	</body>
</html>
<script type="text/javascript">

function changeCode()
{
	$('#checkCodeImg').attr('src','<cms:BasePath/>common/authImg.do?count=4&line=3&point=120&width=90&height=24&jump=4&rand='+Math.random());

}

function hiddenPass(e, value) {
    e = e ? e : window.event; //
    var pass = document.getElementById("parampw");
    var j_pass = document.getElementById("parampw");
    var key = e.which ? e.which : e.keyCode;
 
    if (key == 13) {e.returnvalue = false; } //
    var keychar = String.fromCharCode(key);
    var pattern = /\w/g;//
    if (pattern.test(key)) {
        j_pass.value = j_pass.value + keychar;
        j_pass.value = j_pass.value.substring(0, pass.length);
     
        document.getElementById("parampw1").value = value.replace(/./g, '*');//
    }
    else {
        if (key == 46) {//
            j_pass.value = j_pass.value.remove(j_pass.value.length-2,1);
        }
        else { event.returnvalue = false;
        }
    }
}


// 获取浏览器类型
function getBrowserType() 
{

    var ua = navigator.userAgent.toLowerCase();

    if (ua == null) return "ie";

    else if (ua.indexOf('chrome') != -1) return "c1";//返回chrome有bug,只能返回代号

    else if (ua.indexOf('opera') != -1) return "opera";

    else if (ua.indexOf('msie') != -1) return "ie";

    else if (ua.indexOf('safari') != -1) return "safari";

    else if (ua.indexOf('firefox') != -1) return "firefox";

    else if (ua.indexOf('gecko') != -1) return "gecko";

    else return "ie";

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
	    
	       
	     var pkey = 'no_pk';

	      
	     $.ajaxSettings.async = false;
	   
	     $.get("<cms:BasePath/>appbiz/appClientGetPK.do",function(data,status)
	     { 
	    	var jsond = eval("("+data+")");
				        
	     	pkey =  replaceAll(jsond.pr_key, '**!12**', '+');
	        
	     	if(pkey == null || pkey == '' || pkey == 'no_pk')
	     	{
	     		alert("通信异常");
	     		
	     		return;
	     	}
		    
		 });
 	   
	   // $('#parampw').val($('#parampw').val().substring(0,$('#parampw1').val().length));
	   
	    var ndu =  $('#userName').val();
	    
	    var ndp =  $('#parampw').val();
	   	   
	    var encrypt = new JSEncrypt();
	    
        encrypt.setPublicKey(pkey);
        
        var data = encrypt.encrypt($('#parampw').val());
       
	    $('#parampw').val(data)
	    
	    data = encrypt.encrypt($('#userName').val());
       
	    $('#userName').val(data)
	   
		var url = "<cms:BasePath/>login/postLogin.do";
        
        var postData =encodeURI($("#loginForm").serialize());
        
        
        postData = encodeData(postData);
      
       
		$.ajax({
				      		type: "POST",
				       		url: url,
				       		data: postData,
				   
				       		success: function(mg)
				            {      
				            	var msg = eval("("+mg+")");
				            	
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
				               		
				               		$('#userName').val(ndu);
	   	   
	                                $('#parampw').val(ndp); 
				               		
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
				               	 
				               		$.dialog.tips('登录成功！正在跳转...',3600,'loading.gif');
				               		window.location.href = '<cms:BasePath/>core/console/main_blue.jsp';
				               		
				               		
				               }
				               else if('-999' == msg)
				               {
				               		$('#codeTr').hide();
				               		$('#phoneCodeTr').show();
				               		
				               		$('#userName').val(ndu);
	   	   
	                                $('#parampw').val(ndp); 
				               		
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
							       			window.location.reload();
							       } 
							                    
								  });
				               } 
				                
				              
				            }
		});	

      
     
}


</script>

