package queue;

import java.util.Arrays;
import java.util.Objects;

//Model: a[1]..a[n] && first elem_0 in - first elem_0 out
//Invariant for i=1..n a[i] != null && head == first elem && tail == last elem
//Let immutable(n): for i=1..n for j=head..tail a'[i] == a[j]
public class ArrayQueueADT {
    private int globalSize;
    private int startSize = 5;
    private int tail;
    private int head;
    private Object[] elements = new Object[startSize];

    /*    public static ArrayQueueADT create() {
            int startSize = 5;
            ArrayQueueADT queue = new ArrayQueueADT();
            queue.elements = new Object[startSize];
            return queue;
        }*/
    //Pred: element != null
    public static void enqueue(final ArrayQueueADT queueADT, final Object element) {
        Objects.requireNonNull(element);
        queueADT.ensureCapacity(queueADT, size(queueADT) + 1);
        queueADT.elements[queueADT.tail] = element;
        queueADT.tail = (queueADT.tail + 1) % queueADT.elements.length;
        queueADT.globalSize++;
    }
    //Post: n' = n + 1 && a[n'] == newElement && for i=1..n': for j=head..tail: a'[i] = a[j]

    //Pred: for i=1..n a[i] != null
    private static void ensureCapacity(final ArrayQueueADT queueADT, int size) {
        if (size >= queueADT.elements.length) {
            int newSize = size - 1;
            Object[] tempObj = new Object[2 * newSize];
            for (int i = 0; i < newSize * 2; i++) {
                tempObj[i] = queueADT.elements[(queueADT.head + i) % queueADT.elements.length];
            }
            queueADT.elements = Arrays.copyOf(tempObj, 2 * queueADT.elements.length);
            queueADT.tail = newSize;
            queueADT.head = 0;
        }
    }
    //Post: size >= elements.length ? elements'.length == 2 * elements.length && tail == newSize && head = 0:
    //nothing. && immutable(n)

    //Pred: True
    public static int size(final ArrayQueueADT queueADT) {
        return queueADT.globalSize;
    }
    //Post: R == n && n' == n && immutable(n)

    //Pred: n >= 1
    public static Object element(final ArrayQueueADT queueADT) {
        return queueADT.elements[queueADT.head];
    }
    //Post: a[head] == first elem of queue && immutable(n)

    //Pred: n >= 1
    public static Object dequeue(final ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        Object result = queueADT.elements[queueADT.head];
        queueADT.globalSize--;
        queueADT.head = (queueADT.head + 1) % queueADT.elements.length;
        return result;
    }
    //Post: n' == n - 1 && R = a[n] && immutable(n')

    //Pred: True
    public static void clear(final ArrayQueueADT queueADT) {
        Object[] tempElements = new Object[queueADT.startSize];
        queueADT.head = 0;
        queueADT.tail = 0;
        queueADT.globalSize = 0;
        queueADT.elements = tempElements;
    }
    //Post: n == 0\
    //Pred: True
    public static boolean isEmpty(final ArrayQueueADT queueADT) {
        return queueADT.head == queueADT.tail;
    }
    //Post: R == (n > 0) && immutable(n)

    //Pred x != null
    public static int count(final ArrayQueueADT queueADT, Object x) {
        int counter = 0;
        int range = queueADT.globalSize + queueADT.head;
        for (int i = queueADT.head; i < range; i++) {
            // System.err.println(elements[i] + " ** " + x + " " + counter);
            if (queueADT.elements[i % queueADT.elements.length].equals(x)) {
                counter++;
            }
        }
        return counter;
    }
    //Post R == count(x in elements) && n` == n && immutable(n)
}


