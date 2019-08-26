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
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		<script>
	

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
						<a href="#">站点相关词</a> &raquo; 外链词管理
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="roleForm" name="roleForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										<a href="javascript:openAddOutLinkWordDialog();" class="btnwithico" onclick=""><img src="../style/icons/tick.png" width="16" height="16" /><b>添加外链词&nbsp;</b> </a>

										<a href="javascript:deleteCheckedRole();" class="btnwithico" onclick=""><img src="../style/blue/icon/application--minus.png" width="16" height="16" /><b>删除&nbsp;</b> </a>

									</div>
									<div class="fr">

									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">

												<td width="2%">
													<strong>ID</strong>
												</td>

												<td width="1%">
													<input class="inputCheckbox" value="*" onclick="" type="checkbox" />
												</td>
												<td width="10%">
													<strong>关注词语</strong>
												</td>

												<td width="12%">
													<strong>访问URL地址</strong>
												</td>

												<td width="5%">
													<strong>打开方式</strong>
												</td>

												<td width="5%">
													<strong>操作</strong>
												</td>
											</tr>

											<cms:CurrentSite>


												<tr>
													<td>
														1
													</td>
													<td>
														<input class="inputCheckbox" id="checkedRoleId" name="checkedRoleId" value="${Gateway.gatewayId}" type="checkbox" onclick="javascript:" />
													</td>
													<td>
														百度, baidu
													</td>
													<td>
														http://www.baidu.com
													</td>

													<td>
														新页面
													</td>

													<td>
														<div>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="EditSystemRole.jsp?roleId=${Role.roleId}">编辑</a>&nbsp; &nbsp; <img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除 </a>&nbsp;&nbsp;&nbsp; </span>
															</center>
														</div>
													</td>
												</tr>

												<tr>
													<td>
														2
													</td>
													<td>
														<input class="inputCheckbox" id="checkedRoleId" name="checkedRoleId" value="${Gateway.gatewayId}" type="checkbox" onclick="javascript:" />
													</td>
													<td>
														CMS
													</td>
													<td>
														http://www.jtopcms.com
													</td>

													<td>
														新页面
													</td>

													<td>
														<div>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="EditSystemRole.jsp?roleId=${Role.roleId}">编辑</a>&nbsp; &nbsp; <img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除 </a>&nbsp;&nbsp;&nbsp; </span>
															</center>
														</div>
													</td>
												</tr>



												<tr>
													<td>
														3
													</td>
													<td>
														<input class="inputCheckbox" id="checkedRoleId" name="checkedRoleId" value="${Gateway.gatewayId}" type="checkbox" onclick="javascript:" />
													</td>
													<td>
														凤凰网
													</td>
													<td>
														http://www.ifeng.com/
													</td>

													<td>
														新页面
													</td>

													<td>
														<div>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="EditSystemRole.jsp?roleId=${Role.roleId}">编辑</a>&nbsp; &nbsp; <img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除 </a>&nbsp;&nbsp;&nbsp; </span>
															</center>
														</div>
													</td>
												</tr>

												<tr>
													<td>
														4
													</td>
													<td>
														<input class="inputCheckbox" id="checkedRoleId" name="checkedRoleId" value="${Gateway.gatewayId}" type="checkbox" onclick="javascript:" />
													</td>
													<td>
														优酷, youku
													</td>
													<td>
														http://www.youku.com/
													</td>

													<td>
														当前页面
													</td>

													<td>
														<div>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="EditSystemRole.jsp?roleId=${Role.roleId}">编辑</a>&nbsp; &nbsp; <img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除 </a>&nbsp;&nbsp;&nbsp; </span>
															</center>
														</div>
													</td>
												</tr>


											</cms:CurrentSite>


										</table>
									</div>
									<div class="mainbody-right"></div>
								</td>
							</tr>


						</table>

						</form>

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

function openAddOutLinkWordDialog()
{
	$.dialog({ 
    	title :'新增外链词',
    	width: '450px', 
    	height: '210px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/words/CreateOutLinkWord.jsp'
	});
}


</script>
