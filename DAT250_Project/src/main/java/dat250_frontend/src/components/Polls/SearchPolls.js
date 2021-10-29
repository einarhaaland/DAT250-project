import React from "react";
import { useHistory } from "react-router-dom";
import { useState } from "react";

export default function SearchPolls() {
    const [id, setId] = useState("");

    const history = useHistory()

    const [question, setQuestion] = useState([])

    const handleSubmit = () => {
        fetch(`/polls/${id}`)
            .then((response) => response.json())
            .then((data) => {
                setQuestion(data.question)
            })
        if (question !== "") history.push('/polls/' + id);

        //if (res.status > 199 && res.status < 300) history.push('/polls/' + id);

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