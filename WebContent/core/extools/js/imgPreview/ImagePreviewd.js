
var strFullPath=window.document.location.href;
var strPath=window.document.location.pathname;
var pos=strFullPath.indexOf(strPath);
var prePath=strFullPath.substring(0,pos);
var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);

var base = prePath+postPath;

var blankData="/core/resources/js/imgPreview/blank.js";

var blankImgSrc = "mhtml:" + base+blankData + "!blankImage";

//每个对象包含一个文件索引，一个外部图片索引，作为缓存的preload模型，
var ImagePreview = function(file, img, options) {
	
	this.file = $$(file);//文件对象 ,关联外部的文件，此文件可为实时创造的
	this.img = $$(img);//预览图片对象，目标图片对象，经过处理后的预览图片将由此img负责显示
	
	this._preload = null;//预载图片对象，原始图像缓存，对此图片进行处理
	this._data = "";//图像数据 ，文件地址或者数据
	this._upload = null;//remote模式使用的上传文件对象
	
	var opt = this._setOptions(options);
	
	this.action = opt.action;//后台模式，但此方法依赖传输速度
	this.timeout = opt.timeout;//超时
	this.ratio = opt.ratio;//缩放比例，此比例由，设定的宽和高

	this.maxWidth = opt.maxWidth;
	this.maxHeight = opt.maxHeight;
	
	this.onCheck = opt.onCheck;
	this.onShow = opt.onShow;
	this.onErr = opt.onErr;
	
	//设置数据获取程序
	this._getData = this._getDataFun(opt.mode);
	
	//设置预览显示程序
	this._show = opt.mode !== "filter" ? this._simpleShow : this._filterShow;
};
//根据浏览器获取模式

ImagePreview.MODE = $$B.ie7 || $$B.ie8 ? "filter" :
	$$B.firefox ? "domfile" :
	$$B.opera || $$B.chrome || $$B.safari ? "remote" : "simple";
	
	//alert(ImagePreview.MODE);
//透明图片
ImagePreview.TRANSPARENT = $$B.ie7 || $$B.ie6 ?
	blankImgSrc : "data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
	//alert(document.scripts[document.scripts.length - 1].getAttribute("src", 4) + "!blankImage");

