///////////////////ajax ///////////////////

var targetJSONsrc = '../../resources/listSiteFolderTree.do';
var targetListContentSrc ='../../core/resources/ListFileRes.jsp';
var targetListSiteSrc ='../../core/resources/ListFileRes.jsp';
var targetFrame = 'nestContentFrame';

var folder = '';//暂时没有分站，永远为总站-9999
var tree = new WebFXTree('站点目录');

tree.icon = "images/monitor.png";
tree.openIcon="images/monitor.png";
tree.setId(-1);
tree.target= targetFrame;
tree.setAction(targetListSiteSrc);
tree.thisNodeLoadComplete=true;

current = tree;
$.getJSON
(
	targetJSONsrc, 
	{
		parentFolder : folder,
		mode : 'folder' //强制目录模式
	},
	function(data)
	{
		var x;
		
		$.each
		(
			data,
			function(i,item)
			{
				x = new WebFXTreeItem(item.fileName);
				x.setId(item.entry);
				x.setAction(targetListContentSrc+"?parentFolder="+item.entry);
				x.target= targetFrame;
				
				
				
				//如果不是叶子节点，需要为他创建一个loading节点
				if(item.isDir)//有孩子的节点
				{
					//alert(item.haveDir+' : '+item.fileName);
					if(item.haveDir)
					{
						var b = new WebFXTreeItem('Loading...');
						b.openIcon = b.icon = "images/loading.gif";  
						
						x.openIcon="images/dir.gif";
						x.icon="images/dir.gif";
						
						x.add(b);  
					}
					else
					{
						//x.openIcon="images/folder-horizontal.png";
						//x.icon="images/folder-horizontal.png";
						x.openIcon="images/dir.gif";
						x.icon="images/dir.gif";
					}
					
					//FF的bug，须调用方法收起树								
					x.setExpanded();					
				}
							
				current.add(x);
			
			}	      			
		);
		
		
	}
 );

	
tree.write();

tree.expand();