/*
	GoogleMaps Plugin for FCKeditor
	Dialog plugin to handle insertion and modification of Google Maps in FCKeditor

	File Author:
		Alfonso Martinez de Lizarrondo amla70 at gmail dot com

	version 1.98

	See readme.html
*/


// If it's empty automatically remove the button from any toolbar.
//if ( !FCKConfig.GoogleMaps_Key || FCKConfig.GoogleMaps_Key.length === 0)
//{
//	for( var name in FCKConfig.ToolbarSets )
//		RemoveButtonFromToolbarSet( FCKConfig.ToolbarSets[name], 'googlemaps' ) ;
//}




/**
	Helper function
	It does remove a button from an toolbarset. 
	It's better than leaving it disabled as it will avoid questions about why some button is always disabled.
*/
function RemoveButtonFromToolbarSet(ToolbarSet, CommandName)
{
	if (!ToolbarSet)
		return;

	for ( var x = 0 ; x < ToolbarSet.length ; x++ )
	{
		var oToolbarItems = ToolbarSet[x] ;

		// If the configuration for the toolbar is missing some element or has any extra comma
		// this item won't be valid, so skip it and keep on processing.
		if ( !oToolbarItems ) 
			continue ;

		if ( typeof( oToolbarItems ) == 'object' )
		{
			for ( var j = 0 ; j < oToolbarItems.length ; j++ )
			{
				if ( oToolbarItems[j] == CommandName)
				{
						oToolbarItems.splice(j, 1);
						ToolbarSet[x] = oToolbarItems ;
						return;
				}
			}
		}
	}
}

// Toolbar button

// Register the related command.
FCKCommands.RegisterCommand( 'googlemapsV3', new FCKDialogCommand( 'googlemapsV3', '插入地图', FCKPlugins.Items['googlemapsV3'].Path + 'dialog/googleMaps.html', 650, 550 ) ) ;

// Create the "googlemaps" toolbar button.
var oGoogleMapsButton = new FCKToolbarButton('googlemapsV3', "Google地图", "新增/编辑地图");
oGoogleMapsButton.IconPath = FCKPlugins.Items['googlemapsV3'].Path + 'images/mapIcon.gif';

FCKToolbarItems.RegisterItem('googlemapsV3', oGoogleMapsButton);


