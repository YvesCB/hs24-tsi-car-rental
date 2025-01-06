import { Car } from "../../types";
import { Link, useLocation } from "react-router-dom";

import "./style.css";
import { imageFromType } from "../../utils";

type CardProps = {
  car: Car;
}

const Card = ({ car }: CardProps) => {
  const location = useLocation();

  const isAdmin = location.pathname.startsWith("/admin");

  return (
    <Link to={`${isAdmin ? "/admin" : ""}/car/${car.id}`} className="card-link">
      <div className={`card${car.active ? "" : " inactive"}`}>
        <div className="card-image">
          <img src={`${imageFromType(car.type)}`} alt="A placeholder image" />
        </div>
        <div className="card-details">
          <h3>{car.name}</h3>
          <p><strong>Brand: {car.brand}</strong></p>
          <p><strong>Type: {car.type.name}</strong></p>
          <p><strong>Year: {car.yearOfConstruction}</strong></p>
          <p><strong>Price: {car.pricePerDay}</strong></p>
        </div>
      </div>
    </Link>
  );
};

export default Card;
