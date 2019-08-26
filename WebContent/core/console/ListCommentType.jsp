<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />


		<title>menu</title>
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<link href="../style/blue/images/left.css" rel="stylesheet" type="text/css" />
		<link type="text/css" rel="stylesheet" href="../javascript/tree/xtree2b/css/xtree2.links.css"></link>

		<script type="text/javascript" src="../common/js/jquery-1.6.2.min.js"></script>

		<script type="text/javascript" src="../javascript/tree/xtree2b/js/xtree2.js"></script>


		<script>
	 	//去掉点击链接 虚线边框
        window.onload=function()
        {
     		for(var i=0; i<document.links.length; i++)
     		document.links[i].onclick=function(){this.blur()}
        }
        
        function loadChildTree() {
        //必须放置一个空方法
        }
	 
      </script>


		<style type="text/css">
html{
scrollbar-3dlight-color:#fff; /*- 最外左 -*/  
scrollbar-highlight-color:#fff; /*- 左二 -*/  
scrollbar-face-color:#DCE7F5; /*- 面子 -*/  
scrollbar-arrow-color:#407DCA; /*- 箭头 -*/  
scrollbar-shadow-color:#efefef; /*- 右二 -*/  
scrollbar-darkshadow-color:#E9EDF8; /*- 右一 -*/  
scrollbar-base-color:#D7DCE0; /*- 基色 -*/  
scrollbar-track-color:#fcfcfc;/*- 滑道 -*/  
 

	}
	body{ padding:0px;
	margin-left: 4px;
	}
</style>

	</head>

	<body class="menutree">

		<table width="100%" height="100%" align="left" border='0' cellspacing='0' cellpadding='0'>
			<tr>
				<td>
					<div>
					
						
					
						<cms:CurrentSite>
							<script type="text/javascript">
			basepath="<cms:Domain/>";
				///////////////////ajax ////////

			var targetJSONsrc = '../../channel/listCommendTypeInfoTree.do';
			//var targetListContentSrc =item.targetListPage+'?classId=';
			
			var parentId = -9999;//暂时没有分站，永远为总站-9999
			var tree = new WebFXTree('${CurrSite.siteName}推荐位');
			
			tree.icon = "images/monitor.png";
			tree.openIcon="images/monitor.png";
			tree.setId(-9999);
			tree.target= 'main';
			tree.setAction("javascript:");
			tree.thisNodeLoadComplete=true;
			
			current = tree;
		 
			var currentClass;
			
			var classId = '';
			
			
			$.ajax({
		  		type: "POST",
		   		 url: targetJSONsrc,
		   		data: 'parentClassId='+parentId,
		
		       	success: function(msg)
		        {     
		       				var jsonObj = eval("("+msg+")");
		           			var x;
			      			
			      			var xc;
			      			
			      			$.each
			      			(
			      				jsonObj,
			      				function(i,item)
			      				{  
			      				
			      					if(item.rootClassId == '-1')
			      					{
			      						x = new WebFXTreeItem(item.rootClassName);
				      					x.setId('root'+i);
				      					
				      					x.openIcon="../../core/style/icons/folder-open.png";
				      					x.icon="../../core/style/icons/folder.png";
				      					

				      					x.expand();
				      					
				      					currentClass = x;
				      					
				      					current.add(x);
			      					
			      					}
			      					else
			      					{
				      					x = new WebFXTreeItem(item.commendName);
				      					x.setId("comm:"+item.commendTypeId);
				      					
				      					//如果为通用推荐位,则取站点第一个classId
				      					if(item.classId == '-9999')
				      					{
				      						classId = item.firstClassId;
				      					}
				      					else
				      					{
				      						classId = item.classId;
				      					}
				      					
				      					x.setAction('<cms:Domain/>core/content/ManageCommendContent.jsp?typeId='+item.commendTypeId+'&classId='+classId);
				      					x.target= 'main';
				      					
				      					
				      					
				      					//如果不是叶子节点，需要为他创建一个loading节点
				      					
				      					
				      						
				      						x.openIcon="../../core/style/icons/document-text-image.png";
				      						x.icon="../../core/style/icons/document-text-image.png";
				      						
				      						currentClass.add(x);
			      					}
			      					
			      					
			      				
			      				}	      			
			      			);
			      			
			      			     			
			      		 
		        }
		 	});	
			
			
			 
			tree.write();
			
			tree.expand();
			
			
			

function manageCommendInfo()
{
	window.parent.parent.document.getElementById('main').src="<cms:Domain/>core/content/ManageCommendContent.jsp";
 
}

function manageBlockInfo()
{
	window.parent.parent.document.getElementById('main').src="<cms:Domain/>core/block/ManageBlockPushContent.jsp";
}



			
		</script>
						</cms:CurrentSite>
						<br />
						<br />
						<br />




























					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
