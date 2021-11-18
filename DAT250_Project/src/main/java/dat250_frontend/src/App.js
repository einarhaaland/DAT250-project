import { Component } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './App.css';
import Polls from './components/Polls/Polls'
import User from './components/user/User'
import Vote from './components/Vote'
import Layout from './components/navbar/Layout'
import Login from './components/login/Login'
import Register from './components/login/Register'

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
  
    this.handleLogin = this.handleLogin.bind(this);
    this.handleLogout = this.handleLogout.bind(this);
  }

  handleLogin(user) {
    this.setState({
      loggedInStatus: true,
      currentUser: user
    });

    console.log(this.currentUser)
  }

  handleLogout(){
    this.setState({
      loggedInStatus: false,
      currentUser: {}
    });
    console.log(this.currentUser)
  }

  render() {
    return (
      <Router>
        <Layout currentUser={this.state.currentUser} handleLogout={this.handleLogout}>
            <Route 
              exact
              path='/login'
              render = {props => (
              <Login {...props} user={this.state.currentUser} handleLogin={this.handleLogin}/>
              )}
            />

            <Route 
              exact 
              path={'/'}
              render={props => (
                <Polls {...props} user={this.state.currentUser} />
              )}
            />

          <Route
            exact
            path={'/profile'}
            render={() => (
              <User {...this.props} user={this.state.currentUser} />
            )}
          />

          <Route path={'/polls/:id'} component={Vote} />
          <Route path='/register' component={Register}/>
    </Layout>
    </Router>
  );
  }

}
