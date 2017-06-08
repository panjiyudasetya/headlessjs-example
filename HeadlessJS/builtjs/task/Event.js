class Event {
    constructor(name, location) {
        this.name = name;
        this.location = location;
        this.timestamp = new Date().toLocaleString();
    }
    toString() {
        return JSON.stringify(this);
    }
}
export default Event;
//# sourceMappingURL=Event.js.map