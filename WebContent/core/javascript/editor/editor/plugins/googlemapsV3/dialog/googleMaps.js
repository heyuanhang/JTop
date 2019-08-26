/*
  GoogleMaps Plugin for FCKeditor
*/

// Rounds a number to just "precission" decimals
Number.prototype.RoundTo = function( precission )
{
	var base = Math.pow(10, precission) ;
	return Math.round( this * base ) / base ;
} ;

function Import(aSrc) {
   document.write('<scr'+'ipt type="text/javascript" src="' + aSrc + '"></sc' + 'ript>');
}

var oEditor		= window.parent.InnerDialogLoaded() ;
var FCK			= oEditor.FCK ;
var FCKLang		= oEditor.FCKLang ;
var FCKConfig	= oEditor.FCKConfig ;
var FCKTools = oEditor.FCKTools ;

Import(FCKConfig.FullBasePath + 'dialog/common/fck_dialog_common.js');

Import('http://maps.google.com/maps/api/js?sensor=false');

//Import('polyline.js');

window.parent.AddTab( 'Map', "地图" ) ;
window.parent.AddTab( 'Search',"目标搜索" ) ;
window.parent.AddTab( 'Marker', "文字" ) ;
window.parent.AddTab( 'Line', "线标" ) ;

var ActiveTab ;

// Function called when a dialog tag is selected.
function OnDialogTabChange( tabCode )
{
	ActiveTab = tabCode ;

	ShowE('MapInfo', ( tabCode == 'Map' ) ) ;
	ShowE('SearchInfo', ( tabCode == 'Search' ) ) ;
	ShowE('MarkerInfo', ( tabCode == 'Marker' ) ) ;
	ShowE('LineInfo', ( tabCode == 'Line' ) ) ;

	if (tabCode != 'Marker')
		FinishAddMarker() ;

	ResizeParent() ;
}


// Get the selected map (if available).
var oFakeImage = FCK.Selection.GetSelectedElement() ;
var oParsedMap ;

if ( oFakeImage )
{
	if ( oFakeImage.getAttribute( 'MapNumberV3' ) )
	{
		oParsedMap = FCK.GoogleMapsHandler.getMap( oFakeImage.getAttribute( 'MapNumberV3' ) );
		oParsedMap.updateDimensions( oFakeImage );
	}
	else
		oFakeImage = null ;
}
if ( !oParsedMap )
		oParsedMap = FCK.GoogleMapsHandler.createNew() ;


window.onload = function()
{
	// Translate the dialog box texts.
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	var btn = GetE('btnAddNewMarker') ;
	btn.alt = btn.title = "新增标记" ;

	//GetE('Polylinenote').innerHTML = FCKLang.GMapsLineInstructions;
	var btn1 = GetE('btnAddNewLine');
	btn1.alt = btn1.title = "折线管理";

	var btn2 = GetE('btnAddNewPoly');
	btn2.alt = btn2.title = "多边形管理";

	var btn3 = GetE('btnDelPoly');
	btn3.alt = btn3.title = "删除最后增加的线标";
	// Load the selected element information (if any).
	LoadSelection() ;
	ConfigureEvents() ;

	// Activate the "OK" button.
	//SetupHelpButton(oEditor.FCKPlugins.Items['googlemapsV3'].Path + 'docs/' + FCKLang.GMapsUserHelpFile);
	window.parent.SetOkButton( true ) ;
//2.6
	if (window.parent.Sizer) window.parent.SetAutoSize( true ) ;
} ;

function ConfigureEvents()
{
	GetE('txtWidth').onblur = UpdateDimensions ;
	GetE('txtHeight').onblur = UpdateDimensions ;
	GetE('btnAddNewLine').onclick = function () { AddLine(); return false; };
	GetE('btnAddNewPoly').onclick = function () { AddPoly(); return false; };
	GetE('btnDelPoly').onclick = function () { DelPoly(); return false; };
	GetE('btnAddNewMarker').onclick = function () {AddMarker(); return false;};

	FCKTools.AddEventListener(GetE('searchDirection') , 'keydown', searchDirection_keydown) ;
	GetE('searchButton').onclick = function () { doSearch(); return false; };
}

