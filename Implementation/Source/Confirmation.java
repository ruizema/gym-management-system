public class Confirmation extends DataRecord {

    public Confirmation(String[] data) {
        super(data);
    }

    public String getDate() {
        String[] dateTime = data[0].split(" ");
        return dateTime[0];
    }

    public String getProfReport() {
        return data[2] + "\n" + data[3] + "\n" + data[4] + "\n";
    }

    public String getProfId() {
        return data[1];
    }

    public String getMemberId() {
        return data[2];
    }

    public String getActualDate() {
        return data[0];
    }

    public String getServiceId() {
        return data[3];
    }

    public String getSessionId() {
        return data[4];
    }
}
