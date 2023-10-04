import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import "../../Styles/ViewTicket.css";
import Popup from "../../components/Popup/Popup";

const API_BASE_URL = "http://localhost:8000/api";

function ViewTicket() {
  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const requestData = storedData.requestData || {};
  const responseData = storedData.responseData || {};

  const [ticketDetails, setTicketDetails] = useState(null);
  const [status, setStatus] = useState();
  const [inptcmt, setInptCmt] = useState(null);
  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState(null);
  const [showPopup, setShowPopup] = useState();

  const handleStatus = (value) => setStatus(value);

  const { ticketId } = useParams();

  const handleUpdate = (obj, cmt) => {
    setSuccessMsg(null);
    setErrorMsg(null);

    if (!cmt || cmt.trim() === "") {
      setShowPopup(true);
      setErrorMsg("Please enter a comment.");

      return;
    }
    const updatedData = {
      status: obj,
      comments: cmt,
      member: {
        email: requestData.email,
      },
    };

    axios
      .put(`${API_BASE_URL}/ticket/update`, updatedData, {
        params: { ticketId },
        headers: {
          email: requestData.email,
          password: requestData.password,
        },
      })
      .then((response) => {
        setTicketDetails(response.data);
        setStatus(response.data.status);
        setSuccessMsg("Ticket Updated successfully");
        setInptCmt("");
      })
      .catch((error) => {
        setErrorMsg(null);
        if (error.response) {
          setErrorMsg(error.response.data.message);
          setShowPopup(true);
          fetchTicketDetails();
        } else {
            setErrorMsg("Network Error")
        }
      });
  };

  const fetchTicketDetails = useCallback(async () => {
    setSuccessMsg(null);
    setErrorMsg(null);
    try {
      const response = await axios.get(`${API_BASE_URL}/ticket/viewById`, {
        params: { ticketId },
        headers: {
          email: requestData.email,
          password: requestData.password,
        },
      });

      setTicketDetails(response.data);
      setStatus(response.data.status);
    } catch (error) {
      if (error.response && error.response.data && error.response.data.msg) {
        setErrorMsg(error.response.data.msg);
      } else {
        setErrorMsg("Network error");
      }
    }
  }, [ticketId, requestData.email, requestData.password]);

  useEffect(() => {
    fetchTicketDetails();
  }, [fetchTicketDetails]);

  if (!ticketDetails) return <div>Loading...</div>;

  return (
    <div className="ticket-details">
      <h2>Ticket Title:- {ticketDetails.ticketName} </h2>
      {successMsg && <Popup message={successMsg} color="green"></Popup>}
      {errorMsg && <Popup message={errorMsg} color="red"></Popup>}
      <div className="details-container">
        <div className="details-section">
          <form className="ticketform">
            <div className="form-group">
              <label>Created By:</label>
              <input type="text" readOnly value={ticketDetails.member} />
            </div>
            <div className="form-group">
              <label>Ticket Type:</label>
              <input type="text" readOnly value={ticketDetails.ticketType} />
            </div>
            <div className="form-group">
              <label>Status:</label>
              <select className="statusEnable"
                name="status"
                value={status}
                disabled={
                    responseData.deptName !== ticketDetails.department &&
                    ticketDetails.member !== responseData.name
                }
                onChange={(event) => {
                  handleStatus(event.target.value);
                }}
              >
                <option value="OPEN">OPEN</option>
                <option value="BEING_ADDRESSED">BEING ADDRESSED</option>
                <option value="RESOLVED">RESOLVED</option>
              </select>
            </div>
            <div className="form-group">
              <label>Assigned To:</label>
              <input type="text" readOnly value={ticketDetails.department} />
            </div>
            <div className="form-group">
              <label>Description:</label>
              <textarea
                className="description"
                readOnly
                value={ticketDetails.description}
              ></textarea>
            </div>
            <div className="form-group">
              <label>Creatin Time:</label>
              <input type="text" readOnly value={ticketDetails.creationDate} />
            </div>
            <div className="form-group">
              <label>Last Update:</label>
              <input
                type="text"
                readOnly
                value={ticketDetails.lastUpdateDate}
              />
            </div>
          </form>
        </div>
        <div className="comments-section">
          <h3>Comments</h3>
          <ul className="cmtContainer">

          {ticketDetails.comments.length === 0 &&
                <h1>No Comments Yet</h1>
            }
            {ticketDetails.comments.map((ele) => (
              <li key={ele.commentId}>
                <div className="cmtby">{ele.memberEmail}</div>
                <span className="cmtcontent">{ele.content} </span><br></br>
                <span className="cmtcontentTime">{ele.creationTime} </span>
              </li>
            ))}
          </ul>
          <textarea
            className="cmtInput"
            value={inptcmt}
            placeholder="write your comment here "
            onChange={(e) => setInptCmt(e.target.value)}
            disabled={
              responseData.deptName !== ticketDetails.department &&
              ticketDetails.member !== responseData.name
            }
          ></textarea>
          <br></br>
          <button
            className="addbutton"
            disabled={
              responseData.deptName !== ticketDetails.department &&
              ticketDetails.member !== responseData.name
            }
            onClick={() => handleUpdate(status, inptcmt)}
          >
            Update{" "}
          </button>
        </div>
      </div>
    </div>
  );
}
export default ViewTicket;
