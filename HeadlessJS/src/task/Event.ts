class Event {
    name: string;
    location: string;
    timestamp: string;

    constructor(name: string, location: string) {
        this.name = name;
        this.location = location;
        this.timestamp = new Date().toLocaleString();
    }

    toString() {
        return JSON.stringify(this);
    }
}

export default Event;
