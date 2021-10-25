import { Collapse } from "bootstrap";
import { React, Component } from "react";
import './NavMenu.css';

export class NavMenu extends Component {
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


    render() {

        return (
            <header>
                <NavBar className="navbar-expand-sm navbar-toggleable-sm ng-white border-bottom box-shadow mb-3" light>
                    <Container>
                        <NavItem tag={Link} to="/" className="text-light">Home</NavItem>
                        <NavbarToggler onClick={this.toggleNavbar} className="mr-2" />
                        <Collapse className="d-sm-inline-flex flex-sm-row-reverse" isOpen={!this.state.collapsed} navbar>
                            <ul className="navbar-nav flex-grow">
                                <NavItem>
                                    <NavLink>Polls</NavLink>
                                </NavItem>
                                <NavItem>
                                    <NavLink>Profile</NavLink>
                                </NavItem>
                                <NavItem>
                                    
                                </NavItem>
                            </ul>
                        </Collapse>
                    </Container>
                </NavBar>
            </header>
        )
    }
}