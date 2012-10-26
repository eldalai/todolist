(function(context) {
	'use strict';
	
	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'text!../../tpl/home.html'
	        ], function($,_,Backbone, template){
		
		context.HomeView = Backbone.View.extend({
			
			initialize:function () {
				console.log('Initializing Home View');
				this.template = _.template( template );
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