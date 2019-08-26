<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/uuid.js"></script>
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

		<script>
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

		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">发布与采集</a> &raquo; 站点内容发布
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
			<tr>
				<td class="mainbody" align="left" valign="top">
					<!--main start-->
					<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td style="padding: 6px 0px;">
								<div class="fl">
									<a href="javascript:submitPageGenerateHtmlForm('','4','muti');" class="btnwithico"><img src="../style/icons/script-globe.png" width="16" height="16" /><b>首页发布&nbsp;</b> </a>
									<a href="javascript:submitPageGenerateHtmlForm('','1','muti');" class="btnwithico"><img src="../style/icons/folder-network.png" width="16" height="16" /><b>栏目发布&nbsp;</b> </a>
									<a href="javascript:submitPageGenerateHtmlForm('','2','muti');" class="btnwithico"><img src="../style/icons/document-text-image.png" width="16" height="16" /><b>内容发布&nbsp;</b> </a>
									<a href="javascript:submitPageGenerateHtmlForm('','3','muti');" class="btnwithico"><img src="../style/icons/folder-open-image.png" width="16" height="16" /><b>全发布&nbsp;</b> </a>
									&nbsp;
									<input type="radio" class="form-radio" value="" name="target">默认端
									<input type="radio" class="form-radio" id="mob" value="mob"  name="target">移动端	
									<input type="radio" class="form-radio" id="pad" value="pad" name="target">Pad端	
									<cms:CurrentSite>
									<script>
										 
										var mob = '${CurrSite.mobVm}';
										var pad = '${CurrSite.padVm}';
										
										if(mob == '0')
										{
											$('#mob').attr('disabled','true');
										}
										
										if(pad == '0')
										{
											$('#pad').attr('disabled','true');
										}
									
									</script>
									</cms:CurrentSite>
								</div>

								 

								<div class="fr">
								<a href="javascript:submitPageGenerateHtmlForm('','2','muti-date');" class="btnwithico"><img src="../style/icons/clock.png" width="16" height="16" /><b>内容范围发布&nbsp;</b> </a>							
									&nbsp;时间间隔:
									<input class="form-input" size="18" maxlength="30" type="text" class="f_input_time" onclick="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy年MM月dd日',vel:'startAddDateVal'});" value="" />
									至
									<input class="form-input" size="18" maxlength="30" type="text" class="f_input_time" onclick="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy年MM月dd日',vel:'endAddDateVal'});" value="" />
									&nbsp;&nbsp;

									<input type="hidden" id="startAddDateVal" />
									<input type="hidden" id="endAddDateVal" />
								</div>
							</td>
						</tr>

						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">

											<td width="3%" height="30">
												<input type="checkbox" class="classCheck" id="selectAll" name="selectAll" value="selectAll" onclick="regCheckBox(this);" />
											</td>

											<td width="34%">
												<strong>栏目名称</strong>
											</td>

											
											<td width="17%">
												<center>
													<strong>发布模型</strong>
												</center>
											</td>
											<td width="6%">
												<strong>频道静态</strong>
											</td>
											<td width="6%">
												<strong>列表静态</strong>
											</td>
											<td width="6%">
												<strong>内容静态</strong>
											</td>


											<td width="17%">
												<center>
													<strong>操作</strong>
												</center>
											</td>
										</tr>
										
										<cms:CurrentSite>
											<cms:SystemClassList site="${CurrSite.siteFlag}" type="all">
												<cms:SysClass>
													<tr id="${Class.linearOrderFlag}" ondblclick="javascript:change(this);">
														<td>
															<cms:if test="${Class.needStaticAction}">
																<input type="checkbox" id="${Class.classId}" name="checkClass" value="${Class.classId}" onclick="regCheckBox(this);" />
															</cms:if>
														</td>

														<td>
															${Class.layerUIClassName}
														</td>
														
														

														<td>
															<cms:SystemDataModel id="${Class.contentType}">
																<center>
																	${DataModel.modelName}
																</center>
															</cms:SystemDataModel>

														</td>
														<td>
															<center>
																${Class.classHomeProduceString}
															</center>
														</td>
														<td>
															<center>
																${Class.classProduceString}
															</center>
														</td>
														<td>
															<center>
																${Class.contentProduceString}
															</center>
														</td>

														<td>
															<center>
															<cms:if test="${Class.classType == 3}">
																<a href="javascript:submitPageGenerateHtmlForm('${Class.classId}','1','single','${Class.classHomeProduceType}','${Class.classProduceType}','${Class.contentProduceType}')">发单页</a> |
																<a name="not_use_href" disabled href="javascript:">发内容</a> |
																<a name="not_use_href" disabled href="javascript:">全发</a>																							
															
																<script>
																		disableAnchorElementByName("not_use_href", true);
																</script>
															</cms:if>
															<cms:else>
																<a href="javascript:submitPageGenerateHtmlForm('${Class.classId}','1','single','${Class.classHomeProduceType}','${Class.classProduceType}','${Class.contentProduceType}')">发栏目</a> |
																<a href="javascript:submitPageGenerateHtmlForm('${Class.classId}','2','single','${Class.classHomeProduceType}','${Class.classProduceType}','${Class.contentProduceType}')">发内容</a> |
																<a href="javascript:submitPageGenerateHtmlForm('${Class.classId}','3','single','${Class.classHomeProduceType}','${Class.classProduceType}','${Class.contentProduceType}')">全发</a>									
															</cms:else>
																	</center>
														</td>
													</tr>
												</cms:SysClass>
											</cms:SystemClassList>
											<cms:Empty flag="Class">
														<tr>
															<td class="tdbgyew" colspan="9">
																<center>
																	当前没有数据!
																</center>
															</td>
														</tr>
											</cms:Empty>
										</cms:CurrentSite>
									</table>
								</div>
							<div class="mainbody-right"></div>
							</td>
						</tr>

					</table>


				</td>
			</tr>

			<tr>
				<td height="10">
					&nbsp;
				</td>
			</tr>
		</table>

		<form method="post" id="generateForm" name="generateForm">
			<input type="hidden" id="siteId" name="siteId" value="${CurrSite.siteId}" />
			<input type="hidden" id="classIdInfo" name="classIdInfo" />
			<input type="hidden" id="staticType" name="staticType" />
			<input type="hidden" id="pubEventKey" name="pubEventKey" />
			<input type="hidden" id="startAddDate" name="startAddDate" value="" />
			<input type="hidden" id="endAddDate" name="endAddDate" value="" />
		</form>

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

