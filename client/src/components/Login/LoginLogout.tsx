import React, { useEffect, useState } from "react";
import {
  createUserWithEmailAndPassword,
  getAuth,
  GoogleAuthProvider,
  signInWithEmailAndPassword,
  signInWithPopup,
} from "firebase/auth";
import { AccountCreation } from "./AccountCreation";
import { ControlledInput } from "./ControlledInput";
import { addLoginCookie } from "../../utils/cookie";

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
    if (password === null || email === null) {
      alert("Please enter your email and password");
    }
    try {
      //0= login page
      //1=home page
      //2= create account page
      const response = await signInWithEmailAndPassword(auth, email, password);
      console.log(response);
      props.setAuthing(1);
      addLoginCookie(response.user.uid);
    } catch (error: any) {
      if (error.message.includes("auth/invalid-email")) {
        alert("invalid email");
        props.setAuthing(0);
      } else if (error.message.includes("WEAK_PASSWORD")) {
        props.setAuthing(2);
      } else {
        try {
          const response = await createUserWithEmailAndPassword(
            auth,
            email,
            password
          );
          addLoginCookie(response.user.uid);
          console.log("acct created");
          props.setAuthing(2);
        } catch (error) {
          props.setAuthing(2);
        }
      }
    }
  };
  // const handleUserKeyPress = (event: KeyboardEvent) => {
  //   if (event.key === "Enter") {
  //     try {
  //       handleClick();
  //     } catch (error) {
  //       alert(error);
  //     }
  //     console.log("pressed enter");
  //   }
  // };
  // useEffect(() => {
  //   document.addEventListener("keydown", handleUserKeyPress);

  //   return () => {
  //     document.removeEventListener("keydown", handleUserKeyPress);
  //   };
  // }, []);
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
