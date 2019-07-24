import java.io.IOException;

public class Session extends DataRecord {
    private String id;


    /**Constructor of Session
     * 
     *@param data Informations for session
     */
    public Session(String[] data) {
        super(data);
        id = data[7].substring(0, 3) + data[8].substring(0, 2) + data[6].substring(data[6].length() - 2);
    }

    /**Method to access price of a session
     * 
     *@return Price of session
     */
    public double getPrice() {
        return Double.parseDouble(data[9]);
    }

    /**Method to access session ID
     * 
     *@return Session ID
     */
    public String getSessionId() {
        return id;
    }
}
