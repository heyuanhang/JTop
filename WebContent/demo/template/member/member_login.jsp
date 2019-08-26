<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>门户演示站 - 本站基于JTopcms构建</title>


		<link href="${ResBase}member/css/style.css" rel="stylesheet" type="text/css"></link>
		<link href="${ResBase}member/css/login.css" rel="stylesheet" type="text/css"></link>

		<script type="text/javascript" src="${ResBase}js/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="${ResBase}js/common.js"></script>



	</head>

	 
	<body>
		<div class="login-main">
			<div class="login-main_m">
				<div class="login-main_bd">

					<div class="yskj step1">
						<div class="yskj_tt">
							<ins>
								还没有账号？
								<a id="sigup_now1" class="btn3" href="${SiteBase}member/member_reg.jsp">免费注册</a>
							</ins>
							<b class="login-logo"><img src="${ResBase}member/images/login-logo.png"/><a href="${SiteBase}"></a></b>
						</div>
						<form id="memForm" name="memForm" method="post">

							<div class="yskj_bd fl-login">
								<ul class="ul">
									<li>
										<label>
											邮箱/用户名：
										</label>
										<%--
										err_label
										--%>	 
										<input name="memberName" id="memberName"   type="text" class="txt1 txt " placeholder="邮箱/用户名"></input>
									</li>
									<div class="err_tip" style="display:none">
										<div class="dis_box">
											<em class="icon_error"></em><span>请输入手机号码</span>
										</div>
									</div>
									<li>
										<label>
											密码：
										</label>
										<input name="parampw" id="parampw" type="password" value="" class="txt1 txt"   placeholder="密码"></input>
										<input id="parampw1" type="hidden" name="parampw1" value=""/>
									</li>
									<div class="err_tip" style="display:none">
										<div class="dis_box">
											<em class="icon_error"></em><span>请输入手机号码</span>
										</div>
									</div>

									<li id="captchas1" class="positon-r dn">
										<label>
											验证码：
										</label>
										<input name="sysCheckCode" id="sysCheckCode" class="txt1 txt" placeholder="验证码" style=" width:130px;" />
										<a href="javascript:changeCode();" class="yzm-img"><img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=1&point=100&width=100&height=34&jump=7" /> </a>
									</li>


									<li>
										<div class="form-field">
											<div class="fl">
												<input type="checkbox" id="rememberMember" checked onclick="javascript:checkRem();" />
												<label for="checkbox">
													记住帐号
												</label>
											</div>
											<a href="${SiteBase}member/member_forget_pw1.jsp" class="fr">忘记密码？</a>
										</div>
									</li>
									<li class="login-padding">
										<div style="width:140px; height:40px;" class="fl"></div>
										<input type="button" name="dosubmit" value="登录" class="btn btn4 fl btn-primary" onclick="javascript:loginMember();"></input>

									</li>
								</ul>

							</div>
						</form>
					</div>
					<div class="thirdlogin">
						<div class="m-box-login">
							<h4>
								用第三方帐号直接登录&nbsp;&nbsp;&nbsp;&nbsp;
							</h4>
							<ul>
								<li id="sinal">
									<a href="${SiteBase}member/thirdLoginReq.do?flag=weibo">微博账号登录</a>
								</li>
								<li id="qql">
									<a href="${SiteBase}member/thirdLoginReq.do?flag=qq">QQ账号登录</a>
								</li>
							</ul>

						</div>
					</div>
				</div>
			</div>
		</div>



		<!--注册弹出代码-->

		<div id="reg_setp">
			<div class="back_setp">
				返回
			</div>
			<div class="blogo">
				<div class="user-box">
					<div class="register-logo">
						<img src="${ResBase}member/images/register-logo.png" />
					</div>
				</div>
			</div>
			<div class="user-box" id="setp_quicklogin">
				<div class="register-form">
					<div class="title-item">
						<ins>
							已有帐号，现在 
							<a href="#" id="title-item-a">登录</a>
						</ins>
						<h4 class="title-biggest">
							注册新账号
						</h4>
					</div>
					<div class="regbox step1">
						<form id="form1" name="form1" method="post">
							<div class="yskj_bd fl-register">
								<ul class="ul">
									<li>
										<label>
											邮箱/用户名：
										</label>
										
										<input id="account" name="account" type="text" class="txt1 txt err_label" placeholder="邮箱/用户名"></input>
										<script>
											var rememberMember = getCookie('jtopcms_member_login_name');

											if(rememberMember != null && rememberMember !='')
											{
												$('#memberName').val(rememberMember);
											}
										</script>
									</li>
									<div class="err_tip">
										<div class="dis_box">
											<em class="icon_error"></em><span>请输入手机号码</span>
										</div>
									</div>
									<li>
										<label>
											创建密码：
										</label>
										<input type="text" name="textfield" id="textfield" class="txt1 txt" placeholder="创建密码"></input>
									</li>
									<li>
										<label>
											确认密码：
										</label>
										<input type="text" name="textfield" id="textfield" class="txt1 txt" placeholder="确认密码">
									</li>

									<li id="captchas" class="positon-r">
										<label>
											验证码：
										</label>
										<input name="sysCheckCodexx" id="sysCheckCodexx" class="txt1 txt" placeholder="验证码" style=" width:130px;" />
										<%--<a href="javascript:changeCode();" class="yzm-img"><img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4" /> </a>
									--%></li>
									<li class="reg">
										<div style="width:140px; height:40px;" class="fl"></div>
										<input type="submit" name="dosubmit" value="注册" class="btn btn-primary btn4">
										
									</li>
								</ul>

							</div>

							<div class="thirdlogin-fr">
								<div class="m-box-register">
									<h4>
										用第三方帐号直接登录
									</h4>
									<ul>
										<li id="sinal">
											<a href="#">微博账号登录</a>
										</li>
										<li id="qql">
											<a href="#">微博账号登录</a>
										</li>
									</ul>
								</div>
							</div>
						</form>
					</div>

				</div>
			</div>
			<!--reg_setp end-->
			<script type="text/javascript" src="${ResBase}js/jquery-1.7.min.js"></script>
			<script type="text/javascript" src="${ResBase}member/js/jquery.easing.min.js"></script>
			<!--弹出注册界面-->
			<script type="text/javascript">
  
  $("#sigup_now").click(function(){
		$("#reg_setp,#setp_quicklogin").show();
		$("#reg_setp").animate({left:0},500,"easeOutQuart")
	});																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																								
	
	$(".back_setp,#title-item-a").click(function(){
		"block"==$("#setp_quicklogin").css("display")&&$("#reg_setp").animate({left:"100%"},500,"easeOutQuart",function(){$("#reg_setp,#setp_quicklogin").hide()})
	});
	
	$(document).ready(function(){
   		$("#account").focus();
	}); 

