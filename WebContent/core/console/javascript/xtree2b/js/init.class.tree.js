///////////////////ajax ////////

var targetJSONsrc = '../../channel/listContentClassInfoTree.do';
//var targetListContentSrc =item.targetListPage+'?classId=';

var parentId = -9999;//暂时没有分站，永远为总站-9999
var tree = new WebFXTree('总站');

tree.icon = "images/monitor.png";
tree.openIcon="images/monitor.png";
tree.setId(-9999);
tree.target= 'main';
tree.setAction("javascript");
tree.thisNodeLoadComplete=true;

current = tree;

var commend = new WebFXTreeItem('推荐位');

commend.icon = "images/monitor.png";
commend.openIcon="images/monitor.png";
commend.setId(-999);
commend.target= 'main';
commend.setAction("javascript:window.location.href='"+basepath+"core/content/ManageCommendContent.jsp");

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
			      					x.setAction(item.targetListPage+"?classId="+item.classId+"&modelId="+item.contentModelId);
			      					x.target= 'main';
			      					
			      					
			      					
			      					//如果不是叶子节点，需要为他创建一个loading节点
			      					
			      					if(item.isLeaf == 0)//有孩子的节点
			      					{
			      						var b = new WebFXTreeItem('Loading...');
										b.openIcon = b.icon = "images/loading.gif";  
										
										//x.openIcon="../../../style/icon/"+item.ico;
			      						//x.icon="../../../style/icon/"+item.ico;
										
										x.add(b);  
										//FF的bug，须调用方法收起树
										x.setExpanded();
										
															
			      					}
			      					else
			      					{
			      						
			      						x.openIcon="../../../core/style/icons/"+item.ico;
			      						x.icon="../../../core/style/icons/"+item.ico;
			      					}
			      					
			      					current.add(x);
			      				
			      				}	      			
			      			);
			      			
			      			     			
			      		 
		        }
});	

	
	
tree.write();

tree.expand();