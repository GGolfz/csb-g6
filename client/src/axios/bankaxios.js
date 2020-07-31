import axios from "axios";
const instance = axios.create({
  baseURL: "http://52.231.32.241"
});
export default instance;
