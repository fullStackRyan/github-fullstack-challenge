import React, { useState } from "react";
import { MagnifyingGlassIcon } from "@heroicons/react/24/solid";

type SearchBoxProps = {
  onSubmit: (value: string) => void;
};

const SearchBox: React.FC<SearchBoxProps> = ({ onSubmit }) => {
  const [search, setSearch] = useState<string>("");
  const [isInputFocused, setInputFocused] = useState<boolean>(false);

  const handleSearchChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ): void => {
    setSearch(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent): void => {
    event.preventDefault();
    onSubmit(search);
  };

  return (
    <div className="flex justify-center mt-10">
      <div className="max-w-3xl w-full">
        <form onSubmit={handleSubmit} className="flex items-center w-full px-4">
          <div className="relative flex-grow">
            <input
              type="text"
              value={search}
              onChange={handleSearchChange}
              onFocus={() => setInputFocused(true)}
              onBlur={() => setInputFocused(false)}
              className="drop-shadow-xl appearance-none border rounded-lg py-4 pl-10 pr-3 text-grey-darker leading-tight focus:outline-none focus:shadow-outline w-full"
              placeholder="Search for repos..."
            />
            <div className="absolute left-2 top-1/2 transform -translate-y-1/2">
              <MagnifyingGlassIcon className="h-6 w-6 text-gray-500" />
            </div>
            <p
              className={`absolute top-full left-1/2 transform -translate-x-1/2 mt-2 text-md text-gray-500 transition-all duration-300 ${
                isInputFocused ? "opacity-100" : "opacity-0"
              }`}
            >
              Press enter to search
            </p>
          </div>
        </form>
      </div>
    </div>
  );
};

export default SearchBox;
