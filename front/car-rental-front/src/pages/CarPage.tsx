import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import { Car } from "../types";
import CarInfo from "../components/carinfo/CarInfo";
import CarAdminFunctions from "../components/caradminfunctions/CarAdminFunctions";
import { findCarById } from "../api";
import { invariant } from "../utils";

const CarPage = () => {
  const location = useLocation();

  const isAdmin = location.pathname.startsWith("/admin");

  const { carId } = useParams();
  const [car, setCar] = useState<Car | null>(null);
  const [error, setError] = useState<string | null>(null);

  invariant(carId);

  useEffect(() => {
    findCarById(parseInt(carId))
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
    <div>

      {
        isAdmin ?
          <CarAdminFunctions carId={parseInt(carId)} /> :
          null
      }
      <CarInfo car={car} />
    </div>
  );
}

export default CarPage;
