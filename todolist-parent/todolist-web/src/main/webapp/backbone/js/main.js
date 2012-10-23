
 // Tell jQuery to watch for any 401 or 403 errors and handle them appropriately
$.ajaxSetup({
    statusCode: {
        401: function(){
            // Redirec the to the login page.
            window.location.replace('#login');
         
        },
        403: function() {
            // 403 -- Access denied
            window.location.replace('#denied');
        }
    }
});


var AppRouter = Backbone.Router.extend({

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
        this.headerView = new HeaderView();
        $('.header').html(this.headerView.render().el);

        // Close the search dropdown on click anywhere in the UI
        $('body').click(function () {
            $('.dropdown').removeClass("open");
        }); 
    },

    home:function () {
        // Since the home view never changes, we instantiate it and render it only once
        if (!this.homeView) {
            this.homeView = new HomeView();
            this.homeView.render();
        }
        $('#content').html(this.homeView.el);
    },

    contact:function () {
        if (!this.contactView) {
            this.contactView = new ContactView();
            this.contactView.render();
        }
        $('#content').html(this.contactView.el);
    },

    login: function() {
        $('#content').html(new LoginView().render().el);
    },
    
    reassigntask: function(id) {
    	var task = new Task({id:id});
    	task.fetch({
            success:function (data) {
                // Note that we could also 'recycle' the same instance of EmployeeFullView
                // instead of creating new instances
	    		$('#content').html(new ReassignTaskView({model:data}).render().el);
            }
        });
    	
    },
    
    taskslist: function() {
        $('#content').html(new TasksListView().render().el);
    },
    userregistration: function() {
        $('#content').html(new UserRegistrationView().render().el);
    },
    taskdetail: function(id) {
    	var task = new Task({id:id});
    	task.fetch({
            success:function (data) {
                // Note that we could also 'recycle' the same instance of EmployeeFullView
                // instead of creating new instances
            	$('#content').html(new TaskDetailView({model:data}).render().el);
            }
        });
        
    },
    editTask: function(id) {
    	var task = new Task({id:id});
    	task.fetch({
            success:function (data) {
                // Note that we could also 'recycle' the same instance of EmployeeFullView
                // instead of creating new instances
            	$('#content').html(new NewTaskView({model:data}).render().el);
            }
        });
        
    },
    newtask: function(){
    	   	
    	$('#content').html(new NewTaskView().render().el);
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


tpl.loadTemplates(['home','header', 'login', 'reassigntask','reassigntaskitem',"taskslist",'taskslistitem',"userregistration",'taskdetail','newtask'],
    function () {
        app = new AppRouter();
        Backbone.history.start();
    });