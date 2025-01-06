import { useEffect, useState } from "react";
import NewUpdateCar from "../components/newupdatecar/NewUpdateCar";
import { Car, UpsertCar } from "../types";
import { createCar, findCarById, updateCar } from "../api";
import { useNavigate, useParams } from "react-router-dom";
import { invariant } from "../utils";

const EditCar = () => {
  const [car, setCar] = useState<Car | null>(null);

  const [carUpdate, setCarUpdate] = useState<UpsertCar>(
    {
      name: "",
      brand: "",
      yearOfConstruction: 0,
      automatic: true,
      pricePerDay: 0,
      active: true,
      type: { id: 0 },
    }
  )

  const { carId } = useParams();
  const navigate = useNavigate()

  invariant(carId);

  useEffect(() => {
    findCarById(parseInt(carId))
      .then((car: Car) => {
        setCar(car);
        setCarUpdate({
          name: car.name,
          brand: car.brand,
          yearOfConstruction: car.yearOfConstruction,
          automatic: car.automatic,
          pricePerDay: car.pricePerDay,
          active: car.active,
          type: { id: car.type.id },
        });
      })
      .catch((err) => {
        alert(err);
      });
  }, [])

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (confirm("Edit this vehicle?")) {
      updateCar(carUpdate, parseInt(carId))
        .then((updatedCar: Car) => {
          alert(`Updated car with id: ${updatedCar.id}`);
          setCar(car);
        })
        .catch((err) => alert(err.message));

      if (carId) {
        navigate("/admin/car/" + carId);
      }
    }
  }

  return (
    <div className="update-car">
      <h1>Update a vehicle</h1>
      <NewUpdateCar car={carUpdate} setCar={setCarUpdate} handleSubmit={handleSubmit} />
    </div>
  );
}

export default EditCar;
