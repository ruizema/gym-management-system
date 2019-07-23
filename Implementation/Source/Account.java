public class Account extends DataRecord {

    private String id;
    private boolean suspended = false;
    private String email;
    private String type;
    private String name;

    public Account(String[] data) {
        super(data);
        this.name = data[0];
        this.email = data[1];
        this.type = data[6];
    }

    public void generateId() {
        int id = (int) Math.floor(Math.random() * 1000000000);
        String stringId = "0".repeat(9 - String.valueOf(id).length()) + id;
        this.id = stringId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }


    public String getEmail() {
        return email;
    }

    public String toString(String className) {
        String superString = super.toString(className);
        superString += "Numéro du membre (9 chiffres): " + id + "\n";
        superString += "Suspendu: " + suspended + "\n";
        return superString;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean status) {
        this.suspended = status;
    }
}
