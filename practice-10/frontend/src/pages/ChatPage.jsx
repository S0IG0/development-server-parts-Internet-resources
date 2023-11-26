import {MessageServiceClient} from "../proto/chat_grpc_web_pb"
import {Empty, Message} from "../proto/chat_pb"
import {useEffect, useRef, useState} from "react";


import ColorHash from 'color-hash';
import {v1 as uuid} from "uuid";
import {Transition, TransitionGroup} from "react-transition-group";

import "./chat.css"

const colorHash = new ColorHash();

const client = new MessageServiceClient(
    "http://10.192.216.14:8082",
    null,
    null
)
const clientId = uuid();
const clientColor = colorHash.hex(clientId);


function ChatPage() {

    const [username, setUsername] = useState("anonymous")

    const [messages, setMessages] = useState([]);
    const [loading, setLoading] = useState(false);
    const [text, setText] = useState("");

    const btn = useRef(null);
    const inputMessage = useRef(null);

    const sendMessage = () => {
        inputMessage.current.focus();
        setLoading(true);
        const message = new Message();
        message.setText(JSON.stringify(
            {
                clientId: clientId,
                text: text,
                color: clientColor,
                username: username,
            }
        ));
        client.sendMessage(message, {}, (err, response) => {
            setLoading(false);
            if (err) {
                console.error("Error:", err);
            } else {
                setText("")
            }
        });
    }

    const handleKeypress = (event) => {
        if (event.key === "Enter") {
            btn.current.click();
        }
    };


    useEffect(() => {
        const stream = client.joinChannel(new Empty(), {});
        stream.on("data", (response) => {

            const message = JSON.parse(response.array[0]);
            setMessages((prevState) => [...prevState, {...message, isMy: message.clientId !== clientId}])
        })

        stream.on("status", function (status) {
            console.log(status.code, status.details, status.metadata);
        });

        stream.on("end", () => {
            console.log("Stream ended.");
        });

        return () => {
            stream.cancel()
        }
    }, []);


    const scrollingDivRef = useRef(null);
    useEffect(() => {
        if (scrollingDivRef.current) {
            scrollingDivRef.current.scrollIntoView({behavior: "smooth"});
        }
    }, [messages]);

    return (
        <>
            <div className="m-2">
                <div className="m-auto" style={{minWidth: "300px", maxWidth: "1000px"}}>

                    <div className="usernmame mb-4">
                        <label className="form-label">You username for all users</label>
                        <input
                            className="form-control w-50"
                            value={username}
                            onChange={event => setUsername(event.target.value)}
                        />
                    </div>

                    <div
                        className="card w-100 m-auto mb-4 overflow-auto"
                        style={{
                            minWidth: "300px",
                            minHeight: "400px",
                            maxHeight: "400px"
                        }}
                    >
                        <TransitionGroup
                            className="ms-2 me-2 mt-1 mb-4 d-flex flex-column">
                            {messages.map((message, index) => (
                                <Transition
                                    key={index}
                                    in={index === messages.length - 1}
                                    timeout={1000}
                                >
                                    {state => (
                                        <div
                                            key={index}
                                            className={`message ${message.isMy ? "my" : "not-my"} mt-2 p-2 rounded-2 position-relative align-self-${message.isMy ? "start" : "end"} ${state}`}
                                            style={{
                                                backgroundColor: `${message.color}30`,
                                                maxWidth: "max-content"
                                            }}
                                        >
                                            {message.isMy ?
                                                <>
                                            <span
                                                className="badge rounded-2 me-2"
                                                style={{
                                                    backgroundColor: message.color,
                                                }}
                                            >
                                                {message.username}
                                            </span>
                                                    {message.text}
                                                </>
                                                :
                                                <>
                                                    {message.text}
                                                    <span
                                                        className="badge rounded-2 ms-2"
                                                        style={{
                                                            backgroundColor: message.color,
                                                        }}
                                                    >
                                                {message.username}
                                            </span>
                                                </>
                                            }
                                        </div>
                                    )}
                                </Transition>
                            ))}
                        </TransitionGroup>
                        <div ref={scrollingDivRef}/>
                    </div>
                    <div className="d-flex justify-content-between">
                        <input
                            onKeyPress={handleKeypress}
                            ref={inputMessage}
                            className="form-control w-75"
                            type="text"
                            value={text}
                            onChange={event => setText(event.target.value)}
                        />
                        <button
                            className="btn btn-success text-nowrap ms-4"
                            onClick={sendMessage}
                            ref={btn}
                        >
                            {loading ?
                                <div className="spinner-border spinner-border-sm text-light" role="status">
                                    <span className="visually-hidden">Loading...</span>
                                </div> : "send message"
                            }
                        </button>
                    </div>
                </div>
            </div>

        </>
    );
}

export default ChatPage;
