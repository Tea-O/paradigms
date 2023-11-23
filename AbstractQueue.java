package queue;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    protected int globalSize;

    abstract void enqueueImpl(Object element);

    abstract Object elementImpl();

    abstract Object dequeueImpl(int globalSize);

    abstract void clearImpl();

    abstract int countImpl(Predicate<Object> pred, int counter);

    public void enqueue(final Object element) {
        Objects.requireNonNull(element);
        enqueueImpl(element);
        globalSize++;
    }

    public int size() {
        return globalSize;
    }

    public Object element() {
        assert globalSize >= 1;
        return elementImpl();
    }

    public Object dequeue() {
        assert globalSize >= 1;
        globalSize--;
        return dequeueImpl(globalSize);
    }

    public void clear() {
        globalSize = 0;
        clearImpl();
    }

    public boolean isEmpty() {
        return globalSize == 0;
    }

    public int countIf(Predicate<Object> pred) {
        int counter = 0;
        return countImpl(pred, counter);
    }
}
