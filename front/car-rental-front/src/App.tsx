import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import Layout from './components/layout/Layout';
import Home from "./pages/Home";
import About from './pages/About';
import Admin from './pages/Admin';
import NotFound from './pages/NotFound';
import Privacy from './pages/Privacy';
import Terms from './pages/Terms';
import Contact from './pages/Contact';

function App() {

  return (
    <Router>
      <Routes>
        { /* Layout wraps everything under "/" */}
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />}></Route>
          <Route path="admin" element={<Admin />}></Route>
          <Route path="about" element={<About />}></Route>
          <Route path="privacy" element={<Privacy />}></Route>
          <Route path="terms" element={<Terms />}></Route>
          <Route path="contact" element={<Contact />}></Route>
        </Route>
        { /* Fallback for undifined routes */}
        <Route path="*" element={<NotFound />}></Route>
      </Routes>
    </Router>
  );
}

export default App
