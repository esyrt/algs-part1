import java.util.Iterator;
public class RandomizedQueue<Item> implements Iterable<Item> {

           private int tail;

           private Item[] q;

           private int N;
        
           public RandomizedQueue()                           // construct an empty randomized queue
           {
                   q = (Item[]) new Object[1];
                   tail = 0;
                   N = 0;

           }

           public boolean isEmpty()                 // is the deque empty?
           {
                   return N == 0;
           }
           public int size()                        // return the number of items on the deque
           {
                   return N;
           }

           private void resize(int n)
           {
                   Item[] newqueue = (Item[]) new Object[n];
                   for (int i = 0; i < N; i++)
                           newqueue[i] = q[i];
                   q = newqueue;
           }
              
           public void enqueue(Item item)           // add the item
           {
                   if (item == null)
                           throw new java.lang.NullPointerException();
                   if (N == q.length)
                           resize(q.length*2);
                   q[tail++] = item;
                   N++;
           }
                   
           public Item dequeue()                    // delete and return a random item
           {
                   if (isEmpty())
                           throw new java.util.NoSuchElementException();

                   int radkey = StdRandom.uniform(N);
                   Item temp = q[radkey];
                   q[radkey] = q[--tail];
                   q[tail] = null;
                   N--;
                   if (N > 0 && N == q.length/4)
                           resize(q.length/2);

                   return temp;
           }
           
           public Item sample()                     // return (but do not delete) a random item
           {
                   if (isEmpty())
                          throw new java.util.NoSuchElementException();
                   return q[StdRandom.uniform(N)];
           }
           
           public Iterator<Item> iterator()         // return an independent iterator over items in random order
           {
                   return new ArrayIterator();
           }

           private class ArrayIterator implements Iterator<Item> 
           {
                   private int i = 0;
                   private Item[] array;

                   public ArrayIterator()
                   {
                           array = (Item[]) new Object[N];
                           for (int j = 0; j < N; j++)
                                   array[j] = q[j];
                           for (int j = 0; j < N; j++)
                           {
                                   int rand = StdRandom.uniform(j+1);
                                   Item tmp = array[j];
                                   array[j] = array[rand];
                                   array[rand] = tmp;
                           }
                   }

                   public boolean hasNext()
                   {
                           return i < N;
                   }

                   public void remove()
                   {
                           throw new java.lang.UnsupportedOperationException();
                   }

                   public Item next()
                   {
                           if (i >= N) 
                                   throw new java.util.NoSuchElementException();
                           return array[i++];
                   }
           }

           public static void main(String[] args)   // unit testing
           {
                   RandomizedQueue<Integer> z = new RandomizedQueue<Integer>();
                   
                   z.enqueue(1);
                   
                   z.enqueue(4);
                   
                   z.enqueue(3);
                   
                   z.enqueue(6);
                   
                   z.enqueue(7);
                   
                   z.enqueue(8);
                   
                   z.enqueue(41);
                   
                   z.enqueue(53);
                   
                   StdOut.printf("size of queue: %d\n", z.size());
                   for (int i : z) {
                           StdOut.printf("outer i = %d\n", i);
                           
                           for (int j : z) {
                                   StdOut.printf(" %d ", j);
                           }
                           StdOut.println();
                   }
           }
}
