import { useCallback, useEffect, useMemo, useState } from "react";
import { toast } from "react-toastify";
import { InputAdornment, TextField } from "@mui/material";
import { debounce } from "lodash";
import SearchIcon from "@mui/icons-material/Search";
import CloseIcon from "@mui/icons-material/Close";
import { Plant } from "../../model/Plant";
import PlantApi from "../../api/PlantApi";
import { CLOSE_TIME } from "../../constants/constants";

interface SearchPlantsProps {
  updatePlants: (foundPlants: Plant[]) => void;
}

export default function SearchPlants({ updatePlants }: SearchPlantsProps) {
  const [searchTerm, setSearchTerm] = useState<string>("");
  // const [plants, setPlants] = useState<Plant[]>([]);
  const [shouldSearch, setShouldSearch] = useState<boolean>(false);

  const searchPlantsByName = useCallback(async (searchName: string) => {
    try {
      const response = await PlantApi.searchPlantsByName(searchName);
      updatePlants(response.data);
      // console.log("response.data", response.data);
    } catch (error) {
      toast.error("An error occured when trying to connect to server", {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: CLOSE_TIME,
      });
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const debouncedSearch = useMemo(
    () =>
      debounce((searchName: string) => {
        return searchPlantsByName(searchName);
      }, 1200),
    [searchPlantsByName]
  );

  useEffect(() => {
    if (shouldSearch) {
      debouncedSearch(searchTerm);
      setShouldSearch(false);
    }
    if (!searchTerm) {
      updatePlants([]);
      // onSelectedUser(null);
    }
  }, [searchTerm, debouncedSearch, shouldSearch, updatePlants]);

  const onSearchTermChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setSearchTerm(e.target.value);
    if (e.target.value.length > 3) {
      setShouldSearch(true);
    }
  };

  const onClearClick = () => {
    setSearchTerm("");
    updatePlants([]);
  };

  return (
    <TextField
      color="primary"
      // sx={{ backgroundColor: "white" }}
      fullWidth
      id="user_search_field"
      name="plants_serch_field"
      // label="Search e-mail"
      value={searchTerm}
      onChange={onSearchTermChange}
      placeholder="Search plants"
      // size={textFieldSize}
      InputProps={{
        startAdornment: (
          <InputAdornment position="start">
            <SearchIcon />
          </InputAdornment>
        ),
        endAdornment: (
          <InputAdornment position="end">
            <CloseIcon
              sx={{ cursor: "pointer" }}
              onClick={() => onClearClick()}
            />
          </InputAdornment>
        ),
      }}
    />
  );
}
