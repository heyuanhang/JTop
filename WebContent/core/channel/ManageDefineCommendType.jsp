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
						<a href="#">内容组织</a> &raquo;
						<a href="#">推荐位</a>
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
									<select class="form-select" id="classId" name="classId" onchange="javascript:changeCommendTypeClass(this.value)">
										<option value="" selected>
											------ 所有推荐位 ------
										</option>
										<option value="-9999">
											全站共用推荐位
										</option>
										<cms:CurrentSite>
											<cms:SystemClassList site="${CurrSite.siteFlag}" type="all">
												<cms:SysClass>
													<option value="${Class.classId}">
														${Class.layerUIBlankClassName}
													</option>
												</cms:SysClass>
											</cms:SystemClassList>
										</cms:CurrentSite>
									</select>

									&nbsp;
								</div>
								<a href="javascript:openCreateCommendDialog();" class="btnwithico"><img src="../../core/style/icons/medal--plus.png" alt="" /><b>创建推荐位&nbsp;</b> </a>
								<a href="javascript:deleteCommendDialog('');" class="btnwithico" onclick=""><img src="../../core/style/default/images/del.gif" alt="" /><b>删除&nbsp;</b> </a>

								<div class="fr">
								</div>

							</td>
						</tr>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="3%" height="30">
												<strong>ID</strong>
											</td>
											<td width="2%" height="30">
												<input type="checkbox" name="checkbox" onclick="javascript:selectAll('checkCommType',this);"/>
											</td>
											<td width="12%">
												<strong>推荐位名称</strong>
											</td>

											<td width="12%">
												<strong>访问标志</strong>
											</td>
											<td width="8%">
												<strong>所属栏目</strong>
											</td>
											<td width="5%">
												<strong>子栏目可用</strong>
											</td>
											<td width="6%">
												<strong>推送</strong>
											</td>

											<td width="10%">
												<center>
													<strong>操作</strong>
												</center>
											</td>
										</tr>


										<cms:SystemCommendType classId="${param.classId}">
											<tr>
												<td>
													${CommendType.commendTypeId}
												</td>
												<td>
													<input type="checkbox" name="checkCommType" value="${CommendType.commendTypeId}" />
												</td>

												<td>
													${CommendType.commendName}
												</td>
												<td>
													${CommendType.commFlag}
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
													<cms:if test="${CommendType.childClassMode==1}">
													是
													</cms:if>
													<cms:else>
														<font style="color:red">否 
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

												<td>
													<div>
														<center>
															<a href="javascript:openEditCommendDialog('${CommendType.commendTypeId}')"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;
															<a href="javascript:deleteCommendDialog('${CommendType.commendTypeId}')"><img src="../../core/style/default/images/del.gif" width="16" height="16" />&nbsp;删除</a>
														</center>
													</div>
												</td>

											</tr>
										</cms:SystemCommendType>
										<cms:Empty flag="CommendType">
											<tr>
												<td class="tdbgyew" colspan="9">
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

initSelect('classId','${param.classId}');

function openCreateCommendDialog()
{
	$.dialog({ 
	    id : 'ccd',
    	title : '创建推荐位',
    	width: '580px', 
    	height: '490px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/channel/CreateCommendType.jsp?uid='+Math.random()
	});
}

function openEditCommendDialog(typeId)
{
	$.dialog({ 
	    id : 'ccd',
    	title : '编辑推荐位',
    	width: '580px', 
    	height: '490px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/channel/EditCommendType.jsp?uid='+Math.random()+'&typeId='+typeId
	});
}


function deleteCommendDialog(ids)
{
	if('' == ids)
	{
		var cidCheck = document.getElementsByName('checkCommType');
		
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
	    				
	                    content: '没有选择任何推荐位！', 
	       cancel: true 
	                    
		
		  });
		  return;
		}
	}
	
	 $.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选推荐位吗？将删除所有附属推荐内容',
                    
                    ok: function () { 
                    
 					var url = "../../channel/deleteCommendType.do?typeIds="+ids+"&<cms:Token mode='param'/>";
 					
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            
			            	var msg = eval("("+mg+")");
           		
			               if('success' == msg)
			               {
			               		 
			               		$.dialog.tips('删除内容成功...',1); 
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

function changeCommendTypeClass(flag)
{
	window.location.href = 'ManageDefineCommendType.jsp?classId='+flag;
}
</script>

