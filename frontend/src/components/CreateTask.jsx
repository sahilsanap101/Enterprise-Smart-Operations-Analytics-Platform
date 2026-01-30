import { useEffect, useState } from "react";
import api from "../api/axios";
import { createTask } from "../api/taskApi";

export default function CreateTask({ onCreated }) {
  const [projects, setProjects] = useState([]);
  const [employees, setEmployees] = useState([]);

  const [form, setForm] = useState({
    title: "",
    description: "",
    projectId: "",
    employeeId: ""
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    const projRes = await api.get("/projects");
    const empRes = await api.get("/employees");

    setProjects(projRes.data);
    setEmployees(empRes.data);
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await createTask(form);
    alert("Task created");
    setForm({ title: "", description: "", projectId: "", employeeId: "" });
    onCreated();
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>Create Task</h3>

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
      />
      <br /><br />

      <select
        name="projectId"
        value={form.projectId}
        onChange={handleChange}
        required
      >
        <option value="">Select Project</option>
        {projects.map(p => (
          <option key={p.id} value={p.id}>{p.name}</option>
        ))}
      </select>
      <br /><br />

      <select
        name="employeeId"
        value={form.employeeId}
        onChange={handleChange}
        required
      >
        <option value="">Assign Employee</option>
        {employees.map(e => (
          <option key={e.id} value={e.id}>{e.name}</option>
        ))}
      </select>
      <br /><br />

      <button>Create Task</button>
    </form>
  );
}
