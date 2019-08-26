package cn.com.mjsoft.cms.metadata.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.site.bean.CmsServerBean;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.StringUtil;

/**
 * 当前数据快照bean，因元数据多为系统缓存级别数据，此类作为元数据和即时数据之间的桥梁.
 * 
 * @author mj-soft
 * 
 */
public class MetadataValueSnapshotBean
{
    
    public static final String UE_SIMPLE_MODE = ", toolbars: [['fullscreen', 'source', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough','removeformat','forecolor', 'backcolor', 'formatmatch', 'autotypeset', 'link', 'unlink', 'anchor','fontfamily', 'fontsize','undo', 'redo']]";
    /**
     * 系统当前所有模型对应的Html元素,这里作为全局缓存形式，以ID : html元素描述的形式存在
     */
    private static Map htmlElementMap;

    static
    {
        htmlElementMap = MetaDataService.getInstance().retrieveSystemHtmlElementInfo();
    }

    private static ResourcesService resService = ResourcesService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    /**
     * 注意，此bean来自cache
     */
    private ModelFiledInfoBean filedInfo;

    private Map info;

    private String htmlInputTemplet;

    private String htmlEditTemplet;

    public MetadataValueSnapshotBean( ModelFiledInfoBean modelFiledInfoBean )
    {
        this.filedInfo = modelFiledInfoBean;
    }

    public MetadataValueSnapshotBean( ModelFiledInfoBean modelFiledInfoBean, Map targetInfo )
    {
        this.filedInfo = modelFiledInfoBean;
        this.info = targetInfo;
    }

    public void setTargetInfo( Map targetInfo )
    {
        this.info = targetInfo;
    }

    public ModelFiledInfoBean getModelFiledInfoBean()
    {
        return filedInfo;
    }

    public void setModelFiledInfoBean( ModelFiledInfoBean modelFiledInfoBean )
    {
        this.filedInfo = modelFiledInfoBean;
    }

