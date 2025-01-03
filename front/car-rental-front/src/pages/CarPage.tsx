import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Car } from "../types";
import CarInfo from "../components/carinfo/CarInfo";

const CarPage = () => {
  const { carId } = useParams();
  const [car, setCar] = useState<Car | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetch(`http://localhost:8080/api/cars/${carId}`)
      .then((response) => {
        if (response.status === 404) {
          throw new Error("Car not found");
        }
        return response.json();
      })
      .then((car: Car) => {
        setCar(car);
        setError(null); // Clear any previous errors
      })
      .catch((err) => {
        console.log(err);
        setError(err.message);
      });
  }, [carId]);

  if (error) {
    return (
      <div>
        <h1>Error</h1>
        <p>{error}</p>
      </div>
    );
  }
  if (!car) {
    return <p>Loading...</p>;
  }
  return (
    <CarInfo car={car} />
  );
}

export default CarPage;
