import React from 'react'

export default function EditUsername() {
    return (
        <div>
            <h2>Change Username</h2>
            <form>
                <label >New Email:
                    <input type="text" id="fname" name="fname" />
                </label>
                <label >Repeat:
                    <input type="text" id="lname" name="lname" />
                </label>
                <button type="submit">Submit</button>
            </form>
        </div>
    )
}