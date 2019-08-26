//业务代码
function disposeOffice()
{
 	FCKUndo.SaveUndoStep();

   window.parent.showOfficeDialog(FCK.Name);

	FCKUndo.Typing=true;

}
 
var FCKJTCmsOfficeCommand=function() 
{
	this.Name='JTopCmsOffice';
};

FCKJTCmsOfficeCommand.prototype.Execute=function()
{
	disposeOffice();
}

FCKJTCmsOfficeCommand.prototype.GetState=function()
{
	return FCK_TRISTATE_OFF;
} ;

// 注册命令
FCKCommands.RegisterCommand( 'JTopCmsOffice',new FCKJTCmsOfficeCommand()) ;


//定义工具栏 
 var JTCmsBar=new FCKToolbarButton('JTopCmsOffice',FCKLang.JTopCmsOffice ); 
 JTCmsBar.IconPath=FCKPlugins.Items['JTopCmsOffice'].Path+'Office.png'; 
 //注册 
 FCKToolbarItems.RegisterItem('JTopCmsOffice',JTCmsBar); 
 
 

