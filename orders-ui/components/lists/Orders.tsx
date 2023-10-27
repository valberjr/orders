'use client';

import { OrderItem } from '@/model/order-item';
import { User } from '@/model/user';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import CreateOrder from '../forms/CreateOrder';

interface OrderResponse {
  id: number;
  status: string;
  user: User;
  items: OrderItem[];
}

const Orders = () => {
  const router = useRouter();

  const authToken: string | null | undefined =
    localStorage.getItem('auth_token');

  const [isLoading, setIsLoading] = useState(true);

  const [orders, setOrders] = useState<OrderResponse[]>([]);

  useEffect(() => {
    if (!authToken) {
      router.push('/login');
    } else {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    const res = await fetch('http://localhost:8080/api/orders', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${authToken}`,
      },
    });

    if (!res.ok) {
      switch (res.status) {
        case 401:
        case 403:
          localStorage.clear();
          router.push('/login');
          break;
        default:
          break;
      }
    }

    const data = await res.json();

    setOrders(data.content);
  };

  return (
    <div>
      {isLoading ? (
        <div>Loading...</div>
      ) : (
        <div>
          <h1>Orders</h1>
          <ul>
            {orders.map((order: OrderResponse) => (
              <li key={order.id}>
                {order.id} - {order.status}
              </li>
            ))}
          </ul>
          <CreateOrder refreshOrders={fetchOrders} />
        </div>
      )}
    </div>
  );
};

export default Orders;
