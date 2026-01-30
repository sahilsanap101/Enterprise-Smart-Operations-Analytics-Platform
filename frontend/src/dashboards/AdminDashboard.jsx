import { useEffect, useState } from "react";
import { getTicketAnalytics } from "../api/analyticsApi";

export default function AdminDashboard() {
  const [analytics, setAnalytics] = useState(null);

  useEffect(() => {
    loadAnalytics();
  }, []);

  const loadAnalytics = async () => {
    try {
      const res = await getTicketAnalytics();
      setAnalytics(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load analytics");
    }
  };

  if (!analytics) return <p>Loading admin analytics...</p>;

  return (
    <div style={{ padding: "20px" }}>
      <h1>Admin Dashboard</h1>

      <h2>Organization Ticket Health</h2>

      <ul>
        <li>Total Tickets: {analytics.totalTickets}</li>
        <li>Open Tickets: {analytics.openTickets}</li>
        <li>Closed Tickets: {analytics.closedTickets}</li>
        <li style={{ color: "red" }}>
          SLA Breaches: {analytics.slaBreachedTickets}
        </li>
      </ul>
    </div>
  );
}