    /**
     * 获取当前对应模型字段的输入html形式
     * 
     * @return
     */
    public String getInputModeLayoutHtml()
    {
        if( this.htmlInputTemplet == null )
        {
            htmlElementMap = MetaDataService.getInstance().retrieveSystemHtmlElementInfo();
            SystemModelHtmlElementBean bean = ( SystemModelHtmlElementBean ) htmlElementMap
                .get( filedInfo.getHtmlElementId() );

            if( bean != null )
            {
                String targetLayoutContent = bean.getHtmlInputTemplet();

                List paramList = bean.getLayoutParam();

                String param = null;
                String key = null;
                String value = null;

                // 下拉框
                if( Constant.METADATA.SELECT == bean.getHtmlElementId().intValue() )
                {
                    StringBuffer buf = new StringBuffer();
                    String[] layoutPart = StringUtil.split( targetLayoutContent, "," );

                    // select
                    param = ( String ) paramList.get( 0 );
                    key = disposeLayoutParamToKey( param );

                    // value = ObjectUtility.getPrivateFieldValue( key, this )
                    // .toString();

                    value = this.filedInfo.retrieveModelFiledValueByKey( key ).toString();

                    buf.append( StringUtil
                        .replaceString( layoutPart[0], param, value, false, false ) );

                    String layoutTemp = null;

                    if( Constant.COMMON.ON.equals( filedInfo.getLinkType() ) )
                    {
                        List linkValList = metaDataService.retrieveFieldValueListByMetadata(
                            filedInfo.getLinkModelId(), filedInfo.getLinkFieldId() );

                        String choiceKey = null;
                        String choiceValue = null;

                        Long contentId = null;
                        Object val = null;

                        Map valMap = null;
                        for ( int i = 0; i < linkValList.size(); i++ )
                        {
                            valMap = ( Map ) linkValList.get( i );
                            contentId = ( Long ) valMap.get( "contentId" );
                            val = valMap.get( "val" );

                            choiceKey = ( val != null ) ? val.toString() : "";
                            choiceValue = contentId.toString();

                            param = ( String ) paramList.get( 1 );

                            if( choiceValue.equals( filedInfo.getDefaultValue() ) )
                            {
                                layoutTemp = StringUtil.replaceString( layoutPart[2], param,
                                    choiceValue, false, false );

                                param = ( String ) paramList.get( 2 );
                                layoutTemp = StringUtil.replaceString( layoutTemp, param,
                                    choiceKey, false, false );

                                buf.append( layoutTemp );

                            }
                            else
                            {
                                layoutTemp = StringUtil.replaceString( layoutPart[1], param,
                                    choiceValue, false, false );

                                param = ( String ) paramList.get( 2 );
                                layoutTemp = StringUtil.replaceString( layoutTemp, param,
                                    choiceKey, false, false );
                                buf.append( layoutTemp );

                                int x = 0;
                            }
                        }
                    }
                    else
                    {

                        // option
                        String[] choice = StringUtil.split( filedInfo.getChoiceValue() != null
                            ? filedInfo.getChoiceValue() : "", "," );

                        for ( int i = 0; i < choice.length; i++ )
                        {
                            if( StringUtil.isStringNull( choice[i] ) )
                            {
                                continue;
                            }

                            param = ( String ) paramList.get( 1 );

                            String[] choiceKV = StringUtil.split( choice[i], "=" );
                            if( choiceKV[1].equals( filedInfo.getDefaultValue() ) )
                            {
                                layoutTemp = StringUtil.replaceString( layoutPart[2], param,
                                    choiceKV[1], false, false );

                                param = ( String ) paramList.get( 2 );
                                layoutTemp = StringUtil.replaceString( layoutTemp, param,
                                    choiceKV[0], false, false );

                                buf.append( layoutTemp );

                            }
                            else
                            {
                                layoutTemp = StringUtil.replaceString( layoutPart[1], param,
                                    choiceKV[1], false, false );

                                param = ( String ) paramList.get( 2 );
                                layoutTemp = StringUtil.replaceString( layoutTemp, param,
                                    choiceKV[0], false, false );
                                buf.append( layoutTemp );
                            }
                        }
                    }

                    buf.append( layoutPart[3] );

                    this.htmlInputTemplet = buf.toString();
                }
                else if( Constant.METADATA.RADIO == bean.getHtmlElementId().intValue() )
                {
                    String[] layoutPart = StringUtil.split( targetLayoutContent, "," );

                    String singleRadio = "";
                    StringBuffer buf = new StringBuffer();

                    if( Constant.COMMON.ON.equals( filedInfo.getLinkType() ) )
                    {
                        List linkValList = metaDataService.retrieveFieldValueListByMetadata(
                            filedInfo.getLinkModelId(), filedInfo.getLinkFieldId() );

                        String choiceKey = null;
                        String choiceValue = null;

                        Long contentId = null;
                        Object val = null;

                        Map valMap = null;
                        for ( int i = 0; i < linkValList.size(); i++ )
                        {
                            valMap = ( Map ) linkValList.get( i );
                            contentId = ( Long ) valMap.get( "contentId" );
                            val = valMap.get( "val" );

                            choiceKey = ( val != null ) ? val.toString() : "";
                            choiceValue = contentId.toString();

                            // ${fignSign}
                            param = ( String ) paramList.get( 0 );
                            key = disposeLayoutParamToKey( param );

                            // value = ObjectUtility.getPrivateFieldValue( key,
                            // this
                            // )
                            // .toString();
                            value = this.filedInfo.retrieveModelFiledValueByKey( key ).toString();

                            if( StringUtil.isStringNull( choiceKey )
                                || StringUtil.isStringNull( choiceValue ) )
                            {
                                continue;
                            }

                            if( choiceValue.equals( filedInfo.getDefaultValue() ) )
                            {
                                singleRadio = StringUtil.replaceString( layoutPart[1], param,
                                    value, false, false );
                            }
                            else
                            {
                                singleRadio = StringUtil.replaceString( layoutPart[0], param,
                                    value, false, false );
                            }

                            // ${value}
                            param = ( String ) paramList.get( 1 );

                            singleRadio = StringUtil.replaceString( singleRadio, param,
                                choiceValue, false, false );

                            // ${key}
                            param = ( String ) paramList.get( 2 );

                            singleRadio = StringUtil.replaceString( singleRadio, param, choiceKey,
                                false, false );

                            buf.append( singleRadio );
                        }
                    }
                    else
                    {
                        // radio
                        String[] choice = StringUtil.split( filedInfo.getChoiceValue() != null
                            ? filedInfo.getChoiceValue() : "", "," );

                        for ( int i = 0; i < choice.length; i++ )
                        {
                            if( StringUtil.isStringNull( choice[i] ) )
                            {
                                continue;
                            }
                            // ${fignSign}
                            param = ( String ) paramList.get( 0 );
                            key = disposeLayoutParamToKey( param );

                            // value = ObjectUtility.getPrivateFieldValue( key,
                            // this
                            // )
                            // .toString();
                            value = this.filedInfo.retrieveModelFiledValueByKey( key ).toString();

                            String[] choiceKV = StringUtil.split( choice[i], "=" );

                            if( choiceKV.length < 2 )
                            {
                                continue;
                            }

                            String choiceKey = choiceKV[0];
                            String choiceValue = choiceKV[1];

                            if( StringUtil.isStringNull( choiceKey )
                                || StringUtil.isStringNull( choiceValue ) )
                            {
                                continue;
                            }

                            if( choiceKV[1].equals( filedInfo.getDefaultValue() ) )
                            {
                                singleRadio = StringUtil.replaceString( layoutPart[1], param,
                                    value, false, false );
                            }
                            else
                            {
                                singleRadio = StringUtil.replaceString( layoutPart[0], param,
                                    value, false, false );
                            }

                            // ${value}
                            param = ( String ) paramList.get( 1 );

                            singleRadio = StringUtil.replaceString( singleRadio, param,
                                choiceKV[1], false, false );

                            // ${key}
                            param = ( String ) paramList.get( 2 );

                            singleRadio = StringUtil.replaceString( singleRadio, param,
                                choiceKV[0], false, false );

                            buf.append( singleRadio );

                        }
                    }

                    this.htmlInputTemplet = buf.toString();
                }
                else if( Constant.METADATA.CHECK_BOX == bean.getHtmlElementId().intValue() )
                {
                    String[] layoutPart = StringUtil.split( targetLayoutContent, "," );

                    String singleRadio = "";
                    StringBuffer buf = new StringBuffer();

                    // check_box
                    String[] defValue = StringUtil.split( filedInfo.getDefaultValue() != null
                        ? filedInfo.getDefaultValue() : "", "," );

                    if( Constant.COMMON.ON.equals( filedInfo.getLinkType() ) )
                    {
                        List linkValList = metaDataService.retrieveFieldValueListByMetadata(
                            filedInfo.getLinkModelId(), filedInfo.getLinkFieldId() );

                        String choiceKey = null;
                        String choiceValue = null;

                        Long contentId = null;
                        Object val = null;

                        Map valMap = null;
                        for ( int i = 0; i < linkValList.size(); i++ )
                        {
                            valMap = ( Map ) linkValList.get( i );
                            contentId = ( Long ) valMap.get( "contentId" );
                            val = valMap.get( "val" );

                            choiceKey = ( val != null ) ? val.toString() : "";
                            choiceValue = contentId.toString();

                            // ${fignSign}
                            param = ( String ) paramList.get( 0 );
                            key = disposeLayoutParamToKey( param );

                            // value = ObjectUtility.getPrivateFieldValue( key,
                            // this
                            // )
                            // .toString();
                            value = this.filedInfo.retrieveModelFiledValueByKey( key ).toString();

                            boolean needCheck = false;
                            for ( int j = 0; j < defValue.length; j++ )
                            {
                                if( choiceValue.equals( defValue[j] ) )
                                {
                                    needCheck = true;
                                    break;
                                }
                            }

                            if( needCheck )
                            {
                                singleRadio = StringUtil.replaceString( layoutPart[1], param,
                                    value, false, false );
                            }
                            else
                            {
                                singleRadio = StringUtil.replaceString( layoutPart[0], param,
                                    value, false, false );
                            }

                            // ${value}
                            param = ( String ) paramList.get( 1 );

                            singleRadio = StringUtil.replaceString( singleRadio, param,
                                choiceValue, false, false );

                            // ${key}
                            param = ( String ) paramList.get( 2 );

                            singleRadio = StringUtil.replaceString( singleRadio, param, choiceKey,
                                false, false );

                            buf.append( singleRadio );

                        }
                    }
                    else
                    {

                        String[] choice = StringUtil.split( filedInfo.getChoiceValue() != null
                            ? filedInfo.getChoiceValue() : "", "," );

                        for ( int i = 0; i < choice.length; i++ )
                        {
                            if( StringUtil.isStringNull( choice[i] ) )
                            {
                                continue;
                            }

                            // ${fignSign}
                            param = ( String ) paramList.get( 0 );
                            key = disposeLayoutParamToKey( param );

                            value = this.filedInfo.retrieveModelFiledValueByKey( key ).toString();

                            String[] choiceKV = StringUtil.split( choice[i], "=" );
                            boolean needCheck = false;
                            for ( int j = 0; j < defValue.length; j++ )
                            {
                                if( choiceKV[1].equals( defValue[j] ) )
                                {
                                    needCheck = true;
                                    break;
                                }
                            }

                            if( needCheck )
                            {
                                singleRadio = StringUtil.replaceString( layoutPart[1], param,
                                    value, false, false );
                            }
                            else
                            {
                                singleRadio = StringUtil.replaceString( layoutPart[0], param,
                                    value, false, false );
                            }

                            // ${value}
                            param = ( String ) paramList.get( 1 );

                            singleRadio = StringUtil.replaceString( singleRadio, param,
                                choiceKV[1], false, false );

                            // ${key}
                            param = ( String ) paramList.get( 2 );

                            singleRadio = StringUtil.replaceString( singleRadio, param,
                                choiceKV[0], false, false );

                            buf.append( singleRadio );
                        }
                    }

                    this.htmlInputTemplet = buf.toString();
                }
                else if( Constant.METADATA.CUSTOM_HTML_CONTENT == bean.getHtmlElementId()
                    .intValue() )
                {
                    this.htmlInputTemplet = filedInfo.getHtmlContent();
                }
                else
                {
                    for ( int i = 0; i < paramList.size(); i++ )
                    {
                        param = ( String ) paramList.get( i );
                        key = disposeLayoutParamToKey( param );

                        // value = ObjectUtility.getPrivateFieldValue( key, this
                        // )
                        // .toString();

                        if( "${siteBase}".equals( param ) )
                        {
                            CmsServerBean cms = JtRuntime.cmsServer;

                            value = cms.getDomainFullPath();
                        }
                        else if( "${simpleMode}".equals( param ) )
                        {
                            // 富文本编辑器特殊处理,简单功能模式
                            if( Constant.METADATA.EDITER == bean.getHtmlElementId().intValue() )
                            {
                                if( Constant.COMMON.OFF.equals( this.filedInfo.getFullEditor() ) )
                                {

                                    //value = "editor1.ToolbarSet = 'Edit';";
                                    value = UE_SIMPLE_MODE;
                                }
                                else
                                {
                                    value = "";
                                }
                            }
                        }
                        else
                        {
                            Object fieldVal = this.filedInfo.retrieveModelFiledValueByKey( key );

                            if( fieldVal != null )
                            {
                                value = fieldVal.toString();
                            }
                        }

                        targetLayoutContent = StringUtil.replaceString( targetLayoutContent, param,
                            value, false, false );
                    }

                    this.htmlInputTemplet = targetLayoutContent;
                }

                // TODO 技术验证

                // script
                String script = this.filedInfo.getJavascript();
                String endScript = null;
                if( StringUtil.isStringNull( script ) )
                {
                    endScript = "";
                }
                else
                {
                    endScript = script;
                }

                this.htmlInputTemplet = StringUtil.replaceString( this.htmlInputTemplet,
                    "${jsEvent}", endScript, false, false );

                // style
                String style = this.filedInfo.getStyle();
                String endStyle = null;

                if( StringUtil.isStringNull( style ) )
                {
                    endStyle = "";
                }
                else
                {
                    endStyle = "style=\"" + style + "\"";
                }

                this.htmlInputTemplet = StringUtil.replaceString( this.htmlInputTemplet,
                    "${style}", endStyle, false, false );

                // css
                String css = this.filedInfo.getCssClass();
                String endClass = null;

                if( StringUtil.isStringNull( css ) )
                {
                    endClass = "";
                }
                else
                {
                    endClass = "class=\"" + css + "\"";
                }

                this.htmlInputTemplet = StringUtil.replaceString( this.htmlInputTemplet, "${css}",
                    endClass, false, false );

            }
        }
        
        this.htmlInputTemplet = StringUtil.replaceString( this.htmlInputTemplet, "${ph}",
            filedInfo.getHtmlDesc(), false, false );
         
        String psStr = StringUtil.isStringNull( filedInfo.getHtmlDesc() ) ? "" :"<span class='ps'>"+filedInfo.getHtmlDesc()+"</span>";
        
        if( this.htmlInputTemplet.indexOf( "${ps}" ) != -1)
        {
            this.htmlInputTemplet = StringUtil.replaceString( this.htmlInputTemplet, "${ps}", "", false, false );
             
            this.htmlInputTemplet =  this.htmlInputTemplet + psStr;
        }

        return this.htmlInputTemplet;

    }

