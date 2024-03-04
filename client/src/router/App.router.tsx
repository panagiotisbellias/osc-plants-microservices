import { Route, Routes } from "react-router-dom";
import { Navbar } from "../components/Navbar";
import { PlantCards } from "../components/PlantCards";

export const AppRouter = () => {
  return (
    <Routes>
      <Route path="/" element={<Navbar />}>
        <Route index element={<PlantCards />} />
        {/* <Route path="login" element={<Login />} /> */}
      </Route>
    </Routes>
  );
};
