import { NativeModules } from 'react-native';
const HEADLESS_JS_BRIDGE = NativeModules.HeadlessJsModule;
class HeadlessJsImpl {
    constructor() {
        this.headlessJsBridge = HEADLESS_JS_BRIDGE;
    }
    logEvent(descriptions) {
        return this.headlessJsBridge.logEvent(descriptions);
    }
}
let headlessJsObj = null;
export default {
    reqSharedInstance: () => {
        if (headlessJsObj == null) {
            headlessJsObj = new HeadlessJsImpl();
            return Promise.resolve(headlessJsObj);
        }
        return Promise.resolve(headlessJsObj);
    }
};
//# sourceMappingURL=HeadlessJSBridge.js.map