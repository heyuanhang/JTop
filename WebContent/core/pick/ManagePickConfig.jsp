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
						<a href="#">内容采集</a> &raquo; 采集规则管理
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
										<a href="javascript:openAddPickConfigDialog();" class="btnwithico" onclick=""><img src="../style/icons/validation-valid-document.png" width="16" height="16" /><b>新增采集规则&nbsp;</b> </a>
										<a href="javascript:deletePickRules();" class="btnwithico" onclick=""><img src="../style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>
									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">

												<td width="2%">
													<strong>ID</strong>
												</td>

												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>
												<td width="10%">
													<strong>规则名称</strong>
												</td>

												<td width="16%">
													<strong>描叙</strong>
												</td>



												<td width="6%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:CurrentSite>

												<cms:SystemList querySign="SELECT_PICK_RULE_LIST_QUERY" var="${CurrSite.siteId}">
													<tr>
														<td>
															${SysObj.pickCfgId}
														</td>
														<td>
															<input class="form-checkbox" name="checkedId"  value="${SysObj.pickCfgId}" type="checkbox" />
														</td>
														<td>
															${SysObj.configName}
														</td>
														<td>
															${SysObj.configDesc}
														</td>

														<td>
															<div>
																<center>
																	<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditPickRuleDialog('${SysObj.pickCfgId}');">&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deletePickRule('${SysObj.pickCfgId}');">删除 
																	</span>
																</center>
															</div>
														</td>
													</tr>
												</cms:SystemList>
												<cms:Empty flag="SysObj">
												<tr>
													<td class="tdbgyew" colspan="9">
														<center>
															当前没有数据!
														</center>
													</td>
											</cms:Empty>
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

			

			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>

function openAddPickConfigDialog()
{
	$.dialog({ 
		id:'oapcd',
    	title :'新增采集规则',
    	width: '860px', 
    	height: '620px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/pick/CreatePickConfig.jsp'
	});
}

function openEditPickRuleDialog(cfgId)
{
	$.dialog({ 
		id:'oapcd',
    	title :'编辑采集规则',
    	width: '860px', 
    	height: '660px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/pick/EditPickConfig.jsp?cfgId='+cfgId
	});
}


function deletePickRules()
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
    				
                    content: '请选择需要删除的配置！', 
       cancel: true 
                    
	  });
	  return;
	}

	deletePickRule(ids);
}


//dialog操作
function deletePickRule(ids)
{
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选配置吗？',
                    
                    ok: function () { 
                    
                    var url = "<cms:BasePath/>pick/deletePickRule.do?ids="+ids+"&<cms:Token mode='param'/>";
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
			            	
			               if('success' == msg)
			               {
			               		window.location.reload();
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


</script>