// Detection of existing maps
/**
	FCKCommentsProcessor
	---------------------------
	It's run after a document has been loaded, it detects all the protected source elements

	In order to use it, you add your comment parser with 
	FCKCommentsProcessor.AddParser( function )
*/
if (typeof FCKCommentsProcessor === 'undefined')
{
	var FCKCommentsProcessor = FCKDocumentProcessor.AppendNew() ;
	FCKCommentsProcessor.ProcessDocument = function( oDoc )
	{
		if ( FCK.EditMode != FCK_EDITMODE_WYSIWYG )
			return ;

		if ( !oDoc )
			return ;

	//Find all the comments: <!--{PS..0}-->
	//try to choose the best approach according to the browser:
		if ( oDoc.evaluate )
			this.findCommentsXPath( oDoc );
		else
		{
			if (oDoc.all)
				this.findCommentsIE( oDoc.body ) ;
			else
				this.findComments( oDoc.body ) ;
		}

	}

	FCKCommentsProcessor.findCommentsXPath = function(oDoc) {
		var nodesSnapshot = oDoc.evaluate('//body//comment()', oDoc.body, null, XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null );

		for ( var i=0 ; i < nodesSnapshot.snapshotLength; i++ )
		{
			this.parseComment( nodesSnapshot.snapshotItem(i) ) ;
		}
	}

	FCKCommentsProcessor.findCommentsIE = function(oNode) {
		var aComments = oNode.getElementsByTagName( '!' );
		for(var i=aComments.length-1; i >=0 ; i--)
		{
			var comment = aComments[i] ;
			if (comment.nodeType == 8 ) // oNode.COMMENT_NODE) 
				this.parseComment( comment ) ;
		}
	}

	// Fallback function, iterate all the nodes and its children searching for comments.
	FCKCommentsProcessor.findComments = function( oNode ) 
	{
		if (oNode.nodeType == 8 ) // oNode.COMMENT_NODE) 
		{
			this.parseComment( oNode ) ;
		}
		else 
		{
			if (oNode.hasChildNodes()) 
			{
				var children = oNode.childNodes ;
				for (var i = children.length-1; i >=0 ; i--) 
					this.findComments( children[ i ] );
			}
		}
	}

	// We get a comment node
	// Check that it's one that we are interested on:
	FCKCommentsProcessor.parseComment = function( oNode )
	{
		var value = oNode.nodeValue ;

		// Difference between 2.4.3 and 2.5
		var prefix = ( FCKConfig.ProtectedSource._CodeTag || 'PS\\.\\.' ) ;

		var regex = new RegExp( "\\{" + prefix + "(\\d+)\\}", "g" ) ;

		if ( regex.test( value ) ) 
		{
			var index = RegExp.$1 ;
			var content = FCKTempBin.Elements[ index ] ;

			// Now call the registered parser handlers.
			var oCalls = this.ParserHandlers ;
			if ( oCalls )
			{
				for ( var i = 0 ; i < oCalls.length ; i++ )
					oCalls[ i ]( oNode, content, index ) ;

			}

		}
	}

	/**
		The users of the object will add a parser here, the callback function gets two parameters:
			oNode: it's the node in the editorDocument that holds the position of our content
			oContent: it's the node (removed from the document) that holds the original contents
			index: the reference in the FCKTempBin of our content
	*/
	FCKCommentsProcessor.AddParser = function( handlerFunction )
	{
		if ( !this.ParserHandlers )
			this.ParserHandlers = [ handlerFunction ] ;
		else
		{
			// Check that the event handler isn't already registered with the same listener
			// It doesn't detect function pointers belonging to an object (at least in Gecko)
			if ( this.ParserHandlers.IndexOf( handlerFunction ) == -1 )
				this.ParserHandlers.push( handlerFunction ) ;
		}
	}
}
/**
	END of FCKCommentsProcessor
	---------------------------
*/




// Check if the comment it's one of our scripts:
var GoogleMaps_CommentsProcessorParser = function( oNode, oContent, index)
{
		if ( FCK.GoogleMapsHandler.detectMapScript( oContent ) )
		{
			var oMap = FCK.GoogleMapsHandler.createNew() ;
			oMap.parse( oContent ) ;
			oMap.createHtmlElement( oNode, index ) ;
		}
		else
		{
			if ( FCK.GoogleMapsHandler.detectGoogleScript( oContent ) )
				oNode.parentNode.removeChild( oNode );
		}
}

FCKCommentsProcessor.AddParser( GoogleMaps_CommentsProcessorParser );



// Context menu
FCK.ContextMenu.RegisterListener( {
	AddItems : function( menu, tag, tagName )
	{
		// under what circumstances do we display this option
		if ( tagName == 'IMG' && tag.getAttribute( 'MapNumberV3' ) )
		{
			// No other options:
			menu.RemoveAllItems() ;
			// the command needs the registered command name, the title for the context menu, and the icon path
			menu.AddItem('googlemapsV3', "地图內容", oGoogleMapsButton.IconPath);
		}
	}}
);

// Double click
FCK.RegisterDoubleClickHandler( editMap, 'IMG' ) ;

function editMap( oNode )
{
	if ( !oNode.getAttribute( 'MapNumberV3' ))
		return ;

	FCK.Commands.GetCommand('googlemapsV3').Execute();
}