initRadio('target','${param.target}');

var pubCount = 0;
var current = 0;
var statusDialog;
var timeId = -1;

 var errorMsg = '';

function submitPageGenerateHtmlForm(classInfo, pubType, flag, classHomeStatus, listStatus, contentStstus)
{
   if('single' != flag)
   {
		var chkValArray =new Array();    
	    $('input[name="checkClass"]:checked').each(function()
	    {    
	    	chkValArray.push($(this).val());    
		});  
		
		classInfo = chkValArray.join('#');
		
		if('' == classInfo && '4' != pubType)
		{
			$.dialog
		    ({ 
		  		title : '提示',
		    	width: '160px', 
		    	height: '60px', 
		        lock: true, 
		    	icon: '32X32/i.png', 
		    				
		        content: '请选择您要操作的栏目!', 
		       	cancel: true
			 });
			 return;
		}
    }
    else
    {
    	  if('1' == pubType)
	      {
	         if('3' != listStatus && "3" != classHomeStatus)
	         {
	         	$.dialog
			    ({ 
			  		title : '提示',
			    	width: '230px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/i.png', 
			    				
			        content: '当前栏目频道和列表发布都为动态模式!', 
			       	cancel: true
				 });
				 
				 return;
				 
		         }
	      }  
	      else if('2' == pubType)
	      {
	         if('3' != contentStstus)
	         {
	         	$.dialog
			    ({ 
			  		title : '提示',
			    	width: '230px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/i.png', 
			    				
			        content: '当前栏目内容发布为动态模式!', 
			       	cancel: true
				 });
				 return;
		         }
	      }
	      else if('3' == pubType)
	      {
	         if('3' != listStatus && "3" != classHomeStatus && '3' != contentStstus)
	         {
	         	$.dialog
			    ({ 
			  		title : '提示',
			    	width: '230px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/i.png', 
			    				
			        content: '当前栏目所有内容全为动态模式!', 
			       	cancel: true
				 });
				 return;
		         }
	      }
    }
    
    var t = getRadioCheckedValue('target');
    
    var tp = '';
    
    var ts = '';
    
    if(t == 'mob')
    {
    	tp = '?mob=true';
    	
    	ts = 'Mob端';
    }
    else if(t == 'pad')
    {  
    	tp = '?pad=true';
    	
    	ts = 'Pad端';
    }
   
    var key = new UUID().id;
   
    var title = ''; 
    if('4' == pubType)
    {
    	title = ts+'首页';
    }
    
    if('3' == pubType)
    {
    	title = ts+'栏目全部数据';
    }
    
    if('2' == pubType)
    {
    	title = ts+'内容';
    }
    
    if('1' == pubType)
    {
    	title = ts+'栏目列表';
    }
    
    if('0' == pubType)
    {
    	title = ts+'栏目频道页';
    }
   
   document.getElementById("pubEventKey").value = key;
   document.getElementById("staticType").value=pubType;
   document.getElementById("classIdInfo").value=classInfo;
   
   if('muti-date' == flag)
   {
   		document.getElementById("startAddDate").value = document.getElementById("startAddDateVal").value;	
   		document.getElementById("endAddDate").value = document.getElementById("endAddDateVal").value;;	
   }
   
   var url = "<cms:BasePath/>publish/generateContent.do"+tp;
   var postData = $("#someContentId,#staticType,#siteId,#classIdInfo,#pubEventKey,#startAddDate,#endAddDate").serialize();
 		
   
   var error = false;
   
  
   
 	$.ajax({
      	type: "POST",
       	url: url,
       	data:postData,
       	//async:false, 不可同步
   
       	success: function(mg)
        {        
             var msg = eval("("+mg+")");
             
            if('' != msg)
            {
            
            	error = true;
            	
            	errorMsg = msg;
            	 
            
            	
            }
            
            
        }
     });	
     
     
    
      
	    statusDialog = $.dialog
	    ({ 
	  		title : title+'发布进度',
	    	width: '335px', 
	    	height: '160px', 
	        lock: true, 
	    	icon: '32X32/succ.png', 
	    				
	        content: '本次发布内容数总 <strong> <\/strong> 篇 / 当前发布 <strong> <\/strong> 篇', 
	       	ok: function()
	       	{
	       		removePublishRTStatus(key);
			    window.clearInterval(timeId);
			    timeId = -1;
			    	 
		 		errorMsg = '';
			    //unSelectAll();
			    
			  	replaceUrlParam(window.location, 'target='+t);;
	       	} 
		 });
		
	    timeId = setInterval("showPublishRTStatus('"+key+"')",150);
    
    
}



 

