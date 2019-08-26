//tab«–ªª
function setTab2(name,cursel,n){
 for(i=1;i<=n;i++){
  var menus=document.getElementById(name+i);
  var cons=document.getElementById("g3_"+name+"_"+i);
  menus.className=i==cursel?"selectTag":"";
  cons.style.display=i==cursel?"block":"none";
 }
}
