import { Box, Button, Paper, Stack } from "@mui/material";
import { useCallback } from "react";
import { toast } from "react-toastify";
import { UsersPlant } from "../../model/api/UsersPlant";
import { CLOSE_TIME, COLOR_2 } from "../../constants/constants";
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
        elevation={8}
        sx={{
          backgroundColor: COLOR_2,
          width: "250px",
          // padding: "40px 0",
          borderRadius: "10px",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <Stack width={360} direction="column" spacing={2} alignItems="center">
          <Box>
            <img
              src={usersPlant.plant.photo}
              alt="plant"
              width="200px"
              height="200px"
              style={{ marginTop: "24px" }}
            />
          </Box>
          <Box
            sx={{
              width: "200px",
              display: "flex",
              justifyContent: "space-between",
            }}
          >
            <Box>{usersPlant.plant.name}</Box>
            <Box>{usersPlant.plant.wateringInterval}</Box>
          </Box>
          <Box sx={{ width: "200px" }}>{usersPlant.plant.description}</Box>

          <Box>
            Water on {new Date(usersPlant.nextWatering).toLocaleString()}
          </Box>
          <Box
            sx={{
              width: "200px",
              display: "flex",
              justifyContent: "space-between",
            }}
          >
            <Button onClick={deleteUsersPlant}>Delete</Button>
            {usersPlant.needsWater && (
              <Button onClick={waterPlant}>Water me</Button>
            )}
          </Box>
        </Stack>
      </Paper>
    </Box>
  );
}
