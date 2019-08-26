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
		    				
		                    content: '增加权限资源成功!',

		    				ok:function()
		    				{ 
       							W.window.location.reload();  
		    				}
			});        
         }
         
         var ref_flag=/^[\w-]{4,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5a-zA-Z\w-]{2,30}$/;
         
            $(function()
		 {
		    validate('resourceName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
 			
 			
 				
		 })
    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/puzzle--plus.png" width="16" height="16" />资源信息
					</div>

					<form id="resourceForm" name="resourceForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">

							<tr>
								<td width="20%" class="input-title">
									<strong>上级模块</strong>
								</td>
								<td width="80%" class="td-input">
									<select name="parentResId" " id="parentResId" style="width:347px">
										<option value="-1">
											------------------ 无(为顶级资源) ---------------------
										</option>
										<cms:DisplayMemberSecurityResource />
									</select>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>
							<tr>
								<td width="23%" class="input-title">
									<strong>资源名称</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:345px" id="resourceName" name="resourceName" class="form-input"></input>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>
							<tr>
								<td width="23%" class="input-title">
									<strong>目标URL</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:345px"  id="target" name="target" class="form-input"></input>

								</td>
							</tr>

							<tr>
								<td  class="input-title">
									<strong>资源标识</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:345px"  id="sysFlag" name="sysFlag" class="form-input"></input>

								</td>
							</tr>



							<tr>
								<td width="20%" class="input-title">
									<strong>资源描叙</strong>
								</td>
								<td class="td-input">
									<textarea id="resourceDesc" name="resourceDesc" style="width:345px; height:50px;" class="form-textarea"></textarea>
								</td>
							</tr>

							<tr>
								<td width="20%" class="input-title">
									<strong>类型</strong>
								</td>
								<td class="td-input">
									<span id="resTypeSpan"> <input name="resourceType" type="radio" value="5" checked /> 链接 <input name="resourceType" type="radio" value="4" /> 资源集合 <input name="resourceType" type="radio" value="3" /> 菜单链接 <input name="resourceType" type="radio" value="2" /> 模块 <input name="resourceType" type="radio" value="1" /> 系统入口 </span>
								</td>
							</tr>

						

							<tr>
								<td width="20%" class="input-title">
									<strong>引导图标</strong>
								</td>
								<td class="td-input">
									<input type="text" size="25" id="icon" name="icon" class="form-input" value="${Res.icon}" />
									<img src="<cms:Domain/>core/style/icons/${Res.icon}" height="16" width="16" />
									&nbsp;
									<span class="ps">图标用于菜单或按钮引导显示</span>
								</td>

							</tr>

							<%--<tr>
								<td width="20%" class="input-title">
									<strong>状态</strong>
								</td>
								<td class="td-input">
									<input id="userState" name="useState" type="radio" value="1" checked />
									<span class="STYLE12">启用</span> &nbsp;
									<input id="userState" name="useState" type="radio" value="0" />
									<span class="STYLE12">停用</span>
								</td>

							</tr>
						--%>
						</table>
						<!-- hidden -->
						<input id="userState" name="useState" type="hidden" value="1" />
						
						<input id="dataProtectType" name="dataProtectType" type="hidden" value="1" />
						
						 <cms:Token mode="html"/>
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:submitResourceForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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



var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   
function submitResourceForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('resourceName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
        
        if(currError)
        {
        	hasError = true;
        }
        
  
    
    
    			
    if(hasError)
    {
    	    
	    return;

	}
	
	//后台验证
	var url = "<cms:BasePath/>security/checkSecType.do?isMember=true&parentId="+$('#parentResId').val()+'&resType='+$('[name=resourceType]:checked').val();
	 
	  	$.ajax({
		      	type: "POST",
		       	url: url,
		       	async:false,
		       	data:'',
		   
		       	success: function(mg)
		        {       
		        	hideTips('resTypeSpan');
		        	
		        	var msg = eval("("+mg+")");
		        	
		        	hasError = true;
		        	 
		        	if('1' == msg)
		        	{
		        		showTips('resTypeSpan','上级为入口下级类型必须为模块');
		        	}
		        	else if('2' == msg)
		        	{
		        		showTips('resTypeSpan','上级为模块下级类型必须为菜单');
		        	}
		        	else if('3' == msg)
		        	{
		        		showTips('resTypeSpan','上级为菜单下级类型必须为资源集合');
		        	}
		        	else if('4' == msg)
		        	{
		        		showTips('resTypeSpan','上级为集组合下级类型必须为操作链接');
		        	}
		        	else if('5' == msg)
		        	{
		        		showTips('resTypeSpan','操作链接下不可添加任何资源');
		        	}
		        	else if('6' == msg)
		        	{
		        		showTips('resTypeSpan','顶级入口只允许添加入口节点');
		        	}
		        	else
		        	{
		        		hasError = false;
		        	}
		        }
		 });	
	
	if(hasError)
    {
         
	    return;

	}
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	encodeFormInput('resourceForm', false);

   var resourceForm = document.getElementById('resourceForm');

   resourceForm.action="../../security/addMemberSecurityResource.do";
   resourceForm.submit();
   
}

function showDataSec(flag)
{ 
	var dataSecTr = document.getElementById('dataSecTr');
	//var sysFlagTr = document.getElementById('sysFlagTr');
	
	if(flag  == 1)
	{
    	dataSecTr.style.display = '';
    	//sysFlagTr.style.display = '';
    }
    else
    {
    	dataSecTr.style.display = 'none';
    	//sysFlagTr.style.display = 'none';
    }
}

</script>
