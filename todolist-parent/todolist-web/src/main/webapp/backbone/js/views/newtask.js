window.NewTaskView = Backbone.View.extend({

    initialize:function () {
    	if(this.model==null){
    		this.model = new Task();	
    	}
    	
        console.log('Initializing New Task View');
        this.template = _.template(tpl.get('newtask'));
        this.bind('change', this.save);
    },

    events: {
        "click #createnewtask": "createnewtask",
        "click #cancel": "cancel"
    },

    render:function () {
    	$(this.el).html(this.template(this.model.toJSON()));	
    	
        return this;
    },
	
	cancel:function(event){
		window.location.replace('#taskslist');
	},
    
	createnewtask:function(event) {
        event.preventDefault(); // Don't let this button submit the form
        $('.alert-error').hide(); // Hide any errors on a new submit
        //var url = '../rest/tasks';   
        if($('#inputTitle').val()!=''){
        	if(this.model.id==null){
        		var task = new Task({title: $('#inputTitle').val(), taskStatus : parseInt($("#status").val()), taskType : parseInt($("#type").val())});
        	}else{
        		var task = new Task({title: $('#inputTitle').val(), taskStatus : parseInt($("#status").val()), taskType : parseInt($("#type").val()),id : this.model.id});
        		//var task = this.model;
        	}
        	

        
        		task.save({
                    success:function (data) {
                    	console.log(["New Task: ", data]);
                    }	
                });	
        	
            window.location.replace('#taskslist');
        }else{
        	$('.alert-error').show();
        }
        

    }
    
});