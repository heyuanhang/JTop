

//公用方法 开始
function selectAll(targetCheckName)
{
   var checks = document.getElementsByName(targetCheckName);

   for(var i = 0; i < checks.length; i++)
   {
      checks[i].checked=true;
      //targetMap.put(checks[i].value,checks[i].value);
   }
  // targetMap.remove(checkAllIdName);
   
}

function unSelectAll(targetCheckName)
{
   var checks = document.getElementsByName(targetCheckName);

   for(var i = 0; i < checks.length; i++)
   {
      checks[i].checked=false;
   }
   //targetMap.removeAll();
}
//公用方法 结束


