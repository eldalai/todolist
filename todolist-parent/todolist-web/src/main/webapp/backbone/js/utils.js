(function(context) {
	'use strict';

	define([
	        'jquery',
	        'underscore',
	        'backbone'
	    ], function($,_,Backbone){
		
		var instances = context.instances || ( context.instances = {} );
		
		context.utils = {};
		
		context.utils.renderView = function(clazz, model){
			model = model || null; //assign default parameter value
			var i = instances[clazz];
			if ( !_.isObject( i ) ) {
				i = new context[clazz]();
				instances[clazz] = i;
			}
            i.model = model;
			i.render();
		};
	});
	
}(app));