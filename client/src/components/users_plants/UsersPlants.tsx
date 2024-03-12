import { useCallback, useContext, useEffect, useState } from "react";
import { toast } from "react-toastify";
import { Box, Paper, Stack } from "@mui/material";
import { UsersPlant } from "../../model/api/UsersPlant";
import UsersPlantApi from "../../api/UsersPlantApi";
import { UserContext } from "../../context/UserContext";

import { CLOSE_TIME } from "../../constants/constants";
import SingleUsersPlant from "../single_users_plant/SingleUsersPlant";
import {
  PlantCardsContainer,
  PlantCardsMainContainerStyle,
} from "./UsersPlants.styles";

export default function PlantCards() {
  const { currentUser } = useContext(UserContext);
  const [usersPlants, setUsersPlants] = useState<UsersPlant[]>([]);

  function sortUsersPlants(plants: UsersPlant[]) {
    plants.sort((a, b) => {
      if (a.needsWater === b.needsWater) {
        if (a.id > b.id) {
          return -1;
        }
        if (a.id < b.id) {
          return 1;
        }
        return 0;
      }
      if (a.needsWater === true && b.needsWater === false) {
        return -1;
      }
      return 1;
    });
    setUsersPlants(plants);
  }

  const updateNextWatering = (updatedPlant: UsersPlant) => {
    let newUsersPlants: UsersPlant[] = [...usersPlants];
    newUsersPlants = newUsersPlants.map((plant) =>
      plant.id === updatedPlant.id ? updatedPlant : plant
    );

    setUsersPlants(newUsersPlants);
  };

  const removeUsersPlant = (plantToBeDeletedId: number) => {
    let newUsersPlants: UsersPlant[] = [...usersPlants];
    newUsersPlants = newUsersPlants.filter(
      (plant) => plant.id !== plantToBeDeletedId
    );

    setUsersPlants(newUsersPlants);
  };

  const getUsersPlants = useCallback(async () => {
    try {
      if (currentUser?.id) {
        const usersPlantsResponse = await UsersPlantApi.getUsersPlants(
          currentUser?.id
        );
        const newUserPlants: UsersPlant[] = usersPlantsResponse.data;
        const plantsToWaterResponse = await UsersPlantApi.getNotifications(
          currentUser?.id
        );
        // const plantsToWater: number[] = plantsToWaterResponse.data;
        const plantsToWater: number[] = [8, 10];

        const updatedUserPlants = newUserPlants.map((plant) => ({
          ...plant,
          needsWater: plantsToWater.includes(plant.id),
        }));
        sortUsersPlants(updatedUserPlants);
        // setUsersPlants(updatedUserPlants);
      }
    } catch (error) {
      toast.error("something went wrong with the server!", {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: CLOSE_TIME,
      });
    }
  }, [currentUser?.id]);

  useEffect(() => {
    if (currentUser) {
      getUsersPlants();
    }
  }, [currentUser, getUsersPlants]);

  return (
    <PlantCardsMainContainerStyle>
      <PlantCardsContainer>
        {usersPlants.map((usersPlant) => (
          <SingleUsersPlant
            key={usersPlant.id}
            usersPlant={usersPlant}
            updateNextWatering={updateNextWatering}
            removeUsersPlant={removeUsersPlant}
          />
        ))}
      </PlantCardsContainer>
    </PlantCardsMainContainerStyle>
  );
}
