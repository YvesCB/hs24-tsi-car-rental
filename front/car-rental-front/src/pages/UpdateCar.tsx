import { useParams } from "react-router-dom";

const UpdateCar = () => {
  const { carId } = useParams();

  return (
    <div className="update-car">
      <p>update-car</p>
    </div>
  );
}

export default UpdateCar;
