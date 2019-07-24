public class Session extends DataRecord {
    public Session(String[] data) {
        super(data);
    }

    public double getPrice() {
        return Double.parseDouble(data[9]);
    }

    // TODO: generate sessionId & overwrite whatever the user entered lmao
    public String getSessionId() {
        return data[8];
    }
}
