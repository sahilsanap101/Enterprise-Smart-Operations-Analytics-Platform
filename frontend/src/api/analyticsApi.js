import api from "./axios";

export const getTicketAnalytics = () => {
  return api.get("/analytics/tickets");
};

export const getEmployeeAnalytics = () => {
  return api.get("/analytics/employees");
};
