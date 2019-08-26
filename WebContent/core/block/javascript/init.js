///////////////////ajax ////////

var targetJSONsrc = '../../channel/listContentClassInfoTree.do';
//var targetListContentSrc =item.targetListPage+'?classId=';

var parentId = -9999;//暂时没有分站，永远为总站-9999
var tree = new WebFXTree('总站');

tree.icon = basepath+"/core/javascript/tree/xtree2b/images/monitor.png";

tree.openIcon=basepath+"/core/javascript/tree/xtree2b/images/monitor.png";

tree.setId(-9999);
tree.target= 'nestContentFrame';
tree.setAction("javascript");
tree.thisNodeLoadComplete=true;

current = tree;

var commend = new WebFXTreeItem('推荐位');

commend.icon = "images/monitor.png";
commend.openIcon="images/monitor.png";
commend.setId(-999);
commend.target= 'nestContentFrame';
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
			      					x.setAction("ListContent.jsp?classId="+item.classId+"&modelId="+item.contentModelId);
			      					x.target= 'nestContentFrame';
			      					//x.radioNotUse = true;
			      					
			      					
			      					//如果不是叶子节点，需要为他创建一个loading节点
			      					
			      					if(item.isLeaf == 0)//有孩子的节点
			      					{
			      						var b = new WebFXTreeItem('Loading...');
										b.openIcon = b.icon = basepath+"/core/javascript/tree/xtree2b/images/loading.gif";   
										
										//x.openIcon="../../../style/icon/"+item.iconFile;
			      						//x.icon="../../../style/icon/"+item.iconFile;
										
										x.add(b);  
										//FF的bug，须调用方法收起树
										x.setExpanded();
										
															
			      					}
			      					else
			      					{
			      					
			      						
			      						x.openIcon = basepath+"/core/style/icon/"+item.iconFile;
			      						x.icon = basepath+"/core/style/icon/"+item.iconFile;
			      					}
			      					
			      					current.add(x);
			      		
			      				}	      			
			      			);
			      			
			      			     			
			      		 
		        }
});	
 
	
	
tree.write();

tree.expand();