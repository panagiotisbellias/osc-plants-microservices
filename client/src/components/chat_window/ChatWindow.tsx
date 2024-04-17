import { useCallback, useContext, useEffect, useRef, useState } from "react";
import { Button, Paper, Stack, TextField, Typography } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import { CompatClient } from "@stomp/stompjs";
import { toast } from "react-toastify";
import { Message } from "../../model/socket/Message";
import { UserContext } from "../../context/UserContext";
import { CLOSE_TIME, COLOR_3 } from "../../constants/constants";
import ChatApi from "../../api/ChatApi";
import MessageList from "../message_list/MessageList";
import { ChatContainer, ChatInfoDetails } from "../../pages/chat/Chat.styles";
import { Loader } from "../../router/App.styles";
import useSocketClient from "../../hooks/useSocketClient";

interface ChatWindowProps {
  chosenChannel: string;
}

const WIDTH: string = `calc(100% - 250px)`;

export default function ChatWindow({ chosenChannel }: ChatWindowProps) {
  const { currentUser } = useContext(UserContext);
  const [messageList, setMessageList] = useState<Message[]>([]);
  const [messageInput, setMessageInput] = useState<string>("");
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const client = useRef<CompatClient>(useSocketClient());

  const getMessages = useCallback(async (channel: string) => {
    try {
      if (channel) {
        setIsLoading(true);
        const response = await ChatApi.getChat(channel);
        setMessageList(response.data);
      }
    } catch (error: any) {
      toast.error("An error occured when trying to connect to server", {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: CLOSE_TIME,
      });
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    if (chosenChannel) {
      getMessages(chosenChannel);
      client.current.debug = function () {};

      const connectCallback = () => {
        client.current.subscribe(
          `/topic/messages/${chosenChannel}`,
          (payload) => {
            const response = JSON.parse(payload.body);
            if (response && chosenChannel) {
              if (response.room === chosenChannel) {
                setMessageList((prev) => [...prev, response]);
              }
            }
          }
        );
      };

      client.current.connect({}, connectCallback);

      return () => {
        client.current.disconnect(() => {});
      };
    }
  }, [chosenChannel]);

  const sendMessageToChosenRoom = (room: string, message: string) => {
    if (client.current.connected) {
      client.current.send(
        `/app/messages/${room}`,
        {},
        JSON.stringify({
          room,
          username: currentUser!.username,
          email: currentUser!.sub,
          imageUrl: currentUser!.imageUrl,
          message,
        })
      );
    }
  };

  const sendMessage = () => {
    if (currentUser && messageInput.length > 0 && chosenChannel) {
      sendMessageToChosenRoom(chosenChannel, messageInput);
      setMessageInput("");
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent<HTMLDivElement>) => {
    if (e.key === "Enter") {
      sendMessage();
    }
  };

  return (
    <Paper
      sx={{
        backgroundColor: COLOR_3,
        width: WIDTH,
        height: "90vh",
        minHeight: "500px",
        borderRadius: "10px",
      }}
    >
      <ChatContainer>
        <Typography variant="h4">
          {chosenChannel ? chosenChannel : "Choose one of the channels"}
        </Typography>
        <ChatInfoDetails>
          {isLoading ? (
            <Loader style={{ marginTop: "200px" }} />
          ) : (
            <MessageList messageList={messageList} />
          )}
          <Stack
            direction="row"
            spacing={2}
            marginBottom={2}
            width="85%"
            justifyContent="space-between"
          >
            <TextField
              id="outlined-multiline-flexible"
              multiline
              maxRows={1}
              value={messageInput}
              onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
                setMessageInput(event.target.value);
              }}
              onKeyDown={(e) => handleKeyPress(e)}
              fullWidth
            />
            <Button
              variant="contained"
              onClick={() => sendMessage()}
              disabled={!(currentUser && messageInput && chosenChannel)}
              endIcon={<SendIcon />}
              sx={{ width: "150px", maxheight: "80px" }}
              size="medium"
            >
              Send
            </Button>
          </Stack>
        </ChatInfoDetails>
      </ChatContainer>
    </Paper>
  );
}
