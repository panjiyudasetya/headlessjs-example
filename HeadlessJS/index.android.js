/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import { AppRegistry } from 'react-native';
import App from './builtjs';
import HeadlessTaskService from './builtjs/task/HeadlessTaskService'

AppRegistry.registerComponent('HeadlessJS', () => App);
AppRegistry.registerHeadlessTask('HeadlessTaskService', () => HeadlessTaskService);