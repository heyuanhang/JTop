<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>门户演示站 - 本站基于JTopcms构建</title>

		 	<link href="${ResBase}member/css/down-menu.css" rel="stylesheet" type="text/css"></link>
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

						<div class="mr_creatr fr">
							<a href="javascript:deleteMemContent();">删除</a>
							<a href="javascript:toAddContent();">投稿</a>
						</div>
						<span>我的稿件</span> &nbsp;&nbsp;&nbsp;

						<select id="classId" name="classId">
							<option value="-1">
								------------- 目标栏目 -------------
							</option>
							<cms:Class idList="child:root">

								<cms:if test="${Class.memberAddContent == 1}">
									<option value="${Class.classId}">
										${Class.className}
									</option>
								</cms:if>

							</cms:Class>

						</select>



					</h1>
					<div class="con_detail2">
						<form id="form1" name="form1" method="post">
							<div class="title" id="site-nav">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<th width="320" align="left">
											<input type="checkbox" name="checkbox" onclick="javascript:selectAll('checkedId',this)" />
											全选
										</th>
										<th>
											<div class="quick-menu">


												<div class="mytaobao menu-item">
													<div class=menu>
														<a class="menu-hd"><span id="state">所有内容</span><b></b> </a>
														<div class=menu-bd>
															<div class=menu-bd-panel>
																<div>
																	<a href="javascript:changeState('');">所有内容</a>
																	<a href="javascript:changeState('0');">审查中</a>
																	<a href="javascript:changeState('1');">已发布</a>
																	<a href="javascript:changeState('-1');">稿件</a>

																</div>
															</div>
															<s class=r></s><s class=rt></s><s class=lt></s><s class=b></s><s class="b b2"></s><s class=rb></s><s class=lb></s>
														</div>
													</div>
												</div>
											</div>
										</th>
										 
										 
										<th width="105">
											状态
										</th>
										<th width="85">
											时间
										</th>
										<th width="95">
											操作
										</th>
									</tr>
								</table>
							</div>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-b">
								<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberContentForTag" var="8,${param.pn},${param.state}" objName="Info">
									<tr>
										<td width="22">
											<input class="form-checkbox" name="checkedId" value="${Info.contentId}" type="checkbox" onclick="javascript:" />

										</td>
										<td width="280">
											${Info.title}
										</td>
										 
										<td width="105" align="center">
											<cms:if test="${Info.censorState == 1}">已发布</cms:if>
											<cms:elseif test="${Info.censorState == 0}">审核中</cms:elseif>
											<cms:elseif test="${Info.censorState == -1}">草稿</cms:elseif>
											<cms:else></cms:else>
										</td>
										 
										<td width="85" align="center">
											<cms:FormatDate date="${Info.addTime}" />
										</td>
										<td align="center">
											<a href="javascript:toEdit('${Info.censorState}','${Info.contentId}');">编辑</a>
										</td>
									</tr>

								</cms:QueryData>

							</table>
							<!--分页代码-->
							<div class="fenye">
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
							</div>
						</form>
					</div>


				</div>
			</div>

		</div>
		<div class="footer">
			© 2013 jtopcms.com All Rights Reserved
		</div>

		<script type="text/javascript">
	 
		
		if('0' == '${param.state}')
		{
			$('#state').html('审查中');
		}
		else if('1' == '${param.state}')
		{
			$('#state').html('已发布');
		}
		else if('-1' == '${param.state}')
		{
			$('#state').html('稿件');
		}
		else
		{
			$('#state').html('所有内容');
		}
		
		

		
			function toAddContent()
			{
			
			   var cid =$('#classId').val(); 
  
		       if('-1' == cid)
		       {
			     alert('请选择一个投递栏目！');
		         return ;
			   
			   }
			   
			   window.location.href = 'member_push_content.jsp?classId='+cid;

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
		
		
		    function changeState(flag)
		    {
		    	 
		    	
		    	replaceUrlParam(window.location,'state='+flag);
  
		    
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
				$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=60&width=90&height=32&jump=8&rand='+Math.random());
			
			}
</script>
	</body>
</html>

</cms:Member>
