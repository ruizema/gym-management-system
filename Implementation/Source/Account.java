public class Account extends DataRecord {
    private String[] data;
    private int id;

    public Account(String[] data) {
        super(data);
        id = generateId();
    }

    public int getId() {
        return id;
    }
}
