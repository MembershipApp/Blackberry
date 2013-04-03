var jsonObj = {
native_function:"",
jsonCallBack:"",
arguments:{membership_id:123,rememberme:0}
};

var fromLogin = true;
var onDashboard = false;

function loginBtnEvent(){
    var membership_id = document.getElementById("membership_id").value;
    var remembermeflag=document.getElementById("remembermecheckbox").checked;
    
    
    // put validation for membership number here
    
    // create json which contains membership id and "remember me" flag values and pass json to native
    /*
    var loginJsonObj={
    native_function:"loginActivity",
    jsonCallBack:"jsonCallBack",
    arguments:[{membershipid:membership_id,rememberme:remembermeflag}]
    };
    */
    jsonObj.native_function="login";
    jsonObj.jsonCallBack="loginCallBack";
    jsonObj.arguments={membershipid:membership_id,rememberme:remembermeflag};
    var jsonStr=JSON.stringify(jsonObj);
    //    window.location="js-call:"+jsonStr;
    webToNativeCall(jsonStr);
}

function getRememberValue() {
	jsonObj.native_function="getCachedUsername";
    jsonObj.jsonCallBack="getRememberCallback";
    var jsonStr=JSON.stringify(jsonObj);
    //    window.location="js-call:"+jsonStr;
    webToNativeCall(jsonStr);
}

function getRememberCallback(obj){
	document.getElementById("membership_id").value = obj.username;
}

function playVideo() {
	jsonObj.native_function="playVideo";
    jsonObj.jsonCallBack="";
    var jsonStr=JSON.stringify(jsonObj);
    webToNativeCall(jsonStr);
}

function onCheckInClick() {
	checkin();
}

function helpBtnEvent(){
	alert("help");
}

function webToNativeCall(obj){
    // iPhone
    if(navigator.platform=="iPhone Simulator"){
        window.location = "js-call:"+obj;
    }
    // Android
    else if(navigator.platform=="Android"){
        ISOSBridge.serverCall(obj);
    }
    // BB
    else{
    	bb.doNative(obj);
    }
}

function loginCallBack(json){
    var jsonObj = json;
    var statusCode = jsonObj.StatusCode;
    
    if(statusCode!=0){
        alert("Invalid credentials. Please try again later");
    }
    // Display Dashboard on successful login
    else{
        /*
        var dashboard = {
        method:"renderDashboard"
        };
         */
//    	document.getElementById("videotour").style.display="none";
//    	document.getElementById("LoginContent").style.display="none";
//    	document.getElementById("wrapper").style.display="block";
    	
		var dash = new dashBoardView();
		fromLogin = true;
		onDashboard = true;
//    	
//        jsonObj.native_function="renderDashboard";
//        var jsonStr=JSON.stringify(jsonObj);
//        webToNativeCall(jsonStr);
        //        window.location="js-call:"+jsonStr;
    }
}

function checkin()
{
    jsonObj.native_function="checkin";
    jsonObj.jsonCallBack="checkinCallBack";
    var jsonStr=JSON.stringify(jsonObj);
    webToNativeCall(jsonStr);
    //    window.location = "js-call:"+jsonStr;
}

function checkinCallBack(json){
//    alert("JSON in checkin call back: "+json);
    var jsonObj = json;
    var statusCode = jsonObj.StatusCode;
    
    if(statusCode!=0){
        alert("Check-in failed");
    } else {
    	if(fromLogin == true) {
    		fromLogin = false;
    	} else {
    		var chk = new checkInSuccessView();
    		onDashboard = false;
    	}
    }

}

function onAlertClick() {
	
	var alert = new alertView();
	onDashboard = false;
}
function goBack() {
	var dash = new dashBoardView();
	onDashboard = true;
}

function showAlert() {
//	getCountry("getCountryOnAlerts");
	jsonObj.native_function = "showAlerts";
    jsonObj.jsonCallBack = "updateList";
    var jsonStr = JSON.stringify(jsonObj);
    webToNativeCall(jsonStr);
}

