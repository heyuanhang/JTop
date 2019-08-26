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

	 
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
			<tr>
				<td  class="input-title">
					<strong>采集规则</strong>
				</td>
				<td class="td-input">
				<cms:CurrentSite> 
					<select class="form-select" id="ruleId" name="ruleId" style="width:334px">
											<option value="-1">
												----------------- 请选则采集规则 ------------------
											</option>

											<cms:SystemList querySign="SELECT_PICK_RULE_LIST_QUERY" var="${CurrSite.siteId}">
												<option value="${SysObj.pickCfgId}">
													${SysObj.configName}
												</option>
											</cms:SystemList>

										</select>
				</cms:CurrentSite> 
				</td>
			</tr>
			<tr>
				<td width="12%" class="input-title">
					<strong>目标URL</strong>
				</td>
				<td class="td-input">
					 	
					<textarea id="targetUrl" name="targetUrl"  style="width:530px;height:480px" class="form-textarea" ></textarea>
					<br/><span class="ps">多个采集链接使用回车换行分隔,支持http格式或本地目录</span>
				</td>
			</tr>
			
			
			
			<%--<tr>
				<td  class="input-title">
					<strong>来源模式</strong>
				</td>
				<td class="td-input">
					<input   name="innerMode" type="radio"   class="form-radio" value="false"  />网络地址
					<input   name="innerMode" type="radio"   class="form-radio" value='true' checked/>文件夹					
				</td>
			</tr>
		--%></table>


		<div style="height:15px;"></div>
		<div class="breadnavTab"  >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" >
			<tr class="btnbg100">
				<div style="float:right">
					<a href="javascript:doPickSingleContent();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
					<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"><b>关闭&nbsp;</b> </a>
				</div>
				
			</tr>
		</table>
		</div>

		
		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  
var api = frameElement.api, W = api.opener; 

function gotoPage()
{
	W.currentTitle = document.getElementById('pageTitle').value;
	
	W.currentPage = document.getElementById('pagePos').value;
	
	W.okFlag = true;
		 
    api.close();
}

function close()
{
     api.close();
     W.window.location.reload();
}


function doPickSingleContent()
{
	  	 
	 var tip = W.$.dialog.tips('正在尝试采集,请耐心等待...',3600000000,'loading.gif'); 
	 
	 
	 var im = getRadioCheckedValue('innerMode');
	
	 var url = "<cms:BasePath/>pick/pickWeb.do?singleMode=true&innerMode="+im+"&mode=${param.mode}&classId=${param.classId}&ruleId="+$('#ruleId').val()+"&<cms:Token mode='param'/>";
 		
 					
					var postData = encodeURI($("#targetUrl").serialize());
					
					postData = encodeData(postData);
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:postData,
			   
			       		success: function(da)
			            {     
			            	var data = eval("("+da+")");
           		
			            
			            	 if(data.indexOf('您没有操作权限!') != -1)
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
						
								    				cancel: function()
								    				{
								    					tip.close();
								    					
								    					replaceUrlParam(window.location,'targetUrl='+encodeData($("#targetUrl").val()));
								    			 
			     										 
								    				}
								});
								
								return;
		    			   }
			            
			            
			              
		     			   if('success' != data)
		     			   {
		     			   		W.$.dialog(
							    { 
							   					title :'提示',
							    				width: '190px', 
							    				height: '60px', 
							                    lock: true, 
							                    parent:api,
							    				icon: '32X32/i.png', 
							    				
							                    content: '抱歉，当前规则没有采集到文章',
					
							    				cancel: function()
							    				{
							    					tip.close();
							    					replaceUrlParam(window.location,'targetUrl='+encodeData($("#targetUrl").val()));
								    			 
							    				}
								});
								
							 	 
		     			   }
		     			   else
		     			   {
		     			   		W.$.dialog(
							    { 
							   					title :'提示',
							    				width: '190px', 
							    				height: '60px', 
							                    lock: true, 
							                    parent:api,
							    				icon: '32X32/i.png', 
							    				
							                    content: '当前规则已成功执行采集！',
					
							    				ok: function()
							    				{
							    					tip.close();
							    					 window.location.reload();
		     										 
							    				}
								});
		     				
		     			   }
		     			   
		     			   
		     				
		     			 

			            }
	});	
	
			     	
	
	
   
}


  
</script>
