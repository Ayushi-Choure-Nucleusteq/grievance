import React, { useState, useEffect } from "react";
import "../Styles/Sidebar.css";
import { useNavigate, useLocation } from "react-router-dom";

const Sidebar = (props) => {
  const navigate = useNavigate();
  const [activeButton, setActiveButton] = useState("");

  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const responseData = storedData.responseData || {};
  const userRole = responseData.role;

  const location = useLocation();

  useEffect(() => {
    setActiveButton(() => {
      switch (location.pathname) {
        case "/NewMember":
          return "NewUser";
        case "/NewTicket":
          return "NewTicket";
        case "/NewDepartment":
          return "NewDepartment";
        case "/UserTable":
          return "AllUsers";
        case "/DepartmentTable":
          return "AllDepartments";
        case "/TicketTable":
          return "AllTicket";
        default:
          return "";
      }
    });
  }, [location.pathname]);

  const handleButtonClick = (path, buttonId) => {
    navigate(path);
    setActiveButton(buttonId);
  };

  return (
    <div className="sidebar-container">
      <div className="sidebar">
        {userRole === "ADMIN" && (
          <>
            <button
              disabled={props.disableValue}
              className={activeButton === "NewUser" ? "active" : ""}
              onClick={() => handleButtonClick("/NewMember", "NewUser")}
            >
              User ➕
            </button>
            <button
              disabled={props.disableValue}
              className={activeButton === "NewTicket" ? "active" : ""}
              onClick={() => handleButtonClick("/NewTicket", "NewTicket")}
            >
              Ticket ➕
            </button>
            <button
              disabled={props.disableValue}
              className={activeButton === "NewDepartment" ? "active" : ""}
              onClick={() =>
                handleButtonClick("/NewDepartment", "NewDepartment")
              }
            >
              Department ➕
            </button>

            <button
              disabled={props.disableValue}
              className={activeButton === "AllUsers" ? "active" : ""}
              onClick={() => handleButtonClick("/UserTable", "AllUsers")}
            >
              Users ⏩
            </button>
            <button
              disabled={props.disableValue}
              className={activeButton === "AllDepartments" ? "active" : ""}
              onClick={() =>
                handleButtonClick("/DepartmentTable", "AllDepartments")
              }
            >
              Departments ⏩
            </button>

            <button
              disabled={props.disableValue}
              className={activeButton === "AllTicket" ? "active" : ""}
              onClick={() => {
                handleButtonClick("/TicketTable", "AllTicket");
              }}
            >
              Tickets ⏩
            </button>
          </>
        )}

        {userRole === "MEMBER" && (
          <>
            <button
              disabled={props.disableValue}
              className={activeButton === "NewTicket" ? "active" : ""}
              onClick={() => handleButtonClick("/NewTicket", "NewTicket")}
            >
              Ticket ➕
            </button>
            <button
              disabled={props.disableValue}
              className={activeButton === "AllTicket" ? "active" : ""}
              onClick={() => {
                handleButtonClick("/TicketTable", "AllTicket");
                // props.myTicket(false);
              }}
            >
              Tickets ⏩
            </button>
          </>
        )}
      </div>
    </div>
  );
};

export default Sidebar;
