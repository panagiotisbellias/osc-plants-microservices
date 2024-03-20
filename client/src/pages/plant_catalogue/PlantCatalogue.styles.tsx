import styled from "styled-components";
import { COLOR_1, COLOR_3 } from "../../constants/constants";

export const PlantCatalogueMainContainerStyle = styled.div`
  background-color: rgba(255, 255, 255, 0.3);
  background-blend-mode: overlay;
  background-image: url(/pexels-charlotte-may-5824877.jpg);
  background-size: cover;
  display: flex;
  flex-direction: column;
  align-items: center;
  //   justify-content: flex-start;
  min-height: 90vh;
`;

export const SearchBackgroundContainer = styled.div`
  width: 100%;
  height: 100px;
  background-color: ${COLOR_3};
  display: flex;
  align-items: flex-end;
  justify-content: center;
`;

export const SearchPlantsContainer = styled.div`
  background-color: ${COLOR_1};
  border-radius: 4px;
  width: 360px;
  margin-bottom: 8px;
`;

export const SearchResultsContainer = styled.div`
  margin-top: 24px;
  margin-bottom: 24px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  gap: 8px;
`;

export const LoaderContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 30vh;
`;
