import { Product } from './product';

export interface OrderItem {
  quantity: number;
  products: Product[];
}
