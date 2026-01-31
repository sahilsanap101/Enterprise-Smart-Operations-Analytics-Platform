import api from "./axios";

export const getAllProjects = () => api.get("/projects");
export const getMyProjects = () => api.get("/projects/my");