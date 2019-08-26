<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<cms:SystemAdvertContent advertId="${param.advertId}">
<html>
	<head>
		<title>JTOPCMS-广告测试-${Advert.adName}</title>

		
	</head>

	<body>
		
			${Advert.advertCode}
		
	</body>
</html>

</cms:SystemAdvertContent>







