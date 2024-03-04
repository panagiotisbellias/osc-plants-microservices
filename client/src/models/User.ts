export interface User {
  userId: number;
  username: string;
  lastname: string;
  email: string;
  role: "USER" | "ADMIN";
}
