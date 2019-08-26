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
		    				
		                    content: '增加无法识别回复信息成功!',

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
									<td width="24%" class="input-title">
										<strong>信息类型</strong>
									</td>
									<td width="80%" class="td-input">
										<select id="msgType" name="msgType" class="form-select" style="width:347px" >
														 
														<option value="">
															 ------------------  选择用户输入类型  -------------------
														</option>
														<option value="text">
															输入: 文本
														</option>
														
														<option value="image">
															输入: 图片
														</option>
														
														<option value="voice">
															输入: 语音
														</option>
														
														<option value="video">
															输入: 视频
														</option>
														
														<option value="shortvideo">
															输入: 小视频
														</option>
														
														<option value="location">
															输入: 地理位置
														</option>
														
														<option value="link">
															输入: 链接
														</option>

													</select>
									</td>
								</tr>
								
								 
											
								  <tr>
											<td class="input-title">
													<strong>回复选择</strong>
												</td>
												<td class="td-input">
												 		<input type="text" style="width:342px" readonly id="msgTitle" name="msgTitle"  class="form-input" value=""></input>
													  
													<input type="button" value="素材" onclick="javascript:openSelectWxItemInfoDialog( );" class="btn-1" />
												
													<input type="hidden" id="mtId" name="infoId"  class="form-input" value=""></input>
													<input type="hidden" id="resId" name="resId"  class="form-input" value=""></input>
												 
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
	 
	if($('#msgType').val() == '')
        {
           hasError = true;
        	
        	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '170px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个信息类型!',

		    				cancel: true
			});
			
			return;
        }
        
	 
	disableAnchorElementByName("btnwithicosysflag",true);
	
		
    var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
    
   
    var url = "<cms:BasePath/>wx/addWxUnkMsg.do"+"?<cms:Token mode='param'/>";
 
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
	    				width: '170px', 
	    				height: '60px', 
	    				  parent:api,
	                    lock: true, 
	    				icon: '32X32/succ.png',
	                    content: '添加微信无法识别回复成功！', 
	                    ok: function(){ 
      						W.window.location.reload();
    					} 
		  		});
           		
           } 	
           
           else if('exist' == msg)
           {
           					   W.$.dialog(
							   { 
								   					title :'提示',
								    				width: '200px', 
								    				height: '60px', 
								                    lock: true, 
								                    parent:api,
								    				icon: '32X32/fail.png', 
								    				
								                    content: "已经存在相同信息类型！",
						
								    				cancel: function(){ 
								      						window.location.reload();
								    				} 
								});
								
								disableAnchorElementByName("btnwithicosysflag",true);
		
	                            tip.close();
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
