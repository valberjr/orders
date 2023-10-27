import { OrderItem } from './order-item';
import { User } from './user';

export interface Order {
  user: User;
  items: OrderItem[];
}
