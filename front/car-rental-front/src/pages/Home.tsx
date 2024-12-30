import { useState } from "react";
import SearchBar from "../components/searchbar/SearchBar";
import { Car } from "../types";
import CarsArea from "../components/carsarea/CarsArea";

const Home = () => {
  const [carList, setCarList] = useState<Car[]>();
  const carsData: Car[] = [
    { id: 1, name: "Rover X1", year: "2022", price: 25000, type: "Rover" },
    { id: 2, name: "Drone Z4", year: "2021", price: 15000, type: "Lander" },
    { id: 3, name: "Lander Y2", year: "2023", price: 30000, type: "Drone" }
  ];

  return (
    <div>
      <SearchBar />

      <CarsArea cars={carsData} />
    </div>
  );
};

export default Home;