    /**
     * 获取当前对应模型字段的编辑html形式
     * 
     * @return
     */
    public String getEditModeLayoutHtml()
    {
        if( info == null )
        {
            info = new HashMap( 1 );
        }

        if( this.htmlEditTemplet == null )
        {
            htmlElementMap = MetaDataService.getInstance().retrieveSystemHtmlElementInfo();
            SystemModelHtmlElementBean bean = ( SystemModelHtmlElementBean ) htmlElementMap
                .get( filedInfo.getHtmlElementId() );

            if( bean != null )
            {
                String targetLayoutContent = bean.getHtmlEditTemplet();

                List layoutParamList = bean.getLayoutParam();
                List valueParamList = bean.getValueParam();

                String param = null;
                String key = null;
                String value = null;

                String filedSign = this.filedInfo.retrieveModelFiledValueByKey(
                    Constant.METADATA.FILED_SIGN ).toString();

                // 取值不再需要前缀
                // Object dbVlaueTmp = targetInfo
                // .get( Constant.METADATA.PREFIX_COLUMN_NAME + filedSign );

                Object dbVlaueTmp = info.get( filedSign );
                String dbValue = "";
                if( dbVlaueTmp != null )
                {
                    dbValue = dbVlaueTmp.toString();
                }

                // 下拉框
                if( Constant.METADATA.SELECT == bean.getHtmlElementId().intValue() )
                {
                    // 设定数据库实际值
                    if( StringUtil.isStringNull( dbValue ) )
                    {
                        dbValue = filedInfo.getDefaultValue();
                    }

                    String[] layoutPart = StringUtil.split( targetLayoutContent, "," );

                    StringBuffer buf = new StringBuffer();

                    String choiceKey = null;
                    String choiceValue = null;

                    // select
                    param = ( String ) layoutParamList.get( 0 );
                    key = disposeLayoutParamToKey( param );

                    // value = ObjectUtility.getPrivateFieldValue( key, this )
                    // .toString();

                    value = this.filedInfo.retrieveModelFiledValueByKey( key ).toString();

                    buf.append( StringUtil
                        .replaceString( layoutPart[0], param, value, false, false ) );

                    // option
                    String[] choice = StringUtil.split( filedInfo.getChoiceValue() != null
                        ? filedInfo.getChoiceValue() : "", "," );
                    String layoutTemp = null;

                    if( Constant.COMMON.ON.equals( filedInfo.getLinkType() ) )
                    {
                        List linkValList = metaDataService.retrieveFieldValueListByMetadata(
                            filedInfo.getLinkModelId(), filedInfo.getLinkFieldId() );

                        Long contentId = null;
                        Object val = null;

                        Map valMap = null;
                        for ( int i = 0; i < linkValList.size(); i++ )
                        {
                            valMap = ( Map ) linkValList.get( i );
                            contentId = ( Long ) valMap.get( "contentId" );
                            val = valMap.get( "val" );

                            param = ( String ) layoutParamList.get( 1 );

                            choiceKey = ( val != null ) ? val.toString() : "";
                            choiceValue = contentId.toString();

                            if( choiceValue.equals( dbValue ) )
                            {
                                layoutTemp = StringUtil.replaceString( layoutPart[2], param,
                                    choiceValue, false, false );

                                param = ( String ) layoutParamList.get( 2 );

                                layoutTemp = StringUtil.replaceString( layoutTemp, param,
                                    choiceKey, false, false );

                                buf.append( layoutTemp );
                            }
                            else
                            {
                                layoutTemp = StringUtil.replaceString( layoutPart[1], param,
                                    choiceValue, false, false );

                                param = ( String ) layoutParamList.get( 2 );

                                layoutTemp = StringUtil.replaceString( layoutTemp, param,
                                    choiceKey, false, false );
                                buf.append( layoutTemp );
                            }

                        }

                    }
                    else
                    {
                        for ( int i = 0; i < choice.length; i++ )
                        {
                            if( StringUtil.isStringNull( choice[i] ) )
                            {
                                continue;
                            }

                            param = ( String ) layoutParamList.get( 1 );

                            String[] choiceKV = StringUtil.split( choice[i], "=" );

                            choiceKey = choiceKV[0];
                            choiceValue = choiceKV[1];

                            if( choiceValue.equals( dbValue ) )
                            {
                                layoutTemp = StringUtil.replaceString( layoutPart[2], param,
                                    choiceValue, false, false );

                                param = ( String ) layoutParamList.get( 2 );

                                layoutTemp = StringUtil.replaceString( layoutTemp, param,
                                    choiceKey, false, false );

                                buf.append( layoutTemp );
                            }
                            else
                            {
                                layoutTemp = StringUtil.replaceString( layoutPart[1], param,
                                    choiceValue, false, false );

                                param = ( String ) layoutParamList.get( 2 );

                                layoutTemp = StringUtil.replaceString( layoutTemp, param,
                                    choiceKey, false, false );
                                buf.append( layoutTemp );
                            }
                        }

                    }

                    buf.append( layoutPart[3] );

                    this.htmlEditTemplet = buf.toString();
                }
                else if( Constant.METADATA.RADIO == bean.getHtmlElementId().intValue() )
                {
                    // 设定数据库实际值
                    if( StringUtil.isStringNull( dbValue ) )
                    {
                        dbValue = filedInfo.getDefaultValue();
                    }

                    String[] layoutPart = StringUtil.split( targetLayoutContent, "," );

                    String singleRadio = "";
                    StringBuffer buf = new StringBuffer();

                    String choiceKey = null;
                    String choiceValue = null;

                    // radio
                    if( Constant.COMMON.ON.equals( filedInfo.getLinkType() ) )
                    {
                        List linkValList = metaDataService.retrieveFieldValueListByMetadata(
                            filedInfo.getLinkModelId(), filedInfo.getLinkFieldId() );

                        Long contentId = null;
                        Object val = null;

                        Map valMap = null;
                        for ( int i = 0; i < linkValList.size(); i++ )
                        {
                            valMap = ( Map ) linkValList.get( i );
                            contentId = ( Long ) valMap.get( "contentId" );
                            val = valMap.get( "val" );

                            // ${fignSign}
                            param = ( String ) layoutParamList.get( 0 );
                            key = disposeLayoutParamToKey( param );

                            value = this.filedInfo.retrieveModelFiledValueByKey( key ).toString();

                            choiceKey = ( val != null ) ? val.toString() : "";
                            choiceValue = contentId.toString();

                            if( StringUtil.isStringNull( choiceKey )
                                || StringUtil.isStringNull( choiceValue ) )
                            {
                                continue;
                            }

                            if( choiceValue.equals( dbValue ) )
                            {
                                singleRadio = StringUtil.replaceString( layoutPart[1], param,
                                    value, false, false );
                            }
                            else
                            {
                                singleRadio = StringUtil.replaceString( layoutPart[0], param,
                                    value, false, false );
                            }

                            // ${value}
                            param = ( String ) layoutParamList.get( 1 );

                            singleRadio = StringUtil.replaceString( singleRadio, param,
                                choiceValue, false, false );

                            // ${key}
                            param = ( String ) layoutParamList.get( 2 );

                            singleRadio = StringUtil.replaceString( singleRadio, param, choiceKey,
                                false, false );

                            buf.append( singleRadio );
                        }
                    }
                    else
                    {
                        String[] choice = StringUtil.split( filedInfo.getChoiceValue() != null
                            ? filedInfo.getChoiceValue() : "", "," );
                        for ( int i = 0; i < choice.length; i++ )
                        {
                            if( StringUtil.isStringNull( choice[i] ) )
                            {
                                continue;
                            }

                            // ${fignSign}
                            param = ( String ) layoutParamList.get( 0 );
                            key = disposeLayoutParamToKey( param );

                            value = this.filedInfo.retrieveModelFiledValueByKey( key ).toString();

                            String[] choiceKV = StringUtil.split( choice[i], "=" );

                            if( choiceKV.length < 2 )
                            {
                                continue;
                            }

                            choiceKey = choiceKV[0];
                            choiceValue = choiceKV[1];

                            if( StringUtil.isStringNull( choiceKey )
                                || StringUtil.isStringNull( choiceValue ) )
                            {
                                continue;
                            }

                            if( choiceValue.equals( dbValue ) )
                            {
                                singleRadio = StringUtil.replaceString( layoutPart[1], param,
                                    value, false, false );
                            }
                            else
                            {
                                singleRadio = StringUtil.replaceString( layoutPart[0], param,
                                    value, false, false );
                            }

                            // ${value}
                            param = ( String ) layoutParamList.get( 1 );

                            singleRadio = StringUtil.replaceString( singleRadio, param,
                                choiceValue, false, false );

                            // ${key}
                            param = ( String ) layoutParamList.get( 2 );

                            singleRadio = StringUtil.replaceString( singleRadio, param, choiceKey,
                                false, false );

                            buf.append( singleRadio );

                        }
                    }

                    this.htmlEditTemplet = buf.toString();
                }
                else if( Constant.METADATA.CHECK_BOX == bean.getHtmlElementId().intValue() )
                {

                    String[] layoutPart = StringUtil.split( targetLayoutContent, "," );

                    String singleBox = "";
                    StringBuffer buf = new StringBuffer();

                    String choiceKey = null;
                    String choiceValue = null;

                    // check_box
                    if( Constant.COMMON.ON.equals( filedInfo.getLinkType() ) )
                    {
                        String[] defValue = null;
                        if( StringUtil.isStringNull( dbValue ) )
                        {
                            defValue = StringUtil.split( filedInfo.getDefaultValue(), "," );
                        }
                        else
                        {
                            defValue = StringUtil.split( dbValue, "," );
                        }

                        List linkValList = metaDataService.retrieveFieldValueListByMetadata(
                            filedInfo.getLinkModelId(), filedInfo.getLinkFieldId() );

                        Long contentId = null;
                        Object val = null;

                        Map valMap = null;
                        for ( int i = 0; i < linkValList.size(); i++ )
                        {
                            valMap = ( Map ) linkValList.get( i );
                            contentId = ( Long ) valMap.get( "contentId" );
                            val = valMap.get( "val" );

                            // ${fignSign}
                            param = ( String ) layoutParamList.get( 0 );
                            key = disposeLayoutParamToKey( param );

                            value = this.filedInfo.retrieveModelFiledValueByKey( key ).toString();

                            choiceKey = ( val != null ) ? val.toString() : "";
                            choiceValue = contentId.toString();

                            boolean needBoxCheckedStatus = false;
                            for ( int j = 0; j < defValue.length; j++ )
                            {
                                if( choiceValue.equals( defValue[j] ) )
                                {
                                    needBoxCheckedStatus = true;
                                    break;
                                }
                            }

                            if( needBoxCheckedStatus )
                            {
                                singleBox = StringUtil.replaceString( layoutPart[1], param, value,
                                    false, false );
                            }
                            else
                            {
                                singleBox = StringUtil.replaceString( layoutPart[0], param, value,
                                    false, false );
                            }

                            // ${value}
                            param = ( String ) layoutParamList.get( 1 );

                            singleBox = StringUtil.replaceString( singleBox, param, choiceValue,
                                false, false );

                            // ${key}
                            param = ( String ) layoutParamList.get( 2 );

                            singleBox = StringUtil.replaceString( singleBox, param, choiceKey,
                                false, false );

                            buf.append( singleBox );
                        }

                    }
                    else
                    {
                        String[] choice = StringUtil.split( filedInfo.getChoiceValue() != null
                            ? filedInfo.getChoiceValue() : "", "," );

                        // 设定数据库实际值
                        String[] defValue = null;
                        if( StringUtil.isStringNull( dbValue ) )
                        {
                            defValue = StringUtil.split( filedInfo.getDefaultValue(), "," );
                        }
                        else
                        {
                            defValue = StringUtil.split( dbValue, "," );
                        }

                        for ( int i = 0; i < choice.length; i++ )
                        {
                            if( StringUtil.isStringNull( choice[i] ) )
                            {
                                continue;
                            }

                            // ${fignSign}
                            param = ( String ) layoutParamList.get( 0 );
                            key = disposeLayoutParamToKey( param );

                            value = this.filedInfo.retrieveModelFiledValueByKey( key ).toString();

                            String[] choiceKV = StringUtil.split( choice[i], "=" );
                            boolean needBoxCheckedStatus = false;
                            for ( int j = 0; j < defValue.length; j++ )
                            {
                                if( choiceKV[1].equals( defValue[j] ) )
                                {
                                    needBoxCheckedStatus = true;
                                    break;
                                }
                            }

                            if( needBoxCheckedStatus )
                            {
                                singleBox = StringUtil.replaceString( layoutPart[1], param, value,
                                    false, false );
                            }
                            else
                            {
                                singleBox = StringUtil.replaceString( layoutPart[0], param, value,
                                    false, false );
                            }

                            // ${value}
                            param = ( String ) layoutParamList.get( 1 );

                            singleBox = StringUtil.replaceString( singleBox, param, choiceKV[1],
                                false, false );

                            // ${key}
                            param = ( String ) layoutParamList.get( 2 );

                            singleBox = StringUtil.replaceString( singleBox, param, choiceKV[0],
                                false, false );

                            buf.append( singleBox );
                        }

                    }

                    this.htmlEditTemplet = buf.toString();
                }
                else if( Constant.METADATA.UPLOAD_IMG_GROUP == bean.getHtmlElementId().intValue() )
                {

                }
                else if( Constant.METADATA.CUSTOM_HTML_CONTENT == bean.getHtmlElementId()
                    .intValue() )
                {
                    this.htmlEditTemplet = filedInfo.getHtmlContent();
                }
                else
                {
                    // layout参数处理
                    for ( int i = 0; i < layoutParamList.size(); i++ )
                    {
                        // ${key}
                        param = ( String ) layoutParamList.get( i );
                        // key
                        key = disposeLayoutParamToKey( param );

                        if( "${siteBase}".equals( param ) )
                        {
                            CmsServerBean cms = JtRuntime.cmsServer;

                            value = cms.getDomainFullPath();
                        }
                        else if( "${simpleMode}".equals( param ) )
                        {
                            // 富文本编辑器特殊处理,简单功能模式
                            if( Constant.METADATA.EDITER == bean.getHtmlElementId().intValue() )
                            {
                                if( Constant.COMMON.OFF.equals( this.filedInfo.getFullEditor() ) )
                                {

                                    value = UE_SIMPLE_MODE;
                                }
                                else
                                {
                                    value = "";
                                }
                            }
                        }

                        else
                        {
                            value = this.filedInfo.retrieveModelFiledValueByKey( key ).toString();
                        }

                        targetLayoutContent = StringUtil.replaceString( targetLayoutContent, param,
                            value, false, false );
                    }

                    // info实际值替换

                    // String valueSign = null;
                    for ( int i = 0; i < valueParamList.size(); i++ )
                    {
                        // $V{key}
                        param = ( String ) valueParamList.get( i );
                        // key
                        key = disposeValueParamToKey( param );

                        // 字段名称
                        // valueSign = this.modelFiledInfoBean
                        // .retrieveModelFiledValueByKey( key ).toString();

                        // // 字段值String形式
                        // value = targetInfo.get(
                        // Constant.METADATA.PREFIX_COLUMN_NAME + valueSign )
                        // .toString();

                        // // 设定数据库实际值
                        // if( "".equals( dbValue ) )
                        // {
                        // dbValue = modelFiledInfoBean.getDefaultValue();
                        // }

                        if( Constant.METADATA.UPLOAD_IMG == ( ( Long ) filedInfo
                            .retrieveModelFiledValueByKey( Constant.METADATA.MF_HE_ID ) )
                            .intValue() )
                        {
                            String reUrl = ( String ) info.get( filedSign + "CmsSysReUrl" );

                            // 强制解析reUrl
                            if( StringUtil.isStringNull( reUrl ) )
                            {
                                reUrl = ServiceUtil
                                    .getImageReUrl( ( String ) info.get( filedSign ) );
                            }

                            // 处理文件类型
                            if( "imageFiledSign".equals( key ) )
                            {
                                if( StringUtil.isStringNull( reUrl ) )
                                {
                                    dbValue = JtRuntime.cmsServer.getDomainFullPath()
                                        + "/core/style/blue/images/no-image.gif";
                                }
                                else
                                {
                                    SecuritySession session = SecuritySessionKeeper
                                        .getSecuritySession();

                                    SiteGroupBean site = ( SiteGroupBean ) session
                                        .getCurrentLoginSiteInfo();

                                    dbValue = site.getSiteImagePrefixUrl()

                                        + StringUtil.replaceString( reUrl, "/", "/imgResize",
                                            false, false );
                                }
                            }

                            if( "imageSrc".equals( key ) )
                            {
                                if( StringUtil.isStringNull( reUrl ) )
                                {
                                    dbValue = JtRuntime.cmsServer.getDomainFullPath()
                                        + "/core/style/blue/images/no-image.gif";
                                }
                                else
                                {
                                    SecuritySession session = SecuritySessionKeeper
                                        .getSecuritySession();

                                    SiteGroupBean site = ( SiteGroupBean ) session
                                        .getCurrentLoginSiteInfo();

                                    dbValue = site.getSiteImagePrefixUrl() + reUrl;
                                }
                            }
                            else if( "width".equals( key ) )
                            {
                                String iw = ( String ) info.get( filedSign + "ImageW" );

                                dbValue = ( iw == null ) ? "" : iw;

                            }
                            else if( "height".equals( key ) )
                            {
                                String ih = ( String ) info.get( filedSign + "ImageH" );

                                dbValue = ( ih == null ) ? "" : ih;
                            }
                            else if( "filedSign".equals( key ) )
                            {
                                if( StringUtil.isStringNull( reUrl ) )
                                {
                                    dbValue = "";
                                }
                                else
                                {
                                    dbValue = ( String ) info.get( filedSign + "ResId" );
                                }
                            }
                        }
                        else if( Constant.METADATA.UPLOAD_MEDIA == ( ( Long ) filedInfo
                            .retrieveModelFiledValueByKey( Constant.METADATA.MF_HE_ID ) )
                            .intValue() )
                        {
                            String reUrl = ( String ) info.get( filedSign + "CmsSysReUrl" );

                            if( StringUtil.isStringNull( reUrl ) )
                            {
                                reUrl =  ServiceUtil.getImageReUrl( ( String ) info.get( filedSign ));
                            }
                            
                            SecuritySession session = SecuritySessionKeeper.getSecuritySession();

                            SiteGroupBean site = ( SiteGroupBean ) session
                                .getCurrentLoginSiteInfo();

                            // 处理文件类型
                            if( "mediaFiledSign".equals( key ) )
                            {
                                if( StringUtil.isStringNull( reUrl ) )
                                {
                                    dbValue = "";
                                }
                                else
                                {
                                    dbValue = site.getSiteMediaPrefixUrl() + reUrl;
                                }
                            }
                            else if( "name".equals( key ) )
                            {
                                SiteResourceBean resBean = resService
                                    .retrieveSingleResourceBeanBySource( reUrl );

                                if( resBean != null )
                                {
                                    dbValue = resBean.getResName();
                                }
                            }
                            else if( "cover".equals( key ) )
                            {
                                SiteResourceBean resBean = resService
                                    .retrieveSingleResourceBeanBySource( reUrl );

                                if( resBean != null )
                                {
                                    if( StringUtil.isStringNull( resBean.getCover() ) )
                                    {
                                        dbValue = "";
                                    }
                                    else
                                    {
                                        dbValue = site.getSiteImagePrefixUrl() + resBean.getCover();
                                    }
                                }
                            }
                            else if( "coverReUrl".equals( key ) )
                            {
                                String cru = ( String ) info.get( filedSign + "CmsSysCoverReUrl" );

                                dbValue = ( cru == null ) ? "" : cru;
                            }
                            else if( "coverName".equals( key ) )
                            {
                                SiteResourceBean resBean = resService
                                    .retrieveSingleResourceBeanBySource( reUrl );

                                if( resBean == null || StringUtil.isStringNull( resBean.getCover() ) )
                                {
                                    dbValue = "";
                                }
                                else
                                {
                                    resBean = resService
                                        .retrieveSingleResourceBeanBySource( resBean.getCover() );

                                    if( resBean != null )
                                    {
                                        dbValue = resBean.getResName();
                                    }
                                }
                            }
                            else if( "type".equals( key ) )
                            {
                                String re = ( String ) info.get( filedSign + "CmsSysReUrl" );

                                if( StringUtil.isStringNull( re ) )
                                {
                                    dbValue = "";
                                }
                                else
                                {
                                    dbValue = StringUtil.subString( re, re.lastIndexOf( "." ) + 1 );
                                }

                            }
                            else if( "showTime".equals( key ) )
                            {
                                String vt = ( String ) info.get( filedSign + "MediaT" );

                                if( StringUtil.isStringNull( vt ) )
                                {
                                    dbValue = "";
                                }
                                else
                                {
                                    dbValue = vt;
                                }

                            }
                            else if( "width".equals( key ) )
                            {
                                String vw = ( String ) info.get( filedSign + "MediaW" );

                                if( StringUtil.isStringNull( vw ) )
                                {
                                    dbValue = "";
                                }
                                else
                                {
                                    dbValue = vw;
                                }

                            }
                            else if( "height".equals( key ) )
                            {
                                String vh = ( String ) info.get( filedSign + "MediaH" );

                                if( StringUtil.isStringNull( vh ) )
                                {
                                    dbValue = "";
                                }
                                else
                                {
                                    dbValue = vh;
                                }
                            }
                            else if( "filedSign".equals( key ) )
                            {
                                if( StringUtil.isStringNull( reUrl ) )
                                {
                                    dbValue = "";
                                }
                                else
                                {
                                    dbValue = ( String ) info.get( filedSign + "ResId" );
                                }
                            }
                        }
                        else if( Constant.METADATA.UPLOAD_FILE == ( ( Long ) filedInfo
                            .retrieveModelFiledValueByKey( Constant.METADATA.MF_HE_ID ) )
                            .intValue() )
                        {
                            String resId = ( String ) info.get( filedSign );

                            if( resId != null && resId.startsWith( "id=" ) )
                            {
                                resId = ServiceUtil.getResId( resId ).toString();

                                info.put( filedSign, resId );
                            }

                            SiteResourceBean resBean = resService
                                .retrieveSingleResourceBeanByResId( Long.valueOf( StringUtil
                                    .getLongValue( resId, -1 ) ) );

                            // 处理文件类型
                            if( "fileInfo".equals( key ) )
                            {
                                if( resBean == null )
                                {
                                    dbValue = "";
                                }
                                else
                                {
                                    dbValue = resBean.getResName()
                                        + "."
                                        + resBean.getFileType()
                                        + " ("
                                        + FileUtil.changeFileSizeToStr( resBean.getResSize()
                                            .longValue() ) + ")";
                                }
                            }
                            else
                            {
                                if( resBean == null )
                                {
                                    dbValue = "";
                                }
                                else
                                {
                                    dbValue = ( String ) info.get( filedSign );
                                }
                            }
                        }

                        targetLayoutContent = StringUtil.replaceString( targetLayoutContent, param,
                            dbValue, false, false );
                    }

                    this.htmlEditTemplet = targetLayoutContent;

                }

                // script
                String script = this.filedInfo.getJavascript();
                String endScript = null;
                if( StringUtil.isStringNull( script ) )
                {
                    endScript = "";
                }
                else
                {
                    endScript = script;
                }

                this.htmlEditTemplet = StringUtil.replaceString( this.htmlEditTemplet,
                    "${jsEvent}", endScript, false, false );

                // style
                String style = this.filedInfo.getStyle();
                String endStyle = null;

                if( StringUtil.isStringNull( style ) )
                {
                    endStyle = "";
                }
                else
                {
                    endStyle = "style=\"" + style + "\"";
                }

                this.htmlEditTemplet = StringUtil.replaceString( this.htmlEditTemplet, "${style}",
                    endStyle, false, false );

                // css
                String css = this.filedInfo.getCssClass();
                String endClass = null;

                if( StringUtil.isStringNull( css ) )
                {
                    endClass = "";
                }
                else
                {
                    endClass = "class=\"" + css + "\"";
                }

                this.htmlEditTemplet = StringUtil.replaceString( this.htmlEditTemplet, "${css}",
                    endClass, false, false );
            }
        }
        
        this.htmlEditTemplet = StringUtil.replaceString( this.htmlEditTemplet, "${ph}",
           filedInfo.getHtmlDesc(), false, false );
        
        String psStr = StringUtil.isStringNull( filedInfo.getHtmlDesc() ) ? "" :"<span class='ps'>"+filedInfo.getHtmlDesc()+"</span>";
        
        if( this.htmlEditTemplet.indexOf( "${ps}" ) != -1)
        {
            this.htmlEditTemplet = StringUtil.replaceString( this.htmlEditTemplet, "${ps}", "", false, false );
             
            this.htmlEditTemplet =  this.htmlEditTemplet + psStr;
        }

        return this.htmlEditTemplet;
    }

