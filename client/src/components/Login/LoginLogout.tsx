import React, { useState } from "react";
import {
  getAuth,
  GoogleAuthProvider,
  signInWithEmailAndPassword,
  signInWithPopup,
} from "firebase/auth";
import { AccountCreation } from "./AccountCreation";

export interface ILoginPageProps {
  authing: boolean;
  setAuthing: React.Dispatch<React.SetStateAction<boolean>>;
}

const Login: React.FunctionComponent<ILoginPageProps> = (props) => {
  const auth = getAuth();
  const [error, setError] = useState("");

  function createAccount() {
    signInWithGoogle();
  }

  const signInWithGoogle = async () => {
    try {
      const response = await signInWithPopup(auth, new GoogleAuthProvider());
      const userEmail = response.user.email || "";

      // Check if the email ends with the allowed domain
      if (userEmail.endsWith("@brown.edu")) {
        console.log(response.user.uid);
        props.setAuthing(true);
      } else {
        // User is not allowed, sign them out and show a message
        await auth.signOut();
        setError("User not allowed. Signed out.");
        console.log("User not allowed. Signed out.");
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="login-box">
      <h1>Login Page</h1>
      <button
        className="google-login-button"
        onClick={() => createAccount()}
        disabled={props.authing}
        aria-label="Login"
      >
        Create account
      </button>
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
