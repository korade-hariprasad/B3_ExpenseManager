package sumago.androidipt.b3expensemanagement.model;

public class AnalyticsRecord {
    String name;
    double min, max, avg, total;

    public AnalyticsRecord(String name, double min, double max, double avg, double total) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.total = total;
    }

    public AnalyticsRecord() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
