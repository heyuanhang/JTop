//业务代码
 function disposeImageCut()
{
 	FCKUndo.SaveUndoStep();
 
 	window.parent.showDisposeImageDialog(FCK.Name);

	FCKUndo.Typing=true;
}
 
var FCKJTCmsDisposeImageCommand=function() 
{
	this.Name='JTopCmsDisposeImage';
};

FCKJTCmsDisposeImageCommand.prototype.Execute=function()
{
	disposeImageCut();
}

FCKJTCmsDisposeImageCommand.prototype.GetState=function()
{
	return FCK_TRISTATE_OFF;
} ;

// 注册命令
FCKCommands.RegisterCommand( 'JTopCmsDisposeImage',new FCKJTCmsDisposeImageCommand()) ;


//定义工具栏 
 var JTCmsBar=new FCKToolbarButton('JTopCmsDisposeImage',FCKLang.JTopCmsDisposeImage ); 
 JTCmsBar.IconPath=FCKPlugins.Items['JTopCmsDisposeImage'].Path+'image-resize.png'; 
 //注册 
 FCKToolbarItems.RegisterItem('JTopCmsDisposeImage',JTCmsBar); 
 
 
 
 

