<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<!--加载 js -->
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>


	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td style="padding: 2px 6px;">
								<div class="fl">
									<a href="javascript:backEnterFolder('${param.entry}');" class="btnwithico"> <img src="../style/blue/icon/folder_up.png" alt="" /><b>返回上层&nbsp;</b> </a> &nbsp;&nbsp;&nbsp;路径:
									<input type="text" id="showPathTrace" style="width:605px" class="form-input" readonly />
									&nbsp;
								</div>
								<div class="fr">
									<div class="fl">

									</div>

								</div>
							</td>
						<tr>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="2%">

											</td>

											<td width="30%">

												<strong>模板全名 
											</td>
											<td width="10%">
												<strong>文件大小 
											</td>




										</tr>


										<cms:TempletFile entry="${param.entry}" >
											<cms:if test="${TempletFile.dir==true}">
												<tr>
													<td>
														<div align="center">

														</div>
													</td>
													<td ondblclick="javascript:enterFolder('${TempletFile.entry}');">
														<a href="javascript:enterFolder('${TempletFile.entry}');">&nbsp;&nbsp;<img src='../style/blue/icon/folder.gif' />${TempletFile.fileName}</a>
													</td>
													<td>
														
													</td>
												</tr>

											</cms:if>
											<cms:else>
												<tr>
													<td>

														<input type="radio" name="selectFile" id="selectFile" value="${TempletFile.entry}" />

													</td>
													<td>
														<div id="${TempletFile.entry}" onclick="javascript:radioClick(this)">
															&nbsp;
															<cms:if test="${TempletFile.type=='jsp' || TempletFile.type=='html' || TempletFile.type=='htm'}">
																<img src='../../core/style/default/images/page_code.png' />
															</cms:if>
															<cms:elseif test="${TempletFile.type=='thtml'}">
																<img src='../style/blue/icon/page_white_freehand.png' />
															</cms:elseif>
															<cms:elseif test="${TempletFile.type=='js'}">
																<img src='../../core/style/default/images/js.png' />
															</cms:elseif>
															<cms:elseif test="${TempletFile.type=='swf'}">
																<img src='../../core/style/default/images/flash.png' />
															</cms:elseif>
															<cms:elseif test="${TempletFile.type=='css'}">
																<img src='../../core/style/default/images/css.png' />
															</cms:elseif>
															<cms:elseif test="${TempletFile.type=='jpg' || TempletFile.type=='png' || TempletFile.type=='gif' || TempletFile.type=='jpeg'}">
																<img src='../../core/style/default/images/image.png' />
															</cms:elseif>
															<cms:else>
																<img src='../../core/style/default/images/unknown.png' />
															</cms:else>
															${TempletFile.fileName}

														</div>
													</td>
													<td>
														${TempletFile.size}kb
													</td>
												</tr>
											</cms:else>
										</cms:TempletFile>
									</table>
								</div>
							</td>
						</tr>
					</table>
					
					<div style="height:40px"></div>
					
					<div class="breadnavTab" >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:selectTempletFile();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" ><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
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

<script type="text/javascript">
var entryPath = '${param.entry}';

if(entryPath == '')
{
	entryPath = '/';
}

document.getElementById('showPathTrace').value=entryPath.replaceAll('\\*',' / ');

var api = frameElement.api, W = api.opener; 

function close()
{
	api.close();
}

function enterFolder(entry)
{
	window.location.href = "SelectChannelTemplet.jsp?mode=${param.mode}&entry="+encodeURIComponent(encodeURIComponent(entry))+'&apiId=${param.apiId}&objId=${param.objId}&vm=${param.vm}';
}

function backEnterFolder(entry)
{
	var parentEntry = entry.substring(0,entry.lastIndexOf('*'));
	
	window.location.href = "SelectChannelTemplet.jsp?mode=${param.mode}&entry="+encodeURIComponent(encodeURIComponent(parentEntry))+'&apiId=${param.apiId}&objId=${param.objId}&vm=${param.vm}';
}

