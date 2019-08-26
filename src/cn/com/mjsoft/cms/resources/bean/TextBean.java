package cn.com.mjsoft.cms.resources.bean;

public class TextBean
{
    private String content = "";

    private Integer lineCount = Integer.valueOf( 0 );

    private String charset;

    public String getContent()
    {
        return content;
    }

    public void setContent( String content )
    {
        this.content = content;
    }

    public Integer getLineCount()
    {
        return lineCount;
    }

    public void setLineCount( Integer lineCount )
    {
        this.lineCount = lineCount;
    }

    public String getCharset()
    {
        return charset;
    }

    public void setCharset( String charset )
    {
        this.charset = charset;
    }
}
