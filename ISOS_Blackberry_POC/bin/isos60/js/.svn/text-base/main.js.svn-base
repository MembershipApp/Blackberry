var AppRouter = Backbone.Router.extend({
                                       
                                       routes: {
                                       ""                            : "home"
                                                                       },

                                       home: function ()
                                       {
                                       $('#wrapper').append(new loginHeaderView().render() );
                                       $('#wrapper').append(new videoView().render() );
									    $('#wrapper').append(new loginView().render() );
									   }
                                    });


	

// Instantiation of AppRouter
app = new AppRouter();
Backbone.history.start();