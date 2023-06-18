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

        int[][] result = multiplyMatrix(matrixA, matrixB);


        // Print final result matrix
        System.out.println("Final result:");
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] multiplyMatrix(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int columnsB = matrixB[0].length;

        int[][] result = new int[rowsA][columnsB];

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < columnsB; j++) {
                executor.execute(new MatrixMultiplierTask(matrixA, matrixB, result, i, j));
            }
        }

        executor.shutdown();
        return result;
    }

}
