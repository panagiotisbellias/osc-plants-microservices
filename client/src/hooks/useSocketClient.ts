import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { ACCESS_TOKEN } from "../constants/constants";

const useSocketClient = () => {
  return Stomp.over(
    () =>
      new SockJS(`${import.meta.env.VITE_API_URL}/stomp`, {
        Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
      })
  );
};

export default useSocketClient;
