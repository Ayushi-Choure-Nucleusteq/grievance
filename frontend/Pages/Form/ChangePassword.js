import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../Styles/NewMember.css";

const API_URL_CHANGE_PASSWORD =
  "http://localhost:8000/api/member/changePassword";

function ChangePassword(props) {
  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const requestData = storedData.requestData || {};
  const navigate = useNavigate();

  const [fieldErrors, setFieldErrors] = useState({
    oldPassword: "",
    newPassword: "",
    confirmPassword: "",
  });

  const [formData, setFormData] = useState({
    oldPassword: "",
    newPassword: "",
    confirmPassword: "",
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const isValidPassword = (password) => {
    const pattern =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})/;
    return pattern.test(password);
  };

  const handleApiCall = async () => {
    try {
      const encodedNewPassword = btoa(formData.newPassword);
      const encodedOldPassword = btoa(formData.oldPassword);
      const response = await axios.put(
        API_URL_CHANGE_PASSWORD,
        {
          oldPassword: encodedOldPassword,
          newPassword: encodedNewPassword,
        },
        {
          headers: {
            email: requestData.email,
            password: requestData.password,
          },
        }
      );

      if (response.status === 200) {
        setSuccessMessage("Password changed successfully!");
        props.getloginmsg("Password changed successfully!");
        localStorage.removeItem("loginData");
        navigate("/");
      } else {
        setErrorMessage("Unexpected response from the server.");
      }
    } catch (error) {
      const backendErrorMessage = error.response.data.message;
      setErrorMessage(
        backendErrorMessage || "Network error. Please try again later."
      );
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    e.preventDefault();

    let hasErrors = false;
    const errors = {
      oldPassword: "",
      newPassword: "",
      confirmPassword: "",
    };

    if (!formData.oldPassword) {
      errors.oldPassword = "Old password is required.";
      hasErrors = true;
    }

    if (!formData.newPassword) {
      errors.newPassword = "New password is required.";
      hasErrors = true;
    }
    if (!formData.confirmPassword) {
      errors.confirmPassword = "Confirm password is required.";
      hasErrors = true;
    }

    setFieldErrors(errors);

    if (hasErrors) return;

    if (formData.newPassword !== formData.confirmPassword) {
      setErrorMessage("New password and Confirm password must match!");
      return;
    }

    if (!isValidPassword(formData.newPassword)) {
      setErrorMessage(
        "Password must have one uppercase letter, lowercase special character,number, and be at least 8 characters long."
      );
      return;
    }

    handleApiCall();
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <h2>Change Password</h2>
        {successMessage && (
          <div className="success-message">{successMessage}</div>
        )}
        {errorMessage && <div className="error-message">{errorMessage}</div>}

        <div>
          <label>
            Old Password:
            <input
              type="password"
              name="oldPassword"
              value={formData.oldPassword}
              onChange={handleInputChange}
            />
            {fieldErrors.oldPassword && (
              <div className="error-message">{fieldErrors.oldPassword}</div>
            )}
          </label>
        </div>

        <div>
          <label>
            New Password:
            <input
              type="password"
              name="newPassword"
              value={formData.newPassword}
              onChange={handleInputChange}
            />
            {fieldErrors.newPassword && (
              <div className="error-message">{fieldErrors.newPassword}</div>
            )}
          </label>
        </div>

        <div>
          <label>
            Confirm Password:
            <input
              type="password"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleInputChange}
            />
            {fieldErrors.confirmPassword && (
              <div className="error-message">{fieldErrors.confirmPassword}</div>
            )}
          </label>
        </div>

        <button className="addbutton" type="submit">
          Change Password
        </button>
      </form>
    </div>
  );
}

export default ChangePassword;
