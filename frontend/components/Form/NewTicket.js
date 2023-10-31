import React, { useState, useEffect } from "react";
import "../Styles/NewMember.css";
import axios from "axios";
import Popup from "../Popup/Popup";

const API_BASE_URL = "http://localhost:8000/api";

function NewTicket() {
  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const requestData = storedData.requestData || {};

  const [successMessage, setSuccessMessage] = useState("");
  const [backendError, setBackendError] = useState("");

  const [formData, setFormData] = useState({
    ticketType: "",
    ticketName: "",
    description: "",
    status: "OPEN",
    department: "",
    comments: "",
    member: {
      email: requestData.email,
    },
  });

  const [errors, setErrors] = useState({});
  const [departments, setDepartments] = useState([]);

  useEffect(() => {
    const fetchDepartments = async () => {
      try {
        const response = await axios.get(`${API_BASE_URL}/department/getAll`, {
          headers: {
            email: requestData.email,
            password: requestData.password,
          },
        });
        setDepartments(response.data);
      } catch (error) {
        console.error("Error fetching departments:", error);
      }
    };

    fetchDepartments();
  }, [requestData.email, requestData.password]);

  const checkTicketTitle = (ticketName) => {
    const namePattern = /^[A-Z][a-zA-Z]{2,}/;
    return namePattern.test(ticketName);
  };

  const validateForm = () => {
    const validationErrors = {};

    if (!formData.ticketType) {
      validationErrors.ticketType = "Ticket Type cannot be empty.";
    }
    if (!formData.ticketName) {
      validationErrors.ticketName = "Ticket Name cannot be empty.";
    } else if (!checkTicketTitle(formData.ticketName.trim())) {
      validationErrors.ticketName =
        "Title should start with an uppercase letter and contain at least 3 letters.";
    }
    if (!formData.description || formData.description.trim() === "") {
      validationErrors.description = "Description cannot be empty.";
    } else {
      const descriptionWords = formData.description.trim().split(" ").length;
      if (descriptionWords < 3 || descriptionWords > 100) {
        validationErrors.description =
          "Description should be between 3 to 100 words.";
      }
    }
    if (!formData.department) {
      validationErrors.department = "Department cannot be empty.";
    }

    return validationErrors;
  };

  const handleChange = (e) => {
    if (e.target.name === "department") {
      setFormData((prevState) => ({
        ...prevState,
        department: e.target.value,
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

  const handleSubmit = async (e) => {
    e.preventDefault();

    const validationErrors = validateForm();
    setErrors(validationErrors);

    if (Object.keys(validationErrors).length === 0) {
      setSuccessMessage("");
      try {
        await axios.post(`${API_BASE_URL}/ticket/create`, formData, {
          headers: {
            email: requestData.email,
            password: requestData.password,
          },
        });
        setSuccessMessage("Ticket Added successfully!");
        setFormData({
          ticketType: "",
          ticketName: "",
          description: "",
          status: "OPEN",
          department: "",
          comments: "",
          member: {
            email: requestData.email,
          },
        });
      } catch (error) {
        console.error("Error adding ticket:", error);
        if (
          error.response &&
          error.response.data &&
          error.response.data.message
        ) {
          setBackendError(error.response.data.message);
        } else {
          setBackendError(
            "Error occurred while adding the ticket. Please try again later."
          );
        }
      }
    } else {
      setErrors(validationErrors);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>New Ticket</h2>
      {/* {successMessage && <div className="success-message">{successMessage}</div>} */}
      {successMessage ? (
        <Popup message={successMessage} color="green"></Popup>
      ) : (
        ""
      )}
      {backendError ? <Popup message={backendError} color="red"></Popup> : ""}
      {/* {backendError && <div className="backend-error-message">{backendError}</div>} */}

      <div>
        <label>Ticket Type:</label>
        <select
          name="ticketType"
          value={formData.ticketType}
          onChange={handleChange}
        >
          <option value="" disabled>
            Select a Ticket Type
          </option>
          <option value="GRIEVANCE">GRIEVANCE</option>
          <option value="FEEDBACK">FEEDBACK</option>
        </select>

        {errors.ticketType && (
          <div style={{ color: "red", fontSize: "12px" }}>
            {errors.ticketType}
          </div>
        )}
      </div>

      <div>
        <label>Title:</label>
        <input
          type="text"
          name="ticketName"
          value={formData.ticketName}
          onChange={handleChange}
          placeholder="Enter title"
        />
        {errors.ticketName && (
          <div style={{ color: "red", fontSize: "12px" }}>
            {errors.ticketName}
          </div>
        )}
      </div>

      <div>
        <label>Description:</label>
        <textarea
          className="description"
          name="description"
          value={formData.description}
          onChange={handleChange}
          placeholder="Enter description"
        ></textarea>
        {errors.description && (
          <div style={{ color: "red", fontSize: "12px" }}>
            {errors.description}
          </div>
        )}
      </div>

      <div>
        <label>Assigned To:</label>
        <select
          name="department"
          value={formData.department}
          onChange={handleChange}
          onBlur={handleChange}
        >
          <option value="" disabled>
            Select Department
          </option>
          console.log(departments);
          {departments.map((dept) => (
            <option key={dept.id} value={dept.deptName}>
              {dept.deptName}
            </option>
          ))}
        </select>
        {errors.department && (
          <div style={{ color: "red", fontSize: "12px" }}>
            {errors.department}
          </div>
        )}
      </div>

      <div>
        <label>Status:</label>
        <select name="status" value={formData.status} disabled>
          <option value="OPEN">OPEN</option>
          <option value="BEING_ADDRESSED">BEING ADDRESSED</option>
          <option value="RESOLVED">RESOLVED</option>
        </select>
      </div>
      <button className="addbutton" type="submit">
        Add
      </button>
    </form>
  );
}

export default NewTicket;
