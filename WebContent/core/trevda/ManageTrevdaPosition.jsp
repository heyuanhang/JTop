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
		<script type="text/javascript" src="../common/js/jquery-1.7.min.js"></script>
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
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0" >
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">交互信息</a> &raquo;
						<a href="#">广告版位管理</a>
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
							<td style="padding: 6px 11px;" class="">
								<div class="fl">
									<a href="javascript:openCreateAdvertPosDialog('-1');" class="btnwithico"> <img src="../style/icons/ui-scroll-pane-image.png" alt="" /><b>添加广告版位&nbsp;</b> </a>
									&nbsp;(请关闭浏览器广告屏蔽插件以管理广告)
											</div>
								<div class="fr">
								</div>
							</td>
						<tr>
							<td id="uid_td25" style="padding: 1px 9px;">
								<div class="DataGrid">

									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="3%" height="30">
												<strong>ID</strong>
											</td>
											 
											<td width="14%">
												<strong>版位名称</strong>
											</td>

											<td width="8%">
												<strong>引用名称</strong>
											</td>


											<td width="9%">
												<strong>版位类型</strong>
											</td>

											<td width="5%">
												<strong>排位方式</strong>
											</td>




											<td width="11%">
												<center>
													<strong>维护</strong>
												</center>
											</td>
										</tr>


										<cms:SystemAdvertPos posId="all">
											<tr>
												<td>
													${Pos.posId}
												</td>
												 

												<td>
													${Pos.posName}
												</td>

												<td>
													${Pos.posFlag}
												</td>
												<td>
													<cms:SystemAdvertConfig configId="${Pos.configId}">
													${Config.configName}
													</cms:SystemAdvertConfig>
												</td>

												<td>
													<cms:if test="${Pos.showMode==1}">随机投放</cms:if>
													<cms:elseif test="${Pos.showMode==2}">权重优先</cms:elseif>
													<cms:else></cms:else>

												</td>

												<td>
													<div>
														<center>
															<span><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditTrevdaPosDialog('${Pos.posId}')">&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteTrevdaPos('${Pos.posId}');">删除 </a></span>
														</center>
													</div>
												</td>

											</tr>
										</cms:SystemAdvertPos>


										<cms:Empty flag="Pos">
											<tr>
												<td class="tdbgyew" colspan="10">
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
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">


function openCreateAdvertPosDialog(cfgId)
{
	$.dialog({ 
	    id : 'ocapd',
    	title : '新建广告版位',
    	width: '590px', 
    	height: '450px',
    	lock: true,     
        min: false,
        max: false,
        resize: false,
        
        content: 'url:<cms:BasePath/>core/trevda/CreateTrevdaPosition.jsp?configId='+cfgId+'&uid='+Math.random()

	});
}

function openEditTrevdaPosDialog(posId)
{
	$.dialog({ 
	    id : 'oeapd',
    	title : '编辑广告版位',
    	width: '590px', 
    	height: '450px',
    	lock: true, 
        min: false,
        max: false,
        resize: false,
        
        
        content: 'url:<cms:BasePath/>core/trevda/EditTrevdaPosition.jsp?posId='+posId+"&uid="+Math.random()+'&dialogApiId=oeapd'

	});
}

function deleteTrevdaPos(posId)
{

	var dialog = $.dialog({ 
   					title :'提示',
    				width: '220px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除当前广告版位吗？<br/>所属广告将同时被删除!',
                    
                    ok: function () { 
       
    window.location.href = '<cms:BasePath/>trevda/deleteTrevdaPos.do?posId='+posId+"&<cms:Token mode='param'/>";
    }, 
    cancel: true 
    });
	
}


</script>

