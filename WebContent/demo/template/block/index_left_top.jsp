<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<div class="slider310">
             <div id="slides">
             
            
              
                
              <cms:Content pageSize="5" classId="allChild:root" order="pubDate-down" filter="contentImg">
              
                <a class="ma" target="_blank" href="${Info.contentUrl}"><img src="${Info.contentImage}" alt=""/><strong> ${Info.title}</strong><div class="bg"></div></a>
            
           
            
              </cms:Content>
              
              
              
</div>
<script type="text/javascript">
//首页幻灯
    $(function() {
      $('#slides').slidesjs({
        width:310,
        height:220,
        play: {
          auto: true,
          interval: 4000,
          swap: false
        }
      });
    });


</script>