import { useEffect, useState } from "react";
import api from "../api/axios";
import CreateTicket from "../components/CreateTicket";

export default function EmployeeDashboard() {
  const [profile, setProfile] = useState(null);
  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const profileRes = await api.get("/employees/me");
      const ticketsRes = await api.get("/tickets/my");

      setProfile(profileRes.data);
      setTickets(ticketsRes.data);
    } catch (err) {
      console.error(err);
      setError("Failed to load employee data");
    } finally {
      setLoading(false);
    }
  };

  // ⏳ Loading state
  if (loading) {
    return <p style={{ padding: "20px" }}>Loading dashboard...</p>;
  }

  // ❌ Error state
  if (error) {
    return (
      <p style={{ padding: "20px", color: "red" }}>
        {error}
      </p>
    );
  }

  // ❗ Safety check (should not happen, but defensive)
  if (!profile) {
    return (
      <p style={{ padding: "20px", color: "red" }}>
        Employee profile not found
      </p>
    );
  }

  return (
    <div style={{ padding: "20px" }}>
      <h1>Employee Dashboard</h1>

      {/* 👤 Profile */}
      <section>
        <h2>My Profile</h2>
        <p><b>Name:</b> {profile.name}</p>
        <p><b>Designation:</b> {profile.designation}</p>
        <p><b>Department:</b> {profile.department}</p>
        <p><b>Email:</b> {profile.email}</p>
        <p><b>Role:</b> {profile.role}</p>
      </section>

      <hr />

      {/* 🎫 Create Ticket */}
      <CreateTicket onTicketCreated={loadData} />

      <hr />

      {/* 📋 Tickets */}
      <section>
        <h2>My Tickets</h2>

        {tickets.length === 0 && <p>No tickets created yet.</p>}

        <ul>
          {tickets.map((ticket) => (
            <li key={ticket.id}>
              <b>{ticket.title}</b> — {ticket.status} — {ticket.priority}
            </li>
          ))}
        </ul>
      </section>
    </div>
  );
}
