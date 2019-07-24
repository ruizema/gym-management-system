import java.io.IOException;

public class Service extends DataRecord {
    private String name;
    private String id;

    /**Constructor of Service
     * 
     *@param data Information of service
     */
    public Service(String[] data) {
        super(data);
        this.name = data[0];
        this.id = generateId();
    }

    /**Function creating an ID for service
     * 
     *@return Service ID
     */
    private String generateId() {
        String output = "";
        for (int i = 0; i < 7; i++) {
            output += String.valueOf(Math.floor(Math.random() * 10));
        }
        return output;
    }

    /**Method to get name of service
     * 
     *@return Name of service
     */
    public String getName() {
        return name;
    }

    /**Method to get ID of service
     * 
     *@return ID of service
     */
    public String getId() {
        return id;
    }
}
