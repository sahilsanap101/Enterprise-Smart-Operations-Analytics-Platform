import TicketStatusActions from "./TicketStatusActions";

export default function TicketList({ tickets, onStatusChange }) {
  return (
    <ul>
      {tickets.map((t) => (
        <li key={t.id}>
          <b>{t.title}</b> — {t.type} — {t.status} — {t.priority}
          <br />
          <TicketStatusActions
            ticket={t}
            onUpdate={onStatusChange}
          />
        </li>
      ))}
    </ul>
  );
}import TicketStatusActions from "./TicketStatusActions";

export default function TicketList({ tickets, onStatusChange }) {
  return (
    <ul>
      {tickets.map((t) => (
        <li key={t.id}>
          <b>{t.title}</b> — {t.type} — {t.status} — {t.priority}
          <br />
          <TicketStatusActions
            ticket={t}
            onUpdate={onStatusChange}
          />
        </li>
      ))}
    </ul>
  );
}