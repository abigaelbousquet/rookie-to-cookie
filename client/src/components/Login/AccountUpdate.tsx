import Slider from "rc-slider";
import "rc-slider/assets/index.css";
import "./../../styles/login.css";
import Select from "react-select";
import "../../styles/AccountUpdate.css";

import React, { useEffect, useState } from "react";
import { ControlledInput } from "../SelectionTypes/ControlledInput";
import Creatable from "react-select/creatable";
import { addUser, getUser } from "../../utils/api";
import { diets, intoleranceOptions } from "../../data/Spoonacular";
import { profileProps } from "./AccountCreation";
import IntegerInput from "../SelectionTypes/IntegerInput";
/**
 * This component is very similar to the account creation one, but does not have a name field and pops up over the profile page.
 */
interface acctProps {
  onClose: () => void;
}

export const AccountUpdate: React.FC<acctProps> = ({ onClose }) => {
  const [exp, setExp] = useState("1");
  const [diet, setDiet] = useState("");
  const [allergen, setAllergen] = useState<{ label: string; value: string }[]>(
    []
  );
  const [fam_size, setFam_Size] = useState(1);
  const heightMarks = {
    1: "novice",
    2: "beginner",
    3: "moderate",
    4: "master",
  };
  const [userName, setUserName] = useState("");

  useEffect(() => {
    const fetchUserName = async () => {
      try {
        const name = await getUserName(); // Retrieve the user's name asynchronously
        setUserName(name); // Set the retrieved name in the state variable
      } catch (error) {
        console.error("Error fetching user's name:", error);
      }
    };

    fetchUserName(); // Call the function to fetch the user's name
  }, []);

  const getUserName = async () => {
    try {
      const user = await getUser();
      const myUser = user["User"];
      const userData = myUser[0];
      return userData.name;
    } catch (error) {
      console.error("Error retrieving user name:", error);
      return null; // or handle the error accordingly
    }
  };

  // Updated handleSubmit function to be asynchronous
  const handleSubmit = async (props: profileProps, onClose: () => void) => {
    onClose();
    try {
      // Get the user's name asynchronously
      const userName = await getUserName();
      if (props.exp === undefined || props.fam_size === undefined) {
        alert("Please enter experience and family size.");
      } else {
        await addUser({
          ...props,
          name: userName, // Assign the retrieved name to the props
        });

        console.log(
          `?name=${userName}&exp=${props.exp}&diet=${props.diet}&fam-size=${props.fam_size}&intolerances=${props.intolerances}`
        );
      }
    } catch (error) {
      alert(error);
    }
  };

  return (
    <div className="popup" aria-label="update-account-popup">
      <div className="popup-inner">
        <div className="acct-inmost">
          <button className="close-button" onClick={onClose}>
            X
          </button>
          <div className="popup-header">
            <div id="account-header">
              <h2>Update Account</h2>
            </div>
          </div>
          <div className="form" aria-label="account-update-form">
            <div className="exp-slider">
              <legend>Cooking Experience:</legend>
              <Slider
                ariaLabelForHandle={"experience-selector"}
                defaultValue={1}
                min={1}
                max={4}
                onChange={(e) => setExp(e.toString())}
                marks={heightMarks}
              />
            </div>
            <div className="acct-elt" aria-label="diet-selector">
              <legend>Diet:</legend>
              <div className="selector">
                <Select
                  aria-label="diet-drop-down"
                  options={diets}
                  onChange={(opt) => setDiet(opt.value)}
                  defaultValue={"None"}
                />
              </div>
            </div>
            <div className="acct-elt" aria-label="intolerances-selector">
              <legend>Intolerances:</legend>
              <div className="selector">
                <Creatable
                  aria-label="intolerances-input"
                  options={intoleranceOptions}
                  isMulti
                  onChange={(opt) => {
                    console.log(allergen);
                    setAllergen(opt);
                  }}
                />
              </div>
            </div>
            <div className="acct-elt" aria-label="family-size">
              <legend>Family Size:</legend>
              <IntegerInput
                value={fam_size}
                onChange={(num) => setFam_Size(num)}
              ></IntegerInput>
            </div>
            <button
              aria-label="update-button"
              className="butt"
              onClick={() => {
                handleSubmit(
                  {
                    name: userName,
                    exp: exp,
                    diet: diet,
                    fam_size: fam_size.toString(),
                    intolerances: allergen.map((val) => val.value),
                  },
                  onClose
                );
                alert(
                  "Successfully updated account. Sign out then in again to see changes."
                );
              }}
              type="submit"
            >
              Update
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
