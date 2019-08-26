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
       		W.window.location.reload();
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
					 
					<div class="fl" style="padding: 10px 2px;">
						Tag分类:&nbsp;
						<select class="form-select" id="tagTypeId" name="tagTypeId" onchange="javascript:change();">
							<option value="">
								----- 所有类型Tag -----
							</option>
							<cms:QueryData objName="TagType" service="cn.com.mjsoft.cms.channel.service.ChannelService" method="getTagTypeListQueryTag">
								<option value="${TagType.tagTypeId}">
									${TagType.tagTypeName}
								</option>
							</cms:QueryData>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</div>

					<div style="padding: 10px 2px;">
						首字母:&nbsp;
						<select class="form-select" id="fc" name="fc" onchange="javascript:change();">
							<option value="">
								----- 无筛选 -----
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
						&nbsp;
					</div>

					<%--<a  href="javascript:queryForFc('');">全部</a>&nbsp;
						<a style="text-decoration: underline" id="aa" href="javascript:queryForFc('a');">A</a>&nbsp;
						<a style="text-decoration: underline" id="ab" href="javascript:queryForFc('b');">B</a>&nbsp;
						<a style="text-decoration: underline" id="ac" href="javascript:queryForFc('c');">C</a>&nbsp;
						<a style="text-decoration: underline" id="ad" href="javascript:queryForFc('d');">D</a>&nbsp;
						<a style="text-decoration: underline" id="ae" href="javascript:queryForFc('e');">E</a>&nbsp;
						<a style="text-decoration: underline" id="af" href="javascript:queryForFc('f');">F</a>&nbsp;
						<a style="text-decoration: underline" id="ag" href="javascript:queryForFc('g');">G</a>&nbsp;
						<a style="text-decoration: underline" id="ah" href="javascript:queryForFc('h');">H</a>&nbsp;
						<a style="text-decoration: underline" id="ai" href="javascript:queryForFc('i');">I</a>&nbsp;
						<a style="text-decoration: underline" id="aj" href="javascript:queryForFc('j');">J</a>&nbsp;
						<a style="text-decoration: underline" id="ak" href="javascript:queryForFc('k');">K</a>&nbsp;
						<a style="text-decoration: underline" id="al" href="javascript:queryForFc('l');">L</a>&nbsp;
						<a style="text-decoration: underline" id="am" href="javascript:queryForFc('m');">M</a>&nbsp;
						<a style="text-decoration: underline" id="an" href="javascript:queryForFc('n');">N</a>&nbsp;
						<a style="text-decoration: underline" id="ao" href="javascript:queryForFc('o');">O</a>&nbsp;
						<a style="text-decoration: underline" id="ap" href="javascript:queryForFc('p');">P</a>&nbsp;
						<a style="text-decoration: underline" id="aq" href="javascript:queryForFc('q');">Q</a>&nbsp;
						<a style="text-decoration: underline" id="ar" href="javascript:queryForFc('r');">R</a>&nbsp;
						<a style="text-decoration: underline" id="as" href="javascript:queryForFc('s');">S</a>&nbsp;
						<a style="text-decoration: underline" id="at" href="javascript:queryForFc('t');">T</a>&nbsp;
						<a style="text-decoration: underline" id="au" href="javascript:queryForFc('u');">U</a>&nbsp;
						<a style="text-decoration: underline" id="av" href="javascript:queryForFc('v');">V</a>&nbsp;
						<a style="text-decoration: underline" id="aw" href="javascript:queryForFc('w');">W</a>&nbsp;
						<a style="text-decoration: underline" id="ax" href="javascript:queryForFc('x');">X</a>&nbsp;
						<a style="text-decoration: underline" id="ay" href="javascript:queryForFc('y');">Y</a>&nbsp;
						<a style="text-decoration: underline" id="az" href="javascript:queryForFc('z');">Z</a>&nbsp;
						--%>
					<%--
						标签关键字:
						<input type="text" size="20" class="form-input" />
						<input type="button" value="搜索"
							onclick="javascript:getKeywordFromContent();" class="btn-1" />
					--%>
					</div>

					<div>
						<%--<a
							href="javascript:javascript:openCreateFlowStepDialog('${param.actId}','<cms:UrlParam target='${param.flowName}' />');"
							class="btnwithico" onclick=""><img
								src="../../style/icons/tag--plus.png" width="16"
								height="16" /><b>新建Tag&nbsp;</b> </a>
					--%>
					</div>




					<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

						<tr class="datahead">
							<td width="2%">
								<strong> <input type="checkbox" onclick="javascript:selectAll('checkedtag',this);"></input> </strong>
							</td>

							<td width="14%">
								<strong>名称</strong>
							</td>

							<td width="10%">
								<strong>关联内容数</strong>
							</td>

							<td width="10%">
								<strong>Tag点击数</strong>
							</td>
						</tr>

						<cms:QueryData objName="Tag" service="cn.com.mjsoft.cms.channel.service.ChannelService" method="getTagWordListQueryTag" var="${param.tagTypeId},${param.fc},${param.pn},10">

							<tr>
								<td>
									<input class="inputCheckbox" name="checkedTag" value="${Tag.tagId}(*)[${Tag.tagName}]" type="checkbox"   />
								</td>

								<td>
									${Tag.tagName}
								</td>

								<td>
									${Tag.contentCount}
								</td>

								<td>
									${Tag.clickCount}
								</td>
							</tr>
						</cms:QueryData>
						<cms:Empty flag="Tag">
							<tr>
								<td class="tdbgyew" colspan="7">
									<center>
										当前没有数据!
									</center>
								</td>
							</tr>
						</cms:Empty>


						<cms:PageInfo>
							<tr id="pageBarTr">
								<td colspan="8" class="PageBar" align="left">
									<div class="fr">
										<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
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
																
																	
																	replaceUrlParam(window.location,'pn='+cp);		
																}
													
													
																function jump()
																{
																	replaceUrlParam(window.location,'pn='+document.getElementById('pageJumpPos').value);
																}
															</script>
									<div class="fl"></div>
								</td>
							</tr>
						</cms:PageInfo>


					</table>
					<div style="height:2px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:clearTag();"  class="btnwithico"><img src="../../style/icons/tag--minus.png" width="16" height="16" /><b>清空当前内容Tag&nbsp;</b> </a>

									<a href="javascript:chooseTag();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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

