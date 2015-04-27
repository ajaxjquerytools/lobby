var viewModel = {
    messages: new ko.observableArray(),
    eventDispatcher: new EventDispatcher(),

    registerEventHandlers: function () {
        var that = this;
        this.eventDispatcher.register(
            "MESSAGE",
            function (event) {
                console.log("MESSAGE" + event.data);
                that.messages.push(event.data);
            }
        );
    }
}

ko.applyBindings(viewModel, $('html')[0]);
viewModel.registerEventHandlers();