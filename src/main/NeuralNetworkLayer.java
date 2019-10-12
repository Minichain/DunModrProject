package main;

import java.util.Random;

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
        neuronValues = new double[numberOfNeurons];
        double[] matrixProduct = Utils.multiplyMatrices(weights, inputValues);
        if (matrixProduct != null) {
            maxValue = 0.0;
            for (int i = 0; i < numberOfNeurons; i++) {
                neuronValues[i] = ActivationFunctionUtils.activationFunction(matrixProduct[i] + biases[i]);
                if (maxValue < neuronValues[i]) {
                    maxValue = neuronValues[i];
                    neuronWithMaxValue = i;
                }
            }
        }
    }

    private void initializeWeights() {
        weights = new double[numberOfNeurons][numberOfInputs];
        Random random = new Random();
        for (int i = 0; i < numberOfNeurons; i++) {
            for (int j = 0; j < numberOfInputs; j++) {
                if (ActivationFunctionUtils.activationFunction == ActivationFunctionUtils.ActivationFunction.RELU) {
                    weights[i][j] = random.nextGaussian() * Math.sqrt(2.0 / numberOfInputs);
                } else {
                    weights[i][j] = random.nextGaussian() * Math.sqrt(1.0 / numberOfInputs);
                }
            }
        }
    }

    private void initializeBiases() {
        biases = new double[numberOfNeurons];
        for (int i = 0; i < numberOfNeurons; i++) {
            biases[i] = 0;
        }
    }

    public double[] getNeuronValues() {
        return neuronValues;
    }

    public void setNeuronValues(double[] values) {
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
