// AccountCreationPopup.tsx
import Slider from "rc-slider";
import "rc-slider/assets/index.css";
import Select from "react-select";
import React, { Dispatch, SetStateAction, useState } from "react";
import { ControlledInput } from "./ControlledInput";
import Creatable from "react-select/creatable";

export function AccountCreation() {
  const [name, setName] = useState("");
  const [exp, setExp] = useState("");

  const heightMarks = {
    1: "novice",
    2: "beginner",
    3: "moderate",
    4: "master",
  };
  const diets = [
    { label: "Vegetarian", value: "Vegetarian" },
    { label: "Vegan", value: "Vegan" },
    { label: "Gluten-Free", value: "Gluten-Free" },
    { label: "Dairy-Free", value: "Dairy-Free" },
    { label: "Pescatarian", value: "Pescatarian" },
    { label: "None", value: "None" },
  ];
  let allergies = [
    { label: "Shellfish", value: "Shellfish" },
    { label: "Egg", value: "Egg" },
    { label: "Peanut", value: "Peanut" },
    { label: "Nut", value: "Nut" },
    { label: "Soy", value: "Soy" },
    { label: "Sesame", value: "Sesame" },
  ];
  const handleSubmit = (props: string[]) => {
    // Handle form submission here, e.g., send data to server
    console.log("Form submitted:", props);
  };

  return (
    <div className="popup">
      <div className="popup-inner">
        <div className="popup-inmost">
          <div className="popup-header">
            <div id="account-header">
              <h2>Create Account</h2>
            </div>
            <button className="x-button">X</button>
          </div>
          <div className="form">
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
            <div>
              <legend>Cooking Experience:</legend>
              <Slider
                defaultValue={1}
                min={1}
                max={4}
                onChange={(e) => setExp(e.toString())}
                marks={heightMarks}
              />
            </div>
            <div>
              <legend>Diet:</legend>
              <Select
                options={diets}
                isMulti
                onChange={(opt) => console.log(opt)}
              />
            </div>
            <div>
              <legend>Allergies & Foods to Exclude:</legend>
              <Creatable
                options={allergies}
                isMulti
                onChange={(opt, meta) => console.log(opt, meta)}
              />
            </div>

            <button
              className="butt"
              onClick={() => handleSubmit([name, exp])}
              type="submit"
            >
              Create Account
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
