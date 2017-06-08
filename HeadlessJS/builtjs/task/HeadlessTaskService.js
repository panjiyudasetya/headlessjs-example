var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import HeadlessJsBridge from '../HeadlessJsBridge';
import Event from '../task/Event';
// tslint:disable-next-line:variable-name
const HeadlessTaskService = (event) => __awaiter(this, void 0, void 0, function* () {
    console.log('JS Background > called.');
    // stuff
    HeadlessJsBridge.reqSharedInstance()
        .then((bridge) => {
        const DETECTED_EVENT = new Event('Headless Event detected', 'On Background JS').toString();
        console.log('JS Background > event detected > ' + DETECTED_EVENT);
        bridge.logEvent(DETECTED_EVENT);
    }).catch((error) => {
        console.log(error);
    });
});
export default HeadlessTaskService;
//# sourceMappingURL=HeadlessTaskService.js.map