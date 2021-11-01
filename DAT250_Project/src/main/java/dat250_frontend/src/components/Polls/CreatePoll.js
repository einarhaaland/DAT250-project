import React from "react";
import { useState } from "react";

export default function CreatePoll(props) {
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
                <form>
                    <input 
                        type="text" 
                        name="question"
                        placeholder="Poll Question" 
                        value= {question}
                        onChange={(e) => setQuestion(e.target.value)}
                        />
                    <button type="button" onClick={handleSubmit}>Submit</button>
                </form>
            </div>
        </div>
    )
}