package cn.com.mjsoft.cms.common;

import java.sql.Timestamp;

import cn.com.mjsoft.framework.util.DateAndTimeUtil;

/**
 * 
 * @author mjsoft
 * 
 */
public class Constant
{
    public interface COMMON
    {
        // 各种flag的共同状态值
        public static final int FLAG_IN = 1;

        public static final int FLAG_OUT = 0;

        public static final Integer ON = Integer.valueOf( 1 );

        public static final Integer OFF = Integer.valueOf( 0 );

        // 各种flag的共同状态值
        public static final String FLAG_IN_VAL = "1";

        public static final String FLAG_OUT_VAL = "0";

        public static final String EMPTY_STRING = "";

        // 登录状态
        public static final Integer LOGIN_SUCCESS = Integer.valueOf( 1 );

        public static final Integer LOGIN_FAIL = Integer.valueOf( 0 );

        public static final Integer LOGIN_BLACK_IP = Integer.valueOf( -1 );

        public final static String POST = "POST";

        public final static String GET = "GET";

    }

    public interface SITE_CHANNEL
    {
        public final static String RANDOM_INPUT_RAND_CODE_KEY = "_____JTOPCMS_RANDOM_KEY_FOR_INPUT_____";

        public final static String RANDOM_INPUT_RAND_CODE_KEY_STR = "_____JTOPCMS_RANDOM_KEY_FOR_INPUT_STR_____";

        public static final String DEF_PAGE_CODE = "GBK";

        public static final String HTML_BLANK_CHAR = "&nbsp;";

        public static final Long SITE_NODE_FLAG = Long.valueOf( -9999 );

        public static final int LIMIT_CLASS_SIZE = 999;

        public static final String LIMIT_PAGE_PUB_SIZE = "10";

        // 用于内部节点操作
        public static final Integer TRUE_FLAG = Integer.valueOf( "1" );

        public static final Integer PAGE_PRODUCE_H_TYPE = Integer.valueOf( "3" );

        public static final Integer PAGE_PRODUCE_D_TYPE = Integer.valueOf( "1" );

        /**
         * 站点信息分类类型
         */
        public static final Integer CLASS_TYPE_CONTENT_CLASS = Integer.valueOf( "1" );

        public static final Integer CLASS_TYPE_OUT_LINK = Integer.valueOf( "2" );

        public static final Integer CLASS_TYPE_SINGLE_PAGE = Integer.valueOf( "3" );

        public static final Integer CLASS_TYPE_SPECIAL = Integer.valueOf( "4" );

        public static final Integer CLASS_TYPE_SPECIAL_SUBJECT = Integer.valueOf( "5" );

        public static final Integer CLASS_TYPE_SPECIAL_SUB_NODE = Integer.valueOf( "6" );

        public static final Integer CLASS_TYPE_DEF_FORM = Integer.valueOf( "7" );

        public static final Integer CHANNEL_SHOW = Integer.valueOf( 1 );

        public static final Integer CHANNEL_ALL_SHOW = Integer.valueOf( 3 );

        /**
         * 栏目配置
         */
        public static final String CHANNEL_TEMPLATE = "1";

        public static final String LIST_TEMPLATE = "2";

        public static final String CONTENT_TEMPLATE = "3";

        public static final String PUB_CHANNEL = "1";

        public static final String PUB_LIST = "2";

        public static final String PUB_CONTENT = "3";

        public static final String STATIC_RULE_CHANNEL = "1";

        public static final String STATIC_RULE_LIST = "2";

        public static final String STATIC_RULE_CONTENT = "3";

        public static final String GUIDE_IMG_EDITOR = "1";

        public static final String GUIDE_IMG_HOME = "2";

        public static final String GUIDE_IMG_CHANNEL = "3";

        public static final String GUIDE_IMG_CLASS = "4";

        public static final String GUIDE_IMG_CONTENT = "5";

        public static final String GUIDE_EDITOR_MARK = "6";

        public static final String CHANNEL_SHOW_STATUS = "1";

        public static final String CHANNEL_PAGE_LIMIT = "2";

        public static final String CHANNEL_SYNC_PUB = "3";

        public static final String CHANNEL_SEARCH_STATUS = "4";

        public static final String CHANNEL_CONTENT_RANGE = "5";

        public static final String CHANNEL_MEMBER_CONTENT = "6";

        public static final String CHANNEL_CONTENT_MODEL = "7";

        public static final String CHANNEL_CONTENT_WORKFLOW = "8";

        public static final String CHANNEL_DEF_MODEL = "9";

        public static final Integer COPY_RANGE_ALL_CHILD = Integer.valueOf( 1 );

        public static final Integer COPY_RANGE_SAME_MODEL = Integer.valueOf( 2 );

        public static final Integer COPY_RANGE_SINGLE_PAGE = Integer.valueOf( 3 );

        /**
         * 编辑器组件类型
         */
        public static final Integer EDITOR_MEDIA = Integer.valueOf( 1 );

        public static final Integer EDITOR_IMAGE = Integer.valueOf( 2 );

        public static final Integer EDITOR_FILE = Integer.valueOf( 3 );

        /**
         * 水印位置对应编码
         */
        public static final String MARK_NW = "northwest";

        public static final String MARK_N = "north";

        public static final String MARK_NE = "northeast";

        public static final String MARK_WE = "west";

        public static final String MARK_CE = "center";

        public static final String MARK_EA = "east";

        public static final String MARK_SW = "southwest";

        public static final String MARK_SO = "south";

        public static final String MARK_SE = "southeast";

        /**
         * 服务器传输类型
         */

        public static final Integer PROTOCOL_FTP = Integer.valueOf( 1 );

