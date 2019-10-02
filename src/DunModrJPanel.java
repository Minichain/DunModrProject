import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DunModrJPanel extends JPanel {
    private long timeElapsed;
    private long fps;

    DunModrJPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
    }

    public void setTimeElapsed(long te) {
        timeElapsed = te;
        if (timeElapsed > 0) {
            fps = 1000 / timeElapsed;
        }
    }

    public void paint(Graphics g){
        super.paint(g); // cleans the panel
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());  // Fill area with background.
        g.setColor(Color.WHITE);             // Restore color for drawing.

        ArrayList<TrainedElement> trainedElements = TrainedData.getInstance().getArrayOfTrainedElements();
        int numberOfTrainedElements = trainedElements.size();
        double amplifyingFactor = 500;
        for (int i = 0; i < (Parameters.getInstance().getWindowWidth() - 1) && (i + 1) < numberOfTrainedElements; i++) {
            int x1 = Parameters.getInstance().getWindowWidth() - i - (Parameters.getInstance().getWindowWidth() / 2);
            int x2 = Parameters.getInstance().getWindowWidth() - (i + 1) - (Parameters.getInstance().getWindowWidth() / 2);
            int y1 = Parameters.getInstance().getWindowHeight() - (int)(trainedElements.get(numberOfTrainedElements - i - 1).getError() * amplifyingFactor);
            int y2 = Parameters.getInstance().getWindowHeight() - (int)(trainedElements.get(numberOfTrainedElements - (i + 1) - 1).getError() * amplifyingFactor);
            g.drawLine(x1, y1, x2, y2);
        }

        g.drawString("Error: " + trainedElements.get(numberOfTrainedElements - 1).getError(), 10, 20);
        g.drawString("Iterations: " + numberOfTrainedElements, 10, 35);
        g.drawString("FPS: " + fps, 10, 50);
    }
}