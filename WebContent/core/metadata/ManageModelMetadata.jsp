<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		 
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

		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">系统管理</a> &raquo; 系统数据模型管理 &raquo; 字段管理 &raquo;
						<cms:DecodeParam enc="utf-8" str="${param.modelName}"/>
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
									<a href="javascript:openCreateModelFiledDialog('${param.modelId}');" class="btnwithico" onclick=""><img src="../style/icons/paint-can--plus.png" width="16" height="16" /><b>新建模型字段&nbsp;</b> </a>
									<a href="javascript:changeModelFieldOrder();" class="btnwithico"  ><img src="../style/icons/sort-price.png" width="16" height="16" /><b>行排列生效&nbsp;</b> </a>
									<a href="javascript:openPrev();" class="btnwithico"  ><img src="../style/icons/monitor-window-3d.png" width="16" height="16" /><b>预览&nbsp;</b> </a>
									
									<a href="javascript:backManageModel()" class="btnwithico" onclick=""><img src="../style/icons/arrow-return-180-left.png" width="16" height="16" /><b>返回模型管理&nbsp;</b> </a>
								</div>
							</td>
						</tr>

						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="2%" height="30">
												<strong>ID</strong>
											</td>
											<td width="2%" height="30">
												<input class="inputCheckbox" onclick="javascript:selectAll('checkId',this);" type="checkbox" />
											</td>

											<td width="10%">
												<strong>名称</strong>
											</td>

											<td width="7%">
												<strong>访问标识</strong>
											</td>

											<td width="6%">
												<strong>页面表现</strong>
											</td>

											<td width="4%">
												<strong>行编号</strong>
											</td>
											<td width="4%">
												<strong>列间隔</strong>
											</td>

											<td width="2%">
												<strong>查询</strong>
											</td>
											<td width="2%">
												<strong>排序</strong>
											</td>
											<td width="2%">
												<strong>搜索</strong>
											</td>
											<td width="2%">
												<strong>采集</strong>
											</td>
											
											<td width="7%">
												<center><strong>操作</strong></center>
											</td>
										</tr>

										<cms:SystemModelFiledList modelId="${param.modelId}">
											<cms:SystemModelFiled >
												<tr id="tr-${status.index}">
													<td>
														${ModelFiled.metaDataId}
													</td>
													<td>
														<input class="inputCheckbox" name="checkId" value="${DataModel.dataModelId}" type="checkbox" onclick="javascript:changeTRBG(this, '${status.index}');" />
													</td>

													<td>
														<span title="">${ModelFiled.showName}</span>
													</td>

													<td>
														${ModelFiled.fieldSign}
													</td>

													<td>
														${ModelFiled.htmlModeStr}
													</td>

													<td>
														<input id="order-${ModelFiled.metaDataId}" name="fileldOrder" type="input" class="form-input" size="4" value="${ModelFiled.orderIdVal}" />
													</td>

													<td>
														<input id="blank-${ModelFiled.metaDataId}" name="fileldBlank" type="input" class="form-input" size="4" value="${ModelFiled.blankCount}" />
													</td>

													<td>

														<cms:if test="${ModelFiled.queryFlag==1}">
															<img src="../style/icons/tick-small.png" />
														</cms:if>
														<cms:else>
															<img src="../style/icons/cross-small.png" />
														</cms:else>
													</td>

													<td>
														<cms:if test="${ModelFiled.orderFlag==1}">
															<img src="../style/icons/tick-small.png" />
														</cms:if>
														<cms:else>
															<img src="../style/icons/cross-small.png" />
														</cms:else>

													</td>

													<td>

														<cms:if test="${ModelFiled.searchFlag==1}">
															<img src="../style/icons/tick-small.png" />
														</cms:if>
														<cms:else>
															<img src="../style/icons/cross-small.png" />
														</cms:else>

													</td>
													
													<td>

														<cms:if test="${ModelFiled.pickFlag==1}">
															<img src="../style/icons/tick-small.png" />
														</cms:if>
														<cms:else>
															<img src="../style/icons/cross-small.png" />
														</cms:else>

													</td>

													

													<td>
														<center>
															<div>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditModelFiledDialog('${param.modelId}','${ModelFiled.metaDataId}')">&nbsp;编辑</a>&nbsp;&nbsp;&nbsp; <img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteModelFiled('${ModelFiled.metaDataId}')">删除 </a></span>
															</div>
														</center>
													</td>
												</tr>
											</cms:SystemModelFiled>
										</cms:SystemModelFiledList>
										<cms:Empty flag="ModelFiled">
											<tr>
												<td class="tdbgyew" colspan="13">
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


function openPrev()
{
	 	
	window.open('PreviewModelHtmlLayout.jsp?modelId=${param.modelId}','','width=1100,height=580，top=20,left=40,toolbar=no,menubar=no,scrollbars=yes,titlebar=no, resizable=no,location=no, status=no');
	 

}

