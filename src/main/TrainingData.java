package main;

import java.util.ArrayList;

public class TrainingData {
    private static TrainingData instance = null;
    private static ArrayList<TrainingElement> listOfTrainingElements = new ArrayList<>();

    public static TrainingData getInstance() {
        if (instance == null) {
            instance = new TrainingData();
        }
        return instance;
    }

    TrainingData() {

    }

    public void storeTrainingElement(TrainingElement element) {
        if (element != null) {
            listOfTrainingElements.add(element);
            Log.l("NeuralNetworkLog:: storeTrainingElement");
            Log.l("NeuralNetworkLog:: number of elements: " + listOfTrainingElements.size());
        }
    }

    public ArrayList<TrainingElement> getTrainingElements() {
        return listOfTrainingElements;
    }
}
