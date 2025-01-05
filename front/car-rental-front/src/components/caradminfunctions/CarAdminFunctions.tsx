import { useNavigate } from "react-router-dom";
import { deleteCar } from "../../api";
import "./style.css";

type CarAdminFunctionsProps = {
  carId: number;
}

const CarAdminFunctions = ({ carId }: CarAdminFunctionsProps) => {
  const navigate = useNavigate();

  const handleDelete = () => {
    if (confirm("Delete this car?")) {
      deleteCar(carId)
        .then(() => {
          alert(`Car with ID: ${carId} deleted.`);
          navigate("/admin");
        })
        .catch((err) => {
          alert(err.message);
        })
    }
  }

  return (
    <div className="car-admin-functions">
      <button>Edit Car</button>
      <button onClick={handleDelete}>Delete Car</button>
      <button>Make Car unavailable</button>
    </div>
  );
}

export default CarAdminFunctions;
