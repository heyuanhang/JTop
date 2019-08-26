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
		    				
		                    content: '增加微信用户组成功!',

		    				ok:function()
		    				{ 
       							W.window.location.reload();  
		    				}
			});  
         }
         
         
    
      	</script>
	</head>
	<body>

		 
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
						<!--main start-->
						 

						<form id="exForm" name="exForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								 
								 
											
								  <tr>
											<td width="28%" class="input-title">
													<strong>用户组</strong>
												</td>
												<td class="td-input">
												 		<select class="form-select" id="gid" name="gid"  >
										
															 
															
															<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxUserGroupForTag" objName="UG" var="">
															
															<option value="${UG.wuGroupId}">
																	${UG.wuGroupName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															</option>
															
															 </cms:QueryData>
															 
														</select>										 		 
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

	disableAnchorElementByName("btnwithicosysflag",true);
	
		
    var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
    
   
    W.changeWxUserGroup($('#gid').val());

 
}



function submitForm()
{
	
	 var cidCheck = W.document.getElementsByName('checkedId');
	
	var ids='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value+',';
		}
	}
	
	if('' == ids)
	{
	   W.$.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true,
                    parent:api,
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要换组的用户！', 
       cancel: true 
                    
	  });
	  return;
	}
	
	
	W.$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				parent:api,
                    content: '您确认移动用户组吗？',
                    
                    ok: function () 
                    { 
                    
                    disableAnchorElementByName("btnwithicosysflag",true);
	
		
    				var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
                   
                    var url = "<cms:BasePath/>wx/changeWxUserGroupFromWxServer.do?ids="+ids+"&gid="+$('#gid').val()+"&<cms:Token mode='param'/>";
                    
 		 
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   			
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
			            	
			               if('success' == msg)
			               {
			               		 	
			               		W.$.dialog({ 
				   					title :'提示',
				    				width: '200px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				parent:api,
				                    content: '移动用户组成功!',
				                    
				                    ok: function () 
				                    { 
				                    	W.window.location.reload();
				                    }
				                    
    								
                                 });
			               		
			               	
			               } 
			               else if(mg.indexOf('errcode') != -1)
				           {
				           		W.$.dialog({ 
					   					title :'提示',
					    				width: '270px', 
					    				height: '60px', 
					    				 parent:api,
					                    lock: true, 
					    				icon: '32X32/fail.png',
					                    content: msg, 
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
							
									    				cancel: function () 
									                    { 
									                    	W.window.location.reload();
									                    }
									});
			               }   
			              
			            }
			     	});	
       
       
    				}, 
    				cancel: true 
   	});


}
  
</script>
 
