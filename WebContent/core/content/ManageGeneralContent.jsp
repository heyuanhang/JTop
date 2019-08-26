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
		<script type="text/javascript" src="../common/js/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>

		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>


		<style>
		/**增加title类型a，改变文字颜色**/
		.title-a {
			text-decoration:none;
			color:#454545;
		}
		
		.title-a:hover {
			text-decoration:none; color:#999;
		}
		
		</style>
		<script>
			basePath = '<cms:BasePath/>';
			
			var tip;
			
			var orderFlag = '${param.orderBy}';
		
			var orderBy = '';
			var orderWay = '';
			if(orderFlag != '')
			{
				var temp = orderFlag.split('-');
				orderBy = temp[0];
				orderWay = temp[1];
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
			
			//主显标题字段的信息
			var tcId = '-1';
			
			var mustFill = '0';
			
      	</script>
	</head>
	<body>
		<cms:SysClass id="${param.classId}">
			<cms:SystemDataModel id='${Class.contentType}'>

				<div class="breadnav">
					<table width="99.8%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="left">
								&nbsp;
								<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
								当前位置：
								<a href="#">文档管理</a> &raquo;
								<a href="#">栏目内容管理</a> &raquo;
								<a href="#">${Class.className}</a>
							</td>
							<td align="right">

							</td>
						</tr>
					</table>
				</div>
				<div style="height:25px;"></div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
					<tr>
						<td class="mainbody" align="left" valign="top">
							<!--main start-->
							<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td style="padding: 11px" >
										<div class="fl">
											<a href="javascript:gotoAddUserDefineContentPageDialog();" class="btnwithico"> <img src="../../core/style/default/images/doc_add.png" title="" /><b>添加${DataModel.modelName}
											
											<cms:if test="${Class.classType==3}">
												(单页)
											
											</cms:if>
											 
											
											&nbsp;</b> </a>
											
											<%--<a href="javascript:gotoAddUserDefineContentPageTest();" class="btnwithico"> <img src="../../core/style/default/images/doc_add.png" title="" /><b>测试&nbsp;</b> </a>
										
											<a href="javascript:publishContent('${Class.contentProduceType}');" class="btnwithico"> <img src="../../core/style/default/images/doc_pub.png" title="" /><b>发布&nbsp;</b> </a>
												
											--%>
											<%--<cms:SystemUiRole flag="copy_content_cmd" accId="${param.classId}">
												<a href="javascript:copySomeContent();" class="btnwithico"> <img src="../../core/style/default/images/doc_copy.png" title="" /><b>复制&nbsp;</b> </a>
											</cms:SystemUiRole>
											--%>
											<a href="javascript:deleteSelectContent();" class="btnwithico"> <img src="../../core/style/default/images/doc_delete.png" title="" /><b>删除&nbsp;</b> </a>
									
											<a href="javascript:copySomeContent();" class="btnwithico"> <img src="../../core/style/default/images/doc_copy.png" title="" /><b>复制&nbsp;</b> </a>
											<a href="javascript:linkSomeContent();" class="btnwithico"> <img src="../../core/style/icons/document-sub.png" title="" /><b>引用&nbsp;</b> </a>
											
											<a href="javascript:moveSomeContent('${param.classId}');" class="btnwithico" onclick=""> <img src="../../core/style/default/images/doc_move.png" title="" /><b>移动&nbsp;</b> </a>
											
											 
													<a href="javascript:pickSingleAitlcleContent();" class="btnwithico"> <img src="../../core/style/icons/mouse--pencil.png" title="" /><b>转载&nbsp;</b> </a>																		
												 
											
											<a href="javascript:showCommendMutpiContentDialog('${param.classId}','${Class.contentType}');" class="btnwithico"> <img src="../../core/style/default/images/flag-blue.png" title="" /><b>推荐&nbsp;</b> </a>
											<a href="javascript:shareContent();" class="btnwithico"> <img src="../../core/style/blue/icon/sitemap-application.png" title="" /><b>共享&nbsp;</b> </a>
											
											<a id="topA" style="display:none" href="javascript:setTopFlag();" class="btnwithico"> <img src="../../core/style/default/images/doc_bro.png" title="" /><b><span id="topActName">固顶</span>&nbsp;</b> </a>
											<a href="javascript:addTagForSomeContent();" class="btnwithico"> <img src="../../core/style/icons/tags-label.png" title="" /><b><span id="topActName">标签</span>&nbsp;</b> </a>
											<a id="sortA" style="display:none" href="javascript:javascript:sortContent();" class="btnwithico" > <img src="../../core/style/default/images/sort-number.png" title="" /><b>排序&nbsp;</b> </a>
											<a href="javascript:copyAllSomeContent();" class="btnwithico"> <img src="../../core/style/default/images/doc_copy.png" title="" /><b>全复&nbsp;</b> </a>
											<a href="javascript:linkAllContent();" class="btnwithico"> <img src="../../core/style/icons/document-sub.png" title="" /><b>全引&nbsp;</b> </a>
											
											<a href="javascript:moveAllSomeContent('${param.classId}');" class="btnwithico" onclick=""> <img src="../../core/style/default/images/doc_move.png" title="" /><b>全移&nbsp;</b> </a>	
											<a href="javascript:deleteAllContent();" class="btnwithico"> <img src="../../core/style/default/images/doc_delete.png" title="" /><b>全删&nbsp;</b> </a>
												</div>
										<div class="fr">
										</div>
									</td>
								</tr>
								<tr>
									<td style="padding: 0px 11px 11px 11px">
										<div class="fl">
											<select id="typeBy" name="typeBy" class="form-select" onchange="javascript:filterAction(this.value);">
												<option value="" selected>
													内容分类
												</option>
												<cms:SystemContentTypeList>
													<option value="${ContentType.typeFlag}">
														${ContentType.typeName}
													</option>
												</cms:SystemContentTypeList>
											</select>
											&nbsp;
											<select id="createBy" name="createBy" class="form-select" onchange="javascript:filterAction(this.value);">
												<option value="0" selected>
													所有人员&nbsp;&nbsp;&nbsp;
												</option>
												<option value="1">
													我创建的
												</option>
												
												<option value="3">
													当前部门
												</option>
												
												<option value="4">
													部门体系
												</option>
												<option value="2">
													外部投稿
												</option>
												
												
										
											</select>
											&nbsp;
											<select id="censorBy" name="censorBy" class="form-select" onchange="javascript:filterAction(this.value);">
												<option value="-9999" selected>
													所有状态&nbsp;&nbsp;&nbsp;
												</option>												
												<option value="-1">
													新稿件
												</option>
												<option value="0">
													审核中
												</option>
												<option value="2">
													待发布
												</option>
												<option value="1">
													已发布
												</option>
												<option value="3">
													已下线
												</option>
												<option value="-4">
													已采集
												</option>
												<%--<option value="-2">
													预发布
												</option>
												--%><%--
												<option value="4">
													已归档
												</option>
											--%></select>
											&nbsp;
											<select id="orderBy" name="orderBy" class="form-select" onchange="javascript:filterAction(this.value);">
												<option value="default" selected>
													默认位置&nbsp;&nbsp;&nbsp;
												</option>
												<option value="addDate">
													创建时间
												</option>
												<option value="pubDate">
													发布时间
												</option>
												<!-- 以上包含top数据排序,排序位按照本身值 -->
												<option value="click">
													总点击
												</option>
												<option value="dayClick">
													日点击
												</option>
												<option value="weekClick">
													周点击
												</option>
												<option value="monthClick">
													月点击
												</option>

												<option value="comm">
													总评论
												</option>
												<option value="dayComm">
													日评论
												</option>
												<option value="weekComm">
													周评论
												</option>
												<option value="monthComm">
													月评论
												</option>

												<option value="su">
													顶人数
												</option>
												<option value="ag">
													踩人数
												</option>
												<!-- 以上各种 评论 数据不包含top数据排序,不包含一般数据,排序位按照本身值 -->

												<%--<option value="myself">
													我的文档
												</option>
												--%>
												<!-- 以上 我添加的 内容不包含top数据,排序位按照orderIdFlag-->

											</select>

											&nbsp;
											<select id="filterBy" name="filterBy" class="form-select" onchange="javascript:filterAction(this.value);">
												<option value="default" selected>
													所有内容&nbsp;&nbsp;&nbsp;
												</option>
												<option value="contentImg">
													含内容图
												</option>
												<option value="classImg">
													含列表图
												</option>
												<option value="channelImg">
													含栏目图
												</option>
												<option value="homeImg">
													含首页图
												</option>


												<!-- 以上各 引导图 不包含top数据排序,不包含一般数据,排序位按照orderIdFlag -->
												<%--<option value="myself">
													我的文档
												</option>
												--%>
												<!-- 以上 我添加的 内容不包含top数据,排序位按照orderIdFlag-->

											</select>
											&nbsp;
											<select id="orderWay" name="orderWay" class="form-select" onchange="javascript:filterAction(this.value);">
												<option value="down" selected>
													降序排列
												</option>
												<option value="up">
													升序排列
												</option>
											</select>
											&nbsp;
											<input id="filterStartDate" size="14" maxlength="30" type="text" class="form-input-top-date"  onclick="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd',onpicked: fs});" value="${param.filterStartDate}" />
											至
											<input id="filterEndDate" size="14" maxlength="30" type="text" class="form-input-top-date" onclick="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd',onpicked: fs});" value="${param.filterEndDate}" />
											 
											<script>
												function fs()
												{
													 
														filterAction();
														 
														
												}
											</script>
											
										</div>
										
										<input id="ofilterStartDate" type="hidden" />
										
										<div class="fr">
											<input id="searchKey" name="searchKey" size="25" maxlength="60" class="form-input" style="vertical-align:top;" value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>" />
											<input onclick="javascript:search();" value="搜索" class="btn-1" type="button" style="vertical-align:top;" />
										</div>

									</td>
								</tr>
								<tr>
									<td id="uid_td25" style="padding: 2px 5px;">
										<div class="DataGrid">
											<cms:SystemManageContentList createBy="${param.createBy}" type="${param.typeBy}" order="${param.orderBy}" filter="${param.filterBy}" censorBy="${param.censorBy}" page="true" pageSize="11" classId="${param.classId}" startDate="${param.filterStartDate}" endDate="${param.filterEndDate}" key="${param.key}">
												<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

													<tr class="datahead">
														<td width="4%" height="30">
															<strong>ID</strong>
														</td>
														<td width="2%" height="30">
															<input type="checkbox" name="checkbox" value="checkbox" onclick="javascript:selectAll('checkContent',this);" />
														</td>
														<td width="25%">
															<strong>
															
															<cms:if test="${DataModel.titleMode==1}">
																标题
															</cms:if>
															<cms:else>
																<cms:ModelField fieldSign="${DataModel.titleCol}">
																	${Field.showName}
																	<script>
																		tcId = '${Field.metaDataId}';
																		
																		mustFill =  '${Field.isMustFill}';
																	
																	</script>
																</cms:ModelField>
															</cms:else>
															
															
															</strong>
														</td>

														<td width="4%">
															<strong>状态</strong>
														</td>
														
														 
															<cms:if test='${param.createBy=="3" || param.createBy=="4"}'>
																<td width="8%"><strong>所属部门</strong></td>
															</cms:if>
															<cms:else >
																<td width="8%"><strong>创建者</strong></td>
															</cms:else >
															 
														 
														 
														 
														
															<cms:if test='${param.orderBy=="comm-down" || param.orderBy=="comm-up"}'>
																<td width="4%"><strong>总评论</strong></td>
															</cms:if>
															<cms:elseif test='${param.orderBy=="dayComm-down" || param.orderBy=="dayComm-up"}'>
																<td width="4%"><strong>日评论</strong></td>
															</cms:elseif>
															<cms:elseif test='${param.orderBy=="weekComm-down" || param.orderBy=="weekComm-up"}'>
																<td width="4%"><strong>周评论</strong></td>
															</cms:elseif>
															<cms:elseif test='${param.orderBy=="monthComm-down" || param.orderBy=="monthComm-up"}'>
																<td width="4%"><strong>月评论</strong></td>
															</cms:elseif>
															<cms:elseif test='${param.orderBy=="su-down" || param.orderBy=="su-up"}'>
																<td width="4%"><strong>支持数</strong></td>
															</cms:elseif>
															<cms:elseif test='${param.orderBy=="ag-down" || param.orderBy=="ag-up"}'>
																<td width="4%"><strong>反对数</strong></td>
															</cms:elseif>
															<cms:elseif test='${param.orderBy=="dayClick-down" || param.orderBy=="dayClick-up"}'>
																<td width="4%"><strong>日点击</strong></td>
															</cms:elseif>
															<cms:elseif test='${param.orderBy=="weekClick-down" || param.orderBy=="weekClick-up"}'>
																<td width="4%"><strong>周点击</strong></td>
															</cms:elseif>
															<cms:elseif test='${param.orderBy=="monthClick-down" || param.orderBy=="monthClick-up"}'>
																<td width="4%"><strong>月点击</strong></td>
															</cms:elseif>
															<cms:elseif test='${param.orderBy=="click-down" || param.orderBy=="click-up"}'>
																<td width="4%"><strong>总点击</strong></td>
															</cms:elseif>
															<cms:else>
																
															</cms:else>
														
														
														<td width="5%">
															<cms:if test='${param.orderBy=="addDate-down" || param.orderBy=="addDate-up"}'>
																<strong>添加时间</strong>
															</cms:if>
															<cms:else>
																<strong>发布时间</strong>
															</cms:else>
														</td>
														<td width="10%">
															<center><strong>操作</strong></center>
														</td>
													</tr>

													<cms:SystemContent>
													
													<cms:SystemContent id="${Info.contentId}" modelId="${Info.modelId}">

														<tr id="tr-${status.index}">
															<td>
																${Info.contentId}
															</td>
															<td>
																<input type="checkbox" name="checkContent" value="${Info.contentId}" onclick="javascript:regId('${Info.topFlag}', this, '${status.index}');" />
															</td>
															<td>
																<div align="left">
																	
																	<cms:if test="${Info.topFlag==1}">
																		<input id="top-${Info.contentId}" type="hidden" value="true" />
																		<img src="../../core/style/default/images/top_flag.png" title="固顶内容" />
																	</cms:if>
																	
																	
																	
																	<cms:if test="${!empty Info.linkCid && Info.linkCid != -1}">
																		<img src="../../core/style/icons/chain-small.png" title="引用的内容" />
																	</cms:if>
																	
																	<%--<cms:elseif test="${Info.refCid != -1}">
																		<img src="../../core/style/icons/magnet-small.png" title="复制的内容" />
																	</cms:elseif>
																	
																	--%><cms:if test="${'true' == Info.____CMS_DELETE_FOR_LUCENE____}">
																		<img src="../../core/style/default/images/delete-small.png" title="已删内容" />
																	</cms:if>
																	 
																	
																	
																	<cms:elseif test="${Info.hotFlag==1}">
																		<img src="../../core/style/default/images/fire-small.png" title="热点内容" />
																	</cms:elseif>
																	<cms:elseif test="${Info.commendFlag==1}">
																		<img src="../../core/style/default/images/hot.png" title="推荐内容" />
																	</cms:elseif>
																
																	<cms:else>
																		<img src="../../core/style/default/images/some.png" title="普通内容" />
																	</cms:else>
																	
																	<cms:if test="${Info.linkCid > 0}">
																		<cms:SystemContent objName="Linkc"  modelId="${Info.modelId}" id="${Info.linkCid}">


																		<cms:if test="${empty Linkc.outLink}">
																			<span class="STYLE1"> <a class="title-a" target="_blank" href="<cms:SystemPreview mode="content" />"  title="来自站点：<cms:Site  siteId="${Info.siteId}">${Site.siteName}</cms:Site>,  栏目： <cms:Class id="${Linkc.classId}" objName="LinkClass">${LinkClass.className}</cms:Class>">${Info.title}</a> </span></a>
																
																		</cms:if>
																		<cms:else>
																			<span class="STYLE1"> <a class="title-a" target="_blank" href="${Linkc.outLink}"  title="来自站点：<cms:Site  siteId="${Info.siteId}">${Site.siteName}</cms:Site>,  栏目： <cms:Class id="${Linkc.classId}" objName="LinkClass">${LinkClass.className}</cms:Class>">${Info.title}</a> </span></a>
																
																			 		
																		</cms:else>
																		
																		</cms:SystemContent>
																	</cms:if>
																	<cms:else>
																	
																		<cms:if test="${empty Info.outLink}">
																			<span class="STYLE1"> <a class="title-a" target="_blank" href="<cms:SystemPreview mode="content" />"   >${Info.title}</a> </span></a>
											
																		</cms:if>
																		<cms:else>
																			<span class="STYLE1"> <a class="title-a" target="_blank" href="${Info.outLink}"   >${Info.title}</a> </span></a>
											
																		</cms:else>
																							
																	</cms:else>
																</div>
															</td>

															<td>
																<cms:if test="${Info.censorState==0}">送审</cms:if>
																<cms:elseif test="${Info.censorState==1}">已发</cms:elseif>
																<cms:elseif test="${Info.censorState==-2}">预发</cms:elseif>
																<cms:elseif test="${Info.censorState==2}">待发</cms:elseif>
																<cms:elseif test="${Info.censorState==3}">下线</cms:elseif>
																<cms:elseif test="${Info.censorState==-1}">稿件</cms:elseif>
																<cms:elseif test="${Info.censorState==-4}">采集</cms:elseif>
															</td>
															 
															 <cms:if test='${param.createBy=="3" || param.createBy=="4"}'>
																<td><cms:SystemOrg orgCode="${Info.orgCode}">${Org.orgName}</cms:SystemOrg></td>
															</cms:if>
															<cms:else>
																<td>
																
																 
																 <cms:SystemUser name="${Info.creator}">
																									 
																								 	
																 ${SysUser.userTrueName} 						 
																									
																</cms:SystemUser> 
																 
																</td>
															</cms:else >
															 
															 
															
																<cms:if test='${param.orderBy=="comm-down" || param.orderBy=="comm-up"}'>
																	<td>${Info.commCount}</td>
																</cms:if>
																<cms:elseif test='${param.orderBy=="dayComm-down" || param.orderBy=="dayComm-up"}'>
																	<td>${Info.commDayCount}</td>
																</cms:elseif>
																<cms:elseif test='${param.orderBy=="weekComm-down" || param.orderBy=="weekComm-up"}'>
																	<td>${Info.commWeekCount}</td>
																</cms:elseif>
																<cms:elseif test='${param.orderBy=="monthComm-down" || param.orderBy=="monthComm-up"}'>
																	<td>${Info.commMonthCount}</td>
																</cms:elseif>
																<cms:elseif test='${param.orderBy=="su-down" || param.orderBy=="su-up"}'>
																	<td>	${Info.supportCount}</td>
																</cms:elseif>
																<cms:elseif test='${param.orderBy=="ag-down" || param.orderBy=="ag-up"}'>
																		<td>${Info.againstCount}</td>
																</cms:elseif>
																<cms:elseif test='${param.orderBy=="dayClick-down" || param.orderBy=="dayClick-up"}'>
																		<td>${Info.clickDayCount}</td>
																</cms:elseif>
																<cms:elseif test='${param.orderBy=="weekClick-down" || param.orderBy=="weekClick-up"}'>
																		<td>${Info.clickWeekCount}</td>
																</cms:elseif>
																<cms:elseif test='${param.orderBy=="monthClick-down" || param.orderBy=="monthClick-up"}'>
																		<td>${Info.clickMonthCount}</td>
																</cms:elseif>
																<cms:elseif test='${param.orderBy=="click-down" || param.orderBy=="click-up"}'>
																	<td>${Info.clickCount}</td>
																</cms:elseif>
																<cms:else>
														
																</cms:else>

															</td>
															
															<td>
																<cms:if test='${param.orderBy=="addDate-down" || param.orderBy=="addDate-up"}'>
																${Info.addTime}
															</cms:if>
																<cms:else>
																	<cms:if test="${Info.appearStartDateTime==''}">
																		<br />
																		<br />
																	</cms:if>
																	<cms:else>
															    ${Info.appearStartDateTime}
															</cms:else>

																</cms:else>

															</td>
															<td>
																<div align="center">
																	<cms:if test="${'true' == Info.____CMS_DELETE_FOR_LUCENE____}">
																		<a name="not_use_href" disabled  href="javascript:;"><img src="../../core/style/icon/balloon-ellipsis.png" width="16" height="16" />&nbsp;评论</a>&nbsp;
																		<a name="not_use_href" disabled  href="javascript:;"><img src="../../core/style/icon/document-pencil.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;
																		<a name="not_use_href" disabled  href="javascript:;"><img src="../../core/style/icon/del.gif" width="16" height="16" />&nbsp;删除</a>
																	
																		<script>
																			disableAnchorElementByName("not_use_href", true);
																		</script>
																	</cms:if>
																	<cms:else>
																		<%--<a href="javascript:openManageCommentDialog('${Info.contentId}','<cms:JsEncode str='${Info.title}'/>');"><img src="../../core/style/icon/balloon-ellipsis.png" width="16" height="16" />&nbsp;评论</a>&nbsp;
																		--%>
																		
																		<cms:if test="${empty Info.outLink}">
																		 	<a  target="_blank" href="<cms:SystemPreview mode="content" />" ><img src="../../core/style/icons/projection-screen-presentation.png" width="16" height="16" />&nbsp;预览</a>&nbsp;
																		
																		</cms:if>
																		<cms:else>
																		 	<a   target="_blank" href="${Info.outLink}"  />" ><img src="../../core/style/icons/projection-screen-presentation.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;
																		
																		</cms:else>
																		
																		<a href="javascript:gotoEditUserDefineContentPageDialog('${Info.contentId}', '${Info.linkCid}', '<cms:JsEncode str='${Info.creator}'/>','${Info.censorState}','${Info.orgCode}');"><img src="../../core/style/icon/document-pencil.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;
																		
																		<a href="javascript:openContentOperLogDialog('${Info.contentId}','<cms:JsEncode str='${Info.title}'/>');"><img src="../../core/style/icons/document-task.png" width="16" height="16" />&nbsp;日志</a>&nbsp;
																		
																		
																		<%--<a href="javascript:deleteContent('${Info.modelId}','${Info.contentId}','<cms:JsEncode str='${Info.creator}'/>','${Info.censorState}');"><img src="../../core/style/icon/del.gif" width="16" height="16" />&nbsp;删除</a>
																		--%>
																		</cms:else>
																	</span>
																</div>
															</td>

														</tr>
													</cms:SystemContent>
													</cms:SystemContent>

													<cms:Empty flag="Info">
														<tr>
															<td class="tdbgyew" colspan="9">
																<center>
																	当前没有数据!
																</center>
															</td>
														</tr>
													</cms:Empty>

													<cms:if test="${empty param.key}">
														<tr id="pageBarTr">
															<td colspan="8" class="PageBar" align="left">
																<div class="fr">
																	<span class="text_m"> 共 ${page.totalCount} 条记录 第${page.currentPage}页 / ${page.pageCount}页 <input type="text" size="5" id="pageJumpPos" name="pageJumpPos"> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()"> </span>
																	<span class="page">[<a href="${page.headQuery}">首页</a>]</span>
																	<span class="page">[<a href="${page.prevQuery}">上一页</a>]</span>
																	<span class="page">[<a href="${page.nextQuery}">下一页</a>]</span>
																	<span class="page">[<a href="${page.endQuery}">末页</a>]</span>&nbsp;
																</div>
																<script>
																function jump()
																{
																	window.location="${page.jumpQuery}&currentPage="+document.getElementById('pageJumpPos').value;
																}
															</script>
																<div class="fl"></div>
															</td>
														</tr>
													</cms:if>
													<cms:else>
														<cms:PageInfo>
															<tr id="pageBarTr">
																<td colspan="8" class="PageBar" align="left">
																	<div class="fr">
																		<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
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
																
																	
																	replaceUrlParam(window.location,'currentPage='+cp);		
																}
													
													
																function jump()
																{
																    var cp = parseInt(document.getElementById('pageJumpPos').value);
																    
																    if(cp > parseInt('${Page.pageCount}'))
																	{
			                                                           cp=parseInt('${Page.pageCount}');
																	}
																
																	replaceUrlParam(window.location,'currentPage='+cp);
																}
															</script>
																	<div class="fl"></div>
																</td>
															</tr>
														</cms:PageInfo>
													</cms:else>

												</table>

											</cms:SystemManageContentList>
										</div>

									</td>
								</tr>

							</table>
						<div class="mainbody-right"></div>
						</td>
					</tr>


				</table>

				<from id="publishForm" name="publishForm">
				<input type="hidden" id="someContentId" name="someContentId"></input>
				<input type="hidden" id="staticType" name="staticType" value="2"></input>
				</from>
				<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">


