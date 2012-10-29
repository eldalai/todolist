(function(context) {
	
	/*
	 
	  Removes features from the language, making the programs simpler and less error-prone.
	  The strict mode is triggered by an ordinary string, which older implementations of the language simply ignore.
	  This means that the usage of strict mode is backward compatible, because it won’t raise errors in older browsers that don’t understand it.
	  
	 */
	
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