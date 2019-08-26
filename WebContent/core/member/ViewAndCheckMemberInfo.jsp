<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />

		<script>  
		
         var api = frameElement.api, W = api.opener; 
		 	
      </script>
	</head>
	<body>
		<cms:Member id="${param.id}">



			<form id="advertPosForm" name="advertPosForm" method="post">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" valign="top">

							<!--main start-->
							<div class="auntion_tagRoom" style="margin-top:2px">
								<ul>
									<li id="two1" onclick="setTab2('two',1,2)" class="selectTag">
										<a href="javascript:;"><img src="../style/icons/user.png" width="16" height="16" />会员信息&nbsp;</a>
									</li>
									<li id="two2" onclick="setTab2('two',2,2)">
										<a href="javascript:;"><img src="../style/icons/table.png" width="16" height="16" />扩展信息&nbsp;</a>
									</li>

								</ul>
							</div>

							<div class="auntion_tagRoom_Content">
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
												<tr>
													<td width="19%" class="input-title">
														<strong>会员名称</strong>
													</td>
													<td class="td-input">
														<input type="text"  style="width:455px" readonly class="form-input" value="${Member.memberName}"></input>

													</td>
												</tr>
												<tr>
													<td class="input-title">
														<strong>Email</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:455px" readonly id="email"  class="form-input" value="${Member.email}"></input>

													</td>
												</tr>
												<tr>
													<td class="input-title">
														<strong>手机号码</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:455px" readonly  id="phoneNumber" class="form-input" value="${Member.phoneNumber}"></input>

													</td>
												</tr>
												<tr>
													<td class="input-title">
														<strong>真实姓名</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:455px" readonly class="form-input" value="${Member.trueName}"></input>

													</td>
												</tr>
												
												
												<tr>
													<td class="input-title">
														<strong>所属会员组</strong>
													</td>
													<td class="td-input">
														<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberRoleInfoForTag" objName="RoleInfo" var="${Member.memberId}">												
														
														<input type="text" style="width:455px" readonly class="form-input" value="${RoleInfo}"></input>
														
														</cms:QueryData>
													</td>
												</tr>
												
												<tr>
													<td class="input-title">
														<strong>会员等级</strong>
													</td>
													<td class="td-input">
														<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberSingleRankByLevelForTag" objName="Rank" var="${Member.memLevel}">												
														
															<cms:if test="${empty Rank}">
																<input type="text" style="width:455px" readonly class="form-input" value="无等级信息"></input>
															</cms:if>
															<cms:else>
																<input type="text" style="width:455px" readonly class="form-input" value="${Rank.rankName} (等级值：${Rank.rankLevel} 级 )"></input>
															</cms:else>
														
														</cms:QueryData>
														 
													</td>
												</tr>
												
												<tr>
													<td class="input-title">
														<strong>当前积分</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:455px" readonly class="form-input" value="${Member.score} 分"></input>

													</td>
												</tr>
												
												<tr>
													<td class="input-title">
														<strong>帐户类型</strong>
													</td>
													<td class="td-input">
														<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberRegTypeForTag" objName="loginType" var="${Member.memberId}">												
														
														<input type="text" style="width:189px" readonly class="form-input" value="${loginType}"></input>
														</cms:QueryData>
														&nbsp;&nbsp;<span class="input-title"><strong>登录次数</strong>&nbsp;</span><input type="text" style="width:189px" readonly class="form-input" value=" ${Member.loginSuccessCount} 次"></input>

		
													</td>
												</tr>
												
											 
												
 
												<tr>
													<td class="input-title">
														<strong>注册时间</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:189px" readonly class="form-input" value="${Member.regDT}"></input>
														&nbsp;&nbsp;<span class="input-title"><strong>上次登陆</strong>&nbsp;</span><input type="text" style="width:189px" readonly class="form-input" value="${Member.prevLoginDT}"></input>
		
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>上次登陆地址</strong>
													</td>
													<td class="td-input">
														<input type="text" style="width:189px" readonly class="form-input" value="${Member.prevLoginIp} ${Member.prevLoginArea}"></input>

													</td>
												</tr>
												
												<tr>
													<td class="input-title">
														<strong>头像及身份证</strong>
													</td>
													<td class="td-input">
															<cms:ResInfo res="${Member.headPhoto}">
															<a class="cmsSysShowSingleImage"  href="${Res.url}"><img  src="<cms:SystemResizeImgUrl reUrl="${Res.reUrl}" />" width="95" height="76" /> </a>
																
															</cms:ResInfo>
															<cms:ResInfo res="${Member.certPhotoP}">
															<a class="cmsSysShowSingleImage"  href="${Res.url}"><img  src="<cms:SystemResizeImgUrl reUrl="${Res.reUrl}" />" width="95" height="76" /> </a>
																
															</cms:ResInfo>
															
															<cms:ResInfo res="${Member.certPhotoR}">
															<a class="cmsSysShowSingleImage"  href="${Res.url}"><img  src="<cms:SystemResizeImgUrl reUrl="${Res.reUrl}" />" width="95" height="76" /> </a>
																
															</cms:ResInfo>
													</td>
												</tr>

											 


											</table>

											<div style="height:15px"></div>
											 
										</li>
									</ul>
								</div>

								<!-- 第二部分:参数 -->
								<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
									<div style="height:10px;"></div>
									<ul>
										<li>
											 
											<cms:CurrentSite>
												
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table-big-content" >
												
												<cms:SystemModelFiledList modelId="${CurrSite.extMemberModelId}" showMode="true">
													<cms:SystemModelFiled>
														<tr>
															<td class="input-title" width="20%">
																<cms:SystemModelRowFiled >
																	<!-- 图集字段区域 -->
																	<cms:if test="${RowFiled.htmlElementId==14}">
																	${RowFiled.showName}:										
															
															</td>
															<td class="td-input">
																<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload" style="padding-top:0px;">
																	<tr>
																		<td>
																			<!-- 图集操作 -->
																			<table width="100%" border="0" cellpadding="0" cellspacing="0" style="padding-top:0px;">
																				<tr style="padding-top:0px;">
																					<td style="padding-top:0px;">
																					
																			                <a onclick="javascript:deleteAllGroupPhoto('${RowFiled.fieldSign}');" class="btnwithico"> <img src="../../core/style/icons/image--minus.png" alt="" /><b>全部删除&nbsp;</b> </a>
																							<a onclick="javascript:showModuleImageGroupDialog('${RowFiled.fieldSign}');" class="btnwithico"> <img src="../../core/style/icons/images.png" alt="" /><b>多图上传&nbsp;</b> </a>
				
																					</td>
																					<td style="padding-top:0px;">
																						&nbsp;&nbsp;&nbsp;&nbsp;
																						<input type="text" size="5" id="${RowFiled.fieldSign}CmsSysMaxWidth" class="form-input" value="${RowFiled.fieldInfo.imageW}"></input>
																						宽度&nbsp;&nbsp;&nbsp;&nbsp;
																						<input type="text" size="5" id="${RowFiled.fieldSign}CmsSysMaxHeight" class="form-input" value="${RowFiled.fieldInfo.imageH}"></input>
																						高度&nbsp;&nbsp;&nbsp;&nbsp;
																						<select class="form-select" id="${RowFiled.fieldSign}CmsSysDisposeMode">
																							<option value="0">
																								原宽高
																							</option>
																							<option value="1">
																								按宽度
																							</option>
																							<option value="2">
																								按高度
																							</option>
																							<option value="3">
																								按宽高&nbsp;&nbsp;
																							</option>
																						</select>
																						缩放&nbsp;&nbsp;&nbsp;水印: 
																						<input class="form-checkbox" disabled type="checkbox" value="1" id="${RowFiled.fieldSign}CmsSysNeedMark" />
																						&nbsp;&nbsp;图片数 [
																						<font color=red><span id="${RowFiled.fieldSign}CmsSysImageGroupCount">-1</span></font> ]&nbsp;&nbsp;张
				
																						<script>
							
																						initSelect('${RowFiled.fieldSign}CmsSysDisposeMode','${RowFiled.fieldInfo.imageDisposeMode}');	
																						initRadio('${RowFiled.fieldSign}CmsSysNeedMark','${RowFiled.fieldInfo.needMark}');	
																
																						</script>
																					</td>
																				</tr>
				
																			</table>
																			<!-- 图集操作结束 -->
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
														<!-- 图集内容开始 -->
														<tr>
															<td class="input-title" width="20%">
																<!-- 图集内容空标题 -->
															</td>
															<td class="td-input" style="padding-top:0px;">
																<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload" style="padding-top:0px;">
																	<tr style="padding-top:0px;">
																		<td style="padding-top:0px;">
																			<input type="hidden" id="${RowFiled.fieldSign}CmsSysImageCurrentCount" name="${RowFiled.fieldSign}CmsSysImageCurrentCount" value="0" />
																			<input type="hidden" id="${RowFiled.fieldSign}CmsSysImageCover" name="${RowFiled.fieldSign}CmsSysImageCover" value="" />
																			<input type="hidden" id="${RowFiled.fieldSign}CmsSysImageArrayLength" name="${RowFiled.fieldSign}CmsSysImageArrayLength" value="0" />
				
																			<div id="${RowFiled.fieldSign}CmsSysImageUploadTab">
																				<!-- 图片信息区 -->
																				<script>
																		
																				//派序用
				
																				allImageGroupSortInfo['${RowFiled.fieldSign}'] = new Array();
																				
																				<cms:PhotoGroup  contentId="${RowFiled.info.contentId}" serverMode="true">
																				
																					addGroupPhotoToPage('${RowFiled.fieldSign}','${RowFiled.fieldInfo.imageW}','${RowFiled.fieldInfo.imageH}','${Photo.orderFlag}','${Photo.url}','${Photo.resizeUrl}','${Photo.resId}','${Photo.reUrl}','${Photo.photoName}','${Photo.height}','${Photo.width}','${Photo.photoDesc}');
																					//封面
																					if('${Photo.isCover}' == 1)
																					{
																						document.getElementById('${RowFiled.fieldSign}-cover-${Photo.orderFlag-1}').checked = true;
																						$("#${RowFiled.fieldSign}CmsSysImageCover").val('${Photo.reUrl}');
																					}	
																				
																				</cms:PhotoGroup>
																				
																				//图片数
																				document.getElementById('${RowFiled.fieldSign}CmsSysImageGroupCount').innerHTML = document.getElementById('${RowFiled.fieldSign}CmsSysImageCurrentCount').value;
																				
																				var sortArray = allImageGroupSortInfo['${RowFiled.fieldSign}'];
																				
																				document.getElementById('${RowFiled.fieldSign}CmsSysImageArrayLength').value = allImageGroupSortInfo['${RowFiled.fieldSign}'].length;
																				
																				//alert(document.getElementById('${RowFiled.fieldSign}CmsSysImageCurrentCount').value);
																				</script>
																			</div>
				
																		</td>
				
																		<!-- 图集内容结束 -->
																		</cms:if>
				
																		<!-- 普通字段区域开始 -->
																		<cms:else>
																			<cms:if test="${status.index == 0}">
																			${RowFiled.showName}:									
																		</td>
																				<td class="td-input">
																				
																					<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
																						<tr>
																							<td>
																								<table  border="0"  cellpadding="0" cellspacing="0">
																									 <tr>
																									 	<td>
																									 		<div id="sys-obj-${RowFiled.fieldSign}">${RowFiled.editModeLayoutHtml}
																											<cms:if test="${RowFiled.isMustFill==1}">
																												<span class="red">*</span>
																												<span class="ps"></span>
																											</cms:if>
																											</div>
																									 	</td>
																									 	<td>
																									 		<div style="width:${RowFiled.blankCount}px;"></div>
																									 	</td>
																									 </tr>
																								 
																								 </table>
																								
																							</td>
																							
																							</cms:if>
																							<cms:else>
																								<td>
																									<span class="input-title">${RowFiled.showName}:</span>&nbsp;
																								</td>
																								<td>
																									<table  border="0"  cellpadding="0" cellspacing="0">
																									 <tr>
																									 	<td>
																									 		<div id="sys-obj-${RowFiled.fieldSign}">${RowFiled.editModeLayoutHtml}
																											<cms:if test="${RowFiled.isMustFill==1}">
																												<span class="red">*</span>
																												<span class="ps"></span>
																											</cms:if>
																											</div>
																									 	</td>
																									 	<td>
																									 		<div style="width:${RowFiled.blankCount}px;"></div>
																									 	</td>
																									 </tr>
																								 
																								  </table>
																									
																								</td>
																								
																							</cms:else>
																							</cms:else>
																							</cms:SystemModelRowFiled>
																						</tr>
																					</table>
																					
																				</td>
																	</tr>
																	</cms:SystemModelFiled>
																	</cms:SystemModelFiledList>
																	
																	
																	<cms:Empty flag="ModelFiled">
														

																		<tr>
																			<td>
																				<center>
																				<table class="listdate" width="98%" cellpadding="0" cellspacing="0">
				
																					<tr>
																						<td class="tdbgyew" style="height:20px;">
				
																							
																								当前没有数据!
																							
																							</div>
				
																						</td>
																					</tr>
				
																				</table>
																				</center>
																			</td>
																		</tr>
				
																	
		
									
																</cms:Empty>
																	
																</table>
														
															</table>
												

											</cms:CurrentSite>
											
											<div style="height:15px"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														 <div style="float:right">
														 <%--

															 <a href="javascript:resetPhone();"  class="btnwithico"><img src="../style/blue/icon/application--pencil.png" width="16" height="16"><b>手机重置&nbsp;</b> </a>
															<a href="javascript:resetMail();"  class="btnwithico"><img src="../style/blue/icon/application--pencil.png" width="16" height="16"><b>邮件重置&nbsp;</b> </a>															
															--%>
															
															 <a href="javascript:censorMan('1');"  class="btnwithico"><img src="../style/blue/icon/application--pencil.png" width="16" height="16"><b>身份认可&nbsp;</b> </a>
														<a href="javascript:censorMan('0');"  class="btnwithico"><img src="../style/blue/icon/application--pencil.png" width="16" height="16"><b>无效用户&nbsp;</b> </a>
														
															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>关闭&nbsp;</b> </a>
														 </div>
													</tr>
												</table>
											</div>
											
											
										</li>
									</ul>
								</div>

							</div>

						</td>
					</tr>
				</table>

				<!-- hidden -->
				

			</form>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">


