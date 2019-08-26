function replace(str,targetStr,repStr) 
{ 
 	var i; 
 	var s2 = str; 
 
 	while(s2.indexOf(targetStr)>0) 
 	{ 
	     i = s2.indexOf(targetStr); 
	     s2 = s2.substring(0, i) + repStr + s2.substring(i + 2, s2.length); 
 	} 
 	return s2; 
} 


