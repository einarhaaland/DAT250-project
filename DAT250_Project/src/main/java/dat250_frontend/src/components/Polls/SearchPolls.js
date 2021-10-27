import React from "react";
import { useHistory } from "react-router-dom";
import { useState } from "react";

export default function SearchPolls() {
    const [id, setId] = useState("");

    const handleSubmit = async () => {
        await fetch('/polls/' + id)
            .then((response) => {
                if (response.ok) {
                    useHistory().push('/vote/' + id);
                }
                else {
                    //throw error
                }
            })
    }

    return (
        <div>
            <h2>Search for poll</h2>
            <form onSubmit={handleSubmit}>
                <input 
                    type="text"  
                    name="id" 
                    placeholder="Poll id"
                    value={id}
                    onChange={(e) => setId(e.target.value)}
                />
                <input type="submit" />
            </form>
        </div>
    )
}