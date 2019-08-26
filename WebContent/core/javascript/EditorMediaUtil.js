var base = '';

//图象上传
function showImageDialog(FCK)
{
  var paramMap=window.showModalDialog(base+"core/content/imageUpload.html","","dialogWidth=670px;dialogHeight=560px;status=no");
     
  //var imgGroup=document.getElementById("imageGroup").value;
  //alert(imgGroup);
 //alert("1222123123");
  if(typeof(paramMap) != 'undefined')
  {
   	   var oEditor = FCK;
   	   
	   if(oEditor.EditMode == FCK_EDITMODE_WYSIWYG)
	   {      
	   	  var type=paramMap["type"];
	      var imgSiteFile=paramMap["imgSiteFile"];
       	  var imgWebUrl=paramMap["imgWebUrl"]; 
       	  
           //alert(type);
       	  if(type=='resource')
       	  {
               var allImg=paramMap["allImg"].split("*");
               
               for(var i=0;i<allImg.length;i++)
               {
                  if(allImg[i] != '')
                  {
                     oEditor.InsertHtml("<div style='text-align: center'><img name='jtopcms_content_image' src='" + base+'UPLOAD/'+allImg[i] + "' alt='' />&nbsp</div><br>");          
                     //imgGroup+=allImg[i]+"|";
                  }
                         
               }
               
       	  }
       	  else if(type=='s')
       	  {
              oEditor.InsertHtml("<div style='text-align: center'><img name='jtopcms_content_image' src='" + base+'UPLOAD/'+imgSiteFile + "' alt='' />&nbsp</div><br>");    	    
              //imgGroup+=imgSiteFile+"|";
       	  }   
          else if(type=='w')
          {
              oEditor.InsertHtml("<div style='text-align: center'><img src='" + imgWebUrl + "' alt='' />&nbsp</div><br>");
          }  
          
          //alert(imgGroup);
          //document.getElementById("imageGroup").value=imgGroup;
	   }
	   
	   
	   
	   if(typeof(paramMap) != 'undefined')
	   {
	     
	   }
   }
	
}

//插入多媒体
function showMediaDialog(FCK)
{

  var returnMap=window.showModalDialog(base+"fckeditor/editor/plugins/JTopCmsVideo/videoUpload.html","","dialogWidth=550px;dialogHeight=430px;status=no");
    
  if(typeof(returnMap) != 'undefined')
  {
   	   var oEditor = FCK;
   	   
	   if(oEditor.EditMode == FCK_EDITMODE_WYSIWYG)
	   {      
	      //
        var fileName = returnMap['fileName'];
        var width = returnMap['width']; 
        var height = returnMap['height'];
        var autostart = returnMap['autoStart'];
        var allowfullscreen = returnMap['allowFullScreen'];
        var repeat = returnMap['repeatFlag'];
        var quality= returnMap['quality'];
        
        var x = '<object id="video" type="application/x-shockwave-flash" width="'+width+'" height="'+height+'"';
        x+= 'data='+base+'"core/extools/player/jwplayer/5.8/player.swf?file='+base+'VIDEO/'+fileName+'&autostart='+autostart+'&allowfullscreen='+allowfullscreen+'&repeat='+repeat+'">';
    
        x+= '<param name="movie" value="'+base+'core/extools/player/jwplayer/5.8/player.swf?file='+base+'VIDEO/'+fileName+'&autostart='+autostart+'&repeat='+repeat+'" />';
        x+= '<param name="quality" value="'+quality+'" />';
        x+= '<param name="allowfullscreen" value="'+allowfullscreen+'" />';
        x+= '<param name="strewoflength" value="2" />';
        x+= '</object>';
        //alert(x);
        oEditor.InsertHtml(x);
	      
	   }
	   
	   if(typeof(returnMap) != 'undefined')
	   {
	     
	   }
   }
	
}

//插入flash格式
function showFlashVideossDialog(FCK)
{

  var returnMap=window.showModalDialog(base+"fckeditor/editor/plugins/JTopCmsVideo/videoUpload.html","","dialogWidth=550px;dialogHeight=430px;status=no");
    
  if(typeof(returnMap) != 'undefined')
  {
         var oEditor = FCK;
         
      if(oEditor.EditMode == FCK_EDITMODE_WYSIWYG)
      {      
         //
        var fileName = returnMap['fileName'];
        var width = returnMap['width']; 
        var height = returnMap['height'];
        var autostart = returnMap['autoStart'];
        var allowfullscreen = returnMap['allowFullScreen'];
        var repeat = returnMap['repeatFlag'];
        var quality= returnMap['quality'];
        
        var x = ' <embed height="'+height+'" width="'+width+'" type="application/x-shockwave-flash" src='+base+'core/extools/player/jwplayer/5.8/player.swf?file='+base+'VIDEO/'+fileName+'&autostart='+autostart+'&allowfullscreen='+allowfullscreen+'&repeat='+repeat+'></embed>';
       
        //alert(x);
        oEditor.InsertHtml(x);
        
       
         
      }
      
      if(typeof(returnMap) != 'undefined')
      {
        
      }
   }
   
}
