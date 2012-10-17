var app = app || {};

(function() {
	'use strict';

	// Todo Model
	// ----------

	// Our basic **Todo** model has `title`, `order`, and `completed` attributes.
	app.Task = Backbone.Model.extend({
		
		urlRoot:"../../rest/tasks",
		// Default attributes for the todo
		// and ensure that each todo created has `title` and `completed` keys.
		defaults: {
			id: 0,
			title: '',
			taskStatus: '', // PENDING, DONE
			taskType: '' // NORMAL, URGENT 
		},

		// Toggle the `completed` state of this todo item.
		toggle: function() {
			this.save({
				// TODO: cambiar de estado...
				//completed: !this.get('completed')
			});
		}

	});

}());
