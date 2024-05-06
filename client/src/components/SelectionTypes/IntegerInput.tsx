import "../../styles/IntegerInput.css"; // Import the CSS file
import React, { useState } from "react";

/**
 * Component used in generation and account creation for numerical values
 */
interface IntegerInputProps {
  value: number;
  onChange: (value: number) => void;
  minValue?: number;
  maxValue?: number;
}

const IntegerInput: React.FC<IntegerInputProps> = ({
  value,
  onChange,
  minValue,
  maxValue,
}) => {
  const [inputValue, setInputValue] = useState<number>(value);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newValue = parseFloat(event.target.value);
    setInputValue(newValue);
    onChange(newValue);
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    const step = event.shiftKey
      ? 10
      : event.altKey
      ? 0.1
      : event.ctrlKey
      ? 100
      : 1;

    if (event.key === "ArrowUp") {
      const newValue = Math.min(
        maxValue !== undefined ? maxValue : Number.MAX_SAFE_INTEGER,
        inputValue + step
      );
      setInputValue(newValue);
      onChange(newValue);
      event.preventDefault();
    } else if (event.key === "ArrowDown") {
      const newValue = Math.max(
        minValue !== undefined ? minValue : Number.MIN_SAFE_INTEGER,
        inputValue - step
      );
      setInputValue(newValue);
      onChange(newValue);
      event.preventDefault();
    }
  };

  return (
    <input
      aria-label="integer-input"
      type="number"
      value={inputValue}
      onChange={handleInputChange}
      onKeyDown={handleKeyDown}
      min={minValue}
      max={maxValue}
      className="integer-input" // Apply the CSS class here
    />
  );
};

export default IntegerInput;
