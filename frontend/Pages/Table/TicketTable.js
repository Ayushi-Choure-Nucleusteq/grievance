import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../../Styles/TicketTable.css";
import NoDataImage from "../../Images/login.jpg";

const API_URL = "http://localhost:8000/api/ticket/getAll";

function TicketTable(props) {
  const [tickets, setTickets] = useState([]);
  const [status, setStatus] = useState("filter");
  const [pageNo, setPageNo] = useState(0);
  const [myTicket, setMyTicket] = useState(false);
  const [myDeptTicket, setMyDeptTicket] = useState(false);

  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const requestData = storedData.requestData || {};
  const responseData = storedData.responseData || {};
  const navigate = useNavigate();
  const [hasMore, setHasMore] = useState(true);

  const fetchTickets = useCallback(async () => {
    setTickets([]);
    setHasMore(true);

    let endpoint = `${API_URL}?myTicket=${myTicket}&pageNo=${pageNo}&myDeptTicket=${myDeptTicket}`;
    if (status && status !== "filter") {
      endpoint += `&status=${status}`;
    }

    try {
      const response = await axios.get(endpoint, {
        headers: {
          email: requestData.email,
          password: requestData.password,
        },
      });

      if (response.data.length === 0) {
        setHasMore(false);
      } else {
        setTickets(response.data);
      }
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setHasMore(false);
      }
    }
  }, [pageNo, status, myTicket, myDeptTicket, requestData.email, requestData.password]);

  useEffect(() => {
    fetchTickets();
  }, [fetchTickets, status, myTicket, myDeptTicket]);



  const handleTicketView = (ticketId) => {
    navigate(`/ViewTicket/${ticketId}`);
  };

  const handleStatus = (value) => {
    setStatus(value);
  };

  const handleMyTicket = (e) => {
    if (e.target.checked) {
      setMyTicket(true);
      setMyDeptTicket(false);
    } else {
      setMyTicket(false);
    }
  };

  const handleMyDeptTicket = (e) => {
    if (e.target.checked) {
      setMyDeptTicket(true);
      setMyTicket(false);
    } else {
      setMyDeptTicket(false);
    }
  };

  return (
    <div className="table-container">
      <div className="filter-container">
        <select
          className="filter"
          name="status"
          value={status}
          onChange={(event) => handleStatus(event.target.value)}
        >
          <option value="filter">Filter</option>
          <option value="OPEN">OPEN</option>
          <option value="BEING_ADDRESSED">BEING ADDRESSED</option>
          <option value="RESOLVED">RESOLVED</option>
        </select>

        <div>
          <input
            id="myTicketCheckbox"
            onChange={(e) => handleMyTicket(e)}
            type="checkbox"
            name="myTicket"
            value="myTicket"
          />
          <label htmlFor="myTicketCheckbox"> My Tickets</label>
        </div>

        {responseData.role === "ADMIN" && (
  <div>
    <input
      id="myDeptTicketCheckbox"
      onChange={handleMyDeptTicket}
      type="checkbox"
      name="myDeptTicket"
      value="myDeptTicket"
    />
    <label htmlFor="myDeptTicketCheckbox">Department Tickets</label>
  </div>
)}


      </div>

      <table className="ticket-table">
        <thead>
          <tr>
            <th> Ticket Id</th>
            <th>Ticket Title</th>
            <th>Ticket Status</th>
            <th>Assigned To</th>
            <th>Assigned By</th>
            <th>Last Update</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {tickets.map((ticket, index) => (
            <tr key={ticket.id} className="short-height">
              <td>{ticket.ticketId}</td>
              <td>{ticket.ticketName}</td>
              <td>{ticket.status}</td>
              <td>{ticket.department}</td>
              <td>{ticket.member}</td>
              <td>{ticket.lastUpdateDate}</td>
              <td>
                <button
                  onClick={() => handleTicketView(ticket.ticketId)}
                  className="edit-btn"
                >
                  View
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {tickets.length === 0 && (
        <img
          className="nodataImage"
          src={NoDataImage}
          alt="No Data Available"
        />
      )}
      <div className="pagination-buttons">
        {pageNo > 0 && (
          <button
            className="pagination-btn"
            onClick={() => setPageNo((prevPageNo) => prevPageNo - 1)}
          >
            ⬅️Previous
          </button>
        )}
        {hasMore && (
          <button
            className="pagination-btn"
            onClick={() => setPageNo((prevPageNo) => prevPageNo + 1)}
          >
            Next ➡️
          </button>
        )}
      </div>
    </div>
  );
}

export default TicketTable;
