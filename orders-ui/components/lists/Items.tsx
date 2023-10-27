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

  const remove = (index: number) => () => {
    const newProducts = products.filter((product, i) => i !== index);
    products.splice(index, 1);
    setQuantity(newProducts.length);
    return newProducts;
  };

  return (
    <div>
      <ul>
        {products.map((product, index) => (
          <li key={index}>
            {product.name} - R${product.price} -{' '}
            <a href='#' onClick={remove(index)}>
              remove
            </a>
          </li>
        ))}
      </ul>
      <p>Total of items: {quantity} </p>
    </div>
  );
};

export default Items;
