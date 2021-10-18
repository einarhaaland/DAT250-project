import React from 'react'
//import PollUserService from '../services/PollUserService'

class PollUsers extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            users:[]
        }
    }


    componentDidMount() {
        
        this.getUsers()
        /*PollUserService.getPollUsers()
            .then(response => {
                this.setState({ users: response.data })
            })*/
    }

    render() {
        return (
            <div>
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <td>User Id</td>
                            <td>Username</td>
                        </tr>
                    </thead>

                    <tbody>

                        {
                            this.state.users.map(
                                user =>
                                <tr key={user.id}>
                                    <td>{user.id}</td>
                                    <td>{user.question}</td>
                                </tr>
                            )
                        }

                    </tbody>
                </table>
            </div>
        )
    }
    async getUsers() {
        const response = await fetch("/polls")
        const data = await response.json()
        this.setState({ users: data })
    }
}
export default PollUsers