//业务代码
 function disposeMap()
{
 	FCKUndo.SaveUndoStep();
 
 	window.parent.showDisposeBaiduMapDialog(FCK.Name);

	FCKUndo.Typing=true;
}
 
var FCKJTCmsBaiduMapCommand=function() 
{
	this.Name='JTopCmsBaiduMap';
};

FCKJTCmsBaiduMapCommand.prototype.Execute=function()
{
	disposeMap();
}

FCKJTCmsBaiduMapCommand.prototype.GetState=function()
{
	return FCK_TRISTATE_OFF;
} ;

// 注册命令
FCKCommands.RegisterCommand( 'JTopCmsBaiduMap',new FCKJTCmsBaiduMapCommand()) ;


//定义工具栏 
 var JTCmsBar=new FCKToolbarButton('JTopCmsBaiduMap',FCKLang.JTopCmsBaiduMap ); 
 JTCmsBar.IconPath=FCKPlugins.Items['JTopCmsBaiduMap'].Path+'map.png'; 
 //注册 
 FCKToolbarItems.RegisterItem('JTopCmsBaiduMap',JTCmsBar); 
 
 
 
 

