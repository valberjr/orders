'use client';

import { Product } from '@/model/product';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

const AddProduct = ({
  setProducts,
}: {
  setProducts: React.Dispatch<React.SetStateAction<Product[]>>;
}) => {
  const router = useRouter();

  const [nameInput, setNameInput] = useState('');

  const [priceInput, setPriceInput] = useState('');

  useEffect(() => {
    const authToken: string | null | undefined =
      localStorage.getItem('auth_token');
    if (!authToken) {
      router.push('/login');
    }
  }, []);

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

export default AddProduct;
