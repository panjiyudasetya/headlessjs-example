import HeadlessJsBridge from '../HeadlessJsBridge';
import Event from '../task/Event';
import {DeviceEventEmitter, EventEmitter} from 'react-native';

// tslint:disable-next-line:variable-name
const HeadlessTaskService = async(event) => {
    console.log('JS Background > called.');
    // stuff
    HeadlessJsBridge.reqSharedInstance()
    .then((bridge) => {
        const DETECTED_EVENT: string = new Event(
                'Headless Event detected',
                'On Background JS').toString();
        console.log('JS Background > event detected > ' + DETECTED_EVENT);
        bridge.logEvent(DETECTED_EVENT);
    }).catch((error) => {
        console.log(error);
    });
};

export default HeadlessTaskService;