function showPublishRTStatus(key)
{
	if(errorMsg != '')
	{
		 
	
		 statusDialog.content(errorMsg);
	
		 
		 return;
	}	

   var url = '<cms:BasePath/>common/queryOperationRTStatus.do?eventKey='+key+"&mode=json";

   $.ajax
   (
   {type:'POST',async:false,url:url,success:
	   function(da, textStatus)
	   {      
	        var data = eval("("+da+")");
	        
	     if(data != '')
	     {
		     var jsonObj = eval("("+data+")");
		     
		     var content = '';
		     
		     content = '本次发布内容数总 <strong>'+jsonObj.pubCount+'<\/strong> 篇 / 当前发布 <strong>'+jsonObj.current+'<\/strong> 篇<br/>';	 
		 
		     if(jsonObj.tranContentCurrent != '0')
		     {		     
		     	content = content+'<br/>正在分发静态文件 <strong>'+jsonObj.tranContentCount+'<\/strong> 篇 / 当前传送 <strong>'+jsonObj.tranContentCurrent+'<\/strong> 篇<br/>';	 
		     }
		   
		     content =  content+ '<font color=\'red\'><strong>'    
		  
		     if('1' == jsonObj.homeOperStatus)
		     {
		     	content = content+'<br/>站点首页发布完成! [1] 篇<br/>';
		     }
		     
		     if('1' == jsonObj.channelOperStatus)
		     {
		     	content = content+'<br/>频道页发布完成! ['+jsonObj.channelAllCount+'] 篇<br/>';
		     }
		     
		     if('1' == jsonObj.listOperStatus)
		     {
		     	content = content+'<br/>列表页发布完成! ['+jsonObj.listAllCount+'] 篇<br/>';
		     }
		     
		     if('1' == jsonObj.contentOperStatus)
		     {
		     	content = content+'<br/>内容页发布完成! ['+jsonObj.contentAllCount+'] 篇<br/>';
		     }
		     
		     if(jsonObj.tranContentCurrent == jsonObj.tranContentCount && jsonObj.tranContentCount != '0')
		     {		    
		     	content = content+'<font color=\'red\'><strong><br/>分发到服务器完成 ['+jsonObj.tranContentCount+'] 篇<br/><\/strong><\/font>';	 
		     }
		     	
		     
		     content = content + '<\/strong><\/font>';
		     
		     
		     statusDialog.content(content);
		      	     
	      }
          else
          {
                statusDialog.content('本次发布内容数总 <strong>0<\/strong> 篇 / 当前发布 <strong>0<\/strong> 篇');  
          }
	      
	      return;
	   	}
	 }
	);

}

