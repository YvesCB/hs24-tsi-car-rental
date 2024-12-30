import { useState } from "react";
import "./style.css";

const SearchBar = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [type, setType] = useState("");
  const [minPrice, setMinPrice] = useState<number | "">("");
  const [maxPrice, setMaxPrice] = useState<number | "">("");
  const [yearRange, setYearRange] = useState<{ start: string; end: string }>({
    start: "",
    end: "",
  });

  const handleSearch = () => {
    // Handle search logic here
    console.log({
      searchTerm,
      type,
      minPrice,
      maxPrice,
      yearRange,
    });
  };
  return (
    <div className="search-bar">
      <div className="search-input">
        <input
          type="text"
          placeholder="Search for vehicles..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <button onClick={handleSearch}>Search</button>
      </div>
      <div className="filters">
        <div className="filter">
          <label htmlFor="type">Type</label>
          <select
            id="type"
            value={type}
            onChange={(e) => setType(e.target.value)}
          >
            <option value="">All</option>
            <option value="Rover">Rover</option>
            <option value="Drone">Drone</option>
            <option value="Lander">Lander</option>
          </select>
        </div>
        <div className="filter">
          <label htmlFor="minPrice">Min Price</label>
          <input
            type="number"
            id="minPrice"
            placeholder="0"
            value={minPrice}
            onChange={(e) => setMinPrice(Number(e.target.value) || "")}
          />
        </div>
        <div className="filter">
          <label htmlFor="maxPrice">Max Price</label>
          <input
            type="number"
            id="maxPrice"
            placeholder="10000"
            value={maxPrice}
            onChange={(e) => setMaxPrice(Number(e.target.value) || "")}
          />
        </div>
        <div className="filter">
          <label htmlFor="yearRange">Year Range</label>
          <div className="year-range">
            <input
              type="number"
              placeholder="Start Year"
              value={yearRange.start}
              onChange={(e) =>
                setYearRange({ ...yearRange, start: e.target.value })
              }
            />
            <span>to</span>
            <input
              type="number"
              placeholder="End Year"
              value={yearRange.end}
              onChange={(e) =>
                setYearRange({ ...yearRange, end: e.target.value })
              }
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default SearchBar;
