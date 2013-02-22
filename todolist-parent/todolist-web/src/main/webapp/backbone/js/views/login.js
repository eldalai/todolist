(function(context) {
	'use strict';

	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'text!../../tpl/login.html'
	    ], function($,_,Backbone,template){
		
		context.LoginView = Backbone.View.extend({
			
			el: $('#content'),
			
			url:null ,
			
			initialize:function (params) {
				if( params != undefined )
					this.url = params.url;

				this.template = _.template(template);
			},
			
			events: {
				"click #loginButton": "authenticate",
				"click #createAcount": "createAcount"	
			},
			
			render:function () {
				var rememberValue = localStorage['remember'];
				var username = '';
				var password = '';
				if ( rememberValue == 'true' ) {
					rememberValue = 'checked';
					username = localStorage['username'];
					password = localStorage['password'];
				} else {
					rememberValue = '';
				}
				
				$(this.el).html(this.template( { username: username, password: password, remember: rememberValue } ));
		    	 // si esta en modo confirmation
		    	 	//si ok:
		    	 		//$('.alert-success)
		    	 	//sino
		    	 		//$('.alert-error').
			},
			
			authenticate:function (event) {
				self = this;
				event.preventDefault(); // Don't let this button submit the form
				$('.alert-error').hide(); // Hide any errors on a new submit
				var username = $('#inputEmail').val();
				var password = $('#inputPassword').val();
				var remember = $('#remember').is(':checked');
				$.ajax({
					url:'../login', //j_spring_security_check
					type:'POST',
					//dataType:"json",
					data: { j_username: username, j_password: password },
					success:function (data) {
//						console.log(["Login request details: ", data]);
						
						if(data.error) {  // If there is an error, show the error messages
							$('.alert-error').text(data.error.text).show();
							remember = false;
						}
						else { // If not, send them back to the home page
							if( self.url == null )
								window.location.replace('#taskslist'); // todo: tasklist
	        				else
	        					app.application.navigate(decodeURIComponent(self.url),{trigger: true});
						}
					},
					error: function( data ) {
		            	 $('.alert-error').text(data.statusText + ' - ' + data.responseText ).show();
							remember = false;
		            }
				});
				this.savePassword(username,password,remember);
			},
			
			savePassword:function (username,password,remember) {
				if( remember )
				{
					localStorage['username'] = username;
					localStorage['password'] = password;
					localStorage['remember'] = true;
				} else {
					localStorage['username'] = null;
					localStorage['password'] = null;
					localStorage['remember'] = null;
				}	
			},	
			
			createAcount:function(event){
				event.preventDefault();
				window.location.replace('#userregistration');
			}
			
			
		});
	});

})(app);