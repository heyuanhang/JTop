<%@ page contentType="text/html; charset=utf-8" session="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	
	<title>center</title>
	<link rel="stylesheet" type="text/css" href="../../style/default/console.css" />
	<script type="text/javascript" src="../common/js/jquery-1.5.1.min.js"></script>
</head>
<script>
$
(	
	function()
	{
		$('#switchPoint').click
		(
			function()
			{
				var cols = $(window.parent.document.all[id='centerFrame']).attr("cols");
				 
				if(cols=="171,9,*")
				{
					$(window.parent.document.all[id='centerFrame']).attr("cols","0,9,*");
					$('#switchPoint img').attr("src","images/f_1.jpg");
				}
				else if(cols=="0,9,*")
				{
					$(window.parent.document.all[id='centerFrame']).attr("cols","171,9,*");
					$('#switchPoint img').attr("src","images/f_1.jpg");
				}
		  	}
		);
	}
)

</script>
<table width="9" height="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
	<tr>
		<td width="9" style="width:9px;" valign="middle" bgcolor="#eaf7ff">
			<SPAN class=navPoint id=switchPoint title=关闭/打开左栏><img src="images/f_2.jpg" name="img1" width=9 height=58 id=img1> </SPAN>
		</td>
	</tr>
</table>



