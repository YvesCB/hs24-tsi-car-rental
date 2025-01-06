import { useParams } from "react-router-dom";
import { invariant } from "../utils";
import { useEffect, useState } from "react";
import { Car } from "../types";
import { findCarById } from "../api";
import CarBookingOptions from "../components/carbookingoptions/CarBookingOptions";

const CarBooking = () => {
  const { carId } = useParams();
  const [car, setCar] = useState<Car | null>(null);

  invariant(carId);

  useEffect(() => {
    findCarById(parseInt(carId))
      .then((car: Car) => {
        setCar(car);
      })
      .catch((err) => {
        alert(err.message);
      });
  }, [carId]);

  if (car) {
    return (
      <div className="car-booking">
        <CarBookingOptions car={car} />
      </div>
    );
  } else {
    return (
      <div className="car-booking">
        <h3>Loading...</h3>
      </div>
    );
  }

}

export default CarBooking;
