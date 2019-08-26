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
						修改密码
					</h1>
					<div class="eidt-password">
						<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberSingleMsgForTag" var="${param.msgId}" objName="Msg">
							<form id="memForm" name="memForm" method="post">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">

									 
									<tr>
										<td width="27%" align="right">
											标题
										</td>
										<td>
											<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="updateMemberMsgReadFlagForTag" var="${param.msgId}"/>
											<input name="title" id="title" readonly value="${Msg.msgTitle}" type="text" class="from-input"  />
																	
										</td>
									</tr>
									
									 

									<tr>
										<td align="right">
											消息内容
										</td>
										<td>
										
											<textarea   style="width:320px;height:290px"  > ${Msg.msgContent} </textarea>
																			 
																							
										</td>
									</tr>
									

									

									<tr>
										<td align="right">
											&nbsp;
										</td>
										<td>
											<input type="button" name="button" id="button" value="返回" class="btn btn2  btn-primary" onclick="javascript:goBack();" />
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

		
	
 
			function changeCode()
			{
				$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=60&width=90&height=32&jump=8&rand='+Math.random());
			
			}
			
			function goBack()
			{	
				window.location.href = '${SiteBase}member/member_msg.jsp';
			
			}
</script>
	</body>
</html>
</cms:QueryData>
</cms:Member>
