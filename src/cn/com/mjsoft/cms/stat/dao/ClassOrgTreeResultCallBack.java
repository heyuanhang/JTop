package cn.com.mjsoft.cms.stat.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.cms.organization.service.OrgService;
import cn.com.mjsoft.framework.persistence.core.support.MapValueCallback;

public class ClassOrgTreeResultCallBack implements MapValueCallback
{
    private static ChannelService channelService = ChannelService.getInstance();

    private OrgService orgService = OrgService.getInstance();

    @SuppressWarnings( "unchecked" )
    public void transformVlaue( Map result )
    {
        Long classId = ( Long ) result.get( "classId" );

        Long orgId = ( Long ) result.get( "orgId" );

        ContentClassBean classBean = channelService
            .retrieveSingleClassBeanInfoByClassId( classId );

        StringBuilder buf = new StringBuilder();

        List resultList = null;

        if( classBean != null && classBean.getClassId().longValue() > 0 )
        {
            resultList = channelService
                .retrieveContentClassBeanByCurrentPath( classBean
                    .getChannelPath() );

            for ( int i = 0; i < resultList.size(); i++ )
            {
                classBean = ( ContentClassBean ) resultList.get( i );

                if( i + 1 != resultList.size() )
                {
                    buf.append( classBean.getClassName() + " > " );
                }
                else
                {
                    buf.append( classBean.getClassName() );
                }
            }
        }

        result.put( "classTree", buf.toString() );

        // org

        buf = new StringBuilder();

        resultList = new ArrayList();

        if( orgId == null )
        {
            return;
        }

        SystemOrganizationBean orgBean = orgService
            .retrieveSingleOrganizationBeanById( orgId );

        resultList.add( orgBean );

        while ( orgBean != null && orgBean.getParentId().longValue() > 0 )
        {
            orgBean = orgService.retrieveSingleOrganizationBeanById( orgBean
                .getParentId() );

            resultList.add( orgBean );
        }

        Collections.reverse( resultList );

        SystemOrganizationBean org = null;

        for ( int i = 0; i < resultList.size(); i++ )
        {
            org = ( SystemOrganizationBean ) resultList.get( i );

            if( i + 1 != resultList.size() )
            {
                buf.append( org.getOrgName() + " > " );
            }
            else
            {
                buf.append( org.getOrgName() );
            }
        }

        result.put( "orgTree", buf.toString() );

    }
}
