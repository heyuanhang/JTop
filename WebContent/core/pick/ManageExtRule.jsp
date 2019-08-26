<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>

		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>

		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />

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
						<a href="#">发布与采集</a> &raquo;
						<a href="#">采集管理</a> &raquo;
						<a href="#">扩展采集字段</a>
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="flForm" name="flForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										<a href="javascript:openAddExtRuleDialog();" class="btnwithico" onclick=""><img src="../style/icons/validation-valid-document.png" width="16" height="16" /><b>添加扩展规则&nbsp;</b> </a>
										&nbsp;(每种可扩展采集模型可以添加多种采集规则)
								
									</div>
									<div>
											</div>
									<div class="fr">

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
												
												<td width="9%">
													<strong>采集名称</strong>
												</td>

												 
												<td width="9%">
													<strong>数据模型</strong>
												</td>

												
 			

												<td width="5%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:QueryData objName="Er" service="cn.com.mjsoft.cms.pick.service.PickService" method="getPickModelExt" var="-9999">
												<tr>
													<td>
														${Er.eprId}
													</td>
												 
													<td>
														${Er.eprName}
													</td>
													
													<td>
														模型ID : ${Er.modelId}
													</td>
													
													 
													
													<td>
														<div>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditExtRulrDialog('${Er.eprId}');">&nbsp;编辑</a>&nbsp; &nbsp; <img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteExtRule('${Er.eprId}');">删除 </a>&nbsp;&nbsp;&nbsp; </span>
															</center>
														</div>
													</td>
												</tr>
											</cms:QueryData>

											<cms:Empty flag="Er">
												<tr>
													<td class="tdbgyew" colspan="9">
														<center>
															当前没有数据!
														</center>
													</td>
											</cms:Empty>

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
<script type="text/javascript">

function openAddExtRuleDialog()
{
	$.dialog({ 
		id: 'oasld',
    	title :'新增扩展规则',
    	width: '560px', 
    	height: '190px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/pick/CreateExtRule.jsp'
	});
}

function openEditExtRulrDialog(id)
{
	$.dialog({ 
		id: 'oesld',
    	title :'编辑扩展规则',
    	width: '660px', 
    	height: '590px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/pick/EditExtRule.jsp?id='+id
	});
}

 
function deleteExtRule(id)
{
	$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
                    
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选扩展规则吗？',
                    
                    ok: function () 
                    { 
                    
                   
                    	var url = "<cms:BasePath/>pick/deletePickModelExt.do?id="+id+"&<cms:Token mode='param'/>";
	
				
 		
						$.ajax({
								      		type: "POST",
								       		url: url,
								       		data:'',
								   
								       		success: function(mg)
								            {     
								            	var msg = eval("("+mg+")");
								            	
								               if('success' == msg)
								               {
							
								               		
								               		$.dialog({ 
									   					title :'提示',
									    				width: '160px', 
									    				height: '60px', 
									                    lock: true, 
									                   
									    				icon: '32X32/i.png', 
									    				
									                    content: '删除操作成功!',
									                    
									                    ok: function () 
									                    { 
									                    	window.location.reload();
									                    }
									                    
					    								
					                                   	});
								               		
								               	
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