function selectTempletFile()
{
    var fs =  document.getElementsByName('selectFile');
    
    var check =false;
    
    var selectVal;
    for(var i=0; i<fs.length;i++)
    {
      if(fs[i].checked==true)
      {
         selectVal = fs[i].value;
         check=true;
         break;
      }
    }
   
   
    if(!check)
    {
      W.$.dialog
      ({ 
	                id:'stfe',
   					title :'提示',
    				width: '160px', 
    				parent:api,
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png',   				
                    content: '您确认不选择模板文件吗?',
					cancel: true 
      });
    }
    else
    {
    	var mode = '${param.mode}';
    	
    	var apiWin = '${param.apiId}';
    	
    	var vm = '${param.vm}';
    	
    	if('' == apiWin)
    	{
    		var targetObj = '${param.objId}';


    		if('undefined' != targetObj && '' != targetObj)
    		{
    			W.document.getElementById(targetObj).value = selectVal.replaceAll('\\*','/');
    		}
    		else
    		{
    			var vmid = '';
    			
	    		if('channel' == mode)
		      	{
		      		vmid = 'classHomeTemplateUrl';
		      		
		      		if('mob' == vm)
		      		{
		      			vmid = 'mobClassHomeTemplateUrl';
		      		}
		      		else if('pad' == vm)
		      		{
		      			vmid = 'padClassHomeTemplateUrl';
		      		}
		      		
		      		W.document.getElementById( vmid ).value = selectVal.replaceAll('\\*','/');
		      	}
		      	else if('class' == mode)
		      	{
		      	
		      		vmid = 'classTemplateUrl';
		      		
		      		if('mob' == vm)
		      		{
		      			vmid = 'mobClassTemplateUrl';
		      		}
		      		else if('pad' == vm)
		      		{
		      			vmid = 'padClassTemplateUrl';
		      		}
		      	
		      		W.document.getElementById(vmid).value = selectVal.replaceAll('\\*','/');
		      	}
		      	else if('content' == mode)
		      	{
		      		vmid = 'contentTemplateUrl';
		      		
		      		if('mob' == vm)
		      		{
		      			vmid = 'mobContentTemplateUrl';
		      		}
		      		else if('pad' == vm)
		      		{
		      			vmid = 'padContentTemplateUrl';
		      		}
		      
		      		W.document.getElementById(vmid).value = selectVal.replaceAll('\\*','/');
		      	}
		      	else if('home' == mode)
		      	{
		      		vmid = 'homePageTemplate';
		      		
		      		if('mob' == vm)
		      		{
		      			vmid = 'mobHomePageTemplate';
		      		}
		      		else if('pad' == vm)
		      		{
		      			vmid = 'padHomePageTemplate';
		      		}
		      		
		      		W.document.getElementById(vmid).value = selectVal.replaceAll('\\*','/');
		      	}
		      	else if('spec' == mode)
		      	{
		      		vmid = 'homePageTemplate';
		      		
		      		if('mob' == vm)
		      		{
		      			vmid = 'mobHomePageTemplate';
		      		}
		      		else if('pad' == vm)
		      		{
		      			vmid = 'padHomePageTemplate';
		      		}
		      		W.document.getElementById(vmid).value = selectVal.replaceAll('\\*','/');
		      	}
		      	else if('block' == mode)
		      	{
		      		W.document.getElementById('templateUrl').value = selectVal.replaceAll('\\*','/');
		      	}
	      	}
    	}
    	else
    	{
    	   
    	     var vmid = '';
	      		
    		if('channel' == mode)
	      	{
	      		
	      		
	      		vmid = 'classHomeTemplateUrl';
		      		
		      	if('mob' == vm)
		      	{
		      		vmid = 'mobClassHomeTemplateUrl';
		      	}
		      	else if('pad' == vm)
		      	{
		      		vmid = 'padClassHomeTemplateUrl';
		      	}
		      	
		      	
		      	api.get(apiWin).document.getElementById(vmid).value = selectVal.replaceAll('\\*','/');
	            	}
	      	else if('class' == mode)
	      	{
	      		api.get(apiWin).document.getElementById('classTemplateUrl').value = selectVal.replaceAll('\\*','/');
	      	}
	      	else if('content' == mode)
	      	{	
	      	
	      	 
	      		$(api.get(apiWin).document.getElementById('contentTemplateUrl')).val( selectVal.replaceAll('\\*','/') );
	      		
	      		$(api.get(apiWin).document.getElementById('especialTemplateUrl')).val( selectVal.replaceAll('\\*','/') );
	      	}
	      	else if('home' == mode)
	      	{
	      		api.get(apiWin).document.getElementById('homePageTemplate').value = selectVal.replaceAll('\\*','/');
	      	}
	      	else if('spec' == mode)
	      	{	
				 
	      		vmid = 'listTemplateUrl';
		      		
		      	if('mob' == vm)
		      	{
		      		vmid = 'mobListTemplateUrl';
		      	}
		      	else if('pad' == vm)
		      	{
		      		vmid = 'padListTemplateUrl';
		      	}
		      	 		 
	      		api.get(apiWin).document.getElementById(vmid).value = selectVal.replaceAll('\\*','/');
	      	}
	      	else if('block' == mode)
	      	{      	
	      		api.get(apiWin).document.getElementById('templateUrl').value = selectVal.replaceAll('\\*','/');
	      	}
    	}
    	
    	 
    	close();
    }
    
   
    
}

function radioClick(divNode)
{
	
    var entry = divNode.id;
    
    var fs =  document.getElementsByName('selectFile');
    
    var check =false;
    
    for(var i=0; i<fs.length;i++)
    {
      if(fs[i].value==entry)
      {
         
        fs[i].click();
         break;
      }
    }

}








</script>

