define([
  'jquery',
  'underscore',
  'backbone',
  'router', // Request router.js
  'bootstrap-transition','bootstrap-alert','bootstrap-modal','bootstrap-dropdown','bootstrap-scrollspy',
  'bootstrap-tab','bootstrap-tooltip','bootstrap-popover','bootstrap-button','bootstrap-collapse',
  'bootstrap-carousel','bootstrap-typeahead'
], function($, _, Backbone, Router){
	
// Tell jQuery to watch for any 401 or 403 errors and handle them appropriately
	$.ajaxSetup({
		statusCode: {
			401: function(){
				// Redirect the to the login page.
				window.location.replace('#login');
				
			},
			403: function() {
				// 403 -- Access denied
				window.location.replace('#denied');
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