import React from "react";
import { useState } from "react";

export function CreatePoll(props) {
    const [question, setQuestion] = useState("");
    const user = props.user;

    const handleSubmit = () => {
        fetch("/user/" + user.id + "/polls", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify(question)
        })
        //.then(resp => resp.json())
    }
    
    return (
        <div>
            <div>
                <h2>Create new poll</h2>
                <form onSubmit={handleSubmit}>
                    <label>Poll Question:
                        <input 
                            type="text" 
                            name="question" 
                            value= {question}
                            onChange={(e) => setQuestion(e.target.value)}
                            />
                    </label>
                    <input type="submit" />
                </form>
            </div>
        </div>
    )
}