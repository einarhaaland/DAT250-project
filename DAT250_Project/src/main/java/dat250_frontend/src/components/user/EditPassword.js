import React from 'react';
import { useState } from 'react';

export default function EditPassword(props) {
    const [user, password] = useState("");
    const user = props.user;
    var fname, lname = null;

    const handleSubmit = () => {
        if(fname == lname){
            fetch("/user/" + user.id, {
                method: 'POST',
                headers: {'Content-Type': 'applicaiton/json'},
                credentials: 'include',
                body: JSON.stringify(lname)
            })
        }else{
            console.log("Passwords are not equal");
        }
    }

    return (
        <div>
            <h2>Change Password</h2>
            <form onSubmit={handleSubmit}>
                <label for="fname">New Password:</label>
                <input type="text" id="fname" name="fname" value={fname} />
                <label for="lname">Repeat:</label>
                <input type="text" id="lname" name="lname" value={lname} />
                <button type="submit">Submit</button>
            </form>
        </div>
    )
}