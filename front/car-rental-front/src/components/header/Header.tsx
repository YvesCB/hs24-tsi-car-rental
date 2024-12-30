import { Link } from "react-router-dom";
import "./style.css";

const Header = () => {
  return (
    <div className="container">
      <div className="logo">
        <p>Cars for Mars</p>
      </div>
      <nav>
        <ul>
          <li><Link to="/">Home</Link></li>
          <li><Link to="/about">About</Link></li>
        </ul>
      </nav>
      <div className="admin-mode-container">
        <button>Admin mode</button>
      </div>
    </div>
  );
}

export default Header;
