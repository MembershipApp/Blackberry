$(function(){
// Data model for location
var Location = Backbone.Model.extend({
	defaults:{
		lat:0.0,
		lng: 0.0,
		label: null
	},
	toGeoLocation: function(){
		        return new google.maps.LatLng(this.get('lat'), this.get('lng'));
	}
});

// Collection for location
var Locations = Backbone.Collection.extend({
    model: location
});

// Map model with all locations
var MapModel = Backbone.Model.extend({
    initialize: function(operations) {

        Backbone.Model.prototype.initialize.apply(this, arguments);

        this.locations = new Locations;
        this.center = operations.center;

        var self = this;

        this.locations.on('add', function(newLocation) {
            self.trigger('locations:add', newLocation);
        });

		this.center.on('change', function(newCenter) {
            self.trigger('center:change', newCenter);
        });
    },

	defaults: {
        label: null
    }
});

var GeoLocationView = Backbone.View.extend({
    el: $('#mapCanvas'),

    initialize: function(operations) {
        this.model = operations.model;
        this.gmap = operations.gmap;

        var self = this;
        // update the position when the point changes
        this.model.on('change', function(updatedLocation) {
            self.gMarker.setPosition(updatedLocation.toGeoLocation());
        });
        // render the initial point state
        this.render();
    },

    render: function() {
        this.gMarker = new google.maps.Marker({
            position: this.model.toGeoLocation(),
            map: this.gmap,
            title: this.model.get('label')
        });
    },

    remove: function() {
        this.gMarker.setMap(null);
        this.gMarker = null;
    }
});


// updating the map view everytime a center changes its position
var GeoMapView = Backbone.View.extend({
    el: $('#mapCanvas'),

    initialize: function(opts) {
        // watch for location changes
        this.mapModel = opts.model;
        this.locationToViews = {};


        var mapOptions = {
            // set how much zooming we require
            zoom: 9,
			// update center
            center: this.mapModel.center.toGeoLocation(),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        // Actual loading of map into map view for backbone
        this.gmap = new google.maps.Map(document.getElementById('mapCanvas'), mapOptions);

        // attach to the map
        var self = this;
        this.mapModel.on('locations:add', function(newLocation) {
            // convert added point to new marker
            self.locationToViews[newLocation.cid] = new GeoLocationView({
                model: newLocation,
                gmap: self.gmap
            });
        });

    },

    render: function() {}
});



});