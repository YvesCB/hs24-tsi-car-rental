import { Outlet } from "react-router-dom";
import Header from "../header/Header";
import Footer from "../footer/Footer";
import "./style.css";

const Layout = () => {
  return (
    <div className="page-container">
      {/* Persistent navigation */}
      <header>
        <Header />
      </header>

      {/* Main content */}
      <main>
        <Outlet /> {/* Render the routed content here */}
      </main>

      {/* Persistent footer */}
      <footer>
        <Footer />
      </footer>
    </div>
  );
};

export default Layout;