// Object that handles the common functions about all the maps
FCK.GoogleMapsHandler = {
	// Object to store a reference to each map
	maps: {},

	getMap: function(id){
		return this.maps[id];
	},

	// Verify that the node is a script generated by this plugin.
	detectMapScript: function( script )
	{
		// We only know about version 1:
		if ( !(/FCK googlemaps v1\.(\d+)/.test(script)) )
			return false;

		return true
	},

	// Self-executed function, we want to run it once at initialization only.
	// Public key that will be used for the generated maps,
	// while we are editing we will use only FCKConfig.GoogleMaps_Key
	publicKey : function() {
		// if FCKConfig.GoogleMaps_PublicKey is set to something, then use it 
		if ( FCKConfig.GoogleMaps_PublicKey )
			return FCKConfig.GoogleMaps_PublicKey ;

		// else we will use GoogleMaps_Key for both the public and private side.
		return FCKConfig.GoogleMaps_Key ;
	}(),

	// Detects both the google script as well as our ending block
	// both must be removed and then added later only if neccesary
	detectGoogleScript: function( script )
	{
		// Our final script
		if (/FCK googlemapsEnd v1\./.test(script) )
			return true ;

		// If it is the Google Maps script, get the public key from here:
		if ( !/^<script src="http:\/\/maps\.google\.com\/.*key=(.*?)("|&)/.test(script) )
		    return true;

		this.publicKey = RegExp.$1 ;
		return ( true ) ;
	},

	GenerateGoogleScript : function()
	{
	    return '\r\n<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript" charset="utf-8"></script>';
	},

	// This can be called from the dialog
	createNew: function()
	{
		var map = new FCKGoogleMap() ;
		this.maps[ map.number ] = map;
		return map;
	},

	BuildEndingScript: function()
	{
		var versionMarker = '// FCK googlemapsEnd v1.97';

		var aScript = [] ;
		aScript.push('\r\n<script type="text/javascript">') ;
		aScript.push( versionMarker ) ;

		aScript.push('  var infowindow = new google.maps.InfoWindow();');
		aScript.push('function openinfo( map, marker)');
		aScript.push('{');
		aScript.push('   infowindow.setContent(marker.title)');
		aScript.push('  infowindow.open(map, marker);');
		aScript.push('}');

		aScript.push('function AddMarkers( map, aPoints)');
		aScript.push('{');
		aScript.push('	for (var i=0; i<aPoints.length ; i++)');
		aScript.push('	{');
		aScript.push('		var point = new google.maps.LatLng(parseFloat(aPoints[i].lat), parseFloat(aPoints[i].lon));');
		aScript.push('	var marker = new google.maps.Marker({position: point,map:map,title: aPoints[i].title});');

		aScript.push('  google.maps.event.addListener(marker, \'click\', function () { openinfo( map, marker); });');

		aScript.push('	}');
		aScript.push('}');

		aScript.push(' function Addlines(aPoints,amap,aflightPath){');
		aScript.push('	for (var i=0; i<aPoints.length ; i++)');
		aScript.push('	{');
		aScript.push('		var point = new google.maps.LatLng(parseFloat(aPoints[i].lat), parseFloat(aPoints[i].lon));');
		aScript.push('      var path = aflightPath.getPath();');
		aScript.push('      path.push(point);');
//		aScript.push('      var marker = new google.maps.Marker({');
//		aScript.push('          position: point,');
//		aScript.push('          title: "#" + path.getLength(),');
//		aScript.push('          map: amap');
//		aScript.push('      });');
		aScript.push('	}');
		aScript.push('}');

		var maps = this.CreatedMapsNames ;
		for (var i = 0; i < maps.length; i++)
		{
			// Append event listeners instead of replacing previous ones
			aScript.push('if (window.addEventListener) {');
			aScript.push('    window.addEventListener("load", CreateGMap' + maps[i]  + ', false);');
			aScript.push('} else {');
			aScript.push('    window.attachEvent("onload", CreateGMap' + maps[i]  + ');');
			aScript.push('}');
		}

		aScript.push('</script>');

		return aScript.join('\r\n');
	},

	// We will use this to track the number of maps that are generated
	// This way we know if we must add the Google Script or not.
	// We store their names so they are called properly from BuildEndingScript
	CreatedMapsNames : [],

	// Function that will be injected into the normal core
	GetXHTMLAfter: function( node, includeNode, format, Result )
	{
		if (FCK.GoogleMapsHandler.CreatedMapsNames.length > 0)
		{
			Result += FCK.GoogleMapsHandler.BuildEndingScript() ;
		}
		// Reset the counter each time the GetXHTML function is called
		FCK.GoogleMapsHandler.CreatedMapsNames = [];

		return Result ;
	},

	// Store any previous processor so nothing breaks
	previousProcessor: FCKXHtml.TagProcessors[ 'img' ] 
}


