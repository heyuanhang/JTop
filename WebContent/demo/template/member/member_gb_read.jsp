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

			<cms:GbInfo gbId="${param.id}">
				<div class="my_myresume_r">

					<div class="switchBox" id="switchBox">
						<h1 class="new_section">
							留言查阅
						</h1>
						<div class="eidt-password">
							<form id="memForm" name="memForm" method="post">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">

									<tr>
										<td width="26%" align="right">
											 类型
										</td>
										<td>
											
											<cms:if test="${GbInfo.mh_lylx == 1}">业务咨询</cms:if>
											<cms:elseif test="${GbInfo.mh_lylx == 2}">改进建议</cms:elseif>
											<cms:elseif test="${GbInfo.mh_lylx == 3}">服务投诉</cms:elseif>
											<cms:elseif test="${GbInfo.mh_lylx == 4}">需求确认</cms:elseif>
											<cms:elseif test="${GbInfo.mh_lylx == 5}">人员查询</cms:elseif>
											<cms:else></cms:else>
											
											 
										</td>
									</tr>
									<tr>
										<td width="26%" align="right">
											留言标题
										</td>
										<td>

											<input name="title" id="title" type="text" readonly class="from-input" value="${GbInfo.gbTitle}" />

										</td>
									</tr>

									<tr>
										<td align="right">
											留言内容
										</td>
										<td>
											<textarea style="width:305px;height:120px"  readonly  class="from-textarea">${GbInfo.gbText}</textarea>
																		


										</td>
									</tr>

									<tr>
										<td align="right">
											回复
										</td>
										<td>
											<textarea style="width:305px;height:120px"  readonly  class="from-textarea">${GbInfo.replyText}</textarea>
																		


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
				window.location.href = '${SiteBase}member/member_gb.jsp';
			
			}
</script>
	</body>
</html>
</cms:GbInfo>
</cms:Member>
