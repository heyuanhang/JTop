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
		<script>
		basePath='<cms:BasePath/>';
		
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
		    		
		                    content: '新增系统模型成功!',

		    				ok:function()
		    				{ 
       							W.window.location.reload();  
		    				}
			});     
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('modelName',0,ref_name,'格式为文字,数字,下划线');
 			validate('modelSign',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
       
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/ruler--pencil.png" width="16" height="16" />基本信息
					</div>

					<form id="modelForm" name="modelForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="22%" class="input-title">
									<strong>模型名称: 
								</td>
								<td class="td-input">
									<input id="modelName" name="modelName" type="text" class="form-input" style="width:400px" />
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>模型标识: 
								</td>
								<td class="td-input">
									<input id="modelSign" name="modelSign" type="text" class="form-input" style="width:400px" />
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>描叙:</strong>
								</td>
								<td class="td-input">
									<textarea id="remark" name="remark" class="form-textarea" style="width:400px; height:55px;"></textarea>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>私有模型: 
								</td>
								<td class="td-input">
									<input type="radio" name="privateMode" value="0" checked />
									否
									<input type="radio" name="privateMode" value="1" />
									是
									<span class="ps">若为私有模型,则只在当前站点有效,其他站点不可使用</span>
								</td>
							</tr>
							
							<tr>
									<td class="input-title">
										<strong>引导图标</strong>
									</td>
									<td class="td-input">
										<input type="text" size="25" id="ico" name="ico" class="form-input" value="document.png" />
										
										<input onclick="javascript:openSelectIconDialog('ocm');" value="选择" class="btn-1" type="button" />
										
										
										<img id="icoImg" style="vertical-align: middle;" src="<cms:Domain/>core/style/icons/document.png" height="16" width="16" />
									</td>

								</tr>


						</table>
						<!-- hidden -->
						<input type="hidden" id="modelType" name="modelType" value="${param.modelType}" />
						
						<cms:Token mode="html"/>

					</form>
						<div style="height:15px"></div>
					<div class="breadnavTab"  >
					<table width="100%" border="0" cellspacing="0" cellpadding="0" >
						<tr class="btnbg100">
							<div style="float:right">
								<a name="btnwithicosysflag" href="javascript:submitModelForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
								<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
							</div>
							 
						</tr>
					</table>
					</div>


				</td>
			</tr>

			<tr>
				<td height="10">
					&nbsp;
				</td>
			</tr>
		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  

function submitModelForm()
{
   var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('modelSign',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('modelName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
         
	    return;

	}
	
	//后台验证
	
	
		var count = validateExistFlag('model', $('#modelSign').val());
		
		if(count > 0)
		{

			showTips('modelSign', '系统已存在此值，不可重复录入');
			
			return;
		}
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
    var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
    
    encodeFormInput('modelForm', false);
	
   modelForm.action="<cms:BasePath/>metadata/addDataModel.do";
   modelForm.submit();
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

function close()
{
	api.close();
}
   
  
</script>
