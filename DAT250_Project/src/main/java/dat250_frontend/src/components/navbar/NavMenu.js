import { React, Component } from "react";
import { Collapse, Container, Navbar, NavbarToggler, NavItem, NavLink} from 'reactstrap';
import { Link } from 'react-router-dom';
import './NavMenu.css';

export default class NavMenu extends Component {
    static displayName = NavMenu.name

    constructor (props) {
        super(props)

        this.toggleNavbar = this.toggleNavbar.bind(this);
        this.state = {
            collapsed: true,

        };
        
    }

    toggleNavbar () {
        this.setState({
          collapsed: !this.state.collapsed
        });
      }

    isLoggedIn(){
        console.log(this.props.currentUser)
        if (Object.keys(this.props.currentUser).length > 0) {
        return(
            <NavLink tag={Link} onClick={this.props.handleLogout} className="text-light" to="/login">Logout</NavLink>
        );
        }
        else {
        return (   
            <NavLink tag={Link} className="text-light" to="/login">Login</NavLink>
        );
        }
    }


    render() {
        let contents = this.isLoggedIn();

        return (
            <header>
                <Navbar className="navbar-expand-sm navbar-toggleable-sm ng-white border-bottom box-shadow mb-3" light>
                    <Container>
                        <NavbarToggler onClick={this.toggleNavbar} className="mr-2" />
                        <Collapse className="d-sm-inline-flex flex-sm-row-reverse" isOpen={!this.state.collapsed} navbar>
                            <ul className="navbar-nav flex-grow">
                                <NavItem>
                                    <NavLink tag={Link} className="text-light" to="/">Polls</NavLink>
                                </NavItem>
                                <NavItem>
                                    <NavLink tag={Link} className="text-light" to="/profile">Profile</NavLink>
                                </NavItem>
                                <NavItem>
                                    {contents}
                                </NavItem>
                            </ul>
                        </Collapse>
                    </Container>
                </Navbar>
            </header>
        )
    }
}