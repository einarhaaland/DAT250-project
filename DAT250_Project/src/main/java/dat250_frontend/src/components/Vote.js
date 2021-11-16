import React from "react";
import { useState, useEffect } from "react";
import { useParams } from "react-router";

export default function Vote() {
    const { id } = useParams();

    const [question, setQuestion] = useState([])
    const [voteYes, setYes] = useState([])
    const [voteNo, setNo] = useState([])


    const polls = async () => {
        await fetch(`/polls/${id}`)
            .then((response) => response.json())
            .then((data) => {
                setQuestion(data.question)
            })
    }

    const votes = async () => {
        let yesVotes = 0;
        let noVotes = 0;

        await fetch(`/polls/${pollId}/votes`)
            .then((response) => response.json())
            .then((data) => {
                for (let i = 0; i < data.length; i++) {
                    if (data[i].vote === "YES") yesVotes++;
                    else noVotes++;
                }
                setYes(yesVotes);
                setNo(noVotes);
            })
    }

    useEffect(() => {
        polls();
        votes();
    })

    const handleSubmit = (vote) => (event) => {

        event.preventDefault();
        fetch(`/polls/${id}/votes`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify({ vote: vote })
        })
        if(vote === "Yes") {
            setYes(Integer.parseInt(voteYes) + 1)
        } else {
            setNo(Integer.parseInt(voteNo) + 1)
        }
    }

    return (
        <div>
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
            <div>
                <br/><br/><br/>
                <h3>Results</h3>
                <table className='table table-sm'>
                    <thead className="thead-dark">
                    <tr>
                        <th>Total votes:</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>YES: {voteYes}</td>
                        <td>NO: {voteNo}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    )
}