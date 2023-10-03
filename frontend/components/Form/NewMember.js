import React, { useState, useEffect, useCallback } from "react";
import "../Styles/NewMember.css";
import Popup from "../Popup/Popup";
import axios from "axios";

const API_URL_CREATE_MEMBER = "http://localhost:8000/api/member/create";
const API_URL_GET_ALL_DEPTS = "http://localhost:8000/api/department/getAll";

function NewMember() {
  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const requestData = storedData.requestData || {};

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    role: "",
    department: {
      deptId: 1,
      deptName: "",
    },
  });

  const [errors, setErrors] = useState({});
  const [departments, setDepartments] = useState([]);
  const [backendError, setBackendError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);

  //to fetch department for the dropdown

  const fetchDepartments = useCallback(async () => {
    try {
      const response = await axios.get(API_URL_GET_ALL_DEPTS, {
        headers: {
          email: requestData.email,
          password: requestData.password,
        },
      });
      setDepartments(response.data);
    } catch (error) {
      console.error("Error fetching departments:", error);
    }
  }, [requestData.email, requestData.password]);

  useEffect(() => {
    fetchDepartments();
  }, [fetchDepartments]);

  //Api call to add user after encoding the password
  const handleApiCall = async () => {
    try {
      const encodedPassword = btoa(formData.password);
      const dataToSend = {
        ...formData,
        password: encodedPassword,
      };

      await axios.post(API_URL_CREATE_MEMBER, dataToSend, {
        headers: {
          email: requestData.email,
          password: requestData.password,
        },
      });
      setSuccessMessage("User Added successfully!");
      setFormData({
        name: "",
        email: "",
        password: "",
        role: "",
        department: {
          deptId: 1,
          deptName: "",
        },
      });
    } catch (error) {
      if (error.response && error.response.data) {
        setBackendError(error.response.data.message);
      }
    }
  };

  const handleChange = (e) => {
    if (e.target.name === "department") {
      const selectedDept = departments.find(
        (dept) => dept.deptName === e.target.value
      );
      setFormData((prevState) => ({
        ...prevState,
        department: {
          deptId: selectedDept?.id,
          deptName: selectedDept?.deptName,
        },
      }));
    } else {
      setFormData({
        ...formData,
        [e.target.name]: e.target.value,
      });
    }
    if (errors[e.target.name]) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        [e.target.name]: undefined,
      }));
    }
  };

  const checkName = (name) => {
    const namePattern = /^[A-Z][a-zA-Z]{2,}/;
    return namePattern.test(name);
  };

  const checkEmail = (email) => {
    const emailPattern = /^[a-z.]+@nucleusteq\.com$/;
    return emailPattern.test(email);
  };

  const checkPassword = (password) => {
    const hasLowercase = /[a-z]/;
    const hasUppercase = /[A-Z]/;
    const hasNumber = /\d/;
    const hasSpecialCharacter = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;

    return (
      hasLowercase.test(password) &&
      hasUppercase.test(password) &&
      hasNumber.test(password) &&
      hasSpecialCharacter.test(password) &&
      password.length >= 8
    );
  };

  //submit handle function . Check validation.
  const handleSubmit = async (e) => {
    e.preventDefault();
    const validationErrors = {};

    if (!formData.name || formData.name.trim() === "") {
      validationErrors.name = "Name cannot be empty.";
    } else if (!checkName(formData.name.trim())) {
      validationErrors.name =
        "Name should start with an uppercase letter and contain atleast 3 letters.";
    }
    if (!formData.email) {
      validationErrors.email = "Email cannot be empty.";
    } else if (!checkEmail(formData.email)) {
      validationErrors.email =
        'Email can only contain lowercase and "." and must end with @nucleusteq.com.';
    }

    if (!formData.password) {
      validationErrors.password = "Password cannot be empty.";
    } else if (!checkPassword(formData.password)) {
      validationErrors.password =
        "Password must contain an uppercase letter, a lowercase letter, a number, a special character, and be atleast 8 characters long.";
    }

    if (!formData.role) {
      validationErrors.role = "User role cannot be empty.";
    }

    if (!formData.department.deptName) {
      validationErrors.department = "Department cannot be empty.";
    }
    // if no error call api
    if (Object.keys(validationErrors).length === 0) {
      setSuccessMessage(null);
      await handleApiCall();
    } else {
      setErrors(validationErrors);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>New User</h2>
      {successMessage ? (
        <Popup message={successMessage} color="green"></Popup>
      ) : (
        <div className="error-message">{backendError}</div>
      )}
      <div>
        <label>Name:</label>
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
          placeholder="Enter your name"
        />
        {errors.name && <span>{errors.name}</span>}
      </div>

      <div>
        <label>Email:</label>
        <input
          type="email"
          name="email"
          value={formData.email}
          onChange={handleChange}
          // onkeyup={handleSubmit}
          placeholder="john@nucleusteq.com"
        />
        {errors.email && <span>{errors.email}</span>}
      </div>

      <div>
        <label>Password:</label>
        <input
          type="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
          placeholder="Enter a strong password"
        />
        {errors.password && <span>{errors.password}</span>}
      </div>

      <div>
        <label>User Role:</label>
        <select name="role" value={formData.role} onChange={handleChange}>
          <option value="" disabled>
            Select Role
          </option>
          <option value="MEMBER">MEMBER</option>
          <option value="ADMIN">ADMIN</option>
        </select>
        {errors.role && <span>{errors.role}</span>}
      </div>

      <div>
        <label>Department:</label>
        <select
          name="department"
          value={formData.department.deptName}
          onChange={handleChange}
        >
          <option value="" disabled>
            Select Department
          </option>
          {departments.map((dept) => (
            <option key={dept.id} value={dept.deptName}>
              {dept.deptName}
            </option>
          ))}
        </select>
        {errors.department && <span>{errors.department}</span>}
      </div>

      <button className="addbutton" type="submit">
        Add
      </button>
    </form>
  );
}

export default NewMember;
