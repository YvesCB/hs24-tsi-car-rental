import { Link } from "react-router-dom";
import "./style.css";

const HomeAdminFunctions = () => {
  return (
    <div className="home-admin-functions">
      <Link to="/admin/newcar"><button>New Car</button></Link>
      <Link to="/admin/types"><button>Vehicle Type Overview</button></Link>
      <button>Booking Overview</button>
    </div>
  );
}

export default HomeAdminFunctions;
