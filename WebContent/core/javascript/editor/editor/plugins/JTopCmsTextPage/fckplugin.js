//业务代码
function disposeTextPage()
{
 	FCKUndo.SaveUndoStep();
 
    window.parent.showTextPageDialog(FCK.Name);
  
	FCKUndo.Typing=true;
}
 
var FCKJTCmsTextPageCommand=function() 
{
	this.Name='JTopCmsTextPage';
};

FCKJTCmsTextPageCommand.prototype.Execute=function()
{
	disposeTextPage();
}

FCKJTCmsTextPageCommand.prototype.GetState=function()
{
	return FCK_TRISTATE_OFF;
} ;

// 注册命令
FCKCommands.RegisterCommand( 'JTopCmsTextPage',new FCKJTCmsTextPageCommand()) ;


//定义工具栏 
 var JTCmsBar=new FCKToolbarButton('JTopCmsTextPage',FCKLang.JTopCmsTextPage ); 
 JTCmsBar.IconPath=FCKPlugins.Items['JTopCmsTextPage'].Path+'page_text.png'; 
 //注册 
 FCKToolbarItems.RegisterItem('JTopCmsTextPage',JTCmsBar); 
 
 

