public class Account extends DataRecord {
    // Extra fields
    private String id;
    private boolean suspended = false;

    public Account(String[] data) {
        super(data);
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
        return data[0];
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
