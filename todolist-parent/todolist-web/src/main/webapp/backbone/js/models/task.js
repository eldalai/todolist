//var app = app || {};
//
//(function() {
//	'use strict';

	// Todo Model
	// ----------

	// Our basic **Todo** model has `title`, `order`, and `completed` attributes.
	window.Task = Backbone.Model.extend({
		
		urlRoot:"../rest/tasks",
		// Default attributes for the todo
		// and ensure that each todo created has `title` and `completed` keys.
		defaults: {
			id: null,
			title: '',
			taskStatus: '', // PENDING, DONE
			taskType: '' // NORMAL, URGENT 
		}

	});
//
//}());
