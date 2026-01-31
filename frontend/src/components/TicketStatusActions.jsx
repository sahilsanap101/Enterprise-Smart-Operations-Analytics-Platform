import { Button, Stack } from "@mui/material";

export default function TicketStatusActions({ ticket, onUpdate }) {
  return (
    <Stack direction="row" spacing={1}>
      {ticket.status === "OPEN" && (
        <Button size="small" onClick={() => onUpdate(ticket.id, "IN_PROGRESS")}>
          Start
        </Button>
      )}
      {ticket.status === "IN_PROGRESS" && (
        <Button size="small" onClick={() => onUpdate(ticket.id, "RESOLVED")}>
          Resolve
        </Button>
      )}
      {ticket.status === "RESOLVED" && (
        <Button size="small" onClick={() => onUpdate(ticket.id, "CLOSED")}>
          Close
        </Button>
      )}
    </Stack>
  );
}