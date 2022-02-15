import axios from "axios";
import API_BASE_URL from "@/config";

function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}
export { apiInstance };
