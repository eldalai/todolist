(function(context) {
	'use strict';
	
	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'text!../../tpl/taskslist.html',
	        'text!../../tpl/taskslistitem.html',
	        'models/task',
	        'collections/tasks'
	        ], function($,_,Backbone,template,templateItem){
		
		context.TasksListView = Backbone.View.extend({
			el: $('#content'),
			
			initialize:function () {
				this.template = _.template(template);
			},
			events:{
				"click #createtask":"createtask"
			},
			
			render:function () {
				$(this.el).html(this.template());
				new context.TasksListItemView({el:$('#tasklist-content', this.el)}).render();
			},
			createtask:function(event){
				event.preventDefault();
				window.location.replace('#newtask');
			}
		});
		
		context.TasksListItemView = Backbone.View.extend({
			tagName:'ul',
			
			className:'nav nav-list',
			
			initialize:function () {
//				console.log('Initializing Tasks List View');
				this.model = new context.TaskList();
				this.model.bind("all", this.render, this);
				this.model.fetch();
				
			},
			
			render:function () {
				var self = this;
				$(self.el).empty();
				_.each(this.model.models, function (task) {
					new context.TasksItemView({model:task, el: $(self.el)}).render();
				}, this);
			}
		});
		
		context.TasksItemView = Backbone.View.extend({
			
			tagName:"li",
			
			initialize:function () {
				this.template = _.template(templateItem);
				this.model.bind("change", this.render, this);
				this.model.bind("destroy", this.close, this);
			},
			
			render:function () {
				$(this.el).append(this.template(this.model.toJSON()));
			}
			
		});
		
	});
	
})(app);