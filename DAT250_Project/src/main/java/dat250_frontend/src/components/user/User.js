import React from 'react'
import EditPassword from "./EditPassword";
import EditUsername from "./EditUsername";
import UserPolls from "./UserPolls";

export default function User(props) {
    return (
        <div>
            <EditUsername {...props} />
            <EditPassword />
            <UserPolls />
        </div>
    )
}