package main;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        // Load training data into main.TrainingData class
        loadTrainingData();

        long timeElapsed;
        long lastUpdateTime = 0;
        long currentTime;
        long startTime = System.currentTimeMillis();

        DunModrGraphics dunModrGraphics = new DunModrGraphics();
        dunModrGraphics.createJFrame();
        dunModrGraphics.createJPanel();
        dunModrGraphics.addJPanelToJFrame();

        NeuralNetwork neuralNetwork;

        ApplicationStatus.getInstance().setApplicationRunning(true);

        if (!TrainingData.getInstance().getTrainingElements().isEmpty()) {
            TrainingElement trainingElement = TrainingData.getInstance().getTrainingElements().get(0);
            Log.l("NeuralNetworkLog:: Creating neural network. Inputs size: " + trainingElement.getImage().getHeight()  + "x" + trainingElement.getImage().getWidth());

            neuralNetwork = new NeuralNetwork(trainingElement.getImage().getHeight() * trainingElement.getImage().getWidth(),
                    4, 300, 0.01, 10);

            double error = 1.0;
            int iteration = 0;

            while (ApplicationStatus.getInstance().isApplicationRunning()) {
                if (error > 0.1 && ApplicationStatus.getInstance().getStatus() == ApplicationStatus.Status.TRAINING) {
                    error = neuralNetwork.train();
                    TrainedData.getInstance().getArrayOfTrainedElements().add(new TrainedElement(error, -1));
                    iteration++;
                } else {
                    testNeuralNetwork(neuralNetwork);

                    try {
                        Thread.sleep(1000 / Parameters.getInstance().getFramesPerSecond());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //Compute the time elapsed since the last frame
                currentTime = System.currentTimeMillis();
                timeElapsed = currentTime - lastUpdateTime;

                dunModrGraphics.updateFrame(timeElapsed);
                lastUpdateTime = currentTime;
            }

            Log.l("NeuralNetworkLog:: Number of training iterations: " + iteration);
            Log.l("NeuralNetworkLog:: Elapsed time: " + (System.currentTimeMillis() - startTime) + " ms");
        }

        System.exit(0);
    }

    private static void testNeuralNetwork(NeuralNetwork neuralNetwork) {
        double[] testImage;
        for (int i = 0; i < 10; i++) {
            try {
                testImage = Utils.bufferedImageToArrayOfPixels(ImageIO.read(new File("ExtraData/test_digit_" + i + "_01.png")));
                Log.l("\nNeuralNetworkLog:: Test image " + i + " has been detected as number " + neuralNetwork.predictValue(testImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadTrainingData() {
        TrainingElement trainingElement;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                String fileName = "ExtraData/digit_" + i + "_0" + (j + 1) + ".png";
                trainingElement = null;
                try {
                    trainingElement = new TrainingElement(ImageIO.read(new File(fileName)), i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (trainingElement != null) {
                    TrainingData.getInstance().storeTrainingElement(trainingElement);
                }
            }
        }
    }
}
