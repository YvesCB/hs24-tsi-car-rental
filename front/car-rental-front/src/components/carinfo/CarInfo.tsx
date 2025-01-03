import { Car } from "../../types";
import "./style.css";

type CarInfoProps = {
  car: Car;
}

const CarInfo = ({ car }: CarInfoProps) => {
  return (
    <div className="carinfo-container">
      <div className="thumbnail">
        <img src="/placeholder.jpg" alt="A placeholder image" />
      </div>
      <div className="car-details">
        <h1>{car.name}</h1>
        <p>{car.type.name}: {car.type.description}</p>
        <p><strong>Year of Construction:</strong> {car.yearOfConstruction}</p>
        <p><strong>Price per Day:</strong> {car.pricePerDay} Credits</p>
        <button>Book</button>
      </div>
    </div>
  );
}

export default CarInfo;
