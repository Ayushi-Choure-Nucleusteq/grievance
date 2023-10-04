import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import Popup from "../../components/Popup/Popup";
import "../../Styles/TicketTable.css";
import NoDataImage from "../../Images/login.jpg";

const API_URL = "http://localhost:8000/api/department/getAll";
const DELETE_DEPARTMENT_API_URL = "http://localhost:8000/api/department/delete";

function DepartmentTable() {
  const [departments, setDepartments] = useState([]);
  const [pageNo, setPageNo] = useState(0);
  const [hasMore, setHasMore] = useState(true);
  const [deleted, setDeleted] = useState();
  const [errorMsg, setErrorMsg] = useState(null);
  const TICKETS_PER_PAGE = 5;

  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const requestData = storedData.requestData || {};
  const responseData = storedData.responseData || {};

  const fetchDepartments = useCallback(async () => {
    try {
      const endpoint = `${API_URL}?pageNo=${pageNo}`;
      const response = await axios.get(endpoint, {
        headers: {
          email: requestData.email,
          password: requestData.password,
        },
      });

      setDepartments(response.data);
      setHasMore(response.data.length > 0);
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setDepartments([]);
        setHasMore(false);
        return;
      }
    }
  }, [pageNo, requestData.email, requestData.password]);

  useEffect(() => {
    fetchDepartments();
  }, [fetchDepartments]);

  const handleDelete = async (deptName) => {
    if (deptName === responseData.deptName) {
      setDeleted(false);
      return;
    }

    if (window.confirm("Are you sure?")) {
      try {
        await axios.delete(DELETE_DEPARTMENT_API_URL, {
          headers: {
            email: requestData.email,
            password: requestData.password,
          },
          data: {
            deptName: deptName,
          },
        });
        setDeleted(true);
        fetchDepartments();
        setPageNo(0);
      } catch (error) {
        setErrorMsg(error.response.data.message);
      }
    } else {
      setErrorMsg("Network Error");
    }
  };

  return (
    <div className="table-container">
      <table className="ticket-table">
        <thead>
          <tr>
            <th>Sr. No</th>
            <th>Department</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {departments.map((department, index) => (
            <tr key={department.id}>
              <td>{pageNo * TICKETS_PER_PAGE + index + 1}</td>
              <td>{department.deptName}</td>
              <td className="deptbtn">
                <button
                  className="deletedept-btn"
                  disabled={responseData.deptName === department.deptName}
                  onClick={() => handleDelete(department.deptName)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {departments.length === 0 && (
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
            onClick={() => setPageNo(pageNo - 1)}
          >
            ⬅️Previous
          </button>
        )}
        {hasMore && (
          <button
            className="pagination-btn"
            onClick={() => setPageNo(pageNo + 1)}
          >
            Next ➡️
          </button>
        )}
      </div>
      {deleted === true && (
        <Popup message="Department deleted successfully" color="green"></Popup>
      )}
      {deleted === false && (
        <Popup message="Cannot delete your department" color="red"></Popup>
      )}
    </div>
  );
}

export default DepartmentTable;
