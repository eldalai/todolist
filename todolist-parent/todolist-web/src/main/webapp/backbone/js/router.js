var app = app || {};

define([
        'jquery','underscore','backbone','views/header','views/home','views/login','views/reassigntask',
        'views/userregistration','views/taskslist','views/newtask','views/taskdetail','views/contact',
        'models/task'
        ], function($,_,Backbone){
	
		var instances = app.instances || ( app.instances = {} );
	
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
				this.headerView.render();
				
				// Close the search dropdown on click anywhere in the UI
				$('body').click(function () {
					$('.dropdown').removeClass("open");
				}); 
			},
			
			home:function () {
				// Since the home view never changes, we instantiate it and render it only once
				if ( !_.isObject( instances.homeView ) ) {
					instances.homeView = new app.HomeView();
				}
				instances.homeView.render();
			},
			
			contact:function () {
				if ( !_.isObject( instances.contactView ) ) {
					instances.contactView = new app.ContactView();
				}
				instances.contactView.render();
			},
			
			login: function() {
				
				if ( !_.isObject( instances.loginView ) ) {
					instances.loginView = new app.LoginView();
				}
				instances.loginView.render();
			},
			
			reassigntask: function(id) {
				var task = new app.Task({id:id});
				task.fetch({
					success:function (data) {
						// Note that we could also 'recycle' the same instance of EmployeeFullView
						// instead of creating new instances
						if ( !_.isObject( instances.reassignTaskView ) ) {
							instances.reassignTaskView = new app.ReassignTaskView();
						}
						instances.reassignTaskView.model = data;
						instances.reassignTaskView.render();
					}
				});
				
			},
			
			taskslist: function() {
				if ( !_.isObject( instances.tasksListView ) ) {
					instances.tasksListView = new app.TasksListView();
				}
				instances.tasksListView.render();
			},
			userregistration: function() {
				if ( !_.isObject( instances.userRegistrationView ) ) {
					instances.userRegistrationView = new app.UserRegistrationView();
				}
				instances.userRegistrationView.render();
			},
			taskdetail: function(id) {
		     	var task = new app.Task({id:id});
		     	task.fetch({
		             success:function (data) {
		                 // Note that we could also 'recycle' the same instance of EmployeeFullView
		                 // instead of creating new instances
		             	if ( !_.isObject( instances.taskDetailView ) ) {
							instances.taskDetailView = new app.TaskDetailView();
						}
		             	instances.taskDetailView.model = data;
						instances.taskDetailView.render();
		             }
		         });
				         
			},
		    editTask: function(id) {
		    	var task = new app.Task({id:id});
		     	task.fetch({
		             success:function (data) {
		                 // Note that we could also 'recycle' the same instance of EmployeeFullView
		                 // instead of creating new instances
		             	if ( !_.isObject( instances.newTaskView ) ) {
							instances.newTaskView = new app.NewTaskView();
						}
		             	instances.newTaskView.model = data;
						instances.newTaskView.render();
		             },
		             error:function (data) {
		            	 console.log( 'error', data );
		             }
		         });
			         
			},
			newtask: function(){
				if ( !_.isObject( instances.newTaskView ) ) {
					instances.newTaskView = new app.NewTaskView();
				}
				instances.newTaskView.model = new app.Task();
				instances.newTaskView.render();
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
		           	 	new app.LoginView({}).render();
		           	 	$('#errorLogin').hide();
		           	 	$('#confirmation').text("Your email has been verified.").show();
		            },
		            error: function(jqXHR, textStatus, errorThrown) {
		            	$('#errorLogin').text("Error").show();
		            	$('#confirmation').text("Your email has been verified.").hide();
		            }
		        });
		    }
		});
		
		var initialize = function(){
			//app = new AppRouter();
			app.application = new app.AppRouter();
			Backbone.history.start();
		};
		
		return {
			initialize: initialize
		};
		
});