function searchDirection_keydown(e)
{
	if (!e) e = window.event ;
	if ( e.keyCode == 13 )
	{
		doSearch();
		if (e.preventDefault) e.preventDefault() ;
		if (e.stopPropagation) e.stopPropagation() ;
		return false;
	}
}
//地图
var map;
var mapDiv;
var geocoder;
//层集合
var markers = [];
//当前层
var activeMarker = null;
var Mode = '';
var infowindow;
var allMapTypes;

//折线
var polyline;
var polygon;

function LoadSelection()
{
	GetE('txtWidth').value  = oParsedMap.width ;
	GetE('txtHeight').value = oParsedMap.height ;

	//GetE('cmbZoom').value  = oParsedMap.zoom ;
	GetE('txtCenterLatitude').value  = oParsedMap.centerLat ;
	GetE('txtCenterLongitude').value = oParsedMap.centerLon ;

	var markerpoints = oParsedMap.markerPoints;
	var lpoints = oParsedMap.LinePoints;
	
	SetPreviewElement(oParsedMap.mapType);
	UpdatePreview() ;
	for (var i = 0; i < markerpoints.length; i++) {
	    var point = new google.maps.LatLng(parseFloat(markerpoints[i].lat), parseFloat(markerpoints[i].lon));
	    AddMarkerAtPoint(point, markerpoints[i].title, false);
	}
	for (var i = 0; i < lpoints.length; i++) {
	    var point = new google.maps.LatLng(parseFloat(lpoints[i].lat), parseFloat(lpoints[i].lon));
	    addLatLng(point);
	}
	lpoints = oParsedMap.PolyPoints;
	for (var i = 0; i < lpoints.length; i++) {
	    var point = new google.maps.LatLng(parseFloat(lpoints[i].lat), parseFloat(lpoints[i].lon));
	    addLatLng_P(point);
	}
}

//#### The OK button was hit.
function Ok()
{

	oEditor.FCKUndo.SaveUndoStep() ;

	oParsedMap.width = GetE('txtWidth').value ;
	oParsedMap.height = GetE('txtHeight').value ;

	//oParsedMap.zoom = GetE('cmbZoom').value ;
	oParsedMap.zoom = map.getZoom();
	oParsedMap.centerLat = GetE('txtCenterLatitude').value ;
	oParsedMap.centerLon = GetE('txtCenterLongitude').value ;

	oParsedMap.mapType = GetMapTypeIndex(map);

	var markerpoints = [];
	//markerpoints.push({ lat: 31.22384, lon: 121.5297, title: '【向城会所】浦东新区向城路17号J07  电话:021-50586796  ' });
	for (var i=0; i<markers.length ; i++) {
    if (markers[i].title.substr(0,1)!="#")
	    markerpoints.push({ lat: markers[i].position.lat().RoundTo(5), lon: markers[i].position.lng().RoundTo(5), title: markers[i].title });
	}
	oParsedMap.markerPoints = markerpoints ;

	var lpoints = [];
	var path = polyline.getPath();
	for (var i = 0; i < path.getLength(); i++) {
	    lpoints.push({ lat: path.getAt(i).lat().RoundTo(5), lon: path.getAt(i).lng().RoundTo(5)});
	}
	oParsedMap.LinePoints = lpoints;

	var ppoints = [];
	var ppath = polygon.getPath();
	for (var i = 0; i < ppath.getLength(); i++) {
	    ppoints.push({ lat: ppath.getAt(i).lat().RoundTo(5), lon: ppath.getAt(i).lng().RoundTo(5) });
	}
	oParsedMap.PolyPoints = ppoints;

		var script = oParsedMap.BuildScript() ;

	if ( !oFakeImage )
		oFakeImage = oParsedMap.createHtmlElement() ;

	oParsedMap.updateHTMLElement(oFakeImage);
	
	return true ;
}