initSelect('createBy','${param.createBy}');
initSelect('typeBy','${param.typeBy}');
initSelect('censorBy','${param.censorBy}');
initSelect('filterBy','${param.filterBy}');
initSelect('orderBy',orderBy);
initSelect('orderWay',orderWay);

if('' == orderBy || 'default' == orderBy)
{
	$('#sortA').show();
}

if('' == orderBy || 'default' == orderBy || 'addDate' == orderBy || 'pubDate' == orderBy )
{
	$('#topA').show();
}



var currentClassId = "${param.classId}";
var currentModelId = "${Class.contentType}"

var topf = '';

//提示

<cms:SystemWorkflowAction actId="${param.actId}">
		<cms:SystemContent id="${param.censorCId}" modelId="${param.modelId}">
		
		
		if('1' == '${Action.conjunctManFlag}')
		{
			//会签模式
			var info;
			
			if('1' == '${param.actSuccess}')
			{
				info = '${Info.title}<br/><br/> 提交<font color="red"> [${Action.passActionName}]</font> 成功11111!';		
			}
			else
			{
				info = '${Info.title}<br/><br/> 提交<font color="red"> [${Action.passActionName}]</font> 成功! <br/><br/>当前动作为会签,需全体通过生效。';	
			}
			
			
			$.dialog({ 
   					title :'提示',
    				width: '280px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/succ.png', 
    				
                    content: info,
   
                    ok: true 
			});

			
		}
		else
		{
			$.dialog({ 
   					title :'提示',
    				width: '280px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/succ.png', 
    				
                    content: '${Info.title}<br/><br/> 提交<font color="red"> [${Action.passActionName}]</font> 成功2222!',
   
                    ok: true 
			});

			
		}
		</cms:SystemContent>
