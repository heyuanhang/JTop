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
						重置密码
					</h1>
					<div class="eidt-password">
						<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberSingleMsgForTag" var="${param.msgId}" objName="Msg">
							<form id="memForm" name="memForm" method="post">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">

									<tr>
										<td width="27%" align="right">
											会员名称
										</td>
										<td>
											<cms:ParamCode objName="memId" use="E" target="${param.idKey}" mode="d">

												<cms:Member id="${memId}">
													<input readonly value="${Member.memberName}" type="text"  class="from-input"  />
												</cms:Member>

											</cms:ParamCode>

										</td>
									</tr>
									<tr>
										<td align="right">
											新密码
										</td>
										<td>
											<input id="newPw" name="newPw" type="password" type="text"  class="from-input"  />
																	
										</td>
									</tr>
									
									<tr>
										<td align="right">
											确认新密码
										</td>
										<td>
											<input  type="password" id="checkNewPw" name="checkNewPw" type="text"  class="from-input"  />
																							
										</td>
									</tr>


									<tr>
										<td align="right">
											验证码
										</td>
										<td>
											<table width="75%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>

														<input name="sysCheckCode" id="sysCheckCode" type="text" class="from-input" style="width:80px" maxlength="4" />


													</td>
													<td>
														<img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=1&point=60&width=90&height=32&jump=8" />

													</td>
													<td>

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
											<input type="button" name="button" id="button" value="确认修改" class="btn btn2  btn-primary" onclick="javascript:resetPw();" />
										</td>
									</tr>
								</table>
								
								<input type="hidden" id="key" name="key" value="${param.key}"/>
								<input type="hidden" id="idKey" name="idKey" value="${param.idKey}"/>
								<input type="hidden" id="mailKey" name="mailKey" value="${param.mailKey}"/>
								<input type="hidden" id="regKey" name="regKey" value="${param.regKey}"/>
								
							</form>
					</div>


				</div>
			</div>

		</div>
		<div class="footer">
			© 2013 jtopcms.com All Rights Reserved
		</div>

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
