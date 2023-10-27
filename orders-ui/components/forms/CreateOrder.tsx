'use client';

import { nextLocalStorage } from '@/lib/utils';
import { Order } from '@/model/order';
import { OrderItem } from '@/model/order-item';
import { Product } from '@/model/product';
import { User } from '@/model/user';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import Items from '../lists/Items';
import AddProduct from './AddProduct';

const CreateOrder = () => {
  const router = useRouter();

  const authToken = nextLocalStorage()?.getItem('auth_token');

  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    if (!authToken) {
      router.push('/login');
    }
  }, [router, authToken]);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const userData: string | null | undefined =
      nextLocalStorage()?.getItem('user');

    let orderUser!: User;

    if (userData) {
      const { id, name }: User = JSON.parse(userData);
      orderUser = {
        id: id,
        name: name,
      };
    }

    const orderItem: OrderItem = {
      quantity: products.length,
      products: products,
    };

    const newOrder: Order = {
      user: orderUser,
      items: [orderItem],
    };

    createOrder(newOrder);
  };

  const createOrder = async (order: Order) => {
    const res = await fetch('http://localhost:8080/api/orders', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${nextLocalStorage()?.getItem('auth_token')}`,
      },
      body: JSON.stringify(order),
    });

    if (!res.ok) {
      return;
    }

    // clear products list
    setProducts([]);

    router.push('/orders');
  };

  return (
    <div>
      <h1>Create order</h1>
      <form onSubmit={handleSubmit}>
        <AddProduct setProducts={setProducts} />
        <Items products={products} />
        <button type='submit'>Create order</button>
      </form>
    </div>
  );
};

export default CreateOrder;
