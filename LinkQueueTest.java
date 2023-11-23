package queue;

public class LinkQueueTest {
    public static void main(String[] args) {
        LinkedQueue queue1 = new LinkedQueue();
        LinkedQueue queue2 = new LinkedQueue();
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
    }
}
