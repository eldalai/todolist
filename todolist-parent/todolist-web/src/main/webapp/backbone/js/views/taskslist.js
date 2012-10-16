window.TasksListView = Backbone.View.extend({
	tagName:'ul',
	
	className:'nav nav-list',
    
	initialize:function () {
        console.log('Initializing Tasks List View');
        this.model = new TaskList();
        this.model.bind("all", this.render, this);
        this.model.fetch();
        
    },


    render:function () {
    	$(this.el).empty();
        _.each(this.model.models, function (user) {
            $(this.el).append(new TasksItemView({model:task}).render().el);
        }, this);
        return this;
    }
});

window.TasksItemView = Backbone.View.extend({
	

    tagName:"li",

    initialize:function () {
		this.template = _.template(tpl.get('taskslist'));
        this.model.bind("change", this.render, this);
        this.model.bind("destroy", this.close, this);
    },

    render:function () {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    }
    
});