</cms:SystemWorkflowAction>


var targetSortId = -1;

var checkedIdMap = new HashMapJs();



function gotoAddUserDefineContentPage()
{
	window.location.href="UserDefineModelGeneralAdd.jsp?classId=${param.classId}&modelId=${Class.contentType}";
}
 
function gotoAddUserDefineContentPageDialog()
{
 	if('${Class.singleContentId}' != '0')
 	{
 		var dialog = $.dialog({ 
   					title :'提示',
    				width: '220px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '已经存在单页内容，不可继续添加！',
                                      
       cancel: true 
                    
	   });
       return;
 	
 	}
 	
 	if(mustFill != '1' && '${DataModel.titleMode}' == '0')
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '470px', 
    				height: '70px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '当前模型为数据模式,须指定一个必填类型文本字段为主显标题才可操作内容！<br/><br/>请前往模型管理页设定或检查指定字段是否为必填模式',
                                      
       cancel: true 
                    
	   });
       return;
	
	}
 	
 	
	$.dialog({ 
		id : 'main_content',
    	title :'新增内容',
    	width:   '1200px', 
    	 height: (window.parent.document.body.scrollHeight-80 )+'px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/content/UserDefineModelGeneralAdd.jsp?classId=${param.classId}&modelId=${Class.contentType}&innerWidth=1200'
	});

}

