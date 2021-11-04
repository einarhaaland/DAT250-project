import { Component } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './App.css';
import Polls from './components/Polls/Polls'
import User from './components/user/User'
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
  }

  handleLogin(user){
    this.setState({
      loggedInStatus: true,
      currentUser: user
    });

  }

  handleLogout(){
    this.setState({
      loggedInStatus: false,
      currentUser: {}
    });
  }

  render() {

  
  return (
    <Router>
    <Layout>

      <Route 
              exact
              path='/login'
              render = {props => (
              <Login {...props} handleLogin={this.handleLogin} handleLogout={this.handleLogout}/>
              )}
      />

      <Route 
            exact 
            path={'/'}
            render = {props => (
              <Polls {...props} />
            )}
      />
      
      <Route 
            exact 
            path={'/profile'}
            render = {props => (
              <User {...props} />
            )}
      />

      <Route path='/register' component={Register}/>
    </Layout>
    </Router>
  );
  }

}
