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
						<cms:LoginUser>
						<img src="../style/blue/icon/application-import.png" width="16" height="16" />可选站点&nbsp;:&nbsp;<select class="form-select" id="targetSiteId" name="targetSiteId" onchange="javascript:changeSite(this);"><option value="-1">---------- 无站点 ----------</option><cms:SystemOrgSite orgId="${Auth.orgIdentity}"><option value="${OrgSite.siteId}">${OrgSite.siteName}</option></cms:SystemOrgSite></select>
						</cms:LoginUser>
					</div>

					<iframe src="SingleSiteSelectClassTree.jsp?siteId=${param.siteId}&classId=${param.classId}&linear=${param.linear}" height="495" width="100%" id="classTree" scrolling="yes" frameborder="0"></iframe>

					<div class="breadnavTab" >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:selectTargetParentClass();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
								</div>

							</tr>
						</table>
					</div>

				</td>
			</tr>


		</table>
		<form id='moveContentClassForm' name='moveContentClassForm' method="post">
			<input type="hidden" id='classId' name='classId' value='${param.classId}' />
			<input type="hidden" id='currentParent' name='currentParent' value='${param.parent}' />
			<input type="hidden" id='selectParent' name='selectParent' />
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

function selectTargetParentClass()
{
    if(selectedTargetClassId != '')
    {
    	
		
		if(false)
		{
		   W.$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '请选择需要移动的栏目！', 
	       				cancel: true 
		  });
		  return;
		}
		
		 	
		var selectParent = selectedTargetClassId;
        var currentParent = document.getElementById('currentParent').value;
        var classId = document.getElementById('classId').value;
        
      	 	
	 	var url = '<cms:BasePath/>channel/getClassTreeInfo.do?id='+selectParent;
  
	 	
	 	$.ajax({
	      	type: "GET",
	       	url: url,
	       	data:'',
	   		dataType:'json',
	       	success: function(msg)
	        {         
	        	 if('to' == '${param.mode}')
	        	 {
	        	 	W.document.getElementById('linkToClassShow').value=msg;
	        	 	W.document.getElementById('linkToClass').value=selectParent;
	        	 }
	        	 else
	        	 {
	        	 	W.document.getElementById('linkFromClassShow').value=msg;
	        	 	W.document.getElementById('linkFromClass').value=selectParent;
	        	 }
		        
		         api.close();
	        	 
	        }
	     });	
	     
	     return;    	
    }
    else
    {
     	api.close();
    }
	
  
    
    
}
 

initSelect('targetSiteId','${param.siteId}');

   function changeSite(obj)
{
	if('-1' != obj.value)
	{
		replaceUrlParam(window.location,'siteId='+obj.value);
	}
}

  
</script>