function gotoEditUserDefineContentPage(contentId, linkId, creator, censor)
{
	<cms:LoginUser>
		var currentManager = '${Auth.apellation}';
	</cms:LoginUser>
	
	if(censor == '-1' && creator != currentManager && currentManager != 'admin')
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '170px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '只能处理自己的稿件！',
                                      
       cancel: true 
                    
	   });
       return;
	}
	
	if(linkId != '' && linkId != '-1')
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '170px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '引用来的内容无法编辑！',
                                      
       cancel: true 
                    
	   });
       return;
	}

 
	window.location.href="EditUserDefineModelContent.jsp?contentId="+contentId+"&classId=${param.classId}&modelId=${Class.contentType}&uid="+Math.random()+'&manageParam='+encodeURIComponent(window.location.search);
}




function gotoEditUserDefineContentPageDialog(contentId, linkId, creator, censor, orgCode)
{
	<cms:LoginUser>
		var currentManager = '${Auth.apellation}';
		var uorgCode = '${Auth.orgCode}';
	</cms:LoginUser>
	
	if(censor == '-1' && creator != currentManager && currentManager != 'admin')
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '170px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '只能处理自己的稿件！',
                                      
       cancel: true 
                    
	   });
       return;
	}
	
	if(linkId != '' && linkId != '-1')
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '170px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '引用来的内容无法编辑！',
                                      
       cancel: true 
                    
	   });
       return;
	}
	
	if(mustFill != '1' && '${DataModel.titleMode}' == '0')
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '470px', 
    				height: '70px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '当前模型为数据模式,须指定一个必填类型文本字段为主显标题才可操作内容！<br/><br/>请前往模型管理页设定或检查指定字段是否为必填模式',
                                      
       cancel: true 
                    
	   });
       return;
	
	}
	
	
	if('1' == censor && orgCode != uorgCode && '1' == '${Class.orgMode}')
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '210px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您无法编辑其他部门的内容！',
                                      
       cancel: true 
                    
	   });
       return;
	}


	$.dialog({ 
		id : 'main_content',
    	title :'编辑内容',
    	width:   '1200px', 
    	 height: (window.parent.document.body.scrollHeight-80 )+'px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/content/EditUserDefineModelContent.jsp?contentId='+contentId+"&classId=${param.classId}&modelId=${Class.contentType}&uid="+Math.random()+'&manageParam='+encodeURIComponent(window.location.search)+'&innerWidth=1200'
	});
 
}





