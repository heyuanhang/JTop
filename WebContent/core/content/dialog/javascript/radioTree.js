
//栏目数风格图标
webFXTreeConfig.folderIcon=basepath+"/core/javascript/tree/xtree2b/images/folder-horizontal.png";
webFXTreeConfig.openFolderIcon=basepath+"/core/javascript/tree/xtree2b/images/folder-horizontal-open.png";

var targetJSONsrc = "../../../channel/listContentClassInfoTree.do";

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
			      					
			      					x.radioVal = item.name;
			      					
			      					//if(currentRefClassIdStr.indexOf(item.name+',') != -1)
			      					//{
			      						//x.boxCheck = true;						
			      					//}
			      					
			      					//相同模型栏目才可选择
			      					if(item.modelId != targetModelId || selfClassId == item.classId || item.haveRole == false)
			      					{
			      						x.radioNotUse = true;
			      					}
			      					//x.setAction("ListContent.jsp?classId="+item.classId+"&modelId="+item.contentModelId);
			      					//x.target= 'nestContentFrame';
			      					//如果不是叶子节点，需要为他创建一个loading节点
			      					
			      					if(item.isLeaf == 0)//有孩子的节点
			      					{
			      						var b = new WebFXTreeItem('Loading...');
										b.openIcon = b.icon = basepath+"/core/javascript/tree/xtree2b/images/loading.gif";   							
										x.add(b);  	
										//FF的bug，须调用方法收起树
										//x.setExpanded();					
			      					}
			      					else
			      					{
			      						x.openIcon=basepath+"/core/style/icon/"+item.iconFile;
		      							x.icon=basepath+"/core/style/icon/"+item.iconFile;
			      					}
			      					
			      					current.add(x);
			      					
			      					loadDataOver = true;
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