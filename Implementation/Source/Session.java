import java.io.IOException;

public class Session extends DataRecord {

    /**Constructor of Session
     * 
     *@param data Informations for session
     */
    public Session(String[] data) {
        super(data);
    }

    /**Method to access price of a session
     * 
     *@return Price of session
     */
    public int getPrice() {
        return Integer.parseInt(data[9]);
    }

    /**Method to access session ID
     * 
     *@return Session ID
     */
    // TODO: generate sessionId & overwrite whatever the user entered lmao
    public String getSessionId() {
        return data[8];
    }
}
