import api from "./axios";

// CREATE TICKET
export const createTicket = (ticket) =>
  api.post("/tickets", ticket);

// GET MY TICKETS
export const getMyTickets = (page = 0, size = 10) =>
  api.get(`/tickets/my?page=${page}&size=${size}`);

// GET ASSIGNED TICKETS
export const getAssignedTickets = (page = 0, size = 10) =>
  api.get(`/tickets/assigned?page=${page}&size=${size}`);

// UPDATE TICKET STATUS
export const updateTicketStatus = (ticketId, status) =>
  api.put(`/tickets/${ticketId}/status?status=${status}`);