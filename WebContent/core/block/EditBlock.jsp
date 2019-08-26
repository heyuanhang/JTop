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
			    				width: '160px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    				
			                    content: '编辑区块成功!',
	
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
		    validate('blockName',0,ref_name,'格式为文字,数字,下划线');
 			validate('flag',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/layers-group.png" width="16" height="16" />配置
					</div>
					<cms:SystemBlockInfo blockId="${param.blockId}">
						<form id="blockForm" name="blockForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">


								<tr>
									<td width="24%" class="input-title">
										<strong>所属页: 
									</td>
									<td width="80%" class="td-input">
										<select id="parentId" name="parentId" class="form-select"  style="width:329px">
											<option value="-1">
												------------------- 请选择来源页面 ------------------
											</option>
											<cms:SystemBlockType>
												<option value="${BlockType.blockTypeId}">
													${BlockType.blockTypeName}
												</option>
											</cms:SystemBlockType>
										</select>
									</td>
								</tr>
								<tr>
									<td width="20%" class="input-title">
										<strong>区块名称: 
									</td>
									<td width="80%" class="td-input">
										<input  id="blockName" name="blockName" type="text" class="form-input"  style="width:325px" value="${TreeItem.blockName}" />
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>访问标识: 
									</td>
									<td class="td-input">
										<input disabled id="flag" name="flag" type="text" class="form-input"  style="width:325px" value="${TreeItem.flag}" />
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>
								<tr id="trSeg">
									<td class="input-title">
										<strong>发布频率:</strong>
									</td>
									<td class="td-input">
										每隔
										<input type="text" id="period" name="period" size="4" class="form-input" value="${TreeItem.period}" />
										<select id="periodType" name="periodType" class="form-select">
											<option value="2" selected>
												分钟 &nbsp;&nbsp;&nbsp;
											</option>
											<option value="3">
												小时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</option>
											<option value="4">
												天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</option>
										</select>
										发布一次 <span class="ps">(0值为手动更新模式)</span>
									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>区块模板:</strong>
									</td>
									<td class="td-input">
										<input id="templateUrl" name="templateUrl" type="text" class="form-input" size="50" value="${TreeItem.templateUrl}" />
										<input type="button" value="模板..." class="btn-1" onclick="javascript:openSelectTempletDialog('block');" />

									</td>

								</tr>

								<tr>
									<td class="input-title">
										<strong>描叙:</strong>
									</td>
									<td class="td-input">
										<textarea id="blockDesc" name="blockDesc" class="form-textarea" style="width:325px; height:50px;">${TreeItem.blockDesc}</textarea>
								</tr>

								<%--<tr>
									<td class="input-title">
										<strong>类型:</strong>
									</td>
									<td class="td-input">
										<input name="type" type="radio" value="1" />
										<span class="STYLE12">模板区块</span> &nbsp;
										<input name="type" type="radio" value="2" />
										<span class="STYLE12">手动推送区块</span>
									</td>

								</tr>
							--%></table>
							<!-- hidden -->
							<input id="blockId" name="blockId" type="hidden" value="${param.blockId}" />
							
							<input id="flag" name="flag" type="hidden" value="${TreeItem.flag}" />
							
							<input id="jobId" name="jobId" type="hidden" value="${TreeItem.jobId}"/>
							
							<input id="type" name="type" type="hidden" value="1"/>
							
							<cms:Token mode="html"/>
									
					</form>
						<script>  		
						initSelect('parentId','${TreeItem.parentId}');
						initSelect('periodType','${TreeItem.periodType}');
						initRadio('type','${TreeItem.type}');
					</script>
					</cms:SystemBlockInfo>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
					<table width="100%" border="0" cellspacing="0" cellpadding="0"  >
						<tr class="btnbg100">
							<div style="float:right">
								<a href="javascript:submitBlockForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
								<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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
		    				
		                    content: '请选择一个来源页面!',

		    				cancel: true
		});
		
		return;
    }
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('flag',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('blockName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;

	}
	
	encodeFormInput('blockForm', false);
	
   var blockForm = document.getElementById('blockForm');
   blockForm.action="../../block/editBlock.do";
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
             
        content: 'url:<cms:BasePath/>core/channel/SelectChannelTemplet.jsp?mode='+mode+'&apiId=oebd'
	});
}
  
</script>
