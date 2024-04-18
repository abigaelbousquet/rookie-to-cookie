import React from "react";
import { Dispatch, SetStateAction } from "react";

/**
 * An interface to store string value, setValue, and label string
 */
interface ControlledInputProps {
  value: string;
  setValue: Dispatch<SetStateAction<string>>;
  ariaLabel: string;
  placeholder: string;
  styleID: string;
}

/**
 * A function to represent the frontend of the command input box,
 * where users can enter commands
 */
export function ControlledInput({
  value,
  setValue,
  ariaLabel,
  placeholder,
  styleID,
}: ControlledInputProps) {
  return (
    <input
      type="text"
      className={styleID}
      id="input-box"
      value={value}
      placeholder={placeholder}
      onChange={(ev) => setValue(ev.target.value)}
      aria-label={ariaLabel}
    ></input>
  );
}
