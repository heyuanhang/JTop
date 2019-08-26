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
						<a href="#">发布与采集</a> &raquo; 专题发布
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
							<td style="padding: 7px 10px;">
								<div class="fl">
									专题分类:
									<select id="classId" class="form-select" name="classId" onchange="javascript:changeFilter();">
										<option value="">
											-------- 全部专题 --------
										</option>
										<cms:SystemClassList type="all" isSpec="true" classType="4">
											<cms:SysClass>
												<option value="${Class.classId}">
													${Class.layerUIBlankClassName}
												</option>
											</cms:SysClass>
										</cms:SystemClassList>
									</select>
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
									&nbsp;
								</div>
								<div>
									<a href="javascript:submitPageGenerateHtmlForm('','1','muti');" class="btnwithico"><img src="../style/icons/script-globe.png" width="16" height="16" /><b>专题首页发布&nbsp;</b> </a>
									<a href="javascript:submitPageGenerateHtmlForm('','7','muti');" class="btnwithico"><img src="../style/icons/folder-open-table.png" width="16" height="16" /><b>列表页发布&nbsp;</b> </a>
									
								</div>
								<div class="fr">
 
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

											<td width="35%">
												<strong>专题名称</strong>
											</td>
											

											<td width="7%">
												<strong>专题静态</strong>
											</td>

											<td width="7%">
												<strong>列表静态</strong>
											</td>

											<td width="16%">

												<center><strong>操作</strong></center>

											</td>

										</tr>
										<cms:CurrentSite>
											<cms:SystemClassList parentId="${param.classId}" type="all" isSpec="true" classType="5" size="10">

												<cms:SysClass>
													<tr id="${Class.linearOrderFlag}" ondblclick="javascript:change(this);">
														<td>
															<cms:if test="${Class.needStaticAction}">
																<input type="checkbox" id="${Class.classId}" name="checkClass" value="${Class.classId}#${Class.contentProduceType}#${Class.classProduceType}" onclick="regCheckBox(this);" />
															</cms:if>
															<cms:else>
																<input type="checkbox" disabled />
															</cms:else>
														</td>

														<td>
															<img src="../style/icons/script-text.png" width="16" height="16" class="home" />
															&nbsp;${Class.className}
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
																<a href="javascript:submitPageGenerateHtmlForm('${Class.classId}','1','single','${Class.classHomeProduceType}','${Class.classProduceType}','${Class.contentProduceType}')">发首页</a> |
																<a href="javascript:submitPageGenerateHtmlForm('${Class.classId}','7','single','${Class.classHomeProduceType}','${Class.classProduceType}','${Class.contentProduceType}')">发列表</a>
															</center>
														</td>

													</tr>
												</cms:SysClass>

												<cms:Empty flag="Class">
													<tr>
														<td class="tdbgyew" colspan="6">
															<center>
																当前没有数据!
															</center>
														</td>
													</tr>
												</cms:Empty>
												<cms:PageInfo>
												<tr id="pageBarTr">
												<td colspan="8" class="PageBar" align="left">
													<div class="fr">
														<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
														<span class="page">[<a href="javascript:query('h');">首页</a>]</span>
														<span class="page">[<a href="javascript:query('p');">上一页</a>]</span>
														<span class="page">[<a href="javascript:query('n');">下一页</a>]</span>
														<span class="page">[<a href="javascript:query('l');">末页</a>]</span>&nbsp;
													</div>
													<script>
																function query(flag)
																{
																	var cp = 0;
																	
																	if('p' == flag)
																	{
			                                                             cp = parseInt('${Page.currentPage-1}');
																	}
		
																	if('n' == flag)
																	{
			                                                             cp = parseInt('${Page.currentPage+1}');
																	}
		
																	if('h' == flag)
																	{
			                                                             cp = 1;
																	}
		
																	if('l' == flag)
																	{
			                                                             cp = parseInt('${Page.pageCount}');
																	}
		
																	if(cp < 1)
																	{
			                                                           cp=1;
																	}
																
																	
																	replaceUrlParam(window.location,'pn='+cp);		
																}
													
													
																function jump()
																{
																	replaceUrlParam(window.location,'pn='+document.getElementById('pageJumpPos').value);
																}
															</script>
													<div class="fl"></div>
												</td>
											</tr>
										</cms:PageInfo>
												
											</cms:SystemClassList>
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
		</form>

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

initSelect('classId','${param.classId}');

initRadio('target','${param.target}');


var pubCount = 0;
var current = 0;
var statusDialog;
var timeId = -1;

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
		    				
		        content: '请选择您要操作的专题!', 
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
			    				
			        content: '当前专题首页为动态模式!', 
			       	cancel: true
				 });
				 
				 return;
				 
		         }
	      }
	      if('7' == pubType)
	      {
	         if('3' != listStatus )
	         {
	         	$.dialog
			    ({ 
			  		title : '提示',
			    	width: '230px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/i.png', 
			    				
			        content: '当前专题内容列表页为动态模式!', 
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
			    				
			        content: '当前专题内容发布为动态模式!', 
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
			    				
			        content: '当前专题所有内容全为动态模式!', 
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
    	title = '首页';
    }
    
    if('3' == pubType)
    {
    	title = '专题全部数据';
    }
    
    if('2' == pubType)
    {
    	title = '内容';
    }
    
    if('1' == pubType)
    {
    	title = '专题列表';
    }
    
    if('0' == pubType)
    {
    	title = '专题页';
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
 		
 	$.ajax({
      	type: "POST",
       	url: url,
       	data:postData,
   
       	success: function(mg)
        {        
              var msg = eval("("+mg+")");
              
            if('' != msg)
            {
            	statusDialog.content(msg);
        		removePublishRTStatus(key);
		      	window.clearInterval(timeId);
		      	timeId = -1;
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
		    //unSelectAll();
		    
		    replaceUrlParam(window.location, 'target='+t);;
       	} 
	 });
	
    timeId = setInterval("showPublishRTStatus('"+key+"')",150);
}

function showPublishRTStatus(key)
{
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
		     	content = content+'<br/>专题页发布完成! [1] 篇<br/>';
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

function changeFilter()
{ 
	window.location.href='ManageStaicSpecial.jsp?classId='+$('#classId').val();
}


</script>
