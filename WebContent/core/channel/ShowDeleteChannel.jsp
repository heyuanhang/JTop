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
		var size = 0;  
      	var classIdMap = new HashMapJs();
      	
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	
            W.$.dialog.tips('删除成功...',1); 
            //api.close(); 
         	//api.reload( api.get('cwa') );        
       		//W.window.location.reload();
         }
         
         //表格变色
			$(function()
			{ 
		   		$("#showlist tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
			});  

		</script>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->


					<div style="height:4px"></div>
					<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

						<tr class="datahead">
							
							<td width="18%">
								<strong>&nbsp;&nbsp;栏目名称</strong>
							</td>

							<td width="10%">
								<center><strong>操作</strong></center>
							</td>
						</tr>
						<cms:CurrentSite>
							<cms:SystemClassList site="${CurrSite.siteFlag}" parentId="${param.classId}" showMode="false">

								<cms:SysClass >
									<tr>
										
										<td>
											&nbsp;&nbsp;${Class.className}
										</td>

										<td>
											<div align="center">
												<a href="javascript:submitDeleteAction('${Class.classId}', '${Class.className}');"><img src="../../core/style/default/images/del.gif" width="16" height="16" />删除</a>
											</div>
										</td>
									</tr>
									<script type="text/javascript">	
								size++;
                        		classIdMap.put(${status.index+1},"${Class.classId}");
                   				</script>
								</cms:SysClass>
							</cms:SystemClassList>
							<cms:Empty flag="Class">
								<tr>
									<td class="tdbgyew" colspan="7">
										<center>
											当前没有数据!
										</center>
									</td>
								</tr>
							</cms:Empty>
						</cms:CurrentSite>

					</table>

					<form method="post" id="classForm" name="classForm">
						
						<input type="hidden" id="parent" name="parent" value='${param.classId}' />
					</form>

					<div style="height:40px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16" /><b>关闭&nbsp;</b> </a>
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
 

  
function close()
{
	api.close();
	
}

function submitDeleteAction(id, name)
{
	W.$.dialog({ 
		 title :'提示',
		 width: '300px', 
		 height: '60px', 
         lock: true, 
         parent:api,
		 icon: '32X32/i.png', 
		 ok : function()
		 {
		 	W.deleteSucc = true;
		 	document.all.classForm.action='<cms:BasePath/>channel/deleteContentClass.do?childMode=true&classId='+id+"&<cms:Token mode='param'/>";
    	    document.all.classForm.submit();
		 },
		    				
		 content: '当前栏目 <font color="red">'+name+'</font> 和其所有子栏目的相关信息将被物理删除,无法恢复! 您确定执行吗?', 
		 cancel: true 
	});

     


}

</script>
