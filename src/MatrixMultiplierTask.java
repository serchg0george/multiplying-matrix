import java.util.Date;

public class MatrixMultiplierTask implements Runnable {
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
        result[row][column] = sum;
        System.out.println("Thread " + Thread.currentThread().getId() + ": " + sum + " (Local time: " + localTime + ")");
    }
}