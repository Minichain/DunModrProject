import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Load training data into TrainingData class
        try {
            for (int i = 0; i < 10; i++) {
                String fileName = "ExtraData/digit_" + i + "_01.png";
                TrainingData.getInstance().storeTrainingElement(new TrainingElement(ImageIO.read(new File(fileName)), i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        NeuralNetwork neuralNetwork;
        if (!TrainingData.getInstance().getTrainingElements().isEmpty()) {
            TrainingElement trainingElement = TrainingData.getInstance().getTrainingElements().get(0);
            Log.l("NeuralNetworkLog:: Creating neural network. Inputs size: " + trainingElement.getImage().getHeight()  + "x" + trainingElement.getImage().getWidth());
            neuralNetwork = new NeuralNetwork(trainingElement.getImage().getHeight() * trainingElement.getImage().getWidth());

            double error = 1.0;
            long startTime = System.currentTimeMillis();
            int iteration = 0;
            while (error > 0.5) {
                error = neuralNetwork.train();
                iteration++;
            }
            Log.l("NeuralNetworkLog:: Number of training iterations: " + iteration);
            Log.l("NeuralNetworkLog:: Elapsed time: " + (System.currentTimeMillis() - startTime) + " ms");
        }
    }
}
