<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
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



					<div style="padding: 9px 2px;">
						首字母:&nbsp;
						<select class="form-select" id="fc" name="fc" onchange="javascript:change();">
							<option value="">
								---- 所有来源 ----
							</option>
							<option value="a">
								A
							</option>
							<option value="b">
								B
							</option>
							<option value="c">
								C
							</option>
							<option value="d">
								D
							</option>
							<option value="e">
								E
							</option>
							<option value="f">
								F
							</option>
							<option value="g">
								G
							</option>
							<option value="h">
								H
							</option>
							<option value="i">
								I
							</option>
							<option value="j">
								J
							</option>
							<option value="k">
								K
							</option>
							<option value="l">
								L
							</option>
							<option value="m">
								M
							</option>
							<option value="n">
								N
							</option>
							<option value="o">
								O
							</option>
							<option value="p">
								P
							</option>
							<option value="q">
								Q
							</option>
							<option value="r">
								R
							</option>
							<option value="s">
								S
							</option>
							<option value="t">
								T
							</option>
							<option value="u">
								U
							</option>
							<option value="v">
								V
							</option>
							<option value="w">
								W
							</option>
							<option value="x">
								X
							</option>
							<option value="y">
								Y
							</option>
							<option value="z">
								Z
							</option>

						</select>
						&nbsp;(来源媒体为版权所有者，初发单位)
					</div>
					<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

						<tr class="datahead">
							<td width="2%">

							</td>

							<td>
								<strong><center>
										媒体名称
									</center> </strong>
							</td>


						</tr>
						<cms:QueryData objName="As" service="cn.com.mjsoft.cms.content.service.ContentService" method="getContentSourceTag" var=",${param.fc},${param.pn},10">

							<tr>
								<td>
									<input type="radio" name="checkedId" value="${As.sourceName}"></input>
								</td>

								<td>
									<center>
										${As.sourceName}
									</center>
								</td>


							</tr>
						</cms:QueryData>
						<cms:Empty flag="As">
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
					<div style="height:2px"></div>
					<div class="breadnavTab" >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:submitSource();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
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
 
initSelect('fc','${param.fc}'); 
 
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}

function submitSource()
{
    var sn = getRadioCheckedValue('checkedId');
    
    if(sn != null)
    {
    	api.get('main_content').document.getElementById('author').value = sn;
    	W.$.dialog.tips('设定来源媒体成功',1,'32X32/succ.png'); 
    }
    

    api.close();
    
    
}
   

 function change()
{
	replaceUrlParam(window.location,'fc='+$('#fc').val());
}

  
</script>
