'use client';

import { nextLocalStorage } from '@/lib/utils';
import { OrderItem } from '@/model/order-item';
import { User } from '@/model/user';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

interface OrderResponse {
  id: number;
  status: string;
  user: User;
  items: OrderItem[];
}

const Orders = () => {
  const router = useRouter();

  const authToken = nextLocalStorage()?.getItem('auth_token');

  const [isLoading, setIsLoading] = useState(true);
  const [orders, setOrders] = useState<OrderResponse[]>([]);

  useEffect(() => {
    if (!authToken) {
      router.push('/login');
    } else {
      setIsLoading(false);
    }
  }, [router, authToken]);

  useEffect(() => {
    fetchOrders();
  }, [orders]);

  async function fetchOrders() {
    const res = await fetch('http://localhost:8080/api/orders', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${authToken}`,
      },
    });

    const data = await res.json();
    setOrders(data.content);
  }

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
        </div>
      )}
    </div>
  );
};

export default Orders;
