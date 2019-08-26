//tab«–ªª
function setTab2(name,cursel,n){
 for(i=1;i<=n;i++){
  var menus=document.getElementById(name+i);
  var cons=document.getElementById("g3_"+name+"_"+i);
  menus.className=i==cursel?"selectTag":"";
  cons.style.display=i==cursel?"block":"none";
 }
}

clickMenu = function(menus) {
	var getAgn = getEls;
	for (var i=0; i<getEls.length; i++) {
			getEls[i].onmouseover=function() {
				for (var x=0; x<getAgn.length; x++) {
				getAgn[x].className=getAgn[x].className.replace("click", "");
				}
				this.className+=" click";
				}
			}
		}

function nTabs(tabObj,obj){
        var tabList = document.getElementById(tabObj).getElementsByTagName("li");
        for(i=0; i <tabList.length; i++)
        {
                if (tabList[i].id == obj.id)
                {
                        document.getElementById(tabObj+"_Title"+i).className = "active"; 
                    document.getElementById(tabObj+"_Content"+i).style.display = "block";
                }else{
                        document.getElementById(tabObj+"_Title"+i).className = "normal"; 
                        document.getElementById(tabObj+"_Content"+i).style.display = "none";
						
                }
        } 
}