    /**
     * 处理html布局参数,将参数替换为值
     * 
     * @param param
     * @return
     */
    private String disposeLayoutParamToKey( String param )
    {
        return StringUtil.replaceString( StringUtil.replaceString( param, "${", "", false, true ),
            "}", "", false, true );
    }

    /**
     * 处理实际值参数,将参数替换为值
     * 
     * @param param
     * @return
     */
    private String disposeValueParamToKey( String param )
    {
        return StringUtil.replaceString( StringUtil.replaceString( param, "$V{", "", false, true ),
            "}", "", false, true );
    }

    // ///////////////////////////// ModelFiledInfoBean API
    // ////////////////////////////////

    public String getAllowableFile()
    {
        return filedInfo.getAllowableFile();
    }

    public String getChoiceValue()
    {
        return filedInfo.getChoiceValue();
    }

    public Long getDataModelId()
    {
        return filedInfo.getDataModelId();
    }

    public String getDefaultValue()
    {
        return filedInfo.getDefaultValue();
    }

    public String getErrorMessage()
    {
        return filedInfo.getErrorMessage();
    }

    public String getFieldSign()
    {
        return filedInfo.getFieldSign();
    }

    public Long getHtmlConfigId()
    {
        return filedInfo.getHtmlConfigId();
    }

