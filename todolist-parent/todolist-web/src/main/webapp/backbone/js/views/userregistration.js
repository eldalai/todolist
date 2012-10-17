window.UserRegistrationView = Backbone.View.extend({

    initialize:function () {
        console.log('Initializing User registration View');
        this.template = _.template(tpl.get('userregistration'));
    },

    events: {
        "click #newaccount": "newaccount",
        "click #cancel": "cancel"
        
    },

    render:function () {
    	 $(this.el).html(this.template());
        return this;
    },
	
	cancel:function(event){
        event.preventDefault(); // Don't let this button submit the form
        $('.alert-error').hide(); // Hide any errors on a new submit
        $.ajax({
        	
            url:url,
            type:'POST',

            success:function () {
                    window.location.replace('#login');

            }
        });
	},
    
    newaccount:function(event) {
//       event.preventDefault(); // Don't let this button submit the form
//       $('.alert-error').hide(); // Hide any errors on a new submit
//       var url = '../api/login';
//       console.log('Loggin in... ');
//       var formValues = {
//          email: $('#inputEmail').val(),
//          password: $('#inputPassword').val()
//       };
//
//       $.ajax({
//          url:url,
//          type:'POST',
//          dataType:"json",
//          data: formValues,
//          success:function (data) {
//              console.log(["Login request details: ", data]);
//             
//              if(data.error) {  // If there is an error, show the error messages
//                  $('.alert-error').text(data.error.text).show();
//              }
//              else { // If not, send them back to the home page
//                  window.location.replace('#');
//              }
//          }
//       });
     }
    
});