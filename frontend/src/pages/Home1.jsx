// import { useEffect, useState } from "react";
// import { useNavigate } from "react-router-dom";
// import APII from "../utils/apii";
// import { getTokenExpiration } from "../utils/jwtUtils";
// import "../components/Home.css";
// import SummaryModal from "../components/SummaryModal";
// import BlogCard from "../components/BlogCard";
// import ReportsModal from "../components/ReportModal"; 
// import "../components/SummaryModal.css";
// import "../components/BlogCard.css";

// const Home = () => {
//   const [allBlogs, setAllBlogs] = useState([]);
//   const [myBlogs, setMyBlogs] = useState([]);
//   const [summary, setSummary] = useState("");
//   const [showModal, setShowModal] = useState(false);
//   const [selectedBlog, setSelectedBlog] = useState(null);
//   const [remainingTime, setRemainingTime] = useState(null);
//   const [reportData, setReportData] = useState({});
//   const [showReportModal, setShowReportModal] = useState(false);

//   const userId = localStorage.getItem("userId");
//   const navigate = useNavigate();

//   useEffect(() => {
//     fetchAllBlogs();
//     fetchMyBlogs();
//     startLogoutTimer();
//   }, []);

//   const startLogoutTimer = () => {
//     const token = localStorage.getItem("token");
//     const expiry = getTokenExpiration(token);
//     if (!expiry) return;

//     const interval = setInterval(() => {
//       const timeLeft = expiry - Date.now();

//       if (timeLeft <= 0) {
//         clearInterval(interval);
//         logout();
//       } else {
//         setRemainingTime(timeLeft);
//       }
//     }, 1000);

//     return () => clearInterval(interval);
//   };

//   const formatTime = (ms) => {
//     const totalSeconds = Math.floor(ms / 1000);
//     const minutes = Math.floor(totalSeconds / 60)
//       .toString()
//       .padStart(2, "0");
//     const seconds = (totalSeconds % 60).toString().padStart(2, "0");
//     return `${minutes}:${seconds}`;
//   };

//   const logout = () => {
//     localStorage.removeItem("token");
//     localStorage.removeItem("userId");
//     navigate("/login");
//   };

//   const fetchAllBlogs = async () => {
//     try {
//       const res = await APII.get("/blogs?page=0&size=10");
//       setAllBlogs(res.data.content);
//     } catch (err) {
//       console.error("Failed to fetch all blogs", err);
//     }
//   };

//   const fetchMyBlogs = async () => {
//     try {
//       const res = await APII.get(`/users/${userId}/blogs`);
//       setMyBlogs(res.data);
//     } catch (err) {
//       console.error("Failed to fetch my blogs", err);
//     }
//   };

//   const handleDelete = async (blogId) => {
//     try {
//       await APII.delete(`/blogs/${blogId}`);
//       fetchAllBlogs();
//       fetchMyBlogs();
//     } catch (err) {
//       console.error("Delete failed", err);
//     }
//   };

//   const handleSummary = async (blogId) => {
//     try {
//       const res = await APII.get(`/blogs/${blogId}/summary`);
//       setSummary(res.data);
//       setShowModal(true);
//     } catch (err) {
//       console.error("Summary fetch failed", err);
//     }
//   };

//   const handleView = async (blogId) => {
//     try {
//       const res = await APII.get(`/blogs/${blogId}`);
//       setSelectedBlog(res.data);
//     } catch (err) {
//       console.error("View blog failed", err);
//     }
//   };

//   const handleGetReport = async () => {
//     try {
//       const res = await APII.get(`/top-words/${userId}`);
//       if (res.data && typeof res.data === "object") {
//         setReportData(res.data);
//         setShowReportModal(true);
//       } else {
//         console.error("Invalid report data", res.data);
//       }
//     } catch (err) {
//       console.error("Report fetch failed", err);
//     }
//   };

//   return (
//     <div className="home-container">
//       <nav className="navbar">
//         <h1>Modern Blog App</h1>
//         <div className="nav-links">
//           <a href="/home">Home</a>
//           <a href="/create">Create Blog</a>
//           <a href="/myblogs">My Blogs</a>
//           <button
//             onClick={handleGetReport}
//             className="logout-btn"
//             style={{ marginRight: "10px" }}
//           >
//             📊 Get Reports
//           </button>
//           <button onClick={logout} className="logout-btn">
//             Logout
//           </button>
//         </div>
//       </nav>

//       {remainingTime && (
//         <div className="logout-timer">
//           ⏳ You will be logged out in:{" "}
//           <strong>{formatTime(remainingTime)}</strong>
//         </div>
//       )}

//       <section className="blogs-section">
//         <h2>All Blogs</h2>
//         <div className="blogs-list">
//           {allBlogs.map((blog) => (
//             <BlogCard
//               key={blog.id}
//               blog={blog}
//               isOwner={false}
//               onSummary={handleSummary}
//               onView={handleView}
//             />
//           ))}
//         </div>
//       </section>

//       <section className="blogs-section">
//         <h2>My Blogs</h2>
//         <div className="blogs-list">
//           {myBlogs.map((blog) => (
//             <BlogCard
//               key={blog.id}
//               blog={blog}
//               isOwner={true}
//               onDelete={handleDelete}
//               onSummary={handleSummary}
//               onView={handleView}
//             />
//           ))}
//         </div>
//       </section>

//       {showModal && (
//         <SummaryModal summary={summary} onClose={() => setShowModal(false)} />
//       )}

//       {selectedBlog && (
//         <div className="view-modal">
//           <div className="view-modal-content">
//             <h2>{selectedBlog.title}</h2>
//             <p>
//               <strong>Author:</strong> {selectedBlog.author}
//             </p>
//             <p style={{ whiteSpace: "pre-line" }}>{selectedBlog.content}</p>
//             <button className="close-btn" onClick={() => setSelectedBlog(null)}>
//               Close
//             </button>
//           </div>
//         </div>
//       )}

//       {showReportModal && (
//         <ReportsModal
//           data={reportData}
//           onClose={() => setShowReportModal(false)}
//         />
//       )}
//     </div>
//   );
// };

// export default Home;