function sortContent()
{
   if(targetSortId == -1)
   {
 
     
        var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择待排序内容！',
                    
                   
        cancel: true 
                    
	
		});
        return;
   }
   
   
   $.dialog({ 
		    id : 'ocsd',
	    	title : '内容排序',
	    	width: '950px', 
	    	height: '740px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:<cms:BasePath/>core/content/dialog/SortContent.jsp?uid='+Math.random()+'&classId=${param.classId}&targetId='+targetSortId
	
	});
   
  
   
}

//以下为排序筛选操作

function filterAction(filterValue)
{
    var filterStartDate = document.getElementById('filterStartDate').value;
    var filterEndDate = document.getElementById('filterEndDate').value;
    
    var createByVar = document.getElementById('createBy').value;
    var censorByVar = document.getElementById('censorBy').value;
    var typeByVar = document.getElementById('typeBy').value;
    var orderByVar = document.getElementById('orderBy').value+'-'+document.getElementById('orderWay').value;
    var filterByVar = document.getElementById('filterBy').value;
    
    
    if(filterStartDate != '' || filterEndDate != '')
    {
    	//若时间范围不为空,强制为按照添加时间排序
    	//orderByVar = 'addDate-'+document.getElementById('orderWay').value;
    }
    else if('pubDate' == document.getElementById('orderBy').value)
    {
    	//若为发布时间,则强制状态为已发布内容筛选
    	censorByVar = '1';
    }
    
    //alert('ManageArticleContent.jsp?classId='+currentClassId+'&modelId='+currentModelId+'&appearStartDate='+appearStartDate+'&appearEndDate='+appearEndDate+'&censorBy='+censorByVar+'&typeBy='+typeByVar+'&orderBy='+orderByVar);
    window.location='ManageGeneralContent.jsp?classId='+currentClassId+'&modelId='+currentModelId+'&filterStartDate='+filterStartDate+'&filterEndDate='+filterEndDate+'&createBy='+createByVar+'&censorBy='+censorByVar+'&typeBy='+typeByVar+'&orderBy='+orderByVar+'&filterBy='+filterByVar;
	

}