        public static final Integer PROTOCOL_SFTP = Integer.valueOf( 2 );

        public static final Integer PROTOCOL_CURRENT_HOST = Integer.valueOf( 3 );

        public static final String NO_DOMAIN_INFO = "NO-DOMAIN-INFO";

        /**
         * 服务器文件类型
         */
        public static final Integer TRAN_TYPE_HTML = Integer.valueOf( 1 );

        public static final Integer TRAN_TYPE_IMAGE = Integer.valueOf( 2 );

        public static final Integer TRAN_TYPE_MEDIA = Integer.valueOf( 3 );

        public static final Integer TRAN_TYPE_FILE = Integer.valueOf( 4 );

        public static final Integer TRAN_TYPE_TEMPLATE = Integer.valueOf( 0 );

        /**
         * 字段资源类型
         */
        public static final Integer RES_IMAGE = Integer.valueOf( 1 );

        public static final Integer RES_MEDIA = Integer.valueOf( 2 );

        public static final Integer RES_FILE = Integer.valueOf( 3 );

        public static final Integer RES_IMG_GROUP = Integer.valueOf( 4 );

        public static final Integer RES_ARTICLE = Integer.valueOf( 5 );

        /**
         * 字段时间类型
         */
        public static final Integer RES_DATETIME = Integer.valueOf( 6 );

        /**
         * 文件监听事件
         */
        public static final Integer FILE_EVENT_CREATED = Integer.valueOf( 1 );

        public static final Integer FILE_EVENT_MODIFIED = Integer.valueOf( 2 );

        public static final Integer FILE_EVENT_DELETED = Integer.valueOf( 3 );

        public static final Integer FILE_EVENT_RENAMED = Integer.valueOf( 4 );

        /**
         * 处理状态
         */
        public static final Integer FILE_TRAN_STATUS_WAIT = Integer.valueOf( 0 );

        public static final Integer FILE_TRAN_STATUS_SUCCESS = Integer.valueOf( 1 );

        public static final Integer FILE_TRAN_STATUS_ERROR = Integer.valueOf( -1 );
    }

    public interface CONTENT
    {
        public static final String URL_SEP = "/";

        /**
         * 站点资源分类目录
         */
        public static final String TEMPLATE_BASE = "template";

        public static final String TEMPLATE_STYLE = "sys_template_style";

        public static final String TEMPLATE_TEMP_BASE = "sys_template_temp";

        public static final String HTML_BASE = "html";

        public static final String IMG_BASE = "upload";

        public static final String MEDIA_BASE = "video";

        public static final String MEDIA_IMG_BASE = "GUIDE";

        public static final String FILE_BASE = "file";

        public static final String TEMP_FILE_BASE = "sys_file_temp";

        public static final String TEMP_IMAGE_BASE = "sys_image_temp";

        public static final String CHANNEL_BASE = "CHANNEL";

        public static final String BLOCK_BASE = ".block";

        public static final String SORT_LEFT = "left";

        public static final String SORT_RIGHT = "right";

        public static final String GEN_CONTENT_URL_PREFIX = "JTOPCMS-CONTENT-URL:";

        // public static final String CONTENT_BASE = "content/";

        public static final String CONTENT_BASE = "";

        public static final String RESIZE_IMG_FLAG = "imgResize";

        // index dir
        public static final String INDEX_BASE = "_sysIndex_";

        // 以下为实际数据库ID
        public static long ARTICLE_TYPE_ID = 1;
        public static long PHOTO_TYPE_ID = 2;
        public static long VIDEO_TYPE_ID = 3;
        public static long DOWNLOAD_TYPE_ID = 4;

        public static final String PHOTO = "图集";
        public static final String DOWNLOAD = "下载";
        public static final String ARTICLE = "文章";
        public static final String VIDEO = "视频";

        public static final String USER_DEFINE = "自定义";

        public static final int IMMEDIATELY_STATIC = 10;

        public static final int NOT_IMMEDIATELY_STATIC = 20;

        public static final Integer CONTENT_PRODUCE_OUT_LINK_TYPE = Integer.valueOf( "2" );

        /**
         * 评论顶踩
         */
        public static final String DIGG_SUPPORT = "s";

        public static final String DIGG_AGAINST = "a";

        // 排序
        public static final int IS_SYS_ORDER = 1;

        public static final int NOT_SYS_ORDER = 0;

        public static final double OREDER_UNIT = 0.000001;

        public static final String ORDER_FORMAT = "########0.0#####";

        public static final double MAX_ORDER_ID_FLAG = 999999999999999999.999999;

        public static final double MIN_ORDER_ID_FLAG = 0;

        public static final long MAX_ID_FLAG = 999999999999999999L;

        public static final long MIN_ID_FLAG = 0;

        public static final String DEFAULT_ORDER = "default";

        public static final String ADD_DATE_ORDER = "addDate";

        public static final String PUB_DATE_ORDER = "pubDate";

        public static final String CLICK_COUNT_ORDER = "click";

        public static final String CLICK_COUNT_ORDER_DAY = "dayClick";

        public static final String CLICK_COUNT_ORDER_WEEK = "weekClick";

        public static final String CLICK_COUNT_ORDER_MONTH = "monthClick";

        public static final String COMM_COUNT_ORDER = "comm";

        public static final String COMM_COUNT_ORDER_DAY = "dayComm";

        public static final String COMM_COUNT_ORDER_WEEK = "weekComm";

        public static final String COMM_COUNT_ORDER_MONTH = "monthComm";

        public static final String SUPPORT_ORDER = "su";

        public static final String AGAINST_ORDER = "ag";

        public static final String ID_ORDER = "id";

