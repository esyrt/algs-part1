import java.util.Iterator;
public class Deque<Item> implements Iterable<Item> {
           private class Node
           {
                   private Item item;
                   private Node next;
                   private Node prev;
           }

           private Node head;
           private Node tail;

           private int N;
        
           public Deque()                           // construct an empty deque
           {
                   head = null;
                   tail = head;
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
           public void addFirst(Item item)          // insert the item at the front
           {
                   if (item == null)
                           throw new java.lang.NullPointerException();
                   Node oldhead = head;
                   head = new Node();
                   head.item = item;
                   head.next = oldhead;
                   if (isEmpty()) tail = head;
                   else oldhead.prev = head;
                   N++;

           }   
           public void addLast(Item item)           // insert the item at the end
           {
                   if (item == null)
                           throw new java.lang.NullPointerException();
                   Node oldtail = tail;
                   tail = new Node();
                   tail.item = item;
                   tail.prev = oldtail;
                   if (isEmpty()) head = tail;
                   else oldtail.next = tail;
                   N++;

           }                                 
           public Item removeFirst()                // delete and return the item at the front
           {
                   if (isEmpty())
                           throw new java.util.NoSuchElementException("no such element!");
                   Node oldhead = head;
                   head = head.next;
                   N--;
                   if (N == 0) tail = head;
                   return oldhead.item;
           }              
           public Item removeLast()                 // delete and return the item at the end
           {
                   if (isEmpty())
                           throw new java.util.NoSuchElementException("no such element!");
                   Node oldtail = tail;
                   tail = tail.prev;
                   N--;
                   if (N == 0) head = tail;
                   return oldtail.item;
           }
           public Iterator<Item> iterator()         // return an iterator over items in order from front to end
           {
                   return new ListIterator();
           }
           private class ListIterator implements Iterator<Item>
           {
                   private Node current = head;
                   public boolean hasNext()
                   {
                           return current != null;
                   }
                   public void remove()
                   {
                           throw new UnsupportedOperationException("unsupport this operate!");
                   }
                   public Item next()
                   {
                           if (current == null)
                                   throw new java.util.NoSuchElementException("no such element!");
                           Item item = current.item;
                           current = current.next;
                           return item;
                   }
           }
           public static void main(String[] args)   // unit testing
           {
                   Deque<Integer> deq = new Deque<Integer>();
                   deq.addFirst(3);
                   deq.addFirst(4);
                   deq.addFirst(5);
                   deq.addLast(6);
                   deq.addLast(8);
                   for (int d : deq) StdOut.print(d);
                   StdOut.println();
                   deq.removeFirst();
                   deq.removeFirst();
                   deq.removeFirst();
                   deq.removeFirst();
                   deq.removeFirst();
                   for (int d : deq) StdOut.print(d);
                   StdOut.println();


           }
}
