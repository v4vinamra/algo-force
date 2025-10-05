import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { isLoggedIn, logout } from "../utils/jwtUtils";
import { apiService } from "../services/apiServices";
import ContestCard from "../components/ContestsCard";
import "../components/ContestPage.css";

const ContestsPage = () => {
  const [contests, setContests] = useState([]);
  const [platform, setPlatform] = useState("All");
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLoggedIn()) {
      logout();
      navigate("/login");
    }
  }, [navigate]);

  useEffect(() => {
    const loadContests = async () => {
      try {
        const data = await apiService.getContests();
        setContests(data || []);
      } catch (e) {
        console.error("Failed to load contests", e);
        localStorage.removeItem("token");
        navigate("/login");
      } finally {
        setLoading(false);
      }
    };

    if (isLoggedIn()) {
      loadContests();
    }
  }, [navigate]);

  const filtered =
    platform === "All"
      ? contests
      : contests.filter((c) => c.platform === platform);

  const platforms = [...new Set(contests.map((c) => c.platform || "Unknown"))];

  return (
      <div className="contests-page">
      <div className="contests-container">
        <h1 className="contests-heading">All Upcoming Contests</h1>

        <div className="filter-row">
          <label className="filter-label">Filter by platform:</label>
          <select
            className="filter-select"
            value={platform}
            onChange={(e) => setPlatform(e.target.value)}
          >
            <option value="All">All</option>
            {platforms.map((p, idx) => (
              <option key={p || idx} value={p}>
                {p || "Unknown"}
              </option>
            ))}
          </select>
        </div>

        {loading ? (
          <p className="loading-text">Loading contests...</p>
        ) : filtered.length === 0 ? (
          <p className="empty-text">No contests found.</p>
        ) : (
          <div className="contest-grid">
            {filtered.map((contest, index) => (
              <ContestCard
                key={contest.id || `${contest.title}-${index}`}
                contest={contest}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default ContestsPage;
