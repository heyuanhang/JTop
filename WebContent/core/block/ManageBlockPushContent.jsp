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
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>

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
						<a href="#">区块内容管理</a>
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
									<a href="javascript:openCreateBlockDialog();" class="btnwithico"> <img src="../../core/style/default/images/doc_add.png" alt="" /><b>发布所选区块&nbsp;</b> </a>

								</div>
								<div class="fr">
								</div>




							</td>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">

											<td width="2%" height="30">
												<input type="checkbox" name="checkbox" value="checkbox" />
											</td>
											<td width="17%">
												<strong>区块名称</strong>
											</td>


											<td width="5%">
												<strong>上次发布时间</strong>
											</td>


											<td width="4%">
												<strong>区块类型</strong>
											</td>

											<td width="6%">
												<strong>操作者</strong>
											</td>

											<td width="5%">
												<strong>维护</strong>
											</td>
										</tr>

										<cms:SystemBlockInfo>
											<tr height="20">

												<td>
													<input type="checkbox" name="checkContent" value="${TreeItem.blockId}" id="check${TreeItem.blockId}" onclick="javascript:regId();" />
												</td>

												<td>
													<cms:if test="${TreeItem.isType == true}">
														<img src="<cms:Domain/>core/style/default/images/t_small.png"/>${TreeItem.blockTypeName}
													</cms:if>
													<cms:else>
														&nbsp;&nbsp;&nbsp;&nbsp;<img src="<cms:Domain/>core/style/default/images/control_small.png"/>${TreeItem.blockName}
													</cms:else>
												</td>

												<td>
													<cms:if test="${TreeItem.isType == false}">${TreeItem.lastPubTime}</cms:if>
													<cms:else />
												</td>

												<td>
													<cms:if test="${TreeItem.isType == false}">
														<cms:if test="${TreeItem.type==1}">
														模板驱动
													</cms:if>
														<cms:else>
													     手动推送
													</cms:else>
													</cms:if>
													<cms:else />
												</td>


												<td>
													${TreeItem.creator}
												</td>
												<td>
													<cms:if test="${TreeItem.isType == false}">
														<div>
															<center>
																<span ><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:gotoMaintainBlockData('${TreeItem.blockId}')">管理推送内容</a>
															</center>
														</div>
													</cms:if>
													<cms:else>

													</cms:else>
												</td>

											</tr>
										</cms:SystemBlockInfo>


										<tr>
											<td colspan="8" class="PageBar" align="left">
												<div class="fr">

												</div>
												<div class="fl"></div>
											</td>
										</tr>

									</table>

								</div>
							<div class="mainbody-right"></div>
							</td>
						</tr>

					</table>

				</td>
			</tr>
		</table>
		<form method="post" action="../../publish/generateContent.do" id="generateForm" name="generateForm">

			<input type="hidden" id="staticType" name="staticType" />
			<input type="hidden" id="blockFlag" name="blockFlag" />
		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">


function gotoMaintainBlockData(blockId)
{
	window.location.href = 'MaintainBlockData.jsp?blockId='+blockId;
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



</script>

