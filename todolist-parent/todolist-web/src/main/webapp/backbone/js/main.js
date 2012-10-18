
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
        "home":"home",
        "taskdetail":"taskdetail",
        "contact":"contact",
        "employees/:id":"employeeDetails",
        "" : "login",
        "reassigntask": "reassigntask",
        "taskslist":"taskslist",
        "userregistration":"userregistration"
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

    employeeDetails:function (id) {
        var employee = new Employee({id:id});
        employee.fetch({
            success:function (data) {
                // Note that we could also 'recycle' the same instance of EmployeeFullView
                // instead of creating new instances
                $('#content').html(new EmployeeFullView({model:data}).render().el);
            }
        });
    },
    
    login: function() {
        $('#content').html(new LoginView().render().el);
    },
    
    reassigntask: function() {
        $('#content').html(new ReassignTaskView().render().el);
    },
    
    taskslist: function() {
        $('#content').html(new TasksListView().render().el);
    },
    userregistration: function() {
        $('#content').html(new UserRegistrationView().render().el);
    },
    taskdetail: function() {
    	console.log("task function");
        $('#content').html(new TaskDetailView().render().el);
    }

});


tpl.loadTemplates(['home','header', 'login', 'reassigntask','reassigntaskitem',"taskslist","userregistration",'taskdetail'],
    function () {
        app = new AppRouter();
        Backbone.history.start();
    });