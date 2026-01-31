import { useEffect, useState } from "react";
import { getTicketAnalytics } from "../api/analyticsApi";
import { PieChart, Pie, Cell, Tooltip } from "recharts";

export default function AdminDashboard() {
  const [data, setData] = useState(null);

  useEffect(() => {
    getTicketAnalytics().then(res => setData(res.data));
  }, []);

  if (!data) return <p>Loading...</p>;

  const chartData = [
    { name: "Open", value: data.openTickets },
    { name: "Closed", value: data.closedTickets },
  ];

  return (
    <>
      <h1>Admin Dashboard</h1>
      <PieChart width={300} height={300}>
        <Pie data={chartData} dataKey="value" outerRadius={100}>
          <Cell fill="#ff9800" />
          <Cell fill="#4caf50" />
        </Pie>
        <Tooltip />
      </PieChart>
    </>
  );
}