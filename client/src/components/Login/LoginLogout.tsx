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
  authing: boolean;
  setAuthing: React.Dispatch<React.SetStateAction<boolean>>;
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
            <h2>Login</h2>
            <legend>Email:</legend>
            <ControlledInput
              styleID="input-box"
              value={email}
              setValue={setEmail}
              ariaLabel="email"
              placeholder="josiah_carberry@brown.edu"
            ></ControlledInput>
            <legend>Password:</legend>
            <ControlledInput
              styleID="input-box"
              value={password}
              setValue={setPassword}
              ariaLabel="password"
              placeholder="ilovecooking"
            ></ControlledInput>
            <div>
              <button
                className="google-login-button"
                onClick={() => {
                  if (password === null || email === null) {
                    alert("Please enter your email and password");
                  }
                  try {
                    signInWithEmailAndPassword(auth, email, password);
                    props.setAuthing(true);
                  } catch (error) {
                    createUserWithEmailAndPassword(auth, email, password);
                    props.setAuthing(true);
                  }
                }}
                disabled={props.authing}
                aria-label="Login"
              >
                Login
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
          props.setAuthing(false);
        }}
      >
        Sign Out
      </button>
    </div>
  );
};

const LoginLogout: React.FunctionComponent<ILoginPageProps> = (props) => {
  return <>{!props.authing ? <Login {...props} /> : <Logout {...props} />}</>;
};

export default LoginLogout;
