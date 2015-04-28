var viewModel = {
    messages: new ko.observableArray(),
    eventDispatcher: new EventDispatcher(),

    registerEventHandlers: function () {
        var that = this;
        this.eventDispatcher.register(
            "MESSAGE",
            function (event) {
                var jsonData = JSON.parse(event.data);
                that.messages.push(jsonData.eventData.message.toString());
            }
        );
    }
}

ko.applyBindings(viewModel, $('html')[0]);
viewModel.registerEventHandlers();