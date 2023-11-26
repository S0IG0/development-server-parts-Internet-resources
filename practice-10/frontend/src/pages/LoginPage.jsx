import {UserRequest} from "../proto/chat_pb"
import {UserServiceClient} from "../proto/chat_grpc_web_pb"
import {useState} from "react";
import {Transition} from 'react-transition-group';

import "./login.css"

const client = new UserServiceClient(
    "http://10.192.216.14:8082",
    null,
    null
)

function LoginPage() {

    const [user, setUser] = useState({
        username: "",
        password: "",
    })

    const [jwt, setJwt] = useState({
        access: "",
        refresh: "",
    })

    const [loading, setLoading] = useState(false);
    const [isClipboard, setIsClipboard] = useState(false)

    const [register, setRegister] = useState(false);

    const clipboard = (data) => {
        setIsClipboard(true);

        navigator?.clipboard?.writeText(data)
            .then(() => {
                console.log('Text copied to clipboard:', data);
            })
            .catch((err) => {
                console.error('Error copying to clipboard:', err);
            });
    }

    const login = () => {

        setLoading(true);
        const userRequest = new UserRequest();
        userRequest.setUsername(user.username);
        userRequest.setPassword(user.password);

        if (register) {
            client.registerUser(userRequest, {}, (err, response) => {
                setLoading(false);
                if (err) {
                    console.error("Error:", err);
                } else {
                    setJwt({access: response.array[0], refresh: response.array[1]})
                }
            });
        } else {
            client.loginUser(userRequest, {}, (err, response) => {
                setLoading(false);
                if (err) {
                    console.error("Error:", err);
                } else {
                    setJwt({access: response.array[0], refresh: response.array[1]})
                }
            });
        }

    }

    return (
        <>
            <Transition
                in={isClipboard}
                timeout={{
                    enter: 1000,
                    exit: 400,
                    appear: 200,
                }}
                mountOnEnter
                unmountOnExit

                onEntered={() => setIsClipboard(false)}
            >
                {state => (
                    <div className={`alert alert-success w-50 position-absolute ${state}`}
                         style={{minWidth: "300px", maxWidth: "500px"}}>
                        Success copied to clipboard
                    </div>
                )}
            </Transition>
            <div>
                <div className="w-75 m-auto mt-4" style={{minWidth: "300px", maxWidth: "500px"}}>
                    <p className="d-inline-flex gap-1">
                        <button className="btn btn-primary" type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                            You JWT token
                        </button>
                    </p>
                    <div className="collapse" id="collapseExample">
                        <div className="card card-body">
                            <Transition
                                in={jwt.access !== ""}
                                timeout={500}
                            >
                                {state => (
                                    <>
                                        <h5 className="card-title">Access</h5>
                                        <div className={`card mb-4 bg-body-tertiary clipboard-body ${state}`}
                                             onClick={() => clipboard(jwt.access)}
                                        >
                                            <p className={`card-text m-2 ${state}`}>{jwt.access}</p>
                                            <i className="bi bi-clipboard position-absolute m-2 clipboard-icon"></i>
                                        </div>
                                        <div className="card w-100 mb-3" style={{height: "1px"}}></div>

                                        <h5 className="card-title">Refresh</h5>
                                        <div className={`card mb-2 bg-body-tertiary clipboard-body ${state}`}
                                             onClick={() => clipboard(jwt.refresh)}
                                        >
                                            {/*style={{minHeight: "120px"}}*/}
                                            <p className={`card-text m-2 ${state}`}>{jwt.refresh}</p>
                                            <i className="bi bi-clipboard position-absolute m-2 clipboard-icon"></i>
                                        </div>
                                    </>
                                )}
                            </Transition>
                        </div>
                    </div>
                </div>
                <form className="w-75 m-auto mt-4" style={{minWidth: "300px", maxWidth: "500px"}}>
                    <div className="mb-3">
                        <label className="form-label">Username</label>
                        <input value={user.username} type="text" className="form-control" placeholder="killer20015"
                               onChange={event => setUser({...user, username: event.target.value})}
                        />
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Password</label>
                        <input value={user.password} type="password" className="form-control"
                               onChange={event => setUser({...user, password: event.target.value})}
                        />
                    </div>
                    <div className="d-flex align-items-center">
                        <div
                            onClick={login}
                            className="btn btn-success"
                        >
                            {loading ?
                                <div className="spinner-border  spinner-border-sm text-light" role="status">
                                    <span className="visually-hidden">Loading...</span>
                                </div>
                                : register ? "register" : "login"}
                        </div>
                        <div className="form-check ms-4">
                            <input className="form-check-input" type="checkbox"
                                   checked={register}
                                   onChange={event => setRegister(!register)}
                            />
                            <label className="form-check-label">
                                Register
                            </label>
                        </div>
                    </div>
                </form>
            </div>
        </>
    );
}

export default LoginPage;
