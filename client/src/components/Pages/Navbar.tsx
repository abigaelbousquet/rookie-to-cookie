import React from "react";
import { Link } from "react-router-dom";
import "../../styles/Navbar.css"; // Import CSS file for styling

interface NavbarProps {
  handleLogout: () => void; // Logout handler function
}

const Navbar: React.FC<NavbarProps> = ({ handleLogout }) => {
  return (
    <nav aria-label="navigation-bar" className="navbar">
      <ul aria-label="navigation-options" className="nav-list">
        <li aria-label="home-link" className="nav-item">
          <Link to="/" className="nav-link">
            Home
          </Link>
        </li>
        <li aria-label="calendar-link" className="nav-item">
          <Link to="/calendar" className="nav-link">
            Calendar
          </Link>
        </li>
        <li aria-label="about-link" className="nav-item">
          <Link to="/about" className="nav-link">
            About
          </Link>
        </li>
        <li aria-label="profile-link" className="nav-item">
          <Link to="/profile" className="nav-link">
            Profile
          </Link>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
