window.NewTaskView = Backbone.View.extend({

    initialize:function () {
        console.log('Initializing New Task View');
        this.template = _.template(tpl.get('newtask'));
        
    },

    events: {
        "click #createnewtask": "createnewtask",
        "click #cancel": "cancel"
    },

    render:function () {
    	 $(this.el).html(this.template());
        return this;
    },
	
	cancel:function(event){
		window.location.replace('#taskslist');
	},
    
	createnewtask:function(event) {
        event.preventDefault(); // Don't let this button submit the form
        $('.alert-error').hide(); // Hide any errors on a new submit
        //var url = '../rest/tasks';   
        var task = new Task({title: $('#inputTitle').val(), taskStatus : parseInt($("#status").val()), taskType : parseInt($("#type").val())});
        //this.model.set();
        task.save({
            success:function (data) {
            	console.log(["New Task: ", data]);
            }	
        });
//        $.ajax({
//            url:url,
//            type:'POST',
//            contentType: 'application/json',
//            dataType:"json",
//            data: JSON.stringify(this.model),
//            success:function (data) {
//                console.log(["New Task: ", data]);
//               
//                if(data.error) {  // If there is an error, show the error messages
//                    $('.alert-error').text(data.error.text).show();
//                }
//                else { // If not, send them back to the home page
//                	$('.form-horizontal').hide();
//                    $('#confirmation').show();
//                }
//            }
//        });
    }
    
});