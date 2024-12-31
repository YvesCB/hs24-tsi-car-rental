import { useState } from "react";
import SearchBar from "../components/searchbar/SearchBar";
import { Car } from "../types";
import CarsArea from "../components/carsarea/CarsArea";

const Home = () => {
  const [carList, setCarList] = useState<Car[]>();
  const carsData = [
    { id: 1, name: "Rover X1", year: "2022", price: 25000, type: "Rover" },
    { id: 2, name: "Drone Z4", year: "2021", price: 15000, type: "Drone" },
    { id: 3, name: "Lander Y2", year: "2023", price: 30000, type: "Lander" },
    { id: 4, name: "Rover X2", year: "2023", price: 26000, type: "Rover" },
    { id: 5, name: "Drone Z5", year: "2022", price: 17000, type: "Drone" },
    { id: 6, name: "Lander Y3", year: "2024", price: 32000, type: "Lander" },
    // Add more cars as needed
  ];

  return (
    <div>
      <SearchBar />

      <CarsArea cars={carsData} />
    </div>
  );
};

export default Home;
