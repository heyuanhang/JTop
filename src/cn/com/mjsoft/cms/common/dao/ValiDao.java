package cn.com.mjsoft.cms.common.dao;

import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;

public class ValiDao

{
    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public ValiDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public Integer querySystemTableFlagExist( String table, String flagName,
        String val )
    {
        String sql = "select count(*) from " + table + " where " + flagName
            + "=?";

        return ( Integer ) pe.querySingleObject( sql, new Object[] { val },
            Integer.class );
    }

}
