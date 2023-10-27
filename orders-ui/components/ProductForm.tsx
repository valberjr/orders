'use client';

import { Product } from '@/model/product';
import { useState } from 'react';

const ProductForm = ({
  setProducts,
}: {
  setProducts: React.Dispatch<React.SetStateAction<Product[]>>;
}) => {
  const [nameInput, setNameInput] = useState('');
  const [priceInput, setPriceInput] = useState('');

  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();

    const newProduct: Product = {
      name: nameInput,
      price: Number(priceInput),
    };

    setProducts((prevProducts) => [...prevProducts, newProduct]);
    setNameInput('');
    setPriceInput('');
  };

  return (
    <div>
      <h3>1. Add a new product</h3>
      <input
        type='text'
        name='name'
        placeholder='Product name'
        value={nameInput}
        onChange={(e) => setNameInput(e.target.value)}
      />
      <input
        type='number'
        name='price'
        placeholder='Price'
        value={priceInput}
        onChange={(e) => setPriceInput(e.target.value)}
      />
      <button type='button' onClick={handleClick}>
        Add Product
      </button>
    </div>
  );
};

export default ProductForm;
