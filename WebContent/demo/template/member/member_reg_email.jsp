<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>门户演示站 - 本站基于JTopcms构建</title>


		<link href="${ResBase}member/css/style.css" rel="stylesheet" type="text/css"></link>
		
		 
		 
	</head>


	<body>
		<div class="header">
			<!--头部开始-->
			<cms:Include page="common/member_head.jsp" />
			<!--头部结束-->

			<cms:Member loginMode="true">
		</div>

		<div class="container">

			<!--左边开始-->
			<cms:Include page="common/member_left.jsp" />
			<!--左边开始-->


			<div class="my_myresume_r">

				<div class="switchBox" id="switchBox">
					<h1 class="new_section">
						验证邮箱
					</h1>
					<div class="eidt-password">
					<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberSingleMsgForTag" var="${param.msgId}" objName="Msg">
						<form id="memForm" name="memForm" method="post">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							
								<tr>
									<td width="27%" align="right">
										认证状态
									</td>
									<td>
										<cms:if test="${Member.isTrueEmail == 1}">
											邮箱已经验证
										</cms:if>
										<cms:else>
											邮箱尚未验证
										</cms:else>
																			 	
									</td>
								</tr>
								<tr>
									<td  align="right">
										邮箱地址
									</td>
									<td>
										<input name="email" id="email" value="${Member.email}" type="text"  class="from-input" />
																			 	
									</td>
								</tr>
								
								
								<tr>
									<td align="right">
										验证码
									</td>
									<td>
									<table width="75%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td  >

														<input name="sysCheckCode" id="sysCheckCode" type="text" class="from-input" style="width:80px" maxlength="4" />
									

													</td>
													<td  >
														<img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=1&point=60&width=90&height=32&jump=8" />

													</td>
													<td  >

														<a style="cursor: pointer;" onclick="javascript:changeCode();">重刷</a>
													</td>

												</tr>
											</table>
										 
									</td>
								</tr>
								
								<tr>
									<td align="right">
										&nbsp;
									</td>
									<td>
										<input type="button" name="button" id="button" value="发验证邮件" class="btn btn2  btn-primary" onclick="javascript:sendEmail();"/>
									</td>
								</tr>
							</table>
						</form>
					</div>


				</div>
			</div>

		</div>
		<div class="footer">
			© 2013 jtopcms.com All Rights Reserved
		</div>
		 
<script type="text/javascript">

function sendEmail()
{


	var ev = $('#email').val();

	if(ev == '')
	{
		alert('地址为空');
		return;
	}
 
	var url = '${SiteBase}member/memberSendVaEmail.do';
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
				alert('发送成功!');
            	
            }
            else if('-1' == msg)
            {
				alert('验证码错误!');
            	
            }
			else if('-3' == msg)
            {
				alert('邮箱已被注册!');
            	
            }
            else if('-4' == msg)
            {
				alert('邮箱已经验证过!');
            	
            }
			else
			{
					alert('验证失败!');
					//window.location.href = '${SiteBase}member/main.jsp';

			}
        }
     });	

	//window.location.href = '${SiteBase}zcm/memberSendVaEmail.do?targetMail='+$('#email').val().replace("@","#");
}
	
 
function changeCode()
{
	$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=60&width=90&height=32&jump=8&rand='+Math.random());

}
</script>
	</body>
</html>
</cms:QueryData>
</cms:Member>
