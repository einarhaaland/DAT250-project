import React from "react";

export function SearchPolls() {
    return (
        <div>
            <h2>Search for poll</h2>
            <form>
                <input type="text" id="search" name="search" placeholder="Name of poll"></input><br/>
                <button onClick="submit">Search</button>
            </form>
        </div>
    )
}