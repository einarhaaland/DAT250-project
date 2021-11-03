import React from 'react';
import { useState } from 'react';

export default function EditPassword(props) {
    const [password, setPassword] = useState("");
    const [repeat, setRepeat] = useState("");
    const user = props.user;
    //var first, repeat = null;
    console.log(props.toString());
    const handleSubmit = () => {

        if (password === repeat) {

            console.log("fetching...")
            fetch("/users/51", {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                //credentials: 'include',
                body: JSON.stringify({password: repeat})
            })

        } else {
            console.log("Passwords are not equal");
        }
    }

    return (
        <div>
            <h2>Change Password</h2>
            <form onSubmit={handleSubmit}>
                <label >New Password:
                    <input 
                    type="password" 
                    name="password" 
                    value={password}
                    onChange = {(e) => setPassword(e.target.value)}
                    />
                </label>
                <label >Repeat:
                    <input 
                    type="password" 
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