(function(context) {
	'use strict';

	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'utils',
	        'models/user'
	    ], function($,_,Backbone){
		
		// User Collection
		// ---------------
		
		// The collection of users is backed by *localStorage* instead of a remote
		// server.
		context.UserList = Backbone.Collection.extend({
			
			// Reference to this collection's model.
			model: context.User,
			
			url:"../rest/users",
			
			// Filter down the list of all todo items that are finished.
			otherUsers: function() {
				return this.filter(function( todo ) {
					return true; // todo.get('id'); != user in session
				});
			}
		
		});
//
//	// Create our global collection of **UserList**.
//	app.UserList = new UserList();
		
		
	});
	
//
}(app));