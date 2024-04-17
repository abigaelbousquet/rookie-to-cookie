import React, { useCallback, useEffect } from "react";
import { getAuth, GoogleAuthProvider, signInWithPopup } from "firebase/auth";

export interface ILoginPageProps {
  authing: boolean;
  setAuthing: React.Dispatch<React.SetStateAction<boolean>>;
}

const Login: React.FunctionComponent<ILoginPageProps> = (props) => {
  const auth = getAuth();

  const handleKeyPress = useCallback((event) => {
    if (event.key == "Enter") {
      document.getElementById("sign-in")?.click();
    }
  }, []);

  /**
   * Adds an event listener upon handleKeyPress method
   */
  useEffect(() => {
    // attach the event listener
    document.addEventListener("keydown", handleKeyPress);

    // remove the event listener
    return () => {
      document.removeEventListener("keydown", handleKeyPress);
    };
  }, [handleKeyPress]);

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
        console.log("User not allowed. Signed out.");
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="login-box" aria-label="Sign in with google">
      <h1>Login Page</h1>
      <p>
        Click the login button to enter the application. To log in, use your
        @brown.edu email address.
      </p>
      <button
        aria-label="Sign in with google"
        className="google-login-button"
        id="sign-in"
        onClick={() => signInWithGoogle()}
        disabled={props.authing}
      >
        Sign in with Google
      </button>
    </div>
  );
};

const Logout: React.FunctionComponent<ILoginPageProps> = (props) => {
  return (
    <div aria-label="Sign out" className="logout-box">
      <button
        aria-label="Sign Out"
        className="SignOut"
        id="sign-out"
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
