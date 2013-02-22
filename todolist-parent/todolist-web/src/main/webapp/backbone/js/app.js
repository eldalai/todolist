define([
  'jquery',
  'underscore',
  'backbone',
  'router', 
  'bootstrap'
], function($, _, Backbone, Router){
	
// Tell jQuery to watch for any 401 or 403 errors and handle them appropriately
	$.ajaxSetup({
		cache: false, // dont cached ajax's calls...
		statusCode: {
			401: function(){
				// Redirect the to the login page.
				window.location.replace('#');
				
			},
			403: function() {
				// 403 -- Access denied
				window.location.replace('#denied/'+encodeURIComponent(window.location.hash));
			}
		}
	});

	var initialize = function(){
		// Pass in our Router module and call it's initialize function
		Router.initialize();
	}

	return {
		initialize: initialize
	};
});