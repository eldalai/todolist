//Modernizr.load({
//  test: Modernizr.localstorage,
//  nope: '/polyfills/localstorage-polyfill.js'
//});

// Require.js allows us to configure shortcut alias
// There usage will become more apparent further along in the tutorial.
require.config({
		baseUrl: "js/",
		paths: {
			'jquery': '../lib/jquery-1.7.1.min', 'underscore': '../lib/underscore-min', 
			'backbone': '../lib/backbone-min','bootstrap':'../lib/bootstrap.min'//,
			//'localstoragepollyfill':'localstoragepollyfill'
		},
		shim: {
			"underscore": {exports: '_'},"jquery": {exports: '$'},
			"backbone": {deps: ["underscore", "jquery"],exports: 'Backbone'},
			"bootstrap": {deps: ["jquery"]}	
		}
	});

require([

  // Load our app module and pass it to our definition function
  'app',
], function(App){
	// The "app" dependency is passed in as "App"
	App.initialize();
});