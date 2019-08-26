package cn.com.mjsoft.cms.content.controller;

import java.io.File;

import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class RandomFileRenamePolicy implements FileRenamePolicy
{

    public File rename( File file )
    {
        String body = "";
        String ext = "";
        int pot = file.getName().lastIndexOf( "." );
        if( pot != -1 )
        {
            body = DateAndTimeUtil.clusterTimeMillis() + "";
            ext = StringUtil.subString( file.getName(), pot );
        }
        else
        {
            body = DateAndTimeUtil.clusterTimeMillis() + "";
            ext = "";
        }
        String newName = body + StringUtil.getUUIDString() + ext;
        file = new File( file.getParent(), newName );
        return file;
    }
}
