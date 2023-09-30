import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../Styles/TicketTable.css";

const API_URL = "http://localhost:8000/api/ticket/getAll";

function TicketTable(props) {
  const [tickets, setTickets] = useState([]);
  const [status, setStatus] = useState("filter");
  const [filtered, setFilter] = useState([]);
  const [pageNo, setPageNo] = useState(0);
  const [myTicket, setMyTicket] = useState(false);

  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const requestData = storedData.requestData || {};
  const navigate = useNavigate();

  const [hasMore, setHasMore] = useState(true);

  useEffect(() => {
    const fetchTickets = async () => {
      try {
        let endpoint = `${API_URL}?myTicket=${myTicket}&pageNo=${pageNo}`;

        if (status && status !== "filter") {
          endpoint += `&status=${status}`;
        }

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
          setFilter(response.data);
          setHasMore(true);
        }
      } catch (error) {
        if (error.response && error.response.status === 404) {
          setHasMore(false);
        }
      }
    };

    fetchTickets();
  }, [pageNo, status, myTicket]);

  const handleTicketView = (ticketId) => {
    navigate(`/ViewTicket/${ticketId}`);
  };

  const handleStatus = (value) => {
    setStatus(value);
    if (value === "filter") {
      setFilter(tickets);
    } else {
      setFilter(tickets.filter((ele) => ele.status === value));
    }
  };

  const handleMyTicket = (e) => {
    if (e.target.checked) {
      setMyTicket(true);
    } else setMyTicket(false);
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
          <option value="BEING_ADDRESSED">BEING ADDRESSED</option>
          <option value="RESOLVED">RESOLVED</option>
          <option value="OPEN">OPEN</option>
        </select>

        <div>
          <input
            id="myTicketCheckbox"
            onChange={(e) => handleMyTicket(e)}
            type="checkbox"
            name="myTicket"
            value="Bike"
          />
          <label htmlFor="myTicketCheckbox"> My Ticket</label>
        </div>
      </div>

      <table className="ticket-table">
        <thead>
          <tr>
            {/* <th>Ticket Id</th> */}
            <th> Sr. No.</th>
            <th>Ticket Title</th>
            <th>Ticket Status</th>
            <th>Assigned To</th>
            <th>Assigned By</th>
            <th>Last Update</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {filtered.map((ticket, index) => (
            <tr key={ticket.id} className="short-height">
              {/* <td>{ticket.ticketId}</td> */}
              <td>{index + 1}</td>
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
                  Edit
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {filtered.length === 0 && (
        <img
          className="nodataImage"
          src="C:\Users\ayush\OneDrive\Desktop\Working\Grievance_FRONT\src\Images\login.jpg"
        ></img>
      )}
      <div className="pagination-buttons">
        {pageNo > 0 && (
          <button
            className="pagination-btn"
            onClick={() => setPageNo((prevPageNo) => prevPageNo - 1)}
          >
            Previous
          </button>
        )}
        {hasMore && (
          <button
            className="pagination-btn"
            onClick={() => setPageNo((prevPageNo) => prevPageNo + 1)}
          >
            Next
          </button>
        )}
      </div>
    </div>
  );
}

export default TicketTable;
