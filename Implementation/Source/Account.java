public class Account extends DataRecord {
    // Extra fields
    private int id;
    private boolean suspended = false;

    public Account(String[] data) {
        super(data);
        id = generateId();
    }

    public int getId() {
        return id;
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
