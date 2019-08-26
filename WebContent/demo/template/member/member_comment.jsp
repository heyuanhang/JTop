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
																栏目:
																<select id="classId" name="classId">
																	<option value="-1">
																		------------- 评论栏目 -------------
																	</option>
																	<cms:Class idList="child:root">

																		<cms:if test="${Class.memberAddContent == 1}">
																			<option value="${Class.classId}">
																				${Class.className}
																			</option>
																		</cms:if>

																	</cms:Class>

																</select>
															</label>

														</td>


														<td>
															<label for="exampleInputEmail1" class="control-label">
																评论状态:
																<select id="state" name="state" onchange="javascript:changeState();">
																	<option value="">
																		------ 所有评论 -------
																	</option>

																	<option value="0">
																		待审中
																	</option>

																	<option value="1">
																		已通过
																	</option>

																	<option value="5">
																		未通过
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
															评论内容
														</td>

														<td width="4%">
															状态
														</td>
														<td width="5%">
															评论时间
														</td>

													</tr>

													<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberCommentForTag" var="8,${param.pn},${param.state}" objName="Comm">
														<tr>
															<td>
																<input class="form-checkbox" name="checkedId" value="${Info.contentId}" type="checkbox" onclick="javascript:" />
															</td>

															<td>
																<textarea style="width:390px;height:50px">${Comm.commentText}</textarea>

															</td>

															<td>
																<cms:if test="${Comm.censorState == 1}">已通过</cms:if>
																<cms:elseif test="${Comm.censorState == 0}">待审中</cms:elseif>
																<cms:elseif test="${Comm.censorState == 5}">被否决</cms:elseif>
																<cms:else></cms:else>
															</td>
															<td>
																<cms:FormatDate date="${Comm.commDT}" />

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
                      
  			function toAddContent()
			{
				
			
			   var cid =$('#classId').val(); 
  
		       if('-1' == cid)
		       {
			     alert('请选择一个投递栏目！');
		         return false;
			
			   }
			   
			   window.location.href = 'member_add_content.jsp?classId='+cid;
			
				
				 
			}
			
			function toEdit(state,id)
			{
				if(state != '-1')
				{
					alert('只能编辑草稿！');
					return;
				}
				
				window.location.href = '${SiteBase}member/member_edit_content.jsp?contentId='+id;
			}
		
		
		    function changeState()
		    {
		    	 
		    	
		    	replaceUrlParam(window.location,'state='+$('#state').val());
  
		    
		    }
		    
		    function deleteMemContent()
		    {
		    	var cidCheck = document.getElementsByName('checkedId');
	
				var ids='';
				for(var i=0; i<cidCheck.length;i++)
				{
					if(cidCheck[i].checked)
					{
						ids += cidCheck[i].value+',';
					}
				}
				
				if('' == ids)
				{
					 alert('请选择需要删除的稿件!');
				    
				  return;
				}
		    
		    	var url = '${SiteBase}member/deleteMemContent.do?ids='+ids;
			    var postData = encodeURI($("#memForm").serialize());
			    //alert(postData);
			    $.ajax({
			      	type: "POST",
			       	url: url,
			       	data:postData,
			   		dataType:'json',
			       	success: function(msg)
			        {        
			            alert(msg);
			            if('1' == msg)
			            {
			            	alert('删除稿件成功!');
							
							window.location.href = '${SiteBase}member/member_content.jsp';
			            }
			            else if('-1' == msg)
			            {
							alert('请输入正确的验证码!');
			            	
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
