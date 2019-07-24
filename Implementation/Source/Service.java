public class Service extends DataRecord {
    private String name;
    private String id;

    public Service(String[] data) {
        super(data);
        this.name = data[0];
        this.id = generateId();
    }

    private String generateId() {
        String output = "";
        for (int i = 0; i < 7; i++) {
            output += String.valueOf((int) Math.floor(Math.random() * 10));
        }
        return output;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
