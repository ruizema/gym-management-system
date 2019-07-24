public class Session extends DataRecord {
    public Session(String[] data) {
        super(data);
    }

    public int getPrice() {
        return Integer.parseInt(data[9]);
    }

    // TODO: generate sessionId & overwrite whatever the user entered lmao
    public String getSessionId() {
        return data[8];
    }
}
