public class Session extends DataRecord {
    public Session(String[] data) {
        super(data);
    }

    public int getPrice() {
        return Integer.parseInt(data[9]);
    }

    // TODO: generate sessionId
    public int getSessionId() {
        return Integer.parseInt(data[8]);
    }
}
