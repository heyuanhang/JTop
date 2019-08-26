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
		    				width: '200px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '增加微信回复信息成功!',

		    				ok:function()
		    				{ 
       							W.window.location.reload();  
		    				}
			});  
         }
         
         var ref_flag=/^[\w-]{1,25}$/; 
         
          var ref_name = /^[\u0391-\uFFE5a-zA-Z\w-]{1,50}$/;
         
         
         $(function()
		 {
		    validate('inputKey',0,ref_name,'格式为文字,数字,上下划线(至少输入1字)');
 		
 		 	
 		
 				
		 })
    
      	</script>
	</head>
	<body>

		<cms:LoginUser>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
						<!--main start-->
						<div class="addtit">
							<img src="../style/icons/zone.png" width="16" height="16" />回复信息
						</div>

						<form id="exForm" name="exForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								<tr>
									<td width="25%" class="input-title">
										<strong>关键字</strong>
									</td>
									<td width="80%" class="td-input">
										<input type="text" size="52" id="inputKey" name="inputKey" class="form-input"></input>
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>
								
								 
											
								  <tr>
											<td class="input-title">
													<strong>素材选择</strong>
												</td>
												<td class="td-input">
												 		<input type="text" size="52" readonly id="msgTitle" name="msgTitle"  class="form-input" value=""></input>
													  
													<input type="button" value="素材" onclick="javascript:openSelectWxItemInfoDialog( );" class="btn-1" />
												
													<input type="hidden" id="mtId" name="infoId"  class="form-input" value=""></input>
													<input type="hidden" id="resId" name="resId"  class="form-input" value=""></input>
												 
												</td>
								 </tr>
								
								 <tr>
												<td class="input-title">
														<strong>匹配模式
													</td>
													<td class="td-input">
														<input name="isInclude" type="radio" value="0" class="form-radio" checked/>全字&nbsp;
														<input name="isInclude" type="radio" value="1" class="form-radio"/>模糊
														<span class="ps">若为模糊匹配,只需出现关键字即可</span>
												</td>
									</tr>
									
									 <tr>
												<td class="input-title">
														<strong>信息类型
													</td>
													<td class="td-input">
														<input name="isText" type="radio" value="1" class="form-radio" checked/>文本&nbsp;
														<input name="isText" type="radio" value="0" class="form-radio"/>素材
														<span class="ps">选择文本模式,可直接编辑回复文本</span>
												</td>
									</tr>


								
							</table>
							<!-- hidden -->
						 
						</form>
						<div style="height:15px"></div>
						<div class="breadnavTab"  >
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a name="btnwithicosysflag" href="javascript:submitForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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
   //showTips('modelName','不可为空');
   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   
   
function submitForm()
{
	var hasError = false;
	//系统信息字段验证
    var   currError = submitValidate('inputKey',0,ref_name,'格式为文字,数字,上下划线(至少输入1字)');
        
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
    
   
    var url = "<cms:BasePath/>wx/addWxMsg.do"+"?<cms:Token mode='param'/>";
 
 	var postData = encodeURI($("#exForm").serialize());
 			
 	postData = encodeData(postData);
 					
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
	    				width: '160px', 
	    				height: '60px', 
	    				  parent:api,
	                    lock: true, 
	    				icon: '32X32/succ.png',
	                    content: '添加微信自动回复成功！', 
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
						
								    				cancel: function(){ 
								      						W.window.location.reload();
								    				} 
								});
								
								disableAnchorElementByName("btnwithicosysflag",true);
		
	                            tip.close();
           }   
        }
 	});	

 
}


function openSelectWxItemInfoDialog()
{
	W.$.dialog({ 
	    id : 'odcrd',
    	title : '选取素材',
    	width: '860px', 
    	height: '600px', 
    	lock: true, 
        max: false, 
        parent:api,
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/weixin/SelectWxItemInfo.jsp?apiId=ocwmd'
	});
}


  
</script>
</cms:LoginUser>