        public static final String DEFAULT_ORDER_VAR = "orderIdFlag";

        public static final String ADD_DATE_ORDER_VAR = "addTime";

        public static final String PUB_DATE_ORDER_VAR = "appearStartDateTime";

        public static final String CLICK_COUNT_ORDER_VAR = "clickCount";

        public static final String CLICK_COUNT_ORDER_DAY_VAR = "clickDayCount";

        public static final String CLICK_COUNT_ORDER_WEEK_VAR = "clickWeekCount";

        public static final String CLICK_COUNT_ORDER_MONTH_VAR = "clickMonthCount";

        public static final String COMM_COUNT_ORDER_VAR = "commCount";

        public static final String COMM_COUNT_ORDER_DAY_VAR = "commDayCount";

        public static final String COMM_COUNT_ORDER_WEEK_VAR = "commWeekCount";

        public static final String COMM_COUNT_ORDER_MONTH_VAR = "commMonthCount";

        public static final String SUPPORT_ORDER_VAR = "supportCount";

        public static final String AGAINST_ORDER_VAR = "againstCount";

        public static final String ID_ORDER_VAR = "contentId";

        public static final String UP_ORDER_WAY_VAR = "asc";

        public static final String DOWN_ORDER_WAY_VAR = "desc";

        public static final String UP_ORDER_WAY = "up";

        public static final String DOWN_ORDER_WAY = "down";

        // 内容过滤
        public static final String CREATOR_ALL_FILTER = "0";

        public static final String CREATOR_MY_FILTER = "1";

        public static final String CREATOR_OTHER_FILTER = "2";
        
        public static final String CREATOR_ORG = "3";

        public static final String CREATOR_ORG_CHILD = "4";

        public static final String TOP_FILTER = "top";

        public static final String COMMEND_FILTER = "commend";

        public static final String HAME_IMG_FILTER = "homeImg";

        public static final String HAME_IMG_FILTER_VAR = "homeImgFlag";

        public static final String CHANNEL_IMG_FILTER = "channelImg";

        public static final String CHANNEL_IMG_FILTER_VAR = "channelImgFlag";

        public static final String CLASS_IMG_FILTER = "classImg";

        public static final String CLASS_IMG_FILTER_VAR = "classImgFlag";

        public static final String CONTENT_IMG_FILTER = "contentImg";

        public static final String CONTENT_IMG_FILTER_VAR = "contentImgFlag";

        // 内容属性
        public static final String PUBLISH_SUCC_TYPE = "pubSuccess";

        public static final String NOT_START_PUBLISH_TYPE = "notStartPub";

        // public static final String WAIT_PUBLISH_TYPE = "waitPub";

        public static final String PUBLISH_END_TYPE = "pubEnd";

        public static final String ARCHIVE_OK_TYPE = "archiveSuccess";

        public static final Timestamp MAX_DATE = DateAndTimeUtil.getTimestamp(
            "9999-12-31 23:59:59", DateAndTimeUtil.DEAULT_FORMAT_YMD );

        public static final Timestamp MIN_DATE = DateAndTimeUtil.getTimestamp(
            "1000-01-01 00:00:00", DateAndTimeUtil.DEAULT_FORMAT_YMD );

        // 文章内容分页点前缀
        public static final String CONTENT_PAGE_PREFIX = "<table class=\"jtopcms_system_page_flag\"";
        public static final String CONTENT_PAGE_END = "</strong></span></td></tr></tbody></table>";
        public static final String CONTENT_PAGE_FLAG_ID = "jtopcms_system_page_flag_info";
        public static final String CONTENT_PAGE_SPLIT_STR = "\\(\\(\\(\\*\\)\\)\\)";
        public static final String CONTENT_PAGE_SPLIT_CLASS = "___jtopcms_system_page_flag___";
        public static final int CONTENT_PAGE_END_STR_SIZE = 51;

        // 静态发布标志

        public static final String HTML_PUB_S_HTML = ".shtml";

        public static final String HTML_PUB_HTML = ".html";

        public static final String HTML_PUB = "1";

        public static final String HTML_PUB_S = "2";

        public static final String HTML_PUB_ACTION_FLAG = "____JTOPCMS____SYSTEM____HTML_PUB____ACTION____FLAG____";

        public static final String HTML_PUB_CURRENT_SITE = "____JTOPCMS____SYSTEM____HTML_PUB____CURR____SITE____FLAG____";

        public static final String HTML_PUB_ACTION_STATIC_BLOCKLIST_FLAG = "____JTOPCMS_SYSTEM_HTML_PUB_ACTION_NEED_STATIC_BLOCKLIST_FLAG____";

        // 搜索索引处理状态
        // public static final Integer HTML_PUB_S_HTML = ".shtml";

    }

    public interface BLOCK
    {
        public static final Integer BLOCK_TYPE_AUTO = Integer.valueOf( 1 );

        public static final Integer BLOCK_TYPE_CUSTOM = Integer.valueOf( 2 );
    }

    public interface PAGE
    {
        public static final int PAGE_ACTION_NEXT = 1;

        public static final int PAGE_ACTION_PREV = -1;

        public static final int PAGE_ACTION_HEAD = 2;

        public static final int PAGE_ACTION_END = -2;

        public static final int PAGE_ACTION_JUMP = 3;
    }

    public interface TEMPLET
    {
        public static final String SYSTEM_SITE_ROOT_FLAG = "site";

        public static final Integer TEMPLATE_TYPE_CHANNEL = Integer.valueOf( 1 );

        public static final Integer TEMPLATE_TYPE_LIST = Integer.valueOf( 2 );

