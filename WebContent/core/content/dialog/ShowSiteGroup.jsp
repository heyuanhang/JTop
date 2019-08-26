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
            //W.$.dialog.tips('添加模型步骤成功...',1); 
            api.close(); 
         	//api.reload( api.get('cwa') );        
<%--       		W.window.location.reload();--%>
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
         
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					

					<div style="height:4px"></div>

					<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

						<tr class="datahead">
							<td width="2%">
								<strong> <input type="checkbox" onclick="javascript:selectAll('siteCheck',this);"></input> </strong>
							</td>

							<td>
								<center>
									<strong> 站点名称 </strong>
								</center>
							</td>


						</tr>
						<cms:CurrentSite>
						<cms:Site>
						    <cms:if test="${Site.siteId != CurrSite.siteId}">
							<tr>
								<td>
									<input type="checkbox" name="siteCheck" value="${Site.siteId}-${Site.siteName}" />
								</td>

								<td>
									<center>
										${Site.siteName}
									</center>
								</td>
							</tr>
							</cms:if>
						</cms:Site>
						</cms:CurrentSite>


					</table>
			<div style="height:42px"></div>
			<div class="breadnavTab"  >
				<table width="100%" border="0" cellspacing="0" cellpadding="0" >
					<tr class="btnbg100">
						<div style="float:right">
							<a href="javascript:submitShareContent();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
							<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
						</div>

					</tr>

				</table>
			</div>

			</td>
			</tr>


		</table>
		 
		 
		<input type="hidden" id="cids" name="cids" value="${param.ids}" />
			
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

function submitShareContent()
{
    
    	var cidCheck = document.getElementsByName('siteCheck');
	
		var ids='';
		var siteName='';
		
		var count = 0;
		for(var i=0; i<cidCheck.length;i++)
		{
			if(cidCheck[i].checked)
			{
				ids += cidCheck[i].value.split('-')[0]+',';
				siteName += cidCheck[i].value.split('-')[1]+',';
				count++;
			}
		}
		
		if('' == ids)
		{
		  W.$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	    				parent:api,
	                    lock: true, 
	    				icon: '32X32/i.png',    				
	                    content: '没有选择任何站点！', 
	       				cancel: true 
		  });
		  window.location.reload();
		  return;
		}
		
		
		
		
		
	    if('manage' == '${param.flag}')
		{
			var url = "<cms:BasePath/>content/shareContent.do?ids="+$('#cids').val()+"&siteIds="+ids+"&<cms:Token mode='param'/>";
		 		
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
		    				width: '150px', 
		    				height: '60px', 
		    				parent:api,
		                    lock: true, 
		    				icon: '32X32/succ.png',
		                    content: '共享内容成功！', 
		                    ok: function(){ 
	      						W.window.location.reload();
	    					} 
			  			});
		        	}
		        	else if('close' == msg)
		        	{
		        		W.$.dialog({ 
		   					title :'提示',
		    				width: '170px', 
		    				height: '60px', 
		    				parent:api,
		                    lock: true, 
		    				icon: '32X32/i.png',
		                    content: '当前站点已关闭共享内容！', 
		                    ok: function(){ 
	      						W.window.location.reload();
	    					} 
			  			});
		        	}
		        	else
		        	{
		        		W.$.dialog({ 
		   					title :'提示',
		    				width: '200px', 
		    				height: '60px', 
		    				parent:api,
		                    lock: true, 
		    				icon: '32X32/fail.png', 
		    				
		                    content: "执行失败，无权限请联系管理员！", 
		       				cancel: true 
			  			});
		        	}
		        }
		     });	
	     
	     	return;  
	     }
	     else
	     {
	      
	     	api.get('main_content').document.getElementById('shareSiteIdCount').innerHTML = count;
    		api.get('main_content').document.getElementById('shareSiteIds').value = ids;
    		api.get('main_content').document.getElementById('sysShareContentImg').title = '共享到:  ' + siteName;
	     }  	
    	
    	api.close();
      
}
   

   

  
</script>
