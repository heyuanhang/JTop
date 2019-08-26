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

		<script>  
		
	    basePath = '<cms:BasePath/>';
	    
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

         	if("${param.error}" === "true")	
         	{
         	     showErrorMsg("<cms:UrlParam target='${param.errorMsg}' />");
         	}
         	else
         	{
	             api.close(); 
	             //W.$.dialog.tips('添加成功...',2); 
	             W.location.reload();
         	}
       		       
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^(.){1,7}$/;

         $(function()
		 {
		    validate('btName',0,ref_name,'格式为1~7个文字');
 			validate('btKey',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
         
        	
      </script>
	</head>
	<body>

		<form id="wxForm" name="wxForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<!--main start-->
					 
						<div class="addtit">
							<img src="../style/icons/ui-tab-content.png" width="16" height="16" />菜单配置
					    </div>
						
						<cms:QueryData objName="Wxm" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxMenuForTag" var="1,,${param.btId}">
														
														
						<div class="auntion_tagRoom_Content">
							<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
								<ul>
									<li>
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
											<tr>
												<td width="25%" class="input-title">
													<strong>菜单类型</strong>
												</td>
												<td class="td-input">
													<select id="btType" name="btType" class="form-select" style="width:352px">
														<option value="-1">
															--------------------- 请选择菜单类型 --------------------
														</option>
														
														<option value="click">
															点击推事件
														</option>
														
														<option value="view">
															跳转URL
														</option>
														
														<option value="scancode_push">
															扫码推事件
														</option>
														
														<option value="scancode_waitmsg">
															扫码推事件且弹出“消息接收中”提示
														</option>
														
														<option value="pic_sysphoto">
															弹出系统拍照发图
														</option>
														
														<option value="pic_photo_or_album">
															弹出拍照或者相册发图
														</option>
														
														<option value="pic_weixin">
															弹出微信相册发图器
														</option>
														
														<option value="location_select">
															弹出地理位置选择器
														</option>
														
														 
														 
													</select>
												</td>
											</tr>
											
											 
											<tr>
												<td class="input-title">
													<strong>菜单名称</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:348px" id="btName" name="btName" value="${Wxm.btName}" class="form-input"></input>

												</td>
											</tr>
											<tr>
												<td class="input-title">
													<strong>KEY值</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:348px" id="btKey" name="btKey" value="${Wxm.btKey}" class="form-input"></input>
												</td>
											</tr>
											
											<tr>
												<td class="input-title">
													<strong>网页链接</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:348px" id="btUrl" name="btUrl" value="${Wxm.btUrl}" class="form-input"></input>
												</td>
											</tr>
											
											<%--<tr>
												<td class="input-title">
													<strong>回复消息</strong>
												</td>
												<td class="td-input">
													<cms:QueryData objName="News" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getNewsItemForTag"  var=",,,${Wxm.mtId}">						
														<input type="text" size="41" readonly id="msgTitle"  class="form-input" value="图文：${News.title}"></input>
														<input type="hidden" id="mtId" name="mtId"  class="form-input" value="${News.infoId}"></input>
													</cms:QueryData>
													<input type="button" value="素材" onclick="javascript:openSelectWxItemInfoDialog( );" class="btn-1" />
												</td>
											</tr>
											
											--%><tr>
											<td class="input-title">
													<strong>回复消息</strong>
												</td>
												<td class="td-input">
											 
													<cms:if test="${Wxm.mtId != -1}">
														<cms:QueryData objName="News" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getNewsItemForTag"  var=",,,,${Wxm.mtId}">						
													
													 		<input type="text" style="width:348px" readonly id="msgTitle" name="msgTitle"  class="form-input" value="[图文${News.infoId}] ${News.title}"></input>
															
														<input type="button" value="素材" onclick="javascript:openSelectWxItemInfoDialog( );" class="btn-1" />
														</cms:QueryData>
													
													</cms:if>

													<cms:elseif  test="${Wxm.resId != -1}">
													
														<cms:QueryData objName="WR" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxResForTag"  var="${Wxm.resId},,,,">
														 
														<cms:ResInfo res="${WR.imageRes}">
														
															<input type="text" style="width:348px" readonly id="msgTitle"  name="msgTitle" class="form-input" value="[${WR.resTypeStr}${WR.wrId}] ${WR.resTitle}"></input>
															 
														<input type="button" value="素材" onclick="javascript:openSelectWxItemInfoDialog( );" class="btn-1" />
													
														
														</cms:ResInfo>
														</cms:QueryData>
													
													</cms:elseif>
													 
													<cms:else>
													
														<input type="text" style="width:348px" readonly id="msgTitle" name="msgTitle"  class="form-input" value=""></input>
														<input type="button" value="素材" onclick="javascript:openSelectWxItemInfoDialog( );" class="btn-1" />
														
													</cms:else>
													
													<input type="hidden" id="mtId" name="mtId"  class="form-input" value="${Wxm.mtId}"></input>
													<input type="hidden" id="resId" name="resId"  class="form-input" value="${Wxm.resId}"></input>
												 
													 
												</td>
								</tr>
											
											<tr>
												<td class="input-title">
													<strong>扩展接口</strong>
												</td>
												<td class="td-input">
													<select id="behaviorClass" name="behaviorClass" class="form-select" style="width:352px">
														<option value="">
															---------------------- 请选扩展接口 ----------------------
														</option>
														
														 
														<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxExtendForTag" objName="WB" var=",,menu">
																<option value="${WB.beClass}">
																	${WB.ebName}
																</option>
														</cms:QueryData>
														

													</select>
												</td>
											</tr>
											<%--
											 


											 <tr id="mustFillDIV">
												<td class="input-title">
													<strong>回复类型 
												</td>
												<td class="td-input">
													<input name="msgType" type="radio" value="1" checked onclick="javascript:changeInfo(1);"/>
													图文&nbsp;
													<input name="msgType" type="radio" value="2" onclick="javascript:changeInfo(2);"/>
													文本&nbsp;
													<input name="msgType" type="radio" value="3" onclick="javascript:changeInfo(3);"/>
													音乐&nbsp;
													<input name="msgType" type="radio" value="4" onclick="javascript:changeInfo(4);"/>
													视频&nbsp;
													<input name="msgType" type="radio" value="5" onclick="javascript:changeInfo(5);"/>
													语音&nbsp;
												</td>
											</tr>
											
											<tr>
												<td width="29%" class="input-title">
													<strong>素材</strong>
												</td>
												<td class="td-input">
													<select id="btType" name="btType" class="form-select" onchange="javascript:switchAdvertConfig(this.value,'${param.posId}')">
														<option value="-1">
															------------------- 可选择回复素材 ------------------
														</option>
														
														<option value="click">
															点击推事件
														</option>
														
														<option value="view">
															跳转URL
														</option>
														
														<option value="scancode_push">
															扫码推事件
														</option>
														
														<option value="scancode_waitmsg">
															扫码推事件且弹出“消息接收中”提示
														</option>
														
														<option value="pic_sysphoto">
															弹出系统拍照发图
														</option>
														
														<option value="pic_photo_or_album">
															弹出拍照或者相册发图
														</option>
														
														<option value="pic_weixin">
															弹出微信相册发图器
														</option>
														
														<option value="location_select">
															弹出地理位置选择器
														</option>
														
														 
														 
													</select>
												</td>
											</tr>

											--%><!-- 以下为独立选项 start -->

										</table>

										<div style="height:26px;"></div>
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<div style="float:right">
														<a name="btnwithicosysflag"  href="javascript:submitWxMenuForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
													</div>
												</tr>
											</table>
										</div>
									</li>
								</ul>
							</div>

							<!-- 第二部分:参数 -->

						</div>

					</td>
				</tr>
			</table>

			<!-- hidden -->
			<input type="hidden" name="btId" id="btId" value="${Wxm.btId}" />
			
			<cms:Token mode="html"/>

		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

initSelect('btType','${Wxm.btType}');

initSelect('behaviorClass','${Wxm.behaviorClass}');

function setTab(flag,pos,size)
{
	if(!hasError)
	{
		setTab2(flag,pos,size);
	}
}

  
function close()
{
	api.close();
}


function submitWxMenuForm()
{    
	if($('#btType').val() == '-1')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个菜单类型!',

		    				cancel: true
		});
		
		return;
    }
	
	var hasError = false;
	//系统信息字段验证
   var currError = submitValidate('btKey',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('btName',0,ref_name,'格式为1~7个文字');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    
    			
    if(hasError)
    {
    	 
	    
	    return;

	}
	
	 
	
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	encodeFormInput('wxForm', false);
   
    var form = document.getElementById('wxForm');

    form.action="<cms:BasePath/>wx/editMenu.do";
    
    form.submit(); 
    
}




function openSelectWxItemInfoDialog()
{
	W.$.dialog({ 
	    id : 'odcrd',
    	title : '选取素材',
    	width: '860px', 
    	height: '600px', 
    	lock: true, 
        max: false, 
        parent:api,
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/weixin/SelectWxItemInfo.jsp?apiId=oewmd'
	});
}



</script>
</cms:QueryData>
