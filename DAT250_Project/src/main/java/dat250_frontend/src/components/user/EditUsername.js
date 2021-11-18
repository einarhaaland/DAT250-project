import React from 'react';
import {useState} from 'react';

export default function EditUsername(props) {
    
    const [username, setUsername] = useState("");
    const [repeat, setRepeat] = useState("");
    const user = props.user;
    const handleSubmit = () => {

        if (username === repeat) {

            //TODO: make sure this works
            fetch("/users/" + user.id, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({username: repeat})
            })

        } else {
            console.alert("Usernames are not equal");
        }
    }

    return (
        <div>
            <h2>Change Username</h2>
            <form onSubmit={handleSubmit}>
                <label >New Email:
                    <input 
                    type="text" 
                    name="username" 
                    value={username}
                    onChange = {(e) => setUsername(e.target.value)}
                     />
                </label>
                <label >Repeat:
                    <input 
                    type="text" 
                    name="repeat"
                    value={repeat}
                    onChange = {(e) => setRepeat(e.target.value)}
                    />
                </label>
                <button type="submit">Submit</button>
            </form>
        </div>
    )
}