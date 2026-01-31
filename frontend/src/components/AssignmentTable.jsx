import {
  Table, TableHead, TableRow, TableCell, TableBody, Paper
} from "@mui/material";

export default function AssignmentTable({ assignments }) {
  return (
    <Paper>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Employee</TableCell>
            <TableCell>Allocation %</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {assignments.map(a => (
            <TableRow key={a.id}>
              <TableCell>{a.employee.name}</TableCell>
              <TableCell>{a.allocationPercentage}%</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
  );
}