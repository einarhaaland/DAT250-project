import React from "react";

export function Polls(props) {
    return (
        <div>
            <CreatePoll {...props} />
            <SearchPoll />
        </div>
    )
}