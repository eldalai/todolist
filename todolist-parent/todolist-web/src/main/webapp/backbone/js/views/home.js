(function(context) {
	'use strict';
	
	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'utils'
	        ], function($,_,Backbone){
		
		context.HomeView = Backbone.View.extend({
			
			initialize:function () {
				console.log('Initializing Home View');
				this.template = _.template(context.tpl.get('home'));
			},
			
			events:{
				"click #showMeBtn":"showMeBtnClick"
			},
			
			render:function (eventName) {
				$(this.el).html(this.template());
				return this;
			},
			
			showMeBtnClick:function () {
				context.headerView.search();
			}
			
		});
		
	});
	
})(app);