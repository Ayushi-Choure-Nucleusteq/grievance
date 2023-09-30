import React, { useState, useEffect } from "react";
import axios from "axios";
import Popup from "../Popup/Popup";
import "../Styles/TicketTable.css";

const USER_API_URL = "http://localhost:8000/api/member/getAll";
const DELETE_USER_API_URL = "http://localhost:8000/api/member/delete";

function UserTable() {
  const [users, setUsers] = useState([]);
  const [pageNo, setPageNo] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [deleted, setDeleted] = useState();

  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const requestData = storedData.requestData || {};
  const responseData = storedData.responseData || {};

  const fetchUsers = async () => {
    try {
      const endpoint = `${USER_API_URL}?pageNo=${pageNo}`;
      const response = await axios.get(endpoint, {
        headers: {
          email: requestData.email,
          password: requestData.password,
        },
      });

      setUsers(response.data);
      setHasMore(response.data.length > 0);
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setHasMore(false);
        return;
      }
    }
  };

  useEffect(() => {
    fetchUsers();
  }, [pageNo]);

  const handleDelete = async (userEmail) => {
    if (userEmail === requestData.email) {
      setDeleted(false);
      return;
    }

    try {
      await axios.delete(`${DELETE_USER_API_URL}?email=${userEmail}`, {
        headers: {
          email: requestData.email,
          password: requestData.password,
        },
      });
      // alert("User deleted successfully!");
      setDeleted(true);
      fetchUsers();
      setPageNo(0);
    } catch (error) {
      alert(error.response.message);
      console.error("Error deleting the user:", error);
    }
  };

  return (
    <div className="table-container">
      <table className="ticket-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Department</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.name}</td>
              <td>{user.email}</td>
              <td>{user.role}</td>
              <td>{user.deptName}</td>
              <td>
                <button
                  className="deletedept-btn"
                  disabled={responseData.email === user.email}
                  onClick={() => handleDelete(user.email)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="pagination-buttons">
        {pageNo > 0 && (
          <button
            className="pagination-btn"
            onClick={() => setPageNo(pageNo - 1)}
          >
            Previous
          </button>
        )}
        {hasMore && (
          <button
            className="pagination-btn"
            onClick={() => setPageNo(pageNo + 1)}
          >
            Next
          </button>
        )}
      </div>
      {deleted === true && (
        <Popup message="User deleted successfully" color="green"></Popup>
      )}
      {deleted === false && (
        <Popup message="Cannot delete yourself" color="red"></Popup>
      )}
    </div>
  );
}

export default UserTable;
