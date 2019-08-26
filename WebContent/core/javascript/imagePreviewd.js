
   //var currentPath = '';

function previeImg(obj,mode) 
{	   
	 var filePath = '';
	 if('site'===mode)  
	 {
        if (obj.files) { // webkit, mozilla... (jq:$.support.boxModel)
            var reader = new FileReader();
            reader.readAsDataURL(obj.files[0]);
            reader.onloadend = function(event) {
                var imgObj = new Image();
                imgObj.src = event.target.result; // image src
                filePath = event.target.result;
                 currentPath =event.target.result;
                imgObj.onload = function(event) {
                    var size = checkSize(this.width, this.height);
                    this.width = size[0]; // image width
                    this.height = size[1]; // image height
                    document.getElementById('imgDiv').innerHTML = '<img class="imgP" src="'+this.src+'" width='+this.width+' height='+this.height+'>'; // $('#imgDiv').html(this);
                }
            }
        } else { // msie...
            obj.select();
            var path = document.selection.createRange().text;
          
            if (path) 
            {
            	filePath = path;
                var imgDiv = document.getElementById('imgDiv');
                imgDiv.style.filter = 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image)';
                imgDiv.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = path;
                imgDiv.style.width = 1;
                imgDiv.style.height = 1;
                var size = checkSize(imgDiv.offsetWidth, imgDiv.offsetHeight);
                imgDiv.style.filter = 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)';
                imgDiv.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = path; // image src
                imgDiv.style.width = size[0]; // image width
                imgDiv.style.height = size[1]; // image height
          
              
            }
        }
	 }
	 else if('web'===mode)
	 {
	 	if('' !=obj.value);
	 	document.getElementById('imgDiv').innerHTML = '<img class="imgP" src="'+obj.value+'" width='+200+' height='+150+' >'; // $('#imgDiv').html(this);
	 	
	    filePath = obj.value;
	 }

	 if(filePath != '')
	 {
	 	document.getElementById("photoName").value= filePath.substring(filePath.lastIndexOf('\\')+1,filePath.lastIndexOf('.'));
	 }		
	 	
	 document.getElementById("photoName").focus();
	 
	 return filePath;
}


function previeInitImg(targetDivId,src) 
{	   
	  ua = navigator.userAgent.toLowerCase(),
  
      check = function(r){
         return r.test(ua);
      },
      isOpera = check(/opera/),
	  isFF = check(/firefox/),
	  isIE = !isOpera && check(/msie/);
	
	
	 if(isIE)
	 {
		 var imgDiv = document.getElementById(targetDivId);
		 
	     imgDiv.style.filter = 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image)';
	     imgDiv.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
	     imgDiv.style.width = 1;
	     imgDiv.style.height = 1;
	     var size = checkSize(imgDiv.offsetWidth, imgDiv.offsetHeight);
	     imgDiv.style.filter = 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)';
	     imgDiv.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src; // image src
	     imgDiv.style.width = size[0]; // image width
	     imgDiv.style.height = size[1]; // image height
	 }
	 else if(isFF)
	 {
	 	 var imgObj = new Image();
         imgObj.src = src; // image src
         filePath = src;
         currentPath =src;
         imgObj.onload = function(event) 
         {
              var size = checkSize(this.width, this.height);
              this.width = size[0]; // image width
              this.height = size[1]; // image height
              document.getElementById('imgDiv').innerHTML = '<img class="imgP" src="'+this.src+'" width='+this.width+' height='+this.height+'>'; // $('#imgDiv').html(this);
         }
	 }
}
    
    
 function checkSize(width, height) 
 {
    if (width && height)
    {
       if (width / height > maxWidth / maxHeight) 
       {
          if (width > maxWidth) 
          {
          	height = height * maxWidth / width;
            width = maxWidth;
          }
       }
	   else 
	   {
           if (height > maxHeight) 
           {
             width = width * maxHeight / height;
             height = maxHeight;
           }
       }
       return [ width, height ];
      }
}

function removeDIVimgFilter()
{
	 var imgDiv = document.getElementById('imgDiv');
     imgDiv.style.filter = 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image)';
     imgDiv.style.width = 0;
     imgDiv.style.height = 0;
	
	
}
    

    