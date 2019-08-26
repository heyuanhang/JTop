<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

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
						<a href="#">站点维护</a> &raquo;
						<a href="#">内容组织</a> &raquo;
						<a href="#">Tag词分类</a>
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
										<a href="javascript:openAddTagClassDialog();" class="btnwithico" onclick=""><img src="../style/icons/tag--plus.png" width="16" height="16" /><b>添加Tag分类&nbsp;</b> </a>
										<a href="javascript:deleteTagClass('');" class="btnwithico" onclick=""><img src="../../core/style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>
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

												<td width="2%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedTagType',this);" type="checkbox" />
												</td>
												<td width="18%">
													<strong>Tag类别名</strong>
												</td>

												<td width="12%">
													<strong>访问标识</strong>
												</td>

												<td width="8%">
													<strong>操作</strong>
												</td>
											</tr>


											<cms:QueryData service="cn.com.mjsoft.cms.channel.service.ChannelService" method="getTagTypeListQueryTag" objName="TagType">
												<tr>
													<td>
														${TagType.tagTypeId}
													</td>
													<td>
														<input class="inputCheckbox" name="checkedTagType" value="${TagType.tagTypeId}" type="checkbox" onclick="javascript:" />
													</td>
													<td>
														&nbsp;${TagType.tagTypeName}
													</td>
													<td>
														${TagType.flag}
													</td>

													<td>
														<div>
															<center>
																<span class="STYLE4"><a href="javascript:openEditTagClassDialog('${TagType.tagTypeId}');"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<a href="javascript:deleteTagClass('${TagType.tagTypeId}');"><img src="../../core/style/default/images/del.gif" width="16" height="16" />删除 </a></span>
															</center>
														</div>
													</td>
												</tr>

											</cms:QueryData>
											<cms:Empty flag="TagType">
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

function openAddTagClassDialog()
{
	$.dialog({ 
	    id : 'oascd',
    	title : '增加Tag分类',
    	width: '530px', 
    	height: '190px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/channel/AddTagClass.jsp'
	});
}

function openEditTagClassDialog(id)
{
	$.dialog({ 
	    id : 'oescd',
    	title : '编辑Tag分类',
    	width: '530px', 
    	height: '190px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
           
        content: 'url:<cms:Domain/>core/channel/EditTagClass.jsp?tagTypeId='+id
	});
}



function openAddTagDialog()
{
	$.dialog({ 
    	title :'新增Tag',
    	width: '400px', 
    	height: '180px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/words/CreateSiteTag.jsp'
	});
}


function deleteTagClass(id)
{
	 var ids='';
	 
	 if('' == id)
	 {
	 	var cidCheck = document.getElementsByName('checkedTagType');
	
		
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
	                    content: '没有选择任何Tag分类！', 
	       				cancel: true 
		  });
		  
		  return;
		}
	 }
	 else
	 {
	 	ids = id;
	 }


	 $.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选Tag分类吗？将删除所有所属Tag！',
                    
                    ok: function () { 
                    
 					var url = "<cms:BasePath/>channel/deleteTagClass.do?ids="+ids+"&<cms:Token mode='param'/>";
 					
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
