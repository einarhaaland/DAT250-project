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

    return (
        <div>
            <h1>{question}</h1>
            <button type="submit">YES</button>
            <button type="submit">NO</button>
        </div>
    )
}