function deleteSelectContent()
{
	if('${Class.singleContentId}' != '0')
 	{
 		var dialog = $.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '单页内容不可删除！',
                                      
       cancel: true 
                    
	   });
       return;
 	
 	}

	var cidCheck = document.getElementsByName('checkContent');
	
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
    				
                    content: '请选择要删除的内容！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	deleteContent('${Class.contentType}',ids);
}

function deleteAllContent()
{ 
	if('${Class.workflowId > 0}' == 'true')
 	{
 		var dialog = $.dialog({ 
   					title :'提示',
    				width: '380px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/fail.png', 
    				
                    content: '启用工作流的栏目删除内容请在 [编辑] 中使用删除工作流！',
                                      
       cancel: true 
                    
	   });
       return;
 	
 	}
 	
	if('${Class.singleContentId}' != '0')
 	{
 		var dialog = $.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '单页内容不可删除！',
                                      
       cancel: true 
                    
	   });
       return;
 	
 	}

	$.dialog({ 
   					title :'提示',
    				width: '230px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除栏目所有内容吗？<br/>(注意：不包含其他管理员稿件)',
                    
                    ok: function () 
                    { 
                    var tip = $.dialog.tips('正在删除全栏目内容到回收站,请耐心等待...',3600000000,'loading.gif'); 
                   
                    //var url = "<cms:BasePath/>content/deleteContent.do?flag=allContent&modelId="+modelId;
                    var url = "<cms:BasePath/>content/deleteContentToTrash.do?flag=allContent&modelId=${param.modelId}&classId=${param.classId}"+"&<cms:Token mode='param'/>";
                    
 		
 					//$("#content").val(text);
					//var postData = encodeURI($("#replyText,#configFlag,#gbId").serialize());
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {       
			            	var msg = eval("("+mg+")");
           		
			               if('success' == msg  || '' == msg || mg.indexOf('发布失败') != -1)
			               {
			               		 
			               		window.location.reload();
			               } 	
			               else
			               {
			               		tip.close();
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

//dialog操作
function deleteContent(modelId,ids, creator, censor)
{ 
	if('${Class.workflowId > 0}' == 'true')
 	{
 		var dialog = $.dialog({ 
   					title :'提示',
    				width: '380px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/fail.png', 
    				
                    content: '启用工作流的栏目删除内容请在 [编辑] 中使用删除工作流！',
                                      
       cancel: true 
                    
	   });
       return;
 	
 	}
 	
	if('${Class.singleContentId}' != '0')
 	{
 		var dialog = $.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '单页内容不可删除！',
                                      
       cancel: true 
                    
	   });
       return;
 	
 	}
	<cms:LoginUser>
		var currentManager = '${Auth.apellation}';
	
	
	if(censor == '-1' && creator != currentManager && currentManager != 'admin')
	{
		if('001' != '${Auth.orgCode}')
    	{
			var dialog = $.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '只能处理自己的稿件！',
	                                      
	       cancel: true 
	                    
		   });
	       return;
       }
	}
	
	</cms:LoginUser>


	var dialog = $.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选内容吗？<br/>(注意：不包含其他管理员稿件)',
                    
                    ok: function () 
                    { 
                   	 $.dialog.tips('正在执行删除...',3600000000,'loading.gif');
                    //var url = "<cms:BasePath/>content/deleteContent.do?ids="+ids+"&modelId="+modelId;
                    var url = "<cms:BasePath/>content/deleteContentToTrash.do?classId=${param.classId}&ids="+ids+"&modelId="+modelId+"&<cms:Token mode='param'/>";
                    
 		
 					//$("#content").val(text);
					//var postData = encodeURI($("#replyText,#configFlag,#gbId").serialize());
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
           		
			               if('success' == msg  || '' == msg || mg.indexOf('发布失败') != -1)
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

function showCommendContentDialog(contentId,classId,modelId,title)
{

	var ti = '';
	if(title != '')
	{
		ti = ''+title.substring(0,22)+'...';
	}
	
	ti = ti.replace('<','&lt;');
	ti = ti.replace('>','&gt;');
	
	$.dialog({ 
		    id : 'occtcd',
	    	title : '' + ti,
	    	width: '650px', 
	    	height: '480px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'core/content/dialog/ShowCommendTypeDialog.jsp?uid='+Math.random()+'&classId='+classId+'&contentId='+contentId+'&modelId='+modelId
	
	});

}

function showCommendMutpiContentDialog(classId,modelId)
{
	var cidCheck = document.getElementsByName('checkContent');
	
	var top = false;
	
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
    				
                    content: '请选择需要推荐的内容！', 
       cancel: true 
                    
	
	  });
	  return;
	}
		
	$.dialog({ 
		    id : 'ocmctcd',
	    	title : '推荐到推荐位或专题',
	    	width: '790px', 
	    	height: '525px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'core/content/dialog/ShowCommendTypeDialog.jsp?uid='+Math.random()+'&classId='+classId+'&contentId='+ids+'&modelId='+modelId
	
	});

}


