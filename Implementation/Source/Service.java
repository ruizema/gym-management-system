public class Service extends DataRecord {
    private String name;
    private String id;
    // TODO: 3 digit service id

    public Service(String[] data) {
        super(data);
        this.name = data[0];
        this.id = data[1];
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