function GetMapTypeIndex(m)
{
    //var name = m.getCurrentMapType().getName() ;
    var name = m.mapTypeId;
    //alert(name);
    //alert(allMapTypes[0]);
	for (var i=0; i < allMapTypes.length ; i++)
	{
		if ( name == allMapTypes[i])
			return i ;
	}
	return 0;
}
var fckDiv;
function SetPreviewElement( mapType )
{
    allMapTypes = [google.maps.MapTypeId.ROADMAP, google.maps.MapTypeId.SATELLITE, google.maps.MapTypeId.HYBRID, google.maps.MapTypeId.TERRAIN];
    var maptype = oParsedMap.mapType;
    if (maptype < 0 || maptype >= allMapTypes.length)
        maptype = 0;
    mapDiv = document.getElementById("GMapPreview");
    //document.getElementById("fckbg_div").style.filter = "alpha(opacity=60)";
    fckDiv = document.getElementById("fck_div");

    var myLatlng = new google.maps.LatLng(31.22473, 121.52956); 
	UpdateDimensions() ;
	var myOptions = {
	    //zoom: 11,
	    zoom: parseInt(oParsedMap.zoom),
	    center: myLatlng,
	    navigationControl: true,
	    scaleControl: true,
        mapTypeControl:true,
        mapTypeId: allMapTypes[maptype]
       }
	map = new google.maps.Map(mapDiv, myOptions);

	var polylineOptions = {
	    strokeColor: '#3333cc',
	    strokeOpacity: 0.5,
	    strokeWeight: 3
	};
    polyline = new google.maps.Polyline(polylineOptions);
    polyline.setMap(map);
    var polygonOptions = {
        strokeColor: '#3333cc',
        strokeOpacity: 0.5,
        strokeWeight: 3,
        fillColor: "#000033", 
        fillOpacity: 0.35 
    };
    polygon = new google.maps.Polygon(polygonOptions);
    polygon.setMap(map);
	google.maps.event.addListener(map, "drag", Map_Drag); 
	google.maps.event.addListener(map, 'click', function (event) { Map_Click(event.latLng); });
}

//添加折线
function addLatLng(point) 
{   
    var path = polyline.getPath();
    path.push(point);
    var marker = new google.maps.Marker({
        position: point,
        title: '#L_' + path.getLength(),
        map: map,
        draggable: true
    });
    markers.push(marker);
    google.maps.event.addListener(marker, "click", function () {
        EditMarker(this);
    });
    google.maps.event.addListener(marker, "dragend", function () {
        DragEndPoly(this);
    });
}
function addLatLng_P(point) {
    var path = polygon.getPath();
    path.push(point);
    var marker = new google.maps.Marker({
        position: point,
        title: '#P_' + path.getLength(),
        map: map,
        draggable: true
    });
    markers.push(marker);
    google.maps.event.addListener(marker, "click", function () {
        EditMarker(this);
    });
    google.maps.event.addListener(marker, "dragend", function () {
        DragEndPoly(this);
    });
}
function DragEndPoly(obj) {
//    if (ActiveTab == 'Line') {
        if (obj.title.substr(0, 3) == '#L_') {
            var path = polyline.getPath();
            path.setAt(obj.title.substr(3) - 1, obj.position);
        }
        else if (obj.title.substr(0, 3) == '#P_') {
            var path = polygon.getPath();
            path.setAt(obj.title.substr(3) - 1, obj.position);
        }
//    } 
}

function Map_Drag()
{
	var point = map.getCenter() ;
	GetE("txtCenterLatitude").value = point.lat().RoundTo(5) ;
	GetE("txtCenterLongitude").value = point.lng().RoundTo(5) ;
}
function Map_MoveEnd()
{
	Map_Drag() ;
}

function Map_Click(point) 
{
    switch (ActiveTab) 
    {
	    case 'Map':
		//Nothing
		break;
		case 'Marker':
		if (Mode == 'AddMarker')
			AddMarkerAtPoint( point, "文字" || "提示文字", true ) ;
		break;
        case 'Line':
            if (Mode == 'AddLineer') {
                addLatLng(point);
            }
            else if (Mode == 'AddPolyer') {
                addLatLng_P(point);
            }
            break;
    }
}


function UpdatePreview()
{
	if ( !map )
		return ;
}

function UpdateDimensions()
{
	mapDiv.style.width = GetE('txtWidth').value + 'px' ;
	mapDiv.style.height = GetE('txtHeight').value + 'px' ;
	fckDiv.style.width = GetE('txtWidth').value + 'px';
	ResizeParent();
}

