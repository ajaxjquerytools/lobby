function LoginCreds() {
    var self = this;
    self.username = ko.observable().extend({required: true});
}

function User() {
    var self = this;
    self.username = ko.observable();
}

function Invites() {

}

function ServerRequest() {
    var self = this;
    self.destination;
    self.body;
}

var viewModel = {
    messages: new ko.observableArray(),
    eventDispatcher: new EventDispatcher(),

    tplName: ko.observable("login_view"),
    loginCreds: new LoginCreds(),

    currentUser: new User(),
    onlineUsers: ko.observableArray(),

    //=======CLICK BINDINGS======

    onLoginClick: function () {
        var that = this;
        var sr = new ServerRequest();
        if (this.loginCreds.username.isValid()) {
            sr.destination = "LOGIN";
            sr.body = that.loginCreds.username();
            ws.send(JSON.stringify(sr));
        }
    },

    onInviteClick: function () {
        ws.send("{\"message\": \"INVITE USER TO GAME\" }");
    },
    //=======NAVIGAION BINDINGS======
    toInvites: function () {

    },

    toGame: function () {

    },

    //=======WebSockets events========
    onLogged: function (userData) {
        var that = this;
        that.tplName("game_view");
        var user = new User();
        user.username(userData);
        that.currentUser(user);

        that.getUsersOnline();
        that.getAllActiveInvites();

    },

    onInviteReceived: function (inviteData) {

    },
    onUserConnected: function (userData) {
        console.log("USER CONNECTED");
        var that = this;
        if(that.currentUser.username() != userData){
            var opsUser = new User();
            opsUser.username(userData);
            that.onlineUsers.unshift(opsUser);
        }
    },

    onUserDisconnected: function (userData) {

    },
    onNewGame: function (gameData) {

    },
    onOnlineUsers: function (onlineUsers) {
        var that = this;
        that.onlineUsers([]);
        _.each(onlineUsers, function (user) {
            var obsUser = new User();
            obsUser.username(user.username);
            that.onlineUsers.push(obsUser);
        })
    },
    onActiveInvites: function(activeInvites){

    },

    onError: function (error) {
        alert(error);
    },

    //=======SERVER REQUESTS
    getUsersOnline: function () {
        var sr = new ServerRequest();
        sr.destination = "GET_USERS";
        ws.send(JSON.stringify(sr));
    },

    getAllActiveInvites: function(){
        var sr = new ServerRequest();
        sr.destination = "GET_INVITES";
        ws.send(JSON.stringify(sr));
    },


    registerEventHandlers: function () {
        var that = this;
        //this.eventDispatcher.register(
        //    "MESSAGE",
        //    function (event) {
        //        var jsonData = JSON.parse(event.data);
        //        jsonData.time = new Date();
        //        //put to the front; so new messages will be appear in the top of list
        //        that.messages.unshift(jsonData);
        //    }
        //);
        this.eventDispatcher.register(
            "ERROR",
            function (eventData) {
                that.onError(eventData);
            }
        );

        this.eventDispatcher.register(
            "LOGGED_IN",
            function (data) {
                that.onLogged(data);
            }
        );
        this.eventDispatcher.register(
            "INVITE",
            function (data) {
                that.onInviteReceived(data);
            }
        );
        this.eventDispatcher.register(
            "ON_USER_CONNECTED",
            function (data) {
                that.onUserConnected(data);
            }
        );
        this.eventDispatcher.register(
            "ON_USER_DISCONNECTED",
            function (data) {
                that.onUserDisconnected(data);
            }
        );
        this.eventDispatcher.register(
            "ON_NEW_GAME",
            function (data) {
                that.onNewGame(data);
            }
        );
        this.eventDispatcher.register(
            "ON_ONLINE_USERS",
            function (data) {
                that.onOnlineUsers(data);
            }
        );

        this.eventDispatcher.register(
            "ON_ACTIVE_INVITES",
            function (data) {
                that.onActiveInvites(data);
            }
        );
    }
}

ko.applyBindings(viewModel, $('html')[0]);
viewModel.registerEventHandlers();