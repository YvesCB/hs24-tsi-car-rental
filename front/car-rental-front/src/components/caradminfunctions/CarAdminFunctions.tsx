import { Link, useNavigate } from "react-router-dom";
import { activateCar, deactivateCar, deleteCar } from "../../api";
import "./style.css";

type CarAdminFunctionsProps = {
  carId: number;
  carActive: boolean;
  setCarActive: React.Dispatch<React.SetStateAction<boolean>>;
}

const CarAdminFunctions = ({ carId, carActive, setCarActive }: CarAdminFunctionsProps) => {
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

  const handleMakeUnavailable = () => {
    if (confirm("Make this car unavailable?")) {
      deactivateCar(carId)
        .then(() => {
          alert(`Car with ID: ${carId} deactivated.`);
          setCarActive(false);
        })
        .catch((err) => {
          alert(err.message);
        })
    }
  }

  const handleMakeAvailable = () => {
    if (confirm("Make this car available?")) {
      activateCar(carId)
        .then(() => {
          alert(`Car with ID: ${carId} available.`);
          setCarActive(true);
        })
        .catch((err) => {
          alert(err.message);
        })
    }
  }

  return (
    <div className="car-admin-functions">
      <Link to={`/admin/editcar/${carId}`}><button>Edit Car</button></Link>
      <button className="red-btn" onClick={handleDelete}>Delete Car</button>
      {carActive ?
        <button className="red-btn" onClick={handleMakeUnavailable}>Make Car unavailable</button> :
        <button onClick={handleMakeAvailable}>Make Car available</button>
      }
    </div>
  );
}

export default CarAdminFunctions;
