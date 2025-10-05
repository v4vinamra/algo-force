// services/apiServices.js
import APII from "../utils/apii"; // Assuming APII is an axios instance

export const apiService = {
  getContests: async () => {
    const token = localStorage.getItem("token");

    try {
      const res = await APII.get("api/users/contests/upcoming", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      return res.data; // ✅ Axios gives the response body here
    } catch (error) {
      // Handle 401 or other errors
      if (error.response && error.response.status === 401) {
        throw new Error("Unauthorized");
      }
      throw new Error("Failed to fetch contests");
    }
  },
};
