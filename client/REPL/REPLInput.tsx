import "../../styles/main.css";
import {
  Dispatch,
  SetStateAction,
  useCallback,
  useEffect,
  useState,
} from "react";
import React from "react";
import { CommandResult } from "./REPL";

/**
 * An interface to store the command history of the REPL,
 * a state hook to alter the history of the program's REPL commands,
 * a string mode to indicate the mode of the program,
 * and a state hook to change the mode of the program
 */
interface REPLInputProps {
  history: CommandResult[];
  setHistory: Dispatch<SetStateAction<CommandResult[]>>;
}

//A constant to represent the first argument of a user's command
export let commandStr: string = "";

/**
 * A function to handle the frontend interaction with the
 * command line box
 */
export function REPLInput(props: REPLInputProps) {
  const [commandString, setCommandString] = useState<string>("");

  //  /**
  //  * Uses the React useEffect to listen for and handle the user keypress
  //  * interaction
  //  */
  // const handleKeyPress = useCallback(
  //   (event) => {
  //     if (event.key === "Enter" && event.shiftKey === false) {
  //       handleSubmit();
  //     }
  //   },
  //   [commandString]
  // );

  // useEffect(() => {
  //   // attach the event listener
  //   document.addEventListener("keydown", handleKeyPress);

  //   // remove the event listener
  //   return () => {
  //     document.removeEventListener("keydown", handleKeyPress);
  //   };
  // }, [handleKeyPress]);

  // /**
  //  * Handles the submit functionality.
  //  */
  // function handleSubmit(): void {
  //   try {
  //     let parsedCommandString: string[] =
  //       commandString.match(/('.*?'|[^'\s]+)+(?=\s*|\s*$)/g) || [];
  //     parsedCommandString.forEach(
  //       (arg, index) => (parsedCommandString[index] = arg.replace(/[']+/g, ""))
  //     );

  //     let result : CommandResult = {command : commandString, 
  //       result : props.registry.executeCommand(parsedCommandString[0], parsedCommandString.slice(1))};
  //     if (typeof result.result === "string" || Array.isArray(result.result)) {
  //       props.setHistory([...props.history, result]);
  //     } else {
  //       result.result
  //         .then((fetchedResult) => {
  //           let returnedResult: CommandResult = {
  //             command: commandString,
  //             result: fetchedResult,
  //           };
  //           props.setHistory([...props.history, returnedResult]);
  //         })
  //         .catch((problem) => {
  //           let errorResult: CommandResult = {
  //             command: commandString,
  //             result: problem,
  //           };
  //           props.setHistory([...props.history, errorResult]);
  //         });
  //     }
  //   } catch {
  //     alert("Invalid command. Check documentation for availible commands");
  //   }
  //   setCommandString("");
  //   document.getElementById("command-input")?.focus();
  // }

  //Content to be displayed to the frontend
  return (
    <div className="repl-input">
      <fieldset>
        <legend>Enter a command:</legend>
        
      </fieldset>
      
    </div>
  );
}