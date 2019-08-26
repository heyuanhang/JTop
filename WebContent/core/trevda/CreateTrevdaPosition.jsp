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
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('posName',0,ref_name,'格式为文字,数字,下划线');
 			validate('posFlag',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
         
        	
      </script>
	</head>
	<body>

		<form id="advertPosForm" name="advertPosForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<!--main start-->
					 
						<div class="addtit">
							<img src="../style/icons/ui-scroll-pane-image.png" width="16" height="16" />版位配置
					    </div>

						<div class="auntion_tagRoom_Content">
							<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
								<ul>
									<li>
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
											<tr>
												<td width="28%" class="input-title">
													<strong>广告类型</strong>
												</td>
												<td class="td-input">
													<select id="configId" name="configId" class="form-select" style="width:258px" onchange="javascript:switchAdvertConfig(this.value,'${param.posId}')">
														<option value="-1">
															----------- 请选择广告类型 -----------
														</option>
														<cms:SystemAdvertConfig configId="all">
															<option value="${Config.configId}">
																${Config.configName}
															</option>
														</cms:SystemAdvertConfig>
													</select>
												</td>
											</tr>
											<tr>
												<td class="input-title">
													<strong>版位名称</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:258px" id="posName" name="posName" class="form-input"></input>

												</td>
											</tr>
											<tr>
												<td class="input-title">
													<strong>引用代码</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:258px" id="posFlag" name="posFlag" class="form-input"></input>
												</td>
											</tr>
											<tr>
												<td class="input-title">
													<strong>版位大小</strong>
												</td>
												<td class="td-input">
													宽:
													<input type="text" style="width:102px" id="width" name="width" class="form-input"></input>
													&nbsp;高:
													<input type="text" style="width:102px" id="height" name="height" class="form-input"></input>
												</td>
											</tr>


											<tr id="mustFillDIV">
												<td class="input-title">
													<strong>投放策略 
												</td>
												<td class="td-input">
													<input name="showMode" type="radio" value="1" checked />
													<span class="STYLE12">随机排序</span> &nbsp;
													<input name="showMode" type="radio" value="2" />
													<span class="STYLE12">权重优先</span> &nbsp;
													<input name="showMode" type="radio" value="3" />
													<span class="STYLE12">按关键字</span>

												</td>
											</tr>

											<tr id="dataTypeDIV">
												<td class="input-title">
													<strong>版位描叙</strong>
												</td>
												<td class="td-input">
													<textarea id="posDesc" name="posDesc" style="height:50px;width:258px" class="form-textarea"></textarea>
												</td>
											</tr>

											<tr id="mustFillDIV">
												<td class="input-title">
													<strong>页面打开方式 
												</td>
												<td class="td-input">
													<input name="target" type="radio" value="1" checked />
													<span class="STYLE12">新的页面</span> &nbsp;
													<input name="target" type="radio" value="2" />
													<span class="STYLE12">当前页面</span>
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
														<a name="btnwithicosysflag"  href="javascript:submitAdvertPosForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
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



 


function submitAdvertPosForm()
{    
	if($('#configId').val() == '-1')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个广告类型!',

		    				cancel: true
		});
		
		return;
    }
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('posFlag',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('posName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
    	 
	    
	    return;

	}
	
	//后台验证
	var count = validateExistFlag('advertPos', $('#posFlag').val());
	
	if(count > 0)
	{
		showTips('posFlag', '系统已存在此值,不可重复录入');
		
		return;
	}
	
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	encodeFormInput('advertPosForm', false);
   
    var form = document.getElementById('advertPosForm');

    form.action="<cms:BasePath/>trevda/createTrevdaPos.do";
    
    form.submit(); 
    
}

function switchAdvertConfig(configId)
{
	window.location.href = '<cms:BasePath/>core/trevda/CreateTrevdaPosition.jsp?configId='+configId+'&uid='+Math.random();
}


</script>

