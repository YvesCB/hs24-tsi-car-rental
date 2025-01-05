import "./style.css";
import { CarType, Query } from "../../types";
import { useEffect, useState } from "react";
import { findAllTypes } from "../../api";

type SearchBarProps = {
  query: Query;
  setQuery: React.Dispatch<React.SetStateAction<Query>>;
  setError: React.Dispatch<React.SetStateAction<string | null>>;
  textFilter: string;
  setTextFilter: React.Dispatch<React.SetStateAction<string>>;
  handleSearch: () => void;
}

const SearchBar = ({ query, setQuery, setError, textFilter, setTextFilter, handleSearch }: SearchBarProps) => {
  const [types, setTypes] = useState<CarType[]>();

  useEffect(() => {
    findAllTypes()
      .then((carTypes: CarType[]) => {
        setTypes(carTypes);
        setError(null);
      })
      .catch((err) => {
        console.log(err);
        setError(err.message);
      });
  }, []);

  const handleChange = (key: keyof Query, value: any) => {
    setQuery((prev) => ({ ...prev, [key]: value !== "" ? value : null }));
  };

  const handleDateChange = (key: "start" | "end", value: string) => {
    const dateValue = value ? new Date(value) : null;
    handleChange(key, dateValue);
  };

  return (
    <div className="input-box">
      <div className="search-input">
        <input
          type="text"
          placeholder="Search for vehicles..."
          value={textFilter}
          onChange={(e) => setTextFilter(e.target.value)}
        />
        <button onClick={handleSearch}>Search</button>
      </div>
      <div className="filters">
        <div className="input-with-label">
          <label htmlFor="type">Type</label>
          <select
            id="type"
            value={query.typeName ?? ""}
            onChange={(e) => handleChange("typeName", e.target.value ?? null)}
          >
            <option value="">All</option>
            {types && types.map((type) => {
              return (<option key={type.id} value={type.name}>{type.name}</option>);
            })}
          </select>
        </div>
        <div className="input-with-label">
          <label htmlFor="minPrice">Min Price</label>
          <input
            type="number"
            id="minPrice"
            placeholder="0"
            value={query.minPrice ?? ""}
            onChange={(e) => handleChange("minPrice", e.target.value ? parseInt(e.target.value) : null)}
          />
        </div>
        <div className="input-with-label">
          <label htmlFor="maxPrice">Max Price</label>
          <input
            type="number"
            id="maxPrice"
            placeholder="10000"
            value={query.maxPrice ?? ""}
            onChange={(e) => handleChange("maxPrice", e.target.value ? parseInt(e.target.value) : null)}
          />
        </div>
        <div className="filter">
          <label htmlFor="time-range">Available</label>
          <div className="time-range">
            <input
              type="date"
              value={query.start ? query.start.toISOString().split("T")[0] : ""}
              onChange={(e) => handleDateChange("start", e.target.value)}
            />
            <span> to </span>
            <input
              type="date"
              placeholder="End date"
              value={query.end ? query.end.toISOString().split("T")[0] : ""}
              onChange={(e) => handleDateChange("end", e.target.value)}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default SearchBar;
