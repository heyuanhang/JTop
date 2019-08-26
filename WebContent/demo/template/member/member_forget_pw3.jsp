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
		
		<script>
			 
		
		</script>


	</head>
	
	

	<body style="background:#fafafc;">
		<div>
			<div class="blogo">
				<div class="user-box">
					<div class="register-logo">
						<img src="${ResBase}member/images/find-pw-logo.png" />
					</div>
				</div>
			</div>

			<div class="user-box" id="setp_quicklogin">
				<div class="register-form  main-b-30">
					<!--流程图-->
					<div class="steps-bar">
						<ul>
							<li class="step">
								<span class="step__num">1</span>
								<span>确认邮箱账号</span>
								<span class="arrow__background"></span>
								<span class="arrow__foreground"></span>
							</li>
							<li class="step">
								<span class="step__num">2</span>
								<span>发送确认邮件</span>
								<span class="arrow__background"></span>
								<span class="arrow__foreground"></span>
							</li>
							<li class="step step--current">
								<span class="step__num">3</span>
								<span>设置新密码</span>
							</li>
						</ul>
					</div>
					<form id="memForm" name="memForm" method="post">
						<div id="formDiv" class="m-450-auto step1" style="display:none">
							<div class="yskj_bd fl-register">
								<ul class="ul">
									<li>
										<label>
											会员名称：
										</label>
										<cms:ParamCode objName="memId" use="E" target="${param.idKey}" mode="d">

											<cms:Member id="${memId}">
												<input readonly value="${Member.memberName}" type="text" class="txt1 txt" />
											</cms:Member>

										</cms:ParamCode>

									</li>
									<li>
										<label>
											设置新密码：
										</label>
										<input id="newPw" name="newPw" type="password" type="text" class="txt1 txt" />
									</li>
									<li>
										<label>
											确认密码：
										</label>
										<input type="password" id="checkNewPw" name="checkNewPw" type="text" class="txt1 txt" />

									</li>
									<%--
              <div class="err_tip">
                <div class="dis_box"><em class="icon_error"></em><span>两次输入的密码不一致</span></div>
              </div>
              
              --%>
									<li id="captchas" class="positon-r">
										<label>
											验证码：
										</label>
										<input name="sysCheckCode" id="sysCheckCode" class="txt1 txt" style=" width:130px;" />
										<a href="javascript:changeCode();" class="yzm-img"><img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=6&line=1&point=100&width=145&height=24&jump=4" /> </a>
									</li>
									<li class="reg">
										 <div style="width:100px; height:40px;" class="fl"></div>
										<input type="button" name="dosubmit" onclick="javascript:resetPw();" value="确认修改" class="btn btn4 btn-primary" data-toggle="modal" href="#setpassword-modal" />
									</li>
								</ul>

							</div>
						</div>
						
						

						<input type="hidden" id="key" name="key" value="${param.key}" />
						<input type="hidden" id="idKey" name="idKey" value="${param.idKey}" />
						<input type="hidden" id="mailKey" name="mailKey" value="${param.mailKey}" />
						<input type="hidden" id="regKey" name="regKey" value="${param.regKey}" />

					<div id="errorTipDiv" class="m-450-auto step1" style="display:none">
						当前连接已经失效！
					
					</div>
					
					<script >
						if('1' == '${param.status}')
						{
						 	$('#formDiv').show();
						}
						else  
						{
							$('#errorTipDiv').show();
						 
						}
					</script>

					</form>

				</div>
			</div>

			<div class="footer">
				© 2013 jtopcms.com All Rights Reserved
				<a class="btn03">重置密码</a>
			</div>


			 
			

			 
			<script type="text/javascript" src="${ResBase}member/js/jquery.easing.min.js"></script>
			<script type="text/javascript" src="${ResBase}member/js/modal.js"></script>
			<script type="text/javascript">
			
			

function resetPw()
{


	var ev = $('#newPw').val();

	if(ev == '')
	{
		alert('密码不可为空');
		return;
	}
 
	var url = '${SiteBase}member/resetMemberPw.do';
    var postData = encodeURI($("#memForm").serialize());
    
    $.ajax({
      	type: "POST",
       	url: url,
       	data:postData,
   		dataType:'json',
       	success: function(msg)
        {        
            //alert(msg);
            if('1' == msg)
            {
				alert('修改密码成功!');
            	window.location.href= '${SiteBase}/member/member_login.jsp';
            }
            else if('-1' == msg)
            {
				alert('验证码错误!');
				changeCode()
            	
            }
			else if('-2' == msg)
            {
				alert('会员无效或信息丢失!');
				changeCode()
            	
            }
            else if('-3' == msg || '-4' == msg)
            {
				alert('无效的链接!');
				changeCode()
            	
            }
            else if('-5' == msg)
            {
				alert('两次输入新密码不一样!');
				changeCode()
            	
            }
            else if('-6' == msg)
            {
				alert('请求连接已经失效!');
				changeCode()
            	
            }
			else
			{
					alert('修改失败!');
					//window.location.href = '${SiteBase}member/main.jsp';

			}
        }
     });	

	//window.location.href = '${SiteBase}zcm/memberSendVaEmail.do?targetMail='+$('#email').val().replace("@","#");
}

function changeCode()
{
	$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=6&line=1&point=100&width=145&height=24&jump=4?rand='+Math.random());

}

</script>
	</body>
</html>