function removePublishRTStatus(key)
{

   var url = '<cms:BasePath/>common/queryOperationRTStatus.do?eventKey='+key+"&mode=remove";
   
   $.ajax
   (
   	 {type:'POST',async:false,url:url,success:
		   function(data, textStatus)
		   {      
		    
		   }
	 }
	);
}


function regCheckBox(cBox)
{
  if(cBox.id==="selectAll" && cBox.checked==true)
  {
      selectAll();
      return false;
  }
  
  if(cBox.id==="selectAll" && cBox.checked==false)
  {
      unSelectAll();
      return false;
  }
  
  if(!cBox.checked)
  {
    document.getElementById("selectAll").checked=false;
  }
  
  if(checkAllBoxChecked())
  {
  	document.getElementById('selectAll').checked = true;
  }
  

   return false;
  
}



function selectAll()
{
   var checks = document.getElementsByName('checkClass');

   for(var i = 0; i < checks.length; i++)
   {
      checks[i].checked=true;  
   }
}

function unSelectAll()
{
   var checks = document.getElementsByName('checkClass');

   for(var i = 0; i < checks.length; i++)
   {
      checks[i].checked=false;
   }
}

function checkAllBoxChecked()
{
   var checks = document.getElementsByName('checkClass');

   for(var i = 0; i < checks.length; i++)
   {
     if(checks[i].checked == false)
     {
     	return false;
     }  
   }
   
   return true;
}


function change(tr)
{
    var expandIco="core/style/default/images/t_expand.png";
	var smallIco="core/style/default/images/t_small.png";
    var trId = tr.id
    var trs = document.getElementsByTagName("tr");
    
    var expand = false;
    var img = document.getElementById('img'+trId);
    
    if(img == null)
    {
    	return;
    }
    
	if(img.src.indexOf(expandIco) != -1)
	{
		expand = true;
	}
 
	 //alert(expand);
	for(var i=0; i<trs.length;i++)
	{
		if(trs[i].id.indexOf(trId) == 0 && trs[i].id != trId  )
		{
			if(expand)
			{
				trs[i].style.display="";
				img.src="../../"+smallIco;
			}
			else
			{
				trs[i].style.display="none";
				img.src="../../"+expandIco;
			}		
		}
	}
}




</script>
