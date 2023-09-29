import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import "../Styles/ViewTicket.css";
import Popup from '../Popup/Popup';



const API_BASE_URL = "http://localhost:8000/api";

function ViewTicket() {
  const storedData = JSON.parse(sessionStorage.getItem("loginData")) || {};
  const requestData = storedData.requestData || {};
  const responseData = storedData.responseData || {};

  const [ticketDetails, setTicketDetails] = useState(null);
  const [isDisable, setIsDisable] = useState(false);
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
      console.log("Setting error: Please enter a comment.", showPopup);
      setErrorMsg("Please enter a comment.");

      return; // Return immediately if no comment is provided
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
      })
      .catch((error) => {
        setErrorMsg(null);
        if (error.response) {
          setErrorMsg(error.response.data.message);
          setShowPopup(true);
          fetchTicketDetails();
        } else {
          console.log(error);
        }
      });
  };

  useEffect(() => {
    fetchTicketDetails();
  }, [ticketId]);

  const fetchTicketDetails = async () => {
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
        console.error("Error fetching ticket details:", error);
      }
    }
  };

  if (!ticketDetails) return <div>Loading...</div>;

  return (
    <div className="ticket-details">
      <h2>Ticket Title:- {ticketDetails.ticketName} </h2>
      {showPopup ? <Popup message={errorMsg} color="red"></Popup> : ""}
      {successMsg && <Popup message={successMsg} color="green"></Popup>}
      {/* Display error message if there's any */}
      {/* {errorMsg &&
                <div style={{ color: 'red', textAlign: 'center', paddingBottom: '30px', fontSize: "20px", fontWeight: 'bold' }}>
                    {errorMsg}
                </div>
            } */}
      <div className="details-container">
        <div className="details-section">
          {/* Ticket Details Form */}
          <form>
            <div className="form-group">
              <label>Created By:</label>
              <input type="text" readOnly value={ticketDetails.member} />
            </div>
            {/* <div className="form-group">
                            <label>Ticket Title:</label>
                            <input type="text" readOnly value={ticketDetails.ticketName} />
                        </div> */}
            <div className="form-group">
              <label>Ticket Type:</label>
              <input type="text" readOnly value={ticketDetails.ticketType} />
            </div>
            <div className="form-group">
              <label>Status:</label>
              <select
                name="status"
                value={status}
                disabled={isDisable}
                onChange={(event) => {
                  handleStatus(event.target.value);
                }}
              >
                <option value="BEING_ADDRESSED">BEING ADDRESSED</option>
                <option value="RESOLVED">RESOLVED</option>
                <option value="OPEN">OPEN</option>
              </select>
            </div>
            <div className="form-group">
              <label>Department:</label>
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
            {/* {ticketDetails.comments && ticketDetails.comments.map((comment, index) => (
                            <li key={index}>{comment}</li>
                        ))} */}

            {ticketDetails.comments.map((ele) => (
              <li key={ele.commentId}>
                <div className="cmtby">{ele.memberEmail}</div>
                <span className="cmtcontent">{ele.content} </span>
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
