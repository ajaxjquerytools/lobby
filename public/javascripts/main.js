var viewModel = {
    messages: new ko.observableArray(),
    eventDispatcher: new EventDispatcher(),

    registerEventHandlers: function () {
        var that = this;
        this.eventDispatcher.register(
            "MESSAGE",
            function (event) {
                var jsonData = JSON.parse(event.data);
                jsonData.time = new Date();
                //put to the front; so new messages will be appear in the top of list
                that.messages.unshift(jsonData);
            }
        );
    }
}

ko.applyBindings(viewModel, $('html')[0]);
viewModel.registerEventHandlers();