import java.util.ArrayList;

public class NeuralNetwork {
    private static int numberOfHiddenLayers = 3;
    private static int numberOfNeuronsPerHiddenLayer = 100;
    private static int numberOfOutputs = 10;
    private static double[] inputs;
    private static double[] outputs;
    private static boolean initialized = false;
    private static ArrayList<NeuralNetworkLayer> listOfNeuralNetworkLayers = new ArrayList<>();
    private static double learningFactor = 0.01;

    public NeuralNetwork(int numberOfInputs) {
        initNeuralNetwork(numberOfInputs);
    }

    private void initNeuralNetwork(int numberOfInputs) {
        NeuralNetworkLayer neuralNetworkLayer;
        if (listOfNeuralNetworkLayers.isEmpty()) {
            neuralNetworkLayer = new NeuralNetworkLayer(0, numberOfInputs);
            listOfNeuralNetworkLayers.add(neuralNetworkLayer);
            Log.l("NeuralNetworkLog:: Layer nº 1 loaded");
            for (int i = 0; i < numberOfHiddenLayers; i++) {
                neuralNetworkLayer = new NeuralNetworkLayer(neuralNetworkLayer.getNumberOfNeurons(), getNumberOfNeuronsPerHiddenLayer());
                listOfNeuralNetworkLayers.add(neuralNetworkLayer);
                Log.l("NeuralNetworkLog:: Hidden layer nº " + (i + 1) + " layer loaded");
            }
            neuralNetworkLayer = new NeuralNetworkLayer(neuralNetworkLayer.getNumberOfNeurons(), numberOfOutputs);
            listOfNeuralNetworkLayers.add(neuralNetworkLayer);
            Log.l("NeuralNetworkLog:: Output layer loaded");
        }
        initialized = true;
    }

    public void predictValue(double[] inputValues) {
//        Log.l("NeuralNetworkLog:: PredictValue. Number of inputs: " + inputValues.length);
//        for (int i = 0; i < inputValues.length; i++) {
//            Log.l("NeuralNetworkLog:: Input[" + i + "]: " + inputValues[i]);
//        }
        inputs = inputValues;
        for (int i = 0; i < listOfNeuralNetworkLayers.size(); i++) {
            if (i == 0) {   // First layer
                listOfNeuralNetworkLayers.get(i).setNeuronValues(inputValues);
            } else {        // Middle and Output layers
                listOfNeuralNetworkLayers.get(i).predictValue(listOfNeuralNetworkLayers.get(i - 1).getNeuronValues());
            }
        }
        NeuralNetworkLayer lastLayer = listOfNeuralNetworkLayers.get(listOfNeuralNetworkLayers.size() - 1);
        outputs = lastLayer.getNeuronValues();
        Log.l("NeuralNetworkLog:: Predicted value is " + lastLayer.getNeuronWithMaxValue());
    }

    public double[] getOutputs() {
        return outputs;
    }

    public double train() {
        if (!initialized) {
            Log.l("NeuralNetworkLog:: It cannot be trained. The neural network is not initialized.");
            return -1;
        }

        ArrayList<TrainingElement> trainingElements = TrainingData.getInstance().getTrainingElements();
        double[] vectorOfErrors = new double[trainingElements.size()];
        for (int i = 0; i < trainingElements.size(); i++) {
            predictValue(Utils.bufferedImageToArrayOfPixels(trainingElements.get(i).getImage()));
            double[] resultValues = getOutputs();
            double[] targetValues = new double[numberOfOutputs];

            int targetValue = trainingElements.get(i).getValue();
            for (int j = 0; j < targetValues.length; j++) {
                if (j == targetValue) {
                    targetValues[j] = 1.0;
                } else {
                    targetValues[j] = 0.0;
                }
            }

//            for (int k = 0; k < resultValues.length; k++) {
//                Log.l("NeuralNetworkLog:: resultValues[" + k + "]: " + resultValues[k]);
//            }

            Log.l("NeuralNetworkLog:: targetValue: " + trainingElements.get(i).getValue());

            computeNewWeights(targetValues);
            vectorOfErrors[i] = Utils.euclideanDistance(listOfNeuralNetworkLayers.get(listOfNeuralNetworkLayers.size() - 1).getError());
        }

        double error = Utils.getAverage(vectorOfErrors);
        Log.l("NeuralNetworkLog:: Current output Error = " + error + "\n");
        return error;
    }

