var app = app || {};

define([
        'jquery','underscore','backbone','utils','views/header','views/home','views/login','views/reassigntask',
        'views/userregistration','views/taskslist','views/newtask','views/taskdetail','views/contact',
        'models/task'
        ], function($,_,Backbone){
	
		var utils = app.utils || ( app.utils = {} );
	
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
				
				utils.renderView( 'HeaderView' );
			},
			
			home:function () {
				// Since the home view never changes, we instantiate it and render it only once
				utils.renderView( 'HomeView' );
			},
			
			contact:function () {
				utils.renderView( 'ContactView' );
			},
			
			login: function() {
				utils.renderView( 'LoginView' );
			},
			
			reassigntask: function(id) {
				var task = new app.Task({id:id});
				task.fetch({
					success:function (data) {
						// Note that we could also 'recycle' the same instance of EmployeeFullView
						// instead of creating new instances
						utils.renderView( 'ReassignTaskView', data );
					}
				});
				
			},
			
			taskslist: function() {
				utils.renderView( 'TasksListView');
			},
			userregistration: function() {
				utils.renderView( 'UserRegistrationView', new app.User());
			},
			taskdetail: function(id) {
		     	var task = new app.Task({id:id});
		     	task.fetch({
		             success:function (data) {
		                 // Note that we could also 'recycle' the same instance of EmployeeFullView
		                 // instead of creating new instances
		             	utils.renderView( 'TaskDetailView', data);
		             }
		         });
				         
			},
		    editTask: function(id) {
		    	var task = new app.Task({id:id});
		     	task.fetch({
		             success:function (data) {
		                 // Note that we could also 'recycle' the same instance of EmployeeFullView
		                 // instead of creating new instances
		             	utils.renderView( 'NewTaskView', data);
		             },
		             error:function (data) {
		            	 console.log( 'error', data );
		             }
		         });
			         
			},
			newtask: function(){
				utils.renderView( 'NewTaskView', new app.Task());
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
		                utils.renderView( 'LoginView', data);
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