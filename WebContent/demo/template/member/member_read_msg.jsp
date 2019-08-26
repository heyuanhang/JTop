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

		<cms:Member loginMode="true">
		<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberSingleMsgForTag" var="${param.msgId}" objName="Msg">
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
									<span style="left:120px; top:30px; font-size:18px; font-weight:bold; padding:10px 15px; color:#06c">会员主页</span>
									<ins>

									</ins>
								</div>
								<form id="memForm" name="memForm" method="post">

									<table width="100%" border="0" cellpadding="0" cellspacing="0">

										<tr>
											<td>
												<cms:Include page="../include/member_left.jsp"></cms:Include>
											</td>

											<td>


												<!-- 右边开始 -->

												<table width="100%" border="0" cellpadding="0" cellspacing="12">


													<tr>
														<td align="right" width="15%">
															<label for="exampleInputEmail1" class="control-label">
																信件标题
															</label>

														</td>

														<td>
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td>
																		<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="updateMemberMsgReadFlagForTag" var="${param.msgId}"/>
																		<input name="title" id="title" readonly value="${Msg.msgTitle}" type="text" style="width:395px" class="form-control" />

																	</td>


																	<td>

																	</td>

																</tr>

															</table>


														</td>


													</tr>

													
													<tr>
														<td align="right" width="15%">
															<label for="exampleInputEmail1" class="control-label">
																信件内容
															</label>

														</td>

														<td>
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td>

																		${Msg.msgContent} 

																	</td>


																	<td>

																	</td>

																</tr>

															</table>

														</td>

													</tr>



													


												</table>


												<!-- 右边结束 -->

											</td>

										</tr>


									</table>





									<input type="hidden" id="sysConfigFlag" name="sysConfigFlag" value="mh_ly" />

								</form>

								<div class="highlight">

									<span class="fr"><button type="button" class="btn btn-primary" onclick="javascript:goBack();">
											返回
										</button>   </span>
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
</cms:QueryData>
</cms:Member>
