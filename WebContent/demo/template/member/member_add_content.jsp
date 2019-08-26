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
														<td align="right" width="15%">
															<label for="exampleInputEmail1" class="control-label">
																标题
															</label>

														</td>

														<td>
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td>

																		<input name="title" id="title" type="text" style="width:395px" class="form-control" />

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
																目标栏目
															</label>

														</td>

														<td>
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td>
																		<cms:Class id="${param.classId}">
																			<input name="className" id="className" readonly type="text" style="width:395px" class="form-control" value="${Class.className}"/>
																			<input name="classId" id="classId" readonly type="hidden"  value="${Class.classId}"/>
																		</cms:Class>
																		 
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
																关键字
															</label>

														</td>

														<td>
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td>

																		<input name="keywords" id="keywords" type="text" style="width:395px" class="form-control" />

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
																来源
															</label>

														</td>

														<td>
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td>

																		<input name="author" id="author" type="text" style="width:395px" class="form-control" />

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
																作者
															</label>

														</td>

														<td>
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td>

																		<input name="creator" id="creator" type="text" style="width:395px" class="form-control" value="${Member.memberName}" />

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
																摘要
															</label>

														</td>

														<td>
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td>

																		<input name="summary" id="summary" type="text" style="width:395px" class="form-control" />

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
																内容
															</label>

														</td>

														<td>
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td>

																		<textarea style="width:560px;height:150px" name="mh_wz" id="mh_wz" class="form-control"></textarea>

																	</td>


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
															<table width="40%" border="0" cellpadding="0" cellspacing="0">
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


												</table>


												<!-- 右边结束 -->

											</td>

										</tr>


									</table>





									<input type="hidden" id="sysConfigFlag" name="sysConfigFlag" value="mh_ly" />

								</form>

								<div class="highlight">

									<span class="fr"><button type="button" class="btn btn-primary" onclick="javascript:addContent('draft');">
											存稿
										</button> <cms:WorkflowActionUIHelp classId="${param.classId}">
									
									&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-primary" onclick="javascript:addContent('','${Action.actionId}','${Action.fromStepId}','${Action.toStepId}');">
												${Action.passActionName}
											</button>
										</cms:WorkflowActionUIHelp> </span>
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
		 	
                      
  			function addContent(mode, aid, fid, tid)
			{
				
			
				var cid =$('#classId').val(); 
  
		       if('-1' == cid)
		       {
			     alert('请选择一个投递栏目！');
		         return false;
			
			   }
			
				var url = '${SiteBase}content/clientAddContent.do?contentAddStatus='+mode+'&actionId='+aid+'&fromStepId='+fid+'&toStepId='+tid;
			    var postData = encodeURI($("#memForm").serialize());
			    //alert(postData);
			    $.ajax({
			      	type: "POST",
			       	url: url,
			       	data:postData,
			        dataType:'json',
			   
			       	success: function(msg)
			        {        
			            
			            if('1' == msg)
			            {
							if('draft' == mode)
			            	{
			            		alert('保存稿件成功!');
			            		
			            	}
			            	else
			            	{
								alert('投递稿件成功!请等待管理员处理');
							}
							
							window.location.href = '${SiteBase}member/member_content.jsp';
			            }
			            else if('-1' == msg)
			            {
							alert('请输入正确的验证码!');
			            	
			            }
			            else if('0' == msg)
			            {
							alert('当前栏目不允许投稿!');
			            	
			            }
			            else if('-3' == msg)
			            {
							alert('您的会员权限不够，无法投稿！');
			            	
			            }
						else 
			            {
							alert('投递稿件失败!');
			            	
			            }
						
			        }
			  });	

				 
			}
		
		
function changeCode()
{
	$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4?rand='+Math.random());

}
</script>
	</body>
</html>
</cms:Member>