function linkSomeContent()
{
	var cidCheck = document.getElementsByName('checkContent');
	
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
    				
                    content: '请选择需要引用的内容！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	<cms:CurrentSite>
	openLinkContentToSiteClassDialog('manage','${Class.contentType}','','${CurrSite.siteId}');
	</cms:CurrentSite>
}

function copySomeContent()
{
	var cidCheck = document.getElementsByName('checkContent');
	
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
    				
                    content: '请选择需要复制的内容！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	<cms:CurrentSite>
	openCopyContentToSiteClassDialog('manage','${Class.contentType}','','${CurrSite.siteId}');
	</cms:CurrentSite>
}

function copyAllSomeContent()
{
	var siteId = <cms:CurrentSite>'${CurrSite.siteId}';</cms:CurrentSite>
	
	$.dialog({ 
	    id : 'occtcd',
    	title : '复制所有内容至栏目',
    	width: '480px', 
    	height: '560px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:'+basePath+'core/content/dialog/ShowCopyContentClass.jsp?all=true&siteId='+siteId+'&uid='+Math.random()+'&modelId=${Class.contentType}&refClassIdStr=&flag=manage'

	});
	

}

function linkAllContent()
{
	
	var siteId = <cms:CurrentSite>'${CurrSite.siteId}';</cms:CurrentSite>
	
	$.dialog({ 
	    id : 'occtcd',
    	title : '引用所有内容至栏目',
    	width: '480px', 
    	height: '560px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:'+basePath+'core/content/dialog/ShowLinkContentClass.jsp?all=true&siteId='+siteId+'&uid='+Math.random()+'&modelId=${Class.contentType}&refClassIdStr=&flag=manage'

	});
	

}

