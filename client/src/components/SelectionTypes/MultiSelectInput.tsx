import React, { KeyboardEventHandler } from "react";
import CreatableSelect from "react-select/creatable";

interface Option {
  readonly label: string;
  readonly value: string;
}

interface MultiSelectInputProps {
  onSelectChange: (selectedOptions: Option[]) => void;
}

const createOption = (label: string) => ({
  label,
  value: label,
});

const MultiSelectInput: React.FC<MultiSelectInputProps> = ({
  onSelectChange,
}) => {
  const [inputValue, setInputValue] = React.useState("");
  const [value, setValue] = React.useState<readonly Option[]>([]);

  const handleKeyDown: KeyboardEventHandler = (event) => {
    if (!inputValue) return;
    switch (event.key) {
      case "Enter":
      case "Tab":
        const newOption = createOption(inputValue);
        setValue((prev) => [...prev, newOption]);
        onSelectChange([...value, newOption]);
        setInputValue("");
        event.preventDefault();
    }
  };

  return (
    <CreatableSelect
      inputValue={inputValue}
      isClearable
      isMulti
      menuIsOpen={false}
      onChange={(newValue) => {
        setValue(newValue || []);
        onSelectChange(newValue || []);
      }}
      onInputChange={(newValue) => setInputValue(newValue)}
      onKeyDown={handleKeyDown}
      placeholder="Type something and press enter..."
      value={value}
    />
  );
};

export default MultiSelectInput;
