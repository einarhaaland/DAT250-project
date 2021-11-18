import React, { Component } from 'react';
import {Alert} from 'reactstrap';

export default class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            response: '',
            onDismissWarning: true,
            change: true
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    showWarning = () => this.setState({onDismissWarning: false});
    onDismissWarning = () => this.setState({onDismissWarning: true});

    checkIfValid(change) {
        if(this.state.username === '' || this.state.password === '') {
            this.setState({change: true});
        } else {
            this.setState({change: false});
        }
    }

    handleChange(e) {
        const {name, value} = e.target;
        this.setState({ [name]: value});
        this.checkIfValid(this.change);
    }

    async handleSubmit(e) {
        e.preventDefault()
        const {username, password} = this.state

        await fetch('/login', {
            method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify({
                     username, 
                     password,
                    })
            }).then((response) => {
                if(response.status >= 200 && response.status <= 299){
                    this.props.handleLogin(response.json);
                    return response.json();
                }
                else{
                    this.showWarning();
                    throw Error("Login was not successful");
                }
            }).then((jsonResponse) => {
                console.log("from login:" + jsonResponse);
                this.props.history.push('/')
            }).catch((error) => {
                console.log("Something went wrong in login: " + error);
            });

    }

    async handleRedirect() {
        const{response} = this.state;
        console.log(response);
         if(response.status === 200) {
             //this.props.handleLogin()
             this.props.history.push('/');
         }
         else{
             this.props.history.push('/register')
         }
     }

    render () {

        const{ username, password} = this.state;
        return (
            <div>
                <Alert color="warning" toggle={this.onDismissWarning} hidden={this.state.onDismissWarning}>
                 Feil brukernavn eller passord.
                </Alert>
                <form onSubmit={this.handleSubmit}>
                    <label htmlFor="username">
                        <p> Username: </p>
                        <input type="text" name="username" value={username} onChange={this.handleChange} />       
                    </label><br/><br/>
                    <label htmlFor="password">
                        <p> Password: </p>
                        <input type="password" name="password" value={password} onChange={this.handleChange}/><br/>
                    </label>
                        <br/><br/><button type="submit" className={"btn btn-info"} disabled={this.state.change}>Login</button>   
                 </form>  
                 <a href={"/register"}>Register here</a>
            </div>  
        )
    }
    
}