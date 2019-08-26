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
						<a href="#">区块数据管理</a> &raquo; 头条新闻
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
									<a href="javascript:openSelectSiteAndPushContentDialog();" class="btnwithico"> <img src="../../core/style/default/images/doc_add.png" alt="" /><b>选取站点内容&nbsp;</b> </a>
									<a href="javascript:openSelectSiteAndPushContentDialog();" class="btnwithico"> <img src="../../core/style/default/images/doc_add.png" alt="" /><b>插入单条内容&nbsp;</b> </a>
									
									<a href="javascript:;" class="btnwithico" onclick=""> <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>立即发布&nbsp;</b> </a>
									<a href="javascript:openCreateBlockTypeDialog();" class="btnwithico"> <img src="../../core/style/default/images/doc_add.png" alt="" /><b>预览&nbsp;</b> </a>
								</div>
								<div class="fr">
									<a href="javascript:gotoManageBlockPushContent();" class="btnwithico"> <img src="../../core/style/default/images/doc_add.png" alt="" /><b>返回区块维护&nbsp;</b> </a>

								</div>




							</td>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table class="listdate" width="100%" height="100%"cellpadding="0" cellspacing="0">

										<tr class="datahead">

											<td width="2%">

											</td>
											<td width="67%">
												<strong>标题</strong>
											</td>






											<td width="6%">
												<strong>动作</strong>
											</td>
										</tr>

										<cms:SystemBlockInfo>
											<tr height="20">

												<td>
													1.
												</td>

												<td>
													<input type="radio" name="radio" value="radio" />药监局：对制售假药企业撤销药品批准证明文件 <input type="radio" name="radio" value="radio" />药监局：对制售假药企业撤销药品批准证明文件<input type="radio" name="radio" value="radio" /> 药监局：对制售假药企业撤销药品批准证明文件<input type="radio" name="radio" value="radio" />

												</td>

												<td>
													<div>
														<center>
															<span ><img src="../../core/style/icons/plus.png" width="16" height="16" alt="添加新行"/>&nbsp;&nbsp;&nbsp;<img src="../../core/style/icons/cross.png" width="16" height="16" alt="删除当前行"/>
															<br/><br/></span>
														</center>
													</div>
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

			<tr>
				<td height="10">
					&nbsp;
				</td>
			</tr>
		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">



function gotoManageBlockPushContent()
{
	window.location.href = 'ManageBlockPushContent.jsp';
}

function openSelectSiteAndPushContentDialog()
{
	$.dialog({ 
	    id : 'ospcd',
    	title : '选取区块数据',
    	width: '830px', 
    	height: '580px', 
    	lock: true, 
        max: true, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/block/SelectSiteAndPushContent.jsp?uid='+Math.random()
	});
}


</script>

