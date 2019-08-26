<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />

		<title></title>
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../common/js/jquery-1.7.gzjs"></script>
		<script language="javascript" type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script>
			
			 
			
			
		</script>
	</head>
	<body>
	
	<form id="renameForm" name="renameForm" method="post">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
				

					
						<table  id="prevSug" width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							 
							
							<tr>
								<td  width="18%" class="input-title">
									<strong>审批意见</strong>
								</td>
								<td class="td-input">
									<textarea id="flowEditSug" style="width:430px; height:255px;" class="form-textarea"></textarea>
									<br/><span class="ps">将发送给下一步骤审核者,您可根据内容特征说明本次审批理由或后期建议</span>
								</td>
							</tr>
						

						</table>
						<!-- hidden -->
						<input type="hidden" id="entry" name="entry" value="${param.entry}" />
							
							 
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
								 
									 <cms:if test="${param.ts > 0}">
									 <a  href="javascript:nextSug();"  class="btnwithico"><img src="../../style/icons/users.png" width="16" height="16" id="submitTagClassFormImg"/><b>指定下一步骤执行人&nbsp;</b> </a>
									 </cms:if>
								 
									<a  href="javascript:addSuggest();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" id="submitTagClassFormImg"/><b><span  id="commitWKB">提交审核</span>&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
								</div>
								 
							</tr>
						</table>
					</div>
				</td>
			</tr>
	
	<cms:if test="${param.mode == 'gb'}">
		<cms:Set val="2" id="infoType" /> 
	</cms:if>
	<cms:else>
		<cms:Set val="1" id="infoType" /> 
	</cms:else>
	 
	</body>
</html>

<script type="text/javascript">

var mode = '${param.mode}';

var nextMan = '';

var prevMan = '';

var actionName = '<cms:DecodeParam str="${param.an}" />';

var actionId = '${param.ai}';

var fromStep = '${param.fs}';

var toStep = '${param.ts}';
 
var toStepId = parseInt(toStep);

var isEnd = false;

if(toStepId < 0)
{
	isEnd = true;
}

var api = frameElement.api, W = api.opener; 
 
var  btn = '审核并通过';
 
if( api.get('main_content') != null )
{
	var wkt = api.get('main_content').wks+'';
	
	
	if(wkt.indexOf('发布')!=-1 && toStepId==-1)
	{
		btn = '审核并发布';
	}
	else if(wkt.indexOf('下线')!=-1 && toStepId==-1)
	{
		btn = '审核并下线';
	}
	else if(wkt.indexOf('删除')!=-1 && toStepId==-1)
	{
		btn = '审核并删除';
	}

}

if(isEnd)
{
	$('#commitWKB').text(btn);
}

 


var curre = '';
 
if( api.get('oegbd') != null )
{
		curre = api.get('oegbd').$('#jtopcms_sys_flow_edit_suguest').val();

		if(curre == '' || typeof(curre) == 'undefined')
		{
			curre = '【该内容无改动】';
		}
}
else
{

	curre = api.get('main_content').$('#jtopcms_sys_flow_edit_suguest').val();
 
	if(curre == '' || typeof(curre) == 'undefined')
	{
		curre = '【该内容无改动】';
	}
}
 	

$('#flowEditSug').val(curre);

//201935:需求改动
	
				//初始化prev是说
				if('${empty param.reload}' == 'true')
				{
					var ps = '';
					 
					
				   // $('#jtopcms_sys_flow_prev_step').val(ps);	
				    
				    if( api.get('main_content') != null )
					{
						 api.get('main_content').window.document.getElementById('jtopcms_sys_flow_prev_step').value = ps;
					}
					else
					{
						 api.get('oegbd').window.document.getElementById('jtopcms_sys_flow_prev_step').value = ps;				 
					}	
				}
	
				 
function addSuggest()
{
	 
	var flowSug = $('#flowSug').val();
	
	var flowEditSug = $('#flowEditSug').val();
	
	if( 'gb' == mode)
	{
		var nextStepMan = api.get('oegbd').window.document.getElementById('jtopcms_sys_flow_next_step').value;
		 
		if('' == nextStepMan && toStepId != -1 && '1' == '${param.dMode}')
		{
			W.$.dialog(
		    { 
			   					title :'提示',
			    				width: '190px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/fail.png', 
			    				
			                    content: '需要指定下一步骤执行人!',
	
			    				cancel: true
			});
			
			return;		
		}
		
		api.get('oegbd').$('#jtopcms_sys_flow_suguest').val(flowSug);
		
		api.get('oegbd').$('#jtopcms_sys_flow_edit_suguest').val(flowEditSug);
		 		 
		api.get('oegbd').replyGbInfo(actionName, actionId, fromStep, toStep); 
	}
	else
	{
		var nextStepMan = api.get('main_content').window.document.getElementById('jtopcms_sys_flow_next_step').value;
		 
		if('' == nextStepMan && toStepId != -1 && '1' == '${param.dMode}')
		{
			W.$.dialog(
		    { 
			   					title :'提示',
			    				width: '190px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/fail.png', 
			    				
			                    content: '需要指定下一步骤执行人!',
	
			    				cancel: true
			});
			
			return;		
		}
	
		api.get('main_content').$('#jtopcms_sys_flow_suguest').val(flowSug);
		
		api.get('main_content').$('#jtopcms_sys_flow_edit_suguest').val(flowEditSug);
		 		 
		api.get('main_content').submitUserDefineContentInfo(actionName, actionId, fromStep, toStep); 
	}	
	 	
	 api.close();      
}


function prevSug()
{
	var infoType = 1;
	
	if( 'gb' == mode)
	{
		infoType = 2;
	}

	W.$.dialog({ 
    	title : '选取已审核过此内容的审核人',
    	width: '650px', 
    	height: '540px',
    	parent:api,
    	lock: true, 
    	max: false,
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/content/dialog/SelectWKMsgUserDialog.jsp?contentId=${param.contentId}&infoType='+infoType
	});

}


function nextSug()
{
	var infoType = 1;
	
	if( 'gb' == mode)
	{
		infoType = 2;
	}
	
	W.$.dialog({ 
    	title : '选取下一步骤审核人',
    	width: '650px', 
    	height: '540px',
    	parent:api,
    	lock: true, 
    	max: false,
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/content/dialog/SelectWKMsgCensorUserDialog.jsp?contentId=${param.contentId}&toStep=${param.ts}&infoType='+infoType
	});

}


function close()
{
	     
    api.close();      
}
				
 
									
</script>

