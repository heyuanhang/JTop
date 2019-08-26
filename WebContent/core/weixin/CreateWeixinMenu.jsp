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
		
		  
	
		 
         if("" != "${param.code}")
         {  

         	if("${param.code}" === "-2")	
         	{
         	 
         	     W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '150px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/fail.png', 
			    				
			                    content: '一级菜单不能超过三个!',
	
			    				ok:function()
			    				{ 
	       							 
			    				}
				});
         	}
         	else if("${param.code}" === "-3")	
         	{
         	      
         	     W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '150px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/fail.png', 
			    				
			                    content: '二级菜单不能超过五个!',
	
			    				ok:function()
			    				{ 
	       							 
			    				}
				});
         	}
         	else if("${param.code}" === "1")	
         	{
	           
	            W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '150px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/i.png', 
			    				
			                    content: '添加菜单成功!',
	
			    				ok:function()
			    				{ 
	       							W.window.location.reload();  
			    				}
				});
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
													<select id="btType" name="btType" class="form-select" style="width:352px" >
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
															扫码推且弹出“消息接收中”提示
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
												<td  class="input-title">
													<strong>父菜单</strong>
												</td>
												<td class="td-input">
													<select id="parentId" name="parentId" class="form-select" style="width:352px">
														<option value="-1">
															 --------------------- 请选择父菜单 ---------------------
														
														<option value="-9999">
															>>>>创建为一级菜单<<<<
														</option>
														
														<cms:QueryData objName="Wxm" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxMenuForTag" var="1,-9999,">
																<option value="${Wxm.btId}">
																	${Wxm.btName}
																</option>
														</cms:QueryData>
														
														 
														
														 
														
														 
														 
													</select>
												</td>
											</tr>
											<tr>
												<td class="input-title">
													<strong>菜单名称</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:348px" id="btName" name="btName" class="form-input"></input>

												</td>
											</tr>
											<tr>
												<td class="input-title">
													<strong>KEY值</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:348px" id="btKey" name="btKey" class="form-input"></input>
												</td>
											</tr>
											
											<tr>
												<td class="input-title">
													<strong>网页链接</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:348px" id="btUrl" name="btUrl" class="form-input"></input>
												</td>
											</tr>
											 
											 <tr>
												<td class="input-title">
													<strong>回复消息</strong>
												</td>
												<td class="td-input">
												 		<input type="text" style="width:348px" readonly id="msgTitle"  class="form-input" value=""></input>
													 
													<input type="button" value="素材" onclick="javascript:openSelectWxItemInfoDialog( );" class="btn-1" />
													
													<input type="hidden" id="mtId" name="mtId"  class="form-input" value="-1"></input>
													<input type="hidden" id="resId" name="resId"  class="form-input" value="-1"></input>
												 
												</td>
											</tr>
											
											<tr>
												<td class="input-title">
													<strong>扩展接口</strong>
												</td>
												<td class="td-input">
													
													<select d="behaviorClass" name="behaviorClass" class="form-select" style="width:352px" >
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
											



											<%--<tr id="mustFillDIV">
												<td class="input-title">
													<strong>启用状态 
												</td>
												<td class="td-input">
													<input name="useState" type="radio" value="1" checked />
													启用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input name="useState" type="radio" value="0" />
													停用
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
			<input type="hidden" name="useState" id="useState" value="1" />
			
			<cms:Token mode="html"/>

		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

initSelect('configId','${param.configId}');

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
    
    if($('#parentId').val() == '-1')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个父菜单!',

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
		
	//W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	encodeFormInput('wxForm', false);
   
    var form = document.getElementById('wxForm');

    form.action="<cms:BasePath/>wx/createMenu.do";
    
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
       
        content: 'url:<cms:Domain/>core/weixin/SelectWxItemInfo.jsp?apiId=ocwmd'
	});
}


</script>

