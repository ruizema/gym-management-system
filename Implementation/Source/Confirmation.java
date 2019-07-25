public class Confirmation extends DataRecord {

    /**Constructor of Confirmation
     * 
     *@param data Informations to construct Confirmation
     */
    public Confirmation(String[] data) {
        super(data);
    }

    /**Method to access day date of Confirmation
     * 
     *@return The date of confirmation
     */
    public String getDate() {
        String[] dateTime = data[0].split(" ");
        return dateTime[0];
    }

    /**Method to generate informations for session report
     * 
     *@return The informations for report
     */
    public String getProfReport() {
        return data[2] + "\n" + data[3] + "\n" + data[4] + "\n";
    }

    /**Method to accesss the ID of the professional
     * 
     *@return The ID of the professionnal
     */
    public String getProfId() {
        return data[1];
    }

    /**Method to access the ID of member
     * 
     *@return The ID of member
     */
    public String getMemberId() {
        return data[2];
    }

    /**Method to access date and time of Confirmation
     * 
     *@return The date and time of confirmation
     */
    public String getActualDate() {
        return data[0];
    }

    /**Method to access service ID
     * 
     *@return The ID of the service
     */
    public String getServiceId() {
        return data[3];
    }

    /**Method to access the session ID
     * 
     *@return The ID of a1 session
     */
    public String getSessionId() {
        return data[4];
    }
}