</script>

			<script>

//alert('${sessionScope.loginErrorMore}');
if('true' == '${sessionScope.loginErrorMore}')
{
	$('#captchas1').removeClass('dn');
}

if('-3' == '${param.error}')
{
 alert('当前会员 ${param.memberName} 被停用!');
}

	
 
function loginMember()
{ 
   
     
  //$('#parampw').val($('#parampw').val().substring(0,$('#parampw1').val().length))
 
	var url = "${SiteBase}member/memberLogin.do?<cms:Token mode='param'/>";
    var postData = encodeURI($("#memberName, #parampw, #sysCheckCode").serialize());
    
    $.ajax({
      	type: "POST",
       	url: url,
       	data:postData,
    	dataType: 'json',
    	dataType:'json',
       	success: function(msg)
        {        
          
            if(msg.userId != null)
			{
					alert('登录成功!');
					 
					  
					
					if (document.getElementById('rememberMember').checked == true)
					{
						setLastLoginSuccessUser($('#memberName').val());
					}
				 
				 
					window.location.href = '${SiteBase}member/member_main_page.jsp';

			}
			else if('-1' == msg)
			{		 
					 $('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4&rand='+Math.random());
		     
					 alert('请输入正确的验证码');
					 
					 window.location.reload();
		     		 
					 
			}
			else if('-2' == msg)
			{		 
					 $('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4&rand='+Math.random());
		     
		     		 alert('用户名或密码错误!');
		     		 
		     		 window.location.reload();
					 
			}
			else if('-3' == msg)
			{		 
					 $('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4&rand='+Math.random());
		     
		     		 alert('当前会员被停用!');
		     		 
		     		 window.location.reload();
					 
			}
			else if('-4' == msg)
			{		 
					 $('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4&rand='+Math.random());
		     
		     		 $('#captchas1').removeClass('dn');
		     
		     		 alert('用户名或密码错误!！');		
		     		 
		     		 window.location.reload();			 
			}
			
			
			$('#sysCheckCode').val('');
			
			
			 
        }
     });	
  
   
   
	// var x = document.getElementById('memberRegForm');
	// x.action= url;
	// x.submit();
	
}

function getToken(base, mode)
{
	var nt = "";
	$.ajax
	({
      	type: "POST",
      	async : false,
       	url: base+'common/token.do?mode='+mode,
       	data:'',
   		dataType:'json',
       	success: function(msg)
        {        
        	nt = msg;
        }
    });
	return nt;
}


function checkRem()
{
	if (document.getElementById('rememberMember').checked == false)
	{
		resetCookie();
	
	}


}


 function handler()
 {
      window.location.href = '${SiteBase}member/reg_phone.jsp';
 }
 
function changeCode()
{
	//$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=100&height=34&jump=7?rand='+Math.random());

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

</script>
	</body>
	
</html>
