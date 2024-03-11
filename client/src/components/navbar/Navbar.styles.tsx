import styled from "styled-components";
import { Link } from "react-router-dom";
import {
  COLOR_1,
  COLOR_2,
  COLOR_3,
  COLOR_4,
  COLOR_5,
  COLOR_BLACK,
  MIN_WIDTH,
  NAVBAR_HEIGHT,
} from "../../constants/constants";

export const NavbarContainer = styled.div`
  height: ${NAVBAR_HEIGHT};
  background-color: ${COLOR_5};
  display: flex;
  justify-content: end;
  // min-width: ${MIN_WIDTH};
`;

export const NavbarLinks = styled.div`
  width: 60%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
`;

export const NavbarLink = styled(Link)`
  text-decoration: none;
  color: ${COLOR_2};
  font-size: 24px;

  &:hover {
    cursor: pointer;
    color: white;
  }
`;

export const LogoutLinkButton = styled.span`
  text-decoration: none;
  color: ${COLOR_2};
  font-size: 24px;

  &:hover {
    cursor: pointer;
    color: ${COLOR_1};
  }
`;

export const LinkGroup = styled.div`
  display: flex;
  gap: 24px;
  justify-content: center;
`;
