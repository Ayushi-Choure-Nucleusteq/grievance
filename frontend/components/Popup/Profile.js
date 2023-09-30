import React from "react";
import "../Styles/Profile.css";
import { useNavigate } from "react-router-dom";

const Profile = () => {
  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const responseData = storedData.responseData || {};
  //   if (!user) return null;
  const navigate = useNavigate();

  const onClose = () => {
    navigate("/TicketTable");
  };

  return (
    <div className="profile-modal">
      <div className="profile-content">
        <button className="close-button" onClick={onClose}>
          X
        </button>
        <h2 className="profile">Profile</h2>
        <div className="profile-field">
          <strong>Name:</strong> {responseData.name}
        </div>
        <div className="profile-field">
          <strong>Email:</strong> {responseData.email}
        </div>
        <div className="profile-field">
          <strong>Role:</strong> {responseData.role}
        </div>
        <div className="profile-field">
          <strong>Department:</strong> {responseData.deptName}
        </div>
      </div>
    </div>
  );
};

export default Profile;
