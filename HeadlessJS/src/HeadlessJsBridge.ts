import { NativeModules } from 'react-native';
const HEADLESS_JS_BRIDGE = NativeModules.HeadlessJsModule;

class HeadlessJsImpl {
    private headlessJsBridge: HeadlessJsBridge;

    constructor() {
        this.headlessJsBridge = HEADLESS_JS_BRIDGE;
    }

    logEvent(descriptions: string) {
        return this.headlessJsBridge.logEvent(descriptions);
    }

    getEvents() {
        return this.headlessJsBridge.getEvents();
    }

    startService() {
        return this.headlessJsBridge.startService();
    }

    stopService() {
        return this.headlessJsBridge.stopService();
    }
}

let headlessJsObj: HeadlessJsImpl = null;

export default {
    reqSharedInstance: (): Promise<HeadlessJsImpl> => {
        if (headlessJsObj == null) {
            headlessJsObj = new HeadlessJsImpl();
            return Promise.resolve(headlessJsObj);
        }
        return Promise.resolve(headlessJsObj);
    }

};
