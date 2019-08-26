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
					
					<div style="height:12px"></div>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<select class="form-select" id="direct">						
						<option value="up" selected> 
							向 上 移动
						</option>
						<option value="down">
							向 下 移动
						</option>

					</select>
					&nbsp;
					<input type="text" id="count" class="form-input" size="6" value="1" />
					&nbsp;行
					<div style="height:10px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:sortRow();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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

function sortRow()
{
	var direct = document.getElementById('direct').value;
	var count = document.getElementById('count').value;
	
	var url = '';
	
	if('true' == '${param.spec}')
	{
		url = "<cms:BasePath/>content/sortSpecInfo.do?typeId=${param.typeId}&type=column&rowFlag=${param.rowFlag}&commFlag=${param.commFlag}"+"&direct="+direct+"&count="+count+"&<cms:Token mode='param'/>";
	}
	else
	{
		url = "<cms:BasePath/>content/sortCommendInfo.do?typeId=${param.typeId}&type=column&rowFlag=${param.rowFlag}&commFlag=${param.commFlag}"+"&direct="+direct+"&count="+count+"&<cms:Token mode='param'/>";
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
	        		W.$.dialog.tips('移位成功...',1);
	        		api.close(); 
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
}

   

  
</script>
