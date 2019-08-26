<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>

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
						<a href="#">静态发布页规则</a>
					</td>
					<td align="right"></td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<cms:CurrentSite>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										&nbsp;
										<select id="typeFilter" class="form-select" onchange="javascript:filterAction(this.value);">
											<option value="-9999">
												--- 所有发布类型 ---
											</option>
											<option value="1">
												栏目和专题首页
											</option>
											<option value="2">
												栏目列表页
											</option>
											<option value="3">
												专题列表页
											</option>
											<option value="4">
												内容页
											</option>
										</select>
										&nbsp; &nbsp;
									</div>
									<div>
										<a href="javascript:openCreatePublishRuleDialog();" class="btnwithico"> <img src="../../core/style/icons/property-import.png" alt="" /><b>添加规则&nbsp;</b> </a><%--

										<a href="javascript:deleteRules();" class="btnwithico"> <img src="../../core/style/default/images/del.gif" alt="" /><b>删除&nbsp;</b> </a>

									--%></div>
									<div class="fr">
									</div>

								</td>
							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<cms:SystemPublishRuleList type="${param.type}" currSite="${param.curr}">
											<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

												<tr class="datahead">
													<td width="2%">
														<strong>ID</strong>
													</td>
													  <td width="28%">
														<strong>规则名称</strong>
													</td>

													<td width="11%">
														<strong>类型</strong>
													</td>
													 
													<td width="12%">
														<center>
															<center><strong>操作</strong></center>
														</center>
													</td>
												</tr>

												<cms:SystemPublishRule>

													<tr>
														<td>
															${Rule.ruleId}
														</td>
														 
														<td>
															&nbsp;&nbsp;${Rule.ruleName}
														</td>
														<td>
															${Rule.typeStr}
														</td>
														 


														<td>
															<center>
																<a href="javascript:openEditPublishRuleDialog('${Rule.ruleId}','${Rule.siteId}')"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;
																<a href="javascript:deleteRule('${Rule.ruleId}','${Rule.siteId}');"><img src="../../core/style/default/images/del.gif" width="16" height="16" />删除</a>&nbsp;&nbsp;&nbsp;
															</center>
														</td>
													</tr>

												</cms:SystemPublishRule>
												<cms:Empty flag="Rule">
													<tr>
														<td class="tdbgyew" colspan="9">
															<center>
																当前没有数据!
															</center>
														</td>
													</tr>
												</cms:Empty>


											</table>
										</cms:SystemPublishRuleList>
									</div>
								<div class="mainbody-right"></div>
								</td>
							</tr>

						</table>

					</td>
				</tr>

				<tr>
					<td>

					</td>
				</tr>
			</table>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->

	</body>
</html>

<script type="text/javascript">

initRadio('curr','${param.curr}');

initSelect('typeFilter','${param.type}');

var currSiteId = '${CurrSite.siteId}';

function openCreatePublishRuleDialog()
{
  
	$.dialog({ 
	    id : 'ocprd',
    	title : '添加发布URL规则',
    	width: '780px', 
    	height: '300px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:BasePath/>core/deploy/CreatePublishRule.jsp'
	
	  
	});
}

function openEditPublishRuleDialog(id, siteId)
{
  	 
    
	$.dialog({ 
	    id : 'oeprd',
    	title : '编辑发布URL规则',
    	width: '780px', 
    	height: '300px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:BasePath/>core/deploy/EditPublishRule.jsp?ruleId='+id
	
	  
	});
}




function filterAction(val)
{
	window.location.href = 'ManagePublishUrlRule.jsp?curr=${param.curr}&type='+val;
}

function deleteRules()
{
	var cidCheck = document.getElementsByName('checkRule');
	
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
    				
                    content: '请选择要删除的规则！', 
       cancel: true 
                    
	
	  });
	  return;
	}

	deleteRule(ids);

}


function deleteRule(id, siteId)
{
	<cms:LoginUser>

	if(currSiteId != siteId)
    {
    	
    	if('001' != '${Auth.orgCode}')
    	{
    	
		    $.dialog({ 
		   					title :'提示',
		    				width: '180px', 
		    				height: '60px', 
		                    lock: true, 
		    				icon: '32X32/i.png', 
		    				
		                    content: '不可删除非本站规则！',
		                    
		    				cancel: true
	
			});
			return;
		}
    }
    
    </cms:LoginUser>

	$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选规则吗？',
                    
                    ok: function () 
                    { 

                    var url = "<cms:BasePath/>publish/deletePubRule.do?ids="+id+"&<cms:Token mode='param'/>";
                    

 		
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

function change(obj)
{	
	if(obj.checked == true)
	{
		replaceUrlParam(window.location, 'curr=true');
	}
	else
	{
		replaceUrlParam(window.location, 'curr=false');
	}

}

</script>



</cms:CurrentSite>
