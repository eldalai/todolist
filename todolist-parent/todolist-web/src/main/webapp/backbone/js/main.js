// Require.js allows us to configure shortcut alias
// There usage will become more apparent further along in the tutorial.
require.config({
		baseUrl: "js/",
		paths: {
			'jquery': '../lib/jquery-1.7.1.min', 'underscore': '../lib/underscore-min', 'backbone': '../lib/backbone-min',
			'bootstrap-transition':'../lib/bootstrap-transition', 'bootstrap-alert':'../lib/bootstrap-alert',
			'bootstrap-modal':'../lib/bootstrap-modal','bootstrap-dropdown':'../lib/bootstrap-dropdown',
			'bootstrap-tab':'../lib/bootstrap-tab','bootstrap-scrollspy':'../lib/bootstrap-scrollspy',
			'bootstrap-tooltip':'../lib/bootstrap-tooltip','bootstrap-button':'../lib/bootstrap-button',
			'bootstrap-popover':'../lib/bootstrap-popover','bootstrap-collapse':'../lib/bootstrap-collapse',
			'bootstrap-carousel':'../lib/bootstrap-carousel','bootstrap-typeahead':'../lib/bootstrap-typeahead'
		},
		shim: {
			"underscore": {exports: '_'},"jquery": {exports: '$'},"backbone": {deps: ["underscore", "jquery"],exports: 'Backbone'},
			"bootstrap-transition": {deps: ["jquery"]},"bootstrap-alert": {deps: ["jquery"]},
			"bootstrap-modal": {deps: ["jquery"]},"bootstrap-dropdown": {deps: ["jquery"]},
			"bootstrap-scrollspy": {deps: ["jquery"]},"bootstrap-tab": {deps: ["jquery"]},
			"bootstrap-tooltip": {deps: ["jquery"]},"bootstrap-popover": {deps: ["jquery","bootstrap-tooltip"]},
			"bootstrap-button": {deps: ["jquery"]},"bootstrap-collapse": {deps: ["jquery"]},
			"bootstrap-carousel": {deps: ["jquery"]},"bootstrap-typeahead": {deps: ["jquery"]}
		}
	});

require([

  // Load our app module and pass it to our definition function
  'app',
], function(App){
	// The "app" dependency is passed in as "App"
	App.initialize();
});