'use client';

import { Product } from '@/model/product';
import { useEffect, useState } from 'react';

interface ItemsProps {
  products: Product[];
}

const Items = ({ products }: ItemsProps) => {
  const [quantity, setQuantity] = useState(products.length);

  useEffect(() => {
    setQuantity(products.length);
  }, [products]);

  return (
    <div>
      <ul>
        {products.map((product, index) => (
          <li key={index}>
            {product.name} - R${product.price}
          </li>
        ))}
      </ul>
      <p>Total of items: {quantity} </p>
    </div>
  );
};

export default Items;
