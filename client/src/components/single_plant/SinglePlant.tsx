import {
  Button,
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Typography,
} from "@mui/material";
import { Plant } from "../../model/Plant";

interface SinglePlantProps {
  plant: Plant;
  addUsersPlant: (chosenPlantId: string) => void;
}

export default function SinglePlant({
  plant,
  addUsersPlant,
}: SinglePlantProps) {
  return (
    <Card sx={{ width: 345 }}>
      <CardMedia sx={{ height: 200 }} image={plant.photo} title={plant.name} />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {plant.name}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {plant.description}
        </Typography>
        <Typography variant="body1" color="text.secondary">
          Water every {plant.wateringInterval} days
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small" onClick={() => addUsersPlant(plant.id)}>
          Add to my collection
        </Button>
      </CardActions>
    </Card>
  );
}
