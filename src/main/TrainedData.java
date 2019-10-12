package main;

import java.util.ArrayList;

public class TrainedData {
    private static TrainedData instance = null;
    ArrayList<TrainedElement> arrayOfTrainedElements = new ArrayList<>();

    public static TrainedData getInstance() {
        if (instance == null) {
            instance = new TrainedData();
        }
        return instance;
    }

    TrainedData() {

    }

    public ArrayList<TrainedElement> getArrayOfTrainedElements() {
        return arrayOfTrainedElements;
    }
}
