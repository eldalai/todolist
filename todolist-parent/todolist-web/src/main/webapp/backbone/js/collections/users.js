//var app = app || {};
//
//(function() {
//	'use strict';

	// User Collection
	// ---------------

	// The collection of users is backed by *localStorage* instead of a remote
	// server.
window.UserList = Backbone.Collection.extend({

		// Reference to this collection's model.
		model: User,

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
//
//}());
