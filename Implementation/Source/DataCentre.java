import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DataCentre {

    private Main main;
    private int[] accounts;
    private int[] registrations = new int[1];
    private HashMap[] confirmations;

    public int validateId(int id) {
        if (id == 1234567) {
            return 0;
        } else {
            return 1;
        }
    }

    public int createDataRecord(String[] data, String dataType) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(dataType.toLowerCase() + "s.txt", true));
        DataRecord dataRecord;
        switch (dataType) {
            case "Session":
                dataRecord = new Session(data);
                break;
            case "Registration":
                dataRecord = new Registration(data);
                break;
            case "Confirmation":
                dataRecord = new Confirmation(data);
                break;
            case "Account":
                dataRecord = new Account(data);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dataType);
        }
        writer.write(((DataRecord) dataRecord).toString());
        writer.close();
        // return the id if an account, -1 otherwise, -2 if a field is wrong
        if (dataType.equals("Account")) {
            return ((Account) dataRecord).getId();
        } else {
            return -1;
        }
    }
}
