<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script>  
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '130px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/i.png', 
			    		
			                    content: '编辑操作成功!',
	
			    				ok:function()
			    				{ 
	             					W.location.reload();
			    				}
			});   
         }
       
    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/image-crop.png" width="16" height="16" />分辨率
					</div>

					<cms:QueryData service="cn.com.mjsoft.cms.channel.service.ChannelService" method="getImageRadioTag" objName="Ra" var="${param.irId}">
					
					<form id="irForm" name="irForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							
							<tr>
								<td width="27%" class="input-title">
									<strong>图片规格名称</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:230px;" id="ratioName" name="ratioName" class="form-input" value="${Ra.ratioName}"></input>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>
							<tr>
								<td class="input-title">
									<strong>宽度</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:75px;" id="ratioWidth" name="ratioWidth" class="form-input" value="${Ra.ratioWidth}"></input>&nbsp;px
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span class="input-title"><strong>高度</strong></span>
									<input type="text" style="width:75px;" id="ratioHeight" name="ratioHeight" class="form-input" value="${Ra.ratioHeight}"></input>&nbsp;px				
								</td>
							</tr>

						</table>
						<!-- hidden -->
						<input type="hidden" name="irId" id="irId" value="${Ra.irId}" />
						
						<cms:Token mode="html"/>
						
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a  href="javascript:submitIrForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" id="submitTagClassFormImg"/><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
								</div>
								 
							</tr>
						</table>
					</div>
				</td>
			</tr>


		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>
   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}


function submitIrForm()
{
	encodeFormInput('irForm', false);
	
	var irFrom = document.getElementById('irForm');
	
	irFrom.action = "<cms:BasePath/>channel/editIR.do";;
	
	irFrom.submit();
}
   

  
</script>
</cms:QueryData>
