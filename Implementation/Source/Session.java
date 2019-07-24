public class Session extends DataRecord {
    private String id;

    public Session(String[] data) {
        super(data);
        id = data[7].substring(0, 3) + data[8].substring(0, 2) + data[6].substring(data[6].length() - 2);
    }

    public double getPrice() {
        return Double.parseDouble(data[9]);
    }

    public String getSessionId() {
        return id;
    }
}
