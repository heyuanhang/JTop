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




		<script language="javascript" type="text/javascript" src="../../javascript/upload/SWFUpload/swfupload.js"></script>
		<script language="javascript" type="text/javascript" src="../../javascript/upload/SWFUpload/handlerImage.js"></script>

		<style type="text/css">
		<!--
		* {
		    margin:0;
		    padding:0;
		    font-size:12px;
		    text-decoration:none;
		}
		#products {
		    width:520px;
		    margin:1px auto;
		}
		#products li {
		    width:115px;
		    height:125px;
		    float:left;
		    margin-top:5px;
		    margin-left:12px;
		    display:inline;
		}
		#products li a {
		    display:block;
		}
		#products li a img {
		    border:1px solid #666;
		    padding:1px;
		}
		#products li span a {
		    width:110px;
		    height:30px;
		    line-height:14px;
		    text-align:center;
		 white-space:nowrap;
		    text-overflow:ellipsis; 
		    overflow: hidden;
		}
		-->
		</style>
		<script>  
	
		basePath='<cms:Domain/>';
	
		 var api = frameElement.api, W = api.opener; 
		 
         $(function()
		 {
		 	<cms:CurrentSite>
			var swfu = initUploadImage('<cms:SID/>','${CurrSite.imageATVal}','${CurrSite.imageMaxC}');
			</cms:CurrentSite>
				
			//当前页图片数量初始化
			imageCount = '${Info.photoCount}';		    
		 })
         
       
			
          imageSrcId='${param.srcId}';
          
          imageValId='${param.valId}';

		  targetClassId='${param.classId}';
		  
		  fieldSign = '${param.fieldSign}';
		  
		  dialogApi = '${param.apiId}';
		  	
      </script>
	</head>
	<body>
		<form method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<%--<tr>
								<td width="17%" class="input-title">
									<strong>类型</strong>
								</td>
								<td class="td-input">
									<input type="radio" name="source" class="form-radio" value="server" onclick="javascript:changeSource('s')" checked />
									本地图片
									<input type="radio" name="source" class="form-radio" value="web" onclick="javascript:changeSource('w')" />
									网络图片

								</td>
							</tr>

							--%>
							<tr id="serverTr">
								<td width="17%" class="input-title">
									<strong>当前文件</strong>
								</td>
								<td class="td-input">
									<textarea readonly id="fileInfo" name="fileInfo" style="word-wrap:normal;height:100px;width:355px" class="form-textarea"></textarea>
									<span id="spanButtonPlaceholder">选择...</span>

								</td>
							</tr>

							<tr id="webTr" style="display:none">
								<td class="input-title">
									<strong>网络地址</strong>
								</td>
								<td class="td-input">
									<textarea id="webUrl" name="webUrl" style="word-wrap:normal;height:100px;width:355px" class="form-textarea"></textarea>
									(地址以逗号分割)
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>图片规格</strong>
								</td>
								<td class="td-input">
									<input type="text" size="8" id="maxWidth" class="form-input" value="${param.mw}"></input>
									宽度&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" size="8" id="maxHeight" class="form-input" value="${param.mh}"></input>
									高度&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select class="form-select" id="disposeMode">
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
											按宽高&nbsp;&nbsp;&nbsp;
										</option>
									</select>
									缩放
								</td>
							</tr>


							<tr>

								<!-- 以下为独立选项 start -->
						</table>
						<div id="divProcessing" style="width:200px;height:30px;position:absolute;left:200px;top:180px;display:none">
							<img style="vertical-align: middle;" src="../../javascript/dialog/skins/icons/loading.gif" width=28 height=28 class="ui_icon_bg" />
							文件处理中......
							<span id="uploadPercent">0%</span>
						</div>
						<div style="height:20px;"></div>
						<div class="breadnavTab" >
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="btnbg100">
									<div style="float:right">
										<a id="buttonHref" href="javascript:addNewImage();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
										<a href="javascript:close();"  class="btnwithico"><img src="../../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
									</div>
								</tr>
							</table>
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

initSelect('disposeMode','${param.dw}');

function addNewImage()
{

	startUploadFile();
	
	
}

function changeSource(flag)
{
	if('s' == flag)
	{
		$('#serverTr').show();
		$('#webTr').hide();
	}
	else if('w' == flag)
	{
		$('#webTr').show();
		$('#serverTr').hide();
	}

}

function close()
{
	api.close();
}

function selectImage(resId)
{
	var imageCheck = document.getElementsByName('selectImage');
	
	for(var i=0; i<imageCheck.length;i++)
	{
		if(imageCheck[i].value == resId)
		{
			if(imageCheck[i].checked == false)
			{
				imageCheck[i].checked = true;
			}
			else
			{
				imageCheck[i].checked = false;
			}
			
		}
	} 
}

function changeMark()
{
	if($('input[name="needMark"]:checked').val() == '1')
	{
		mark = '1';
	}
	else
	{
		mark = '0';
	}

}

//优化link
linkBlur();


</script>