        public static final Integer TEMPLATE_TYPE_CONTENT = Integer.valueOf( 3 );
    }

    public interface RESOURCE
    {
        public static final String RULE = "*.jsp;*.xml;*.shtml;*.htm;*.html;*.js;*.css;*.txt;*.pdf;*.swf;*.flv;*.png;*.jpeg;*.gif;*.bmp;*.jpg;*.f4v;*.avi;*.mpg;*.mpeg;*.mp3;*.wav;*.mp4;*.doc;*.docx;*.pptx;*.ppt;*.xlsx;*.xls;*.vsd;*.zip;*.rar;*.dat";// 设置可上传的类型

        public static final String IMAGE_RULE = "*.png;*.jpeg;*.gif;*.bmp;*.jpg";

        public static final String IMAGE_RULE_VAL = "png,jpeg,gif,bmp,jpg";

        public static final String VIDEO_RULE = "*.swf;*.flv;*.f4v;*.avi;*.mpg;*.mpeg;*.mp4;*.3gp;*.rm;*.rmvb;*.wmv;*.wma";

        public static final String VIDEO_RULE_VAL = "swf,flv,f4v,avi,mpg,mpeg,mp4,rm,wmv,wma,mp3,wav";

        public static final String MUSIC_RULE = "*.mp3;*.wav";

        public static final String DOC_RULE = "*.doc;*.docx;*.pptx;*.ppt;*.xlsx;*.xls;*.vsd;*.pdf;*.txt";

        public static final String FILE_RULE = "*.txt;*.pdf;*.swf;*.flv;*.png;*.jpeg;*.gif;*.bmp;*.jpg;*.f4v;*.avi;*.mpg;*.mpeg;*.mp3;*.wav;*.mp4;*.doc;*.docx;*.pptx;*.ppt;*.xlsx;*.xls;*.vsd;*.zip;*.rar;*.dat";

        public static final String FILE_RULE_VAL = "txt,pdf,swf,flv,png,jpeg,gif,bmp,jpg,f4v,avi,mpg,mpeg,mp3,wav,mp4,doc,docx,pptx,ppt,xlsx,xls,vsd,zip,rar,dat";

        public static final String LIST_FILE_MODE_ALL = "all";

        public static final String LIST_FILE_MODE_FOLDER = "folder";

        /**
         * 文件类型分类
         */
        public static final Integer IMAGE_RES_TYPE = Integer.valueOf( 1 );

        public static final Integer VIDEO_RES_TYPE = Integer.valueOf( 2 );

        public static final Integer MUSIC_RES_TYPE = Integer.valueOf( 3 );

        public static final Integer DOC_RES_TYPE = Integer.valueOf( 4 );

        public static final Integer ANY_RES_TYPE = Integer.valueOf( 5 );

        /**
         * 图片缩放模式
         */
        public static final Integer IMAGE_DISPOSE_MODE_NO = Integer.valueOf( 0 );

        public static final Integer IMAGE_DISPOSE_MODE_W = Integer.valueOf( 1 );

        public static final Integer IMAGE_DISPOSE_MODE_H = Integer.valueOf( 2 );

        public static final Integer IMAGE_DISPOSE_MODE_W_AND_H = Integer.valueOf( 3 );

        /**
         * 图片缩放质量
         */

        public static final int IMAGE_RESIZE_Q_MAX = 100;

        public static final int IMAGE_RESIZE_Q_MID = 90;
        
        /**
         * 云存储类型
         */

        public static final String CLOUD_COS = "TXCOS";

        public static final String CLOUD_OSS = "ALOSS";
        
        public static final String CLOUD_QN = "QN";

    }

    public interface SECURITY
    {
        public static final Long SYSTEM_ENTRY_RES_FLAG = Long.valueOf( 2 );

        public static final Integer RES_IS_USE_STATE = Integer.valueOf( 1 );

        public static final Integer RES_IS_ALL_USE_STATE = Integer.valueOf( -1 );

        public static final Integer DATA_P_TYPE_SIMPLE = Integer.valueOf( 1 );

        public static final Integer DATA_P_TYPE_ACC = Integer.valueOf( 2 );;

        public static final String SYS_SEC_CONTENT_ACC_FLAG = "system_content_acc_class";

        public static final String SYS_SEC_CLASS_ACC_FLAG = "system_class_acc_class";

        public static final String SYS_SEC_SPEC_ACC_FLAG = "system_spec_acc_class";

        /**
         * 系统入口
         */
        public static final Integer RES_TYPE_ENTRY = Integer.valueOf( 1 );

        /**
         * 模块分类
         */
        public static final Integer RES_TYPE_CLASS = Integer.valueOf( 2 );

        /**
         * 菜单
         */
        public static final Integer RES_TYPE_ACT_LINK = Integer.valueOf( 3 );

        /**
         * 受保护URL组合
         */
        public static final Integer RES_TYPE_GROUP = Integer.valueOf( 4 );

        /**
         * 受保护URL
         */
        public static final Integer RES_TYPE_URL = Integer.valueOf( 5 );

        /**
         * 细粒度保护类型
         */
        public static final Long DATA_SEC_CONTENT = Long.valueOf( 1 );

        public static final Long DATA_SEC_CLASS = Long.valueOf( 2 );

        public static final Long DATA_SEC_SPEC = Long.valueOf( 3 );

        public static final Long DATA_SEC_COMMEND = Long.valueOf( 4 );

        public static final Long DATA_SEC_LEAVE_MSG = Long.valueOf( 5 );
        
        public static final Long DATA_SEC_FORM_DATA = Long.valueOf( 6 );
        
        //登录模式
        public static final Integer ADMIN_LG_M1 = Integer.valueOf( 1 );
        