// Our object that will handle parsing of the script and creating the new one.
var FCKGoogleMap = function () {
    var now = new Date();
    this.number = '' + now.getFullYear() + now.getMonth() + now.getDate() + now.getHours() + now.getMinutes() + now.getSeconds();

    this.width = FCKConfig.GoogleMaps_Width || 600;
    this.height = FCKConfig.GoogleMaps_Height || 380;

    this.centerLat = FCKConfig.GoogleMaps_CenterLat || 37.4419;
    this.centerLon = FCKConfig.GoogleMaps_CenterLon || -122.1419;
    this.zoom = FCKConfig.GoogleMaps_Zoom || 11;

    //this.markerPoints = [] ;
    this.markerPoints = FCKConfig.GoogleMaps_MarkerPoints || [];
    this.PolyPoints = [];
    this.LinePoints = [];
    // this.LineLevels = '';

    this.mapType = 0;

    this.WrapperClass = FCKConfig.GoogleMaps_WrapperClass || '';
}


FCKGoogleMap.prototype.createHtmlElement = function( oReplacedNode, index)
{
	var oFakeNode = FCK.EditorDocument.createElement( 'IMG' ) ;

	// Are we creating a new map?
	if ( !oReplacedNode )
	{
    index = FCKTempBin.AddElement( this.BuildScript() ) ;
		var prefix = ( FCKConfig.ProtectedSource._CodeTag || 'PS..' ) ;
		oReplacedNode = FCK.EditorDocument.createComment( '{' + prefix + index + '}' ) ;
		FCK.InsertElement(oReplacedNode);
	}
	oFakeNode.contentEditable = false ;
//	oFakeNode.setAttribute( '_fckfakelement', 'true', 0 ) ;

	oFakeNode.setAttribute( '_fckrealelement', FCKTempBin.AddElement( oReplacedNode ), 0 ) ;
	oFakeNode.setAttribute( '_fckBinNode', index, 0 ) ;

	oFakeNode.src = FCKConfig.FullBasePath + 'images/spacer.gif' ;
	oFakeNode.style.display = 'block' ;
	oFakeNode.style.border = '1px solid black' ;
	oFakeNode.style.background = 'white center center url("' + FCKPlugins.Items['googlemapsV3'].Path + 'images/maps_res_logo.png' + '") no-repeat';

	oFakeNode.setAttribute("MapNumberV3", this.number, 0) ;

	oReplacedNode.parentNode.insertBefore( oFakeNode, oReplacedNode ) ;
	oReplacedNode.parentNode.removeChild( oReplacedNode ) ;

	// dimensions
	this.updateHTMLElement( oFakeNode );

	return oFakeNode ;
}

FCKGoogleMap.prototype.updateScript = function( oFakeNode )
{
	this.updateDimensions( oFakeNode ) ;

	var index = oFakeNode.getAttribute( '_fckBinNode' );
	FCKTempBin.Elements[ index ] =  this.BuildScript() ;
}

