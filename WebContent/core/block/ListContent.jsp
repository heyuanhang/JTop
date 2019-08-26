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
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>	

		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

		<script>
	
      </script>
	</head>
	<body>

		
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td  align="left" valign="top">
					<!--main start-->
					<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

						<tr>
							<td style="padding: 7px 10px;" class="">
								<div class="fl">
											</div>
								<div class="fr">
								</div>
							</td>
						</tr>
						<tr>
							<td style="padding: 2px 10px;">
								<div class="fl">
									
									<select id="orderBy" name="orderBy" onchange="javascript:filterAction(this.value);">
										<option value="default-down" selected>
											所有新闻&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</option>
										<option value="default-up">
											&nbsp;&nbsp;国际新闻
										</option>
										
										<option value="addDate-down">
											&nbsp;&nbsp;国内新闻
										</option>
										<option value="addDate-down">
											&nbsp;&nbsp;&nbsp;&nbsp;安徽
										</option>
										<option value="addDate-down">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;合肥
										</option>
										<option value="addDate-down">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;安庆
										</option>
										
										<option value="addDate-down">
											&nbsp;&nbsp;&nbsp;&nbsp;河南
										</option>
										<option value="addDate-down">
											&nbsp;&nbsp;&nbsp;&nbsp;山东
										</option>
										<option value="addDate-up">
											&nbsp;&nbsp;军事新闻
										</option>
										<option value="hot-down">
											&nbsp;&nbsp;社会新闻
										</option>
										

									</select>
									
								</div>

								<div class="fr">
									高级搜索:
									<select id="site" name="site">
										<option value="-1">
											关键词
										</option>
										<option value="1">
											ID
										</option>
									</select>
									&nbsp;
									<input id="query" name="query" size="20" maxlength="60" />
									<input onclick="" value="搜索" class="btn-1" type="button" />
								</div>

							</td>
						</tr>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<cms:SystemManageContentList type="${param.typeBy}" order="${param.orderBy}" censorBy="${param.censorBy}" page="true" pageSize="5" classId="${param.classId}" modelId="${param.modelId}" startDate="${param.appearStartDate}" endDate="${param.appearEndDate}">
										<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="4%" height="30">
													<strong>ID</strong>
												</td>
												<td width="3%" height="30">
													<input type="checkbox" name="checkbox" value="checkbox" />
												</td>
												<td width="30%">
													<strong>标题</strong>
												</td>
												<td width="3%">
													<strong>状态</strong>
												</td>
												<td width="6%">
													<strong>创建者</strong>
												</td>
												
											</tr>

											<cms:Content>

												<tr height="20">
													<td>
														${Info.contentId}
													</td>
													<td>
														<input type="checkbox" name="checkContent" value="${Info.contentId}" onclick="javascript:regId(this);" />
													</td>
													<td>
														<div align="left">
															<cms:if test="${Info.topFlag==1}">
																<img src="../../core/style/default/images/top_flag.png" alt="固顶内容" />
															</cms:if>
															<cms:elseif test="${Info.hotFlag==1}">
																<img src="../../core/style/default/images/hot.png" alt="热点内容" />
															</cms:elseif>
															<cms:elseif test="${Info.commendFlag==1}">
																<img src="../../core/style/default/images/commend.png" alt="推荐内容" />
															</cms:elseif>
															<cms:else>
																<img src="../../core/style/default/images/some.png" alt="普通内容" />
															</cms:else>
															<span class="STYLE1"> ${Info.title }</span>
															<font color="red"> <cms:if test="${Info.photoArticleType==1}">
																	<span class="STYLE1">&nbsp;[图文]</span>
																</cms:if> <cms:if test="${Info.videoType==1}">
																	<span class="STYLE1">[视频]</span>
																</cms:if> <cms:if test="${Info.attachType==1}">
																	<span class="STYLE1">[附件]</span>
																</cms:if> </font>
														</div>
													</td>
													<td>
														<cms:if test="${Info.censorState==0}">送审</cms:if>
														<cms:elseif test="${Info.censorState==1}">已发</cms:elseif>
														<cms:elseif test="${Info.censorState==-2}">重编</cms:elseif>
														<cms:elseif test="${Info.censorState==2}">待发</cms:elseif>
														<cms:elseif test="${Info.censorState==3}">下线</cms:elseif>

													</td>
													<td>
														${Info.creator}
													</td>
													

												</tr>

											</cms:Content>

											<tr>
												<td colspan="8" class="PageBar" align="left">
													<div class="fr">
														<page:page />
													</div>
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
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">


initSelect('orderBy','${param.orderBy}');

var currentClassId = "${param.classId}";
var currentModelId = "${param.modelId}"


var targetSortId = -1;

var checkedIdMap = new HashMapJs();

function regId(check)
{
   if(check.checked==true)
   {
      targetSortId=check.value;
      checkedIdMap.put(check.value,check.value);
  
   }
   else
   {
      targetSortId = -1;
      checkedIdMap.remove(check.value);
   }
} 

function gotoAddUserDefineContentPage(modelId)
{
	window.location.href="UserDefineModelGeneralAdd.jsp?classId=${param.classId}&modelId="+modelId;




}


function testDialog()
{

$.dialog({ 
   title :'添加工作流',
    width: '900px', 
    height: '500px', 
     lock: true, 
    
    content: 'url:<cms:Domain/>core/workflow/CreateWorkFlow.jsp'
	
	
});
}

function sortContent()
{
   if(targetSortId == -1)
   {
     // alert("请选择待排序内容！");
     
        var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择待排序内容！',
                    
                   
    cancel: true 
                    
	
	//../../content/deleteUserDefineContent.do?contentId=${Info.contentId}&classId=${Info.classId}&&modelId=${Info.modelId}；
	});
      return;
   }
   

   
  
    //document.getElementById('targetId').value=targetSortId;
    //alert( document.getElementById('targetId').value);
   var returnValue = window.showModalDialog("SortContent.jsp"+window.location.search+"&targetId="+targetSortId,targetSortId,"dialogWidth=950px;dialogHeight=500px;status=yes");
   if("ok"==returnValue)
   {
      window.location=window.location.protocol+"//"+window.location.host+window.location.pathname+'?classId='+currentClassId+'&modelId='+currentModelId;
   }
}

//以下为排序筛选操作

function filterAction(filterValue)
{
    var appearStartDate = document.getElementById('appearStartDate').value;
    var appearEndDate = document.getElementById('appearEndDate').value;
    //alert(appearStartDate);
    
    
   
    var censorByVar = document.getElementById('censorBy').value;
    var typeByVar = document.getElementById('typeBy').value;
    var orderByVar = document.getElementById('orderBy').value;
	
    window.location='ManageGeneralContent.jsp?classId='+currentClassId+'&modelId='+currentModelId+'&appearStartDate='+appearStartDate+'&appearEndDate='+appearEndDate+'&censorBy='+censorByVar+'&typeBy='+typeByVar+'&orderBy='+orderByVar;
	

}





//dialog操作
function deleteContent(modelId,classId,contentId)
{
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除此内容吗？',
                    
                    ok: function () { 
       
     window.location='../../content/deleteUserDefineContent.do?contentId='+contentId+'&classId='+classId+'&&modelId='+modelId;
       
    }, 
    cancel: true 
                    
	
	//../../content/deleteUserDefineContent.do?contentId=${Info.contentId}&classId=${Info.classId}&&modelId=${Info.modelId}；
	});



} 

 




</script>

