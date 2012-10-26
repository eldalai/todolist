(function(context) {
	'use strict';
	
	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'text!../../tpl/contact.html'
	        ], function($,_,Backbone, template){
		
		context.ContactView = Backbone.View.extend({
			
			el: $('.contact'),
			
			initialize:function () {
				console.log('Initializing Contact View');
				this.template = _.template( template );
			},
			
			render:function (eventName) {
				$(this.el).html( this.template() );
			},
			
		});
		
	});
	
})(app);