import {
  Table, TableHead, TableRow, TableCell, TableBody, Paper
} from "@mui/material";
import TicketStatusActions from "./TicketStatusActions";

export default function TicketTable({ tickets, onUpdate }) {
  return (
    <Paper>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Title</TableCell>
            <TableCell>Type</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Priority</TableCell>
            <TableCell>Action</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {tickets.map(t => (
            <TableRow key={t.id}>
              <TableCell>{t.title}</TableCell>
              <TableCell>{t.type}</TableCell>
              <TableCell>{t.status}</TableCell>
              <TableCell>{t.priority}</TableCell>
              <TableCell>
                <TicketStatusActions ticket={t} onUpdate={onUpdate} />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
  );
}