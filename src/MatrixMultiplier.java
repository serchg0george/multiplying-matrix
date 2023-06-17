import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMultiplier {
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        int[][] matrixA = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] matrixB = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };

        int[][] result = multiplyMatrices(matrixA, matrixB);

        // Вывод результатов потоков
        System.out.println("Результаты потоков:");
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }

        // Вывод результирующей матрицы
        System.out.println("Окончательный результат:");
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int columnsA = matrixA[0].length;
        int columnsB = matrixB[0].length;

        int[][] result = new int[rowsA][columnsB];

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < columnsB; j++) {
                executor.execute(new MatrixMultiplierTask(matrixA, matrixB, result, i, j));
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static class MatrixMultiplierTask implements Runnable {
        private final int[][] matrixA;
        private final int[][] matrixB;
        private final int[][] result;
        private final int row;
        private final int column;

        public MatrixMultiplierTask(int[][] matrixA, int[][] matrixB, int[][] result, int row, int column) {
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.result = result;
            this.row = row;
            this.column = column;
        }

        @Override
        public void run() {
            Date localTime = new Date();
            int sum = 0;
            for (int k = 0; k < matrixA[0].length; k++) {
                sum += matrixA[row][k] * matrixB[k][column];
            }
            System.out.println("Поток " + Thread.currentThread().getId() + ": " + sum + " (Локальное время: " + localTime + ")");
            result[row][column] = sum;
        }
    }
}