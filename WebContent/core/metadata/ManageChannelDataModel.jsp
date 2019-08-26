<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../style/blue/js/tab_change.js"></script>
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
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
		<cms:CurrentSite>
		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">系统管理</a> &raquo; 栏目扩展模型管理
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
							<td style="padding: 7px 10px;" class="">
								<div class="fl">
  										筛选:
										<select id="showMode" onchange="javascript:changeFilter(this);">
											<option value="-1">
												--- 全部可见模型 ---
											</option>
											<option value="1">
												本站私有模型
											</option>
											<option value="2">
												本站共用模型
											</option>
										</select>
										&nbsp;
									</div>
									<div>
										<a href="javascript:openCreateModelDialog();" class="btnwithico" onclick=""><img src="../style/icons/application-tile.png" width="16" height="16" /><b>新建栏目扩展模型&nbsp;</b> </a>

										<a href="javascript:deleteSelectSystemDataModel();" class="btnwithico" onclick=""><img src="../../core/style/default/images/del.gif" width="16" height="16" /><b>删除所选&nbsp;</b></a>

									</div>
							</td>
						</tr>

						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
												<td width="2%" height="30">
													<strong>模型ID</strong>
												</td>
												<td width="1%" height="30">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkId',this);" type="checkbox" />
												</td>

												<td width="7%">
													<strong>模型名称</strong>
												</td>

												<td width="5%">
													<strong>模型标识</strong>
												</td>

												<td width="4%">
													<strong>模型可见性</strong>
												</td>


												<td width="2%">
													<strong>图标</strong>
												</td>


												<td width="7%">
													<center>
														<strong>操作</strong>
													</center>
												</td>
											</tr>

										<cms:SystemDataModelList mode="manage" modelType="3" showMode="${param.showMode}">
												<cms:SystemDataModel >
													<tr id="tr-${status.index}">
														<td>
															${DataModel.dataModelId}
														</td>
														<td>
															<input class="inputCheckbox" name="checkId" value="${DataModel.dataModelId}" type="checkbox" onclick="javascript:" />
														</td>

														<td style="">
															<span title="">${DataModel.modelName}</span>
														</td>

														<td>
															${DataModel.modelSign}
														</td>

														<td>
															<cms:if test="${DataModel.privateMode == 1}"><font color="red">本站</font></cms:if>
															<cms:elseif test="${DataModel.siteId == CurrSite.siteId}"><font color="red">系统</font></cms:elseif>
															<cms:else><font color="green">系统</font></cms:else>
														</td>

														 

														<td>
															<img src="<cms:Domain/>core/style/icons/${DataModel.ico}" height="16" width="16" />
														</td>



														<td>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditModelDialog('${DataModel.dataModelId}','${DataModel.siteId}');">&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:manageModelField('${DataModel.dataModelId}','${DataModel.modelName}','${DataModel.siteId}')"><img src="../../core/style/icons/table-paint-can.png" width="16" height="16" />&nbsp;字段管理</a> </span>
															</center>
														</td>
													</tr>

												</cms:SystemDataModel>
											</cms:SystemDataModelList>
											<cms:Empty flag="DataModel">
														<tr>
															<td class="tdbgyew" colspan="9">
																<center>
																	当前没有数据!
																</center>
															</td>
														</tr>
											</cms:Empty>
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

		<form id="deleteSystemForm" name="deleteSystemForm" method="post">

			<input type="hidden" id="modelId" name="modelId" value="-1" />

		</form>

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

initSelect('showMode','${param.showMode}');

var currSiteId = '${CurrSite.siteId}';

function openCreateModelDialog()
{
	$.dialog({ 
	    id : 'ocm',
    	title : '创建栏目扩展模型',
    	width: '720px', 
    	height: '350px',
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/metadata/CreateDataModelNoBe.jsp?modelType=3&uid='+Math.random()

	});
}

function openEditModelDialog(modelId, siteId)
{
	if(currSiteId != siteId)
    {
    	<cms:LoginUser>	
			<cms:SystemOrg orgCode="001">
			
				if('false' == '${Org.orgBossId == Auth.identity}')
				{
					$.dialog({ 
	   					title :'提示',
	    				width: '200px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有总机构主管可编辑非本站共用模型字段!',
	                    
	                   
	    					cancel: true
	                    
		
		
					});
					return;
				}
				
			</cms:SystemOrg>
		</cms:LoginUser>
	    
    }


	$.dialog({ 
	    id : 'oem',
    	title : '编辑栏目扩展模型',
    	width: '720px', 
    	height: '350px',
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/metadata/EditDataModelNoBe.jsp?modelType=3&modelId='+modelId+'&uid='+Math.random()

	});
}

function manageModelField(modelId,modelName,siteId)
{  
	if(currSiteId != siteId)
    {
    	<cms:LoginUser>	
			<cms:SystemOrg orgCode="001">
			
				if('false' == '${Org.orgBossId == Auth.identity}')
				{
					$.dialog({ 
	   					title :'提示',
	    				width: '200px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有总机构主管可编辑非本站共用模型字段!',
	                    
	                   
	    					cancel: true
	                    
		
		
					});
					return;
				}
				
			</cms:SystemOrg>
		</cms:LoginUser>
	    
    }
    
	window.location.href="ManageModelMetadata.jsp?modelId="+modelId+'&modelName='+encodeURIComponent(encodeURIComponent(modelName))+'&modelType=3';
}

function deleteSelectSystemDataModel()
{
	var cidCheck = document.getElementsByName('checkId');
	
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
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要删除的模型！', 
       cancel: true 
                    
	  });
	  return;
	}

	$.dialog({ 
   					title :'提示',
    				width: '330px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '警告!模型以及其相关数据将被彻底删除,您确认执行吗？<br/>(注:只有主管能删除非本站模型,否则忽略)',
                   
                    ok: function () { 
        $.dialog.tips('正在更新系统元数据 可能需要较长时间...',90000); 
			        
			        var url = "<cms:BasePath/>metadata/deleteSystemDataModel.do?modelId="+ids+"&<cms:Token mode='param'/>";
			        $.ajax({
			  		type: "POST",
			   		 url: url,
			   		data: '',
			
			       	success: function(mg)
			        {     
			       		var msg = eval("("+mg+")");
			       		
			           if('success' == msg)
			           {
			           		window.location.reload();
			           		
			           } 	
			           else
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
									
											    				cancel: true
											});
											
										 
			           }   
			        }
			 	});	
       
    }, 
    cancel: true
                    
	
	
	});



}





function changeFilter(obj)
{
   replaceUrlParam(window.location,'showMode='+obj.value);
}

</script>
</cms:CurrentSite>
