import java.io.IOException;
import java.util.Arrays;

public class Registration extends DataRecord {

    /**Constructor of Registration
     * 
     *@param data Informations of registration
     */
    public Registration(String[] data) {
        super(data);
    }

    /**Method to access employee ID
     * 
     *@return Employee ID
     */
    public int getEmployeeId() {
        return Integer.parseInt(data[2]);
    }

    /**Method to extract informations for confirmation of presence
     * 
     *@return Informations for confirmation of presence
     */
    public String[] getPresenceInformation() {
        return Arrays.copyOfRange(data, 3, 6);
    }
}
