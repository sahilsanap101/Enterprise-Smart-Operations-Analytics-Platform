import api from "./axios";

export const getAssignmentsByProject = (projectId) =>
  api.get(`/assignments/project/${projectId}`);