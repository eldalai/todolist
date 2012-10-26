(function(context) {
	'use strict';
	
	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'text!../../tpl/taskdetail.html'
	        ], function($,_,Backbone,template){
		
		context.TaskDetailView = Backbone.View.extend({
			
			initialize:function () {
				this.template = _.template(template);
			},
			
			render:function () {
				$(this.el).html(this.template(this.model.toJSON()));
				return this;
			}
			
		});
		
	});
	
})(app);