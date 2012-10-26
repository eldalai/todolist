var app = app || {};

define([
        'jquery','underscore','backbone','views/header','views/home','views/login','views/reassigntask',
        'views/userregistration','views/taskslist','views/newtask','views/taskdetail','models/task'
        ], function($,_,Backbone){
	
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
			           	 	$('#content').html(new app.LoginView({}).render().el);
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