function showAlertsCallback(alerts) {
	for ( var i = 0; i < alerts.length; i++) {
		updateList(alerts[i],i);
	}
}
var h3;
function updateList(data) {
	for ( var i = 0; i < data.length; i++) {
		var datavalues = data[i].split("$$");
		var ul = document.getElementById("AlertsList");
		var li = document.createElement("li");
		var list_item = document.createElement("div");
		var img = document.createElement("img");
		img.src = "img/Plus__Orange.png";
		var div = document.createElement('div');
		div.setAttribute('class', 'plusimage'+i);
		div.appendChild(img);
		$('.plusimage'+i).css('float', 'left');
		$('.plusimage'+i).css('width', '40px');
		
		var subStr1 = "";
		var subStr2 = "";
		list_item.appendChild(div);
		var h3 = document.createElement("h3");

		var str = datavalues[0];console.log('TEXT '+str);
		var h3Value = document.createTextNode(str);
		h3.appendChild(h3Value);
		list_item.appendChild(h3);
		var p = document.createElement("p");
		var pValue = document.createTextNode(datavalues[1]);
		p.appendChild(pValue);
		list_item.appendChild(p);
		li.appendChild(list_item);
		ul.insertBefore(li, ul.getElementsByTagName("li")[0]);
		$('.plusimage'+i+' img').css('padding-top', (h3.offsetHeight - 32)/2);
	}
}

function getNextText(str, startIndex, EndIndex) {
	return str.substring(startIndex, EndIndex);
}

function getDate() {
	var d1 = new Date();
	return d1.toString();
}

function comingFeature() {
	alert("This feature is coming soon!");
}

function getCountry(callback) {
	jsonObj.native_function = "getCountry";
    jsonObj.jsonCallBack = callback;
    var jsonStr = JSON.stringify(jsonObj);
    webToNativeCall(jsonStr);
}

function getCountryCallback(obj) {
	document.getElementById("countryName").innerHTML = obj.country;
}

function getCountryOnAlerts(obj) {
	document.getElementById("country").innerHTML = obj.country;
}

function getCountryOnCheckinSuccess(obj) {
	document.getElementById("locationName").innerHTML = "You've successfully checked into " + obj.country + " and International SOS has been notified.";
}

function getCountryOnCheckin(obj) {
	document.getElementById("countryOnCheckin").innerHTML = obj.country;
}

function loadAddress() {
	getCountry("getCountryCallback");
	jsonObj.native_function = "getLocation";
    jsonObj.jsonCallBack = "loadAddressCallback";
    var jsonStr = JSON.stringify(jsonObj);
    webToNativeCall(jsonStr);
}

function loadAddressCallback(obj) {
	document.getElementById("countryName").innerHTML = obj.country;
	if(fromLogin == true) {
		checkin();
	}
}

function loadMap() {
//	getCountry("getCountryOnCheckin");
	jsonObj.native_function = "getLocation";
    jsonObj.jsonCallBack = "loadMapCallback";
    var jsonStr = JSON.stringify(jsonObj);
    webToNativeCall(jsonStr);
}

function loadMapCallback(obj) {
	var width = screen.width;
	document.getElementById("country").innerHTML=obj.country;
	document.getElementById("latitudeValue").innerHTML=obj.lat;
	document.getElementById("longitudeValue").innerHTML=obj.lng;
	document.getElementById('image').src = "http://maps.googleapis.com/maps/api/staticmap?center=" + obj.lat + "," + obj.lng + "&zoom=13&size=" + width + "x200&maptype=roadmap&markers=label:S%7C" + obj.lat + "," + obj.lng + "&sensor=false";
}

function setCountry(obj) {
	document.getElementById("countryName").innerHTML = obj.country;
}

function isOnDashboard() {
	return onDashboard;
}

function onCheckin() {
	var checkin = new checkInView();
	onDashboard = false;
}

function getExternalLink() {
	jsonObj.native_function = "getExternalLink";
    jsonObj.jsonCallBack = "getExternalLinkCallback";
    var jsonStr = JSON.stringify(jsonObj);
    webToNativeCall(jsonStr);
}

function getExternalLinkCallback() {
	
}
/*
function nativeToJSCall(json){
    
    var jsonObj = JSON.parse(json);
    
    var statusCode = jsonObj.StausCode;
    
    if(statusCode!=0){
        alert("Invalid credentials. Please try again later");
    }
    // Display Dashboard on successful login
    else{
        var dashboard = {
        method:"renderDashboard"
        };
        var jsonStr=JSON.stringify(dashboard);
        webToNativeCall(jsonStr);
        //        window.location="js-call:"+jsonStr;
    }
}
*/