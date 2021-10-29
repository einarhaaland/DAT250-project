import React from 'react'

export default function EditPassword() {
    return (
        <div>
            <h2>Change Password</h2>
            <form>
                <label for="fname">New Password:</label>
                <input type="text" id="fname" name="fname" />
                <label for="lname">Repeat:</label>
                <input type="text" id="lname" name="lname" />
                <button type="submit">Submit</button>
            </form>
        </div>
    )
}