package queue;

public class MeArrayQueueTest {

    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        for (int i = 0; i < 7; i++) {
            queue1.enqueue("que1" + i);
            System.out.println(" " + queue1.element() + " " + queue1.size());
            queue2.enqueue("que2" + i);
            System.out.println(queue2.element() + " " + queue2.size());
        }

        while (!queue1.isEmpty()) {
            System.out.println(queue1.element() + " " + queue1.size());
            System.out.println(queue1.dequeue() + " " + queue1.size());
            if (queue1.size() == 2) {
                queue1.clear();
                System.out.println(queue1.size());
            }
        }

        while (queue2.size() > 0) {
            System.out.println(queue2.element() + " " + queue2.size());
            System.out.println(queue2.dequeue() + " " + queue2.size());
            if (queue2.size() == 4) {
                queue2.clear();
                System.out.println(queue2.size());
            }
        }
        System.out.println();

        for (int i = 0; i < 7; i++) {
            queue1.enqueue("que1" + i);
            System.out.println(queue1.element() + " " + queue1.size());
            System.out.println(queue1.dequeue() + " " + queue1.size());
        }
        System.out.println();

        for (int i = 0; i < 7; i++) {
            queue2.enqueue("que2" + i);
            System.out.println(queue2.element() + " " + queue2.size());
            System.out.println(queue2.dequeue() + " " + queue2.size());
        }
        System.out.println();
    }
}
