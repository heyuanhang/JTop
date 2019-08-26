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
            <li class="step step--current">
              <span class="step__num">1</span>
              <span>确认账号</span>
              <span class="arrow__background"></span>
              <span class="arrow__foreground"></span>
            </li>
            <li class="step">
              <span class="step__num">2</span>
              <span>验证/修改</span>
              <span class="arrow__background"></span>
              <span class="arrow__foreground"></span>
            </li>
            <li class="step">
              <span class="step__num">3</span>
              <span>设置新密码</span>
            </li>
          </ul>
        </div>
        <form id="memForm" name="memForm" method="post">
        <div class="m-450-auto step1">
          <div class="yskj_bd fl-register">
            <ul class="ul">
              <li>
                <label>邮箱地址：</label>
                <input id="email" name="email" type="text" class="txt1 txt"  ></input>
              </li><%--
              <div class="err_tip">
                <div class="dis_box"><em class="icon_error"></em><span>此帐号不存在</span></div>
              </div>
              --%><li id="captchas" class="positon-r">
                <label>验证码：</label>
               <input name="sysCheckCode" id="sysCheckCode" class="txt1 txt"  style=" width:130px;" />
										<a href="javascript:changeCode();" class="yzm-img"><img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=6&line=1&point=100&width=145&height=24&jump=4" /> </a>
              </li>
              <li class="reg">
             <div style="width:100px; height:40px;" class="fl"></div>
              <input type="button" name="dosubmit" value="下一步" class="btn btn4 btn-primary" onclick="javascript:sendEmail();"></input>
              </li>
            </ul>
            
          </div>
        </div>
        </form>
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
