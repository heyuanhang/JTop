package cn.com.mjsoft.cms.content.bean;

import org.apache.commons.lang.builder.ToStringBuilder;

public class VideoMetadataBean
{
    private String duration;

    private Integer durationHour = Integer.valueOf( 0 );

    private Integer durationMinute = Integer.valueOf( 0 );

    private Integer durationSec = Integer.valueOf( 0 );

    private String bitrate;// 如123kb

    private String codeFormat;// 如h264

    private String resolution="";// 如800*600

    public VideoMetadataBean()
    {

    }

    public VideoMetadataBean( String duration, Integer durationHour,
        Integer durationMinute, Integer durationSec, String bitrate,
        String codeFormat, String resolution )
    {
        this.duration = duration;
        this.durationHour = durationHour;
        this.durationMinute = durationMinute;
        this.durationSec = durationSec;
        this.bitrate = bitrate;
        this.codeFormat = codeFormat;
        this.resolution = resolution;
    }

    public String getBitrate()
    {
        return bitrate;
    }

    public void setBitrate( String bitrate )
    {
        this.bitrate = bitrate;
    }

    public String getCodeFormat()
    {
        return codeFormat;
    }

    public void setCodeFormat( String codeFormat )
    {
        this.codeFormat = codeFormat;
    }

    public Integer getDurationHour()
    {
        return durationHour;
    }

    public void setDurationHour( Integer durationHour )
    {
        this.durationHour = durationHour;
    }

    public Integer getDurationMinute()
    {
        return durationMinute;
    }

    public void setDurationMinute( Integer durationMinute )
    {
        this.durationMinute = durationMinute;
    }

    public Integer getDurationSec()
    {
        return durationSec;
    }

    public void setDurationSec( Integer durationSec )
    {
        this.durationSec = durationSec;
    }

    public String getResolution()
    {
        return resolution;
    }

    public void setResolution( String resolution )
    {
        this.resolution = resolution;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration( String duration )
    {
        this.duration = duration;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder( this ).append( "resolution",
            this.resolution ).append( "durationSec", this.durationSec ).append(
            "durationMinute", this.durationMinute ).append( "duration",
            this.duration ).append( "durationHour", this.durationHour ).append(
            "codeFormat", this.codeFormat ).append( "bitrate", this.bitrate )
            .toString();
    }

}
