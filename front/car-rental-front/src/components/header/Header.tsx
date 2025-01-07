import { Link, useLocation } from "react-router-dom";
import "./style.css";

const Header = () => {
  const location = useLocation();

  const isAdmin = location.pathname.startsWith("/admin");

  return (
    <div className="container">
      <div className="logo">
        <p>Cars for Mars</p>
      </div>
      <nav>
        <ul>
          <li><Link to={isAdmin ? "/admin" : "/"}>Home</Link></li>
          <li><Link to={isAdmin ? "/admin/about" : "/about"}>About</Link></li>
        </ul>
      </nav>
      <div className="admin-mode-container">
        {isAdmin ?
          <a href={`${location.pathname.replace("/admin", "")}`}><button>Normal mode</button></a> :
          <a href={`/admin${location.pathname}`}><button>Admin mode</button></a>
        }
      </div>
    </div>
  );
}

export default Header;
