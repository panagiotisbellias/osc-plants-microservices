import { Box, Button, Paper, Stack } from "@mui/material";
import { useCallback } from "react";
import { toast } from "react-toastify";
import { UsersPlant } from "../../model/api/UsersPlant";
import { CLOSE_TIME, COLOR_1 } from "../../constants/constants";
import UsersPlantApi from "../../api/UsersPlantApi";

interface SingleUsersPlantProps {
  usersPlant: UsersPlant;
  updateNextWatering: (updatedPlant: UsersPlant) => void;
  removeUsersPlant: (plantToBeDeletedId: number) => void;
}

export default function SingleUsersPlant({
  usersPlant,
  updateNextWatering,
  removeUsersPlant,
}: SingleUsersPlantProps) {
  const waterPlant = useCallback(async () => {
    try {
      const response = await UsersPlantApi.updateNextWatering(usersPlant.id);
      const updatedPlant: UsersPlant = { ...response.data, needsWater: false };
      updateNextWatering(updatedPlant);
    } catch (error) {
      toast.error("something went wrong with the server!", {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: CLOSE_TIME,
      });
    }
  }, [updateNextWatering, usersPlant.id]);

  const deleteUsersPlant = useCallback(async () => {
    try {
      await UsersPlantApi.deleteUsersPlant(usersPlant.id);
      removeUsersPlant(usersPlant.id);
    } catch (error) {
      toast.error("something went wrong with the server!", {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: CLOSE_TIME,
      });
    }
  }, [usersPlant.id, removeUsersPlant]);

  return (
    <Box>
      <Paper
        elevation={4}
        sx={{
          backgroundColor: COLOR_1,
          width: "420px",
          padding: "40px 0",
          borderRadius: "10px",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <Stack width={360} direction="column" spacing={2} alignItems="center">
          <Box>{usersPlant.needsWater ? "Needs Water" : "No water"}</Box>
          <Box>{usersPlant.id}</Box>
          <Box>{usersPlant.plant.photo}</Box>
          <Box>{usersPlant.plant.name}</Box>
          <Box>{usersPlant.plant.description}</Box>
          <Box>{usersPlant.plant.wateringInterval}</Box>
          <Box>{new Date(usersPlant.nextWatering).toLocaleString()}</Box>
          <Button onClick={waterPlant}>Water me</Button>
          <Button onClick={deleteUsersPlant}>Delete</Button>
        </Stack>
      </Paper>
    </Box>
  );
}