ImagePreview.prototype = {
  //设置默认属性,将调用时给予的参数和默认值合并
  _setOptions: function(options) {
  	
    this.options = {//默认值
    	
    	mode:		ImagePreview.MODE,//预览模式，此模式在进入页面的时候就已经判断好
		ratio:		0,//自定义比例0
		maxWidth:	0,//缩略图宽度，最大宽，按照ratio
		maxHeight:	0,//缩略图高度，最大高，按照ratio
		onCheck:	function(){},//预览检测时执行
		onShow:		function(){},//预览图片时执行
		onErr:		function(){},//预览错误时执行
		//以下在remote模式时有效
		action:		undefined,//设置action
		timeout:	0//设置超时(0为不设置)
    };
    
    //在这里合并所有参数，若给予了参数，就替换默认值
    return $$.extend(this.options, options || {});
  },
  
  
  
  
  //开始预览,这是一个入口方法
  preview: function() {
	if ( this.file && false !== this.onCheck() ) {
		//此时进入方法，开始预览
		this._preview( this._getData() );
	}
  },
  
  //根据mode返回数据获取程序
  _getDataFun: function(mode) {
	switch (mode) {
		case "filter" :
			return this._filterData;
		case "domfile" :
			return this._domfileData;
		case "remote" :
			return this._remoteData;
		case "simple" :
		    //alert(this._simpleData);
		    return this._simpleData;
		default :
			return this._simpleData;
	}
  },
  //滤镜数据获取程序
  _filterData: function() {
	this.file.select();
	try{
		return document.selection.createRange().text;
	} finally { document.selection.empty(); }
  },
  //domfile数据获取程序
  _domfileData: function() {
  	var img = this.img;
  alert(this.width);
	var ratio = Math.max( 0, this.ratio ) || Math.min( 1,
				Math.max( 0, this.maxWidth ) / this._preload.offsetWidth  || 1,
				Math.max( 0, this.maxHeight ) / this._preload.offsetHeight || 1
			);
	//设置预览尺寸
	//alert(ratio);
	//alert(img.width);
	img.width = Math.round( width * ratio ) + "px";
	img.height = Math.round( height * ratio ) + "px";
	
	return this.file.files[0].getAsDataURL();
  },
  //远程数据获取程序
  _remoteData: function() {
	this._setUpload();
	this._upload && this._upload.upload();
  },
  //一般数据获取程序
  _simpleData: function() {
	return this.file.value;
  },
  
  //设置remote模式的上传文件对象
  _setUpload: function() {
	if ( !this._upload && this.action !== undefined && typeof QuickUpload === "function" ) {
		var oThis = this;
		this._upload = new QuickUpload(this.file, {
			onReady: function(){
				this.action = oThis.action; this.timeout = oThis.timeout;
				var parameter = this.parameter;
				parameter.ratio = oThis.ratio;
				parameter.width = oThis.maxWidth;
				parameter.height = oThis.maxHeight;
			},
			onFinish: function(iframe){
				try{
					oThis._preview( iframe.contentWindow.document.body.innerHTML );
				}catch(e){ oThis._error("remote error"); }
			},
			onTimeout: function(){ oThis._error("timeout error"); }
		});
	}
  },
  
  //预览程序
  _preview: function(data) {
	//空值或相同的值不执行显示
	if ( !!data && data !== this._data ) {
		this._data = data; 
		//根据浏览器的不同，选择图片处理方式
		this._show();
	}
  },
  
  //设置一般预载图片对象
  _simplePreload: function() {
  	//alert('--');
	if ( !this._preload ) {
		var preload = this._preload = new Image(), oThis = this,
			onload = function(){ oThis._imgShow( oThis._data, this.width, this.height ); };
		this._onload = function(){ this.onload = null; onload.call(this); }
		preload.onload = $$B.ie ? this._onload : onload;
		preload.onerror = function(){ oThis._error(); };
	} else if ( $$B.ie ) {
		this._preload.onload = this._onload;
	}
  },
  //一般显示
  _simpleShow: function() {
	//this._simplePreload();
	//alert(_preload.src);
	//this._preload.src = this._data;
	document.getElementById("idImg").src=this._data;
  },
  
  //设置滤镜预载图片对象,这里new了一个隐藏的对象
  _filterPreload: function() {
	if ( !this._preload ) {
		//此img为原始图片
		var preload = this._preload = document.createElement("img");
		//隐藏并设置滤镜
		$$D.setStyle( preload, {
			width: "1px", height: "1px",
			visibility: "hidden", position: "absolute", left: "-9999px", top: "-9999px",
			filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image')"
		});
		//插入body
		var body = document.body; 
		body.insertBefore( preload, body.childNodes[0] );
	}
  },
  //滤镜显示
  _filterShow: function() {
	this._filterPreload();
	var preload = this._preload;
	
	//这个data为本地的地址，经过escape
	var data = this._data.replace(/[)'"%]/g, function(s){ return escape(escape(s)); });
	
	//alert(data);
	
	try
	{	
		//将地址给予缓存图片的滤镜
		preload.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = data;
	}
	catch(e)
	{ 
		this._error("filter error"); 
		return; 
	}
	//设置滤镜并显示，关键的一步,真正的缩小的目标img被给予缓存图片的滤镜
	this.img.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + data + "\")";
	//此时目标img开始显示
	//alert(preload.offsetWidth+" : "+preload.offsetHeight);
	
		//var mhtmlSrc = "mhtml:" + data + "!blankImage";
	//alert("sdsd  : "+ImagePreview.TRANSPARENT);
	this._imgShow( ImagePreview.TRANSPARENT, preload.offsetWidth, preload.offsetHeight );
  },
  
  //显示预览
  _imgShow: function(src, width, height) {
  	//alert(style.width);
	var img = this.img, style = img.style,
		ratio = Math.max( 0, this.ratio ) || Math.min( 1,
				Math.max( 0, this.maxWidth ) / width  || 1,
				Math.max( 0, this.maxHeight ) / height || 1
			);
	//设置预览尺寸
	style.width = Math.round( width * ratio ) + "px";
	style.height = Math.round( height * ratio ) + "px";
	//设置src,IE完全不需要设置，作者也许想留点代码后路在里面,现在做修改
	
	alert(style.width+" : "+style.width);
	//alert(style.width);
	
	
	if($$B.firefox)
	{
		//img.width=Math.round( width * ratio ) + "px";
		//img.height = Math.round( height * ratio ) + "px";
		img.src = src;
	}
	else if($$B.ie)
	{
		//以下是为了防止出现下img在src为空情况下，出现提示红叉图标，需要给一个空白资源填充
		
		//alert("t:"+blankImgSrc );
		//alert("ImagePreview.TRANSPAREN:"+ImagePreview.TRANSPAREN);
		img.src = blankImgSrc  ;
		
		


	}
	//alert('end :'+ img.src);
	
	//调用外部显示代码
	this.onShow();
  },
  
  //销毁程序
  dispose: function() {
	//销毁上传文件对象
	if ( this._upload ) {
		this._upload.dispose(); this._upload = null;
	}
	//销毁预载图片对象
	if ( this._preload ) {
		var preload = this._preload, parent = preload.parentNode;
		this._preload = preload.onload = preload.onerror = null;
		parent && parent.removeChild(preload);
	}
	//销毁相关对象
	this.file = this.img = null;
  },
  //出错
  _error: function(err) {
	this.onErr(err);
  }
}