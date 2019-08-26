<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script>  
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {    
         	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '150px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '编辑角色成功!',

		    				ok:function()
		    				{ 
       							W.window.location.reload();  
		    				}
			}); 	 	
               
         }
         
         var ref_name = /^[\u0391-\uFFE5a-zA-Z\w-]{2,30}$/;
         
        

         $(function()
		 {
		    validate('roleName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
 			
 			
 		 
 			
 				
		 })
    
      	</script>
	</head>
	<body>

		<cms:MemberRole id="${param.roleId}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
						<!--main start-->
						<div class="addtit">
							<img src="../style/icons/users.png" width="16" height="16" />会员组
						</div>

						<form id="roleForm" name="roleForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								<tr>
									<td width="25%" class="input-title">
										<strong>会员组名称:</strong>
									</td>
									<td   class="td-input">
										<input type="text" style="width:242px" id="roleName" name="roleName" class="form-input" value="${Role.roleName}"></input>
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>
								 
								<tr>
									<td width="20%" class="input-title">
										<strong>组描叙:</strong>
									</td>
									<td class="td-input">
										<textarea id="roleDesc" name="roleDesc" style="width:242px; height:60px;" class="form-textarea">${Role.roleDesc}</textarea>
									</td>
								</tr>


								<%--<tr>
									<td width="20%" class="input-title">
										<strong>状态:</strong>
									</td>
									<td class="td-input">
										<input id="userState" name="useState" type="radio" value="1" checked />
										<span class="STYLE12">启用</span> &nbsp;
										<input id="userState" name="useState" type="radio" value="0" />
										<span class="STYLE12">停用</span>
									</td>

								</tr>
							--%></table>
						 

							<!-- hidden -->
							<input type="hidden" id="roleId" name="roleId" value="${Role.roleId}" />
							 
							
							<input type="hidden" id="userState" name="userState" value="1" />
							
							<cms:Token mode="html"/>

						</form>
						<div style="height:15px"></div>
						<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:submitRoleForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
								</div>
								 
							</tr>
						</table>
						</div>
					</td>
				</tr>


			</table>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  
   //showTips('modelName','不可为空');
  

   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   
   
function submitRoleForm()
{
   var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('roleName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
        
        if(currError)
        {
        	hasError = true;
        }
        
     

	
    
    if(hasError)
    {
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;

	}
	
	encodeFormInput('roleForm', false);
	
   var roleForm = document.getElementById('roleForm');
   roleForm.action="<cms:BasePath/>security/editMemberRole.do";
   roleForm.submit();
}

   

  
</script>
</cms:MemberRole>
