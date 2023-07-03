import java.util.Arrays;
import java.util.Random;

public class ParallelArraySum {
    private static final int aSize = 200000000;
    private static final int threadCount = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        int[] array = createRandomArray(aSize);

        long startTime = System.currentTimeMillis();
        long parallelSum = parallelSum(array);
        long parallelTime = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        long singleThreadSum = singleThreadSum(array);
        long singleThreadTime = System.currentTimeMillis() - startTime;

        System.out.println("Parallel Sum: " + parallelSum);
        System.out.println("Parallel Time: " + parallelTime + "ms");
        System.out.println("Single-Threaded Sum: " + singleThreadSum);
        System.out.println("Single-Threaded Time: " + singleThreadTime + "ms");
    }

    private static int[] createRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10) + 1;
        }
        return array;
    }

    private static long parallelSum(int[] array) {
        int chunkSize = array.length / threadCount;
        int[][] chunks = new int[threadCount][];

        for (int i = 0; i < threadCount; i++) {
            int startIndex = i * chunkSize;
            int endIndex = (i == threadCount - 1) ? array.length : (i + 1) * chunkSize;
            chunks[i] = Arrays.copyOfRange(array, startIndex, endIndex);
        }

        SumWorker[] workers = new SumWorker[threadCount];
        for (int i = 0; i < threadCount; i++) {
            workers[i] = new SumWorker(chunks[i]);
            workers[i].start();
        }

        long sum = 0;
        try {
            for (SumWorker worker : workers) {
                worker.join();
                sum += worker.getPartialSum();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sum;
    }

    private static long singleThreadSum(int[] array) {
        long sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    private static class SumWorker extends Thread {
        private int[] array;
        private long partialSum;

        public SumWorker(int[] array) {
            this.array = array;
        }

        @Override
        public void run() {
            partialSum = singleThreadSum(array);
        }

        public long getPartialSum() {
            return partialSum;
        }
    }
}

