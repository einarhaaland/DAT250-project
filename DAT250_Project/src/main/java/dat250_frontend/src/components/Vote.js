import React from "react";
import { useState, useEffect } from "react";
import { useParams } from "react-router";

export default function Vote() {
    const { id } = useParams();

    const [question, setQuestion] = useState([])
    const polls = async () => {
        await fetch(`/polls/${id}`)
            .then((response) => response.json())
            .then((data) => {
                setQuestion(data.question)
            })
    }

    useEffect(() => {
        polls();
    })

    const handleSubmit = (vote) => (event) => {

        event.preventDefault();
        fetch(`/polls/${id}/votes`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify({ vote: vote })
        })

    }

    return (
        <div>
            <h1>{question}</h1>
            <button
                onClick={handleSubmit("YES")}
                type="submit">
                YES
            </button>
            <button
                onClick={handleSubmit("NO")}
                type="submit">
                NO
            </button>
        </div>
    )
}