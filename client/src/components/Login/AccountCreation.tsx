// AccountCreationPopup.tsx
import React, { useState } from "react";
import { ControlledInput } from "./ControlledInput";

export function AccountCreation() {
  const [name, setName] = useState("");

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    // Handle form submission here, e.g., send data to server
    console.log("Form submitted:", name);
  };

  return (
    <div className="popup">
      <div className="popup-inner">
        <div className="popup-header">
          <div id="account-header">
            <h2>Create Account</h2>
          </div>
          <button className="x-button">X</button>
        </div>
        <form onSubmit={handleSubmit}>
          <div>
            <legend>Name:</legend>
            <ControlledInput
              value={name}
              setValue={setName}
              ariaLabel={"Name"}
              placeholder={"Nim Telson"}
              styleID={"input-box"}
            />
          </div>
          <button type="submit">Create Account</button>
        </form>
      </div>
    </div>
  );
}
