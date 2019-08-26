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
						<a href="#">发布与采集</a> &raquo; 表单索引文件管理
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
									<a href="javascript:submitSelectClassRebuildIndex();" class="btnwithico"><img src="../style/icon/sort-quantity.png" width="16" height="16" /><b>生成索引&nbsp;</b> </a>
									<a href="javascript:deleteSearchIndex('','false');" class="btnwithico"><img src="../style/icons/bin-full.png" width="16" height="16" /><b>清除索引&nbsp;</b> </a> (索引更新需要一分钟左右才会在搜索结果中生效)
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

											<td width="18%">
												<strong>表单名称</strong>
											</td>
											
											 <td width="16%">
												 
													<strong>关联表</strong>
												 
											 </td>



											<td width="13%">

												<center><strong>操作</strong></center>

											</td>

										</tr>
										<cms:CurrentSite>
											 <cms:SystemDataModelList modelType="9" siteId="${CurrSite.siteId}">
												<cms:SystemDataModel>
													<tr >
														<td>
															 
															<input type="checkbox" id="${DataModel.dataModelId}" name="checkClass" value="${DataModel.dataModelId}"   />
														 
														</td>

														<td>
															&nbsp;${DataModel.modelName}
														</td>
														 <td>
															 	 
															${DataModel.relateTableName}
				 			 
														 </td>



														<td>
															<center>
																<a href="javascript:submitRebuildIndex('${DataModel.dataModelId}', '${DataModel.modelName}');"><img src="../../core/style/icon/sort-quantity.png" width="16" height="16" />索引</a> &nbsp;
																<a href="javascript:deleteSearchIndex('${DataModel.dataModelId}');"><img src="../style/icons/bin-full.png" width="16" height="16" />清除</a>&nbsp;&nbsp;&nbsp;
															</center>
														</td>
													</tr>
												 </cms:SystemDataModel>
											</cms:SystemDataModelList>
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

		<form method="post" id="indexForm" name="indexForm">
			<input type="hidden" id="siteId" name="siteId" value="${CurrSite.siteId}" />
			<input type="hidden" id="classIdInfo" name="classIdInfo" />
			<input type="hidden" id="siteMode" name="siteMode" />
			<input type="hidden" id="pubEventKey" name="pubEventKey" />
		</form>

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

var pubCount = 0;
var current = 0;
var statusDialog;
var timeId = -1;

var checkedIdMap = new HashMapJs();

function submitSelectClassRebuildIndex()
{
	var cidCheck = document.getElementsByName('checkClass');
	
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
	   $.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要重建索引的表单！', 
       ok: true 
                    
	  });
	  return;
	}
	 
	submitRebuildIndex(ids, "所选表单")
}


function submitRebuildIndex(classId, className)
{
	var key = new UUID().id;
  
	var url = "<cms:BasePath/>search/rebuildIndex.do?mode=form&ids="+classId+"&key="+key+"&<cms:Token mode='param'/>";


 	$.ajax({
      	type: "POST",
       	url: url,
       	data:'',
   
       	success: function(mg)
        {        
        	var msg = eval("("+mg+")");
        	
            if('success' == msg)
            {
            	//statusDialog.content(msg);
        		//removeIndexRTStatus(key);
		      	//window.clearInterval(timeId);
		      	//timeId = -1;
            }
            else
            {
            	statusDialog.content(msg);
        		removeIndexRTStatus(key);
		      	window.clearInterval(timeId);
		      	timeId = -1;
		      	
		      	statusDialog.close();
            
            					   $.dialog(
								   { 
									   					title :'提示',
									    				width: '200px', 
									    				height: '60px', 
									                    lock: true, 
									                     
									    				icon: '32X32/fail.png', 
									    				
									                    content: "执行失败，无权限请联系管理员！",
							
									    				cancel: true
									});
            }
        	
        	
        }
     });


    statusDialog = $.dialog
    ({ 
  		title : '建立索引进度 - ' + className,
    	width: '315px', 
    	height: '80px', 
        lock: true, 
    	icon: '32X32/succ.png', 
    				
        content: '本次索引内容数总 <strong>  <\/strong> 篇 / 当前索引 <strong>  <\/strong> 篇', 
       	cancel: function()
       	{
       		removeIndexRTStatus(key);
		    window.clearInterval(timeId);
		    timeId = -1;
		    //unSelectAll();
		    window.location.reload();
       	} 
	 });
	
    timeId = setInterval("showIndexRTStatus('"+key+"')",150);
}