    private void computeNewWeights(double[] targetValues) {
        // Starting with the output layer and ending with the first one
        for (int i = listOfNeuralNetworkLayers.size() - 1; i > 0; i--) {
            NeuralNetworkLayer currentNetworkLayer = listOfNeuralNetworkLayers.get(i);
            double[] currentNetworkLayerValues = currentNetworkLayer.getNeuronValues();
            double[] prevNetworkLayerValues;
            if (i > 1) {
                prevNetworkLayerValues = listOfNeuralNetworkLayers.get(i - 1).getNeuronValues();
            } else {
                prevNetworkLayerValues = inputs;
            }

            //Compute the error in each layer
            computeLayerError(targetValues, i);

            double[][] weights = currentNetworkLayer.getWeights();
            double[] biases = currentNetworkLayer.getBiases();

//            Log.l("Layer " + i + " Errors:\n" + Utils.toString(currentNetworkLayer.getError()));
//            Log.l("Layer " + i + " Weights:\n" + Utils.toString(currentNetworkLayer.getWeights()));
//            Log.l("Layer " + i + " Bias:\n" + Utils.toString(currentNetworkLayer.getBiases()));

//            Log.l("NeuralNetworkLog:: computeNewWeights:: computeNewWeights size " + weights.length + ", " + weights[0].length);
//            Log.l("NeuralNetworkLog:: computeNewWeights:: currentNetworkLayerValues size " + currentNetworkLayerValues.length);
//            Log.l("NeuralNetworkLog:: computeNewWeights:: prevNetworkLayerValues size " + prevNetworkLayerValues.length);

            double deltaE_deltaNeuronValue;
            for (int j = 0; j < weights.length; j++) {
                deltaE_deltaNeuronValue = computeDeltaE_deltaNeuronValue(i, j, currentNetworkLayerValues);
                for (int k = 0; k < weights[0].length; k++) {
                    weights[j][k] -= learningFactor * deltaE_deltaNeuronValue * prevNetworkLayerValues[k];
                }
                biases[j] -= learningFactor * deltaE_deltaNeuronValue;
            }

            currentNetworkLayer.setWeights(weights);
            currentNetworkLayer.setBiases(biases);
        }
    }

    private double computeDeltaE_deltaNeuronValue(int i, int j, double[] currentNetworkLayerValues) {
        return listOfNeuralNetworkLayers.get(i).getError()[j] * (currentNetworkLayerValues[j] * (1 - currentNetworkLayerValues[j]));
    }

    private void computeLayerError(double[] targetValues, int layer) {
        double[] errorVector;
        NeuralNetworkLayer currentLayer = listOfNeuralNetworkLayers.get(layer);
        if (layer == listOfNeuralNetworkLayers.size() - 1) {    //Output layer
            errorVector = new double[targetValues.length];
            for (int j = 0; j < targetValues.length; j++) {
                errorVector[j] = currentLayer.getNeuronValues()[j] - targetValues[j];
            }
        } else {
            NeuralNetworkLayer nextLayer = listOfNeuralNetworkLayers.get(layer + 1);
            errorVector = Utils.multiplyMatrices(Utils.transposeMatrix(nextLayer.getWeights()), nextLayer.getError());
        }
        currentLayer.setError(errorVector);
    }

    public static int getNumberOfNeuronsPerHiddenLayer() {
        return numberOfNeuronsPerHiddenLayer;
    }

    public static int getNumberOfHiddenLayers() {
        return numberOfHiddenLayers;
    }

    public static int getNumberOfOutputs() {
        return numberOfOutputs;
    }
}
