import styled from "styled-components";
import { COLOR_2 } from "../../constants/constants";

export const PlantCardsMainContainerStyle = styled.div`
  background-color: rgba(255, 255, 255, 0.3);
  background-blend-mode: overlay;
  background-image: url(/pexels-charlotte-may-5824877.jpg);
  background-size: cover;
  // background-color: ${COLOR_2};
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 90vh;
`;

export const PlantCardsContainer = styled.div`
  margin-top: 32px;
  margin-bottom: 32px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  gap: 16px;
`;
