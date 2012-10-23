(function(context) {
	'use strict';
	
	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'utils',
	        'collections/users'
	        ], function($,_,Backbone){
		
		context.ReassignTaskView = Backbone.View.extend({
			
			initialize:function () {
				this.template = _.template(context.tpl.get('reassigntask'));
			},
			
			render:function () {
				$(this.el).html(this.template(this.model.toJSON()));
				$('#reassigntask-content', this.el).html(new context.ReassignTaskListView().render().el);
				return this;
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
				
				$(this.el).empty();
				_.each(this.model.models, function (user) {
					$(this.el).append(new context.ReassignTaskItemView({model:user}).render().el);
				}, this);
				return this;
			}
		});
		
		context.ReassignTaskItemView = Backbone.View.extend({
			
			tagName:"li",
			
			initialize:function () {
				this.template = _.template(context.tpl.get('reassigntaskitem'));
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