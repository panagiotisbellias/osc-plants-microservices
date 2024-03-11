import { authorizedApi } from "../hoc/withAxiosIntercepted";
import { Plant } from "../model/Plant";

export default class UsersPlantApi {
  static searchPlantsByName = async (searchName: string) =>
    authorizedApi.get<Plant[]>(`/api/plant/name`, {
      params: { name: searchName },
    });
}

// sk-yyjM65eee55d0e0404529