//图片查看效果
loadImageShow();

 


function censorMan(flag)
{
	var url = "<cms:BasePath/>member/editMemberBasic.do?censorMode=true&id=${Member.memberId}&trueMan="+flag;
    
    var postData = "";

 	$.ajax({
      	type: 'POST',
       	url: url,
       	data:postData,
   
       	success: function(msg)
        {        
            
            	W.$.dialog.tips('设定成功...',1); 
            	window.location.reload();
				return;
           
            
        }
     });

}

function close()
{
	api.close();
	W.window.location.reload();
}

function checkCoreInfo(mode,check)
{

	var url = "<cms:BasePath/>member/checkCoreInfo.do?mode="+mode+"&check="+check+"&memberId=${Member.memberId}";
    
    var postData = "";

 	$.ajax({
      	type: 'POST',
       	url: url,
       	data:postData,
   
       	success: function(msg)
        {        
            
            if('success' == msg)
            {
            	W.$.dialog.tips('设定成功...',1); 
            	window.location.reload();
				return;
            }
            else
            {
            	W.$.dialog.tips('设定失败...',1); 
            }
        }
     });


}


function openPhoto(url,flag)
{
	var mode='';
	
	if('p' == flag)
	{
		mode='正';
	}
	else
	{
		mode='反';
	}
	
	if(url == '' || url == null)
	{
		W.$.dialog
			    ({ 
			  		title : '提示',
			    	width: '160px', 
			    	height: '60px', 
			        lock: true, 
			        parent:api,
			    	icon: '32X32/succ.png', 
			    				
			        content: '没有上传身份证明图片或图片已失效!', 
			       	cancel: function()
			       	{
			       		window.location.reload();
			       	}
		});
		return;
	}
	window.open("ZcmVierCertPhoto.jsp?url="+url+"&name=${Member.memberName}&mode="+mode); 

}
 



</script>
</cms:Member>
