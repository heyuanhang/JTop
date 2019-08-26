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
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script>
		 var selectedTargetClassId = '';
		 
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            //W.$.dialog.tips('添加模型步骤成功...',1); 
            api.close(); 
         	//api.reload( api.get('cwa') );        
<%--       		W.window.location.reload();--%>
         }
         
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


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->



					<div style="padding: 3px 2px;">
					
					</div>
					<table id="showlist" class="listdate" width="98%" cellpadding="0" cellspacing="0">

						<tr class="datahead">
							<td width="2%">

							</td>

							<td>
								<strong><center>
										媒体名称
									</center> </strong>
							</td>


						</tr>
						<cms:QueryData objName="Icon" service="cn.com.mjsoft.cms.resources.service.ResourcesService" method="getIconResForTag" var=",${param.pn},60">

							<tr>
								<td>
									<input type="radio" name="checkedId" value="${Icon.iconName}"></input>
								</td>

								<td>
									<center>
										<img width="16" height="16" src="<cms:BasePath/>core/style/icons/${Icon.iconName}"/>
									</center>
								</td>


							</tr>
						</cms:QueryData>
						<cms:Empty flag="Icon">
							<tr>
								<td class="tdbgyew" colspan="9">
									<center>
										当前没有数据!
									</center>
								</td>
						</cms:Empty>
						<cms:PageInfo>
							<tr id="pageBarTr">
								<td colspan="8" class="PageBar" align="left">
									<div class="fr">
										<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage}页 / ${Page.pageCount}页 &nbsp;&nbsp; </span>
										<span class="page">[<a href="javascript:query('h');">首页</a>]</span>
										<span class="page">[<a href="javascript:query('p');">上一页</a>]</span>
										<span class="page">[<a href="javascript:query('n');">下一页</a>]</span>
										<span class="page">[<a href="javascript:query('l');">末页</a>]</span>&nbsp;
									</div>
									<script>
																function query(flag)
																{
																	var cp = 0;
																	
																	if('p' == flag)
																	{
			                                                             cp = parseInt('${Page.currentPage-1}');
																	}
		
																	if('n' == flag)
																	{
			                                                             cp = parseInt('${Page.currentPage+1}');
																	}
		
																	if('h' == flag)
																	{
			                                                             cp = 1;
																	}
		
																	if('l' == flag)
																	{
			                                                             cp = parseInt('${Page.pageCount}');
																	}
		
																	if(cp < 1)
																	{
			                                                           cp=1;
																	}
																	
																	if(cp > parseInt('${Page.pageCount}'))
																	{
			                                                           cp=parseInt('${Page.pageCount}');
																	}
																
																	
																	replaceUrlParam(window.location,'pn='+cp);		
																}
													
													
																function jump()
																{
																    var cp = parseInt(document.getElementById('pageJumpPos').value);
																    
																    if(cp > parseInt('${Page.pageCount}'))
																	{
			                                                           cp=parseInt('${Page.pageCount}');
																	}
																
																	replaceUrlParam(window.location,'pn='+cp);
																}
															</script>
									<div class="fl"></div>
								</td>
							</tr>
						</cms:PageInfo>




					</table>
					<div style="height:40px"></div>
					<div class="breadnavTab" >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:submitIco();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
								</div>

							</tr>

						</table>
					</div>

				</td>
			</tr>


		</table>
		<form method="post" id="commendForm" name="commendForm">
			<input type="hidden" id="contentId" name="contentId" value="${param.contentId}" />

		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  
 

 
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}

function submitIco()
{ 

	var target = '${param.target}';
	
	 
    var ico = getRadioCheckedValue('checkedId');
  
    if(ico != null)
    {    
    	 api.get(target).document.getElementById('icoImg').src='<cms:BasePath/>core/style/icons/'+ico;
    	 
    	 if(api.get(target).document.getElementById('ico') != null)
    	 {
    	 	api.get(target).document.getElementById('ico').value=ico;
    	 }
    	 else
    	 {
    	 	api.get(target).document.getElementById('icon').value=ico;
    	 }
    	 
    }
    

    api.close();
    
    
}
   

 function change()
{
	replaceUrlParam(window.location,'fc='+$('#fc').val());
}

  
</script>
