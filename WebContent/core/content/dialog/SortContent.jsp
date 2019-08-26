<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../../common/js/jquery-1.7.gzjs"></script>

		<script>  
		
		var hasError = false;
		//验证
		
	     var api = frameElement.api, W = api.opener; 
		
		 function showErrorMsg(msg)
		 {
		
		    W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: msg,

		    				cancel: true
			});
			
		}
      
	
		 
         if("true"==="${param.fromFlow}")
         {  

         	//api.close(); 
	            
	        W.location.reload();
       		       
         }
         
         
         
        	
      </script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="left" valign="top">
								<!--main start-->
								<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

									<tr>
										<td id="uid_td25" style="padding: 12px 6px;">
											<div class="DataGrid">
												<cms:SystemManageContentList classId="${param.classId}" order="${param.orderBy}" censorBy="1" page="true" pageSize="11" startDate="${param.appearStartDate}" endDate="${param.appearEndDate}">
													<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

														<tr class="datahead">
															<td width="4%" height="30">
																<strong>ID</strong>
															</td>
															<td width="1%" height="30">



															</td>
															<td width="28%">
																<strong>标题</strong>
															</td>

															<td width="7%">
																<strong>内容模型</strong>
															</td>

														</tr>

														<cms:SystemContent>

															<tr>
																<td>
																	${Info.contentId}
																</td>
																<td>
																	<cms:if test="${param.targetId == Info.contentId}">
																		<input disabled type="radio" name="nextIdCheck" id="nextIdCheck" value="${Info.contentId}" onclick="javascript:regId(this)" />

																	</cms:if>
																	<cms:else>
																		<input type="radio" name="nextIdCheck" id="nextIdCheck" value="${Info.contentId}" onclick="javascript:regId(this)" />

																	</cms:else>
																</td>
																<td>
																	<div align="left">
																		<cms:if test="${param.targetId == Info.contentId}">
																			<strong><span>&nbsp;${Info.title }</span><strong>
																		</cms:if>
																		<cms:else>
																			<span>&nbsp;${Info.title }</span>
																		</cms:else>


																	</div>
																</td>

																<td>
																	<cms:SystemDataModel id="${Info.modelId}">																					
																								${DataModel.modelName}	
																		</cms:SystemDataModel>
																</td>
															</tr>
														</cms:SystemContent>

														<tr>
															<td colspan="8" class="PageBar" align="left">
																<div class="fr">
																	<span class="text_m"> 共 ${page.totalCount} 条记录 第${page.currentPage}页 / ${page.pageCount}页 <input type="text" size="5" id="pageJumpPos" name="pageJumpPos"></input> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()"></input> </span>
																	<span class="page">[<a href="${page.headQuery}&typeId=${param.typeId}&single=${param.single}&dialogId=${param.dialogId}&targetId=${param.targetId}">首页</a>]</span>
																	<span class="page">[<a href="${page.prevQuery}&typeId=${param.typeId}&single=${param.single}&dialogId=${param.dialogId}&targetId=${param.targetId}">上一页</a>]</span>
																	<span class="page">[<a href="${page.nextQuery}&typeId=${param.typeId}&single=${param.single}&dialogId=${param.dialogId}&targetId=${param.targetId}">下一页</a>]</span>
																	<span class="page">[<a href="${page.endQuery}&typeId=${param.typeId}&single=${param.single}&dialogId=${param.dialogId}&targetId=${param.targetId}">末页</a>]</span>&nbsp;
																</div>
																<script>
																					function jump()
																					{
																						window.location="${page.jumpQuery}&currentPage="+document.getElementById('pageJumpPos').value+'&typeId=${param.typeId}';
																					}			
																	</script>
																<div class="fl"></div>
															</td>
														</tr>

													</table>
												</cms:SystemManageContentList>
											</div>

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


					<div style="height:5px;"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:sortContent();"  class="btnwithico"><img src="../../../core/style/icon/sort-number.png" width="16" height="16"><b>插入到所选前&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico"><img src="../../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
								</div>
							</tr>
						</table>
					</div>



				</td>
			</tr>
		</table>

		<!-- hidden -->

		<form method="post" id="sortContentForm" name="sortContentForm">
			<input type="hidden" id="targetId" name="targetId" value="${param.targetId}"></input>
			<input type="hidden" id="nextId" name="nextId" value="-1"></input>
			<input type="hidden" id="classId" name="classId" value="${param.classId}"></input>
		</form>

		 

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">



document.getElementById('targetId').value=W.targetSortId;

function regId(next)
{
   if(next.checked == true)
   {
      document.getElementById("nextId").value = next.value;
   }


}

function sortContent(targetId)
{
    var nextId =  document.getElementById("nextId").value;
    if(nextId == -1)
    {
        
        W.$.dialog({ 
        			id:'asdasd',
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    parent:api, 
                    api:true,
    				icon: '32X32/i.png', 
    				
                    content: '请选择目标内容！',
                    
                   
       	cancel: true 
                    
	
		});
        return;
    }
    
    if(nextId == document.getElementById("targetId").value)
    {
        
        W.$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    parent:api,
                    api:true,
    				icon: '32X32/i.png', 
    				
                    content: '不可和自身排序！',
                    
                   
       	cancel: true 
                    
	
		});
        return;
    
    }
    
    disableAnchorElementByName("btnwithicosysflag",true);
		
	W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
    
    
   document.all.sortContentForm.action="<cms:BasePath/>content/sortContent.do"+"?<cms:Token mode='param'/>";
   document.all.sortContentForm.submit();
   
 

}

  
function close()
{
	api.close();
}



 


</script>

