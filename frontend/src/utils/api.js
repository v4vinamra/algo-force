import axios from 'axios';
// process.env.REACT_APP_API_BASE_URL,
// "http://localhost:8080/"
const API = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
});

export default API;
