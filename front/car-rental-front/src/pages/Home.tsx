import { useEffect, useState } from "react";
import SearchBar from "../components/searchbar/SearchBar";
import { Car } from "../types";
import CarsArea from "../components/carsarea/CarsArea";

const Home = () => {
  const [carList, setCarList] = useState<Car[]>();

  useEffect(() => {
    fetch("http://localhost:8080/api/cars")
      .then((data) => data.json())
      .then((cars: Car[]) => setCarList(cars))
      .catch((err) => console.log(err));
  }, []);

  return (
    <div>
      <SearchBar />

      <CarsArea cars={carList} />
    </div>
  );
};

export default Home;
