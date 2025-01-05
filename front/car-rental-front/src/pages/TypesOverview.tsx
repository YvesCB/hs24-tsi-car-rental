import { useEffect, useState } from "react";
import CreateCarType from "../components/createcartype/CreateCarType";
import { findAllTypes } from "../api";
import { CarType } from "../types";
import ExistingTypes from "../components/existingtypes/ExistingTypes";

const TypesOverview = () => {
  const [types, setTypes] = useState<CarType[]>();

  useEffect(() => {
    findAllTypes()
      .then((carTypes: CarType[]) => {
        setTypes(carTypes);
      })
      .catch((err) => {
        alert(err.message);
      });
  }, [types]);

  return (
    <div className="new-car">
      <h1>Create a new vehicle type</h1>
      <CreateCarType />
      <h1>Administer existing types</h1>
      {types &&
        <ExistingTypes types={types} setTypes={setTypes} />
      }
    </div>
  );
}

export default TypesOverview;
