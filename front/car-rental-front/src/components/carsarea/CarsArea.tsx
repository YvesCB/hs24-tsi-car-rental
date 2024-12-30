import { Car } from "../../types";
import Card from "../card/Card";

import "./style.css";

type CarsAreaProps = {
  cars: Car[];
}

const CarsArea = ({ cars }: CarsAreaProps) => {

  return (
    <div className="cars-area">
      {cars.map((car) => {
        return <Card key={car.id} car={car} />;
      })}
    </div>
  );
};

export default CarsArea;
