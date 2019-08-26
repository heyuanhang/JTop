<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>tree</title>


		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />

		<link type="text/css" rel="stylesheet" href="../javascript/tree/xtree2b/css/xtree2.links.class.css">
		<script type="text/javascript" src="../common/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="../javascript/tree/xtree2b/js/xtree2.js"></script>

		<style type="text/css">

   body
   { 
   padding-top: 0px;
   	padding-left: 8px;
    margin-top: 0px;
    margin-left: 4px;
    
   }
   
   

</style>

	</head>

	<body>
		<cms:CurrentSite>
			<script type="text/javascript">
		var targetJSONsrc = "<cms:BasePath/>channel/listContentClassInfoTree.do?mode=channel&parent=-9999&layer=2";
		var targetListContentSrc ='<cms:BasePath/>core/channel/EditContentClass.jsp?classId=';
		var targetListSpecialSrc ='<cms:BasePath/>core/channel/EditSpecial.jsp?classId=';
		
		var targetListSiteSrc ='<cms:BasePath/>core/channel/EditSite.jsp?siteId=${CurrSite.siteId}'+"&random="+Math.random();
		var targetFrame = 'nestContentFrame';

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
					      					 
					      					 x = new WebFXTreeItem(item.name);
					      				
					      					//x.setAction("#");		      					
					      					x.setId(item.classId);
					      					
					      					if(item.classType  == 4)
					      					{
					      						x.setAction(targetListSpecialSrc+item.classId+"&parent="+item.parent+"&random="+Math.random());			      				
					      					}
					      					else
					      					{
					      						x.setAction(targetListContentSrc+item.classId+"&parent="+item.parent+"&random="+Math.random());			      				
					      					}
					      					
					      					x.target= targetFrame;
					      					//如果不是叶子节点，需要为他创建一个loading节点
					      					
					      							
					      					if(item.isLeaf == 0)//有孩子的节点
					      					{
					      						var b = new WebFXTreeItem('Loading...');
												b.openIcon = b.icon = "images/loading.gif";  							
												x.add(b);  	
												//FF的bug，须调用方法收起树
												//x.setExpanded();	
												if(!item.haveRole)
									      		{
									      			x.openIcon="<cms:BasePath/>core/style/icon/document--exclamation.png";
						      						x.icon="<cms:BasePath/>core/style/icon/document--exclamation.png";	
									      		}	
									      		else
									      		{
									      			x.openIcon="<cms:BasePath/>core/style/icons/"+item.ico;
		      										x.icon="<cms:BasePath/>core/style/icons/"+item.ico;	
									      		}			
					      					}
					      					else
					      					{		      						
					      						if(item.haveRole)
							      				{
							      					x.openIcon="<cms:BasePath/>core/style/icons/"+item.ico;
		      										x.icon="<cms:BasePath/>core/style/icons/"+item.ico; 
							      				}
							      				else
							      				{
							      					x.openIcon="<cms:BasePath/>core/style/icon/document--exclamation.png";
						      						x.icon="<cms:BasePath/>core/style/icon/document--exclamation.png";
							      				}
				      					
					      					}
					      					
					      					if(item.classType  == 4)
						      				{
						      					x.openIcon="<cms:BasePath/>core/style/icon/folder_table.png";
				      							x.icon="<cms:BasePath/>core/style/icon/folder_table.png";
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

	///////////////////ajax ///////////////////




var parentId = -9999;//暂时没有分站，永远为总站-9999
var tree = new WebFXTree('${CurrSite.siteName}');

tree.icon = "images/monitor.png";
tree.openIcon="images/monitor.png";
tree.setId(-1);
tree.target= targetFrame;
tree.setAction(targetListSiteSrc);
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
										
										if(item.classType  == 4)
									    {
									      	x.setAction(targetListSpecialSrc+item.classId+"&parent="+item.parent+"&random="+Math.random());			      				
									    }
									    else
									    {
									      	x.setAction(targetListContentSrc+item.classId+"&parent="+item.parent+"&random="+Math.random());			      				
									    }
									      					
										x.target= targetFrame;
										
									
										//如果不是叶子节点，需要为他创建一个loading节点
										if(item.isLeaf == 0)//有孩子的节点
										{
											//var b = new WebFXTreeItem('Loading...');
											//b.openIcon = b.icon = "images/loading.gif";  
											
											//x.add(b);
											
											
											
											  
											//FF的bug，须调用方法收起树
											x.setExpanded();
											
											if(!item.haveRole)
										    {
												x.openIcon="<cms:BasePath/>core/style/icon/document--exclamation.png";
									      		x.icon="<cms:BasePath/>core/style/icon/document--exclamation.png";	     					
											}
											else
										    {
												x.openIcon="<cms:BasePath/>core/style/icons/"+item.ico;
					      						x.icon="<cms:BasePath/>core/style/icons/"+item.ico;
										    }	
																
										}
										else
										{			      	
											x.openIcon="<cms:BasePath/>core/style/icons/"+item.ico;
					      					x.icon="<cms:BasePath/>core/style/icons/"+item.ico;										
										}
										
										if(item.classType  == 4)
										{
										    x.openIcon="<cms:BasePath/>core/style/icon/folder_table.png";
								      		x.icon="<cms:BasePath/>core/style/icon/folder_table.png";
										}
										
										current.add(x);
										
										rootClassNode = x;
									}
									else if(item.layer == 2)//次级栏目
					      			{
					      				xl2 = new WebFXTreeItem(item.name);
										xl2.setId(item.classId);
										
										if(item.classType  == 4)
									    {
									      	xl2.setAction(targetListSpecialSrc+item.classId+"&parent="+item.parent+"&random="+Math.random());			      				
									    }
									    else
									    {
									      	xl2.setAction(targetListContentSrc+item.classId+"&parent="+item.parent+"&random="+Math.random());			      				
									    }
									      					
										xl2.target= targetFrame;
										
									
										//如果不是叶子节点，需要为他创建一个loading节点
										if(item.isLeaf == 0)//有孩子的节点
										{
											var b = new WebFXTreeItem('Loading...');
											b.openIcon = b.icon = "images/loading.gif";  
											
											xl2.add(b);
											
											if(!item.haveRole)
											{
												  xl2.openIcon="<cms:BasePath/>core/style/icon/folder-stand.png";
									      		  xl2.icon="<cms:BasePath/>core/style/icon/folder-stand.png";
											}
											else
										    {
												  xl2.openIcon="<cms:BasePath/>core/style/icons/"+item.ico;
					      						  xl2.icon="<cms:BasePath/>core/style/icons/"+item.ico;
										    }	
											
											  
											//FF的bug，须调用方法收起树
											xl2.setExpanded();
																
										}
										else
										{			      	
											if(item.haveRole)
					  						{
					  							xl2.openIcon="<cms:BasePath/>core/style/icons/"+item.ico;
					      						xl2.icon="<cms:BasePath/>core/style/icons/"+item.ico;	
					  						}
					  						else
					  						{
					  							xl2.openIcon="<cms:BasePath/>core/style/icon/document--exclamation.png";
												xl2.icon="<cms:BasePath/>core/style/icon/document--exclamation.png";	
					  						}																							
										}
										
										
										
										if(item.classType  == 4)
										{
										    xl2.openIcon="<cms:BasePath/>core/style/icon/folder_table.png";
								      		xl2.icon="<cms:BasePath/>core/style/icon/folder_table.png";
										}
										
										//current.add(x);	
										
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

</script>
		</cms:CurrentSite>

		<br />
		<br />
	</body>


</html>
