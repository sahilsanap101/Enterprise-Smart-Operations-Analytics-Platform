export default function AssignmentList({ assignments }) {
  return (
    <ul>
      {assignments.map((a) => (
        <li key={a.id}>
          {a.employee.name} — {a.allocationPercentage}%
        </li>
      ))}
    </ul>
  );
}