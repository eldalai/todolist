(function(context) {
	'use strict';
	
	define([
	        'jquery',
	        'underscore',
	        'backbone',
	        'text!../../tpl/userregistration.html',
	        'models/user'
	        ], function($,_,Backbone,template){
		
		context.UserRegistrationView = Backbone.View.extend({
			el: $('#content'),
			className:'box',
			initialize:function () {
//				console.log('Initializing User registration View');
				this.template = _.template(template);
				this.model = new context.User();
			},
			
			events: {
				"click #newaccount": "newaccount",
				"click #cancel": "cancel"
			},
			
			render:function () {
				$(this.el).html(this.template());
			},
			
			cancel:function(event){
				
			},
			
			newaccount:function(event) {
				event.preventDefault(); // Don't let this button submit the form
				$('.alert-error').hide(); // Hide any errors on a new submit
				if( $('#inputEmail').val() == '' )
				{
					$('.alert-error').text('Email obligatorio').show();
					return;
				}	
				if( $('#inputPassword').val() == '' )
				{
					$('.alert-error').text('Password obligatorio').show();
					return;
				}	
				if( $('#inputPassword').val() != $('#inputPasswordConfirmation').val() )
				{
					$('.alert-error').text('Password confirmation error').show();
					return;
				}	
				var url = '../rest/users';       
				this.model.set({name: $('#inputEmail').val(), password : $('#inputPassword').val()});
				$.ajax({
					url:url,
					type:'POST',
					contentType: 'application/json',
					dataType:"json",
					data: JSON.stringify(this.model),
					success:function (data) {
//						console.log(["Users request details: ", data]);
						
						if(data.error) {  // If there is an error, show the error messages
							$('.alert-error').text(data.error.text).show();
						}
						else { // If not, send them back to the home page
							$('.form-horizontal').hide();
							$('#confirmation').show();
						}
					}
				});
			}
			
		});
		
	});
	
})(app);