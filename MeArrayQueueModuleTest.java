package queue;

public class MeArrayQueueModuleTest {

    public static void main(String[] args) {

        for (int i = 0; i < 7; i++) {
            ArrayQueueModule.enqueue("test" + i);
            System.out.println(ArrayQueueModule.element() + " " + ArrayQueueModule.size());
        }

        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.element() + " " + ArrayQueueModule.size());
            System.out.println(ArrayQueueModule.dequeue() + " " + ArrayQueueModule.size());
            if (ArrayQueueModule.size() == 2) {
                ArrayQueueModule.clear();
                System.out.println(ArrayQueueModule.size());
            }
        }

        System.out.println();

        for (int i = 0; i < 7; i++) {
            ArrayQueueModule.enqueue("secondTest" + i);
            System.out.println(ArrayQueueModule.element() + " " + ArrayQueueModule.size());
            System.out.println(ArrayQueueModule.dequeue() + " " + ArrayQueueModule.size());
        }

        System.out.println();

        for (int i = 0; i < 7; i++) {
            ArrayQueueModule.enqueue("lastTest" + i);
            System.out.println(ArrayQueueModule.element() + " " + ArrayQueueModule.size());
        }

        for (int i = 0; i < 3; i++) {
            System.out.println(ArrayQueueModule.dequeue() + " " + ArrayQueueModule.size());
        }

        for (int i = 13; i < 15; i++) {
            ArrayQueueModule.enqueue("lastTest" + i);
            System.out.println(ArrayQueueModule.element() + " " + ArrayQueueModule.size());
        }
    }
}
