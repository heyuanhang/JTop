//业务代码
function disposeFormat()
{
 	FCKUndo.SaveUndoStep();

   window.parent.showFormatDialog(FCK.Name);
   
  
	FCKUndo.Typing=true;

}
 
var FCKJTCmsFormatCommand=function() 
{
	this.Name='JTopCmsFormat';
};

FCKJTCmsFormatCommand.prototype.Execute=function()
{
	disposeFormat();
}

FCKJTCmsFormatCommand.prototype.GetState=function()
{
	return FCK_TRISTATE_OFF;
} ;

// 注册命令
FCKCommands.RegisterCommand( 'JTopCmsFormat',new FCKJTCmsFormatCommand()) ;


//定义工具栏 
 var JTCmsBar=new FCKToolbarButton('JTopCmsFormat',FCKLang.JTopCmsFormat ); 
 JTCmsBar.IconPath=FCKPlugins.Items['JTopCmsFormat'].Path+'format.png'; 
 //注册 
 FCKToolbarItems.RegisterItem('JTopCmsFormat',JTCmsBar); 
 
 

