public class Account extends DataRecord {

    private String id;
    private boolean suspended = false;
    private String email;
    private String name;
    private String type;
    private String report;

    /**Constructor of Account
     * 
     *@param data The data to implement an account
     */
    public Account(String[] data) {
        super(data);
        this.email = data[0];
        this.name = data[1];
        this.type = data[6];
    }

    /**Method to create an ID for the account user
     * 
     */
    public void generateId() {
        int id = (int) Math.floor(Math.random() * 1000000000);
        String stringId = "0".repeat(9 - String.valueOf(id).length()) + id;
        this.id = stringId;
    }

    /**Method to access id of Account
     * 
     *@return User ID
     */
    public String getId() {
        return id;
    }

    /**Method to set an ID to the user
     * 
     *@param id Number ID of account client
     */
    public void setId(String id) {
        this.id = id;
    }

    /**Method to access name of Account holder
     * 
     *@return Name of Account holder
     */
    public String getName() {
        return name;
    }

    /**Method to access type of Account
     * 
     *@return Type of Account
     */
    public String getType() {
        return type;
    }

    /**Method to access email of client in Account
     * 
     *@return Email of Account holder
     */
    public String getEmail() {
        return email;
    }

    /**Method to get the informations needed for a1 session report
     * 
     *@return A String of information for the report
     */
    public String getInfosReport() {
        String infos = "";
        infos += name + "\n" + id + "\n";
        for(int i=2; i < data.length-1; i++){
            infos += data[i] + "\n";
        }
        return infos;
    }

    /**Method to write informations into a1 String
     * 
     *@param className ClassName
     *@return ClassName and ID with Account status into String
     */
    public String toString(String className) {
        String superString = super.toString(className);
        superString += "Numéro du membre (9 chiffres): " + id + "\n";
        superString += "Suspendu: " + suspended + "\n";
        return superString;
    }

    /**Method to verify if Account is suspended
     * 
     *@return The truth value of Account status
     */
    public boolean isSuspended() {
        return suspended;
    }

    /**Method to set the status of an Account
     * 
     *@param status Truth value of suspended Account
     */
    public void setSuspended(boolean status) {
        this.suspended = status;
    }

    /**Method to add report to Account
     * 
     *@param report Report
     */
    public void addReport(String report) {
        this.report = report;
    }

    /**Method to access Account report
     * 
     *@return The report of the Account
     */
    public String getReport() {
        return report;
    }
}
