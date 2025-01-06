import { Link } from "react-router-dom";
import { Car } from "../../types";
import { imageFromType } from "../../utils";
import "./style.css";

type CarInfoProps = {
  car: Car;
}

const CarInfo = ({ car }: CarInfoProps) => {
  return (
    <div className="carinfo-container">
      <div className="thumbnail">
        <img src={`${imageFromType(car.type)}`} alt="Image of the vehicle" />
      </div>
      <div className="car-details">
        <h1>{car.name}</h1>
        <h3>{car.type.name}</h3>
        <p>{car.type.description}</p>
        <p><strong>Brand: </strong> {car.brand}</p>
        <p><strong>Gearbox: </strong> {car.automatic ? "Automatic" : "Manual"}</p>
        <p><strong>Year of Construction:</strong> {car.yearOfConstruction}</p>
        <p><strong>Price per Day:</strong> {car.pricePerDay} Credits</p>
        <Link to={`/car/${car.id}/booking`}><button>Book</button></Link>
      </div>
    </div>
  );
}

export default CarInfo;
