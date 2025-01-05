import { useEffect, useState } from "react";
import SearchBar from "../components/searchbar/SearchBar";
import { Car, Query } from "../types";
import CarsArea from "../components/carsarea/CarsArea";
import { buildQueryString, findCarsFiltered } from "../api";
import { useLocation } from "react-router-dom";
import HomeAdminFunctions from "../components/homeadminfunctions/HomeAdminFunctions";

const Home = () => {
  const location = useLocation();

  const isAdmin = location.pathname.startsWith("/admin");

  const [carList, setCarList] = useState<Car[]>();
  const [displayCarList, setDisplayCarList] = useState<Car[]>();
  const [query, setQuery] = useState<Query>({
    minPrice: null,
    maxPrice: null,
    brand: null,
    typeName: null,
    start: null,
    end: null,
  });
  const [error, setError] = useState<string | null>(null);
  const [textFilter, setTextFilter] = useState<string>("");

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const initialQuery: Query = {
      minPrice: urlParams.has("minPrice") ? parseInt(urlParams.get("minPrice")!) : null,
      maxPrice: urlParams.has("maxPrice") ? parseInt(urlParams.get("maxPrice")!) : null,
      brand: urlParams.has("brand") ? urlParams.get("brand") : null,
      typeName: urlParams.has("typeName") ? urlParams.get("typeName") : null,
      start: urlParams.has("start") ? new Date(urlParams.get("start")!) : null,
      end: urlParams.has("end") ? new Date(urlParams.get("end")!) : null,
    };
    setQuery(initialQuery);
    handleSearch();
  }, []);

  useEffect(() => {
    if (carList) {
      if (textFilter !== "") {
        const filteredCars = carList.filter((car) => car.name.toLowerCase().includes(textFilter.toLowerCase()));
        setDisplayCarList(filteredCars);
      } else {
        setDisplayCarList(carList);
      }
    }
  }, [textFilter]);

  useEffect(() => {
    const queryString = buildQueryString(query);
    const newUrl = `${window.location.pathname}${queryString}`;
    window.history.pushState({}, "", newUrl);
  }, [query])

  const handleSearch = () => {
    if ((query.start == null) != (query.end == null)) {
      alert("Please set a start and end date for availability search.");
    } else {
      findCarsFiltered(query)
        .then((cars: Car[]) => {
          setCarList(cars)
          setDisplayCarList(cars)
          setError(null); // Clear any previous errors
        })
        .catch((err) => {
          alert(err.message)
          setError(err.message);
        });
    }
  };

  if (error) {
    return (
      <div>
        <SearchBar query={query} setQuery={setQuery} setError={setError} handleSearch={handleSearch} textFilter={textFilter} setTextFilter={setTextFilter} />
        <h1>Error</h1>
        <p>{error}</p>
      </div>
    );
  }
  return (
    <div>
      {isAdmin ?
        <HomeAdminFunctions /> :
        null
      }

      <SearchBar query={query} setQuery={setQuery} setError={setError} handleSearch={handleSearch} textFilter={textFilter} setTextFilter={setTextFilter} />

      <CarsArea cars={displayCarList} />
    </div>
  );
};

export default Home;
