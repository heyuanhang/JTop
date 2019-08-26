package cn.com.mjsoft.cms.metadata.bean;

public class ModelPersistenceMySqlCodeBean
{
    private Long dataModelId = Long.valueOf( -1 );

    private String insertSql;

    private String deleteSql;

    private String updateSql;

    private String selectSql;

    private String selectColumn;

    private String listSelectColumn;

    public Long getDataModelId()
    {
        return dataModelId;
    }

    public void setDataModelId( Long dataModelId )
    {
        this.dataModelId = dataModelId;
    }

    public String getDeleteSql()
    {
        return deleteSql;
    }

    public void setDeleteSql( String deleteSql )
    {
        this.deleteSql = deleteSql;
    }

    public String getInsertSql()
    {
        return insertSql;
    }

    public void setInsertSql( String insertSql )
    {
        this.insertSql = insertSql;
    }

    public String getSelectSql()
    {
        return selectSql;
    }

    public void setSelectSql( String selectSql )
    {
        this.selectSql = selectSql;
    }

    public String getUpdateSql()
    {
        return updateSql;
    }

    public void setUpdateSql( String updateSql )
    {
        this.updateSql = updateSql;
    }

    public String getSelectColumn()
    {
        return selectColumn;
    }

    public void setSelectColumn( String selectColumn )
    {
        this.selectColumn = selectColumn;
    }

    public String getListSelectColumn()
    {
        return listSelectColumn;
    }

    public void setListSelectColumn( String listSelectColumn )
    {
        this.listSelectColumn = listSelectColumn;
    }

}
