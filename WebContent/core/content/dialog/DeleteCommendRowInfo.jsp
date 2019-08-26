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
         
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../../style/blue/icon/application-import.png" width="16" height="16" />
						行数据
					</div>
					
					<div style="height:7px"></div>
					<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

						<tr class="datahead">
							<td width="2%">
							<input type="checkbox" name="checkAll"  onclick="javascript:selectAll('checkInfo',this);"/>
							</td>

							<td width="30%">
								<strong>标题</strong>
							</td>
						</tr>

						<cms:SysCommendRow commFlag="${param.commFlag}" rowFlag="${param.rowFlag}">
							<tr>
								<td>
									<input type="checkbox" name="checkInfo" value="${RowInfo.infoId}" />
								</td>

								<td>
									&nbsp;${RowInfo.title}
								</td>
							</tr>
						</cms:SysCommendRow>

					</table>
		
					<div style="height:10px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:deleteRowInfo();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
								</div>
							</tr>
						</table>
					</div>

				</td>
			</tr>


		</table>
		<form method="post" id="commendForm" name="commendForm">
			<input type="hidden" id="contentId" name="contentId" value="${param.contentId}" />

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
}

function deleteRowInfo()
{
	var cidCheck = document.getElementsByName('checkInfo');
	
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
	                    parent:api,
	    				icon: '32X32/i.png', 
	    				
	                    content: '没有选择要删除的内容！', 
	       				cancel: true 
		  });
		  return;
		}
		
		
		
		W.$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
                    parent:api,
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选内容吗？',
                    
                    ok: function () { 
                    
                    	var url = '';
		
						if('true' == '${param.spec}')
						{
							url = "../../../content/deleteSpecRow.do?typeId=${param.typeId}&rowFlag=${param.rowFlag}&commFlag=${param.commFlag}&ids="+ids+"&mode=2"+"&<cms:Token mode='param'/>";
	 					}
						else
						{
							url = "../../../content/deleteCommRow.do?typeId=${param.typeId}&rowFlag=${param.rowFlag}&commFlag=${param.commFlag}&ids="+ids+"&mode=2"+"&<cms:Token mode='param'/>";
	 					}
                    
                    	 
					 	$.ajax({
					      	type: "POST",
					       	url: url,
					       	data:'',
					       	dataType:'json',
					   
					       	success: function(msg)
					        {        
					        	if('success' == msg)
					        	{				        		
					        		W.window.location.reload();
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
				       
				    	}, 
				    	cancel: true 
				                    
					
		});
}

   

  
</script>
