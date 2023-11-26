import LoginPage from "./pages/LoginPage";
import ChatPage from "./pages/ChatPage";
import {useState} from "react";

function App() {

    const [component, setComponent] = useState(<LoginPage/>)

    return (
        <>
            <div className="btn-group m-2" role="group" aria-label="Basic example">
                <button
                    type="button"
                    className="btn btn-primary"
                    onClick={() => setComponent(<LoginPage/>)}
                >
                    Login
                </button>
                <button
                    type="button"
                    className="btn btn-primary"
                    onClick={() => setComponent(<ChatPage/>)}
                >
                    Chat
                </button>

            </div>
            {component}
        </>
    );
}

export default App;
