import React, { useState } from 'react';
import '../Styles/NewMember.css';
import Popup from '../Popup/Popup';
import axios from 'axios';

const API_URL_CREATE_DEPARTMENT = "http://localhost:8000/api/department/create";

const NewDepartment = () => {
  const storedData = JSON.parse(sessionStorage.getItem('loginData'));
  const requestData = storedData.requestData;

  const [deptName, setDeptName] = useState('');
  const [message, setMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const handleApiCall = async () => {
    try {
      const response = await axios.post(API_URL_CREATE_DEPARTMENT, { deptName }, {
        headers: {
          'email': requestData.email,
          'password': requestData.password
        }
      });

      setSuccessMessage("Department Added successfully!");
      setMessage('');
    } catch (error) {
      setMessage(error.response.data.message || 'An error occurred.');
      setSuccessMessage('');
    }
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    if (deptName.trim() === '') {
      setMessage('Department name cannot be empty.');
      setSuccessMessage('');
    } else {
      handleApiCall();
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <h2>New Department</h2>
      {/* {successMessage && <div className="success-message">{successMessage}</div>} */}
      {successMessage ? <Popup message= {successMessage} color="green"></Popup>:""}
      <div>
        <label>Department Name:</label>
        <input
          type="text"
          value={deptName}
          onChange={(e) => setDeptName(e.target.value)}
          placeholder="Enter department name"
        />
        {message && <div className="error-message">{message}</div>}
      </div>

      <div>
        <button className="addbutton" type="submit">Add</button>
      </div>
    </form>
  );
};

export default NewDepartment;
