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
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>


	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="2%">

											</td>

											<td width="30%">

												<strong>规则名称 
											</td>





										</tr>


										<cms:SystemPublishRuleList type="${param.type}">
											<cms:SystemPublishRule>
												<tr>
													<td>

														<input type="radio" name="selectRule"  value="${Rule.ruleId}|${Rule.ruleName}" />

													</td>
													<td>
														&nbsp;${Rule.ruleName}
													</td>
													
												</tr>
											</cms:SystemPublishRule>
										</cms:SystemPublishRuleList>
									</table>
								</div>
							</td>
						</tr>
					</table>

					<div style="height:40px"></div>

					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:selectRule();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
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

<script type="text/javascript">



var api = frameElement.api, W = api.opener; 

function close()
{
	api.close();
}



function selectRule()
{
    var fs =  document.getElementsByName('selectRule');
    
    var check =false;
    
    var selectVal = '-1|';
    for(var i=0; i<fs.length;i++)
    {
      if(fs[i].checked==true)
      {
         selectVal = fs[i].value;
         check=true;
         break;
      }
    }
   
  
    	var idName = '${param.idName}';
    	
    	var ruleInfo = selectVal.split('|');
    	
    	var apiWin = '${param.apiId}';
    	
    	if('' == apiWin)
    	{
    		W.document.getElementById(idName).value = ruleInfo[0];
    		W.document.getElementById(idName+'Show').value = ruleInfo[1];
    	}
    	else
    	{
    		api.get(apiWin).document.getElementById(idName).value = ruleInfo[0];
    		api.get(apiWin).document.getElementById(idName+'Show').value = ruleInfo[1];
    	}
    	
    	
    	
    	  	
    	close();
    
    
   
    
}

function radioClick(divNode)
{
	
    var entry = divNode.id;
    
    var fs =  document.getElementsByName('selectFile');
    
    var check =false;
    
    for(var i=0; i<fs.length;i++)
    {
      if(fs[i].value==entry)
      {
         
        fs[i].click();
         break;
      }
    }

}








</script>

