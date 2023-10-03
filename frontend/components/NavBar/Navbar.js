import React from "react";
import "../Styles/Navbar.css";
import { useNavigate, useLocation } from "react-router-dom";

const Navbar = (props) => {
  const Navigate = useNavigate();
  const location = useLocation();

  const logoutsession = () => {
    localStorage.removeItem("loginData");
    Navigate("/");
  };

  const handleButtonClick = () => {
    Navigate("/ChangePassword");
  };

  const handleProfileClick = () => {
    Navigate("/Profile");
  };

  return (
    <nav>
      <div>
        <h2>Grievance Management</h2>
      </div>
      <div>
        <ul>
          <li
            onClick={handleProfileClick}
            className={`${props.disableValue ? "disabled" : ""} ${location.pathname === "/Profile" ? "active" : ""}`}
          >
            Profile
          </li>
          <li
            onClick={handleButtonClick}
            className={`${props.disableValue ? "disabled" : ""} ${location.pathname === "/ChangePassword" ? "active" : ""}`}
          >
            Change Password
          </li>
          <li
            onClick={logoutsession}
            className={props.disableValue ? "disabled" : ""}
          >
            Logout
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
