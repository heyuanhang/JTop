<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>门户演示站 - 本站基于JTopcms构建</title>
		<!--[if IE 7]>
			<link rel="stylesheet" href="css/font-awesome-ie7.css">
			<![endif]-->
		<link href="${ResBase}css/font-awesome.min.css" rel="stylesheet" type="text/css"></link>
		<link href="${ResBase}css/base.css" rel="stylesheet" type="text/css"></link>
		<link href="${ResBase}css/content.css" rel="stylesheet" type="text/css"></link>



		<script>  
		 basePath = '<cms:BasePath/>';
		 
				
			 

        	
      </script>


	</head>


	<body>
		<!--头部开始-->
		<cms:Include page="../include/head.jsp" />
		<!--头部结束-->

		<!--主体开始-->
		<div class="main_box">
			<!--左侧-->
			<div>

				<div>
					<!--详情页开始-->
					<div>
						<h1 class="ep-h1 bigtitle">

						</h1>

						<!--登录-->
						<div class="bs-example">
							<div class="tie-titlebar">
								<span style="left:120px; top:30px; font-size:18px; font-weight:bold; padding:10px 15px; color:#06c">重置密码</span>
								<ins>

								</ins>
							</div>
							<form id="memForm" name="memForm" method="post">

								<table width="100%" border="0" cellpadding="0" cellspacing="0">

									<tr>
										<td>

										</td>

										<td>


											<!-- 右边开始 -->

											<table width="100%" border="0" cellpadding="0" cellspacing="12">


												<tr>
													<td align="right" width="19%">
														<label for="exampleInputEmail1" class="control-label">
															会员名称
														</label>

													</td>

													<td>
														<table width="100%" border="0" cellpadding="0" cellspacing="0">
															<tr>
																<td>
																	<cms:ParamCode objName="memId" use="E" target="${param.idKey}" mode="d">
																	
																	<cms:Member id="${memId}">
																	<input readonly value="${Member.memberName}" type="text" style="width:395px" class="form-control" />
																 		</cms:Member>
																	
																	</cms:ParamCode>
																<td>

																</td>

															</tr>

														</table>


													</td>
												</tr>
												
												
												<tr>
													<td align="right" width="19%">
														<label for="exampleInputEmail1" class="control-label">
															新密码
														</label>

													</td>

													<td>
														<table width="100%" border="0" cellpadding="0" cellspacing="0">
															<tr>
																<td>
																	 	
																 
																	<input id="newPw" name="newPw" type="password" type="text" style="width:395px" class="form-control" />
																	 
																<td>

																</td>

															</tr>

														</table>


													</td>
												</tr>
												
												<tr>
													<td align="right" width="19%">
														<label for="exampleInputEmail1" class="control-label">
															确认新密码
														</label>

													</td>

													<td>
														<table width="100%" border="0" cellpadding="0" cellspacing="0">
															<tr>
																<td>
																	 	
																 
																	<input  type="password" id="checkNewPw" name="checkNewPw" type="text" style="width:395px" class="form-control" />
																	 
																<td>

																</td>

															</tr>

														</table>


													</td>
												</tr>
												
												<tr>
													<td align="right" width="15%">
														验证码:

													</td>

													<td>
														<table width="30%" border="0" cellpadding="0" cellspacing="0">
															<tr>
																<td>

																	<input name="sysCheckCode" id="sysCheckCode" type="text" class="form-control" style="width:80px" maxlength="4" />

																</td>
																<td>
																	<img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4" />

																</td>
																<td>

																	<a style="cursor: pointer;" onclick="javascript:changeCode();">重刷</a>
																</td>

															</tr>
														</table>


													</td>


												</tr>

												</tr>









											</table>


											<!-- 右边结束 -->

										</td>

									</tr>


								</table>
								
								<input type="hidden" id="key" name="key" value="${param.key}"/>
								<input type="hidden" id="idKey" name="idKey" value="${param.idKey}"/>
								<input type="hidden" id="mailKey" name="mailKey" value="${param.mailKey}"/>
								<input type="hidden" id="regKey" name="regKey" value="${param.regKey}"/>



							</form>

							<div class="highlight">

								<span class="fr"><button type="button" class="btn btn-primary" onclick="javascript:sendEmail();">
										确认
									</button> </span>
							</div>
						</div>





					</div>
					<!--新闻详情页结束-->

				</div>


			</div>




		</div>

		<!--主体结束-->
		<div class="main_br"></div>
		<!--主体结束-->

		<cms:Include page="../include/foot.jsp" />

		<script>
		 	
                      
function sendEmail()
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
	$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4?rand='+Math.random());
}

function goBack()
{	
	window.location.href = '${SiteBase}member/member_message.jsp';

}

</script>
	</body>
</html>

