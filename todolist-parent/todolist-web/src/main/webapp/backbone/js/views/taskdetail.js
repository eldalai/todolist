window.TaskDetailView = Backbone.View.extend({

    initialize:function () {
        console.log('Initializing Login View');
        this.template = _.template(tpl.get('taskdetail'));
    },

    
    render:function () {
    	 $(this.el).html(this.template());
        return this;
    }


});