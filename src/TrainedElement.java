public class TrainedElement {
    private double error;
    private double accuracy;

    TrainedElement(double error, double accuracy) {
        this.error = error;
        this.accuracy = accuracy;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getError() {
        return error;
    }
}
