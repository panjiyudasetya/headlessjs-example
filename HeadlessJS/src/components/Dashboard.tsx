import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  View,
  Button,
  ListView
} from 'react-native';
import HeadlessJsBridge from '../HeadlessJsBridge';

class Dashboard extends Component<any, any> {
  constructor(props) {
    super(props);
    const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    this.state = {
      dataSource: ds.cloneWithRows([]),
    };
  }

  componentDidMount() {
    this.fetchData();
  }

  render() {
    return (
      <View style={styles.containerStyle}>
        <View style={styles.flexItem}>
            <Text style={styles.title}>
                Headless JS Playground
            </Text>
        </View>
        <View style={styles.horizontalStyle}>
            <View style={styles.flexItem}>
                <Button
                    onPress={this.startService}
                    title="Start Service"
                    color="#4CAF50"/>
            </View>
            <View style={styles.flexItem}>
                <Button
                    onPress={this.stopService}
                    title="Stop Service"
                    color="#F44336"/>
            </View>
        </View>
        <ListView
          style={styles.containerStyle}
          dataSource={this.state.dataSource}
          renderRow={(rowData) => <Text style={styles.rowItem}>{rowData}</Text>}
          renderSeparator={
            (sectionID: number, rowID: number, adjacentRowHighlighted: boolean) => <View
                key={`${sectionID}-${rowID}`}
                style={{
                  height: adjacentRowHighlighted ? 4 : 1,
                  backgroundColor: adjacentRowHighlighted ? '#3B5998' : '#CCCCCC',
                }}
              />
          }
          enableEmptySections={true}
        />
      </View>
    );
  }

  // tslint:disable-next-line:no-empty
  startService() {
      HeadlessJsBridge.reqSharedInstance().then((bridge) => {
          return bridge.startService();
      }).catch((error) => {
          console.log(error);
      });
  }

  // tslint:disable-next-line:no-empty
  stopService() {
      HeadlessJsBridge.reqSharedInstance().then((bridge) => {
          return bridge.stopService();
      }).catch((error) => {
          console.log(error);
      });
  }

  notifyDataSourceChange(contents: object[]) {
    this.setState({
      dataSource: this.state.dataSource.cloneWithRows(contents),
    });
  }

  fetchData() {
      HeadlessJsBridge.reqSharedInstance().then((bridge) => {
          return bridge.getEvents();
      }).then((data: string) => {
          console.log('JS Receiving events data : ' + data);
          const EVENT_HISTORY = JSON.parse(data);
          this.notifyDataSourceChange(EVENT_HISTORY);
      }).catch((error) => {
          console.log(error);
      });
  }
}

export default Dashboard;

const styles = StyleSheet.create({
  containerStyle: {
    paddingLeft: 5,
    paddingRight: 5,
    paddingTop: 10,
    flex: 1
  },
  horizontalStyle: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center'
  },
  flexItem: {
    margin: 5
  },
  title: {
    fontSize: 20,
    margin: 10,
    alignSelf: 'center',
  },
  rowItem: {
    fontSize: 14,
    padding: 10,
  },
});
