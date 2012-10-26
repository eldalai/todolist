(function(context) {
	'use strict';
	
	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'text!../../tpl/reassigntask.html',
	        'text!../../tpl/reassigntaskitem.html',
	        'collections/users'
	        ], function($,_,Backbone,template,templateItem){
		
		context.ReassignTaskView = Backbone.View.extend({
			
			el: $('#content'),
			
			initialize:function () {
				this.template = _.template(template);
			},
			
			render:function () {
				$(this.el).html(this.template(this.model.toJSON()));
				new context.ReassignTaskListView( { el : $('#reassigntask-content', this.el) } ).render();
			}
		});
		
		context.ReassignTaskListView = Backbone.View.extend({
		
			tagName:'ul',
			
			className:'nav nav-list',
			
			initialize:function () {
				//var self = this;
				this.model = new context.UserList();
				this.model.bind("all", this.render, this);
				this.model.fetch();
				//this.model.bind("add", function (user) {
				//    $(self.el).append(new ReassignTaskItemView({model:user}).render().el);
				//});
			},
			
			render:function () {
				var self = this; 
				$(self.el).empty();
				_.each(this.model.models, function (user) {
					new context.ReassignTaskItemView({model:user, el: $(self.el) }).render();
				}, this);
			}
		});
		
		context.ReassignTaskItemView = Backbone.View.extend({
			
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