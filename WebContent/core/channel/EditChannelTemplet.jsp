<%@ page contentType="text/html; charset=utf-8" session="false"%>

<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <base target="_self" />
		<%
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		%>
		<title>编辑模板　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　</title>

		<script type="text/javascript" src="../javascript/commonUtil.js"></script>
		

		<!--加载 js -->
       <style type="text/css">

TEXTAREA
{


font-family:"宋体";
	font-size: 14px;

}
</style>
	</head>



	<body>
		<button type="button" onclick="javascript:previewTemplet();">
			<img src="../../core/style/default/images/add-act.png" alt="" />
			预览
		</button>
		&nbsp;
		<button type="button" onclick="javascript:saveTempletContent();">
			<img src="../../core/style/default/images/add-act.png" alt="" />
			保存
		</button>
		&nbsp;
		<h3>
			<font color=red>${param.name}</font>
		</h3>
		<form method="post" action='../../templet/writeTempletFile.do?from=channel' name='writeTempletFrom' id='writeTempletFrom'>

			<div align="center">
				<cms:TempletContent entry='${param.entry}'>
				<textarea id="content" name="content" class="f_text" cols="170" rows="${template.lineCount+150}">${template.content}</textarea>
				</cms:TempletContent>
			</div>




			<input type="hidden" id="templetFileName" name="templetFileName"  value="${param.name}"/>
			<input type="hidden" name="tcontent" id="tcontent"/>
			<input type="hidden" name="entry" id="entry" value="${param.entry}"/>
			<input type="hidden" name="prevEntry" id="prevEntry" value="${param.prevEntry}"/>
			
		</form>
	</body>

</html>
<script type="text/javascript">

                                                  // var editor1 = new FCKeditor('content');
                                                  // editor1.BasePath = "fckeditor/";
                                                   //editor1.Height = 500 ;
                                                  //editor1.Config["EnterMode"] = "br" ;
                                                   //editor1.ReplaceTextarea() ;   
                                                   
                                                  
                                                   
                                                   
                                                   
                                       </script>
<script type="text/javascript">
function enterFolder(entry)
{
	entryAction("ManagerAllTemplet.jsp",entry);
}

function saveTempletContent()
{

	//alert(document.getElementById('content').value);
	//document.getElementById("entry").value="${param.entry}";
	document.getElementById("tcontent").value=document.getElementById("content").value;
    document.getElementById("writeTempletFrom").submit();
}


function previewTemplet()
{
  window.showModelessDialog("../templet/PreviewTemplet.jsp?entry="+"${param.entry}","","dialogWidth=1200px;dialogHeight=600px;status=no");
	//window.showModalDialog("PreviewTemplet.jsp?entry="+"${param.entry}","","dialogWidth=1200px;dialogHeight=600px;status=no");
	//document.location.href="PreviewTemplet.jsp?entry="+"${param.entry}";

}







function submitTemplet()
{  
   

if(document.all.uploadFrame.contentWindow.document.getElementById("templetFileName").value=='')
{
alert("没有上传任何模板");
return;
}
   document.all.templetFileName.value=document.all.uploadFrame.contentWindow.document.getElementById

("templetFileName").value;


  
   templetForm.submit();

}

</script>
