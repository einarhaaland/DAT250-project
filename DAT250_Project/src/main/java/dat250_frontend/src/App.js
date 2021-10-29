import { Component } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './App.css';
import Polls from './components/Polls/Polls'
import User from './components/user/User'
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

  handleLogin(user){
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

    </Layout>
    </Router>
  );
  }

}
