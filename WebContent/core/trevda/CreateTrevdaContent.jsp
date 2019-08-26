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
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />


		<script>  
		
		basePath = '<cms:BasePath/>';
		
		var dialogApiId = '${param.dialogApiId}';
		
		
	
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
		    validate('adName',0,ref_name,'格式为文字,数字,下划线');
 			validate('adFlag',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
         
        	
      </script>
	</head>
	<body>

		<form id="advertForm" name="advertForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<!--main start-->
						<div class="addtit">
							<img src="../style/icons/megaphone.png" width="16" height="16" />配置
					    </div>

						<div class="auntion_tagRoom_Content">
							<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
								<ul>
									<li>
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
											<tr>
												<td class="input-title">
													<strong>所属版位</strong>
												</td>
												<td class="td-input">
													<select id="posId" name="posId" style="width:365px" class="form-select" onchange="javascript:switchAdvertPos(this.value);">
														<option value="-1">
															-------------------- 请选择版位 ---------------------
														</option>
														<cms:SystemAdvertPos posId="all">
															<option value="${Pos.posId}">
																${Pos.posName}&nbsp;&nbsp;&nbsp;
															</option>
														</cms:SystemAdvertPos>
													</select>
													&nbsp;
													<cms:SystemAdvertConfig posId="${param.posId}">
														<cms:if test="${!empty Config.configName}">
													   	[ ${Config.configName} ]
													   
													   </cms:if>
														<cms:else>

														</cms:else>

													</cms:SystemAdvertConfig>
												</td>
											</tr>
											<tr>
												<td width="24%" class="input-title">
													<strong>广告名称</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:365px" id="adName" name="adName" class="form-input"></input>

												</td>
											</tr>
											<tr>
												<td class="input-title">
													<strong>引用代码</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:365px" id="adFlag" name="adFlag" class="form-input"></input>
												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>连接关键词</strong>
												</td>
												<td class="td-input">
													<input type="text" style="width:365px" id="keyword" name="keyword" class="form-input"></input>
												</td>
											</tr>


											<tr>
												<td class="input-title">
													<strong>投放周期</strong>
												</td>
												<td class="td-input">

													<input id="showStartDate" name="showStartDate" style="width:163px" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" />
													&nbsp;至 &nbsp;
													<input id="showEndDate" name="showEndDate" style="width:163px" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" />

												</td>
											</tr>

											<tr>
												<td class="input-title">
													<strong>权重值</strong>
												</td>
												<td class="td-input">
													<input type="text" size="25" id="importance" name="importance" class="form-input"></input>
													<span class="ps">权重值大的广告，出现机率就会高</span>
												</td>
											</tr>

											<tr id="mustFillDIV">
												<td class="input-title">
													<strong>页面打开方式 
												</td>
												<td class="td-input">
													<input name="target" type="radio" value="1" checked />
													<span class="STYLE12">新页面</span>&nbsp;
													<input name="target" type="radio" value="2" />
													<span class="STYLE12">当前页</span>
												</td>
											</tr>

											<tr id="mustFillDIV">
												<td class="input-title">
													<strong>启用状态 
												</td>
												<td class="td-input">
													<input name="useState" type="radio" value="1" checked />
													<span class="STYLE12">启用</span> &nbsp;
													<input name="useState" type="radio" value="0" />
													<span class="STYLE12">停用</span>
												</td>
											</tr>

											<!-- 以下为独立选项 start -->

										</table>

										<div style="height:26px;"></div>
										<div class="breadnavTab" >
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<div style="float:right">
														<a name="btnwithicosysflag"  href="javascript:submitAdvertForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
														<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
													</div>
												</tr>
											</table>
										</div>
									</li>
								</ul>
							</div>

							<!-- 第二部分:步骤动作 -->
							

						</div>

					</td>
				</tr>
			</table>

			<!-- hidden -->
			<input type="hidden" id="" name="" value=""/>
			
			 <cms:Token mode="html"/>
		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">
initSelect('posId','${param.posId}');
//switchAdvertPos('${param.posId}');

//图片查看效果
loadImageShow();

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



 


function submitAdvertForm()
{    	     
	if($('#posId').val() == '-1')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个广告版位!',

		    				cancel: true
		});
		
		return;
    }
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('adFlag',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
   
    currError = submitValidate('adName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
    	 
	    
	    return;

	}
	
	
	//后台验证
	
	
		var count = validateExistFlag('advertContent', $('#adFlag').val());
		
		if(count > 0)
		{
			showTips('adFlag', '系统已存在此值，不可重复录入');
			
			return;
		}
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	encodeFormInput('advertForm', false);
	
    var form = document.getElementById('advertForm');
	
	
    form.action="<cms:BasePath/>trevda/createTrevda.do";
    
    form.submit(); 
    
}

function switchAdvertPos(posId)
{
	window.location.href='<cms:BasePath/>core/trevda/CreateTrevdaContent.jsp?dialogApiId=ocad&posId='+posId+'&uid='+Math.random();
}


</script>

