import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Popup from '../Popup/Popup';
import '../Styles/TicketTable.css';

const API_URL = 'http://localhost:8000/api/department/getAll';
const DELETE_DEPARTMENT_API_URL = 'http://localhost:8000/api/department/delete';

function DepartmentTable() {
    const [departments, setDepartments] = useState([]);

    const storedData = JSON.parse(sessionStorage.getItem('loginData')) || {};
    const requestData = storedData.requestData || {};
    const responseData = storedData.responseData || {};
    const [deleted , setDeleted]=useState();

    const fetchDepartments = async () => {
        try {
            const response = await axios.get(API_URL, {
                headers: {
                    'email': requestData.email,
                    'password': requestData.password
                }
            });
            setDepartments(response.data);
        } catch (error) {
            console.error("Error fetching the departments:", error);
        }
    };

    useEffect(() => {
        fetchDepartments();
    }, []);

    const handleDelete = async (deptName) => {
        if(deptName == responseData.deptName){
            setDeleted(false);
            return;
        }
        try {
            await axios.delete(DELETE_DEPARTMENT_API_URL, {
                headers: {
                    'email': requestData.email,
                    'password': requestData.password
                },
                data: {
                    "deptName": deptName
                }
            });
            setDeleted(true);
            // alert("Department deleted successfully")
            fetchDepartments();
        } catch (error) {
            console.error("Error deleting the department:", error);
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
                            <td>{index + 1}</td>
                            <td>{department.deptName}</td>
                            <td className='deptbtn'>
                                <button className="deletedept-btn" disabled={responseData.deptName === department.deptName} onClick={() => handleDelete(department.deptName)}>
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {deleted === true && <Popup message= "department deleted successfully" color="green"></Popup>}
            {deleted === false && <Popup message= "Cannot delete your department" color="red"></Popup>}

        </div>
    );
}

export default DepartmentTable;
