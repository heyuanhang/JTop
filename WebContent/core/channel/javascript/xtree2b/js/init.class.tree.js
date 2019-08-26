///////////////////ajax ///////////////////

var targetJSONsrc = '../../channel/listContentClassInfoTree.do';
var targetListContentSrc ='../../core/channel/EditContentClass.jsp?classId=';
var targetListSiteSrc ='../../core/channel/EditSite.jsp?siteId=';
var targetFrame = 'nestContentFrame';

var parentId = -9999;//暂时没有分站，永远为总站-9999
var tree = new WebFXTree('总站');

tree.icon = "images/monitor.png";
tree.openIcon="images/monitor.png";
tree.setId(-1);
tree.target= targetFrame;
tree.setAction(targetListSiteSrc+'1');
tree.thisNodeLoadComplete=true;

current = tree;

$.ajax({
		  		type: "POST",
		   		 url: targetJSONsrc,
		   		data: 'parentClassId='+parentId,
		
		       	success: function(msg)
		        {     
		       				var jsonObj = eval("("+msg+")");
		           			var x;
		
							$.each
							(
								jsonObj,
								function(i,item)
								{
									x = new WebFXTreeItem(item.name);
									x.setId(item.classId);
									x.setAction(targetListContentSrc+item.classId+"&parent="+item.parent);
									x.target= targetFrame;
									
									
									
									//如果不是叶子节点，需要为他创建一个loading节点
									if(item.isLeaf == 0)//有孩子的节点
									{
										var b = new WebFXTreeItem('Loading...');
										b.openIcon = b.icon = "images/loading.gif";  
										
										x.openIcon="../../core/style/icons/"+item.ico;
					      				x.icon="../../core/style/icons/"+item.ico;
										
										x.add(b);  
										//FF的bug，须调用方法收起树
										x.setExpanded();
															
									}
									else
									{
										
										x.openIcon="../../core/style/icons/"+item.ico;
					      				x.icon="../../core/style/icons/"+item.ico;
										
									}
									
									current.add(x);
								
								}	      			
							);
			      			
			      			     			
			      		 
		        }
});	


 

	
tree.write();

tree.expand();