function deleteSearchIndex(classInfo,siteMode)
{
	if('' == classInfo)
	{
		var cidCheck = document.getElementsByName('checkClass');
	
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
		   $.dialog({ 
	   					title :'提示',
	    				width: '180px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '请选择需要删除索引的表单！', 
	       cancel: true 
	                    
		  });
		  return;
		}
	
	}
	else
	{
		ids = classInfo;
	}
	
	
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选索引吗？',
                    
                    ok: function () { 
                    
                    var url = "<cms:BasePath/>search/deleteSearchIndex.do?mode=form&siteMode="+ siteMode+"&idInfo="+ids+"&<cms:Token mode='param'/>";
  
  
  
				 	$.ajax({
				      	type: "POST",
				       	url: url,
				       	data:'',
				   
				       	success: function(mg)
				        {        
				           var msg = eval("("+mg+")");
				           
				            if('success' == msg)
				            {
				            	$.dialog({ 
								   					title :'提示',
								    				width: '160px', 
								    				height: '60px', 
								                    lock: true, 
								    				icon: '32X32/succ.png', 
								    				
								                    content: '清除索引成功！',
								                    
								                    ok: function () { 
								                    	window.location.reload();
								                    }
								})
								                
				            	
				            }
				            else
				            {
				            	   $.dialog(
								   { 
									   					title :'提示',
									    				width: '200px', 
									    				height: '60px', 
									                    lock: true, 
									                     
									    				icon: '32X32/fail.png', 
									    				
									                    content: "执行失败，无权限请联系管理员！",
							
									    				cancel: true
									});
				            }
				        	
				        	
				        }
				     });	
       
        
    }, 
    cancel: true 
                    
	
		});


   
     
}

function showIndexRTStatus(key)
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
		     
		      statusDialog.content('本次索引内容数总 <strong> '+jsonObj.pubCount+' <\/strong> 篇 / 当前索引 <strong> '+jsonObj.current+' <\/strong> 篇');
		      
		      if(jsonObj.pubCount == jsonObj.current && jsonObj.pubCount != 0)
		      {
		        statusDialog.content('本次索引内容数总 <strong>'+jsonObj.pubCount+'<\/strong> 篇 / 当前发布 <strong>'+jsonObj.current+'<\/strong> 篇<br/><font color=\'red\'><strong>索引完成!<\/strong><\/font>');
		   		removeIndexRTStatus(key);
		      	window.clearInterval(timeId);
		      	timeId = -1;
		       }
	      }
	      
	      return;
	   	}
	 }
	);

}

function removeIndexRTStatus(key)
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
  
   
  if(cBox.checked)
  {
      //document.getElementById("classId").value=cBox.value;
      checkedIdMap.put(cBox.value,cBox.value);
  
  }
  else
  {
      checkedIdMap.remove(cBox.value,cBox.value);
      document.getElementById("selectAll").checked=false;
  }
  

   return false;
  
}

function checkSelectCheckBox()
{
   if(checkedIdMap.size()==0)
   {
      return false;
   }
   
   return true;

}

function selectAll()
{
   var checks = document.getElementsByName('checkClass');

   for(var i = 0; i < checks.length; i++)
   {
      checks[i].checked=true;  
      checkedIdMap.put(checks[i].value,checks[i].value);
        
   }
   checkedIdMap.remove('selectAll');
}

function unSelectAll()
{
   var checks = document.getElementsByName('checkClass');

   for(var i = 0; i < checks.length; i++)
   {
      checks[i].checked=false;
   }
   checkedIdMap.removeAll();

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
