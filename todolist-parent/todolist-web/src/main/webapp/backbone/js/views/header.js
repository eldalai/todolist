(function(context) {
	'use strict';
	
	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'text!../../tpl/header.html'
	        ], function($,_,Backbone,template){
		
		context.HeaderView = Backbone.View.extend({
			el: $('.header'),
			
			initialize:function () {
				this.template = _.template(template);
				
				// Close the search dropdown on click anywhere in the UI
				$('body').click(function () {
					$('.dropdown').removeClass("open");
				});
				
				//this.searchResults = new TaskList();
				//this.searchresultsView = new EmployeeListView({model:this.searchResults, className:'dropdown-menu'});
			},
			
			render:function () {
				$(this.el).html(this.template());
				//$('.navbar-search', this.el).append(this.searchresultsView.render().el);
			},
			
			events:{
				"keyup .search-query":"search"
			},
			
			search:function (event) {
//        var key = event.target.value;
				var key = $('#searchText').val();
				console.log('search ' + key);
				this.searchResults.findByName(key);
				setTimeout(function () {
					$('#searchForm').addClass('open');
				});
			}
			
		});
	});
})(app);