        public static final Integer ADMIN_LG_M2 = Integer.valueOf( 2 );

        public static final Integer ADMIN_LG_M3 = Integer.valueOf( 3 );

        public static final Integer ADMIN_LG_D_M = Integer.valueOf( 1 );
        
        public static final Integer ADMIN_LG_H_M = Integer.valueOf( 2 );

 
    }

    public interface METADATA
    {
        /**
         * 模型类别
         */
        public static final Integer MODEL_TYPE_SYSTEM = Integer.valueOf( 1 );

        public static final Integer MODEL_TYPE_CONTENT = Integer.valueOf( 2 );

        public static final Integer MODEL_TYPE_CHANNEL = Integer.valueOf( 3 );

        public static final Integer MODEL_TYPE_GBOOK = Integer.valueOf( 4 );

        public static final Integer MODEL_TYPE_ADVERT_CONFIG = Integer.valueOf( 5 );

        public static final Integer MODEL_TYPE_ADVERT_CONTENT = Integer.valueOf( 6 );
       

        public static final Integer MODEL_TYPE_SITE = Integer.valueOf( 7 );

        public static final Integer MODEL_TYPE_MEMBER = Integer.valueOf( 8 );

        public static final Integer MODEL_TYPE_DEF_FORM = Integer.valueOf( 9 );
        
        
        public static final Integer MODEL_TYPE_COMMEND = Integer.valueOf( 10 );


        /**
         * 模型资源类别
         */
        public static final Integer MODEL_RES_NO = Integer.valueOf( 0 );

        public static final Integer MODEL_RES_ARTICLE = Integer.valueOf( 1 );

        public static final Integer MODEL_RES_PHOTO = Integer.valueOf( 2 );

        // public static final Integer MODEL_TYPE_CHANNEL = Integer.valueOf( 3
        // );

        /**
         * 系统模型默认ID
         */
        public static final Long SYSTEM_MODEL_ARTICLE_ID = Long.valueOf( 1 );

        public static final Long SYSTEM_MODEL_PHOTO_ID = Long.valueOf( 2 );

        public static final Long SYSTEM_MODEL_VIDEO_ID = Long.valueOf( 3 );

        public static final Long SYSTEM_MODEL_DOWNLOAD_ID = Long.valueOf( 4 );

        // 模板目录
        public static final String MDEL_TEMPLET = "TEMPLET";

        public static final Integer MD_IS_USE_STATE = Integer.valueOf( 1 );

        public static final Integer MD_IS_ALL_STATE = Integer.valueOf( -1 );

        // 模型可见
        public static final String P_MODE = "1";

        public static final String S_MODE = "2";

        /**
         * Html元素类型唯一ID
         */

        public static final int TEXT = 1;

        public static final int TEXTAREA = 2;

        public static final int EDITER = 3;

        public static final int SELECT = 4;

        public static final int RADIO = 5;

        public static final int CHECK_BOX = 6;

        public static final int DATE = 7;

        public static final int TIME = 8;

        public static final int DATETIME = 9;

        public static final int UPLOAD_FILE = 10;

        public static final int UPLOAD_IMG = 11;

        public static final int UPLOAD_MEDIA = 12;

        public static final int CUSTOM_HTML_CONTENT = 13;

        public static final int UPLOAD_IMG_GROUP = 14;

        public static final int INNER_DATA = 15;

        /**
         * 数据类型
         */

        public static final Integer TEXT_TYPE = Integer.valueOf( 1 );

        public static final Integer LONGTEXT_TYPE = Integer.valueOf( 2 );

        public static final Integer INT_TYPE = Integer.valueOf( 3 );

        public static final Integer LONG_TYPE = Integer.valueOf( 4 );

        public static final Integer DOUBLE_TYPE = Integer.valueOf( 5 );

        public static final Integer DATETIME_TYPE = Integer.valueOf( 6 );

        public static final Integer DATE_TYPE = Integer.valueOf( 7 );

        public static final Integer TIME_TYPE = Integer.valueOf( 8 );

        /**
         * sql类型
         */

        public static final Integer MYSQL_VARCHAR = Integer.valueOf( 1 );

        public static final Integer MYSQL_TEXT = Integer.valueOf( 2 );

        public static final Integer MYSQL_LONGTEXT = Integer.valueOf( 3 );

        public static final Integer MYSQL_INT = Integer.valueOf( 4 );

        public static final Integer MYSQL_BIGINT = Integer.valueOf( 5 );

        public static final Integer MYSQL_DOUBLE = Integer.valueOf( 6 );

        public static final Integer MYSQL_DATETIME = Integer.valueOf( 7 );

        public static final Integer MYSQL_DATE = Integer.valueOf( 8 );

        public static final Integer MYSQL_TIME = Integer.valueOf( 9 );

        /**
         * 数据层元数据表信息
         */
        public static final String PREFIX_TABLE_NAME = "jtopcms_model_";

        public static final String PREFIX_COLUMN_NAME = "jtopcms_def_";

        public static final String PREFIX_DT_ORDER_COLUMN_NAME = "jtopcms_ord_";

        public static final String PREFIX_QUERY_CACHE_NAME = "tmp";

        public static final String PREFIX_HTML_ID_FLAG = "sys_jtopcms_define_";

        public static final String FILED_SIGN = "filedSign";

        public static final String CONTENT_ID_NAME = "contentId";

        /**
         * 模型元数据key
         */
        public static final String MF_HC_ID = "htmlConfigId";

        public static final String MF_HE_ID = "htmlElementId";

        public static final String MF_MD_ID = "metaDataId";

        public static final String MF_DM_ID = "dataModelId";