    public String getHtmlContent()
    {
        return filedInfo.getHtmlContent();
    }

    public Long getHtmlElementId()
    {
        return filedInfo.getHtmlElementId();
    }

    public Integer getIsMustFill()
    {
        return filedInfo.getIsMustFill();
    }

    public Integer getMaxLength()
    {
        return filedInfo.getMaxLength();
    }

    public Long getMetaDataId()
    {
        return filedInfo.getMetaDataId();
    }

    public Integer getPerdureType()
    {
        return filedInfo.getPerdureType();
    }

    public Long getCapacity()
    {
        return filedInfo.getCapacity();
    }

    public String getRelateFiledName()
    {
        return filedInfo.getRelateFiledName();
    }

    public String getShowName()
    {
        return filedInfo.getShowName();
    }

    public String getDefaultValidate()
    {
        return filedInfo.getDefaultValidate();
    }

    public String getCheckRegex()
    {
        return filedInfo.getCheckRegex();
    }

    public Integer getDataType()
    {
        return filedInfo.getDataType();
    }

    public String getJavascript()
    {
        return filedInfo.getJavascript();
    }

    public String getStyle()
    {
        return filedInfo.getStyle();
    }

    public String getCssClass()
    {
        return filedInfo.getCssClass();
    }

    public String getHtmlDesc()
    {
        return filedInfo.getHtmlDesc();
    }

