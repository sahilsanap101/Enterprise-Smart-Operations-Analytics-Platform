import api from "./axios";

export const createTask = (task) => {
  return api.post("/tasks", task);
};

export const getMyTasks = () => {
  return api.get("/tasks/my");
};

export const updateTaskStatus = (taskId, status) => {
  return api.put(`/tasks/${taskId}/status?status=${status}`);
};
