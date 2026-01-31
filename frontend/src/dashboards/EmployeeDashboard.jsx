import { useEffect, useState } from "react";
import { getMyTickets, updateTicketStatus } from "../api/ticketApi";
import TicketTable from "../components/TicketTable";
import CreateTicket from "../components/CreateTicket";

export default function EmployeeDashboard() {
  const [tickets, setTickets] = useState([]);

  const load = () => {
    getMyTickets().then(res => setTickets(res.data.data.content));
  };

  useEffect(load, []);

  return (
    <>
      <h1>Employee Dashboard</h1>
      <CreateTicket onTicketCreated={load} />
      <TicketTable tickets={tickets} onUpdate={updateTicketStatus} />
    </>
  );
}