    public Integer getOrderFlag()
    {
        return filedInfo.getOrderFlag();
    }

    public Integer getQueryFlag()
    {
        return filedInfo.getQueryFlag();
    }

    public Integer getSearchFlag()
    {
        return filedInfo.getSearchFlag();
    }

    public Double getOrderId()
    {
        return filedInfo.getOrderId();
    }

    public Integer getPickFlag()
    {
        return filedInfo.getPickFlag();
    }

    public Integer getEditorH()
    {
        return filedInfo.getEditorH();
    }

    public Integer getEditorW()
    {
        return filedInfo.getEditorW();
    }

    public Integer getMainEditor()
    {
        return filedInfo.getMainEditor();
    }

    public Integer getFullEditor()
    {
        return filedInfo.getFullEditor();
    }

    public Integer getNeedMark()
    {
        return filedInfo.getNeedMark();
    }

    public Integer getImageW()
    {
        return filedInfo.getImageW();
    }

    public Integer getImageH()
    {
        return filedInfo.getImageH();
    }

    public Integer getImageDisposeMode()
    {
        return filedInfo.getImageDisposeMode();
    }

    public Integer getBlankCount()
    {
        return filedInfo.getBlankCount();
    }

    public Integer getIsListField()
    {
        return filedInfo.getIsListField();
    }

