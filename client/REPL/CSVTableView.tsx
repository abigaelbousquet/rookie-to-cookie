import React from "react";
import "../../styles/main.css";

/**
 * The properties of a CSV Table View componenent.
 */
interface CSVTableViewProps {
  called_command: string;
  csvArray: string[][];
}

/**
 * A function that returns a table representing the CSV.
 * @param props The properties of the table.
 * @returns A JSX Element for the table.
 */
export function CSVTableView(props: CSVTableViewProps) {
  /**
   * A method for handling the table's rendering.
   * @returns A JSX element for the formatted table.
   */
  function handleTable(): JSX.Element {
    return (
      <table>
        <tbody>
          {props.csvArray.map((row) => {
            return (
              <tr>
                {row.map((value) => {
                  return <td>{value}</td>;
                })}
              </tr>
            );
          })}
        </tbody>
      </table>
    );
  }

  /**
   * The return method for rending.
   */
  return handleTable();
}
