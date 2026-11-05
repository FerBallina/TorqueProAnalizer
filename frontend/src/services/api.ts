import axios from "axios";

export const api = axios.create({

  baseURL: "https://bookish-telegram-xrrw5xj6wvpx3qw9-8080.app.github.dev/api",

  timeout: 90000

});