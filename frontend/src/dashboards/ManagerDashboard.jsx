import { useEffect, useState } from "react";
import { getTicketAnalytics, getEmployeeAnalytics } from "../api/analyticsApi";

export default function ManagerDashboard() {
  const [ticketAnalytics, setTicketAnalytics] = useState(null);
  const [employees, setEmployees] = useState([]);

  useEffect(() => {
    loadAnalytics();
  }, []);

  const loadAnalytics = async () => {
    try {
      const ticketRes = await getTicketAnalytics();
      const empRes = await getEmployeeAnalytics();

      setTicketAnalytics(ticketRes.data);
      setEmployees(empRes.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load analytics");
    }
  };

  if (!ticketAnalytics) return <p>Loading analytics...</p>;

  return (
    <div style={{ padding: "20px" }}>
      <h1>Manager Dashboard</h1>

      {/* 📊 Ticket Analytics */}
      <section>
        <h2>Ticket Overview</h2>
        <p>Total Tickets: {ticketAnalytics.totalTickets}</p>
        <p>Open Tickets: {ticketAnalytics.openTickets}</p>
        <p>Closed Tickets: {ticketAnalytics.closedTickets}</p>
        <p style={{ color: "red" }}>
          SLA Breached: {ticketAnalytics.slaBreachedTickets}
        </p>
        <p>
          Avg Resolution Time:{" "}
          {ticketAnalytics.averageResolutionHours.toFixed(1)} hrs
        </p>
      </section>

      <hr />

      {/* 👥 Employee Utilization */}
      <section>
        <h2>Employee Utilization</h2>

        {employees.length === 0 && <p>No employees found</p>}

        <ul>
          {employees.map((e) => (
            <li key={e.employeeId}>
              <b>{e.employeeName}</b> ({e.department}) —{" "}
              {e.utilizationPercentage}% —{" "}
              <span
                style={{
                  color:
                    e.utilizationStatus === "OVERLOADED"
                      ? "red"
                      : e.utilizationStatus === "UNDERUTILIZED"
                      ? "orange"
                      : "green",
                }}
              >
                {e.utilizationStatus}
              </span>
            </li>
          ))}
        </ul>
      </section>
    </div>
  );
}
