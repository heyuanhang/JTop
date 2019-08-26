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

		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />


		<script>  
		basePath='<cms:BasePath/>';
		
		var dialogApiId = '${param.dialogApiId}';
		
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
		    				
		                    content: '新增扩展采集规则成功!',

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
		    
 		 	validate('eprName',0,ref_name,'格式为文字,数字,下划线');	
		 })
         
        </script>
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
							<img src="../style/icons/validation-valid-document.png" width="16" height="16" />规则
					</div>

					<form id="flForm" name="flForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							

							<tr>
								<td width="26%" class="input-title">
									<strong>扩展模型</strong>
								</td>
								<td class="td-input">
									<select class="form-select" id="modelId" name="modelId" style="width:262px">
															<option value="-1">
																-------- 可选扩展采集模型 --------
															</option>
															<cms:SystemDataModelList mode="manage" modelType="2" showMode="${param.showMode}">
															<cms:SystemDataModel >
																<option value="${DataModel.dataModelId}">
																	${DataModel.modelName}
																</option>
															</cms:SystemDataModel>
															</cms:SystemDataModelList>
															
														 
									</select>

								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>规则名称</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:258px" id="eprName" name="eprName" class="form-input"></input>
								</td>
							</tr>
							
							<%--<tr>
								<td class="input-title">
									<strong>描叙</strong>
								</td>
								<td class="td-input">
										<textarea id="eprDesc" name="eprDesc" style="height:65px;width:257px" class="form-textarea"></textarea>
									
								</td>
							</tr>

							


						--%></table>
						<!-- hidden -->
						<input type="hidden" id="censorInfo" name="censorInfo" />
						
						<cms:Token mode="html"/>

					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:submitEr();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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



function submitEr()
{
	if($('#modelId').val() == '-1')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个扩展数据模型!',

		    				cancel: true
		});
		
		return;
    }
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('eprName',0,ref_name,'格式为文字,数字,下划线');
        
        if(currError)
        {
        	hasError = true;
        }
        
   
    			
    if(hasError)
    {

	    return;

	}
	
	 
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
    var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');

	var form = document.getElementById("flForm");
	
	form.action = '<cms:BasePath/>pick/createModelExt.do';
	
	form.submit();
}


function close()
{
	api.close();
}


//图片查看效果
loadImageShow();
  
</script>
