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

			<script>
			  basePath='<cms:BasePath/>';
		
			</script>

			<script type="text/javascript" src="${ResBase}js/dialog/lhgdialog.min.js?skin=iblue"></script>
					<!-- 配置文件 -->
			<script type="text/javascript"
				src="${ResBase}member/js/ueditor/ueditor.config.js"></script>
			<!-- 编辑器源码文件 -->
			<script type="text/javascript"
				src="${ResBase}member/js/ueditor/ueditor.all.gzjs"></script>

			<cms:Set
			val="'fullscreen', 'source', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough','removeformat','forecolor', 'backcolor', 'formatmatch', 'autotypeset', 'link', 'unlink', 'anchor','fontfamily', 'fontsize','insertorderedlist', 'insertunorderedlist','undo', 'redo'"
			id="UE_SMP" />


			<!--头部结束-->

			<cms:Member loginMode="true">
		</div>

		<div class="container">

			<!--左边开始-->
			<cms:Include page="common/member_left.jsp" />
			<!--左边开始-->

			<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberSingleContentForTag" var="${param.contentId}" objName="Info">

				<div class="my_myresume_r">

					<div class="switchBox" id="switchBox">
						<h1 class="new_section">
							稿件编辑
						</h1>
						<div class="eidt-password">
							<form id="memForm" name="memForm" method="post">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="20%" align="right">
											投稿标题
										</td>
										<td>
											<input name="title" id="title" type="text" class="from-input" value="${Info.title}" />
										</td>
									</tr>
									<tr>
										<td align="right">
											目标栏目
										</td>
										<td>
											<cms:Class id="${Info.classId}">
												<input name="className" id="className" readonly type="text" class="from-input" value="${Class.className}" />
												<input name="classId" id="classId" readonly type="hidden" value="${Class.classId}" />
											</cms:Class>

										</td>
									</tr>
									<tr>
										<td align="right">
											关键字
										</td>
										<td>
											<input name="keywords" id="keywords" type="text" class="from-input" value="${Info.keywords}" />
										</td>
									</tr>
									<tr>
										<td align="right">
											文章来源
										</td>
										<td>
											<input name="author" id="author" type="text" class="from-input" value="${Info.author}" />
										</td>
									</tr>
									<tr>
										<td align="right">
											作者
										</td>
										<td>
											<input name="creator" id="creator" type="text" class="from-input" value="${Member.memberName}" />
										</td>
									</tr>
									<tr>
										<td align="right">
											摘要
										</td>
										<td>
											<input name="summary" id="summary" type="text" class="from-input" value="${Info.summary}" />
										</td>
									</tr>
									<tr>

									<td colspan="2">
										<textarea id="mh_wz" name="mh_wz" style="height:290px"  >${Info.mh_wz}</textarea>
										<input type="hidden" id="mh_wz_jtop_sys_hidden_temp_html" name="mh_wz_jtop_sys_hidden_temp_html" >
															 
						                                 	
						               <script type="text/javascript">
						                 var editor1_mh_wz = UE.getEditor('mh_wz',{zIndex : 99, autoFloatEnabled:false, allowDivTransToP: false, enableAutoSave:false, scaleEnabled:true, maximumWords:20000, toolbars: [[${UE_SMP}]]}); 
									</script>

									</td>
								</tr>
									<tr>
										<td align="right">
											验证码
										</td>
										<td>
											<table border="0" cellpadding="0" cellspacing="0">
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
											<input type="button" name="button" id="button" value="存稿" class="btn btn2 fl btn-primary" onclick="javascript:editContent('draft');" />

											<cms:WorkflowActionUIHelp classId="${param.classId}">
										&nbsp;&nbsp;
										<button type="button" class="btn btn2  btn-primary" onclick="javascript:editContent('','${Action.actionId}','${Action.fromStepId}','${Action.toStepId}');">
													${Action.passActionName}
												</button>
											</cms:WorkflowActionUIHelp>
											&nbsp;&nbsp;
											<input type="button" name="button" id="button" value="返回" class="btn btn2  btn-primary" onclick="javascript:goBack();" />

										</td>
									</tr>
								</table>

								<!-- hidden -->
								<input type="hidden" id='contentId' name="contentId" value="${Info.contentId}" />
							</form>
						</div>


					</div>
				</div>
		</div>
		<div class="footer">
			© 2013 jtopcms.com All Rights Reserved
		</div>

		<script type="text/javascript">

			function editContent(mode, aid, fid, tid)
			{			
				var cid =$('#classId').val(); 
  
		       if('-1' == cid)
		       {
			     alert('请选择一个投递栏目！');
		         return false;
			
			   }
			   
			   
			
				var url = '${SiteBase}content/clientEditContent.do?contentAddStatus='+mode+'&actionId='+aid+'&fromStepId='+fid+'&toStepId='+tid;
			    var postData = encodeURI($("#memForm").serialize());
			    //alert(postData);
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
			            	if('draft' == mode)
			            	{
			            		alert('保存稿件成功!');
			            		
			            	}
			            	else
			            	{
								alert('投递稿件成功!请等待管理员处理');
							}
							
							window.location.href = '${SiteBase}member/member_all_content.jsp';
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
			
			function goBack()
			{	
				window.location.href = '${SiteBase}member/member_all_content.jsp';
			
			}
</script>
	</body>
</html>


</cms:QueryData>
</cms:Member>
