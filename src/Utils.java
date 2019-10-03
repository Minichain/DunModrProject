import java.awt.image.BufferedImage;

/**
 * Utils class. Only "static" methods.
 */
public class Utils {
    public static float getAverage(float[] array) {
        int length = array.length;
        float sum = 0;
        for (int i = 0; i < length; i++) {
            sum += array[i];
        }
        return (sum / length);
    }

    public static double getAverage(double[] array) {
        int length = array.length;
        double sum = 0;
        for (int i = 0; i < length; i++) {
            sum += array[i];
        }
        return (sum / length);
    }

    public static float getStandardDeviation(float[] array) {
        int length = array.length;
        float sum = 0;
        float mean = getAverage(array);
        for (int i = 0; i < length; i++) {
            sum += Math.pow(array[i] - mean, 2);
        }
        return (sum / (length - 1));
    }

    /**
     * This method takes an hexadecimal color as an input ("FCFCFCFC" or "FCFCFC") and
     * returns an array of 3 integers which are the colour in RGB (252, 252, 252).
     * */
    public static int[] hexadecimalToRgb(String hex) {
        hex = hex.replace("#", "");
        if (hex.length() == 8) {
            hex = hex.substring(2, 8);  //Transforms "FCFCFCFC" into "FCFCFC"
        }
        final int[] ret = new int[3];
        for (int i = 0; i < 3; i++) {
            ret[i] = Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return ret;
    }

    public static double[] multiplyMatrices(double[][] m1, double[] m2) {
        int m1ColLength = m1[0].length; // m1 columns length
        int m2RowLength = m2.length;    // m2 rows length
        if (m1ColLength != m2RowLength) {
            return null; // matrix multiplication is not possible
        }
        int mRRowLength = m1.length;    // m result rows length
        double[] mResult = new double[mRRowLength];
        for (int i = 0; i < mRRowLength; i++) {         // rows from m1
            for (int k = 0; k < m1ColLength; k++) {     // columns from m1
                mResult[i] += m1[i][k] * m2[k];
            }
        }
        return mResult;
    }

    public static double[][] multiplyMatrices(double[][] m1, double[][] m2) {
        int m1ColLength = m1[0].length; // m1 columns length
        int m2RowLength = m2.length;    // m2 rows length
        if (m1ColLength != m2RowLength) {
            return null; // matrix multiplication is not possible
        }
        int mRRowLength = m1.length;    // m result rows length
        int mRColLength = m2[0].length; // m result columns length
        double[][] mResult = new double[mRRowLength][mRColLength];
        for (int i = 0; i < mRRowLength; i++) {         // rows from m1
            for (int j = 0; j < mRColLength; j++) {     // columns from m2
                for (int k = 0; k < m1ColLength; k++) { // columns from m1
                    mResult[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return mResult;
    }

    public static double[][] transposeMatrix(double [][] inputMatrix){
        double[][] outputMatrix = new double[inputMatrix[0].length][inputMatrix.length];
        for (int i = 0; i < inputMatrix.length; i++) {
            for (int j = 0; j < inputMatrix[0].length; j++) {
                outputMatrix[j][i] = inputMatrix[i][j];
            }
        }
        return outputMatrix;
    }

    public static String toString(double[] vector) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < vector.length; i++) {
            result.append(String.format("[" + i + "]" + " %1.2f", vector[i]));
            result.append(", ");
        }
        return result.toString();
    }

    public static String toString(double[][] m) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < m[0].length; i++) {     //iterate over rows
            for (int j = 0; j < m.length; j++) {    //iterate over columns
                result.append(String.format("[" + j + "]" + "[" + i + "]" + " %1.2f, ", m[j][i]));
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static double[] bufferedImageToArrayOfPixels(BufferedImage bufferedImage) {
        int numberOfInputs = bufferedImage.getWidth() * bufferedImage.getHeight();
        double[] array = new double[numberOfInputs];
        int k = 0;
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                int pixel = bufferedImage.getRGB(i, j);
                int r = (pixel>>16) & 0xff;
                int g = (pixel>>8) & 0xff;
                int b = pixel & 0xff;
                double average = (r + g + b) / 3;
                array[k] = (average / 255);
                k++;
            }
        }
        return array;
    }

    public static double euclideanDistance(double[] vector) {
        double distance = 0;
        for (int i = 0; i < vector.length; i++) {
            distance += Math.pow(vector[i], 2);
        }
        return Math.sqrt(distance);
    }

    public static double euclideanDistance(double[] vector1, double[] vector2) {
        double distance = 0;
        if (vector1.length != vector2.length) {
            return -1;
        }
        for (int i = 0; i < vector1.length; i++) {
            distance += Math.pow(vector1[i] - vector2[i], 2);
        }
        return Math.sqrt(distance);
    }

    public static BufferedImage resize(BufferedImage src, int w, int h) {
        BufferedImage img =
                new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++)
            ys[y] = y * hh / h;
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

    public static double[] normalize(double[] inputVector){
        double maxValue = 0.000000000001;
        for (int i = 0; i < inputVector.length; i++) {
            if (Math.abs(inputVector[i]) > maxValue) {
                maxValue = Math.abs(inputVector[i]);
            }
        }
        for (int i = 0; i < inputVector.length; i++) {
            inputVector[i] /= maxValue;
        }
        return inputVector;
    }

    public static double[] setMinToZero(double[] inputVector){
        double minValue = 0;
        for (int i = 0; i < inputVector.length; i++) {
            if (inputVector[i] < minValue) {
                minValue = inputVector[i];
            }
        }
        for (int i = 0; i < inputVector.length; i++) {
            inputVector[i] -= minValue;
        }
        return inputVector;
    }
}
