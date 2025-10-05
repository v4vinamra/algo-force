import React from "react";
import "./ContestsCard.css";

const ContestCard = ({ contest }) => {
  const formatDateTime = (dt) =>
    new Date(dt).toLocaleString("en-GB", {
      day: "2-digit",
      month: "short",
      hour: "2-digit",
      minute: "2-digit",
      hour12: false,
    });

  const getDuration = (minutes) => {
    const hrs = Math.floor(minutes / 60);
    const mins = minutes % 60;
    return `${hrs}h ${mins}m`;
  };

  const getStartIn = (startTime) => {
    const now = new Date();
    const start = new Date(startTime);
    const diffMs = start - now;

    if (diffMs <= 0) return "Live";

    const diffMins = Math.floor(diffMs / (1000 * 60));
    const days = Math.floor(diffMins / (60 * 24));
    const hours = Math.floor((diffMins % (60 * 24)) / 60);
    const minutes = diffMins % 60;

    if (days > 0) return `${days}d ${hours}h`;
    if (hours > 0) return `${hours}h ${minutes}m`;
    return `${minutes}m`;
  };

  const platformColorMap = {
    leetcode: "#facc15",
    codeforces: "#e83929",
    atcoder: "#f97316",
  };

  const platform = contest.platform?.toLowerCase();
  const borderColor = platformColorMap[platform] || "#e4e4e7";
  console.log(platform)

  return (
    <div
      className="contest-card-ui"
      style={{ borderLeft: `4px solid ${borderColor}` }}
    >
      <div className="card-header">
        <span className="platform-dot" style={{ backgroundColor: borderColor }} />
        <span className="platform-name">{contest.platform}</span>
      </div>

      <h3 className="contest-title">{contest.title}</h3>

      <div className="contest-details">
      <div className="time-top-row">
          
        <div className="time-row">
            <span>🕒</span>
            <span>{formatDateTime(contest.start)}</span>
        </div>

        <div className="time-row">
            <span>{getDuration(contest.duration)}</span>
        </div>
          
    </div>
    
        <div className="time-row">
          <span>Starts in {getStartIn(contest.start)}</span>
        </div>
      </div>

      <div className="contest-actions-ui">
        <button className="button-ui">Set Reminder</button>
        <button
            className="button-ui"
            onClick={() => window.open(contest.registrationUrl, "_blank", "noopener,noreferrer")}
            >
            ↗ Register
            </button>
      </div>
    </div>
  );
};

export default ContestCard;
