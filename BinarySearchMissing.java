package search;


public class BinarySearchMissing {
    //Pred int x && args.length > 0
    //Post a[R] <= x && R >= 0
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        //arr.length == args.length - 1
        int[] arr = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            arr[i - 1] = Integer.parseInt(args[i]);
        }
        if (arr.length > 0) {
            //arr.length > 0
            // args.length > 0 && left' > -1 && right' <= arr.length
            int find = binSearchRec(x, arr, 0, arr.length);
            if (find > arr.length - 1) {
                //find > arr.length - 1
                System.out.println(-find - 1);
            } else {
                //find <= arr.length - 1
                if (arr[find] == x) {
                    // arr[find] == x
                    System.out.println(find);
                } else {
                    //arr[find] != x
                    System.out.println(-find - 1);
                }
            }
        } else {
            //arr.length <= 0
            System.out.println(-1);
        }
    }

    //Pred args.length > 0 && left > -1 && right <= arr.length && left < right
    //Post x >= arr[R] && R >= 0
    private static int binSearch(int x, int[] arr, int left, int right) {
        //x > arr[left'] && x <= arr[right']
        while (right > left + 1) {
            //x > arr[left'] && x <= arr[right'] && right' > left'
            //x > arr[left'] && x <= arr[right'] && mid' = (left' + right') / 2
            int mid = (left + right) / 2;
            if (arr[mid] >= x) {
                //x > arr[left'] && x <= arr[right'] && arr[mid'] >= x
                //x >= arr[mid'] && x <= arr[right']
                right = mid;
            } else {
                //x > arr[left'] && x <= arr[right'] && arr[mid'] < x
                //x > arr[left'] && x <= arr[mid']
                left = mid;
            }
        }
        //x > arr[left'] && x <= arr[right'] && right' == left'
        // arr[right'] <= x
        return right;
    }

    //Pred args.length > 0 && left > -1 && right <= arr.length && left < right
    //Post x >= arr[R] && R >= 0
    private static int binSearchRec(int x, int[] arr, int left, int right) {
        //x >= arr[left'] && x <= arr[right'] && mid' = (left' + right') / 2
        int mid = (left + right) / 2;
        if (left == right) {
            //x >= arr[left'] && x <= arr[right'] && left' == right'
            return left;
        }
        if (x > arr[mid]) {
            /* x >= arr[left'] && x <= arr[right'] && args.length > 0 && left' > -1
                   && right' <= arr.length && left' < right' && left' == mid' + 1
                */
            // return binSearchRec(x, arr, left, mid);
            return binSearchRec(x, arr, mid + 1, right);
        } else {
            /* x >= arr[left'] && x <= arr[right'] && args.length > 0 && left' > -1
                   && right' <= arr.length && left' < right' && right' == mid'
                */
            return binSearchRec(x, arr, left, mid);
            // return binSearchRec(x, arr, mid + 1, right);
        }
    }
}
