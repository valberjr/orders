'use client';

import { nextLocalStorage } from '@/lib/utils';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';

const Page = () => {
  const router = useRouter();

  const authToken = nextLocalStorage()?.getItem('auth_token');

  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (!authToken) {
      router.push('/login');
    } else {
      setIsLoading(false);
    }
  }, [router, authToken]);

  return (
    <div>
      {isLoading ? (
        <div>Loading...</div>
      ) : (
        <div>
          <h1>Orders</h1>
        </div>
      )}
    </div>
  );
};

export default Page;
