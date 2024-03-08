import { authorizedApi } from "../hoc/withAxiosIntercepted";
import { UsersPlant } from "../model/api/UsersPlant";

export default class UsersPlantApi {
  static getUsersPlants = async (userId: string) =>
    authorizedApi.get<UsersPlant[]>(`/api/users_plant/${userId}`);

  static getNotifications = async (userId: string) =>
    authorizedApi.get<number[]>(`/api/notification/${userId}`);

  static updateNextWatering = async (plantId: number) =>
    authorizedApi.patch<UsersPlant>(`/api/users_plant/${plantId}`);

  static deleteUsersPlant = async (plantId: number) =>
    authorizedApi.delete(`/api/users_plant/${plantId}`);
}
