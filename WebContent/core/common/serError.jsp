<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="<cms:BasePath/>/core/style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="<cms:BasePath/>/core/style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<!--加载 js -->



	</head>
	<body> 


		<%--<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="<cms:BasePath/>core/style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">权限提示</a>

					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
	 --%><div style="height:5px;"></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

						<tr>
							<td style="padding: 2px 5px;">
								<div class="fl">
							</td>
						</tr>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="4%" height="30">
												<img src="<cms:BasePath/>core/style/icon/exclamation-white.png" width="16" height="16" class="home" />
												<strong>系统提示:</strong>
											</td>

										</tr>


										<tr>
											<td class="tdbgyew" colspan="9">
												<center>
													您没有操作权限!
												</center>
											</td>
										</tr>
										<tr>
											<td colspan="9">
												<center>

												</center>
											</td>
										</tr>



									</table>


								</div>

							</td>
						</tr>

					</table>

				</td>
			</tr>


		</table>


		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>




