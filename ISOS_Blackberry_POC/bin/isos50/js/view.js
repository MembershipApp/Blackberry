window.loginHeaderView = Backbone.View.extend({
      el:'#wrapper',
      initialize: function () {
          //alert('here');
      this.template = _.template(loginHeaderTmpl);
      },
      render: function () {
          //alert('view rendering');
      $(this.el).html(this.template);
      return this;
      }
});

window.videoView = Backbone.View.extend({
      el:'#wrapper',
      initialize: function () {
          //alert('here');
      this.template = _.template(videoTmpl);
     
      },
      render: function () {
          //alert('view rendering');
      $(this.el).append(this.template);
      return this;
      }
});
window.loginView = Backbone.View.extend({
      el:'#wrapper',
      initialize: function () {
      this.template = _.template(loginFormTmpl);
     
      },
      render: function () {
          //alert('view rendering');
      $(this.el).append(this.template);
      return this;
      }
});

window.dashBoardView = Backbone.View.extend({
    el:'#wrapper',
	
    initialize: function () {
    
                                            
    //  Reverse Geocoding
//    var checkinLat=localStorage.getItem('currentLat');
//    var checkinLong=localStorage.getItem('currentLong');
//    var geocoder;
//    var latlng = new google.maps.LatLng(checkinLat,checkinLong);
//    geocoder = new google.maps.Geocoder();
//    geocoder.geocode({'latLng': latlng}, function(results, status) {
//    if (status == google.maps.GeocoderStatus.OK) {
//         if (results[1]) {
//             // format - Country
//             document.getElementById("countryName").innerHTML=results[5].formatted_address;
//             }
//         }
//     });
                                            
    this.template = _.template(dashBoardTmpl);
        this.render();
    },
    render: function () {
    //alert('view rendering');
    //$('#wrapper').append(this.template);
    $('#wrapper').html(this.template);
    loadAddress();
        //$('#wrapper').html('element is loaded');
    $("#wrapper").fadeIn("fast");
    return this;
    }
});


window.alertView = Backbone.View.extend({
    el:'#wrapper',
    
    initialize: function () {
//    alert('here');
    this.template = _.template(alertInTmpl);
    this.render();
    showAlert();
    },
    
    render: function () {
    //alert('view rendering');
    $('#wrapper').html(this.template);
    //$('#wrapper').html('element is loaded');
    $("#wrapper").fadeIn("fast");
    return this;
    }
});

window.checkInView = Backbone.View.extend({
    el:'#wrapper',
    
    initialize: function () {
    //alert('here');
    this.template = _.template(checkInTmpl);
    this.render();
    },
    render: function () {
    //alert('view rendering');
    $('#wrapper').html(this.template);
      
//    var checkinLat=localStorage.getItem('currentLat');
//    var checkinLong=localStorage.getItem('currentLong');
//    var myLatlng = new google.maps.LatLng( checkinLat, checkinLong );
//    var myOptions = {
//        zoom: 15,
//        center: myLatlng,
//        mapTypeId: google.maps.MapTypeId.ROADMAP
//    }
//              
//    var geocoder;
//    var latlng = new google.maps.LatLng(checkinLat,checkinLong);
//    geocoder = new google.maps.Geocoder();
//
//    geocoder.geocode({'latLng': latlng}, function(results, status) {
//    if (status == google.maps.GeocoderStatus.OK) {
//       if (results[1]) {
//               // format - City,State,Country
//               document.getElementById("country").innerHTML=results[2].formatted_address;
//       }
//    }
//    });
//                                          
//    document.getElementById("latitudeValue").innerHTML=checkinLat;
//    document.getElementById("longitudeValue").innerHTML=checkinLong;
//                                          
//    var map = new google.maps.Map( document.getElementById( "map_canvas" ), myOptions );
//    var marker = new google.maps.Marker({
//        position: map.getCenter(),
//        map: map,
//        title: 'Click to zoom'
//    });
//    map.setCenter(myLatlng);

    loadMap();
    $("#wrapper").fadeIn("fast");
    return this;
    }
});

window.checkInSuccessView = Backbone.View.extend({
    el:'#CheckinLocationDetails',
    
    initialize: function () {
    this.template = _.template(checkinSuccessfulTmpl);
    this.render();
    },
    render: function () {
    $('#btnCheckIn').replaceWith(this.template);
    return this;
    }
});






