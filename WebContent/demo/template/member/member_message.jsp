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
														<td width="40%">
															<label for="exampleInputEmail1" class="control-label">
																站内信类型:
																<select id="classId" name="classId">
																	<option value="-1">
																		------------- 全部类型 -------------
																	</option>
																	 

																		 
																			<option value="1">
																				系统发送
																			</option>
																			
																			<option value="1">
																				人工短信
																			</option>


																</select>
															</label>

														</td>


														<td>
															<label for="exampleInputEmail1" class="control-label">
																阅读状态:
																<select id="state" name="state" onchange="javascript:changeState();">
																	<option value="">
																		------ 所有信件 -------
																	</option>

																	<option value="0">
																		未阅读
																	</option>

																	<option value="1">
																		已阅读
																	</option>

																 
																</select>
															</label>

														</td>

														<td align="right">
															<button type="button" class="btn btn-primary" onclick="javascript:deleteMemContent();">
																删除
															</button>
														</td>

													</tr>


												</table>

												<table width="100%" border="0" cellpadding="0" cellspacing="4">
													<tr>
														<td width="3%">

														</td>
														<td width="20%">
															信件标题
														</td>

														<td width="4%">
															状态
														</td>
														<td width="5%">
															发送时间
														</td>

													</tr>

													<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberMsgForTag" var="8,${param.pn},${param.state}" objName="Msg">
														<tr>
															<td>
																<input class="form-checkbox" name="checkedId" value="${Info.contentId}" type="checkbox" onclick="javascript:" />
															</td>

															<td>
															<a href="javascript:gotoMsg('${Msg.msgId}');">${Msg.msgTitle} </a> 

															</td>

															<td>
																<cms:if test="${Msg.isRead == 1}">已读</cms:if>
																<cms:elseif test="${Msg.isRead == 0}">未读</cms:elseif>
															 
																<cms:else></cms:else>
															</td>
															<td>
																<cms:FormatDate date="${Msg.sendDT}" />

															</td>

														</tr>
													</cms:QueryData>

													<cms:PageInfo>
														<tr id="pageBarTr">
															<td colspan="8" class="PageBar" align="left">
																<div class="fr">
																	<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
																	<span class="page">[<a href="javascript:query('h');">首页</a>]</span>
																	<span class="page">[<a href="javascript:query('p');">上一页</a>]</span>
																	<span class="page">[<a href="javascript:query('n');">下一页</a>]</span>
																	<span class="page">[<a href="javascript:query('l');">末页</a>]</span>&nbsp;
																</div>
																<script>
																function query(flag)
																{
																	var cp = 0;
																	
																	if('p' == flag)
																	{
			                                                             cp = parseInt('${Page.currentPage-1}');
																	}
		
																	if('n' == flag)
																	{
			                                                             cp = parseInt('${Page.currentPage+1}');
																	}
		
																	if('h' == flag)
																	{
			                                                             cp = 1;
																	}
		
																	if('l' == flag)
																	{
			                                                             cp = parseInt('${Page.pageCount}');
																	}
		
																	if(cp < 1)
																	{
			                                                           cp=1;
																	}
																
																	
																	replaceUrlParam(window.location,'pn='+cp);		
																}
													
													
																function jump()
																{
																	replaceUrlParam(window.location,'pn='+document.getElementById('pageJumpPos').value);
																}
															</script>
																<div class="fl"></div>
															</td>
														</tr>
													</cms:PageInfo>


												</table>


												<!-- 右边结束 -->

											</td>

										</tr>


									</table>





									<input type="hidden" id="sysConfigFlag" name="sysConfigFlag" value="mh_ly" />

								</form>

								<div class="highlight">

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
		 	
		 	initSelect('state','${param.state}');
                      
  			
			
			
		    function changeState()
		    {
		    	 
		    	
		    	replaceUrlParam(window.location,'state='+$('#state').val());
  
		    
		    }
		    
		   
		    function gotoMsg(id)
		    {
		    
		    	window.location.href = '${SiteBase}member/member_read_msg.jsp?msgId='+id;
		    }
		
function changeCode()
{
	$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4?rand='+Math.random());

}
</script>
	</body>
</html>
</cms:Member>
