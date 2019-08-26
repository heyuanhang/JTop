<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../../common/js/jquery-1.7.gzjs"></script>

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
		<div style="height:5px"></div>
		<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

			<tr class="datahead">
				<td width="2%">
					<strong> <input type="checkbox" onclick="javascript:selectAll('commendCheck',this);"></input> </strong>
				</td>

				<td width="14%">
					<strong>名称</strong>
				</td>

				<td width="8%">
					<strong>所属</strong>
				</td>

				<td width="8%">
					<strong>推送</strong>
				</td>
			</tr>

			<cms:SystemCommendType classId="${param.classId}" showAll="true">
				<tr>
					<td>
						<input type="checkbox" name="commendCheck" value="${CommendType.commendTypeId}" />
					</td>

					<td>
						${CommendType.commendName}
					</td>

					<td>
						<cms:if test="${CommendType.classId != -9999}">
							<cms:SysClass id="${CommendType.classId}">
								${Class.className}
							</cms:SysClass>
						</cms:if>
						<cms:else>
							全站通用
						</cms:else>
					</td>

					<td>
						<cms:if test="${CommendType.mustCensor == 1}">
							需要审核
						</cms:if>
						<cms:else>
							立即生效
						</cms:else>
					</td>

				</tr>
			</cms:SystemCommendType>
			<cms:Empty flag="CommendType">
				<tr>
					<td class="tdbgyew" colspan="6">
						<center>
							当前没有数据!
						</center>
					</td>
				</tr>
			</cms:Empty>

		</table>
		<div style="height:2px"></div>
	</body>

</html>
