import api from "./axios";

export const createTicket = (ticket) => {
  return api.post("/tickets", ticket);
};

export const getMyTickets = (page = 0, size = 10, sortBy = "createdAt", direction = "DESC") => {
  return api.get(
    `/tickets/my?page=${page}&size=${size}&sortBy=${sortBy}&direction=${direction}`
  );
};
