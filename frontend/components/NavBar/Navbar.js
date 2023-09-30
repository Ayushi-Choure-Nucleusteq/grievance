import React from "react";
import "../Styles/Navbar.css";
import { useNavigate } from "react-router-dom";

const Navbar = (props) => {
  const Navigate = useNavigate();
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
            onClick={() => handleProfileClick("/Profile")}
            className={props.disableValue ? "disabled" : ""}
          >
            Profile
          </li>
          <li
            onClick={() => handleButtonClick("/ChangePassword")}
            className={props.disableValue ? "disabled" : ""}
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
