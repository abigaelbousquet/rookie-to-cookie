import {useState } from "react";
import LoginLogout from "./LoginLogout";
import React from "react";
import { AccountCreation } from "./AccountCreation";
import Master from "../Pages/Master";

/**
 * This component controls the authentication of the webapp
 * 0 = logged out
 * 1 = signed in
 * 2 = creating new account
 * @returns Correct screen based on login status 
 * 
 */
const AuthRoute = () => {
  const [authing, setAuthing] = useState(0);

  return (
    <>
      <div className="App-header">
        <h2>Rookie To Cookie</h2>
        <div className="logo"></div>
      </div>
      {authing === 1 ? <Master /> : null}
      {authing === 2 ? <AccountCreation setAuthing={setAuthing} /> : null}
      <LoginLogout authing={authing} setAuthing={setAuthing} />
    </>
  );
};
export default AuthRoute;
