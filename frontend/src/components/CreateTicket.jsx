import { useEffect, useState } from "react";
import api from "../api/axios";
import { createTicket } from "../api/ticketApi";

export default function CreateTicket({ onTicketCreated }) {
  const [projects, setProjects] = useState([]);
  const [form, setForm] = useState({
    title: "",
    description: "",
    priority: "MEDIUM",
    type: "ESCALATION",
    projectId: "",
  });

  useEffect(() => {
    loadProjects();
  }, []);

  const loadProjects = async () => {
    try {
      const res = await api.get("/projects");
      setProjects(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load projects");
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await createTicket(form);
      alert("Ticket created successfully");
      onTicketCreated();
      setForm({
        title: "",
        description: "",
        priority: "MEDIUM",
        type: "ESCALATION",
        projectId: "",
      });
    } catch (err) {
      console.error(err);
      alert("Failed to create ticket");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>Create Ticket</h3>

      <input
        name="title"
        placeholder="Title"
        value={form.title}
        onChange={handleChange}
        required
      />
      <br /><br />

      <textarea
        name="description"
        placeholder="Description"
        value={form.description}
        onChange={handleChange}
        required
      />
      <br /><br />

      <select name="priority" value={form.priority} onChange={handleChange}>
        <option value="LOW">LOW</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="HIGH">HIGH</option>
        <option value="CRITICAL">CRITICAL</option>
      </select>
      <br /><br />

      <select name="type" value={form.type} onChange={handleChange}>
        <option value="ESCALATION">ESCALATION</option>
        <option value="DELIVERY">DELIVERY</option>
      </select>
      <br /><br />

      <select
        name="projectId"
        value={form.projectId}
        onChange={handleChange}
        required
      >
        <option value="">Select Project</option>
        {projects.map((p) => (
          <option key={p.id} value={p.id}>
            {p.name}
          </option>
        ))}
      </select>
      <br /><br />

      <button type="submit">Create Ticket</button>
    </form>
  );
}