import React from "react";
import { useHistory } from "react-router-dom";
import { useState } from "react";

export default function SearchPolls() {
    const [id, setId] = useState("");
    const history = useHistory()
    const [question, setQuestion] = useState([])

    const handleSubmit = () => {
        fetch(`/poll/${id}`)
            .then((response) => response.json())
            .then((data) => {
                if (data == null) return
                setQuestion(data.question)
                console.log(data)
            })
        if (!(question === undefined || question.length === 0)) {
            history.push('/polls/' + id);
        }

    }

    return (
        <div>
            <h2>Search for poll</h2>
            <form>
                <input 
                    type="text"  
                    name="id" 
                    placeholder="Poll id"
                    value={id}
                    onChange={(e) => setId(e.target.value)}
                />
                <button type="button" onClick={handleSubmit}>Submit</button>
            </form>
        </div>
    )
}