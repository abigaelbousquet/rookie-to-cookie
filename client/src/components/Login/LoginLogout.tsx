import "../../styles/profile.css";
import React, { useState } from "react";
import {
  createUserWithEmailAndPassword,
  fetchSignInMethodsForEmail,
  getAuth,
  signInWithEmailAndPassword,
} from "firebase/auth";
import { ControlledInput } from "../SelectionTypes/ControlledInput";
import { addLoginCookie } from "../../utils/cookie";

/**
 * This component houses the login functionality, querying firebase and the backend, and has the signout button
 */
export interface ILoginPageProps {
  authing: number;
  setAuthing: React.Dispatch<React.SetStateAction<number>>;
}

const Login: React.FunctionComponent<ILoginPageProps> = (props) => {
  const auth = getAuth();
  const [error, setError] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const handleClick = async () => {
    if (password === "" || email === "") {
      alert("Please enter your email and password");
    } else {
      const loginOptions: string[] = await fetchSignInMethodsForEmail(
        auth,
        email
      );
      console.log(loginOptions);

      if (loginOptions.length === 0) {
        // new user
        try {
          const response = await createUserWithEmailAndPassword(
            auth,
            email,
            password
          );
          addLoginCookie(response.user.uid);
          console.log("acct created");
          props.setAuthing(2);
        } catch (error: any) {
          if (error.message.includes("auth/invalid-email")) {
            alert("invalid email");
            props.setAuthing(0);
          } else if (error.message.includes("WEAK_PASSWORD")) {
            props.setAuthing(0);
          } else {
            if (!password || password.length < 6) {
              alert("Password should be at least 6 characters long");
              props.setAuthing(0);
            }
          }
        }
      } else {
        // returning user
        try {
          const response = await signInWithEmailAndPassword(
            auth,
            email,
            password
          );
          console.log(response);
          props.setAuthing(1);
          addLoginCookie(response.user.uid);
        } catch (error: any) {
          alert("Incorrect password for email given. Please try again.");
          props.setAuthing(0);
        }
      }
    }
  };
  return (
    <div className="popup">
      <div className="popup-inner">
        <div className="popup-inmost">
          <div className="login-box">
            <h2>Login:</h2>
            <div className="login-elt">
              <legend>Email:</legend>
              <ControlledInput
                type="text"
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
                type="password"
                styleID="input-box"
                value={password}
                setValue={setPassword}
                ariaLabel="password"
                placeholder="ilovecooking"
              ></ControlledInput>
            </div>
            <div>
              <button
                className="sign-in"
                onClick={handleClick}
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
        className="sign-out"
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
