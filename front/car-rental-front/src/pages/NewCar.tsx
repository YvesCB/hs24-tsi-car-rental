import { useState } from "react";
import NewUpdateCar from "../components/newupdatecar/NewUpdateCar";
import { UpsertCar } from "../types";

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

  return (
    <div className="new-car">
      <h1>Create a new vehicle</h1>
      <NewUpdateCar car={car} setCar={setCar} carId={null} />
    </div>
  );
}

export default NewCar;
