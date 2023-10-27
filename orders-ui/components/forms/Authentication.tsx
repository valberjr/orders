'use client';

import { User } from '@/model/user';
import { useRouter } from 'next/navigation';
import { useState } from 'react';

const Authentication = () => {
  const router = useRouter();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const res = await fetch('http://localhost:8080/api/auth/signin', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        username,
        password,
      }),
    });

    const data = await res.json();

    if (!res.ok) {
      return;
    }

    localStorage.setItem('auth_token', data.token);

    const user: User = {
      id: data.userId,
      name: data.name,
    };

    localStorage.setItem('user', JSON.stringify(user));

    router.push('/orders');
  };

  return (
    <div>
      <h1>Login</h1>
      <form onSubmit={handleSubmit}>
        <input
          type='text'
          name='username'
          placeholder='Username'
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type='password'
          name='password'
          placeholder='Password'
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type='submit'>Login</button>
      </form>
    </div>
  );
};

export default Authentication;
