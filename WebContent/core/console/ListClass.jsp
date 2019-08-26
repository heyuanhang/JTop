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
	  
		var targetJSONsrc = "<cms:BasePath/>channel/listContentClassInfoTree.do";

		function loadChildTree() {
			
			//如果当前树是被选中状态
			if (tree.getSelected()) 
			{
				//获取当前的node
				var current = tree.getSelected();
				//如果此已经加载过一次，不允许重新加载
				if(!current.thisNodeLoadComplete)
				{
					var parentId = current.getId();
					
					$.ajax({
				  		type: "POST",
				   		 url: targetJSONsrc,
				   		data: 'parentClassId='+parentId,
				
				       	success: function(msg)
				        {     
				       				var jsonObj = eval("("+msg+")");
				           			var x;
					      			var loadDataOver = false;
					      			$.each
					      			(
					      				jsonObj,
					      				function(i,item)
					      				{
					      					//alert(item.name);
					      					 x = new WebFXTreeItem(item.name);
					      				
					      					//x.setAction("#");
					      					x.setId(item.classId);
					      					
					      				    if(item.classType == '3' )
											{
												if(item.contentId != null)
												{
													//编辑模式
													x.setAction(item.targetListPage+"?contentId="+item.contentId+"&classId="+item.classId+"&modelId="+item.contentModelId+"&uid="+Math.random());
												}
												else
												{
													x.setAction(item.targetListPage+"?classId="+item.classId+"&modelId="+item.contentModelId+"&uid="+Math.random());
					      						}
											}
											else
											{
												x.setAction(item.targetListPage+"?classId="+item.classId+"&modelId="+item.contentModelId+"&uid="+Math.random());
											}
											
					      					x.target= 'main';
					      					//如果不是叶子节点，需要为他创建一个loading节点
					      					
					      					if(item.isLeaf == 0)//有孩子的节点
					      					{
					      						if(!item.haveRole)
									      		{
									      			x.openIcon="../../core/style/icon/folder-stand.png";
						      						x.icon="../../core/style/icon/folder-stand.png";
									      		}
									      		
					      						var b = new WebFXTreeItem('Loading...');
												b.openIcon = b.icon = "images/loading.gif";  							
												x.add(b);  	
												//FF的bug，须调用方法收起树
												//x.setExpanded();					
					      					}
					      					else
					      					{				      					
					      						//x.openIcon="../../core/style/icon/"+item.iconFile;
				      							//x.icon="../../core/style/icon/"+item.iconFile;
				      							if(item.haveRole)
							      				{
							      					x.openIcon="../../core/style/icons/"+item.ico;
						      						x.icon="../../core/style/icons/"+item.ico;	 
							      				}
							      				else
							      				{
							      					x.openIcon="../../core/style/icon/document--exclamation.png";
						      						x.icon="../../core/style/icon/document--exclamation.png";
							      				}			
				      							
					      					}
					      					
					      					
					      					
					      					current.add(x);
					      					loadDataOver = true
					      				}	      			
					      			);
					      			
					      			//必须在接受到json数据且加载成功node的情况下才可删除当前选中节点的Load node
					      			if(loadDataOver)
					      			{
					      				current.remove(current.getFirstChild());     				
					      			}
					      			
					      			     			
					      		 
				        }
				 	});	

				  	 tree.getSelected().thisNodeLoadComplete =true;
				}
			}
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

		<table width="100%"    align="left" border='0' cellspacing='0' cellpadding='0'>
			<tr>
				<td>
				 
					
						
					
						<cms:CurrentSite>
							<script type="text/javascript">
			basepath="<cms:Domain/>";
				///////////////////ajax ////////

			var targetJSONsrc = '<cms:BasePath/>channel/listContentClassInfoTree.do?parent=-9999&layer=2';
			//var targetListContentSrc =item.targetListPage+'?classId=';
			
			var parentId = -9999;//暂时没有分站，永远为总站-9999
			var tree = new WebFXTree('${CurrSite.siteName}内容');
			
			tree.icon = "images/monitor.png";
			tree.openIcon="images/monitor.png";
			tree.setId(-9999);
			tree.target= 'main';
			tree.setAction("javascript:");
			tree.thisNodeLoadComplete=true;
			
			current = tree;
			
			var rootClassNode = null;
			
		    $.ajax({
		  		type: "POST",
		   		 url: targetJSONsrc,
		   		data: 'parentClassId='+parentId,
		
		       	success: function(msg)
		        {     
		       				var jsonObj = eval("("+msg+")");
		           			var x;
      						var xl2;
			      			
			      			$.each
			      			(
			      				jsonObj,
			      				function(i,item)
			      				{

			      					if(item.layer == 1)//根栏目
			      					{
					      					x = new WebFXTreeItem(item.name);
					      					x.setId(item.classId);
					      						
											if(item.classType == '3' )
											{
												if(item.contentId != null)
												{
													//编辑模式
													x.setAction(item.targetListPage+"?contentId="+item.contentId+"&classId="+item.classId+"&modelId="+item.contentModelId+"&uid="+Math.random());
												}
												else
												{
													x.setAction(item.targetListPage+"?classId="+item.classId+"&modelId="+item.contentModelId+"&uid="+Math.random());
					      						}
											}
											else
											{
												x.setAction(item.targetListPage+"?classId="+item.classId+"&modelId="+item.contentModelId+"&uid="+Math.random());
											}
					      					
					      					x.target= 'main';
					      					
					      					
					      					
					      					//如果不是叶子节点，需要为他创建一个loading节点
					      					
					      					if(item.isLeaf == 0)//有孩子的节点
					      					{
					      						//var b = new WebFXTreeItem('Loading...');
												//b.openIcon = b.icon = "images/loading.gif";  
																					
												//x.add(b);  
												//FF的bug，须调用方法收起树
												//x.setExpanded();
												if(!item.haveRole)
							      				{
							      					x.openIcon="../../core/style/icon/folder-stand.png";
				      								x.icon="../../core/style/icon/folder-stand.png";
							      				}
																					
					      					}
					      					else
					      					{				      					
					      						if(item.haveRole)
							      				{
							      					x.openIcon="../../core/style/icons/"+item.ico;
						      						x.icon="../../core/style/icons/"+item.ico;	 
							      				}
							      				else
							      				{
					      							x.openIcon="../../core/style/icon/document--exclamation.png";
				      								x.icon="../../core/style/icon/document--exclamation.png";
					      						}		
					      						     							
					      					}
					      					
					      					if(item.classType  == 4)
						      				{
						      					x.openIcon="../../core/style/icon/folder_table.png";
				      							x.icon="../../core/style/icon/folder_table.png";
						      				}
					      					
					      					current.add(x);
					      					
					      					rootClassNode = x;
			      					}
			      					else if(item.layer == 2)//次级栏目
      								{
      										xl2 = new WebFXTreeItem(item.name);
					      					xl2.setId(item.classId);
					      					
					      					if(item.classType == '3' )
											{
												if(item.contentId != null)
												{
													//编辑模式
													xl2.setAction(item.targetListPage+"?contentId="+item.contentId+"&classId="+item.classId+"&modelId="+item.contentModelId+"&uid="+Math.random());
												}
												else
												{
													xl2.setAction(item.targetListPage+"?classId="+item.classId+"&modelId="+item.contentModelId+"&uid="+Math.random());
					      						}
											}
											else
											{
												xl2.setAction(item.targetListPage+"?classId="+item.classId+"&modelId="+item.contentModelId+"&uid="+Math.random());
											}
					      					
					      					
					      					xl2.target= 'main';
					      					
					      					
					      					
					      					//如果不是叶子节点，需要为他创建一个loading节点
					      					
					      					if(item.isLeaf == 0)//有孩子的节点
					      					{
					      						if(!item.haveRole)
							      				{
							      					xl2.openIcon="../../core/style/icon/folder-stand.png";
				      								xl2.icon="../../core/style/icon/folder-stand.png";
							      				}
							      				
					      						var b = new WebFXTreeItem('Loading...');
												b.openIcon = b.icon = "images/loading.gif";  
																					
												xl2.add(b);  
												//FF的bug，须调用方法收起树
												xl2.setExpanded();
												
												
																		
					      					}
					      					else
					      					{				      				
					      						if(item.haveRole)
					      						{
					      							xl2.openIcon="../../core/style/icons/"+item.ico;
				      								xl2.icon="../../core/style/icons/"+item.ico;	
					      						}
					      						else
					      						{
					      							xl2.openIcon="../../core/style/icon/document--exclamation.png";
				      								xl2.icon="../../core/style/icon/document--exclamation.png";	
					      						}			
					      						      							
					      					}
					      					
					      					if(item.classType  == 4)
						      				{
						      					xl2.openIcon="../../core/style/icon/folder_table.png";
				      							xl2.icon="../../core/style/icon/folder_table.png";
						      				}
						      				
						      				//设定root栏目为已经加载过
				      						rootClassNode.thisNodeLoadComplete =true;
				      						rootClassNode.add(xl2);
				      						rootClassNode.expand();
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
