import { useCallback, useEffect, useState } from "react";
import "../../styles/main.css";
import { REPLHistory } from "./REPLHistory";
import { REPLInput } from "./REPLInput";
import React from "react";
import { CSVCommandCreator } from "../../commands/CSVCommandCreator";
import { BroadbandCommandCreator } from "../../commands/BroadbandCreator";
import { CommandRegistry } from "../../commands/CommandRegistry";

/**
 * An interface that represents the format that a command returns, including
 * its command name, as well as the result of calling the command.
 */
export interface CommandResult {
  command: string;
  result: string[][] | string | Promise<string | string[][]>;
}

/**
 * A function to share select variables between different classes using
 * state hooks
 *
 * The information that is being shared is:
 * history, which represents past frontend interaction with the command box
 * setHistory, which represents the state hook for history
 * mode, which represents the mode of the program
 * setMode, which represents the state hook for mode
 *
 * @returns
 */
export default function REPL() {
  const [history, setHistory] = useState<CommandResult[]>([]);
  // Whether or not the REPL is in brief mode.

  const [isBrief, setBrief] = useState<boolean>(true);
  // The registry of commands.
  const registry : CommandRegistry = new CommandRegistry;

  // A class that creates the commands related to CSV, adding them to the registry.
  const csvCommands : CSVCommandCreator = new CSVCommandCreator(registry);
  csvCommands.initalizeCommands();
  const broadbandCommands : BroadbandCommandCreator = new BroadbandCommandCreator(registry);
  broadbandCommands.initializeCommands();
  // Registers the "mode" command into the registry.
  registry.registerCommand("mode", () => {
    const newBrief = !isBrief;
    setBrief(newBrief);
    return newBrief ? "Current mode is: BRIEF" : "Current mode is: VERBOSE";
  });

  /**
   * A function to handle key press for user accessbility. If the user presses
   * shift while pressing either the up or down arrow key, they'll be able to
   * navigate the page without the use of a mousepad or keypress
   *
   * A user can also alter element focus using the enter button
   */
  const handleKeyPress = useCallback((event) => {
    if (event.shiftKey === true) {
      if (event.key === "ArrowUp" || event.key === "ArrowDown") {
        document.getElementById("command-input")?.blur();
        document.getElementById("repl-history")?.focus();
      } else if (event.key === "Enter") {
        document.getElementById("command-input")?.focus();
      }
    } else if (event.key == "Escape") {
      document.getElementById("sign-out")?.click();
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

  return (
    <div className="repl">
      <REPLHistory history={history} isBrief={isBrief}></REPLHistory>
      <hr></hr>
      <REPLInput
        history={history}
        setHistory={setHistory}
        registry={registry}
      />
    </div>
  );
}
