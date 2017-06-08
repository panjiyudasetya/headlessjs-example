declare interface HeadlessJsBridge {
    logEvent(descriptions: string): Promise<any>;
    getEvents(): Promise<any>;
    startService();
    stopService();
}