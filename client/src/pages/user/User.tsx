/* eslint-disable @typescript-eslint/no-explicit-any */
import { useCallback, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { UserContext } from "../../context/UserContext";
import UserApi from "../../api/UserApi";
import {
  CLOSE_TIME,
  COLOR_1,
  COLOR_2,
  COLOR_5,
} from "../../constants/constants";
import {
  Avatar,
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  CardMedia,
  Typography,
} from "@mui/material";

export default function User() {
  const navigate = useNavigate();
  const { currentUser, userModifier } = useContext(UserContext);

  const onDeleteClick = useCallback(async () => {
    try {
      if (currentUser?.id) {
        // await UserApi.deleteUserById(currentUser?.id);
        const response = await UserApi.deleteUserById("xxxxx");
        if (
          response.data !==
          "Oops! Something went wrong, please try to delete user later!"
        ) {
          localStorage.clear();
          userModifier(null);
          navigate("/login");
        } else {
          toast.error(
            "Oops! Something went wrong, please try to delete user later!",
            {
              position: toast.POSITION.TOP_RIGHT,
              autoClose: CLOSE_TIME,
            }
          );
        }
      }
    } catch (error: any) {
      let message: string;

      if (error.response && error.response.status === 400) {
        message = "Incorrect user Id";
      } else {
        message = "An error occured when trying to connect to server";
      }
      toast.error(message, {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: CLOSE_TIME,
      });
    }
  }, [currentUser?.id, navigate, userModifier]);

  return (
    <Box sx={{ backgroundColor: COLOR_2, width: "100%", height: "100%" }}>
      <Card sx={{ width: 245, backgroundColor: COLOR_1 }}>
        <CardHeader
          avatar={
            currentUser?.imageUrl ? (
              <Avatar alt="Users picture" src={currentUser?.imageUrl} />
            ) : (
              <Avatar sx={{ bgcolor: COLOR_5 }} aria-label="recipe">
                {currentUser?.username.charAt(0).toUpperCase()}
              </Avatar>
            )
          }
          // title="Shrimp and Chorizo Paella"
          // subheader="September 14, 2016"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {currentUser?.username}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {currentUser?.sub}
          </Typography>
          <Typography variant="body1" color="text.secondary">
            Role: {currentUser?.role}
          </Typography>
        </CardContent>
        <CardActions>
          <Button size="small" onClick={() => onDeleteClick()}>
            Delete me
          </Button>
        </CardActions>
      </Card>
    </Box>
    // <>
    //   <div>{currentUser && currentUser.username}</div>
    //   <div>{currentUser && currentUser.sub}</div>
    //   <div>{currentUser && currentUser.role}</div>
    //   <div>{currentUser && currentUser.imageUrl}</div>
    //   <div>{currentUser && currentUser.id}</div>
    //   <button onClick={onDeleteClick}>Delete me</button>
    // </>
  );
}
