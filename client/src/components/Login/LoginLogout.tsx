import React, { useState } from "react";
import {
  createUserWithEmailAndPassword,
  getAuth,
  GoogleAuthProvider,
  signInWithEmailAndPassword,
  signInWithPopup,
} from "firebase/auth";
import { AccountCreation } from "./AccountCreation";
import { ControlledInput } from "./ControlledInput";

export interface ILoginPageProps {
  authing: number;
  setAuthing: React.Dispatch<React.SetStateAction<number>>;
}

const Login: React.FunctionComponent<ILoginPageProps> = (props) => {
  const auth = getAuth();
  const [error, setError] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  return (
    <div className="popup">
      <div className="popup-inner">
        <div className="popup-inmost">
          <div className="login-box">
            <h2>Login:</h2>
            <div className="login-elt">
              <legend>Email:</legend>
              <ControlledInput
                styleID="input-box"
                value={email}
                setValue={setEmail}
                ariaLabel="email"
                placeholder="josiah_carberry@brown.edu"
              ></ControlledInput>
            </div>
            <div className="login-elt">
              <legend>Password:</legend>
              <ControlledInput
                styleID="input-box"
                value={password}
                setValue={setPassword}
                ariaLabel="password"
                placeholder="ilovecooking"
              ></ControlledInput>
            </div>
            <div>
              <button
                className="butt"
                onClick={async () => {
                  if (password === null || email === null) {
                    alert("Please enter your email and password");
                  }
                  try {
                    //0= login page
                    //1=home page
                    //2= create account page
                    const response = await signInWithEmailAndPassword(
                      auth,
                      email,
                      password
                    );
                    props.setAuthing(1);
                  } catch (error) {
                    if (error.message === "auth/invalid-email") {
                      alert("invalid email");
                      props.setAuthing(0);
                    } else if (
                      error.message ===
                      "WEAK_PASSWORD : Password should be at least 6 characters"
                    ) {
                      props.setAuthing(2);
                    } else {
                      await createUserWithEmailAndPassword(
                        auth,
                        email,
                        password
                      );
                      console.log("acct created");
                      props.setAuthing(2);
                    }
                  }
                }}
                disabled={props.authing !== 0}
                aria-label="Login"
              >
                LOGIN
              </button>
            </div>
          </div>
        </div>
      </div>
      {error && <p>{error}</p>}
    </div>
  );
};

const Logout: React.FunctionComponent<ILoginPageProps> = (props) => {
  return (
    <div className="logout-box">
      <button
        aria-label="Sign Out"
        className="Sign Out"
        onClick={() => {
          props.setAuthing(0);
        }}
      >
        Sign Out
      </button>
    </div>
  );
};

const LoginLogout: React.FunctionComponent<ILoginPageProps> = (props) => {
  return (
    <>{props.authing === 0 ? <Login {...props} /> : <Logout {...props} />}</>
  );
};

export default LoginLogout;
