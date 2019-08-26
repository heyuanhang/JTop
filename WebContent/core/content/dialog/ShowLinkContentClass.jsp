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
		 var checkedClassMap;
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
          
            api.close(); 
         	     
       		W.window.location.reload();
         }
         
      	</script>
	</head>
	<cms:CurrentSite>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<cms:LoginUser>
						<img src="../../style/blue/icon/application-import.png" width="16" height="16" />可选站点&nbsp;:&nbsp;<select class="form-select" id="targetSiteId" name="targetSiteId" onchange="javascript:changeSite(this);"><option value="-1">---------- 无站点 ----------</option><cms:SystemOrgSite orgId="${Auth.orgIdentity}"><option value="${OrgSite.siteId}">${OrgSite.siteName}</option></cms:SystemOrgSite></select>
						</cms:LoginUser>
					</div>
  
					<iframe src="ClassTree.jsp?siteId=${param.siteId}&modelId=${param.modelId}&currentRefClassIdStr=<cms:UrlParam target="${param.refClassIdStr}" />" height="495" width="100%" id="classTree" scrolling="yes" frameborder="0"></iframe>

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

initSelect('targetSiteId','${param.siteId}');
  
function close()
{
	api.close();
}

function selectTargetClass(flag)
{
	var allMode = '${param.all}';

    if('manage' == flag && checkedClassMap.mapSize != 0)
    {
    	if('true' != allMode)
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
		    				
		                    content: '请选择需要引用的内容！', 
		       				cancel: true 
			  });
			  return;
			}
		}
		
		disableAnchorElementByName("btnwithicosysflag",true);
		
		
		var tip = W.$.dialog.tips('正在执行...',9600000000,'loading.gif');
		
		
		var url = "<cms:BasePath/>content/linkContent.do?all="+allMode+"&classId="+W.currentClassId+"&contentIds="+ids+"&linkClassIds="+checkedClassMap.allKeyToString(',')+"&<cms:Token mode='param'/>";
	 		
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
	    				
	                    content: '内容引用成功！', 
	       				ok: function()
	       				{ 
	       					tip.close();
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
	else if(checkedClassMap.mapSize != 0)
	{
    	W.document.getElementById('sysCopyClassCount').innerHTML = checkedClassMap.mapSize;
    	W.document.getElementById('copyClassIds').value = checkedClassMap.allKeyToString(',');
    	W.document.getElementById('sysCopyContentImg').title = '复制到:  ' + checkedClassMap.allValueToString(', ');
    }
    
    api.close();
    
    
}
   
function changeSite(obj)
{
	if('-1' != obj.value)
	{
		replaceUrlParam(window.location,'siteId='+obj.value);
	}
}
   

  
</script>
</cms:CurrentSite>
