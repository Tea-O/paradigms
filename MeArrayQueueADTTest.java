package queue;

public class MeArrayQueueADTTest {

    public static void main(String[] args) {
        ArrayQueueADT queueADT1 = new ArrayQueueADT();
        ArrayQueueADT queueADT2 = new ArrayQueueADT();
        for (int i = 0; i < 7; i++) {
            ArrayQueueADT.enqueue(queueADT1, "que1" + i);
            System.out.println(" " + ArrayQueueADT.element(queueADT1) + " " + ArrayQueueADT.size(queueADT1));
            ArrayQueueADT.enqueue(queueADT2, "que2" + i);
            System.out.println(ArrayQueueADT.element(queueADT2) + " " + ArrayQueueADT.size(queueADT2));
        }

        while (!ArrayQueueADT.isEmpty(queueADT1)) {
            System.out.println(ArrayQueueADT.element(queueADT1) + " " + ArrayQueueADT.size(queueADT1));
            System.out.println(ArrayQueueADT.dequeue(queueADT1) + " " + ArrayQueueADT.size(queueADT1));
            if (ArrayQueueADT.size(queueADT1) == 2) {
                ArrayQueueADT.clear(queueADT1);
                System.out.println(ArrayQueueADT.size(queueADT1));
            }
        }

        while (ArrayQueueADT.size(queueADT2) > 0) {
            System.out.println(ArrayQueueADT.element(queueADT2) + " " + ArrayQueueADT.size(queueADT2));
            System.out.println(ArrayQueueADT.dequeue(queueADT2) + " " + ArrayQueueADT.size(queueADT2));
            if (ArrayQueueADT.size(queueADT2) == 4) {
                ArrayQueueADT.clear(queueADT2);
                System.out.println(ArrayQueueADT.size(queueADT2));
            }
        }

        System.out.println();

        for (int i = 0; i < 7; i++) {
            ArrayQueueADT.enqueue(queueADT1, "que1" + i);
            System.out.println(ArrayQueueADT.element(queueADT1) + " " + ArrayQueueADT.size(queueADT1));
            System.out.println(ArrayQueueADT.dequeue(queueADT1) + " " + ArrayQueueADT.size(queueADT1));
        }
        System.out.println();

        for (int i = 0; i < 7; i++) {
            ArrayQueueADT.enqueue(queueADT2, "que2" + i);
            System.out.println(ArrayQueueADT.element(queueADT2) + " " + ArrayQueueADT.size(queueADT2));
            System.out.println(ArrayQueueADT.dequeue(queueADT2) + " " + ArrayQueueADT.size(queueADT2));
        }
        System.out.println();

        for (int i = 0; i < 7; i++) {
            ArrayQueueADT.enqueue(queueADT1, "que1" + i);
            System.out.println(ArrayQueueADT.element(queueADT1) + " " + ArrayQueueADT.size(queueADT1));
        }

        for (int i = 0; i < 3; i++) {
            System.out.println(ArrayQueueADT.dequeue(queueADT1) + " " + ArrayQueueADT.size(queueADT1));
        }

        for (int i = 13; i < 15; i++) {
            ArrayQueueADT.enqueue(queueADT1, "lastTest" + i);
            System.out.println(ArrayQueueADT.element(queueADT1) + " " + ArrayQueueADT.size(queueADT1));
        }


        for (int i = 0; i < 7; i++) {
            ArrayQueueADT.enqueue(queueADT2, "lastTest" + i);
            System.out.println(ArrayQueueADT.element(queueADT2) + " " + ArrayQueueADT.size(queueADT2));
        }

        for (int i = 0; i < 3; i++) {
            System.out.println(ArrayQueueADT.dequeue(queueADT2) + " " + ArrayQueueADT.size(queueADT2));
        }

        for (int i = 13; i < 15; i++) {
            ArrayQueueADT.enqueue(queueADT2, "lastTest" + i);
            System.out.println(ArrayQueueADT.element(queueADT2) + " " + ArrayQueueADT.size(queueADT2));
        }
    }
}
