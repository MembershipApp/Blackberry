//global declaration for store lat and long value
window.getCurrentLat;
window.getCurrentLong;


/* Fetch Geolocation Data */
function getGeolcation() {
    console.log("inside geolocation");
    navigator.geolocation.getCurrentPosition(onSuccess, onError);
}
/* onSuccess Callback for getCurrentPosition */
var onSuccess = function(position) {
    var geoLatitude=position.coords.latitude;
    var geoLongitude=position.coords.longitude;
    //alert("Current Location \n"+ geoLatitude + ',' + geoLongitude);
    console.log("Sucess--"+ geoLatitude + ',' + geoLongitude);    
    getCurrentLat=geoLatitude;
    getCurrentLong=geoLongitude;
    
    //store getCurrentLat,getCurrentLong
    localStorage.setItem('currentLat', getCurrentLat); //defining a local storage for login User
    localStorage.setItem('currentLong',getCurrentLong);//defining a local storage for remember me flag
};
/* onError Callback receives a PositionError object*/
function onError(error) {
    console.log('code: '    + error.code    + '\n' + 'message: ' + error.message + '\n');
    //alert('code: '    + error.code    + '\n' + 'message: ' + error.message + '\n');
}
