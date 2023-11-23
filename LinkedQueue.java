package queue;

import java.util.function.Predicate;

public class LinkedQueue extends AbstractQueue {
    private static class Node {
        private final Object element;
        private Node next;

        private Node(Object element, Node next) {
            this.element = element;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;


    //Pred: element != null
    @Override
    protected void enqueueImpl(final Object element) {
        if (globalSize == 0) {
            tail = new Node(element, null);
            head = tail;
        } else {
            tail = tail.next = new Node(element, null);
        }
    }
    //Post: n' = n + 1 && a[n'] == newElement && for i=1..n': for j=head..tail: a'[i] = a[j]

    //Pred: n >= 1
    @Override
    public Object elementImpl() {
        return head.element;
    }
    //Post: a[head] == first elem of queue && immutable(n)

    //Pred: n >= 1
    @Override
    public Object dequeueImpl(int globalSize) {
        Object result = head.element;
        head = head.next;
        if (globalSize == 0) {
            tail = null;
        }
        return result;
    }
    //Post: n' == n - 1 && R = a[n] && immutable(n')

    //Pred: True
    @Override
    public void clearImpl() {
        head = tail = null;
    }
    //Post: n == 0

    @Override
    protected int countImpl(Predicate<Object> pred, int counter) {
        for (Node i = head; i != null; i = i.next) {
            if (pred.test(i.element)) {
                counter++;
            }
        }
        return counter;
    }

}
