import api from "./axios";

export const createTicket = (ticket) => {
    return api.post("/tickets", ticket);
};

export const getMTickets = () => {
    return api.get("/tickets/my");
}