import { Component } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './App.css';
import Polls from './components/Polls/Polls'
import User from './components/user/User'
import Vote from './components/Vote'
import Layout from './components/navbar/Layout'

export default class App extends Component {
  static displayName = App.name

  constructor(props) {
    super(props)

    this.state = {
      loggedInStatus: false,
      currentUser: {},
      errorMessage: '',
      loaded: false
    }
  }

  handleLogin(user) {
    this.setState({
      loggedInStatus: true,
      currentUser: user
    });

  }

  render() {


    return (
      <Router>
        <Layout>

          <Route
            exact
            path={'/'}
            render={props => (
              <Polls {...props} />
            )}
          />

          <Route
            exact
            path={'/profile/:id'}
            render={() => (
              <User user={this.state.currentUser} />
            )}
          />

          <Route path={'/polls/:id'} component={Vote} />

        </Layout>
      </Router>
    );
  }

}