FCKGoogleMap.prototype.updateHTMLElement = function (oFakeNode) {
    oFakeNode.width = this.width;
    oFakeNode.height = this.height;

    // Static maps preview :-)
    oFakeNode.src = this.generateStaticMap();
    oFakeNode.style.border = 0;

    // The wrapper class is applied to the IMG not to a wrapping DIV !!!
    if (this.WrapperClass !== '')
        oFakeNode.className = this.WrapperClass;
}

FCKGoogleMap.prototype.generateStaticMap = function () {
    var w = Math.min(this.width, 640);
    var h = Math.min(this.height, 640);
    var staticMapTypes = ['roadmap', 'satellite', 'hybrid', 'terrain'];
    return 'http://maps.google.com/maps/api/staticmap?center=' + this.centerLat + ',' + this.centerLon
		+ '&zoom=' + this.zoom + '&size=' + w + 'x' + h
		+ '&maptype=' + staticMapTypes[this.mapType]
		+ this.generateStaticMarkers()
        + this.generateStaticPPaths()
        + this.generateStaticPaths()
        + '&sensor=false'
}

FCKGoogleMap.prototype.generateStaticMarkers = function () {
    if (this.markerPoints.length == 0)
        return '';

    var aPoints = [];
    for (var i = 0; i < this.markerPoints.length; i++) {
        var point = this.markerPoints[i];
        //aPoints.push(point.lat + ',' + point.lon + ',' + point.title);	
        aPoints.push(point.lat + ',' + point.lon);
    }
    return ('&markers=' + aPoints.join('|'));
}

//path=color:0x0000ff|weight:5|40.737102,-73.990318|40.749825,-73.987963|40.752946,-73.987384|40.755823,-73.986397
FCKGoogleMap.prototype.generateStaticPaths = function () {
    if (this.LinePoints.length == 0)
        return '';

    var aPoints = [];
    for (var i = 0; i < this.LinePoints.length; i++) {
        var point = this.LinePoints[i];
        //aPoints.push(point.lat + ',' + point.lon + ',' + point.title);	
        aPoints.push(point.lat + ',' + point.lon);
    }
    return ('&path=color:0x7F3333cc|weight:3|' + aPoints.join('|'));
}
//path=color:0x00000000|weight:5|fillcolor:0xFFFF0033|8th+Avenue+%26+34th+St,New+York,NY|\ 8th+Avenue+%26+42nd+St,New+York,NY|Park+Ave+%26+42nd+St,New+York,NY,NY|\ Park+Ave+%26+34th+St,New+York,NY,NY
FCKGoogleMap.prototype.generateStaticPPaths = function () {
    if (this.PolyPoints.length == 0)
        return '';

    var aPoints = [];
    for (var i = 0; i < this.PolyPoints.length; i++) {
        var point = this.PolyPoints[i];
        aPoints.push(point.lat + ',' + point.lon);
    }
    return ('&path=fillcolor:0xAA000033|color:0x7F3333cc|weight:0|' + aPoints.join('|'));
}
// Read the dimensions back from the fake node (the user might have manually resized it)
FCKGoogleMap.prototype.updateDimensions = function( oFakeNode )
{
	var iWidth, iHeight ;
	var regexSize = /^\s*(\d+)px\s*$/i ;

	if ( oFakeNode.style.width )
	{
		var aMatchW  = oFakeNode.style.width.match( regexSize ) ;
		if ( aMatchW )
		{
			iWidth = aMatchW[1] ;
			oFakeNode.style.width = '' ;
			oFakeNode.width = iWidth ;
		}
	}

	if ( oFakeNode.style.height )
	{
		var aMatchH  = oFakeNode.style.height.match( regexSize ) ;
		if ( aMatchH )
		{
			iHeight = aMatchH[1] ;
			oFakeNode.style.height = '' ;
			oFakeNode.height = iHeight ;	
		}
	}

	this.width	= iWidth ? iWidth : oFakeNode.width ;
	this.height	= iHeight ? iHeight : oFakeNode.height ;
}

