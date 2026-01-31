export default function ProjectList({ projects }) {
  return (
    <ul>
      {projects.map((p) => (
        <li key={p.id}>
          <b>{p.name}</b> — {p.status}
        </li>
      ))}
    </ul>
  );
}