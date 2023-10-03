import {
  BrowserRouter as Router,
  Routes,
  Route,
  useLocation,
} from "react-router-dom";
import Login from "./components/Form/Login";
import Navbar from "./components/NavBar/Navbar";
import Sidebar from "./components/SideBar/Sidebar";
import NewMember from "./components/Form/NewMember";
import NewTicket from "./components/Form/NewTicket";
import NewDepartment from "./components/Form/NewDepartment";
import ChangePassword from "./components/Form/ChangePassword";
import TicketTable from "./components/Table/TicketTable";
import ViewTicket from "./components/Form/ViewTicket";
import UserTable from "./components/Table/UserTable";
import DepartmentTable from "./components/Table/DepartmentTable";
import Profile from "./components/Popup/Profile";
import "./App.css";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const ProtectedRoute = ({ children, allowedRoles }) => {
  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const responseData = storedData.responseData || {};
  const userRole = responseData.role;

  const navigate = useNavigate();
  if (!allowedRoles.includes(userRole)) {
    navigate("/");
    return null;
  }

  return children;
};

function App() {
  const [loginmsg, setloginmsg] = useState(null);
  const storedData = JSON.parse(localStorage.getItem("loginData")) || {};
  const responseData = storedData.responseData || {};
  const [firstTimeLogin, setFirstTimeLogin] = useState(false);

  const getloginmsg = (value) => {
    setloginmsg(value);
  };

  function PageLayout() {
    const location = useLocation();
    const storedData = localStorage.getItem("loginData");

    const isLoggedIn = storedData ? true : false;

    const isLoginPage = location.pathname === "/";

    // to clear the session.when user loggedin and hits login url.
    if (isLoggedIn && isLoginPage) {
      localStorage.removeItem("loginData");
      window.location.reload();
    }

    if (!isLoggedIn && !isLoginPage) {
      return <Login />;
    }

    return (
      <div className="  ">
        {!isLoginPage && <Navbar disableValue={responseData.isLoggedIn} />}

        <div className="content-container">
          {!isLoginPage && <Sidebar disableValue={responseData.isLoggedIn} />}

          <div className="main-content">
            <Routes>
            <Route path="/" element={<Login msg={loginmsg} firstTimeLogin={firstTimeLogin} setFirstTimeLogin={setFirstTimeLogin} />} />
              <Route
                path="/NewMember"
                element={
                  <ProtectedRoute allowedRoles={["ADMIN"]}>
                    <NewMember />
                  </ProtectedRoute>
                }
              />
              <Route path="/NewTicket" element={<NewTicket />} />
              <Route
                path="/NewDepartment"
                element={
                  <ProtectedRoute allowedRoles={["ADMIN"]}>
                    <NewDepartment />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/ChangePassword"
                element={<ChangePassword getloginmsg={getloginmsg} />}
              />
              <Route path="/TicketTable" element={<TicketTable />} />
              <Route path="/ViewTicket/:ticketId" element={<ViewTicket />} />
              <Route
                path="/UserTable"
                element={
                  <ProtectedRoute allowedRoles={["ADMIN"]}>
                    <UserTable />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/DepartmentTable"
                element={
                  <ProtectedRoute allowedRoles={["ADMIN"]}>
                    <DepartmentTable />
                  </ProtectedRoute>
                }
              />
              <Route path="/Profile" element={<Profile />} />
            </Routes>
          </div>
        </div>
      </div>
    );
  }

  return (
    <Router>
      <PageLayout />
    </Router>
  );
}

export default App;
