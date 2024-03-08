import { Route, Routes } from "react-router-dom";
import Navbar from "../components/navbar/Navbar";
import PlantCards from "../components/users_plants/UsersPlants";
import Login from "../pages/login/Login";
import Register from "../pages/register/Register";
import UnauthorizedRoute from "../components/UnauthorizedRoute";
import { withAxiosIntercepted } from "../hoc/withAxiosIntercepted";
import ProtectedRoute from "../components/ProtectedRoute";

function AppRouter() {
  return (
    <Routes>
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <Navbar />
          </ProtectedRoute>
        }
      >
        <Route index element={<PlantCards />} />
      </Route>

      <Route
        path="login"
        element={
          <UnauthorizedRoute>
            <Login />
          </UnauthorizedRoute>
        }
      />
      <Route
        path="register"
        element={
          <UnauthorizedRoute>
            <Register />
          </UnauthorizedRoute>
        }
      />
    </Routes>
  );
}

export default withAxiosIntercepted(AppRouter);
