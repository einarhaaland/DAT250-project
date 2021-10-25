import React from 'react'

export function EditUsername() {
    return (
        <div>
            <h2>Change Username</h2>
            <form>
                <label for="fname">New Email:</label>
                <input type="text" id="fname" name="fname" />
                <label for="lname">Repeat:</label>
                <input type="text" id="lname" name="lname" />
                <button type="submit">Submit</button>
            </form>
        </div>
    )
}