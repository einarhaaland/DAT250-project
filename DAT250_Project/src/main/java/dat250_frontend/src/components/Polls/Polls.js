import React from "react";
import CreatePoll from "./CreatePoll"
import SearchPoll from "./SearchPolls"

export default function Polls(props) {
    return (
        <div>
            <CreatePoll {...props} />
            <SearchPoll />
        </div>
    )
}