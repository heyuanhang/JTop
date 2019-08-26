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
						<a href="#">会员相关</a> &raquo;
						<a href="#">会员等级管理</a>
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
						<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										<a href="javascript:openCreateRankDialog();" class="btnwithico" onclick=""><img src="../style/icons/crown.png" width="16" height="16" /><b>添加会员等级&nbsp;</b> </a>

										<a href="javascript:deleteMemberRank('');" class="btnwithico" onclick=""><img src="../style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>

									</div>
									<div class="fr">

									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="99.8%" cellpadding="0" cellspacing="0">

											<tr class="datahead">

												<td width="2%">
													<strong>ID</strong>
												</td>

												<td width="1%">
													<input class="inputCheckbox"  onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>

												<td width="14%">
													<strong>等级名称</strong>
												</td>

												<td width="10%">
													<strong>级别</strong>
												</td>

												<td width="12%">
													<strong>积分区间</strong>
												</td>



												<td width="9%">
													<center>
														<strong>操作</strong>
													</center>
												</td>
											</tr>



											<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberRankInfoForTag" objName="Rank"  >
												<tr>
													<td>
														${Rank.rankId}
													</td>
													<td>
														<input class="inputCheckbox" id="checkedId" name="checkedId" value="${Rank.rankId}" type="checkbox" onclick="javascript:" />
													</td>

													<td>
														&nbsp;${Rank.rankName}
													</td>

													<td>
														&nbsp;${Rank.rankLevel}
													</td>

													<td>
														&nbsp;${Rank.minScore} ~ ${Rank.maxScore}
													</td>



													<td>
														<div>
															<center>
																<span ><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditRankDialog('${Rank.rankId}');">&nbsp;编辑</a>&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteMemberRank('${Rank.rankId}');">删除 </a>&nbsp;&nbsp;&nbsp; </span>
															</center>
														</div>
													</td>
												</tr>
											</cms:QueryData>



											<cms:Empty flag="Rank">
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

								</td>
							</tr>
							<div class="mainbody-right"></div>

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

function openCreateRankDialog()
{
	$.dialog({ 
    	title :'新增会员等级',
    	width: '600px', 
    	height: '325px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/member/CreateMemberRank.jsp'
	});
}

function openEditRankDialog(id)
{
	$.dialog({ 
    	title :'编辑会员等级',
    	width: '600px', 
    	height: '325px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/member/EditMemberRank.jsp?id='+id
	});
}

function deleteMemberRank(id)
{
	var ids='';
	 
	 if('' == id)
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
	                    content: '没有选择任何会员等级！', 
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
    				
                    content: '您确认删除会员等级吗？',
                    
                    ok: function () { 
                    
 					var url = "<cms:BasePath/>member/deleteRank.do?ids="+ids+"&<cms:Token mode='param'/>";
 					
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
