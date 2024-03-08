import { Outlet } from "react-router-dom";
import NavbarMainStyle from "./Navbar.styles";

export default function Navbar() {
  return (
    <>
      <NavbarMainStyle>Navbar</NavbarMainStyle>
      <Outlet />
    </>
  );
}
