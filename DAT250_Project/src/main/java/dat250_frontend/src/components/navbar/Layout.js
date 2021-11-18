import React, { Component } from 'react';
import { Container } from 'reactstrap';
import NavMenu from './NavMenu';

export default class Layout extends Component {
  static displayName = Layout.name;

  constructor(props){
    super(props);
    this.state = ({currentUser: {}});
  }

  componentDidMount(){
    this.setState({currentUser: this.props.currentUser});
    
  }

  render () {
    return (
      <div>
        <NavMenu currentUser={this.props.currentUser} handleLogout={this.props.handleLogout}/>
        <Container>
          {this.props.children}
        </Container>
      </div>
    );
  }
}
