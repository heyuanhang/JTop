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
		 
		 basePath = '<cms:BasePath/>';
		 
         
         
         var ref_flag=/^[\w-]{1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5a-zA-Z\w-]{2,30}$/;
         
         
         $(function()
		 {
		    validate('apiName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
 		
 			 
 		
 				
		 })
    
      	</script>
	</head>
	<body>

		
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
						<!--main start-->
						<div class="addtit">
							<img src="../style/icons/network-wireless.png" width="16" height="16" />接口信息
						</div>
			
						<form id="acForm" name="acForm" method="post">
						 						
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								<tr>
									<td width="25%" class="input-title">
										<strong>接口名称</strong>
									</td>
									<td  class="td-input">
										<input type="text" style="width:285px" id="apiName" name="apiName" class="form-input"  ></input>
										 
									</td>
								</tr>
								<tr>
									<td class="input-title">
										<strong>访问Flow</strong>
									</td>

									<td class="td-input">
										<input type="text" style="width:285px" id="flowPath" name="flowPath" class="form-input" ></input>
										 
									</td>
								</tr>
								<tr>
									<td class="input-title">
										<strong>接口保护</strong>
									</td>

									<td class="td-input">
										<input type="checkbox" size="50"  id="mustTok" name="mustTok" class="form-checkbox" value="1">Token验证</input>&nbsp;&nbsp;
										
										<input type="checkbox" size="50"  id="mustEnc" name="mustEnc" class="form-checkbox" value="1">传输加密</input>&nbsp;&nbsp;
										
										<input type="checkbox" size="50"  id="mustSecTok" name="mustSecTok" class="form-checkbox" value="1">扩展权限</input>
										 
									</td>
								</tr>
								
								<tr>
									<td class="input-title">
										<strong>请求类型</strong>
									</td>

									<td class="td-input">
										<input type="radio" size="50"   name="reqMethod" class="form-checkbox" value="get">GET请求</input>&nbsp;&nbsp;&nbsp;
										
										<input type="radio" size="50"   name="reqMethod" class="form-checkbox" value="post">POST提交</input>&nbsp;&nbsp;
										
										 
										 
									</td>
								</tr>
								<tr>
									<td width="20%" class="input-title">
										<strong>扩展权限类</strong>
									</td>

									<td class="td-input">
										<textarea id="extBehaviorClass" name="extBehaviorClass" style="width:285px; height:60px;" class="form-textarea"></textarea>
									</td>
								</tr>
								 


								
							</table>
							<!-- hidden -->
				  	 

						</form>
						<div style="height:15px"></div>
						<div class="breadnavTab" >
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a href="javascript:submitSecForm();" id="submitTagClassFormBut" class="btnwithico"><img id="submitTagClassFormImg" src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
										<a href="javascript:close();" class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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
   
   
function submitSecForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('apiName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
        
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

	 	
	var msg = validateExistFlag('apiCfg', $('#flowPath').val());
		
		if('0' != msg)
		{
			 
			showTips('flowPath', "以下标识已存在");
			
			return;
		}
		
	 
	
	$("#submitTagClassFormBut").attr("disabled","disabled");
	$("#submitTagClassFormImg").attr("disabled","disabled") 
	 
	
	var url = "<cms:BasePath/>appbiz/addApiCfg.do"+"?<cms:Token mode='param'/>";
 	var postData = encodeURI($("#acForm").serialize());
 					
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
	    				width: '170px', 
	    				height: '60px', 
	    				parent:api,
	                    lock: true, 
	    				icon: '32X32/succ.png',
	                    content: '增加接口配置成功！', 
	                    ok: function(){ 
      						W.window.location.reload();
    					} 
		  		});
           		
           } 	
           else
           {         
           						$("#submitTagClassFormBut").removeAttr("disabled"); 
								$("#submitTagClassFormImg").removeAttr("disabled"); 
								
								
           	    			   W.$.dialog(
							   { 
								   					title :'提示',
								    				width: '200px', 
								    				height: '60px', 
								                    lock: true, 
								                    parent:api,
								    				icon: '32X32/fail.png', 
								    				
								                    content: "执行失败，无权限请联系管理员！",
						
								    				cancel: function(){ 
							      						W.window.location.reload();
							    					} 
								});
								
								
           }   
        }
 	});	

   
}

   

  
</script>
 


