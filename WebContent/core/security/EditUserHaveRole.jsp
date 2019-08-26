<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>


		<script>  
		var hasError = false;
		//验证
		$(window).load(function()
		{
			$("#configName").bind('focus', function() 
			{
				var target = $("#adName").val();
				//alert(target);
			    if(target == '')
				{
					hasError = true;
  					showTips('adName','不可为空');
  				}
  				else
  				{
  					hasError = false;
  				}
			});	
			
			$("#adName").bind('propertychange', function() 
						{
						   $( 'div.adName_jtop_ui_tips_class' ).remove();
  							
							var target = $("#adName").val();

    						if(target == '')
    						{
    							hasError = true;
    							showTips('adName','不可为空');               					
  							}
  							else
  							{
  								hasError = false;
  							}
  							
  							
						});
						
						
			
		
		})
	
	     var api = frameElement.api, W = api.opener; 
		
		 function showErrorMsg(msg)
		 {
		
		    W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: msg,

		    				cancel: true
			});
			
		}
      
	
		 
         if("true"==="${param.fromFlow}")
         {  

         	if("${param.error}" === "true")	
         	{
         	     showErrorMsg("<cms:UrlParam target='${param.errorMsg}' />");
         	}
         	else
         	{
	            W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '150px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/i.png', 
			    				
			                    content: '管理员角色操作成功!',
	
			    				ok:function()
			    				{ 
	       							W.window.location.reload();  
			    				}
				}); 
         	}
       		       
         }
         
        	
      </script>
	</head>
	<body>

	

		<form id="userForm" name="userForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<!--main start-->
						<div class="auntion_tagRoom" style="margin-top:2px">
							<ul>
								<%--<li id="two1" onclick="setTab2('two',1,2)" class="selectTag">
									<a href="javascript:;"><img src="../style/blue/icon/application-share.png" width="16" height="16" />用户信息&nbsp;</a>
								</li>
								--%><li id="two2" onclick="setTab2('two',1,1)">
									<a href="javascript:;"><img src="../style/blue/icon/application-search-result.png" width="16" height="16" />可选角色&nbsp;</a>
								</li>




							</ul>
						</div>
						<cms:SystemUser id="${param.userId}">
							<div class="auntion_tagRoom_Content">
								<%--<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
												<tr>
													<td width="24%" class="input-title">
														<strong>所属机构</strong>
													</td>
													<td class="td-input">												
														<select class="form-select" id="relateOrgCode" name="relateOrgCode">
															<option value="-1">
																--------------- 请选择用户所属机构 ---------------
															</option>
															<cms:LoginUser>
																<cms:SystemOrg orgId="${Auth.orgIdentity}" childMode="true">
																	<option value="${Org.linearOrderFlag}">
																		${Org.uiLayerName}
																	</option>
																</cms:SystemOrg>
															</cms:LoginUser>
														</select>

													</td>
												</tr>
												<tr>
													<td width="24%" class="input-title">
														<strong>用户名称</strong>
													</td>
													<td class="td-input">
														<input type="text" size="49" id="userName" name="userName" class="form-input" value="${SysUser.userName}"></input>
														<span class="red">*</span><span class="ps"></span>
													</td>
												</tr>
												<tr>
													<td class="input-title">
														<strong>真实姓名</strong>
													</td>
													<td class="td-input">
														<input type="text" size="49" id="userTrueName" name="userTrueName" class="form-input" value="${SysUser.userTrueName}"></input>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>联系电话</strong>
													</td>
													<td class="td-input">
														<input type="text" size="49" id="phone" name="phone" class="form-input" value="${SysUser.phone}"></input>

													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>email</strong>
													</td>
													<td class="td-input">
														<input type="text" size="49" id="email" name="email" class="form-input" value="${SysUser.email}"></input>
														<span class="red">*</span><span class="ps"></span>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>用户备注</strong>
													</td>
													<td class="td-input">
														<textarea id="remark" name="remark" style="width:325px; height:50px;" class="form-textarea">${SysUser.remark}</textarea>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>状态:</strong>
													</td>
													<td class="td-input">
														<input id="userState" name="useState" type="radio" value="1" checked />
														<span class="STYLE12">启用</span> &nbsp;
														<input id="userState" name="useState" type="radio" value="0" />
														<span class="STYLE12">停用</span>
													</td>

												</tr>

												<!-- 以下为独立选项 start -->


											</table>

											<div style="height:30px;"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<td class="input-title" width="74%"></td>
														<td class="td-input">
															<a id="buttonHref" href="javascript:submitUserForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
														</td>
													</tr>
												</table>
											</div>
										</li>
									</ul>
								</div>

								--%><!-- 第二部分:所属角色 -->
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:s;">
									<div style="height:10px;"></div>
									<ul>
										<li>
											<div class="DataGrid">
												<center>
													<table class="listdate" width="99%" cellpadding="0" cellspacing="0">

														<tr class="datahead">

															<td width="2%" height="30">
																<strong>角色ID</strong>
															</td>

															<td width="1%" height="30">
																<input onclick="javascript:selectAll('checkedRoleId',this);" type="checkbox" />
															</td>

															<td width="7%">
																<strong>角色名称</strong>
															</td>


															<td width="6%">
																<strong>授权机构</strong>
															</td>

														</tr>
														<cms:SystemOrg orgCode="${SysUser.relateOrgCode}">
														<cms:SystemRoleList orgId="${Org.orgId}">
															<cms:SystemRole>
																<tr>
																	<td>
																		${Role.roleId}
																	</td>

																	<td>
																		<input class="inputCheckbox" id="checkedRoleId-${Role.roleId}" name="checkedRoleId" value="${Role.roleId}" type="checkbox"  />
																	</td>

																	<td>
																		<span title="">${Role.roleName}</span>
																	</td>

																	<td>
																		${Role.orgName}
																	</td>

																</tr>
															</cms:SystemRole>
														</cms:SystemRoleList>
														<cms:Empty flag="Role">
															<tr>
																<td class="tdbgyew" colspan="9">
																	<center>
																		当前没有数据!
																	</center>
																</td>
															</tr>
														</cms:Empty>
														</cms:SystemOrg>
													</table>
													<center>
														<div style="height:30px;"></div>
														<div class="breadnavTab"  >
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr class="btnbg100">
																	<div style="float:right">
																		<a id="buttonHref" href="javascript:submitUserForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
																		<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
																	</div>
																</tr>
															</table>
														</div>
											</div>
										</li>
									</ul>
								</div>

							</div>
					</td>
				</tr>
			</table>

			<!-- hidden -->
			<input type="hidden" id="userId" name="userId" value="${SysUser.userId}" />
			
			<cms:Token mode="html"/>


		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">



var checkedRoleArray = new Array();
<cms:SystemRoleList userId="${SysUser.userId}">
	<cms:SystemRole>
	checkedRoleArray.push('${Role.roleId}'); 											
	</cms:SystemRole>
</cms:SystemRoleList>													

var roleBox = null;					
for(var i=0; i<checkedRoleArray.length;i++)
{
	roleBox = document.getElementById('checkedRoleId-'+checkedRoleArray[i]);
	if(roleBox != null)
	{
		document.getElementById('checkedRoleId-'+checkedRoleArray[i]).checked=true;
	}
}							

initRadio('useState','${SysUser.useState}');

function submitUserForm()
{
    userForm.action="<cms:BasePath/>security/editUserRole.do";
    userForm.submit();
   
}


  
function close()
{
	api.close();
}

</script>
</cms:SystemUser>
