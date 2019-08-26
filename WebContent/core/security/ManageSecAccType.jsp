<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/uuid.js"></script>
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
						<a href="#">权限资源</a> &raquo; <a href="#">分类授权类型</a>
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
										
										</div>
									
									<div>
										<a href="javascript:openCreateSecTypeDialog();" class="btnwithico" onclick=""><img src="../style/icons/zone--plus.png" width="16" height="16" /><b>新增授权类型&nbsp;</b> </a>
										<span>(扩展权限业务类提供进一步的和业务相关的复杂权限,非必须)</span>
										</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">



												<td width="2%">
													<strong>ID</strong
												</td>
												<td width="5%">
													<strong>授权类型</strong>
												</td>

												<td width="5%">
													<strong>审查标志</strong>
												</td>
												
												<td width="17%">
													<strong>扩展权限业务类</strong>
												</td>

												
												<td width="3%">
													<strong>系统默认</strong>
												</td>
												
												<td width="5%">
													<center><strong>操作</strong></center>
												</td>

												
											</tr>
											<cms:QueryData service="cn.com.mjsoft.cms.security.service.SecurityService" method="getAccSecDataTypeTag" objName="At" var="">

												<tr>

													<td>
													${At.dataTypeId}
													</td>
													
													<td>														
															${At.typeName}
													</td>
													
													<td>
															${At.accSymbol}
													</td>		
																					
													<td>														
															${At.accBehaviorClass}											
													</td>
													
													<td>														
															<cms:if test="${At.isSys == 1}">
																是
															</cms:if>		
															<cms:else>
																否
															</cms:else>							
													</td>
													
													<td>
															<div>
																<center>
																	<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditSecTypeDialog('${At.dataTypeId}');">&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteSecType('${At.dataTypeId}','${At.isSys}');">删除</a> 
																	</span>
																</center>
															</div>
													</td>
												</tr>

											</cms:QueryData>
											<cms:Empty flag="At">
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
<cms:LoginUser>
<script>

function openCreateSecTypeDialog()
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '210px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有机构主管允许新增细粒度授权！', 
	       cancel: true 
	                    
		  });
		  return;
	}

	$.dialog({ 
		id:'ocstd',
    	title :'新增细粒度授权类型',
    	width: '660px', 
    	height: '390px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/security/CreateSecType.jsp'
	});
}

function openEditSecTypeDialog(id)
{
	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '210px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有机构主管允许编辑细粒度授权！', 
	       cancel: true 
	                    
		  });
		  return;
	}

	$.dialog({ 
		id:'oestd',
    	title :'编辑细粒度授权类型',
    	width: '660px', 
    	height: '390px',  
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/security/EditSecType.jsp?id='+id
	});
}

function deleteSecType(id, isSys)
{
	if('1' == isSys)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '210px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '不允许删除系统默认细粒度授权！', 
	       cancel: true 
	                    
		  });
		  return;
	
	}

	<cms:SystemOrg orgCode="${Auth.orgCode}">
											
		var orgBossId = '${Org.orgBossId}';
		
		var currOrgId = '${Auth.identity}';
		
	</cms:SystemOrg>
	
	if(orgBossId != currOrgId)
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '210px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只有机构主管允许删除细粒度授权！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除细粒度授权吗？',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>security/deleteSecType.do?id="+id+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
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
				    				
				                    content: '删除细粒度授权成功!',
				                    
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
</cms:LoginUser>