$("#a${param.fc}").css({ color: "red", fontSize: "15px", textDecoration: "underline" });

initSelect('tagTypeId','${param.tagTypeId}');

initSelect('fc','${param.fc}');

var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}

function chooseTag()
{
    	var cidCheck = document.getElementsByName('checkedTag');
	
		var ids=api.get('main_content').document.getElementById('tagKey').value;
		var vls=api.get('main_content').document.getElementById('tagKeyVal').value;
		var tmp='';
		
		var haveId= false;
		for(var i=0; i<cidCheck.length;i++)
		{
			if(cidCheck[i].checked)
			{
				haveId=true;
				tmp = cidCheck[i].value.split('(*)');
				
				if(ids.indexOf(tmp[0]) == -1 )
				{
				
					ids += tmp[0]+'*';
					vls += tmp[1]+'  ';
				}
			}
		}
		
		if(!haveId)
		{
		  W.$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	    				parent:api,
	                    lock: true, 
	    				icon: '32X32/i.png',    				
	                    content: '没有选择任何Tag！', 
	       				cancel: true 
		  });
		 
		  return;
		}
		
		
		api.get('main_content').document.getElementById('tagKeyVal').value = vls;
		api.get('main_content').document.getElementById('tagKey').value = ids;	
    
    	W.$.dialog.tips('操作已生效,请继续...',1); 
    	window.location.reload();
        //api.close();
}

function clearTag()
{
	api.get('main_content').document.getElementById('tagKeyVal').value = '';
	api.get('main_content').document.getElementById('tagKey').value = '';	
	
	W.$.dialog.tips('操作已生效,请继续...',1); 
	window.location.reload();
	
}

function change()
{
	replaceUrlParam(window.location,'fc='+$('#fc').val()+'&tagTypeId='+$('#tagTypeId').val());
}



 
  
</script>
