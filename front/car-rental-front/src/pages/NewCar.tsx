import { useState } from "react";
import NewUpdateCar from "../components/newupdatecar/NewUpdateCar";
import { Car, UpsertCar } from "../types";
import { createCar } from "../api";
import { useNavigate } from "react-router-dom";

const NewCar = () => {
  const [car, setCar] = useState<UpsertCar>(
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

  const navigate = useNavigate()

  const carId = null;

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (confirm("Submit new vehicle?")) {
      createCar(car)
        .then((createdCar: Car) => {
          alert(`Created car with id: ${createdCar.id}`);
          setCar(
            {
              name: "",
              brand: "",
              yearOfConstruction: 0,
              automatic: true,
              pricePerDay: 0,
              active: true,
              type: { id: 0 },
            }
          );
        })
        .catch((err) => alert(err.message));

      if (carId) {
        navigate("/admin/car/" + carId);
      }
    }
  }

  return (
    <div className="new-car">
      <h1>Create a new vehicle</h1>
      <NewUpdateCar car={car} setCar={setCar} carId={null} handleSubmit={handleSubmit} />
    </div>
  );
}

export default NewCar;
