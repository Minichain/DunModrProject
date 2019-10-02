import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

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


        long timeElapsed;
        long lastUpdateTime = 0;
        long currentTime;
        long startTime = System.currentTimeMillis();

        DunModrGraphics dunModrGraphics = new DunModrGraphics();
        dunModrGraphics.createJFrame();
        dunModrGraphics.createJPanel();
        dunModrGraphics.addJPanelToJFrame();

        NeuralNetwork neuralNetwork;

        if (!TrainingData.getInstance().getTrainingElements().isEmpty()) {
            TrainingElement trainingElement = TrainingData.getInstance().getTrainingElements().get(0);
            Log.l("NeuralNetworkLog:: Creating neural network. Inputs size: " + trainingElement.getImage().getHeight()  + "x" + trainingElement.getImage().getWidth());
            neuralNetwork = new NeuralNetwork(trainingElement.getImage().getHeight() * trainingElement.getImage().getWidth());

            double error = 1.0;
            int iteration = 0;
            while (error > 0.1) {
                error = neuralNetwork.train();
                TrainedData.getInstance().getArrayOfTrainedElements().add(new TrainedElement(error, -1));
                iteration++;

                //Compute the time elapsed since the last frame
                currentTime = System.currentTimeMillis();
                timeElapsed = currentTime - lastUpdateTime;

                dunModrGraphics.updateFrame(timeElapsed);
                lastUpdateTime = currentTime;

//                try {
//                    Thread.sleep(1000 / Parameters.getInstance().getFramesPerSecond());
//                } catch (InterruptedException e) {
//                    System.out.println(e);
//                    System.exit(1);
//                }
            }
            Log.l("NeuralNetworkLog:: Number of training iterations: " + iteration);
            Log.l("NeuralNetworkLog:: Elapsed time: " + (System.currentTimeMillis() - startTime) + " ms");
        }
    }
}