function changeModelFieldOrder()
{
	var orderValInput = document.getElementsByName('fileldOrder');
	
	var newOrderValArray = new Array();
	for(var i = 0; i < orderValInput.length; i++)
	{
		newOrderValArray.push(orderValInput[i].id.replace('order-','')+ '-'+orderValInput[i].value);	
	}
	
	var blankValInput = document.getElementsByName('fileldBlank');
	
	var blankValArray = new Array();
	for(var i = 0; i < blankValInput.length; i++)
	{
		blankValArray.push(blankValInput[i].id.replace('blank-','')+ '-'+blankValInput[i].value);	
	}
	
	 
	var url = "<cms:BasePath/>metadata/changeFieldOrder.do?modelId=${param.modelId}&newOrderInfo="+newOrderValArray.join('*')+"&blankCountInfo="+blankValArray.join('*')+"&<cms:Token mode='param'/>";

 	$.ajax({
      	type: 'POST',
       	url: url,
       	data:'',
   
       	success: function(mg)
        {        
           var msg = eval("("+mg+")");
           
            if('success' == msg)
            {
            	$.dialog
			    ({ 
			  		title : '提示',
			    	width: '160px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/succ.png', 
			    				
			        content: '更新模型字段新排位成功!', 
			       	ok: function()
			       	{
			       		window.location.reload();
			       	}
				 });
				 return;
		        
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
}

function openCreateModelFiledDialog(modelId)
{
	$.dialog({ 
	    id : 'cmf',
    	title : '创建模型字段',
    	width: '670px', 
    	height: '593px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        
        
        content: 'url:<cms:Domain/>core/metadata/AddModelFiled.jsp?modelType=${param.modelType}&modelId='+modelId+'&uid='+Math.random()

	});
}

function openEditModelFiledDialog(modelId,fieldId)
{
	$.dialog({ 
	    id : 'emf',
    	title : '编辑模型字段',
    	width: '670px', 
    	height: '593px',  
    	lock: true, 
        max: false, 
        min: false,
        resize: false,

        content: 'url:<cms:Domain/>core/metadata/EditModelFiled.jsp?modelType=${param.modelType}&modelId='+modelId+'&fieldId='+fieldId+'&uid='+Math.random()
	});
}



function manageModelField(modelId)
{
	window.location.href="ManageModelField.jsp";
}




function deleteModelFiled(filedId)
{
	$.dialog
			    ({ 
			  		title : '提示',
			    	width: '245px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/i.png', 
			    				
			        content: '当前字段相关信息将被物理删除,不可恢复,您确定要删除吗?', 
			       	ok: function()
			       	{
			       		$.dialog.tips('正在更新系统元数据 可能需要较长时间...',90000); 
			       		window.location.href='../../metadata/deleteModelFiled.do?filedMetedataId='+filedId+"&modelId="+${param.modelId}+"&modelType=${param.modelType}"+"&<cms:Token mode='param'/>";

			       	},
			       	cancel:true
				 });

 

}

function previewModelHtmlLayout()
{
	//window.showModalDialog("PreviewModelHtmlLayout.jsp?modelId="+${param.modelId},"","dialogWidth=960px;dialogHeight=630px;status=no");	
//window.location.href="PreviewModelHtmlLayout.jsp?modelId="+${param.modelId};

window.open("PreviewModelHtmlLayout.jsp?modelId="+${param.modelId}); 
}

function changeTRBG(obj, index)
{
	//背景色

	if(obj.checked == true)
	{
		$('#tr-'+index).addClass("tdbgyewck"); 
	}
	else
	{
		$('#tr-'+index).removeClass("tdbgyewck"); 
	}

}

function backManageModel()
{
	if('${param.modelType}' == '3')
	{
		window.location = 'ManageChannelDataModel.jsp';
	}
	else if('${param.modelType}' == '5')
	{
		window.location = 'ManageAdCfgDataModel.jsp';
	}
	else if('${param.modelType}' == '6')
	{
		window.location = 'ManageAdContentDataModel.jsp';
	}
	else if('${param.modelType}' == '4')
	{
		window.location = 'ManageGbDataModel.jsp';
	}
	else if('${param.modelType}' == '7')
	{
		window.location = 'ManageSiteDataModel.jsp';
	}
	else if('${param.modelType}' == '8')
	{
		window.location = 'ManageMemberDataModel.jsp';
	}
	else if('${param.modelType}' == '9')
	{
		window.location = 'ManageDefFormlDataModel.jsp';
	}
	else if('${param.modelType}' == '10')
	{
		window.location = 'ManageCommendModel.jsp';
	}
 	else if('${param.modelType}' == '2')
	{
		window.location = 'ManageDataModel.jsp';
	}

}


</script>
