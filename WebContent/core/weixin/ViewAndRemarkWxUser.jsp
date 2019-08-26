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
		<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxUserForTag" objName="WU" var="${param.openId},,,,,">
 

			

			<form id="dataForm" name="dataForm" method="post">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" valign="top">

							<!--main start-->
							
							 
							<div class="auntion_tagRoom_Content">
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
												<tr>
													<td width="19%" class="input-title">
														<strong>用户昵称</strong>
													</td>
													<td class="td-input">
													
														<input type="text" size="29" readonly class="form-input" value="${WU.wuNickname}"></input>
														&nbsp;&nbsp;<span class="input-title"><strong>性别信息</strong>&nbsp;</span>
														<cms:if test="${WU.wuSex == 1}"><input type="text" size="28" readonly class="form-input" value="男"></input>
														</cms:if><cms:else><input type="text" size="28" readonly class="form-input" value="女"></input></cms:else>
			
													</td>
												</tr>
												 
												<tr>
													<td class="input-title">
														<strong>openId</strong>
													</td>
													<td class="td-input">
														<input type="text" size="76" readonly id="openId" name="openId"  class="form-input" value="${WU.openId}"></input>

													</td>
												</tr>
												<tr>
													<td class="input-title">
														<strong>所属组</strong>
													</td>
													<td class="td-input">
													<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxUserGroupForTag" objName="UG" var="${WU.wuGroupid}">
 														<input type="text" size="76" readonly  id="phoneNumber" class="form-input" value="${UG.wuGroupName}"></input>
 														
 													</cms:QueryData>
														
													</td>
												</tr>
												<tr>
													<td class="input-title">
														<strong>地区</strong>
													</td>
													<td class="td-input">
														<input type="text" size="76" readonly class="form-input" value="${WU.wuCountry} - ${WU.wuProvince} - ${WU.wuCity}"></input>

													</td>
												</tr>
												
										 
												<tr>
													<td class="input-title">
														<strong>关注时间</strong>
													</td>
													<td class="td-input">
														<input type="text" size="29" readonly class="form-input" value="${WU.wuSubscribe_time}"></input>
														&nbsp;&nbsp;<span class="input-title"><strong>是否关注</strong>&nbsp;</span>
														<cms:if test="${WU.subStatus == 1}">
																<input type="text" size="29" readonly class="form-input" value="是"></input>
		
															</cms:if>
															<cms:else>
																<input type="text" size="29" readonly class="form-input" value="否"></input>
		
															</cms:else>
														
													</td>
												</tr>

												<tr>
													<td class="input-title">
														<strong>备注</strong>
													</td>
													<td class="td-input">
														<textarea id="userRemark" name="userRemark" class="form-textarea" style="width:400px; height:150px;">${WU.userRemark}</textarea>
					
													</td>
												</tr>
												
												<tr>
													<td class="input-title">
														<strong>用户头像</strong>
													</td>
													<td class="td-input">
														 
														<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
																<tr>
																	<td>
																		 <img src="${WU.wuHeadimgurl}" width="95" height="76" /> 
																	</td>
																	<td>
																		<table border="0" cellpadding="0" cellspacing="0" height="65px" class="form-table-big">
																			<tr>
																				<td>
																					&nbsp;
																					<input type="button" onclick="javascript:" value="上传" class="btn-1" />
																					<input type="button" value="裁剪" onclick="javascript:;" class="btn-1" />
																					<input type="button" value="删除" onclick="javascript:;" class="btn-1" />
																				</td>
																			</tr>
																			<tr>
																				<td>
																					&nbsp;&nbsp;宽&nbsp;&nbsp;
																					<input id="logoImageCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${Res.imageW}" />
																					&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																					<input id="logoImageCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" value="${Res.imageH}" />
																				</td>
																			</tr>
																		</table>
																	 			</td>
																</tr>
															</table>
															 
													</td>
												</tr>

											 


											</table>

											<div style="height:15px"></div>
											 
										</li>
									</ul>
								</div>

							
											
											<div style="height:15px"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														 <td class="td-input"> 
														 <div style="float:right">													
															 <a href="javascript:remark();"  class="btnwithico"><img src="../style/blue/icon/application--pencil.png" width="16" height="16"><b>添加备注&nbsp;</b> </a>															
															 <a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>关闭&nbsp;</b> </a>
														&nbsp;
														</div>
														</td>
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

 
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}

function remark()
{
			 var url = "<cms:BasePath/>wx/remarkUser.do?<cms:Token mode='param'/>";
                    
              var postData = encodeURI($("#dataForm").serialize());
	    
	    	 postData = postData.replace(/\+/g, " ");
        	 postData = encodeData(postData);     
                    
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:postData,
			   			
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
			            	
			               if('success' == msg)
			               {
			               		 	
			               		W.$.dialog({ 
				   					title :'提示',
				    				width: '200px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				parent:api,
				                    content: '更新备注成功!',
				                    
				                    ok: function () 
				                    { 
				                    	window.location.reload();
				                    }
				                    
    								
                                 });
			               		
			               	
			               } 	
			               else
			               {
			               	       W.$.dialog(
								   { 
									   					title :'提示',
									    				width: '200px', 
									    				height: '60px', 
									                    lock: true, 
									                     parent:api,
									    				icon: '32X32/fail.png', 
									    				
									                    content: "执行失败，无权限请联系管理员！",
							
									    				cancel: function () 
									                    { 
									                    	W.window.location.reload();
									                    }
									});
			               }   
			              
			            }
			     	});	
}
</script>
</cms:QueryData>
