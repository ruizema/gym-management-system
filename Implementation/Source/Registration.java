public class Registration extends DataRecord {

    public Registration(String[] data) {
        super(data);
    }

    public int getEmployeeId() {
        return Integer.parseInt(data[2]);
    }
}
