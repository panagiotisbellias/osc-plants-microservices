/* eslint-disable react/jsx-no-useless-fragment */
import { Navigate } from "react-router-dom";
import { ACCESS_TOKEN } from "../constants/constants";
import { logger } from '../logger';

export default function ProtectedRoute({ children }: React.PropsWithChildren) {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    logger.info("No access token exists in local storage");
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
}
