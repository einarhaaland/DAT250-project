import React, { Component } from 'react';
import {Alert} from 'reactstrap';

export default class Register extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            rewritePassword: '',
            onDismiss: true,
            onDismissWarning: true,
            statuscode: 0,
            change: true};

            this.handleChange = this.handleChange.bind(this);
            this.handleSubmit = this.handleSubmit.bind(this);
    }

    onDismiss = () => this.setState({onDismiss: true});
    showWarning = () => this.setState({onDismissWarning: false});
    onDismissWarning = () => this.setState({onDismissWarning: true});

    checkIfValid(change) {

        if (this.state.username === '' || this.state.password === '') {
            this.setState({change: true})
        } else {
            this.setState({change: false})
        }
    }

    handleChange(e) {
        const {name, value} = e.target
        this.setState({ [name]: value})
        this.checkIfValid(this.change)
    }

    async handleSubmit(e) {
        e.preventDefault()
        const {username, password} = this.state

        if (this.state.password === this.state.rewritePassword) {

        const res = await fetch('/users', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify({
                 username, 
                 password
                })
            })
            this.setState({statuscode: res.status})
            console.log(this.state.statuscode)
            if(this.state.statuscode > 199 && this.state.statuscode < 300) {
                this.setState({onDismiss: false})
            } else {
                this.showWarning();
            }
        } else {
            this.showWarning()
        }
    }
    


    render () {
        const{ username, password, rewritePassword} = this.state;
        return (
            <div>
                <Alert color="success" toggle={this.onDismiss} hidden={this.state.onDismiss}>
                User created!
                </Alert>
                <Alert color="warning" toggle={this.onDismissWarning} hidden={this.state.onDismissWarning}>
                Feil ved registrering. Ugyldige verdier.
                </Alert>
                <form onSubmit={this.handleSubmit}>
                    <label htmlFor="username">
                        <p> Username: </p>
                        <input type="text" name="username" value={username} onChange={this.handleChange} />           
                    </label><br/><br/>
                    <label htmlFor="password">
                        <p> Password: </p>
                        <input type="password" name="password" value={password} onChange={this.handleChange}/>
                    </label><br/><br/>
                    <label htmlFor="password2">
                        <p> Confirm Password: </p>
                        <input type="password" name="rewritePassword" value={rewritePassword} onChange={this.handleChange}/>
                    </label><br/><br/>
                        <button type="submit" className={"btn btn-info"} disabled={this.state.change}>Sign up</button>   
                 </form>  
            </div> 
        )
    }
}