FCKGoogleMap.prototype.decodeText = function(string)
{
	return string.replace(/<\\\//g, "</").replace(/\\n/g, "\n").replace(/\\'/g, "'").replace(/\\\\/g, "\\");
}
FCKGoogleMap.prototype.encodeText = function (string) {
    
    return string.replace(/\\/g, "\\\\").replace(/'/g, "\\'").replace(/\n/g, "\\n").replace(/<\//g, "<\\/");
}

FCKGoogleMap.prototype.parse = function (script) {
    // We only know about version 1:
    if (!(/FCK googlemaps v1\.(\d+)/.test(script)))
        return false;

    var version = parseInt(RegExp.$1, 10);

    // dimensions:
    //	document.writeln('<div id="gmap1" style="width: 544px; height: 350px;">.</div>');
    var regexpDimensions = /<div id="gmap(\d+)" style="width\:\s*(\d+)px; height\:\s*(\d+)px;">/;
    if (regexpDimensions.test(script)) {
        delete FCK.GoogleMapsHandler.maps[this.number];
        this.number = RegExp.$1;
        FCK.GoogleMapsHandler.maps[this.number] = this;

        this.width = RegExp.$2;
        this.height = RegExp.$3;
    }

    //	map.setCenter(new GLatLng(42.4298,-8.07756), 8);
    //var regexpPosition = /map\.setCenter\(new GLatLng\((-?\d{1,3}\.\d{1,6}),(-?\d{1,3}\.\d{1,6})\), (\d{1,2})\);/;
    //myOptions = { zoom: 13, center: new google.maps.LatLng(31.22473, 121.52956), navigationControl: true, scaleControl: true, mapTypeId: google.maps.MapTypeId.ROADMAP };
    var regexpPosition = /zoom\:\s*(\d{1,2})/;
    if (regexpPosition.test(script)) {
        this.zoom = RegExp.$1;
    }

    //myLatlng = new google.maps.LatLng(31.22473, 121.52956); 
    regexpPosition = /center\:\s*new google\.maps\.LatLng\((-?\d{1,3}\.\d{1,6}),\s*(-?\d{1,3}\.\d{1,6})/;

    var regexpMarkers = /\{lat\:(-?\d{1,3}\.\d{1,6}),\s*lon\:(-?\d{1,3}\.\d{1,6}),\s*title\:("|')(.*)\3}(?:,|])/;
    var point;
    var sampleText = script;
    var startIndex = 0;
    var totalLength = sampleText.length;
    var result, pos;
    if (regexpPosition.test(script)) {
        this.centerLat = RegExp.$1;
        this.centerLon = RegExp.$2;
    }
    this.markerPoints.length = 0;
    this.LinePoints.length = 0;
    this.PolyPoints.length = 0;
    // v > 1.5. multiple points.
    // AddMarkers( [{lat:37.45088, lon:-122.21123, text:'Write your text'}] ) ;
    while (startIndex != totalLength) {
        result = regexpMarkers.exec(sampleText);
        if (result && result.length > 0) {
            pos = sampleText.indexOf(result[0]);
            startIndex += pos;

            this.markerPoints.push({ lat: result[1], lon: result[2], title: this.decodeText(result[4]) });
            sampleText = sampleText.substr(pos + result[0].length);
            startIndex += result[0].length;
        } else {
            break;
        }
    }
    startIndex = 0;
    sampleText = script;
    // var polylines=[{21.454545,31.546465},{34.234323,43.45454}]
    var regexpLinePoints = /\{lat\:(-?\d{1,3}\.\d{1,6}),\s*lon\:(-?\d{1,3}\.\d{1,6})\}/;
    while (startIndex != totalLength) {
        result = regexpLinePoints.exec(sampleText);
        if (result && result.length > 0) {
            pos = sampleText.indexOf(result[0]);
            startIndex += pos;

            this.LinePoints.push({ lat: result[1], lon: result[2] });

            sampleText = sampleText.substr(pos + result[0].length);
            startIndex += result[0].length;
        } else {
            break;
        }
    }
    regexpLinePoints = /\{lat\:(-?\d{1,3}\.\d{1,6}),\s*lon\:(-?\d{1,3}\.\d{1,6}),\s*pol\:(\d{1,3})\}/;
    startIndex = 0;
    sampleText = script;
    while (startIndex != totalLength) {
        result = regexpLinePoints.exec(sampleText);
        if (result && result.length > 0) {
            pos = sampleText.indexOf(result[0]);
            startIndex += pos;
            this.PolyPoints.push({ lat: result[1], lon: result[2] });
            sampleText = sampleText.substr(pos + result[0].length);
            startIndex += result[0].length;
        } else {
            break;
        }
    }

    // 1.8 mapType
    //	map.setMapType( allMapTypes[ 1 ] );
    var regexpMapType = /setMapType\([^\[]*\[\s*(\d+)\s*\]\s*\)/;
    if (regexpMapType.test(script)) {
        this.mapType = RegExp.$1;
    }

    // 1.9 wrapper div with custom class
    if (version >= 9) {
        var regexpWrapper = /<div class=("|')(.*)\1.*\/\/wrapper/;
        if (regexpWrapper.test(script))
            this.WrapperClass = RegExp.$2;
        else
            this.WrapperClass = '';
    }

    return true;
}

FCKGoogleMap.prototype.BuildScript = function () {
    var versionMarker = '// FCK googlemaps v1.97';

    var aScript = [];
    aScript.push('\r\n<script type="text/javascript">');
    aScript.push(versionMarker);

    if (this.WrapperClass !== '')
        aScript.push('document.write(\'<div class="' + this.WrapperClass + '">\'); //wrapper');

    aScript.push('document.write(\'<div id="gmap' + this.number + '" style="width:' + this.width + 'px; height:' + this.height + 'px;">.<\\\/div>\');');

    if (this.WrapperClass !== '')
        aScript.push('document.write(\'<\\\/div>\'); ');

    aScript.push('function CreateGMap' + this.number + '() {');

    aScript.push('	var allMapTypes = [google.maps.MapTypeId.ROADMAP, google.maps.MapTypeId.SATELLITE, google.maps.MapTypeId.HYBRID, google.maps.MapTypeId.TERRAIN];');
    //aScript.push('	var myOptions = { center: new google.maps.LatLng(-33, 151),	    navigationControl: false,	    scaleControl: true, mapTypeId: google.maps.MapTypeId.ROADMAP       } ;');
    aScript.push('	var myOptions = {');
    aScript.push('  zoom: ' + this.zoom + ',');
    aScript.push('  center: new google.maps.LatLng(' + this.centerLat + ',' + this.centerLon + '),');
    aScript.push('  navigationControl: true,');
    aScript.push('  scaleControl: true,');
    aScript.push('  mapTypeId: google.maps.MapTypeId.ROADMAP};');
    aScript.push('	var map = new google.maps.Map(document.getElementById("gmap' + this.number + '"), myOptions);');
    //aScript.push('	map.setCenter(new google.maps.LatLng(' + this.centerLat + ',' + this.centerLon + '), ' + this.zoom + ');');
    aScript.push('	map.setMapTypeId( allMapTypes[ ' + this.mapType + ' ] );');
    var aPoints = [];

    for (var i = 0; i < this.markerPoints.length; i++) {
        var point = this.markerPoints[i];
        aPoints.push('{lat:' + point.lat + ', lon:' + point.lon + ', title:\'' + this.encodeText(point.title) + '\'}');
    }
    aScript.push('	AddMarkers( map, [' + aPoints.join(',\r\n') + '] ) ;');

    //ÕÛÏßÊý×é
    if (this.LinePoints.length > 0) {
        var lPoints = [];
        for (var i = 0; i < this.LinePoints.length; i++) {
            var point = this.LinePoints[i];
            lPoints.push('{lat:' + point.lat + ',lon:' + point.lon + '}');
        }
        aScript.push('  var polylines=[' + lPoints.join(',\r\n') + '];');
        aScript.push(' var flightPath = new google.maps.Polyline({ ');
        //aScript.push(' path: polylines, ');
        aScript.push(' strokeColor: "#3333cc", ');
        aScript.push(' strokeOpacity: 1.0,');
        aScript.push('  strokeWeight: 2 ');
        aScript.push(' });');
        aScript.push('  flightPath.setMap(map);');
        aScript.push(' Addlines(polylines,map,flightPath);');
    }
    //¶à±ßÐÎÊý×é
    if (this.PolyPoints.length > 0) {
        var pPoints = [];
        for (var i = 0; i < this.PolyPoints.length; i++) {
            var point = this.PolyPoints[i];
            pPoints.push('{lat:' + point.lat + ',lon:' + point.lon + ',pol:1}');
        }
        aScript.push('  var polygons=[' + pPoints.join(',\r\n') + '];');
        aScript.push(' var fpPath = new google.maps.Polygon({ ');

        aScript.push(' strokeColor: "#3333cc", ');
        aScript.push(' strokeOpacity: 1.0,');
        aScript.push('  strokeWeight: 0 ,');
        aScript.push('  fillColor: "#FF0000",');
        aScript.push('  fillOpacity: 0.35');
        aScript.push(' });');
        aScript.push('  fpPath.setMap(map);');
        aScript.push(' Addlines(polygons,map,fpPath);');
    }
    aScript.push('}');


    aScript.push('</script>');
    //alert(aScript);
    return aScript.join('\r\n');
}



// Modifications of the core routines of FCKeditor:

FCKXHtml.GetXHTML = Inject(FCKXHtml.GetXHTML, null, FCK.GoogleMapsHandler.GetXHTMLAfter ) ;

FCKXHtml.TagProcessors.img = function( node, htmlNode, xmlNode )
{
	if ( htmlNode.getAttribute( 'MapNumberV3' ) )
	{
		var oMap = FCK.GoogleMapsHandler.getMap( htmlNode.getAttribute( 'MapNumberV3' ) ) ;
		FCK.GoogleMapsHandler.CreatedMapsNames.push( oMap.number ) ;

		oMap.updateScript( htmlNode );
		node = FCK.GetRealElement( htmlNode ) ;
		if ( FCK.GoogleMapsHandler.CreatedMapsNames.length == 1 )
		{
			// If it is the first map, insert the google maps script
			var index = FCKTempBin.AddElement( FCK.GoogleMapsHandler.GenerateGoogleScript() ) ;
			var prefix = ( FCKConfig.ProtectedSource._CodeTag || 'PS..' ) ;
			oScriptCommentNode = xmlNode.ownerDocument.createComment( '{' + prefix + index + '}' ) ;
			xmlNode.appendChild( oScriptCommentNode ) ;
		}

		return xmlNode.ownerDocument.createComment( node.nodeValue ) ;
	}

	if (typeof FCK.GoogleMapsHandler.previousProcessor == 'function') 
		node = FCK.GoogleMapsHandler.previousProcessor( node, htmlNode, xmlNode ) ;
	else
		node = FCKXHtml._AppendChildNodes( node, htmlNode, false ) ;

	return node ;
};

/**
  @desc  inject the function
  @author  Aimingoo&Riceball
*/
function Inject( aOrgFunc, aBeforeExec, aAtferExec ) {
  return function() {
    if (typeof(aBeforeExec) == 'function') arguments = aBeforeExec.apply(this, arguments) || arguments;
    //convert arguments object to array
    var Result, args = [].slice.call(arguments); 
    args.push(aOrgFunc.apply(this, args));
    if (typeof(aAtferExec) == 'function') Result = aAtferExec.apply(this, args);
    return (typeof(Result) != 'undefined')?Result:args.pop();
  } ;
}
