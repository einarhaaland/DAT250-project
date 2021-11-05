import React from "react";
import { useState, useEffect } from "react";
import { useParams } from "react-router";

export default function Vote() {
    const { id } = useParams();

    const [poll, setPoll] = useState([])
    const polls = async () => {
        await fetch(`/polls/${id}`)
            .then((response) => response.json())
            .then((data) => {
                setPoll(data)
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
            <div>
                <h1>{poll.question}</h1>
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
            <div>
                <br /><br /><br />
                <h3>Results</h3>
                <table className='table table-sm'>
                    <thead class="thead-dark">
                        <tr>
                            <th>Total votes:</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>YES: %</td>
                            <td>NO: %</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            
        </div>
    )
}