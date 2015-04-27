function EventDispatcher(){
    this.eventTypesToHandlers = {};
    this.register = function(eventType, dispatcher){
        this.eventTypesToHandlers[eventType] = dispatcher;
    }
    this.dispatch = function(event){
        console.log("dispatching:"+event.data)
        var eventType = event.type;
        if(this.eventTypesToHandlers[eventType]){
            this.eventTypesToHandlers[eventType](event);
        } else {
            //default handler
            //console.log("Unhandled event:\n" + JSON.stringify(event));
            console.log("HANDLING....");
        }
    }
    this.clear = function(){
        this.eventTypesToHandlers = {};
    }
}