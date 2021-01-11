import { notification } from 'antd';
import React, { Component } from "react";
import { Route, Switch, withRouter } from 'react-router-dom';
import { LoadingIndicator } from '../components/common/LoadingIndicator';
import NotFound from '../components/common/NotFound';
import Home from '../components/home/Home';
import Login from '../components/user/login/Login';
import "./App.css";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false
    }

    notification.config({
      placement: 'topRight',
      top: 70,
      duration: 3
    });
  }

  componentDidMount() {
    if (this.state.isAuthenticated === false) {
      this.props.history.push('/login');
    }
  }

  render() {
    if (this.state.isLoading) {
      return <LoadingIndicator />
    }
    else {
      return (
        <Switch>
          <Route exact path="/login" render={(props) => <Login {...props} isAuthenticated={this.state.isAuthenticated} />}></Route>
          <Route path="/" render={(props) => <Home {...props} />}></Route>
          <Route component={NotFound}></Route>
        </Switch>
      );
    }
  }

}

export default withRouter(App);
