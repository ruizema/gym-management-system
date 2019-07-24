public class Account extends DataRecord {

    private String id;
    private boolean suspended = false;
    private String email;
    private String name;
    private String type;
    private String report;

    public Account(String[] data) {
        super(data);
        this.email = data[0];
        this.name = data[1];
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

    public String getInfosReport() {
        String infos = "";
        infos += name + "\n" + id + "\n";
        for(int i=2; i < data.length-1; i++){
            infos += data[i] + "\n";
        }
        return infos;
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

    public void addReport(String report) {
        this.report = report;
    }

    public String getReport() {
        return report;
    }
}
