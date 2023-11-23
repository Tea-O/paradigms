package queue;

import java.util.function.Predicate;

public interface Queue {
    //Pred: element != null
    void enqueue(final Object element);
    //Post: n' = n + 1 && a[n'] == newElement && immutable(n')

    //Pred: True
    int size();
    //Post: immutable && R == size.queue

    //Pred: size > 0
    Object element();
    //Post: R == a[head] && immutable(n')

    //Pred: size > 0
    Object dequeue();
    //Post: R == a[head] && n' = n - 1 && immutable(n') && head = head.next

    //Pred: True
    void clear();
    //Post: n == 0

    //Pred: True
    boolean isEmpty();
    //Post: R == size > 0 ? True: False

    //Pred: x != null
    int countIf(Predicate<Object> pred);
    //Post: R == pred.count
}
