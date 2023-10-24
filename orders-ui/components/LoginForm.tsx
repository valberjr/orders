'use client';

import { useRouter } from 'next/navigation';
import { FormEvent } from 'react';

interface LoginFormProps {
  username: string;
  password: string;
}

const LoginForm = () => {
  const router = useRouter();

  let authAPI = 'http://localhost:8080/api/auth/signin';

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const values: LoginFormProps = {
      username: event.currentTarget.username.value,
      password: event.currentTarget.password.value,
    };

    try {
      const response = await fetch(authAPI, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
      });

      if (response.ok) {
        const data = await response.json();
        // store token in local storage
        localStorage.setItem('auth_token', data.token);
        // clean the inputs
        // redirect to orders page
        router.push('/orders');
      } else {
        throw new Error('Failed to sign in');
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type='text' name='username' placeholder='Username' />
      <input type='password' name='password' placeholder='Password' />
      <button type='submit'>Submit</button>
    </form>
  );
};

export default LoginForm;
