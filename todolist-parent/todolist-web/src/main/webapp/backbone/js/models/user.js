(function(context) {
	'use strict';

	define([
	        'jquery',
	        'underscore',
	        'backbone'
	    ], function($,_,Backbone){
		
		// User Model
		// ----------
		
		// Our basic **User** model has `id`, `name` attributes.
		
		context.User = Backbone.Model.extend({
			
			urlRoot:"../../rest/users",
			
			// Default attributes for the user
			// and ensure that each user created has `id` and `name` keys.
			defaults: {
				id: 0,
				name: '',
				password:''
			}
		
		});
	});
	

}(app));