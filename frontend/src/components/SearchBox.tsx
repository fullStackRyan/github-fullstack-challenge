import React, { useState } from "react";

type SearchBoxProps = {
  onSubmit: (value: string) => void;
};

const SearchBox: React.FC<SearchBoxProps> = ({ onSubmit }) => {
  const [search, setSearch] = useState("");

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearch(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    onSubmit(search);
  };

  return (
    <div className="flex justify-center mt-10">
      <form onSubmit={handleSubmit} className="flex items-center">
        <input
          type="text"
          value={search}
          onChange={handleSearchChange}
          className="shadow appearance-none border rounded py-2 px-3 text-grey-darker leading-tight focus:outline-none focus:shadow-outline w-64"
          placeholder="Search..."
        />
        <button
          type="submit"
          className="ml-2 px-4 py-2 rounded bg-blue-500 text-white focus:outline-none hover:bg-blue-700"
        >
          Search
        </button>
      </form>
    </div>
  );
};

export default SearchBox;
