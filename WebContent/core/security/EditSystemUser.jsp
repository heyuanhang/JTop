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
		basePath='<cms:BasePath/>';
	
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
			    				
			                    content: '编辑管理员成功!',
	
			    				ok:function()
			    				{ 
	       							W.window.location.reload();  
			    				}
				 }); 	 
         	}
       		       
         }
         
         var ref_flag=/^[\w-]{4,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5a-zA-Z\w-]{2,30}$/;
         
         var ref_pw = /^[\w~!@#$%^&*()_+]{6,20}$/;
         
         var ref_email  = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
         
         var ref_phone  = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;

         $(function()
		 {
		    validate('userTrueName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
 			validate('userName',0,ref_flag,'格式为字母,数字,上下划线(至少输入4字)');
 			validate('password',0,ref_pw,'只能输入6-20个字母,数字,特殊字符');	
 			
 			validate('email',0,ref_email,'格式为合法的邮件地址');	
 			
 			validate('phone',0,ref_phone,'格式为合法的手机号码');	
 			
 			validate('relateOrgCode',1,null,null);
 			
 				
		 })
         
        	
      </script>
	</head>
	<body>



		<form id="userForm" name="userForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<!--main start-->
						<div class="addtit">
							<img src="../style/icons/user.png" width="16" height="16" />基本信息
						</div>
						
						<cms:SystemUser id="${param.userId}">
							<div class="auntion_tagRoom_Content">
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
												<tr>
													<td width="24%" class="input-title">
														<strong>所属机构</strong>
													</td>
													<td class="td-input">
														<select  disabled class="form-select" id="relateOrgCodeShow"   style="width:314px">
															<option value="">
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
														<input type="hidden" id="relateOrgCode" name="relateOrgCode" value="${SysUser.relateOrgCode}" />
													</td>
												</tr>
												<tr>
													<td width="24%" class="input-title">
														<strong>用户名称</strong>
													</td>
													<td class="td-input">
														<input type="text" readonly style="width:310px" id="userName" name="userName" class="form-input" value="${SysUser.userName}"></input>
														<span class="red">*</span><span class="ps"></span>
													</td>
												</tr>
												<tr>
													<td class="input-title">
														<strong>真实姓名</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:310px" id="userTrueName" name="userTrueName" class="form-input" value="${SysUser.userTrueName}"></input>
														<span class="red">*</span><span class="ps"></span>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>联系电话</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:310px" id="phone" name="phone" class="form-input" value="${SysUser.phone}"></input>
														<span class="red">*</span><span class="ps"></span>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>email</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:310px" id="email" name="email" class="form-input" value="${SysUser.email}"></input>
														<span class="red">*</span><span class="ps"></span>
													</td>
												</tr>
												
												<tr>
													<td class="input-title">
														<strong>微信OPenId</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:310px" id="weixinName" name="weixinName" class="form-input" value="${SysUser.weixinName}"></input>
														<span class="red"></span><span class="ps"></span>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>用户备注</strong>
													</td>
													<td class="td-input">
														<textarea id="remark" name="remark" style="width:310px; height:70px;" class="form-textarea">${SysUser.remark}</textarea>
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
														<div style="float:right">
															<a id="buttonHref" href="javascript:submitUserForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
														</div>
													</tr>
												</table>
											</div>
										</li>
									</ul>
								</div>

								<!-- 第二部分:所属角色 -->
								<%--<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
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
																<input value="*" onclick="" type="checkbox" />
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
																		<input class="inputCheckbox" id="checkedRoleId-${Role.roleId}" name="checkedRoleId" value="${Role.roleId}" type="checkbox" onclick="javascript:" />
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
														<div class="breadnavTab" style="top:93%">
															<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr class="btnbg100">
																	<td class="input-title" width="73%"></td>
																	<td class="td-input">
																		<a id="buttonHref" href="javascript:submitUserForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
																		<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
																	</td>
																</tr>
															</table>
														</div>
											</div>
										</li>
									</ul>
								</div>

							</div>
					--%>
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

initSelect('relateOrgCodeShow','${SysUser.relateOrgCode}');

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
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('userTrueName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
        
        if(currError)
        {
        	hasError = true;
        }
        
   	currError = submitValidate('userName',0,ref_flag,'格式为字母,数字,上下划线(至少输入4字)');

   		if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('password',0,ref_pw,'只能输入6-20个字母,数字,特殊字符');

   		if(currError)
        {
        	hasError = true;
        }
        
   currError = submitValidate('email',0,ref_email,'格式为合法的邮件地址');	

   		if(currError)
        {
        	hasError = true;
        }
        
   currError = submitValidate('phone',0,ref_phone,'格式为合法的手机号码');	

   		if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('relateOrgCode',1,null,null);	

   		if(currError)
        {
        	hasError = true;
        }
     
    if($('#affirmPassword').val() != $('#password').val())
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '两次输入的密码不一致！!',

		    				cancel: true
		});
		
		return;
    }
    
    
    			
    if(hasError)
    {
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;

	}

    //后台验证
	
	if('${SysUser.userName}' != $('#userName').val())
	{
		var count = validateExistFlag('sysUser', $('#userName').val());
		
		if(count > 0)
		{

			showTips('userName', '系统已存在此值，不可重复录入');
			
			return;
		}
	}

	if('${SysUser.relateOrgCode}' != $('#relateOrgCode').val())
	{
		W.$.dialog({ 
   					title :'提示',
    				width: '290px', 
    				height: '60px', 
                    lock: true, 
                    parent:api,
    				icon: '32X32/i.png', 
    				
                    content: '您修改了机构,这将导致角色全部失效需重新设定！<br/>确定操作吗？', 
                    
                    ok:function()
                    {
                    	encodeFormInput('userForm', false);
                    	userForm.action="<cms:BasePath/>security/editSystemUser.do";
    					userForm.submit();
                    }
                    ,
       				cancel: true 
                    
	
	  });
	}
	else
	{
		encodeFormInput('userForm', false);
		userForm.action="<cms:BasePath/>security/editSystemUser.do";
    	userForm.submit();
	}

    
   
}


  
function close()
{
	api.close();
}

</script>
</cms:SystemUser>
