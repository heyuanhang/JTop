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

 
	<body style="background:#fafafc;">
<div>
	<div class="blogo">
      <div class="user-box">
        <div class="register-logo"><img src="${ResBase}member/images/find-pw-logo.png"/></div>
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
            <li class="step step--current">
              <span class="step__num">2</span>
              <span>发送确认邮件</span>
              <span class="arrow__background"></span>
              <span class="arrow__foreground"></span>
            </li>
            <li class="step">
              <span class="step__num">3</span>
              <span>设置新密码</span>
            </li>
          </ul>
        </div>
        <div class="m-450-auto step1">
          <div class="yskj_bd">
            <div class="retrieve__title">
              <div class="success">
                <div class="icon"></div>
                <div class="success-text">
                  <h3 class="title">邮件已发送</h3>
                  <h5 class="sub-title">请到<b class="color-o6c"><cms:ReplaceString replace="*" str="'${sessionScope.forgetPwMember.email}'" start="4" end="7"/></b>查阅来自<em>${SiteObj.siteName}</em>的邮件，<br>点击邮件中的链接重设您的登录密码</h5>
                </div>
              </div>
              
              <div class="btn-success">
                <a href="#" class="btn btn-primary">立即打开邮箱收信</a>
                <div class="alert alert-warning">
                  <h6 class="h6-title">没有收到邮件？ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${SiteBase}member/member_forget_pw1.jsp" class="a-text">返回上一步</a></h6>
              
                 
                </div>
              </div>
            </div>
          </div>
        </div>
        
	</div>
</div>

<div class="footer">© 2013 jtopcms.com All Rights Reserved </div>
</body>
</html>


<script>

function sendEmail()
{


	var ev = $('#email').val();

	if(ev == '')
	{
		alert('邮件地址为空');
		return;
	}
 
	var url = '${SiteBase}member/sendResetPwMail.do';
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
			
            	window.location.href = '${SiteBase}member/member_forget_pw2.jsp';
            }
            else if('-1' == msg)
            {
				alert('验证码错误!');
				changeCode()
            	
            }
			else if('-2' == msg)
            {
				alert('邮箱对应的会员不存在!');
				changeCode()
            	
            }
			else
			{
					alert('发送失败!');
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
 
