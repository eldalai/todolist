(function(context) {
	'use strict';
	
	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'text!../../tpl/home.html'
	        ], function($,_,Backbone, template){
		
		context.HomeView = Backbone.View.extend({
			
			el: $('#content'),
			
			initialize:function () {
				console.log('Initializing Home View');
				this.template = _.template( template );
			},
			
			events:{
				"click #showMeBtn":"showMeBtnClick"
			},
			
			render:function (eventName) {
				$(this.el).html( this.template() );
			},
			
			showMeBtnClick:function () {
				context.headerView.search();
			}
			
		});
		
	});
	
})(app);