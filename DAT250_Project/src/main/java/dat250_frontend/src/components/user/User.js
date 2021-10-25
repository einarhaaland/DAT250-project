import React from 'react'
import {EditPassword} from "./EditPassword";
import {EditUsername} from "./EditUsername";
import {UserPolls} from "./UserPolls";

export function User() {
    return (
        <div>
            <EditUsername />
            <EditPassword />
            <UserPolls />
        </div>
    )
}