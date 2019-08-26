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
       		W.window.location.reload();
         }
         
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../../style/blue/icon/application-import.png" width="16" height="16" />
						站点栏目
					</div>

					<iframe src="SingleSelectClassTree.jsp?modelId=${param.modelId}&classId=${param.classId}" height="495" width="100%" id="classTree" scrolling="yes" frameborder="0"></iframe>

					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:selectTargetClass('${param.flag}');"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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

function selectTargetClass(flag)
{
    if(selectedTargetClassId != '')
    {
    	if('' == '${param.all}' || 'true' != '${param.all}')
    	{
	    	var cidCheck = W.document.getElementsByName('checkContent');
		
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
		    				width: '160px', 
		    				height: '60px', 
		                    lock: true, 
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择需要复制的内容！', 
		       				cancel: true 
			  });
			  return;
			}
		}
		
		disableAnchorElementByName("btnwithicosysflag",true);
		
		var tip = W.$.dialog.tips('正在执行...',9600000000,'loading.gif');
		
		
		var url = "<cms:BasePath/>content/moveContent.do?all=${param.all}&contentIds="+ids+"&classId=${param.classId}&moveClassId="+selectedTargetClassId[0]+"&<cms:Token mode='param'/>";
	 	
	 	$.ajax({
	      	type: "POST",
	       	url: url,
	       	data:'',
	   
	       	success: function(mg)
	        {       
	        	var msg = eval("("+mg+")");
           		
	        	if('success' == msg  || '' == mg || mg.indexOf('发布失败') != -1)
	        	{
	        		W.$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	    				parent:api,
	                    lock: true, 
	    				icon: '32X32/succ.png', 
	    				
	                    content: '内容移动成功！', 
	       				ok: function()
	       				{ 
	       					
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
	        	
	        	tip.close();
	        }
	     });	
	     
	     return;    	
    }
	
    api.close();
    
    
}
   

   

  
</script>
