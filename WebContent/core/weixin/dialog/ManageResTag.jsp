<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script>
		 var selectedTargetClassId = '';
		 
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
                api.close(); 
               
       		W.window.location.reload();
         }
         
         //表格变色
			$(function()
			{ 
		   		$("#showlist tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
			});  
			
			 var tag_name = /^[\u0391-\uFFE5\w]{1,50}$/;
			 
			 $(function()
		     {
			 	validate('tagName',0,tag_name,'格式为文字,数字,下划线');
         	 })
      	</script>
	</head>
	<body>
		<form method="post" id="rtForm" name="rtForm">
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					 
					<div class="fl" style="padding: 10px 2px;">
						分类名称:&nbsp;
						<input id="tagName" name="tagName" size="30" maxlength="60" class="form-input" />
						<input onclick="javascript:addResTag();" value="添加" class="btn-1" type="button" style="vertical-align:top;" />
					</div>

					 

					 
					</div>




					<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

						<tr class="datahead">
						 

							<td width="14%">
								<strong>分类名称</strong>
							</td>

						 

							<td width="5%">
								<strong>操作</strong>
							</td>
						</tr>

						<cms:QueryData objName="Tag" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getResTagForTag" var="${param.type}">

							<tr>
								 

								<td>
									${Tag.resTagName}
								</td>

								 

								<td>
									 	<a href="javascript:deletResTag('${Tag.rtId}')"><img src="../../../core/style/default/images/del.gif" alt="" />删除</a>&nbsp;&nbsp;&nbsp;
														 
								</td>
							</tr>
						</cms:QueryData>
						<cms:Empty flag="Tag">
							<tr>
								<td class="tdbgyew" colspan="7">
									<center>
										当前没有数据!
									</center>
								</td>
							</tr>
						</cms:Empty>


					 


					</table>
					<div style="height:2px"></div>
					  
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									 
									<a href="javascript:close();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>关闭&nbsp;</b> </a>
								 </div>

							</tr>

						</table>
					</div>

				</td>
			</tr>


		</table>
		
		 
		</form>
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
	
	W.window.location.reload();
}

function addResTag()
{
	var tagName = $('#tagName').val();
	
	 
	
	var error = submitValidate('tagName',0,tag_name,'格式为文字,数字,下划线');
	
    if(error)
    {
    	return;
    }
    
    disableAnchorElementByName("btnwithicosysflag",true);
	
		
     
    var url = "<cms:BasePath/>wx/addResTag.do"+"?<cms:Token mode='param'/>&type=${param.type}";
 
 	var postData = encodeURI($("#rtForm").serialize());
 			
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
	                    content: '添加资源分类成功！', 
	                    ok: function(){ 
      						api.get('omrtd').window.location.reload();
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



function deletResTag(id)
{
	 
	W.$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
                    parent:api,
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除图片资源分类吗？',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>wx/deleteResTag.do?id="+id+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
			 		W.$.ajax({
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
				                    parent:api,
				    				icon: '32X32/succ.png', 
				    				
				                    content: '删除图片资源分类成功!',
				                    
				                    ok: function () 
				                    { 
				                    	api.get('omrtd').window.location.reload();
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
									                    	api.get('omrtd').window.location.reload();
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