        public static final String MF_IS_MUST_FILL = "isMustFill";

        public static final String MF_MAX_LENGTH = "maxLength";

        public static final String MF_DEFAULT_VALUE = "defaultValue";

        public static final String MF_CHOICE_VALUE = "choiceValue";

        public static final String MF_ERROR_MESSAGE = "errorMessage";

        public static final String MF_HTML_CONTENT = "htmlContent";

        public static final String MF_DEFAULT_CHECK = "defaultValidate";

        public static final String MF_CHECK_REGEX = "checkRegex";

        // public static final String MF_WIDTH = "width";
        //
        // public static final String MF_HEIGHT = "height";

        public static final String MF_HTML_DESC = "htmlDesc";

        public static final String MF_DATA_TYPE = "dataType";

        public static final String MF_STYLE = "style";

        public static final String MF_CSS_CLASS = "cssClass";

        public static final String MF_JS = "javascript";

        public static final String MF_ALLOW_FILE = "allowableFile";

        public static final String MF_SHOW_NAME = "showName";

        public static final String MF_RELATE_FILED_NAME = "relateFiledName";

        public static final String MF_PERDURE_TYPE = "perdureType";

        public static final String MF_CAPACITY = "capacity";

        public static final String MF_FILED_SIGN = "filedSign";

        public static final String MF_FULL_EDITOR = "fullEditor";

        public static final String MF_IMAGE_DISPOSE_MODE = "imageDisposeMode";

        public static final String MF_IMAGE_H = "imageH";

        public static final String MF_IMAGE_W = "imageW";

        public static final String MF_LINK_MODEL_ID = "linkModelId";

        public static final String MF_LINK_FIELD_ID = "linkFieldId";

        public static final String MF_LINK_TYPE = "linkType";

        public static final String MF_MAIN_EDITOR = "mainEditor";

        public static final String MF_EDITOR_W = "editorW";

        public static final String MF_EDITOR_H = "editorH";

        public static final String MF_NEED_MARK = "needMark";

        public static final String MF_BLANK_COUNT = "blankCount";

        public static final String MF_IS_LIST_FIELD = "isListField";

        public static final String MF_LIST_SHOW_SIZE = "listShowSize";

        public static final String MF_USE_SYS_URL = "useSysUrl";

        public static final String MF_QUERY_FLAG = "queryFlag";

        public static final String MF_ORDER_FLAG = "orderFlag";

        public static final String MF_SEARCH_FLAG = "searchFlag";

        public static final String MF_PICK_FLAG = "pickFlag";

        public static final String MF_ORDER_ID = "orderId";

        /**
         * 默认验证规则
         */
        public static final Integer CHECK_PHONE = Integer.valueOf( 1 );

        public static final Integer CHECK_MOBILE = Integer.valueOf( 2 );

        public static final Integer CHECK_EMAIL = Integer.valueOf( 3 );

        public static final Integer CHECK_ZIP_CODE = Integer.valueOf( 4 );

        public static final Integer CHECK_URL = Integer.valueOf( 5 );

        public static final Integer CHECK_IP = Integer.valueOf( 6 );

        public static final Integer CHECK_USER_NAME = Integer.valueOf( 7 );

        public static final Integer CHECK_PASSWORD = Integer.valueOf( 8 );

        public static final Integer CHECK_CHN_CHAR = Integer.valueOf( 9 );

        public static final Integer CHECK_ENG_CHAR = Integer.valueOf( 10 );

        public static final Integer CHECK_ANY_DIT = Integer.valueOf( 11 );

        public static final Integer CHECK_INT = Integer.valueOf( 12 );

        public static final Integer CHECK_DOUBLE = Integer.valueOf( 13 );

        public static final Integer CHECK_DATETIME = Integer.valueOf( 14 );

        public static final Integer CHECK_DATE = Integer.valueOf( 15 );

        public static final Integer CHECK_TIME = Integer.valueOf( 16 );

    }

    public interface WORKFLOW
    {
        /**
         * 审核状态
         */
        // 全状态
        public static final Integer CENSOR_ALL_STATUS = Integer.valueOf( -9999 );

        // 采集内容等待状态
        public static final Integer CENSOR_STATUS_PICK_WAIT = Integer.valueOf( -4 );

        // 删除为垃圾(此状态已不使用,但保留值)
        public static final Integer CENSOR_STATUS_TRASH = Integer.valueOf( -3 );

        // 草稿
        public static final Integer CENSOR_STATUS_DRAFT = Integer.valueOf( -1 );

        // 重新编辑，并请求进入工作流
        public static final Integer CENSOR_STATUS_IN_EDIT = Integer.valueOf( -2 );

        // 在工作流处理中
        public static final Integer CENSOR_STATUS_IN_FLOW = Integer.valueOf( 0 );

        // 审查成功
        public static final Integer CENSOR_STATUS_SUCCESS = Integer.valueOf( 1 );

        // 等待发布
        public static final Integer CENSOR_STATUS_WAIT_PUBLISH = Integer.valueOf( 2 );
        // 下线
        public static final Integer CENSOR_STATUS_WITHDRAW = Integer.valueOf( 3 );

        // 归档
        public static final Integer CENSOR_STATUS_PIGEONHOLE = Integer.valueOf( 4 );

        // 不通过,退回
        public static final Integer CENSOR_STATUS_RETURN = Integer.valueOf( 5 );

        /**
         * 流程操作状态
         */
        // 未读
        public static final int OPER_NOT_READ = 0;
        // 流程中
        public static final int OPER_IN_FLOW = 1;
        // 重新编辑
        public static final int OPER_EDIT_AGAIN = 2;
        // 退到原点
        public static final int OPER_REJECT_START = 3;
        // 不在处理中
        public static final int OPER_NOT_POSSESS = 0;
        // 处理中
        public static final int OPER_IS_POSSESS = 1;