//生成弹出编辑层html字符串
function generateEditPopupString(title) {

    return '<div style="width:250px; height:7em;">' +
			'<label for="txtMarkerText">' + "文字" + '</label><br>' +
			'<textarea id="txtMarkerText" style="width:250px; height:4em;">' + title.replace(/<br>/g, '\n') + '</textarea><br>' +
			'<div style="float:left"><input type="button" id="btnDeleteMarker" onclick="DeleteCurrentMarker()" value="' + "删除标记" + '"></div>' +
			'<div style="float:right"><input type="button" id="btnOK2" onclick="UpdateCurrentMarker()" value="' + FCKLang.DlgBtnOK + '">' +
			'<input type="button" id="btnCancel2" onclick="CloseInfoWindow()" value="' + FCKLang.DlgBtnCancel + '"></div>' +
		'</div>'
		;

}
function doSearch()
{
    if (!geocoder) geocoder = new google.maps.Geocoder();
    if (geocoder) {
        geocoder.geocode({ 'address': GetE('searchDirection').value },
        function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                map.setCenter(results[0].geometry.location);
                AddMarkerAtPoint(results[0].geometry.location, GetE('searchDirection').value,true);
            }
            else {
                alert("%s 寻找不到".replace("%s", GetE('searchDirection').value) + "\r\n 原因: " + status);
            }
        } );
             }
}
//改变多边形模式
function AddPoly() {
    if (Mode != 'AddPolyer') {
        Mode = 'AddPolyer';
        GetE('btnAddNewPoly').src = '../images/AddPolyDown.png';
        GetE('btnAddNewLine').src = '../images/AddLine.png';
        GetE('Polylinenote').innerHTML = "鼠标点击增加地点";        
    }
    else {
        Mode = '';
        GetE('btnAddNewPoly').src = '../images/AddPoly.png';
        GetE('Polylinenote').innerHTML = "请选择模式以增减线标";        
    }
}
function AddLine() {
    if (Mode != 'AddLineer') {
        Mode = 'AddLineer';
        GetE('btnAddNewLine').src = '../images/AddLineDown.png';
        GetE('btnAddNewPoly').src = '../images/AddPoly.png';
        GetE('Polylinenote').innerHTML = "鼠标点击增加地点";
    }
    else {
        Mode = '';
        GetE('btnAddNewline').src = '../images/AddLine.png';
        GetE('Polylinenote').innerHTML = "请选择模式以增减线标";
    }
}
// Change mode to enable addition of a new marker.
function AddMarker()
{
	if (Mode=='AddMarker')
	{
		FinishAddMarker() ;
		return ;
	}

	GetE( 'btnAddNewMarker' ).src = '../images/AddMarkerDown.png' ;
	GetE( 'instructions' ).innerHTML = "在地图上新增标记" ;
	Mode = 'AddMarker' ;
	// Change cursor type
	mapDiv.firstChild.firstChild.style.cursor = "crosshair" ;
}
//创建层 并添加监听事件 点击EditMarker 参数point 定位点 text 提示文字
function AddMarkerAtPoint( point, text, interactive )
{
    var marker = new google.maps.Marker({
        position: point,
        map:map,
        title: text,
        draggable:true
    });
    markers.push(marker);
	google.maps.event.addListener(marker, "click", function () {
	    EditMarker(this);
	});
	
	FinishAddMarker();

	if (interactive)
		EditMarker( marker );
}

