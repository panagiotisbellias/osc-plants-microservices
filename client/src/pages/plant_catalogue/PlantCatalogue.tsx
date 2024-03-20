/* eslint-disable @typescript-eslint/no-explicit-any */
import { useCallback, useContext, useState } from "react";
import { toast } from "react-toastify";
import SearchPlants from "../../components/search-plant/SearchPlants";
import { Plant } from "../../model/Plant";
import {
  LoaderContainer,
  PlantCatalogueMainContainerStyle,
  SearchBackgroundContainer,
  SearchPlantsContainer,
  SearchResultsContainer,
} from "./PlantCatalogue.styles";
import { UsersPlantCreate } from "../../model/api/UsersPlantCreate";
import { UserContext } from "../../context/UserContext";
import UsersPlantApi from "../../api/UsersPlantApi";
import { CLOSE_TIME } from "../../constants/constants";
import SinglePlant from "../../components/single_plant/SinglePlant";
import { Loader } from "../../router/App.styles";

export default function PlantCatalogue() {
  const { currentUser } = useContext(UserContext);
  const [plants, setPlants] = useState<Plant[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  // const switchLoading = (loading: boolean) => {
  //   setIsLoading(loading);
  // }

  const updatePlants = (foundPlants: Plant[]) => {
    setPlants(foundPlants);
    setIsLoading(false);
  };

  const addUsersPlant = useCallback(
    async (chosenPlantId: string) => {
      try {
        if (currentUser?.id) {
          const usersPlantCreateRequest: UsersPlantCreate = {
            appUserId: currentUser.id,
            plantId: chosenPlantId,
          };

          await UsersPlantApi.addUsersPlant(usersPlantCreateRequest);
          toast.success("plant has been added to your collection", {
            position: toast.POSITION.TOP_RIGHT,
            autoClose: CLOSE_TIME,
          });
        }
      } catch (error: any) {
        let message: string;

        if (error.response && error.response.status === 400) {
          message = "Incorrect plant id or user id";
        } else {
          message = "An error occured when trying to connect to server";
        }
        toast.error(message, {
          position: toast.POSITION.TOP_RIGHT,
          autoClose: CLOSE_TIME,
        });
      }
    },
    [currentUser?.id]
  );

  return (
    <PlantCatalogueMainContainerStyle>
      <SearchBackgroundContainer>
        <SearchPlantsContainer>
          <SearchPlants updatePlants={updatePlants} /* switchLoading={switchLoading} */ setIsLoading={setIsLoading} />
        </SearchPlantsContainer>
      </SearchBackgroundContainer>

      <SearchResultsContainer>
        {isLoading && 
        <LoaderContainer>
          <Loader />
        </LoaderContainer> }
        {plants.map((plant) => (
          <SinglePlant
            key={plant.id}
            plant={plant}
            addUsersPlant={addUsersPlant}
          />
        ))}
      </SearchResultsContainer>
    </PlantCatalogueMainContainerStyle>
  );
}
