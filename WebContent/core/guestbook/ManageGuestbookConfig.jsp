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
						<a href="#">站点维护</a> &raquo;
						<a href="#">留言板配置</a>
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
									<a href="javascript:openCreateGbConfigDialog();" class="btnwithico"> <img src="../../core/style/icons/socket--plus.png" alt="" /><b>新建留言板配置&nbsp;</b> </a>
									<a href="javascript:deleteGbConfig('');" class="btnwithico" onclick=""> <img src="../../core/style/default/images/del.gif" alt="" /><b>删除&nbsp;</b> </a>
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
												<strong>ID</strong>
											</td>
											<td width="1%" height="30">
												<input type="checkbox" name="checkbox" class="form-checkbox" onclick="javascript:selectAll('checkedId',this);"/>
											</td>
											<td width="10%">
												<strong>留言板名称</strong>
											</td>
											
											<td width="9%">
												<strong>访问标识</strong>
											</td>


											<td width="18%">
												<strong>简介</strong>
											</td>


											<td width="3%">
												<strong>状态</strong>
											</td>



											<td width="10%">
												<center><strong>维护</strong></center>
											</td>
										</tr>



										<cms:SystemGbConfig configId="all">
											<tr>
												<td>
													${GbCfg.configId}
												</td>
												<td>
													<input type="checkbox" name="checkedId" value="${GbCfg.configId}"  />
												</td>

												<td>
													${GbCfg.cfgName}
												</td>
												
												<td>
													${GbCfg.cfgFlag}
												</td>

												<td>
													<cms:SubString len="30" tail="..." str="${GbCfg.cfgDesc}" />
												</td>

												<td>
													<cms:if test="${GbCfg.useState==1}">
														<font color="green">启用</font>
													</cms:if>
													<cms:else>
														<font color="red">停用</font>
													</cms:else>
												</td>



												<td>
													<div>
														<center>
															<span ><a href="javascript:openEditGbConfigDialog('${GbCfg.configId}')"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<a href="javascript:deleteGbConfig('${GbCfg.configId}');"><img src="../../core/style/default/images/del.gif" width="16" height="16" />删除 </a></span>
														</center>
													</div>
												</td>

											</tr>
										</cms:SystemGbConfig>

										<cms:Empty flag="GbCfg">
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


function openCreateGbConfigDialog()
{
	$.dialog({ 
	    id : 'ocacd',
    	title : '新建留言板配置',
    	width: '640px', 
    	height: '565px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/guestbook/CreateGuestbookConfig.jsp?uid='+Math.random()

	});
}

function openEditGbConfigDialog(configId)
{
	$.dialog({ 
	    id : 'oeacd',
    	title : '编辑留言板配置',
    	width: '640px', 
    	height: '565px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/guestbook/EditGuestbookConfig.jsp?uid='+Math.random()+'&configId='+configId

	});
}

function deleteGbConfig(configId)
{
	var ids='';
	
	if(configId == '')
	{
		var cidCheck = document.getElementsByName('checkedId');
		
		
		
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
	    				
	                    content: '请选择要删除的留言板！', 
	       cancel: true 
	                    
		
		  });
		  return;
		}
	}
	else
	{
		ids=configId;
	}
	
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '190px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除当前留言板配置吗？其所属留言都将被删除!',
                    
                    ok: function () { 
                    
                    var url = '<cms:BasePath/>guestbook/deleteGbConfig.do?ids='+ids+"&<cms:Token mode='param'/>";
 		
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
				    				icon: '32X32/succ.png', 
				    				
				                    content: '删除留言分类成功！',
				                    
				                    ok: function () { 
				                    	window.location.reload();
				                    }
				                })
			               		
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

