(function(context) {
	'use strict';

	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'text!../../tpl/login.html'
	    ], function($,_,Backbone,template){
		
		context.LoginView = Backbone.View.extend({
			
			initialize:function () {
				console.log('Initializing Login View');
				this.template = _.template(template);
			},
			
			events: {
				"click #loginButton": "authenticate",
				"click #createAcount": "createAcount"	
			},
			
			render:function () {
				 $(this.el).html(this.template());
		    	 // si esta en modo confirmation
		    	 	//si ok:
		    	 		//$('.alert-success)
		    	 	//sino
		    	 		//$('.alert-error').
		    	 return this;
			},
			
			authenticate:function (event) {
				event.preventDefault(); // Don't let this button submit the form
				$('.alert-error').hide(); // Hide any errors on a new submit
				
				$.ajax({
					url:'../rest/session',
					type:'POST',
					dataType:"json",
					data: { email: $('#inputEmail').val(), password: $('#inputPassword').val() },
					success:function (data) {
						console.log(["Login request details: ", data]);
						
						if(data.error) {  // If there is an error, show the error messages
							$('.alert-error').text(data.error.text).show();
						}
						else { // If not, send them back to the home page
							window.location.replace('#taskslist'); // todo: tasklist
						}
					},
					error: function( data ) {
		            	 $('.alert-error').text(data.statusText + ' - ' + data.responseText ).show();
		            }
				});
			},
			
			createAcount:function(event){
				event.preventDefault(); // Don't let this button submit the form
				$('.alert-error').hide(); // Hide any errors on a new submit
				var url = 'tpl/userregistration.html';
				$.ajax({
					
					url:url,
					type:'POST',
					
					success:function () {
						window.location.replace('#userregistration');
						
					}
				});
			}
			
			
		});
	});

})(app);