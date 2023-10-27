'use client';

import { Product } from '@/model/product';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

interface ItemsProps {
  products: Product[];
}

const Items = ({ products }: ItemsProps) => {
  const router = useRouter();

  const [quantity, setQuantity] = useState(products.length);

  useEffect(() => {
    const authToken: string | null | undefined =
      localStorage.getItem('auth_token');
    if (!authToken) {
      router.push('/login');
    }
  }, []);

  useEffect(() => {
    setQuantity(products.length);
  }, [products]);

  const remove = (product: Product) => () => {
    const newProducts = products.filter((p) => p !== product);
    products.splice(products.indexOf(product), 1);
    setQuantity(newProducts.length);
  };

  return (
    <div>
      <ul>
        {products.map((product: Product, index) => (
          <li key={index}>
            {product.name} - R${product.price} -{' '}
            <a href='#' onClick={remove(product)}>
              remove
            </a>
          </li>
        ))}
      </ul>
      <p>Total of items: {quantity}</p>
    </div>
  );
};

export default Items;
