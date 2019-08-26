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
		    				
		                    content: '增加微信扩展接口成功!',

		    				ok:function()
		    				{ 
       							W.window.location.reload();  
		    				}
			});  
         }
         
         var ref_flag=/^[\w-]{1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5a-zA-Z\w-]{2,30}$/;
         
         
         $(function()
		 {
		    validate('ebName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
 		
 		 	
 		
 				
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
							<img src="../style/icons/zone.png" width="16" height="16" />接口信息
						</div>

						<form id="exForm" name="exForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								<tr>
									<td width="24%" class="input-title">
										<strong>接口名称</strong>
									</td>
									<td width="80%" class="td-input">
										<input type="text" style="width:325px" id="ebName" name="ebName" class="form-input"></input>
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>
								
								<tr>
												<td width="26%" class="input-title">
													<strong>目标事件</strong>
												</td>
												<td class="td-input">
													<select id="eventType" name="eventType" class="form-select"  style="width:328px">
														<option value="-1">
															------------------- 请选择目标事件 ------------------
														</option>
														
														<option value="CLICK">
															菜单: 点击自定义按钮
														</option>
														
														<option value="VIEW">
															菜单: 跳转链接
														</option>
														
														<option value="scancode_push">
															菜单: 扫码推事件
														</option>
														
														<option value="scancode_waitmsg">
															菜单: 扫码推且弹出“消息接收中”提示框
														</option>
														
														<option value="pic_sysphoto">
															菜单: 弹出系统拍照发图
														</option>
														
														<option value="pic_photo_or_album">
															菜单: 弹出拍照或者相册发图
														</option>
														
														<option value="pic_weixin">
															菜单: 弹出微信相册发图器
														</option>
														
														<option value="location_select">
															菜单: 弹出地理位置选择器
														</option>
														<option disabled>
															===========================
														</option>
														<option value="subscribe">
															消息: 用户关注
														</option>
														
														<option value="unsubscribe">
															消息: 用户退订
														</option>
														
														<option value="SCAN">
															消息: 扫描带参数二维码(用户已关注)
														</option>
														
														<option value="LOCATION">
															消息: 上报地理位置
														</option>
														<option disabled>
															===========================
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
									<td  class="input-title">
										<strong>扩展业务类</strong>
									</td>

									<td class="td-input">
										<textarea id="beClass" name="beClass" style="width:325px; height:60px;" class="form-textarea"></textarea>
									</td>
								</tr>
								 


								
							</table>
							<!-- hidden -->
						    <input type="hidden" id="msgType" name="msgType" value="event"/>
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
    var   currError = submitValidate('ebName',0,ref_name,'格式为文字,数字,上下划线(至少输入2字)');
        
        if(currError)
        {
        	hasError = true;
        }
        
   
    
    			
    if(hasError)
    {
        
	    return;

	}
	
	if($('#eventType').val() == '-1')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '200px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个扩展接口类型!',

		    				cancel: true
		});
		
		return;
    }
	
	
	//检查接口是否存在
	var urlCheck = "<cms:BasePath/>wx/checkWxExtend.do";
 
 			
  	var exist = false;
  					
	$.ajax({
  		type: "POST",
  		async: false,
   		 url: urlCheck,
   		data: 'eventType='+$('#eventType').val(),

       	success: function(mg)
        {     
       		var msg = eval("("+mg+")");
       		
           if('-2' == msg)
           {
           		exist = true;
           		
           		W.$.dialog({ 
	   					title :'提示',
	    				width: '300px', 
	    				height: '60px', 
	    				 parent:api,
	                    lock: true, 
	    				icon: '32X32/fail.png',
	                    content: '接口已存在！除菜单事件外，其他事件只可存在一个扩展接口。', 
	                    cancel: function(){ 
      						 
    					} 
		  		});
           		
           } 	
           else if('1' != msg)
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
		
	                            
           }   
        }
 	});	
 
	
	if(exist)
	{
		return;
	}
	 
	disableAnchorElementByName("btnwithicosysflag",true);
	
		
    var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
    
    
    var et = $('#eventType').val();
    
    if('text' == et ||  'image' == et || 'voice' == et 
         || 'shortvideo' == et || 'location' == et || 'link' == et )
    {
    	$('#msgType').val('');
    }
       
    var url = "<cms:BasePath/>wx/addWxExtend.do"+"?<cms:Token mode='param'/>";
 
 	var postData = encodeURI($("#exForm").serialize());
 			
 	postData = encodeData(postData);
 					
	$.ajax({
  		type: "POST",
   		 url: url,
   		data: postData,

       	success: function(mg)
        {     
       		var msg = eval("("+mg+")");
       		
           if('1' == msg)
           {
           		W.$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	    				  parent:api,
	                    lock: true, 
	    				icon: '32X32/succ.png',
	                    content: '添加微信扩展接口成功！', 
	                    ok: function(){ 
      						W.window.location.reload();
    					} 
		  		});
           		
           } 	
           else if('-2' == msg)
           {
           		W.$.dialog({ 
	   					title :'提示',
	    				width: '300px', 
	    				height: '60px', 
	    				 parent:api,
	                    lock: true, 
	    				icon: '32X32/fail.png',
	                    content: '接口已存在！除点击事件外，其他事件只可存在一个扩展接口。', 
	                    cancel: function(){ 
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

   

  
</script>
</cms:LoginUser>
