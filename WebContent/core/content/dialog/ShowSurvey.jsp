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
					<div class="addtit">
						<img src="../../style/icons/document-task.png" width="16" height="16" />
						站点调查问题
					</div>

					<div class="fl" style="padding: 8px 6px;">
						所属栏目：
						<select class="form-select" id="classId" name="classId" onchange="javascript:changeSurveyGroupClass(this.value)">
							<option value="" selected>
								------ 所有调查问卷 ------
							</option>
							<option value="-9999">
								全站调查
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

					<div style="padding: 6px 6px;">
					</div>



					<div style="height:4px"></div>
					<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

						<tr class="datahead">
							<td width="2%">
								<strong> <input type="checkbox" onclick="javascript:selectAll('checkIds',this);"></input> </strong>
							</td>

							<td width="25%">
								<strong>调查标题</strong>
							</td>

							<td width="4%">
								<strong>限制规则</strong>
							</td>

							<td width="6%">
								<strong>开始日期</strong>
							</td>

							<td width="6%">
								<strong>结束日期</strong>
							</td>


						</tr>

						<cms:SystemSurverGroup classId="${param.classId}" size="12">


							<tr>
								<td>
									<input type="checkbox" name="checkIds" value="${Group.groupId}" />
								</td>

								<td>
									${Group.questName}
								</td>

								<td>
									<cms:if test="${Group.restriction == 1}">
													按用户
												</cms:if>
									<cms:else>
													IP限制
												</cms:else>

								</td>

								<td>
									${Group.startDate}
								</td>

								<td>
									<cms:if test="${Group.endDate=='9999-12-31'}">永久有效</cms:if>
									<cms:else>${Group.endDate}</cms:else>
								</td>

							</tr>


						</cms:SystemSurverGroup>
						<cms:Empty flag="Group">
							<tr>
								<td class="tdbgyew" colspan="9">
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
									<a href="javascript:submitRelateSurvey();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
								</div>

							</tr>

						</table>
					</div>

				</td>
			</tr>


		</table>

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  

initSelect('classId','${param.classId}');


var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}

function submitRelateSurvey()
{
	 var ids = '';
		var checks = document.getElementsByName('checkIds');
	
		for(var i = 0; i < checks.length; i++)
		{
			if(checks[i].checked == true)
			{
				ids += checks[i].value+'*';
			}
		}
		
		if(ids == '')
		{
			W.$.dialog({ 
			   	title :'提示',
			    width: '120px', 
			    height: '60px', 
			    parent: api,
			    lock: true, 
			    icon: '32X32/i.png', 
			    				
			    content: '没有选择内容！', 
			    cancel: true 
			});
			return;
		}
		
		//alert(api.get('osrcgd').window.location);
		//api.reload( api.get('osrcgd') );
       api.get('ossd').window.location = 'ShowRelateSurvey.jsp?rsids='+ids+api.get('ossd').currentRids;
  	 
	   close();
}
   

function deleteRelateContent()
{
	var cidCheck = document.getElementsByName('checkIds');
	
		var ids='';
		for(var i=0; i<cidCheck.length;i++)
		{
			if(cidCheck[i].checked)
			{
				ids += cidCheck[i].value+'*';
			}
		}
		
		if('' == ids)
		{
		  W.$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	    				parent:api,
	                    lock: true, 
	    				icon: '32X32/i.png',    				
	                    content: '没有选择任何内容！', 
	       				cancel: true 
		  });
		  
		  return;
		}


      
        
        var ridArray = ids.split('*');
	    var idStr = null;

        for ( var i = 0; i < ridArray.length; i++ )
        {
            idStr = ridArray[i];
				
			if(idStr != '')
			{	
              currentRids = currentRids.replace( idStr + "*", "");
            }
        }
        
  
            
        replaceUrlParam(window.location,'rids='+currentRids);   

}
   
function changeSurveyGroupClass(classId)
{
	
	 
	window.location.href = 'ShowSurvey.jsp?classId='+classId;	
}
  
</script>
