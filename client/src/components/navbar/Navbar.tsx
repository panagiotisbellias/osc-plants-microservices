import { Outlet, useLocation } from "react-router-dom";
import { useContext } from "react";
import { toast } from "react-toastify";
import {
  LinkGroup,
  LogoutLinkButton,
  NavbarContainer,
  NavbarLink,
  NavbarLinks,
} from "./Navbar.styles";

import { UserContext } from "../../context/UserContext";

import { ACCESS_TOKEN, CLOSE_TIME } from "../../constants/constants";

export default function Navbar() {
  // const navigate = useNavigate();
  const { currentUser, userModifier } = useContext(UserContext);
  const location = useLocation();

  const handleOnLogoutClick = async () => {
    const jwtToken: string | null = localStorage.getItem(ACCESS_TOKEN);
    if (jwtToken) {
      userModifier(null);
      localStorage.clear();
      toast.success("You have successfully logged out", {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: CLOSE_TIME,
      });
    } else {
      toast.error("Unsuccessful logout!!!", {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: CLOSE_TIME,
      });
    }
  };

  return (
    <>
      <NavbarContainer>
        <NavbarLinks>
          <LinkGroup>
            <NavbarLink
              style={{
                textDecoration:
                  location.pathname === "/plants" ? "underline" : "none",
              }}
              to="/plants"
            >
              Add Plants
            </NavbarLink>
            {currentUser && (
              <NavbarLink
                style={{
                  textDecoration:
                    location.pathname === "/" ? "underline" : "none",
                }}
                to="/"
              >
                My plants
              </NavbarLink>
            )}
          </LinkGroup>
          <LinkGroup>
            {currentUser && (
              <LogoutLinkButton onClick={handleOnLogoutClick}>
                Logout
              </LogoutLinkButton>
            )}
            {currentUser && (
              <NavbarLink
                style={{
                  textDecoration:
                    location.pathname === "/user" ? "underline" : "none",
                }}
                to="/user"
              >
                Profile
              </NavbarLink>
            )}
          </LinkGroup>
        </NavbarLinks>
      </NavbarContainer>
      <Outlet />
    </>
  );
}
