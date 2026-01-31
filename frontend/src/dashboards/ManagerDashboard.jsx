import { useEffect, useState } from "react";
import { getEmployeeAnalytics } from "../api/analyticsApi";
import { BarChart, Bar, XAxis, YAxis, Tooltip } from "recharts";

export default function ManagerDashboard() {
  const [emps, setEmps] = useState([]);

  useEffect(() => {
    getEmployeeAnalytics().then(res => setEmps(res.data));
  }, []);

  return (
    <>
      <h1>Manager Dashboard</h1>
      <BarChart width={500} height={300} data={emps}>
        <XAxis dataKey="employeeName" />
        <YAxis />
        <Tooltip />
        <Bar dataKey="utilizationPercentage" fill="#2196f3" />
      </BarChart>
    </>
  );
}