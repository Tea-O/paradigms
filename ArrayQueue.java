package queue;

import java.util.function.Predicate;

import java.util.Arrays;
import java.util.function.Predicate;

//Model: a[1]..a[n] && first elem_0 in - first elem_0 out
//Invariant for i=1..n a[i] != null && head == first elem && tail == last elem
//Let immutable(n): for i=1..n for j=head..tail a'[i] == a[j]
public class ArrayQueue extends AbstractQueue {
    private final int startSize = 5;
    private int head;
    private int tail;


    public ArrayQueue() {
        this.elements = new Object[startSize];
    }
    private Object[] elements;

    //Pred: element != null
    @Override
    protected void enqueueImpl(final Object element) {
        ensureCapacity(size() + 1);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }
    //Post: n' = n + 1 && a[n'] == newElement && for i=1..n': for j=head..tail: a'[i] = a[j]

    //Pred: for i=1..n a[i] != null
    private void ensureCapacity(int size) {
        if (size >= elements.length) {
            int newSize = size - 1;
            Object[] tempObj = new Object[2 * newSize];
            for (int i = 0; i < newSize; i++) {
                tempObj[i] = elements[(head + i) % elements.length];
            }
            elements = Arrays.copyOf(tempObj, 2 * elements.length);
            tail = newSize;
            head = 0;
        }
    }
    //Post: size >= elements.length ? elements'.length == 2 * elements.length && tail == newSize && head = 0:
    //nothing. && immutable(n)

    //Pred: n >= 1
    @Override
    public Object elementImpl() {
        return elements[head];
    }
    //Post: a[head] == first elem of queue && immutable(n)

    //Pred: n >= 1
    @Override
    public Object dequeueImpl(int globalSize) {
        Object result = elements[head];
        head = (head + 1) % elements.length;
        return result;
    }
    //Post: n' == n - 1 && R = a[n] && immutable(n')

    //Pred: True
    @Override
    public void clearImpl() {
        Object[] newElements = new Object[startSize];
        tail = head = 0;
        elements = newElements;
    }
    //Post: n == 0

    //Pred x != null
    @Override
    protected int countImpl(Predicate<Object> pred, int counter) {
        for (int i = head; i < globalSize + head; i++) {
            if (pred.test(elements[i % elements.length])) {
                counter++;
            }
        }
        return counter;
    }
    //Post R == count(x in elements)

    public int count(Object x) {
        int counter = 0;
        int range = globalSize + head;
        for (int i = head; i < range; i++) {
            // System.err.println(elements[i] + " ** " + x + " " + counter);
            if (elements[i % elements.length].equals(x)) {
                counter++;
            }
        }
        return counter;
    }
}
