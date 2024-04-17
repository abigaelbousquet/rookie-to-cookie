import React from "react";
import { CSVTableView } from "./CSVTableView";
import { CommandResult } from "./REPL";
import "../../styles/main.css";

/**
 * The properties for REPL History.
 */
interface REPLHistoryProps {
  history: CommandResult[];
  isBrief: boolean;
}

/**
 * A function for the REPL History.
 * @param props The properties of the REPL History.
 * @returns A JSX element representing the REPL History.
 */
export function REPLHistory(props: REPLHistoryProps) {
  /**
   * A method that formats the result depending on if it's an array or a string.
   * @param result The command result.
   * @returns A JSX element for the result.
   */
  function formatResult(result: CommandResult): JSX.Element {
    if (Array.isArray(result.result)) {
      return (
        <CSVTableView
          called_command={result.command}
          csvArray={result.result}
        />
      );
    } else if (typeof result.result === "string") {
      return <p>{result.result}</p>;
    } else {
      return <p>Error getting result!</p>;
    }
  }

  /**
   * Handles the overall formatting of the response, depending on the
   * output mode.
   * @param result The command result.
   * @returns A JSX Element for the command result.
   */
  function handleFormat(result: CommandResult): JSX.Element {
    if (props.isBrief) {
      return formatResult(result);
    } else {
      return (
        <div className="result-box" aria-label="verbose-box">
          <p>
            <b>
              <u>Command</u>
            </b>
          </p>
          <p>{result.command}</p>
          <p>
            <b>
              <u>Output</u>
            </b>
          </p>
          {formatResult(result)}
        </div>
      );
    }
  }

  /**
   * The return method for the REPL History JSX element.
   */
  return (
    <div
      className="repl-history"
      aria-label="repl-history"
      id="repl-history"
      tabIndex={0}
    >
      {props.history.map((value, index) => {
        return (
          <div key={index} aria-label="repl-command">
            {handleFormat(value)}
          </div>
        );
      })}
    </div>
  );
}
