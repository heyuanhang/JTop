<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script>
		
		basePath='<cms:BasePath/>';
		
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '130px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    		
			                    content: '编辑分类成功!',
	
			    				ok:function()
			    				{ 
	             					W.location.reload();
			    				}
			});   
         }
       		
       	 var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('typeName',0,ref_name,'格式为文字,数字,下划线');
 			validate('typeFlag',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/sticky-notes-pin.png" width="16" height="16" />配置
					</div>
					
					<cms:SystemContentTypeList id="${param.id}">
						<form id="irForm" name="irForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">

								<tr>
									<td width="25%" class="input-title">
										<strong>类别名称</strong>
									</td>
									<td class="td-input">
										<input type="text" style="width:280px" id="typeName" name="typeName" class="form-input" value="${ContentType.typeName}"></input>
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>
								<tr>
									<td class="input-title">
										<strong>访问标识</strong>
									</td>
									<td class="td-input">
										<input type="text" style="width:280px" id="typeFlag" name="typeFlag" class="form-input" disabled value="${ContentType.typeFlag}"></input>

									</td>
								</tr>

							</table>
							<!-- hidden -->
							<input type="hidden" size="10" id="typeId" name="typeId" class="form-input" value="${ContentType.typeId}"></input>
							
							<cms:Token mode="html"/>

						</form>
						<div style="height:15px"></div>
						<div class="breadnavTab"  >
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a href="javascript:submitIrForm();"  name="btnwithicosysflag" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" id="submitTagClassFormImg" /><b>确认&nbsp;</b> </a>
										<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16" /><b>取消&nbsp;</b> </a>
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


function submitIrForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('typeFlag',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('typeName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
     
	    return;

	}
	
	//后台验证
	
	if('${ContentType.typeFlag}' != $('#typeFlag').val())
	{
		var count = validateExistFlag('contentType', $('#typeFlag').val());
		
		if(count > 0)
		{
			showTips('typeFlag', '系统已存在此值，不可重复录入');
			
			return;
		}
	}
	
	disableAnchorElementByName("btnwithicosysflag",true);
	
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	var irFrom = document.getElementById('irForm');
	
	irFrom.action = "<cms:BasePath/>channel/editCt.do";;
	
	irFrom.submit();
}
   

  
</script>
</cms:SystemContentTypeList>
