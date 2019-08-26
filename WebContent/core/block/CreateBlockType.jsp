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
		<script type="text/javascript" src="../style/blue/js/tab_change.js"></script>
		<script>  
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            	W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '160px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    				
			                    content: '新增区块类型成功!',
	
			    				ok: function()
			    				{
			    					W.location.reload();
			    				}
			   });     
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('blockTypeName',0,ref_name,'格式为文字,数字,下划线');
 				
		 })
    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/layers-stack-arrange-back.png" width="16" height="16" />分类信息
					</div>
					

					<form id="blockForm" name="blockForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="23%" class="input-title">
									<strong>类别名称</strong>
								</td>
								<td  class="td-input">
									<input id="blockTypeName" name="blockTypeName" type="text" class="form-input" style="width:294px;" />
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>
							
							<tr>
								<td width="20%" class="input-title">
									<strong>目标模板</strong>
								</td>
								<td width="80%" class="td-input">
									<input id="templateUrl" name="templateUrl" type="text" class="form-input" style="width:294px;"  />
									<input type="button" value="模板..." class="btn-1" onclick="javascript:openSelectTempletDialog('block');" />

								</td>
							</tr>

						
						</table>
						<!-- hidden -->
						
						<cms:Token mode="html"/>
						
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
					<table width="100%" border="0" cellspacing="0" cellpadding="0"  >
						<tr class="btnbg100">
							<div style="float:right">
								<a name="btnwithicosysflag" href="javascript:submitBlockForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
								<a href="javascript:close();" class="btnwithico" ><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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
   //showTips('modelName','不可为空');
   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   
   
function submitBlockForm()
{
   var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('blockTypeName',0,ref_name,'格式为文字,数字,下划线');
        
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
   
   encodeFormInput('blockForm', false);
	
   var blockForm = document.getElementById('blockForm');
   blockForm.action="<cms:BasePath/>block/createBlockType.do";
   blockForm.submit();
}

   
function openSelectTempletDialog(mode)
{
      var targetName = '';
      
      if('channel' == mode)
      {
      	targetName = '栏目首页';
      }
      else if('class' == mode)
      {
      	targetName = '列表页';
      }
      else if('block' == mode)
      {
      	targetName = '区块页';
      }
       
	  W.$.dialog({ 
		id:'ostd',
    	title :'选择'+targetName+'模版',
    	width: '700px', 
    	height: '466px', 
    	parent:api,
    	lock: true, 
        max: false, 
         min: false, 
       
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/channel/SelectChannelTemplet.jsp?mode='+mode+'&apiId=ocbtd'
	});
}
  
</script>
