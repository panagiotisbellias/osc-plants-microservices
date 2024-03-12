import { Route, Routes } from "react-router-dom";
import Navbar from "../components/navbar/Navbar";
import PlantCards from "../pages/users_plants/UsersPlants";
import Login from "../pages/login/Login";
import Register from "../pages/register/Register";
import UnauthorizedRoute from "../components/UnauthorizedRoute";
import { withAxiosIntercepted } from "../hoc/withAxiosIntercepted";
import ProtectedRoute from "../components/ProtectedRoute";
import PlantCatalogue from "../pages/plant_catalogue/PlantCatalogue";
import User from "../pages/user/User";

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
        <Route path="plants" element={<PlantCatalogue />} />
        <Route path="user" element={<User />} />
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
