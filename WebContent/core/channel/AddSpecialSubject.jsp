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
		basePath='<cms:BasePath/>';
		 
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            //W.$.dialog.tips('添加模型步骤成功...',1); 
            api.close(); 
         	//api.reload( api.get('cwa') );    
       		//W.window.location.reload();       
       		W.window.parent.location="ManageContentClass.jsp?redirect=true&classId=${param.classId}";
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w-]{1,50}$/;

         $(function()
		 {
		    validate('className',0,ref_name,'格式为文字,数字,上下划线');
 			validate('classFlag',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
       
    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
				 
					<div class="addtit">
							<img src="../style/icon/folder_table.png" width="16" height="16" />配置
					</div>

					<form id="classForm" name="classForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="27%" class="input-title">
									<strong>所属分类</strong>
								</td>
								<td class="td-input">
									<select class="form-select" id="parentShow" name="parentShow" disabled style="width:251px">
										<option value="-9999">
											---------- 请选专题分类 ----------
										</option>
										<cms:CurrentSite>
											<cms:SystemClassList site="${CurrSite.siteFlag}" type="all" isSpec="true" classType="4">
												<cms:SysClass>
													<option value="${Class.classId}">
														${Class.layerUIBlankClassName}
													</option>
												</cms:SysClass>
											</cms:SystemClassList>
										</cms:CurrentSite>
									</select>
								
								</td>
							</tr>
							<tr>
								<td class="input-title">
									<strong>专题名称</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:247px" id="className" name="className" class="form-input"></input>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>
							<tr>
								<td class="input-title">
									<strong>访问标识</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:247px" id="classFlag" name="classFlag" class="form-input"></input>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

						</table>
						<!-- hidden -->
						<input type="hidden" id="classType" name="classType" value="5" />
						
						<input type="hidden" id="parent" name="parent" value="${param.classId}" />
						
						<input type="hidden" id="classId" name="classId" value="${param.classId}" />
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0"  >
							<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:submitContentClassForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" id="submitSpecClassFormImg" /><b>确认&nbsp;</b> </a>
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

initSelect('parentShow','${param.classId}');

//showTips('modelName','不可为空');
   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   
function submitContentClassForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('classFlag',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('className',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
    	 
	    
	    return;

	}
	
	//后台验证
	
	
		var count = validateExistFlag('contentClass', $('#classFlag').val());
		
		if(count > 0)
		{

			showTips('classFlag', '系统已存在此值，不可重复录入');
			
			return;
		}
		
	disableAnchorElementByName("btnwithicosysflag",true);
		
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	var url = "<cms:BasePath/>channel/addSpecSubject.do"+"?<cms:Token mode='param'/>";
	
 	var postData = encodeURI($("#classForm").serialize());
 					
	$.ajax({
  		type: "POST",
   		 url: url,
   		data: postData,
   
       	success: function(mg)
        {     
        
        	var msg = eval("("+mg+")");
        	
           if('success' == msg)
           {
           		W.$.dialog({ 
	   					title :'提示',
	    				width: '150px', 
	    				height: '60px', 
	    				parent:api,
	                    lock: true, 
	    				icon: '32X32/succ.png',
	                    content: '添加专题分类成功！', 
	                    ok: function(){ 
      						W.window.location.reload();
    					} 
		  		});
           		
           } 	
           else
           {
           	   							W.$.dialog(
									    { 
									   					title :'提示',
									    				width: '200px', 
									    				height: '60px', 
									                    lock: true, 
									                    parent:api,
									                     
									    				icon: '32X32/fail.png', 
									    				
									                    content: "执行失败，无权限请联系管理员！",
							
									    				cancel: true
										});
										
										tip.close();
										
									 
           }   
        }
 	});	
}

</script>
