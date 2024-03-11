import { useState } from "react";
import SearchPlants from "../../components/search-plant/SearchPlants";
import { Plant } from "../../model/Plant";

export default function PlantCatalogue() {
  const [plants, setPlants] = useState<Plant[]>([]);

  const updatePlants = (foundPlants: Plant[]) => {
    setPlants(foundPlants);
  };

  return (
    <>
      <SearchPlants updatePlants={updatePlants} />
      {plants.map((plant) => (
        <div key={plant.id}>{plant.name}</div>
      ))}
    </>
  );
}
