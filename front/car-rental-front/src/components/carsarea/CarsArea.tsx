import { Car } from "../../types";
import Card from "../card/Card";

import "./style.css";

type CarsAreaProps = {
  cars: Car[] | undefined;
}

const CarsArea = ({ cars }: CarsAreaProps) => {

  return (
    <div className="cars-area">
      {cars && cars.map((car) => {
        return <Card key={car.id} car={car} />;
      })}
    </div>
  );
};

export default CarsArea;
