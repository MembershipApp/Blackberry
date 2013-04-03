window.token; //global declaration for store token value

//getting token from iOS native 
function getTokenValue(returnSuccessAPN)
{
    PushNotification.getRegisterDevice(nativePluginResultHandlerAPN,nativePluginErrorHandlerAPN,returnSuccessAPN);
}

//Sucess Notification Token
function nativePluginResultHandlerAPN (notificationToken) {
    //alert("Token SUCCESS: \r\n"+notificationToken );
    token=notificationToken;
}
//Failure Notification Token
function nativePluginErrorHandlerAPN (error) {
    //alert("Token ERROR: \r\n"+error );
}   

//called for geofencing
function getGeofencing(returnSuccessGeofencing)
{
    Geofencing.getGeofacingLocationUpdates(nativePluginResultHandlergetGeofencing,nativePluginErrorHandlergetGeofencing,returnSuccessGeofencing);
}
//Sucess Geofencing
function nativePluginResultHandlergetGeofencing (geoToken) {
    //alert("GeoToken SUCCESS: \r\n"+geoToken );
}
//Failure Geofencing
function nativePluginErrorHandlergetGeofencing(error) {
    //alert("GeoToken ERROR: \r\n"+error );
}


//Called Google Analytics

function getGoogleAnalytics(returnSuccessGoogleAnalytics)
{
    GoogleAnalytics.getGoogleAnalytics(nativePluginResultHandlergetGoogleAnalytics,nativePluginErrorHandlergetGoogleAnalytics,returnSuccessGoogleAnalytics);
}
//Sucess Geofencing
function nativePluginResultHandlergetGoogleAnalytics (getGoogleAnalyticsSucess) {
    //alert("GeoToken SUCCESS: \r\n"+geoToken );
    
    //call check when cross region
    //checkinService("Apurv","");
}
//Failure Geofencing
function nativePluginErrorHandlergetGoogleAnalytics(getGoogleAnalyticsError) {
    //alert("GeoToken ERROR: \r\n"+error );
}

//Called Google Analytics Track Event
function getGoogleAnalyticsTrackEvent(returnSuccessGoogleAnalyticsTrackEvent)
{
    GoogleAnalyticsTrackEvent.getGoogleAnalyticsTrackEvent(successGooleAnalyticsTrackEvent,failedGooleAnalyticsTrackEvent,returnSuccessGoogleAnalyticsTrackEvent);
}

function successGooleAnalyticsTrackEvent(sucessTrackEvent)
{

}
function failedGooleAnalyticsTrackEvent(failedTrackEvent)
{
    
}


//Called Google Analytics Track View
function getGoogleAnalyticsTrackPageView(returnSuccessGoogleAnalyticsTrackEventPageView)
{
    GoogleAnalyticsTrackPageview.getGoogleAnalyticsTrackPageview(successGooleAnalyticsTrackPageView,failedGooleAnalyticsTrackPageView,returnSuccessGoogleAnalyticsTrackEventPageView);
}

function successGooleAnalyticsTrackPageView(sucessTrackPageView)
{
    
}
function failedGooleAnalyticsTrackPageView(failedTrackPageView)
{
    
}

function invokeWebService(returnSuccessResponse){
    InvokeWebService.webServiceCallFromNative(successServiceHandler,errorServiceHandler,returnSuccessResponse);
}

function successServiceHandler(successResponse){
    
    var checkinLat =localStorage.getItem('currentLat');
    var checkinLong =localStorage.getItem('currentLong');

    if(successResponse.method == "login"){
        
        isLogin = 1;
        //called to bodyload.js for location updates
        getGeofencing('geofacing');
        
        //Google Analytics
//        getGoogleAnalytics("UA-35764003-1");
        getGoogleAnalytics("UA-36182850-2");
        
        
        //Google Analytics Track Event
        var options = {category:"category",action:"action",label:"label goes here",value:666};
        getGoogleAnalyticsTrackEvent(options);
        
        //Google Analytics Track Page View
        getGoogleAnalyticsTrackPageView("/test");
        
        //daskboard page
        storeDataToLocalStorage(successResponse.membershipID,successResponse.DeviceToken);
        
        //onCheckInClick();
        var checkinUser=localStorage.getItem('currentUserName');
        var checkinToken=localStorage.getItem('fetchCheckinToken');
        
        //dashboard flag
        flag=true;
        
        //called checkin
        checkinService(checkinUser,checkinToken,checkinLat,checkinLong);
    }
    else if(successResponse.method == "checkin"){
        if(flag==true){
            
            dashboardPage();
        }
        /*
        $('body *').each(function() { //selects all elements in body which got id
                         var lastEle = $(this).last();   //selects last one of them
                         //alert(lastEle.attr('id'));
                         console.log("IDs:"+lastEle.attr('id'));//returns it's id to console log
                         });
        */
        if(manualCheckinFlag==true){
            $("#btnCheckIn").css("display","none");
            var temp =  _.template($("#checkinSuccessfulTmpl").html());
            $("#wrapper").append(temp);
        }
        /*
        else{
            $("#btnCheckIn").css("display","none");
            var temp =  _.template($("#checkinSuccessfulTmpl").html());
            $("#wrapper").append(temp);
            
        }
         */
        var lat = document.getElementById("latitudeValue");
        lat.innerHTML=checkinLat;
        var longi = document.getElementById("longitudeValue");
        longi.innerHTML=checkinLong;
    }

}

function errorServiceHandler(errorResponse){
    if(errorResponse.method=="login"){
//        alert("Invalid membership id");
        navigator.notification.alert("Invalid membership id",errCallBack,'International SOS',"OK");
        
        function errCallBack(){
            
        }
    }
}

var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-36649056-1']);
_gaq.push(['_trackPageview']);
_gaq.push(['_trackEvent', 'GA analytics', 'GA action', 'GA label']);

$(document).ready(function(){
	var ga = document.createElement('script');
	ga.type = 'text/javascript';
	ga.async = true;
	ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	var s = document.getElementsByTagName('script')[0];
	s.parentNode.insertBefore(ga, s);
});
