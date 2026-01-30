import { useEffect, useState } from "react";
import { getMyTasks, updateTaskStatus } from "../api/taskApi";

export default function MyTasks() {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    loadTasks();
  }, []);

  const loadTasks = async () => {
    const res = await getMyTasks();
    setTasks(res.data);
  };

  const updateStatus = async (id, status) => {
    await updateTaskStatus(id, status);
    loadTasks();
  };

  return (
    <div>
      <h3>My Tasks</h3>

      {tasks.length === 0 && <p>No assigned tasks</p>}

      <ul>
        {tasks.map(t => (
          <li key={t.id}>
            <b>{t.title}</b> — {t.status}

            {t.status !== "DONE" && (
              <>
                {t.status === "TODO" && (
                  <button onClick={() => updateStatus(t.id, "IN_PROGRESS")}>
                    Start
                  </button>
                )}
                {t.status === "IN_PROGRESS" && (
                  <button onClick={() => updateStatus(t.id, "DONE")}>
                    Complete
                  </button>
                )}
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}
