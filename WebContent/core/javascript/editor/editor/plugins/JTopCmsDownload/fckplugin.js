//业务代码
 function disposeDownload()
{
 	FCKUndo.SaveUndoStep();
 	
 	window.parent.showDownloadDialog(FCK.Name);
 	
	FCKUndo.Typing=true;
}
 
var FCKJTCmsDownloadCommand=function() 
{
	this.Name='JTopCmsDownload';
};

FCKJTCmsDownloadCommand.prototype.Execute=function()
{
	disposeDownload();
}

FCKJTCmsDownloadCommand.prototype.GetState=function()
{
	return FCK_TRISTATE_OFF;
} ;

// 注册命令
FCKCommands.RegisterCommand( 'JTopCmsDownload',new FCKJTCmsDownloadCommand()) ;


//定义工具栏 
 var JTCmsBar=new FCKToolbarButton('JTopCmsDownload',FCKLang.JTopCmsDownload ); 
 JTCmsBar.IconPath=FCKPlugins.Items['JTopCmsDownload'].Path+'download.png'; 
 //注册 
 FCKToolbarItems.RegisterItem('JTopCmsDownload',JTCmsBar); 
 
 
 
 

