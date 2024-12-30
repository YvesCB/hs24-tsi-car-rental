import { Car } from "../../types";

import "./style.css";

type CardProps = {
  car: Car;
}

const Card = ({ car }: CardProps) => {
  return (
    <div className="card">
      <div className="card-image">
        <img src="/placeholder.jpg" alt="A placeholder image" />
      </div>
      <div className="card-details">
        <h3>{car.name}</h3>
        <p><strong>Type: {car.type}</strong></p>
        <p><strong>Year: {car.year}</strong></p>
        <p><strong>Price: {car.price}</strong></p>
      </div>
    </div>
  );
};

export default Card;
