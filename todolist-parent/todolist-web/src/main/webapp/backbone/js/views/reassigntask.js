window.ReassignTaskView = Backbone.View.extend({

    initialize:function () {
		this.template = _.template(tpl.get('reassigntask'));
    },

    render:function () {
    	$(this.el).html(this.template(this.model.toJSON()));
    	$('#reassigntask-content', this.el).html(new ReassignTaskListView().render().el);
    	return this;
    }
});
window.ReassignTaskListView = Backbone.View.extend({
    tagName:'ul',

    className:'nav nav-list',

    initialize:function () {
        //var self = this;
        this.model = new UserList();
        this.model.bind("all", this.render, this);
        this.model.fetch();
        //this.model.bind("add", function (user) {
        //    $(self.el).append(new ReassignTaskItemView({model:user}).render().el);
        //});
    },

    render:function () {
    	
        $(this.el).empty();
        _.each(this.model.models, function (user) {
            $(this.el).append(new ReassignTaskItemView({model:user}).render().el);
        }, this);
        return this;
    }
});

window.ReassignTaskItemView = Backbone.View.extend({

    tagName:"li",

    initialize:function () {
		this.template = _.template(tpl.get('reassigntaskitem'));
        this.model.bind("change", this.render, this);
        this.model.bind("destroy", this.close, this);
    },

    render:function () {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    }

});