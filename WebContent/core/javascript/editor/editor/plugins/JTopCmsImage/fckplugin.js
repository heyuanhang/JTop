//业务代码
 function disposeImage()
{
 	FCKUndo.SaveUndoStep();
 
 	window.parent.showImageDialog(FCK.Name);

	FCKUndo.Typing=true;
}
 
var FCKJTCmsImageCommand=function() 
{
	this.Name='JTopCmsImage';
};

FCKJTCmsImageCommand.prototype.Execute=function()
{
	disposeImage();
}

FCKJTCmsImageCommand.prototype.GetState=function()
{
	return FCK_TRISTATE_OFF;
} ;

// 注册命令
FCKCommands.RegisterCommand( 'JTopCmsImage',new FCKJTCmsImageCommand()) ;


//定义工具栏 
 var JTCmsBar=new FCKToolbarButton('JTopCmsImage',FCKLang.JTopCmsImage ); 
 JTCmsBar.IconPath=FCKPlugins.Items['JTopCmsImage'].Path+'NewImage.gif'; 
 //注册 
 FCKToolbarItems.RegisterItem('JTopCmsImage',JTCmsBar); 
 
 
 
 

