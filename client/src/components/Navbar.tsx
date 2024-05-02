import React from "react";
import { Link } from "react-router-dom";
import "../styles/Navbar.css"; // Import CSS file for styling

interface NavbarProps {
  handleLogout: () => void; // Logout handler function
}

const Navbar: React.FC<NavbarProps> = ({ handleLogout }) => {
  return (
    <nav className="navbar">
      <ul className="nav-list">
        <li className="nav-item">
          <Link to="/" className="nav-link">
            Home
          </Link>
        </li>
        <li className="nav-item">
          <Link to="/calendar" className="nav-link">
            Calendar
          </Link>
        </li>
        <li className="nav-item">
          <Link to="/about" className="nav-link">
            About
          </Link>
        </li>
        <li className="nav-item">
          <Link to="/profile" className="nav-link">
            Profile
          </Link>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
