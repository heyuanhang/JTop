package cn.com.mjsoft.cms.security.service;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.security.headstream.AuthorizationInfoHeadstream;
import cn.com.mjsoft.framework.security.headstream.bean.ResourceState;
import cn.com.mjsoft.framework.util.StringUtil;

/**
 * 针对web的权限信息源。web的请求对象将会在这里处理，解析出信息后会从数据源获取相关权限资源
 * 
 * @author $Author: mjsoft
 * 
 */
public class WebAuthorizationInfoHeadstream implements AuthorizationInfoHeadstream
{
    // 此Headstream应担负系统缓存责任，特记录
    // 写死,非配置
    private SecurityService securityResourceService = SecurityService.getInstance();

    public Set getAuthorizationInfoBatch( String key )
    {

        Set securityResourceRoleIdSet = securityResourceService
            .retrieveTargetResourceRelateRoleIdSetByKey( key );

        return securityResourceRoleIdSet;
    }

    public Set getAccAuthorizationInfoBatch( ResourceState resourceState )
    {
        Set securityResourceRoleIdSet = securityResourceService
            .retrieveTargetResourceRelateRoleIdSetByKeyAndAccInfo( resourceState.getTargetResId(),
                Long.valueOf( StringUtil.getLongValue( resourceState.getSymbolValue(), -1 ) ),
                resourceState.getAccSymbolId() );

        return securityResourceRoleIdSet;
    }

    public Map getAllAuthorizationInfo()
    {
        return null;
    }

    public Set getSecurityResource()
    {
        Set sRes = securityResourceService.retrieveAllSecurityResourceInfo();
        return sRes;
    }

    /**
     * 查询给予的urlKey对应的权限
     * 
     * @param urlKey 从URL解析出的请求信息key
     * @return 目标key的授权细心你
     */
    private Map searchUrlRoleInformation( String urlKey )
    {
        return null;
    }

    private String parseWebRequestUrl( HttpServletRequest request )
    {
        return null;
    }

    public ResourceState getTargetResource( String key )
    {
        // 实际情况应该涉及缓存，当数据集合缓存存在时，优先使用cache
        return securityResourceService.getSecurityResource( key );
    }

}
