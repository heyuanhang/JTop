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
		    				
		                    content: '编辑权限资源成功!',

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
					
					<cms:Resource id="${param.id}">
						<form id="resourceForm" name="resourceForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								<tr>
									<td width="20%" class="input-title">
										<strong>上级模块</strong>
									</td>
									<td width="80%" class="td-input">
										<input type="text"  style="width:347px" disabled class="form-input" value="${Res.parentResName}"></input>
									</td>
								</tr>
								<tr>
									<td width="23%" class="input-title">
										<strong>资源名称</strong>
									</td>
									<td class="td-input">
										<input type="text" style="width:347px" id="resourceName" name="resourceName" class="form-input" value="${Res.resourceName}"></input>
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>
								
								<tr>
									<td width="23%" class="input-title">
										<strong>目标URL</strong>
									</td>
									<td class="td-input">
										<input type="text" style="width:347px" id="target" name="target" class="form-input" value="${Res.target}"></input>
										
									</td>
								</tr>
								
								<tr>
									<td width="23%" class="input-title">
										<strong>资源标识</strong>
									</td>
									<td class="td-input">
										<input type="text" style="width:347px" id="sysFlag" name="sysFlag" class="form-input" value="${Res.sysFlag}"></input>
										
									</td>
								</tr>
								
								<tr>
									<td width="20%" class="input-title">
										<strong>资源描叙</strong>
									</td>
									<td class="td-input">
										<textarea id="resourceDesc" name="resourceDesc" style="width:345px; height:50px;" class="form-textarea">${Res.resourceDesc}</textarea>
									</td>
								</tr>

								<tr>
									<td width="20%" class="input-title">
										<strong>类型</strong>
									</td>
									<td class="td-input">
										<input name="resourceTypeShow" disabled type="radio" value="5" checked />
										链接
										<input name="resourceTypeShow" disabled type="radio" value="4" />
										资源集合
										<input name="resourceTypeShow" disabled type="radio" value="3" />
										菜单链接

										<input name="resourceTypeShow" disabled type="radio" value="2" />
										模块
										<input name="resourceTypeShow" disabled type="radio" value="1" />
										系统入口
										<input id="resourceType" name="resourceType" type="hidden" value="${Res.resourceType}" />
									</td>
								</tr>

								<tr>
									<td width="20%" class="input-title">
										<strong>权限粒度</strong>
									</td>
									<td class="td-input">
										<input id="dataProtectType" name="dataProtectType" type="radio" value="1" checked onclick="javascript:showDataSec(0);" />
										菜单级粗粒度(初步控制访问)
										<input id="dataProtectType" name="dataProtectType" type="radio" value="2" onclick="javascript:showDataSec(1);" />
										数据级细粒度(结合业务控制)
									</td>
								</tr>

								<tr id="dataSecTr" style="display:none">
									<td width="20%" class="input-title">
										<strong>分类授权目标</strong>
									</td>
									<td class="td-input">
										<select id="dataSecTypeId" name="dataSecTypeId">
											<option value="-1">
												------ 细粒度类型 ------
											</option>
											<cms:QueryData service="cn.com.mjsoft.cms.security.service.SecurityService" method="getAccSecDataTypeTag" objName="At" var="">
											<option value="${At.dataTypeId}">
												${At.typeName}
											</option>
											
											</cms:QueryData>
										
										</select>
										&nbsp;<span class="ps">数据细粒度类型授权,可进行业务扩展</span>
										
									</td>
								</tr>
								
								
								<tr>
									<td width="20%" class="input-title">
										<strong>引导图标</strong>
									</td>
									<td class="td-input">
										<input type="text" style="width:130px" id="icon" name="icon" class="form-input" value="${Res.icon}" />
										
										<input onclick="javascript:openSelectIconDialog('oerd');" value="选择" class="btn-1" type="button" />
										
										
										<img id="icoImg" style="vertical-align: middle;" src="<cms:Domain/>core/style/icons/${Res.icon}" height="16" width="16" />
										&nbsp;<span class="ps">用于菜单或者按钮引导显示</span>
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
							--%></table>
							<!-- hidden -->
							<input type="hidden" id="secResId" name="secResId" value="${Res.secResId}" />
							<input id="userState" name="useState" type="hidden" value="1" />

							<cms:Token mode="html"/>
							
						</form>
						<div style="height:15px"></div>
						<div class="breadnavTab"  >
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a href="javascript:submitEditResourceForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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
initRadio('resourceTypeShow','${Res.resourceType}');
initRadio('dataProtectType','${Res.dataProtectType}');
initSelect('dataSecTypeId','${Res.dataSecTypeId}');

if('2'=='${Res.dataProtectType}')
{
	showDataSec(1);
}

initRadio('useState','${Res.useState}');


var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   


function submitEditResourceForm()
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
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;

	}
	
	 encodeFormInput('resourceForm', false);

   resourceForm.action="<cms:BasePath/>security/editSecurityResource.do";
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

function openSelectIconDialog(target)
{
	W.$.dialog({ 
		    id : 'oscsd',
	    	title : '选取图标',
	    	width: '440px', 
	    	height: '580px', 
	    	parent:api,
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:<cms:BasePath/>core/resources/ShowIcon.jsp?uid='+Math.random()+'&target='+target
	
	});
}

</script>
</cms:Resource>