        /**
         * 审核动作
         */
        public static final String PASS_ACT = "audit_pass";

        public static final String REJECT_ACT = "audit_reject";

        public static final String DRAFT = "draft";

        /**
         * 参与审核者身份类型
         */
        public static final Integer ROLE_TYPE = Integer.valueOf( 1 );

        public static final Integer USER_TYPE = Integer.valueOf( 2 );

        public static final Integer ORG_TYPE = Integer.valueOf( 3 );

        /**
         * 默认开始和结束动作ID
         */
        public static final Long REJECT_ACTION_ID_VALUE = Long.valueOf( -5 );
        
        public static final Long DRAFT_ACTION_ID_VALUE = Long.valueOf( -4 );

        public static final Long RE_EDIT_ACTION_ID_VALUE = Long.valueOf( -3 );

        public static final Long START_ACTION_ID_VALUE = Long.valueOf( -2 );

        public static final Long END_ACTION_ID_VALUE = Long.valueOf( -1 );
        
        /**
         * 工作流处理类型
         */
        public static final Integer INFO_TYPE_CONTENT = Integer.valueOf( 1 );

        public static final Integer INFO_TYPE_GB = Integer.valueOf( 2 );


    }

    public interface JOB
    {
        public static final Integer PERROID_SEC = Integer.valueOf( 1 );

        public static final Integer PERROID_MIN = Integer.valueOf( 2 );

        public static final Integer PERROID_HOUR = Integer.valueOf( 3 );

        public static final Integer PERROID_DAY = Integer.valueOf( 4 );

        /**
         * 周期
         */
        // 秒周期
        public static final Integer PERIOD_SEC = Integer.valueOf( 1 );

        // 分种周期
        public static final Integer PERIOD_MIN = Integer.valueOf( 2 );

        // 小时周期
        public static final Integer PERIOD_HOUR = Integer.valueOf( 3 );

        // 天周期
        public static final Integer PERIOD_DAY = Integer.valueOf( 4 );

        // 月周期
        public static final Integer PERIOD_MON = Integer.valueOf( 5 );

        /**
         * 触发类型
         */
        public static final Integer TRIGGER_PERIOD = Integer.valueOf( 1 );

        public static final Integer TRIGGER_DAY = Integer.valueOf( 2 );

        public static final Integer TRIGGER_WEEK = Integer.valueOf( 3 );

        public static final Integer TRIGGER_MONTH = Integer.valueOf( 4 );

        public static final Integer TRIGGER_CRON = Integer.valueOf( 5 );

        /**
         * CRON
         */
        public static final String EVERY_DAY_EVERY_HOUR = "0 00 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23 * * ?";

        /**
         * 索引任务类型
         */
        public static final Integer SEARCH_INDEX_ADD = Integer.valueOf( 1 );

        public static final Integer SEARCH_INDEX_DEL = Integer.valueOf( 0 );

        public static final Integer SEARCH_INDEX_EDIT = Integer.valueOf( 2 );
    }

    public interface PICK
    {
        public static final String START_REG = "{";

        public static final String END_REG = "}";

        public static final String START_GREEDY = "[";

        public static final String END_GREEDY = "]";

        public static final String HTML_HREF_NODE = "href";

        /**
         * 数字
         */
        public static final String REG_D = "\\d";

        public static final String REG_D_FLAG = "数字";

        /**
         * 非数字
         */
        public static final String REG_NOT_D = "\\D";

        public static final String REG_NOT_D_FLAG = "非数字";

        /**
         * [a-zA-Z_0-9]字母或数字
         */
        public static final String REG_W = "\\w";

        public static final String REG_W_FLAG = "字母或数字";

        /**
         * [a-zA-Z]字母
         */
        public static final String REG_C = "[a-zA-Z]";

        public static final String REG_C_FLAG = "字母";

        // /**
        // * 非单词字符
        // */
        // public static final String REG_NOT_W = "\\W";
        //        
        // public static final String REG_NOT_W = "符号";

        /**
         * 任意字符
         */
        public static final String REG_ANY = ".";

        public static final String REG_ANY_FLAG = "任意";

    }

    public interface ADVERT
    {

        public static final Integer ADVERT_CONFIG_POS_TYPE = Integer.valueOf( 1 );

        public static final Integer ADVERT_CONFIG_CON_TYPE = Integer.valueOf( 2 );

        // 显示策略
        public static final Integer ADVERT_SHOW_RANDOM = Integer.valueOf( 1 );

        public static final Integer ADVERT_SHOW_WEIGHT = Integer.valueOf( 2 );

        public static final Integer ADVERT_SHOW_KEY = Integer.valueOf( 3 );

        // 打开方式
        public static final Integer TARGET_BLANK = Integer.valueOf( 1 );

        public static final Integer TARGET_SELF = Integer.valueOf( 2 );

        // public static final Integer ADVERT_CONFIG_POS_TYPE = Integer
        // .valueOf( 3 );
        // 自定义参数前缀
        public static final String POS_PARAM_PEFIX = "pos.";

        public static final String AD_PARAM_PEFIX = "ad.";

    }

    public interface SURVEY
    {
        public static final Integer RESTR_COOKIE_MODE = Integer.valueOf( 1 );

        public static final Integer RESTR_IP_MODE = Integer.valueOf( 2 );

        /**
         * 选项类型
         */

        public static final Integer SINGLE_SELECT_TEXT = Integer.valueOf( 1 );

