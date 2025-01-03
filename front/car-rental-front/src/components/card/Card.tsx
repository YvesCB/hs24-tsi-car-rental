import { Car } from "../../types";
import { Link } from "react-router-dom";

import "./style.css";

type CardProps = {
  car: Car;
}

const Card = ({ car }: CardProps) => {
  return (
    <Link to={`/car/${car.id}`} className="card-link">
      <div className="card">
        <div className="card-image">
          <img src="/placeholder.jpg" alt="A placeholder image" />
        </div>
        <div className="card-details">
          <h3>{car.name}</h3>
          <p><strong>Type: {car.type.name}</strong></p>
          <p><strong>Year: {car.yearOfConstruction}</strong></p>
          <p><strong>Price: {car.pricePerDay}</strong></p>
        </div>
      </div>
    </Link>
  );
};

export default Card;
