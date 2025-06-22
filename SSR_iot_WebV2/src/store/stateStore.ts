import { defineStore } from "pinia";

export const userStore = defineStore("user", {
    state: () => ({
        userID: 0,
        userName: "",
        userJWT: "",
    }),
    actions: {
        constructorUser(userID: number, userName: string, userJWT: string) {
            this.userID = userID;
            this.userName = userName;
            this.userJWT = userJWT;
        }
    }
});