package cn.com.mjsoft.cms.security.bean;


public class ProtectResourceInfoBean
{
    // 目标资源
    SecurityResourceBean Resource;

    // 某组资源的认定标号,一般为父GROUP的flag
    String rootFlag;

    // 所选的授权accId
    String[] checkedAccIdSec;

    public String[] getCheckedAccIdSec()
    {
        return checkedAccIdSec;
    }

    public void setCheckedAccIdSec( String[] checkedAccIdSec )
    {
        this.checkedAccIdSec = checkedAccIdSec;
    }

    public SecurityResourceBean getResource()
    {
        return Resource;
    }

    public void setResource( SecurityResourceBean resource )
    {
        Resource = resource;
    }

    public String getRootFlag()
    {
        return rootFlag;
    }

    public void setRootFlag( String rootFlag )
    {
        this.rootFlag = rootFlag;
    }

}
