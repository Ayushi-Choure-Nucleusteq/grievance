import React, { useState, useEffect } from "react";
import "../Styles/Login.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const API_URL = "http://localhost:8000/api/member/login";

const Login = (props) => {
  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const requestData = storedData.requestData || {};
  const responseData = storedData.responseData || {};

  const [username, setUsername] = useState("");
  const [pass, setPassword] = useState("");
  const navigate = useNavigate();
  const [errorMsg, setErrorMsg] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [loginError, setLoginError] = useState("");

  useEffect(() => {
    // to Clear session when the Login component is loaded.
    localStorage.removeItem("loginData");
  }, []);

  const checkusername = (e) => {
    const value = e.target.value;
    setUsername(value);
    if (value !== value.toLowerCase()) {
      setErrorMsg("Email should be in lowercase.");
    } else if (!value.endsWith("@nucleusteq.com")) {
      setErrorMsg("Invalid email. Please use email of @nucleusteq.com");
    } else {
      setErrorMsg("");
    }
  };

  const validatePassword = (e) => {
    const values = e.target.value;
    setPassword(values);
    const hasSpecialCharacter = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
    const hasNumber = /\d/;
    const hasLowercase = /[a-z]/;
    const hasUppercase = /[A-Z]/;

    if (
      values.length < 8 ||
      !hasNumber.test(values) ||
      !hasLowercase.test(values) ||
      !hasUppercase.test(values) ||
      !hasSpecialCharacter.test(values)
    ) {
      setPasswordError(
        "Password must be at least 8 characters long, include at least one number, one lowercase letter, and one uppercase letter."
      );
    } else {
      setPasswordError("");
    }
  };

  const validateFields = () => {
    if (errorMsg || passwordError) {
      return false;
    }
    return true;
  };

  const handleApiCall = async (data) => {
    try {
      const response = await axios.post(API_URL, data);
      if (response?.status === 202) {
        const dataToStore = {
          requestData: {
            email: username,
            password: btoa(pass),
          },
          responseData: response.data,
        };
        localStorage.setItem("loginData", JSON.stringify(dataToStore));
        if (response?.data?.isLoggedIn) {
          navigate("/ChangePassword");
          return;
        }
        if (
          response?.data?.role === "MEMBER" ||
          response?.data?.role === "ADMIN"
        ) {
          navigate("/TicketTable");
        }
      }
    } catch (err) {
      if (err.response && err.response.data) {
        setLoginError(err.response.data.message);
      } else {
        setLoginError("Login Failed! Please check your credentials.");
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    let hasError = false;

    if (!username) {
      setErrorMsg("Email is required.");
      hasError = true;
    } else {
      setErrorMsg("");
    }

    if (!pass) {
      setPasswordError("Password is required.");
      hasError = true;
    } else {
      setPasswordError("");
    }

    if (hasError) return;

    if (!validateFields()) {
      return;
    }

    const data = {
      password: btoa(pass),
      email: username,
    };

    await handleApiCall(data);
  };

  const isValidForm = () => {
    if (!username || errorMsg || !pass || passwordError) {
      return false;
    }
    return true;
  };

  return (
    <div className="login-container">
      <div className="title-container" />
      <div className="login_container">
        <div className="form-container">
          <h2>Greivance Management</h2>
          {/* {loginError? <Popup message= {loginError} color="red"></Popup>:""} */}
          {loginError && (
            <div className="error-popup" onClick={() => setLoginError("")}>
              {loginError}
            </div>
          )}
          {/* <h2>Login</h2> */}
          <h1>{props.msg ? props.msg : ""}</h1>
          <form onSubmit={handleSubmit} className="login_form">
            <label htmlFor="username">
              Username<span className="star">*</span>
            </label>
            <input
              id="username"
              type="text"
              placeholder="username"
              value={username}
              onChange={checkusername}
            />
            {errorMsg && (
              <div style={{ color: "red", fontSize: "12px" }}>{errorMsg}</div>
            )}
            <label htmlFor="password">
              Password<span className="star">*</span>
            </label>
            <input
              id="password"
              type="password"
              placeholder="Password"
              value={pass}
              onChange={validatePassword}
            />
            {passwordError && (
              <div style={{ color: "red", fontSize: "12px" }}>
                {passwordError}
              </div>
            )}
            <button
              type="submit"
              className="addbutton"
              disabled={!isValidForm()}
            >
              Login
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;
