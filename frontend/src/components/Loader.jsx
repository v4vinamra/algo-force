import React from "react";
import { RotatingLines } from "react-loader-spinner";

const Loader = () => {
  return (
    <div className="loader-overlay">
      <RotatingLines
        strokeColor="#4f46e5"
        strokeWidth="4"
        animationDuration="0.75"
        width="60"
        visible={true}
      />
    </div>
  );
};

export default Loader;
