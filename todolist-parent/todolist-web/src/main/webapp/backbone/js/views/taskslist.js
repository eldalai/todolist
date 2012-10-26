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
			
			initialize:function () {
				this.template = _.template(template);
			},
			events:{
				"click #createtask":"createtask"
			},
			
			render:function () {
				$(this.el).html(this.template());
				$('#tasklist-content', this.el).html(new context.TasksListItemView().render().el);
				return this;
			},
			createtask:function(){
				event.preventDefault(); // Don't let this button submit the form
				var url = 'tpl/newtask.html';
				$.ajax({
					
					url:url,
					type:'POST',
					
					success:function () {
						window.location.replace('#newtask');
						
					}
				});
			}
		});
		
		context.TasksListItemView = Backbone.View.extend({
			tagName:'ul',
			
			className:'nav nav-list',
			
			initialize:function () {
				console.log('Initializing Tasks List View');
				this.model = new context.TaskList();
				this.model.bind("all", this.render, this);
				this.model.fetch();
				
			},
			
			render:function () {
				$(this.el).empty();
				_.each(this.model.models, function (task) {
					$(this.el).append(new context.TasksItemView({model:task}).render().el);
				}, this);
				return this;
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
				$(this.el).html(this.template(this.model.toJSON()));
				return this;
			}
			
		});
		
	});
	
})(app);