function shareContent()
{
	var cidCheck = document.getElementsByName('checkContent');
	
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
    				
                    content: '请选择需要共享的内容！', 
       cancel: true 
                    
	
	  });
	  return;
	}


    openSelectSiteGroupDialog(ids,'manage');
}


function moveSomeContent(classId)
{
	if('${Class.singleContentId}' != '0')
 	{
 		var dialog = $.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '单页内容不可移动！',
                                      
       cancel: true 
                    
	   });
       return;
 	
 	}
 	
	var cidCheck = document.getElementsByName('checkContent');
	
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
    				
                    content: '请选择需要移动的内容！', 
       cancel: true 
                    
	  });
	  return;
	}

	openMoveContentToSiteClassDialog('${Class.contentType}',classId);
}


function moveAllSomeContent(classId)
{
	if('${Class.singleContentId}' != '0')
 	{
 		var dialog = $.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '单页内容不可移动！',
                                      
       cancel: true 
                    
	   });
       return;
 	
 	}
 	
	$.dialog({ 
		    id : 'occtcd',
	    	title : '移动所有内容至栏目',
	    	width: '480px', 
	    	height: '560px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'core/content/dialog/ShowMoveContentClass.jsp?uid='+Math.random()+'&all=true&&modelId=${Class.contentType}&classId='+classId
	
	});
}

function setTopFlag()
{
	var cidCheck = document.getElementsByName('checkContent');
	
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
    				
                    content: '请选择需要置顶的内容！', 
       cancel: true 
                    
	  });
	  return;
	}
	
	 var url = "<cms:BasePath/>content/setTopFlag.do?classId=${param.classId}&ids="+ids+'&top='+topf+"&<cms:Token mode='param'/>";
                    

 		
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

}

function openManageCommentDialog(contentId, title)
{
	var ti = '';
	
	ti = title.replaceAll('<','&lt;');
	ti = ti.replaceAll('>','&gt;');

	$.dialog({ 
		    id : 'omcd',
	    	title : '评论管理 - '+ti,
	    	width: '1180px', 
	    	height: '710px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'core/comment/ManageSingleContentComment.jsp?uid='+Math.random()+'&contentId='+contentId
	
	});

}


function openContentOperLogDialog(contentId, title)
{
	var ti = '';
	
	ti = title.replaceAll('<','&lt;');
	ti = ti.replaceAll('>','&gt;');

	$.dialog({ 
		    id : 'ocold',
	    	title : '操作记录 - '+ti,
	    	width: '1400px', 
	    	height: '780px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'core/content/dialog/ViewContentOperInfo.jsp?uid='+Math.random()+'&contentId='+contentId+'&modelId=${Class.contentType}&classId=${Class.classId}'
	
	});

}


function regId(topFlag, obj, index)
{
	var cidCheck = document.getElementsByName('checkContent');
	
	var ids='';
	var topCheck;
	var haveTop = false;
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			topCheck = document.getElementById('top-'+cidCheck[i].value);
			if(topCheck != null && 'true' == topCheck.value)
			{
				haveTop = true;
			}
		}
	}
	
	if(haveTop)
	{
		topf = 'down';
		document.getElementById('topActName').innerHTML = '解固';
	}
	else
	{
		topf = 'up';
		document.getElementById('topActName').innerHTML = '固顶';
	}
	 
	//背景色

	if(obj.checked == true)
	{
		$('#tr-'+index).addClass("tdbgyewck"); 
	}
	else
	{
		$('#tr-'+index).removeClass("tdbgyewck"); 
	}
	
	//排序
	if(obj.checked==true)
    {
      targetSortId=obj.value;
      checkedIdMap.put(obj.value,obj.value);
  
    }
    else
    {
      targetSortId = -1;
      checkedIdMap.remove(obj.value);
    }

}

function search()
{
	var key = encodeData(encodeURI(encodeURI(document.getElementById('searchKey').value)));
	
	window.location='ManageGeneralContent.jsp?classId=${param.classId}&key='+key;
}

function addTagForSomeContent()
{
	var cidCheck = document.getElementsByName('checkContent');
	
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
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要添加Tag的内容！', 
       cancel: true 
                    
	  });
	  return;
	}

	$.dialog({ 
		    id : 'ostd',
	    	title : '选取Tag标签',
	    	width: '510px', 
	        height: '555px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'core/content/dialog/ShowSiteTagForMoreContent.jsp?cIds='+ids+'&uid='+Math.random()
	
	});
}


function addTagForSomeContent()
{
	var cidCheck = document.getElementsByName('checkContent');
	
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
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要添加Tag的内容！', 
       cancel: true 
                    
	  });
	  return;
	}

	$.dialog({ 
		    id : 'ostd',
	    	title : '选取Tag标签',
	    	width: '510px', 
	        height: '555px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'core/content/dialog/ShowSiteTagForMoreContent.jsp?cIds='+ids+'&uid='+Math.random()
	
	});
}
 

function pickSingleAitlcleContent()
{
	

	$.dialog({ 
		    id : 'psad',
	    	title : '采集转载外部内容',
	    	width: '720px', 
	        height: '650px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'core/content/dialog/PickSingleArticle.jsp?classId=${param.classId}&uid='+Math.random()
	
	});
}

</script>

</cms:SystemDataModel>
</cms:SysClass>
