//业务代码
 function disposeVideo()
{
 	FCKUndo.SaveUndoStep();

 	window.parent.showMediaDialog(FCK.Name);
 
	FCKUndo.Typing=true;
}
 
var FCKJTCmsVideoCommand=function() 
{
	this.Name='JTopCmsVideo';
};

FCKJTCmsVideoCommand.prototype.Execute=function()
{
	disposeVideo();
}

FCKJTCmsVideoCommand.prototype.GetState=function()
{
	return FCK_TRISTATE_OFF;
} ;

// 注册命令
FCKCommands.RegisterCommand( 'JTopCmsVideo',new FCKJTCmsVideoCommand()) ;


//定义工具栏 
 var JTCmsBar=new FCKToolbarButton('JTopCmsVideo',FCKLang.JTopCmsVideo ); 
 JTCmsBar.IconPath=FCKPlugins.Items['JTopCmsVideo'].Path+'NewVideo.png'; 
 //注册 
 FCKToolbarItems.RegisterItem('JTopCmsVideo',JTCmsBar); 
 
 
 
 

