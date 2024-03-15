import { useContext, useRef } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import jwtDecode from "jwt-decode";
import useUrlQuery from "../../hooks/useUrlQuery";
import { ACCESS_TOKEN } from "../../constants/constants";
import { UserContext } from "../../context/UserContext";

import { UserFromToken } from "../../model/UserFromToken";

export default function RedirectHandler() {
  const navigate = useNavigate();
  const { userModifier } = useContext(UserContext);
  const query = useRef<URLSearchParams>(useUrlQuery());

  const token = query.current.get("token");

  if (token) {
    localStorage.setItem(ACCESS_TOKEN, token);
    const decodedAccessToken: UserFromToken = jwtDecode(token);

    userModifier({ ...decodedAccessToken });

    return <Navigate to="/" />;
  }

  return <Navigate to="/login" />;
}
