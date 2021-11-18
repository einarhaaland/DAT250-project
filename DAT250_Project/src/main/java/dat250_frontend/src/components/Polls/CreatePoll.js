import React from "react";
import { useState } from "react";

export default function CreatePoll(props) {
    const [question, setQuestion] = useState("");
    const user = props.user;

    const handlePollResults = (pollId) => {
        //send result to dweet.io
        //To dweet from your thing, simply call a URL like:https://dweet.io/dweet/for/my-thing-name?hello=world
        //Just replace my-thing-name with a unique name. That's it!
        //To read the latest dweet for a thing, you can call...https://dweet.io/get/dweets/for/my-thing-name
        let yesVotes = 0;
        let noVotes = 0;

        fetch(`/polls/${pollId}/votes`)
            .then((response) => response.json())
            .then((data) => {
                for (var i = 0; i < data.length; i++) {
                    console.log("inni loop");
                    if (data[i].vote === "YES") yesVotes++;
                    else noVotes++;
                }
            })
            .then(() => {
                fetch(`/results/${pollId}`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        question: question,
                        yes: yesVotes,
                        no: noVotes
                    })
                })
            })
            .then(() => {
                fetch("https://dweet.io/dweet/for/dat250poller?" + question, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        question: question,
                        yes: yesVotes,
                        no: noVotes
                    })
                })
            })

        //https://dweet.io/get/dweets/for/dat250poller

        //do some messaging system things
    }

    const handleSubmit = () => {
        fetch("/user/" + user.id + "/polls", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify({ question: question })
        })
            .then(resp => resp.json())
            .then((data) => {
                setTimeout(function () { handlePollResults(data.id); }, 1000 * 40); //time in ms
            })

        fetch("https://dweet.io/dweet/for/dat250poller?" + question, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                question: question,
                
            })
        })

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
                        value={question}
                        onChange={(e) => setQuestion(e.target.value)}
                    />
                    <button type="button" onClick={handleSubmit}>Submit</button>
                </form>
            </div>
        </div>
    )
}