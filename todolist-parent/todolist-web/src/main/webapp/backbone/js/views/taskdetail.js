window.TaskDetailView = Backbone.View.extend({

    initialize:function () {
        this.template = _.template(tpl.get('taskdetail'));
    },

    
    render:function () {
    	$(this.el).html(this.template(this.model.toJSON()));
        return this;
    }


});