function FinishAddMarker()
{
	Mode = '';
	GetE( 'btnAddNewMarker' ).src = '../images/AddMarker.png' ;
	GetE( 'instructions' ).innerHTML = '';
	//mapDiv.firstChild.firstChild.style.cursor = "default" ;
}
function removePolyMark(text) {
    for (var j = 0; j < markers.length; j++) {
        if (markers[j].title == text) {
            markers[j].setMap(null);
            markers.splice(j, 1);
            break;
        }
    }
}
function DelPoly() {
    if (ActiveTab == 'Line') {
        var nums;
        var path;
        if (Mode == "AddLineer") {
            path = polyline.getPath();
            nums = path.getLength()
            if (nums > 0) {
                removePolyMark("#L_" + nums);
                path.removeAt(nums - 1);
            }
        }
        else if (Mode = "AddPolyer") {
            path = polygon.getPath();
            nums = path.getLength()
            if (nums > 0) {
                removePolyMark("#P_" + nums);
                path.removeAt(nums - 1);
            }
        }
    }
}
function EditMarker(obj) {
    CloseInfoWindow();
    if (ActiveTab!='Marker' || obj.title.substr(0,1)=="#")
	{
	    infowindow = new google.maps.InfoWindow({
	        content: obj.title
	    });
	    infowindow.open(map, obj);
		return;
	}
	activeMarker = obj ;
	Mode = 'EditMarker';
	try {
	    edittitle(obj.title);
	}
	catch (e) {
	    /* 文本框编辑层title */
	    infowindow = new google.maps.InfoWindow({
	    content: generateEditPopupString(obj.title)
	    });
	    infowindow.open(map, activeMarker);
	}
}
function edittitle(text) {
    var oEditor = FCKeditorAPI.GetInstance('txtMarkerText');
    oEditor.SetHTML(text, true);
    fckDiv.style.display = document.getElementById("fckbg_div").style.display = "block";
}
function CloseInfoWindow() {

    if (Mode == "EditMarker" || Mode == "AddMarker")
        Mode = '';
    try {
        infowindow.close();
    }
    catch (e)
    { }
	activeMarker = null ;
}
function Closefckdiv() {
    if (Mode == "EditMarker" || Mode == "AddMarker")
        Mode = '';
    fckDiv.style.display =document.getElementById("fckbg_div").style.display= "none";
    activeMarker = null;
}
function Updatefckdiv() {
    var oEditor = FCKeditorAPI.GetInstance('txtMarkerText');
    activeMarker.title = oEditor.GetXHTML();
    activeMarker.setMap(null);
    activeMarker.setMap(map);
    Closefckdiv();
}
function UpdateCurrentMarker()
{
    activeMarker.title = GetE('txtMarkerText').value;//.replace(/\n/g, '<br>');
    activeMarker.setMap(null);
    activeMarker.setMap(map);
	CloseInfoWindow();
}
function DeleteCurrentMarkerfckdiv() {
    for (var j = 0; j < markers.length; j++) {
        if (markers[j] == activeMarker) {
            markers.splice(j, 1);
            break;
        }
    }
    var tmp = activeMarker;
    Closefckdiv();
    tmp.setMap(null);
}
function DeleteCurrentMarker()
{
	// Remove it from the global array
	for ( var j = 0 ; j < markers.length ; j++ )
	{
		if ( markers[j] == activeMarker)
		{
				markers.splice(j, 1);
				break ;
		}
	}
	var tmp = activeMarker ;
	CloseInfoWindow() ;
	tmp.setMap(null);
}

// Full resize in x and y and centering
function ResizeParent()
{
	var oParentWindow = window.parent;
//2.6
	if (window.parent.Sizer) {
	oParentWindow.Sizer.RefreshSize() ;
	return;
}
	var oInnerWindow = window ;
	var oInnerDoc = oInnerWindow.document ;
	var iDiff, iFrameHeight, iFrameWidth ;

		if ( document.all )
			iFrameHeight = oInnerDoc.body.offsetHeight ;
		else
			iFrameHeight = oInnerWindow.innerHeight ;

		var iInnerHeight = oInnerDoc.body.scrollHeight ;
		iDiff = iInnerHeight - iFrameHeight ;

		if ( iDiff !== 0 )
		{
			if ( document.all )
				oParentWindow.dialogHeight = ( parseInt( oParentWindow.dialogHeight, 10 ) + iDiff ) + 'px' ;
			else
				oParentWindow.resizeBy( 0, iDiff ) ;
		}

		// Width:
		if ( document.all )
			iFrameWidth = oInnerDoc.body.offsetWidth ;
		else
			iFrameWidth = oInnerWindow.innerWidth ;

		var iInnerWidth = oInnerDoc.body.scrollWidth ;
		iDiff = iInnerWidth - iFrameWidth ;

		if ( iDiff !== 0 )
		{
			if ( document.all )
				oParentWindow.dialogWidth = ( parseInt( oParentWindow.dialogWidth, 10 ) + iDiff ) + 'px' ;
			else
				oParentWindow.resizeBy( iDiff, 0 ) ;
		}

}

function SetupHelpButton( url )
{
	var doc = window.parent.document ;
	var helpButton = doc.createElement( 'INPUT' ) ;
	helpButton.type = 'button' ;
	helpButton.value = "帮助" ;
	helpButton.className = 'Button' ;
	helpButton.onclick = function () { window.open( url ); return false; };

	var okButton = doc.getElementById( 'btnOk' ) ;
	var cell = okButton.parentNode.previousSibling ;
	if (cell.nodeName != 'TD')
			cell = cell.previousSibling ;
	cell.appendChild( helpButton );
}