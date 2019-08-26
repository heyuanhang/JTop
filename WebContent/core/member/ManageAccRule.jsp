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
						<a href="#">会员权限</a> &raquo;
						<a href="#">权限规则管理</a>
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
										<a href="javascript:openAddAccRuleDialog();" class="btnwithico" onclick=""><img src="../../core/style/icons/pencil-ruler.png" width="16" height="16" /><b>添加操作规则&nbsp;</b> </a>
										<a href="javascript:deleteMemAcc('');" class="btnwithico" onclick=""><img src="../../core/style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>
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

												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedIds',this);" type="checkbox" />
												</td>
												<td width="7%">
													<strong>规则名</strong>
												</td>

												<td width="13%">
													<strong>描叙</strong>
												</td>
												
												<td width="2%">
													<strong>类型</strong>
												</td>

												<td width="6%">
													<center><strong>操作</strong></center>
												</td>
											</tr>


											<cms:QueryData service="cn.com.mjsoft.cms.security.service.SecurityService" method="getMemberAccRuleListForTag" objName="MemAcc">
												<tr>
													<td>
														${MemAcc.accRuleId}
													</td>
													<td>
														<input class="inputCheckbox" name="checkedIds" value="${MemAcc.accRuleId}" type="checkbox" onclick="javascript:" />
													</td>
													<td>
														&nbsp;${MemAcc.accName}
													</td>
													<td>
														 ${MemAcc.ruleDesc}
													</td>
													
													<td>
													
													<cms:if test="${MemAcc.typeId == 1}">
														<font color="blue">浏览</font>
													</cms:if>
													<cms:else>
														<font color="green">投稿</font>
													</cms:else>
														 
													</td>

													<td>
														<div>
															<center>
																<span class="STYLE4"><a href="javascript:openEditAccRuleDialog('${MemAcc.accRuleId}');"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<a href="javascript:deleteMemAcc('${MemAcc.accRuleId}');"><img src="../../core/style/default/images/del.gif" width="16" height="16" />删除 </a></span>
															</center>
														</div>
													</td>
												</tr>

											</cms:QueryData>
											<cms:Empty flag="MemAcc">
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

function openAddAccRuleDialog()
{
	$.dialog({ 
	    id : 'oaard',
    	title : '增加行为规则',
    	width: '590px', 
    	height: '530px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:BasePath/>core/member/CreateAccRule.jsp'
	});
}

function openEditAccRuleDialog(id)
{
	$.dialog({ 
	    id : 'oeard',
    	title : '编辑行为规则',
    	width: '590px', 
    	height: '530px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:BasePath/>core/member/EditAccRule.jsp?accId='+id
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


function deleteMemAcc(id)
{
	 var ids='';
	 
	 if('' == id)
	 {
	 	var cidCheck = document.getElementsByName('checkedIds');
	
		
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
	    				width: '180px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png',    				
	                    content: '没有选择任何浏览规则！', 
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
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选浏览规则吗？',
                    
                    ok: function () { 
                    
 					var url = "<cms:BasePath/>security/deleteAccRule.do?ids="+ids+"&<cms:Token mode='param'/>";
 					
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   			dataType: 'json',
			   			
			       		success: function(msg)
			            {     
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
