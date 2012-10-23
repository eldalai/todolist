(function(context) {
	'use strict';
	
	var app = app || {};
	
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
			"bootstrap-tooltip": {deps: ["jquery"]},"bootstrap-popover": {deps: ["jquery"]},
			"bootstrap-button": {deps: ["jquery"]},"bootstrap-collapse": {deps: ["jquery"]},
			"bootstrap-carousel": {deps: ["jquery"]},"bootstrap-typeahead": {deps: ["jquery"]}
		}
	});
	
	define([
	        'jquery','underscore','backbone','utils','views/header','views/home','views/login','views/reassigntask',
	        'views/userregistration','views/taskslist','views/newtask','views/taskdetail','models/task'
	        ], function($,_,Backbone){
		// Tell jQuery to watch for any 401 or 403 errors and handle them appropriately
		$.ajaxSetup({
			statusCode: {
				401: function(){
					// Redirect the to the login page.
					window.location.replace('#login');
					
				},
				403: function() {
					// 403 -- Access denied
					window.location.replace('#denied');
				}
			}
		});
		
		$(function(){
			
			app.AppRouter = Backbone.Router.extend({
				routes:{
					"" : "login",
					"contact":"contact",
					"userregistration":"userregistration",
					"taskslist":"taskslist",
					"taskdetail/:id":"taskdetail",
					"reassigntask/:id": "reassigntask",
					"employees/:id":"employeeDetails",
					"newtask":"newtask",
			        "token/:id/:tokenid":"confirm",
			        "editTask/:id":"editTask"
				},
				
				initialize:function () {
					this.headerView = new app.HeaderView();
					$('.header').html(this.headerView.render().el);
					
					// Close the search dropdown on click anywhere in the UI
					$('body').click(function () {
						$('.dropdown').removeClass("open");
					}); 
				},
				
				home:function () {
					// Since the home view never changes, we instantiate it and render it only once
					if (!this.homeView) {
						this.homeView = new app.HomeView();
						this.homeView.render();
					}
					$('#content').html(this.homeView.el);
				},
				
				contact:function () {
					if (!this.contactView) {
						this.contactView = new app.ContactView();
						this.contactView.render();
					}
					$('#content').html(this.contactView.el);
				},
				
				login: function() {
					$('#content').html(new app.LoginView().render().el);
				},
				
				reassigntask: function(id) {
					var task = new app.Task({id:id});
					task.fetch({
						success:function (data) {
							// Note that we could also 'recycle' the same instance of EmployeeFullView
							// instead of creating new instances
							$('#content').html(new app.ReassignTaskView({model:data}).render().el);
						}
					});
					
				},
				
				taskslist: function() {
					$('#content').html(new app.TasksListView().render().el);
				},
				userregistration: function() {
					$('#content').html(new app.UserRegistrationView().render().el);
				},
				taskdetail: function(id) {
			     	var task = new app.Task({id:id});
			     	task.fetch({
			             success:function (data) {
			                 // Note that we could also 'recycle' the same instance of EmployeeFullView
			                 // instead of creating new instances
			             	$('#content').html(new app.TaskDetailView({model:data}).render().el);
			             }
			         });
					         
				},
			    editTask: function(id) {
			    	var task = new app.Task({id:id});
			     	task.fetch({
			             success:function (data) {
			                 // Note that we could also 'recycle' the same instance of EmployeeFullView
			                 // instead of creating new instances
			             	$('#content').html(new app.NewTaskView({model:data}).render().el);
			             }
			         });
				         
				},
				newtask: function(){
					$('#content').html(new app.NewTaskView().render().el);
				},
				 confirm: function(id, tokenid){
			    	 var url = '../rest/session';
			         console.log('checking email confirmation... ');
			      	 $.ajax({
				            url:url,
				            type:'PUT',
				            dataType:"json",
				            data: { email: id, token: tokenid },
				            success:function (data) {
				                console.log(["Login request details: ", data]);
				           	 	$('#content').html(new LoginView({}).render().el);
				           	 	$('#errorLogin').hide();
				           	 	$('#confirmation').show();
				            },
				            error: function(jqXHR, textStatus, errorThrown) {
				            	$('#errorLogin').text("Error").show();
				            	$('#confirmation').text("Your email has been verified.").hide();
				            }
				        });
			    }
			});
			
			app.tpl.loadTemplates(['home','header', 'login', 'reassigntask','reassigntaskitem',"taskslist",'taskslistitem',"userregistration",'taskdetail','newtask'],
					function () {
				//app = new AppRouter();
				app.application = new app.AppRouter();
				Backbone.history.start();
			});
			
		});
		
	});
	
	context.app = app;
	
})(this);