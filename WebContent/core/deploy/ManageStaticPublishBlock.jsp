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
		<script type="text/javascript" src="../javascript/uuid.js"></script>
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
						<a href="#"></a> &raquo;
						<a href="#">区块静态发布</a>
					</td>
					<td align="right"></td>
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
									<select class="form-select" id="typeId" name="typeId" onchange="javascript:change();">
										<option value="-1">
											------ 所有区块 ------
										</option>
										<cms:SystemBlockType>
											<option value="${BlockType.blockTypeId}">
												${BlockType.blockTypeName}
											</option>
										</cms:SystemBlockType>
									</select>
									&nbsp;
								</div>
								<div>
									<a href="javascript:submitPublishSomeBlock();" class="btnwithico"> <img src="../../core/style/icons/layer--arrow.png" alt="" /><b>发布所选区块&nbsp;</b> </a>

								</div>
								<div class="fr">
								</div>

							</td>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">

											<td width="2%" height="30">
												<input type="checkbox" name="checkbox" onclick="javascript:selectAll('checkedId',this);" />
											</td>
											<td width="14%">
												<strong>区块名称</strong>
											</td>


											<td width="7%">
												<strong>上次发布时间</strong>
											</td>


											<td width="4%">
												<strong>发布频率</strong>
											</td>

											<td width="6%">
												<strong>创建者</strong>
											</td>

											<td width="5%">
												<center><strong>操作</strong></center>
											</td>
										</tr>

										<cms:SystemBlockInfo typeId="${param.typeId}">
											<tr>

												<td>
													<cms:if test="${TreeItem.isType == false}">
														<input type="checkbox" name="checkedId" value="${TreeItem.flag}" id="check${TreeItem.flag}" />
													</cms:if>
													<cms:else>
														<input disabled type="checkbox" name="checkedId" value="" />
													</cms:else>

												</td>

												<td>
													<cms:if test="${TreeItem.isType == true}">
														<img src="<cms:Domain/>core/style/default/images/t_small.png" />${TreeItem.blockTypeName}
													</cms:if>
													<cms:else>
														&nbsp;&nbsp;&nbsp;&nbsp;<img src="<cms:Domain/>core/style/default/images/control_small.png" />${TreeItem.blockName}
													</cms:else>
												</td>

												<td>
													<cms:if test="${TreeItem.isType == false}">
													<cms:QueryData objName="PubTr" service="cn.com.mjsoft.cms.block.service.BlockService" method="getBlockPubDateInfoTag" var="${TreeItem.blockId}"> 
														<cms:if test="${empty PubTr.lastPubDT}">
															暂未执行
														</cms:if>
														<cms:else>
															<cms:FormatDate date="${PubTr.lastPubDT}" />
														</cms:else>
														
													</cms:QueryData>
													</cms:if>

												</td>

												<td>
													<cms:if test="${TreeItem.isType == false}">
														&nbsp;&nbsp;${TreeItem.pubPeriod}
													</cms:if>

												</td>


												<td>
													${TreeItem.creator}
												</td>
												<td>
													<cms:if test="${TreeItem.isType == false}">
														<div>
															<center>
																<a href="javascript:submitPublishBlock('${TreeItem.flag}')"><span><img src="../../core/style/icons/layer--arrow.png" width="16" height="16" />&nbsp;立即发布</a>
															</center>
														</div>
													</cms:if>
													<cms:else />


												</td>

											</tr>
										</cms:SystemBlockInfo>
										<cms:Empty flag="TreeItem">
											<tr>
												<td class="tdbgyew" colspan="9">
													<center>
														当前没有数据!
													</center>
												</td>
											</tr>
										</cms:Empty>

										<tr>
											<td colspan="8" class="PageBar" align="left">
												<div class="fr">

												</div>
												<div class="fl"></div>
											</td>
										</tr>

									</table>
									<div class="mainbody-right"></div>
								</div>

							</td>
						</tr>

					</table>

				</td>
			</tr>
		</table>
		<form method="post" id="generateForm" name="generateForm">
			<input type="hidden" id="staticType" name="staticType" />
			<input type="hidden" id="blockFlag" name="blockFlag" />
			<input type="hidden" id="pubEventKey" name="pubEventKey" />
			<input type="hidden" id="siteId" name="siteId" />
		</form>

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>


<script type="text/javascript">

initSelect('typeId','${param.typeId}');

function submitPublishSomeBlock()
{
	var cidCheck = document.getElementsByName('checkedId');
	
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
    				
                    content: '请选择需要发布的区块！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	submitPublishBlock(ids);

}

function submitPublishBlock(blockFlag)
{   
	var tip = $.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	var key = new UUID().id;
	 
	document.getElementById("pubEventKey").value = key; 
	document.getElementById("staticType").value=5;
	<cms:CurrentSite>
	document.getElementById("siteId").value='${CurrSite.siteId}';
	</cms:CurrentSite>
	
	
	document.getElementById("blockFlag").value=blockFlag;

    
    var url = "<cms:BasePath/>publish/generateContent.do";
    
    var postData = $("#generateForm").serialize();
 		
 	$.ajax({
      	type: "POST",
       	url: url,
       	data:postData,
   
       	success: function(mg)
        {        
      		 var msg = eval("("+mg+")");
      		 
            if('success_block' != msg)
            {
            	$.dialog
			    ({ 
			  		title : '提示',
			    	width: '270px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/fail.png', 
			    				
			        content: msg, 
			       	cancel: true
				 });
            }
            else
            {
            	$.dialog
			    ({ 
			  		title : '提示',
			    	width: '120px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/succ.png', 
			    				
			        content: '发布区块成功', 
			       	ok: function()
			       	{			       		
			       		window.location.reload();
			       	}
				 });
            	
            	
            }
        }
     });
}

function openCreateBlockDialog()
{
	$.dialog({ 
	    id : 'cbd',
    	title : '添加区块',
    	width: '610px', 
    	height: '340px', 
    	lock: true, 
        max: true, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/block/CreateBlock.jsp?uid='+Math.random()
	});
}

function openCreateBlockTypeDialog()
{
	$.dialog({ 
	    id : 'cbt',
    	title : '新建分类',
    	width: '380px', 
    	height: '90px', 
    	lock: true, 
        max: true, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/block/CreateBlockType.jsp?uid='+Math.random()

	});
}

function deleteBlock(blockId)
{
	window.location.href = '../../block/deleteBlock.do?blockId='+blockId;
}

function openEditCodeAndTextDialog(entry,name)
{
	if(entry == '')
	{
		$.dialog.tips('区块模板不存在...',1); 
		return;
	}
	var type = 'text/html';
	
	entry = '*template*'+entry.replaceAll('/','*');

	$.dialog({ 
    	title :'编辑文本内容',
    	width: '1200px', 
    	height: '580px', 
    	lock: true, 
        max: false, 
       
        
        resize: false,
             
        content: 'url:<cms:Domain/>resources/directViewPage.do?entry='+encodeURIComponent(encodeURIComponent(entry))+'&type='+type
	});
	

}

function change()
{
	replaceUrlParam(window.location,'typeId='+$('#typeId').val());
}

</script>

