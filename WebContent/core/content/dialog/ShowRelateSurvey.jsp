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
					<div class="addtit">
						<img src="../../style/icons/document-task.png" width="16" height="16" />
						当前关联调查
					</div>

					<div class="fl" style="padding: 6px 6px;">
						<a href="javascript:openSelectSurveyDialog();" class="btnwithico" onclick=""><img src="../../style/icons/blue-document-hf-insert-footer.png" width="16" height="16" /><b>选取站点调查&nbsp;</b> </a>
						<a href="javascript:deleteRelateSurvey('${param.contentId}');" class="btnwithico" onclick=""><img src="../../style/icons/blue-document--minus.png" width="16" height="16" /><b>删除关联调查&nbsp;</b> </a>

					</div>

					<div style="padding: 10px 6px;">
						
					</div>

					<div style="height:4px"></div>
					<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

						<tr class="datahead">
							<td width="2%">
								<strong> <input type="checkbox" onclick="javascript:selectAll('checkIds',this);"></input> </strong>
							</td>

							<td width="25%">
								<strong>调查标题</strong>
							</td>

						</tr>
						
						<cms:QueryData objName="Rcid" service="cn.com.mjsoft.cms.content.service.ContentService" method="getRelateSurveyQueryTag" var="${param.rsids}">
						
								<cms:SystemSurverGroup groupId="${Rcid}">
								<tr>
									<td>
										<input type="checkbox" name="checkIds" value="${Group.groupId}" />
									</td>

									<td>
										${Group.questName}
									</td>
							
								</tr>
								</cms:SystemSurverGroup>
							
						</cms:QueryData>
						<cms:Empty flag="Rcid">
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
									<a href="javascript:submitRelateContent();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
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

var currentRids = '${param.rsids}';


var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}

function submitRelateContent()
{
	var ridArray = currentRids.split('*');
	var idStr = null;
	
	var endRids = '';

	var count = 0;
    for ( var i = 0; i < ridArray.length; i++ )
    {
    	if(i == 10)
    	{
    		break;//最大关联限制
    	}
    	
        idStr = ridArray[i];
				
		if(idStr != '')
		{	
            endRids += idStr + "*";
            count++;
        }
    }

    api.get('main_content').document.getElementById('relateSurvey').value = endRids;
    
    api.get('main_content').document.getElementById('sysRelateSurveyCount').innerHTML = count;
	
    api.close();
    
    
}
   
function openSelectSurveyDialog()
{
	W.$.dialog({ 
	    id : 'ospcd',
    	title : '选取站点调查',
    	width: '800px', 
    	height: '560px',
    	parent:api,
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:BasePath/>core/content/dialog/ShowSurvey.jsp?uid='+Math.random()
	});
}

function deleteRelateSurvey()
{
	var cidCheck = document.getElementsByName('checkIds');
	
		var ids='';
		for(var i=0; i<cidCheck.length;i++)
		{
			if(cidCheck[i].checked)
			{
				ids += cidCheck[i].value+'*';
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
	                    content: '没有选择任何调查！', 
	       				cancel: true 
		  });
		  
		  return;
		}


      
        
        var ridArray = ids.split('*');
	    var idStr = null;

        for ( var i = 0; i < ridArray.length; i++ )
        {
            idStr = ridArray[i];
				
			if(idStr != '')
			{	
              currentRids = currentRids.replace( idStr + "*", "");
            }
        }
        
  
            
        replaceUrlParam(window.location,'rsids='+currentRids);   

}
   

  
</script>
