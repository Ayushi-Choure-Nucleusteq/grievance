import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../Styles/NewMember.css";
import Popup from "../../components/Popup/Popup";
import axios from "axios";

const API_URL_CREATE_DEPARTMENT = "http://localhost:8000/api/department/create";

const NewDepartment = () => {
  const storedData = JSON.parse(localStorage.getItem("loginData"));
  const requestData = storedData.requestData;

  const [deptName, setDeptName] = useState("");
  const [message, setMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const Navigate = useNavigate();

  const handleApiCall = async () => {
    try {
      await axios.post(
        API_URL_CREATE_DEPARTMENT,
        { deptName },
        {
          headers: {
            email: requestData.email,
            password: requestData.password,
          },
        }
      );

      setSuccessMessage("Department Added successfully!");
      setTimeout(() => {
        Navigate("/DepartmentTable");
      }, 3000);
      setMessage("");
      setDeptName("");
    } catch (error) {
      setMessage(error.response.data.message || "An error occurred.");
      setSuccessMessage("");
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (deptName.trim() === "") {
      setMessage("Department name cannot be empty.");
      setSuccessMessage("");
    } else {
      handleApiCall();
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>New Department</h2>
      <br></br>
      {successMessage ? (
        <Popup message={successMessage} color="green"></Popup>
      ) : (
        ""
      )}
      <div>
        <label>Department Name <span className="star">*</span></label>
        <input
          type="text"
          value={deptName}
          onChange={(e) => setDeptName(e.target.value)}
          placeholder="Enter department name"
        />
        {message && <div className="error-message">{message}</div>}
      </div>

      <div>
        <button className="addbutton" type="submit">
          Add
        </button>
      </div>
    </form>
  );
};

export default NewDepartment;