        public static final Integer MUTI_SELECT_TEXT = Integer.valueOf( 2 );

        public static final Integer SINGLE_SELECT_IMAGE = Integer.valueOf( 3 );

        public static final Integer MUTI_SELECT_IMAGE = Integer.valueOf( 4 );

        public static final Integer INPUT_TEXT = Integer.valueOf( 5 );

        public static final String HTML_ID_PERFIX = "jtopcms-survey-";

        public static final String HTML_TEXT_ID_PERFIX = "jtopcms-text-survey-";

        public static final String HTML_GROUP_FLAG_PERFIX = "jtopcms-group-survey-";

    }

    public interface MEMBER
    {
        public static final String SALT = "jtop!Q(O*I&Ubestp1237$R%Tcms56324rojectis!Q@W#E$R%Tjtopcmsisbest";

        public static final String QQ_LOGIN = "qq";

        public static final String WEIBO_LOGIN = "weibo";

        public static final Integer ACC_CLASS_VIEW = Integer.valueOf( 1 );

        public static final Integer ACC_CLASS_SUBMIT = Integer.valueOf( 2 );

    }

    public interface SHOP
    {
        // public static final Integer ORDER_WAIT_PAY = Integer.valueOf( -3 );//
        // 用户未付款

        public static final Integer ORDER_CANCEL_SUCCESS = Integer.valueOf( -2 );// 取消定单成功

        public static final Integer ORDER_CANCEL_REQUEST = Integer.valueOf( -1 );// 请求取消定单

        public static final Integer ORDER_SUCCESS_NOT_PAY = Integer.valueOf( 0 );// 等待付款

        public static final Integer GOODS_WAIT_OUT = Integer.valueOf( 1 );// 正在出库

        public static final Integer GOODS_WAIT_PAY = Integer.valueOf( 2 );// 已经付款

        public static final Integer GOODS_WAIT_DE_PAY = Integer.valueOf( 5 );// 货到付款

        public static final Integer GOODS_IN_DE = Integer.valueOf( 3 );// 已经发货

        public static final Integer GOODS_TAKE_DE = Integer.valueOf( 4 );// 客户已收货
    }

    public interface WX
    {
        public static final Integer MENU_ORDER_ONE = Integer.valueOf( 10 );

        public static final Integer MENU_ORDER_TWO = Integer.valueOf( 20 );

        public static final Integer MENU_ORDER_THIRD = Integer.valueOf( 30 );

        // 请求消息类型：文本
        public static final String REQ_MESSAGE_TYPE_TEXT = "text";
        // 请求消息类型：图片
        public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
        // 请求消息类型：语音
        public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
        // 请求消息类型：视频
        public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
        // 请求消息类型：小视频
        public static final String REQ_MESSAGE_TYPE_SHORT_VIDEO = "shortvideo";
        // 请求消息类型：地理位置
        public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
        // 请求消息类型：链接
        public static final String REQ_MESSAGE_TYPE_LINK = "link";

        /**
         * event标志
         */

        // 消息类型：事件推送
        public static final String REQ_MESSAGE_TYPE_EVENT = "event";

        /**
         * 消息事件
         */

        // 事件类型：subscribe(订阅)
        public static final String EVENT_TYPE_MSG_SUBSCRIBE = "subscribe";
        // 事件类型：unsubscribe(取消订阅)
        public static final String EVENT_TYPE_MSG_UNSUBSCRIBE = "unsubscribe";
        // 事件类型：scan(用户已关注时的扫描带参数二维码)
        public static final String EVENT_TYPE_MSG_SCAN = "SCAN";
        // 事件类型：LOCATION(上报地理位置)
        public static final String EVENT_TYPE_MSG_LOCATION = "LOCATION";

        /**
         * 菜单事件
         */

        // 事件类型：CLICK(自定义菜单)
        public static final String EVENT_TYPE_CLICK = "CLICK";

        // 事件类型：VIEW(自定义菜单)
        public static final String EVENT_TYPE_VIEW = "VIEW";

        // 事件类型：扫码推(自定义菜单)
        public static final String EVENT_TYPE_SP = "scancode_push";

        // 事件类型：扫码(自定义菜单)
        public static final String EVENT_TYPE_SW = "scancode_waitmsg";

        // 事件类型：弹出系统拍照发图的事件推送(自定义菜单)
        public static final String EVENT_TYPE_PS = "pic_sysphoto";

        // 事件类型：弹出拍照或者相册发图的事件推送(自定义菜单)
        public static final String EVENT_TYPE_PA = "pic_photo_or_album";

        // 事件类型：弹出微信相册发图器(自定义菜单)
        public static final String EVENT_TYPE_PIC = "pic_weixin";

        // 事件类型：地图位置(自定义菜单)
        public static final String EVENT_TYPE_LOCS = "location_select";

        /**
         * 响应类型
         */

        // 响应消息类型：文本
        public static final String RESP_MESSAGE_TYPE_TEXT = "text";
        // 响应消息类型：图片
        public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
        // 响应消息类型：语音
        public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
        // 响应消息类型：视频
        public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
        // 响应消息类型：音乐
        public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
        // 响应消息类型：图文
        public static final String RESP_MESSAGE_TYPE_NEWS = "news";

        /**
         * 特殊类型
         */
        public static final String SPEC_RES_THUMB = "thumb";
    }

    public static abstract interface APP
    {
        public static final String APP_SEC_TOKEN = "sys_sign";

        public static final String APP_SEC_UID = "sys_uid";

        public static final String APP_FLOW_PARAM_TOKEN_MODE = "token";

        public static final String APP_FLOW_PARAM_ENCRYPT_MODE = "encrypt";
    }
}