    public Integer getListShowSize()
    {
        return filedInfo.getListShowSize();
    }

    public Integer getLinkType()
    {
        return filedInfo.getLinkType();
    }

    public Long getLinkFieldId()
    {
        return filedInfo.getLinkFieldId();
    }

    public Long getLinkModelId()
    {
        return filedInfo.getLinkModelId();
    }

    public Integer getUseSysUrl()
    {
        return filedInfo.getUseSysUrl();
    }

    // 业务扩展字段
    public String getOrderIdVal()
    {
        return filedInfo.getOrderIdVal();
    }

    public String getBlankStr()
    {
        int count = filedInfo.getBlankCount().intValue();

        StringBuffer buf = new StringBuffer();
        for ( int i = 0; i < count; i++ )
        {
            buf.append( "&nbsp;" );
        }
        return buf.toString();
    }

    public String getMustFillStr()
    {
        return filedInfo.getMustFillStr();
    }

    public String getHtmlModeStr()
    {
        return filedInfo.getHtmlModeStr();
    }

    /**
     * 获取定义字段以及基本字段内容
     * 
     * @return
     */
    public Map getInfo()
    {
        return info;
    }

    /**
     * 字段元数据信息
     * 
     * @return
     */
    public ModelFiledInfoBean getFieldInfo()
    {
        return this.filedInfo;
    }

    public String getFieldInfoStr()
    {
        return this.info.get( filedInfo.getFieldSign() ).toString();
    }

}
