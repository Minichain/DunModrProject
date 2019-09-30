public class NeuralNetworkLayer {
    private double[][] weights;
    private double[] biases;
    private double[] neuronValues;
    private double maxValue;
    private int neuronWithMaxValue;
    private int numberOfNeurons;
    private int numberOfInputs;
    private double[] error;

    public NeuralNetworkLayer(int numberOfInputs, int numberOfNeurons) {
        this.numberOfInputs = numberOfInputs;
        this.numberOfNeurons = numberOfNeurons;
        error = new double[numberOfNeurons];
        initializeWeights();
        initializeBiases();
    }

    public void predictValue(double[] inputValues) {
//        Log.l("NeuralNetworkLog:: inputValues of size: (" + inputValues.length + ", " + 1 + ")");
        neuronValues = new double[numberOfNeurons];
        double[] matrixProduct = Utils.multiplyMatrices(weights, inputValues);
        if (matrixProduct != null) {
//            Log.l("NeuralNetworkLog:: matrixProduct of size: (" + matrixProduct.length + ", " + 1 + ")");
            maxValue = 0.0;
//            Log.l("inputValues:\n " + Utils.toString(inputValues));
//            Log.l("matrixProduct:\n " + Utils.toString(matrixProduct));
//            Log.l("biases:\n " + Utils.toString(biases));
            for (int i = 0; i < numberOfNeurons; i++) {
                neuronValues[i] = Utils.sigmoid(matrixProduct[i] + biases[i]);
                if (maxValue < neuronValues[i]) {
                    maxValue = neuronValues[i];
                    neuronWithMaxValue = i;
                }
            }
//            Log.l("neuronValues:\n " + Utils.toString(neuronValues));
//            Log.l("\n\n ");
        }
    }

    private void initializeWeights() {
        weights = new double[numberOfNeurons][numberOfInputs];
        for (int i = 0; i < numberOfNeurons; i++) {
            for (int j = 0; j < numberOfInputs; j++) {
                weights[i][j] = Math.random() / (numberOfNeurons * numberOfInputs);
            }
        }
//        Log.l("NeuralNetworkLog:: initializeWeights of size: (" + numberOfInputs + ", " + numberOfNeurons + ")");
    }

    private void initializeBiases() {
        biases = new double[numberOfNeurons];
        for (int i = 0; i < numberOfNeurons; i++) {
            biases[i] = Math.random() / biases.length;
//            biases[i] = 1;
        }
//        Log.l("NeuralNetworkLog:: initializeBiases of size: (" + numberOfNeurons + ", " + 1 + ")");
    }

    public double[] getNeuronValues() {
        return neuronValues;
    }

    public void setNeuronValues(double[] values) {
//        Log.l("set inputValues:\n " + Utils.toString(values));
        neuronValues = values;
    }

    public int getNeuronWithMaxValue() {
        return neuronWithMaxValue;
    }

    public double[][] getWeights() {
        return weights;
    }

    public void setWeights(double[][] newWeights) {
        weights = newWeights;
    }

    public double[] getBiases() {
        return biases;
    }

    public void setBiases(double[] newBiases) {
        biases = newBiases;
    }

    public int getNumberOfNeurons() {
        return numberOfNeurons;
    }

    public double[] getError() {
        return error;
    }

    public void setError(double[] newError) {
        error = newError;
    }
}
