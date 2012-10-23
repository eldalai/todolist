(function(context) {
	'use strict';
	
	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'utils'
	        ], function($,_,Backbone){
		
		context.TaskDetailView = Backbone.View.extend({
			
			initialize:function () {
				this.template = _.template(context.tpl.get('taskdetail'));
			},
			
			render:function () {
				$(this.el).html(this.template(this.model.toJSON()));
				return this;
			}
			
		});
		
	});
	
})(app);